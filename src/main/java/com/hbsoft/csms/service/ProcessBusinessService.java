package com.hbsoft.csms.service;

import com.hb.bean.CallResult;
import com.hbsoft.common.enumtype.*;
import com.hbsoft.csms.bean.*;
import com.hbsoft.csms.dao.service.LoanDistributeBeanDaoService;
import com.hbsoft.csms.dao.service.LoanDistributeLogBeanDaoService;
import com.hbsoft.csms.dao.service.LoanDistributeSourcesBeanDaoService;
import com.hbsoft.csms.dao.service.LoanInfoBeanDaoService;
import com.hbsoft.csms.vo.ProcessHandleVo;
import com.hbsoft.csms.vo.ProcessVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Service
public class ProcessBusinessService {

    @Autowired
    LoanInfoBeanDaoService loanInfoBeanDaoService;

    @Autowired
    ProcessHandleService processHandleService;

    @Autowired
    LoanDistributeBeanDaoService loanDistributeBeanDaoService;

    @Autowired
    LoanDistributeSourcesBeanDaoService loanDistributeSourcesBeanDaoService;

    @Autowired
    LoanDistributeLogBeanDaoService loanDistributeLogBeanDaoService;

    @Transactional
    public CallResult<String> claimProcessHandle(ProcessHandleVo vo, Integer limitMonth,String userId) throws Exception {
        CallResult result = new CallResult<>();
        if(StringUtils.isBlank(userId) ){
            result.setFailResult("请重新登录");
            return result;
        }

            LoanInfoBean loanInfoBean = loanInfoBeanDaoService.getById(Integer.valueOf(vo.getId_prikey()));
            if(ObjectUtils.isEmpty(loanInfoBean)){
                result.setFailResult("未查询到此业务");
                return result;
            }
            String loanContractNum = loanInfoBean.getLoanContractNum();
            ProcessVo processvo = new ProcessVo();
            processvo.setUserId(userId);
            processvo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_CLAIM.getCode());
            processvo.setNodeSatus(vo.getProcessStatus());
            processvo.setBusinessId(vo.getId_prikey());
            processvo.setBusinessCode(loanInfoBean.getLoanContractNum());
            processvo.setNodeId(Integer.valueOf(vo.getNodeId()));
            processvo.setRemark(vo.getRemark());
            processvo.setFilePath(vo.getFiles());
            if(YesOrNoEnum.YES_OR_NO_YES.getCode().equals(loanInfoBean.getIsShare())){
                processvo.setBranch("2");
            }else {
                processvo.setBranch("1");
            }
            CallResult<ProcessHandleBean> processResult = processHandleService.processHandle(processvo);
            if(processResult.isExec()){
                ProcessHandleBean processHandleBean = processResult.getData();
                if("callAssetsAudit".equals(processHandleBean.getProcessHandleMethod())){
                    if(StringUtils.isBlank(loanContractNum)){
                        result.setFailResult("贷款合同号不能为空");
                        return result;
                    }
                    LoanDistributeBean ldb = new LoanDistributeBean();
                    ldb.setLoanContractNum(loanContractNum);
                    LoanDistributeBean ldbByField = loanDistributeBeanDaoService.getByField(ldb);
                    if(ldbByField==null){
                        result.setFailResult("参数有误");
                        return result;
                    }
                    Date date = new Date();
                    //添加LoanDistributeSource
                    LoanDistributeSourcesBean lds = new LoanDistributeSourcesBean();
                    lds.setLoanContractNum(ldbByField.getLoanContractNum());
                    lds.setManager(ldbByField.getManager());
                    lds.setNumber(ldbByField.getNumber());
                    lds.setNumberType(ldbByField.getNumberType());
                    lds.setOrgNum(ldbByField.getOrgNum());
                    lds.setRelId(ldbByField.getId_prikey());
                    lds.setCreateBy(userId);
                    lds.setCreateOn(date);
                    loanDistributeSourcesBeanDaoService.add(lds);

                    //修改 LoanDistribute
                    ldbByField.setLimitMonth(limitMonth);
                    ldbByField.setNumber(userId);
                    ldbByField.setNumberType(DistributeTypeEnum.DISTRIBUTE_TYPE_PERSON.getCode());
                    ldbByField.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_CLAIM.getCode());
                    ldbByField.setOrgNum("");
                    ldbByField.setManager("");
                    ldbByField.setUpdateBy(userId);
                    ldbByField.setUpdateOn(date);
                    loanDistributeBeanDaoService.set(ldbByField);

                    //添加 loanDistributeLog
                    LoanDistributeLogBean log = new LoanDistributeLogBean();
                    log.setLimitMonth(limitMonth);
                    log.setLoanContractNum(loanContractNum);
                    log.setNumber(userId);
                    log.setNumberType(DistributeTypeEnum.DISTRIBUTE_TYPE_PERSON.getCode());
                    log.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_CLAIM.getCode());
                    log.setCreateBy(userId);
                    log.setCreateOn(date);
                    loanDistributeLogBeanDaoService.add(log);

                    //更新loanInfo
                    LoanInfoBean loanInfoParam = new LoanInfoBean();
                    loanInfoParam.setClaimFlag(LoanClaimEnum.LOAN_CLAIM_YES_ENUM.getCode());
                    loanInfoParam.setId_prikey(loanInfoBean.getId_prikey());
                    loanInfoBeanDaoService.set(loanInfoParam);

                    result.setSuccessResult();
                }
            }else{
                result.setFailResult(processResult.getMsg());
            }
        result.setSuccessResult("操作成功");
        return result;
    }

}
