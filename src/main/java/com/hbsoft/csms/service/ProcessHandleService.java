package com.hbsoft.csms.service;

import com.hb.bean.CallResult;
import com.hbsoft.common.enumtype.*;
import com.hbsoft.csms.bean.*;
import com.hbsoft.csms.dao.HbcmUserDao;
import com.hbsoft.csms.dao.ProcessDetailBeanDao;
import com.hbsoft.csms.dao.ProcessHandleDao;
import com.hbsoft.csms.dao.service.*;
import com.hbsoft.csms.vo.ProcessVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName:    ProcessHandleService
 * Package:    com.hbsoft.csms.service
 * Description:
 * Datetime:    2020/5/11   10:47
 * Author:  hwl
 */
@Service
public class ProcessHandleService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ProcessBeanDaoService processBeanDaoService;
    @Autowired
    ProcessDetailBeanDao processDetailBeanDao;
    @Autowired
    ProcessDetailBeanDaoService processDetailBeanDaoServicel;
    @Autowired
    ProcessBusinessBeanDaoService processBusinessBeanDaoService;
    @Autowired
    ProcessDetailBeanDaoService processDetailBeanDaoService;
    @Autowired
    ProcessLogBeanDaoService processLogBeanDaoService;
    @Autowired
    ProcessFileBeanDaoService processFileBeanDaoService;
    @Autowired
    ProcessOperatorConfigBeanDaoService processOperatorConfigBeanDaoService;
    @Autowired
    HbcmUserDao hbcmUserDao;
    @Autowired
    ProcessBusinessOperatorBeanDaoService processBusinessOperatorBeanDaoService;
    @Autowired
    LoanRepayBeanDaoService loanRepayBeanDaoService;
    @Autowired
    ProcessHandleDao processHandleDao;
    @Autowired
    LoanRepayService loanRepayService;

    public CallResult<ProcessBusinessBean> productProcessInfo(Integer id_pirkey, String processAttribute) throws Exception {
        CallResult<ProcessBusinessBean> result = new CallResult<>();

        if (ObjectUtils.isEmpty(id_pirkey) || StringUtils.isBlank(processAttribute)) {
            result.setFailResult("参数有误");
            return result;
        }
        ProcessBusinessBean processBusinessBean = processBusinessBeanDaoService.getById(id_pirkey);
        if (ObjectUtils.isNotEmpty(processBusinessBean)) {
            ProcessLogBean logParam = new ProcessLogBean();
            logParam.setPd_id_prikey(processBusinessBean.getId_prikey());
            List<ProcessLogBean> logList = processLogBeanDaoService.getAll(logParam);
            if (ObjectUtils.isNotEmpty(logList)) {
                for (ProcessLogBean pl : logList) {
                    ProcessFileBean param = new ProcessFileBean();
                    param.setBatchCode(String.valueOf(pl.getId_prikey()));
                    List<ProcessFileBean> processFileBeanList = processFileBeanDaoService.getAll(param);
                    if (ObjectUtils.isNotEmpty(processFileBeanList)) {
                        pl.setProcessFileList(processFileBeanList);
                    }
                }
                processBusinessBean.setLogList(logList);
            }
        }
        result.setSuccessResult(processBusinessBean);
        return result;
    }


    @Transactional
    public CallResult<ProcessHandleBean> processHandle(ProcessVo vo) throws Exception {
        CallResult<ProcessHandleBean> result = new CallResult<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ProcessVo>> constraintViolations = validator.validate(vo);
        if (ObjectUtils.isNotEmpty(constraintViolations)) {
            for (ConstraintViolation<ProcessVo> item : constraintViolations) {
                result.setFailResult(item.getMessage());
                return result;
            }
        }
        Map<String, Object> map = processDetailBeanDao.findMaxNodeAndMinNodeByNodeType(vo.getProcessAttribute());
        if (ObjectUtils.isEmpty(map)) {
            result.setCode(400);
            result.setMsg("流程属性不存在");
            return result;
        }
        synchronized (this) {
            //查询流程节点
            ProcessDetailBean processDetailParam = new ProcessDetailBean();
            processDetailParam.setNodeType(vo.getProcessAttribute());
            processDetailParam.setNodeId(vo.getNodeId());
            ProcessDetailBean processDetail = processDetailBeanDaoService.getByField(processDetailParam);
            if (ObjectUtils.isEmpty(processDetail)) {
                processDetail = processDetailBeanDaoService.findMixNodeIdByNodeType(vo.getProcessAttribute());
                if (ObjectUtils.isEmpty(processDetail)) {
                    result.setCode(400);
                    result.setMsg("此业务节点不存在");
                    return result;
                }
            }
            if (StringUtils.isNotBlank(processDetail.getNexstNode())) {
                if (processDetail.getNexstNode().indexOf(",") > 0) {
                    if (processDetail.getNexstNodeCondition().indexOf(",") > 0) {
                        String[] nextNodes = processDetail.getNexstNodeCondition().split(",");
                        for (String nextNodesAndBranch : nextNodes) {
                            String[] nextNodesAndBranchs = nextNodesAndBranch.split(":");
                            if (nextNodesAndBranchs.length == 2) {
                                if (nextNodesAndBranchs[0].equals(vo.getBranch())) {
                                    processDetail.setNexstNode(nextNodesAndBranchs[1]);
                                    break;
                                }
                            } else {
                                result.setFailResult("流程分支配置有误");
                                return result;
                            }
                        }
                    } else {
                        result.setFailResult("流程分支配置有误");
                        return result;
                    }
                }
                if (StringUtils.isNotBlank(processDetail.getLastNode())) {
                    if (processDetail.getLastNode().indexOf(",") > 0) {
                        if (processDetail.getLastNodeCondition().indexOf(",") > 0) {
                            String[] lastNodes = processDetail.getLastNodeCondition().split(",");

                            for (String lastNodesAndBranch : lastNodes) {
                                String[] lastNodesAndBranchs = lastNodesAndBranch.split(":");
                                if (lastNodesAndBranchs.length == 2) {
                                    if (lastNodesAndBranchs[0].equals(vo.getBranch())) {
                                        processDetail.setLastNode(lastNodesAndBranchs[1]);
                                        break;
                                    }
                                } else {
                                    result.setFailResult("流程分支配置有误");
                                }
                            }
                        } else {
                            result.setFailResult("流程分支配置有误");
                            return result;
                        }
                    }
                }
            }


            ProcessBusinessBean param = new ProcessBusinessBean();
            param.setBusinessId(vo.getBusinessId());
            param.setProcessAttribute(vo.getProcessAttribute());
            ProcessBusinessBean processBusinessBean = processBusinessBeanDaoService.findByProcessStatusDingAndBack(param);
            //验证 防止重复提交
            if (vo.getNodeId() != 0) {
                if (ObjectUtils.isEmpty(processBusinessBean)) {
                    result.setCode(400);
                    result.setMsg("此业务节点流程有误");
                    return result;
                }
                if (!processBusinessBean.getNodeId().equals(vo.getNodeId())) {
                    result.setCode(400);
                    result.setMsg("此业务节点流程有误");
                    return result;
                }
            }

            ProcessBusinessBean processBusinessRecode = new ProcessBusinessBean();
            processBusinessRecode.setUpdateBy(vo.getUserId());
            processBusinessRecode.setUpdateOn(new Date());
            if (ObjectUtils.isEmpty(processBusinessBean)) {
                //开启流程
                CallResult<String> volidateResult = volidateProcess(vo.getBusinessCode(), vo.getProcessAttribute());
                if (!volidateResult.isExec()) {
                    result.setFailResult(volidateResult.getMsg());
                    return result;
                }
                processBusinessRecode.setBusinessId(vo.getBusinessId());
                processBusinessRecode.setBusinessCode(vo.getBusinessCode());
                processBusinessRecode.setProcessAttribute(vo.getProcessAttribute());
                processBusinessRecode.setNodeId(Integer.valueOf(processDetail.getNexstNode()));
                processBusinessRecode.setCreateBy(vo.getUserId());
                processBusinessRecode.setCreateOn(new Date());
                processBusinessRecode.setProcessStatus(ProcessBusinessStatusEnum.PROCESS_NODE_STATUS_DOING.getCode());
                processBusinessBeanDaoService.addPrikey(processBusinessRecode);
            } else {
                if (ProcessBusinessStatusEnum.PROCESS_NODE_STATUS_CANCEL.getCode().equals(processBusinessBean.getProcessStatus())) {
                    result.setFailResult("本流程已撤销");
                    return result;
                }
                if (processBusinessBean.getNodeId().equals(processDetail.getNodeId())) {
                    if (ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode().equals(vo.getNodeSatus())) {
                        processBusinessRecode.setNodeId(Integer.valueOf(processDetail.getNexstNode()));
                        processBusinessRecode.setProcessStatus(ProcessBusinessStatusEnum.PROCESS_NODE_STATUS_DOING.getCode());
                        if (processDetail.getNexstNode().equals(String.valueOf(map.get("maxNode")))) {
                            processBusinessRecode.setProcessStatus(ProcessBusinessStatusEnum.PROCESS_NODE_STATUS_FINISH.getCode());
                        }
                    } else if (ProcessNodeStatusEnum.PROCESS_NODE_STATUS_NO.getCode().equals(vo.getNodeSatus())) {
                        processBusinessRecode.setNodeId(Integer.valueOf(processDetail.getLastNode()));
                        if (processDetail.getLastNode().equals(String.valueOf(map.get("minNode")))) {
                            processBusinessRecode.setProcessStatus(ProcessBusinessStatusEnum.PROCESS_NODE_STATUS_BACK.getCode());
                        }
                    } else if (ProcessNodeStatusEnum.PROCESS_NODE_STATUS_STOP.getCode().equals(vo.getNodeSatus())) {
                        ProcessDetailBean processEnd = processDetailBeanDao.findMaxNodeIdByNodeType(vo.getProcessAttribute());
                        processBusinessRecode.setNodeId(processEnd.getNodeId());
                    } else {
                        result.setCode(400);
                        result.setMsg("节点状态有误");
                        return result;
                    }
                    processBusinessRecode.setId_prikey(processBusinessBean.getId_prikey());
                    processBusinessBeanDaoService.set(processBusinessRecode);
                } else {
                    result.setCode(400);
                    result.setMsg("此业务节点流程有误");
                    return result;
                }
            }

            //操作人
            if (!processBusinessRecode.getNodeId().equals(map.get("maxNode")) && !processBusinessRecode.getNodeId().equals(map.get("minNode"))) {
                ProcessBusinessOperatorBean pbOperator = new ProcessBusinessOperatorBean();
                pbOperator.setPbId(processBusinessRecode.getId_prikey());
                pbOperator.setNode(processBusinessRecode.getNodeId());
                processBusinessOperatorBeanDaoService.deleteByPdIdAndNode(pbOperator);

                ProcessOperatorConfigBean pconfig = new ProcessOperatorConfigBean();
                pconfig.setNodeId(processBusinessRecode.getNodeId());
                ProcessOperatorConfigBean processOperatorConfigBean = processOperatorConfigBeanDaoService.getByField(pconfig);
                if (ObjectUtils.isEmpty(processOperatorConfigBean)) {
                    throw new RuntimeException("未查询到下一步操作人员");
                }
                if (ProcessOperatorRoleTypeEnum.PROCESS_OPERATOR_ROLE_DETAIL.getCode().equals(processOperatorConfigBean.getRoleType())) {
                    if (StringUtils.isBlank(processOperatorConfigBean.getRole())) {
                        throw new RuntimeException("未设置该节点审批人");
                    }
                    pbOperator.setOperator(processOperatorConfigBean.getRole());
                    processBusinessOperatorBeanDaoService.add(pbOperator);
                } else if (ProcessOperatorRoleTypeEnum.PROCESS_OPERATOR_ROLE_TYPE_TEAM_MANAGER.getCode().equals(processOperatorConfigBean.getRoleType())) {

                    if (ProcessOperatorRoleEnum.PROCESS_OPERATOR_ROLE_TEAM_LEADER.getCode().equals(processOperatorConfigBean.getRole())) {
                        Map<String, Object> deptMap = hbcmUserDao.findDeptMananger(vo.getUserId());
                        if (ObjectUtils.isEmpty(deptMap)) {
                            throw new RuntimeException("未查询到部门负责人");
                        }
                        pbOperator.setOperator((String) deptMap.get("manager"));
                        processBusinessOperatorBeanDaoService.add(pbOperator);
                    }
                }
            }
            //日志
            ProcessLogBean log = new ProcessLogBean();
            log.setPd_id_prikey(processBusinessRecode.getId_prikey());
            log.setBusinessId(vo.getBusinessId());
            log.setNodeId(processDetail.getNodeId());
            log.setNexstNode(processBusinessRecode.getNodeId());
            log.setOperateTime(new Date());
            log.setOperateType(Integer.valueOf(vo.getNodeSatus()));
            log.setOperator(vo.getUserId());
            log.setRemark(vo.getRemark());
            log.setProcessAttribute(vo.getProcessAttribute());
            int logId = processLogBeanDaoService.addPrikey(log);
            //附件
            if (StringUtils.isNotBlank(vo.getFilePath())) {
                String[] filePath = vo.getFilePath().split(";");
                if (null != filePath) {
                    for (String file : filePath) {
                        String[] f = file.split(",");
                        if (null != f) {
                            ProcessFileBean pf = new ProcessFileBean();
                            pf.setBatchCode(String.valueOf(logId));
                            pf.setBusinessId(vo.getBusinessId());
                            pf.setNodeId(processDetail.getNodeId());
                            pf.setOperateTime(new Date());
                            pf.setOperator(vo.getUserId());
                            pf.setAppendixType(f[0]);
                            pf.setAppendix(f[1]);
                            processFileBeanDaoService.add(pf);
                        }
                    }
                }
            }
            ProcessHandleBean processHandleBean = new ProcessHandleBean();
            if (StringUtils.isNotBlank(processDetail.getDisposeMethod())) {
                processHandleBean.setProcessHandleMethod(processDetail.getDisposeMethod());
            }
            result.setData(processHandleBean);
        }
        result.setCode(0);
        result.setMsg("操作成功");
        return result;
    }


    public CallResult<String> processHandleCancel(Integer id_prikey, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();
        ProcessBusinessBean processBusinessBean = processBusinessBeanDaoService.getById(id_prikey);

        ProcessBusinessBean pbParam = new ProcessBusinessBean();
        pbParam.setId_prikey(id_prikey);
        pbParam.setProcessStatus(ProcessBusinessStatusEnum.PROCESS_NODE_STATUS_CANCEL.getCode());
        processBusinessBeanDaoService.set(pbParam);

        ProcessLogBean log = new ProcessLogBean();
        log.setBusinessId(processBusinessBean.getBusinessId());
        log.setNodeId(processBusinessBean.getNodeId());
        log.setNexstNode(null);
        log.setOperateTime(new Date());
        log.setOperateType(Integer.valueOf(ProcessNodeStatusEnum.PROCESS_NODE_STATUS_STOP.getCode()));
        log.setOperator(userId);
        //log.setRemark(remark);
        log.setProcessAttribute(processBusinessBean.getProcessAttribute());
        processLogBeanDaoService.addPrikey(log);
        result.setSuccessResult();
        return result;
    }


    public CallResult<List<ProcessBusinessBean>> findProcessBusinessByProcessStatusAndBusinessCode(String businessCode) throws Exception {
        CallResult<List<ProcessBusinessBean>> result = new CallResult<>();
        if (StringUtils.isBlank(businessCode)) {
            result.setFailResult("业务id不能为空");
            return result;
        }
        ProcessBusinessBean pb = new ProcessBusinessBean();
        pb.setBusinessCode(businessCode);
        List<ProcessBusinessBean> list = processBusinessBeanDaoService.findNotDoingAndCancelAll(pb);
        result.setSuccessResult(list);
        return result;
    }

    public CallResult<String> volidateProcess(String businessCode, String processAttribute) throws Exception {
        CallResult<String> result = new CallResult<>();
        if (StringUtils.isBlank(businessCode) || StringUtils.isBlank(processAttribute)) {
            result.setFailResult("参数有误");
            return result;
        }
        LoanRepayBean loanRepayBean = new LoanRepayBean();
        loanRepayBean.setLoanContractNum(businessCode);
        loanRepayBean.setRepayType(Integer.valueOf(RepayTypeEnum.REPAY_TYPE_NORMAL.getCode()));
        loanRepayBean.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_FIRST.getCode());
        LoanRepayBean lr = loanRepayBeanDaoService.getByField(loanRepayBean);
        if (ObjectUtils.isNotEmpty(lr)) {
            result.setFailResult("已有 还款 流程 ");
            return result;
        }

        CallResult<List<ProcessBusinessBean>> pbResult = findProcessBusinessByProcessStatusAndBusinessCode(businessCode);
        if (pbResult.isExec()) {
            List<ProcessBusinessBean> list = pbResult.getData();
            for (ProcessBusinessBean pb : list) {
                if (ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_REVOKE.getCode().equals(pb.getProcessAttribute())
                        || ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_ADD.getCode().equals(pb.getProcessAttribute())
                        || ProcessBusinessEnum.PROCESS_BUSINESS_LOANINFO_UPDATE.getCode().equals(pb.getProcessAttribute())) {
                    result.setFailResult("已有流程  " + pb.getProcessAttributeName());
                    return result;
                }
            }
        }
        result.setSuccessResult();
        return result;
    }

    @Scheduled(cron = "0 50 23 * * ?")
    @Transactional
    public CallResult<String> allIncompleteProcessCancel() throws Exception {
        logger.info("定时任务开始+++++++++++++++++++++++++++++++++++++++++++++++");
        CallResult<String> result = new CallResult<>();
        List<ProcessBusinessBean> list = processHandleDao.findAllUnfinishedProcess();
        if (list != null && list.size() != 0) {
            for (ProcessBusinessBean pb : list) {
                if(ProcessBusinessStatusEnum.PROCESS_NODE_STATUS_FINISH.getCode().equals(pb.getProcessStatus())){
                    continue;
                }
                //信息修改撤销
                if (ProcessBusinessEnum.PROCESS_BUSINESS_LOANINFO_UPDATE.getCode().equals(pb.getProcessAttribute())) {
                    processHandleCancel(pb.getId_prikey(), "Administrator");
                }
                //还款补录撤销
                if (ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_ADD.getCode().equals(pb.getProcessAttribute())) {
                    processHandleCancel(pb.getId_prikey(), "Administrator");
                }
                //还款抹账撤销
                if (ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_REVOKE.getCode().equals(pb.getProcessAttribute())) {
                    loanRepayService.revokeRepayCancel(pb.getId_prikey(),"Administrator");
                }
            }
        }
        return result;
    }
}
