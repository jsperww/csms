package com.hbsoft.csms.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hb.bean.CallResult;
import com.hb.util.DateUtil;
import com.hb.util.V1Pass;
import com.hbsoft.common.constant.Constant;
import com.hbsoft.common.enumtype.LoanFileTypeEnum;
import com.hbsoft.common.enumtype.LoanGuaranteeRelEnum;
import com.hbsoft.common.enumtype.LoanStatusEnum;
import com.hbsoft.common.enumtype.YesOrNoEnum;
import com.hbsoft.csms.bean.*;
import com.hbsoft.csms.dao.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndicmentService {

    @Autowired
    IndictmentCourtDaoService indictmentCourtDaoService;

    @Autowired
    IndictmentExecutionDaoService indictmentExecutionDaoService;

    @Autowired
    IndictmentInfoDaoService indictmentInfoDaoService;

    @Autowired
    IndictmentMediationDaoService indictmentMediationDaoService;

    @Autowired
    LoanMortgageDaoService loanMortgageDaoService;

    @Autowired
    LoanInfoBeanDaoService loanInfoBeanDaoService;

    @Autowired
    LoanGuaranteeDaoService loanGuaranteeDaoService;

    @Autowired
    LoanFileInfoBeanDaoService loanFileInfoBeanDaoService;

    @Autowired
    LoanInfoFileService loanInfoFileService;

    public CallResult<LoanInfoBean> selectIndictmentLoanInfo(String loanContractNum, Integer id_prikey) throws Exception {
        CallResult<LoanInfoBean> result = new CallResult<>();

        if (StringUtils.isNotEmpty(loanContractNum)) {
            LoanInfoBean loanInfoBean = getLoanInfoByLoanContractNum(loanContractNum);
            loanInfoBean.setMobile(V1Pass.pass_decode(loanInfoBean.getMobile()));
            loanInfoBean.setName(V1Pass.pass_decode(loanInfoBean.getName()));
            loanInfoBean.setIdNum(V1Pass.pass_decode(loanInfoBean.getIdNum()));
            if (loanInfoBean.getCollectionDate() != null) {
                loanInfoBean.setCollectionDateStr(DateUtil.formatDate(loanInfoBean.getCollectionDate(), "yyyy-MM-dd"));
            } else {
                loanInfoBean.setCollectionDateStr("");
            }
            if (loanInfoBean.getLoanBeginDate() != null) {
                loanInfoBean.setLoanBeginDateStr(DateUtil.formatDate(loanInfoBean.getLoanBeginDate(), "yyyy-MM-dd"));
            }
            if (loanInfoBean.getLoanEndDate() != null) {
                loanInfoBean.setLoanEndDateStr(DateUtil.formatDate(loanInfoBean.getLoanEndDate(), "yyyy-MM-dd"));
            }
            if (loanInfoBean.getDistributeFlag() != null) {
                loanInfoBean.setDistributeFlagInt(Integer.valueOf(loanInfoBean.getDistributeFlag()));
            }
            if (loanInfoBean != null) {
                //抵押物
                LoanMortgage loanMortgage = new LoanMortgage();
                loanMortgage.setLoanContractNum(loanInfoBean.getLoanContractNum());
                loanInfoBean.setMortgageList(loanMortgageDaoService.getAll(loanMortgage));
                //担保人
                LoanGuarantee loanGuarantee = new LoanGuarantee();
                loanGuarantee.setLoanContractNum(loanInfoBean.getLoanContractNum());
                loanGuarantee.setRelType(LoanGuaranteeRelEnum.LOAN_GUARANTEE_REL_LOANGUARANTEE.getCode());
                loanInfoBean.setGuaranteeList(loanGuaranteeDaoService.getAll(loanGuarantee));
                //配偶
                LoanGuarantee loanMate = new LoanGuarantee();
                loanMate.setLoanContractNum(loanInfoBean.getLoanContractNum());
                loanMate.setRelType(LoanGuaranteeRelEnum.LOAN_GUARANTEE_REL_MATE.getCode());
                loanInfoBean.setMateList(loanGuaranteeDaoService.getAll(loanMate));
                //诉讼
                IndictmentInfo indictmentInfo = new IndictmentInfo();
                indictmentInfo.setId_prikey(id_prikey);
                loanInfoBean.setIndictmentInfo(indictmentInfoDaoService.getByField(indictmentInfo));
                //调解
                IndictmentMediation indictmentMediation = new IndictmentMediation();
                indictmentMediation.setIndictmentId(String.valueOf(loanInfoBean.getIndictmentInfo().getId_prikey()));
                loanInfoBean.setMediations(indictmentMediationDaoService.getAll(indictmentMediation));
                //开庭
                IndictmentCourt indictmentCourt = new IndictmentCourt();
                indictmentCourt.setIndictmentId(String.valueOf(loanInfoBean.getIndictmentInfo().getId_prikey()));
                loanInfoBean.setCourts(indictmentCourtDaoService.getAll(indictmentCourt));
                //执行结果
                IndictmentExecution indictmentExecution = new IndictmentExecution();
                indictmentExecution.setIndictmentId(String.valueOf(loanInfoBean.getIndictmentInfo().getId_prikey()));
                loanInfoBean.setExecutions(indictmentExecutionDaoService.getAll(indictmentExecution));

                result.setSuccessResult(loanInfoBean);
            } else {
                result.setFailResult("暂无数据");
            }
        } else {
            result.setFailResult("主键参数错误");
        }
        return result;
    }

    public LoanInfoBean getLoanInfoByLoanContractNum(String loanContractNum) throws Exception {
        if (StringUtils.isEmpty(loanContractNum)) {
            return null;
        }
        LoanInfoBean param = new LoanInfoBean();
        param.setLoanContractNum(loanContractNum);
        return loanInfoBeanDaoService.getByField(param);
    }

    @Transactional
    public CallResult<String> updateIndictmentLoanInfo(IndictmentInfo indictmentInfo, String userId) throws Exception{
        CallResult result = new CallResult<>();
        LoanInfoBean loanInfoBean = new LoanInfoBean();
        loanInfoBean.setLoanContractNum(indictmentInfo.getLoanContractNum());
        LoanInfoBean loanInfoBeanSource = loanInfoBeanDaoService.getByField(loanInfoBean);
        if (ObjectUtils.isEmpty(loanInfoBeanSource)) {
            result.setFailResult("没有客户信息");
            return result;
        }
        List<IndictmentMediation> indictmentMediationList = new Gson().fromJson(indictmentInfo.getMediations(),
                new TypeToken<List<IndictmentMediation>>() {
                }.getType());
        List<IndictmentCourt> indictmentCourtList = new Gson().fromJson(indictmentInfo.getCourts(),
                new TypeToken<List<IndictmentCourt>>() {
                }.getType());
        List<IndictmentExecution> indictmentExecutionList = new Gson().fromJson(indictmentInfo.getExecutions(),
                new TypeToken<List<IndictmentExecution>>() {
                }.getType());
        if (null != indictmentInfo) {
            //判断是否调解
            if (null != indictmentMediationList && indictmentMediationList.size() > 0) {
                indictmentInfo.setMediationFlag(YesOrNoEnum.YES_OR_NO_YES.getCode());
            }else {
                indictmentInfo.setMediationFlag(YesOrNoEnum.YES_OR_NO_NO.getCode());
            }
            //判断是否开庭
            if (null != indictmentCourtList && indictmentCourtList.size() > 0) {
                indictmentInfo.setCourtFlag(YesOrNoEnum.YES_OR_NO_YES.getCode());
            }else {
                indictmentInfo.setCourtFlag(YesOrNoEnum.YES_OR_NO_NO.getCode());
            }
            //判断是否执行
            if (null != indictmentExecutionList && indictmentExecutionList.size() > 0) {
                indictmentInfo.setExecutionFlag(YesOrNoEnum.YES_OR_NO_YES.getCode());
            }else {
                indictmentInfo.setExecutionFlag(YesOrNoEnum.YES_OR_NO_NO.getCode());
            }
            //更新诉讼信息
            if (null != indictmentInfo.getId_prikey() && !indictmentInfo.getDelFlag().equals(Constant.DELFLAG)) {
                indictmentInfo.setUpdateBy(userId);
                indictmentInfo.setUpdateOn(new Date());
                indictmentInfoDaoService.set(indictmentInfo);
                //附件信息

                loanInfoFileService.handleLoanFile(indictmentInfo.getLoanFiles(),indictmentInfo.getId_prikey(),userId,LoanFileTypeEnum.LOAN_FILE_TYPE_INDICTMENT.getCode());

            } else if (null != indictmentInfo.getId_prikey() && indictmentInfo.getDelFlag().equals(Constant.DELFLAG)) {
                indictmentInfoDaoService.removeOne(indictmentInfo.getId_prikey());
                //删除附件
                LoanFileInfoBean f1  = new LoanFileInfoBean();

            }else if (null == indictmentInfo.getId_prikey()){
                indictmentInfo.setCreateBy(userId);
                indictmentInfo.setCreateOn(new Date());
                indictmentInfo.setUpdateBy(userId);
                indictmentInfo.setUpdateOn(new Date());
                indictmentInfo.setLoanContractNum(loanInfoBeanSource.getLoanContractNum());
                indictmentInfoDaoService.addPrikey(indictmentInfo);

                    loanInfoFileService.handleLoanFile(indictmentInfo.getLoanFiles(), indictmentInfo.getId_prikey(), userId, LoanFileTypeEnum.LOAN_FILE_TYPE_INDICTMENT.getCode());

                //更新loaninfo表
                LoanInfoBean loanInfoParam = new LoanInfoBean();
                loanInfoParam.setLoanContractNum(indictmentInfo.getLoanContractNum());
                LoanInfoBean loanInfo = loanInfoBeanDaoService.getByField(loanInfoParam);
                loanInfoParam.setLoanStatus(Integer.valueOf(LoanStatusEnum.LOAN_STATUS_PROSECUTION.getCode()));
                loanInfoParam.setId_prikey(loanInfo.getId_prikey());
                loanInfoBeanDaoService.set(loanInfoParam);
            }
            if (YesOrNoEnum.YES_OR_NO_YES.getCode().equals(indictmentInfo.getMediationFlag())) {
                //更新调解信息
                for (IndictmentMediation mediation: indictmentMediationList) {
                    if (null != mediation) {
                        if (null != mediation.getId_prikey() && !mediation.getDelFlag().equals(Constant.DELFLAG)) {
                            if ("".equals(mediation.getMediationDate())) {
                                mediation.setMediationDate(null);
                            }
                            mediation.setIndictmentId(String.valueOf(indictmentInfo.getId_prikey()));
                            indictmentMediationDaoService.set(mediation);

                            loanInfoFileService.handleLoanFile(mediation.getLoanFiles(), mediation.getId_prikey(), userId, LoanFileTypeEnum.LOAN_FILE_TYPE_INDICTMENT_MEDIATION.getCode());

                        }else if (null != mediation.getId_prikey() && Constant.DELFLAG.equals(mediation.getDelFlag())) {
                            indictmentMediationDaoService.removeOne(mediation.getId_prikey());
                        }else if (null == mediation.getId_prikey()){
                            if ("".equals(mediation.getMediationDate())) {
                                mediation.setMediationDate(null);
                            }
                            mediation.setIndictmentId(String.valueOf(indictmentInfo.getId_prikey()));
                            indictmentMediationDaoService.addPrikey(mediation);

                            loanInfoFileService.handleLoanFile(mediation.getLoanFiles(), mediation.getId_prikey(), userId, LoanFileTypeEnum.LOAN_FILE_TYPE_INDICTMENT_MEDIATION.getCode());

                        }
                    }
                }
            }
            if (YesOrNoEnum.YES_OR_NO_YES.getCode().equals(indictmentInfo.getCourtFlag())) {
                //更新开庭信息
                for (IndictmentCourt court : indictmentCourtList) {
                    if (null != court) {
                        if (null != court.getId_prikey() && !court.getDelFlag().equals(Constant.DELFLAG)) {
                            if ("".equals(court.getCourtDate())) {
                                court.setCourtDate(null);
                            }
                            court.setIndictmentId(String.valueOf(indictmentInfo.getId_prikey()));
                            indictmentCourtDaoService.set(court);

                                loanInfoFileService.handleLoanFile(court.getLoanFiles(), court.getId_prikey(), userId, LoanFileTypeEnum.LOAN_FILE_TYPE_INDICTMENT_COURT.getCode());

                        }else if (null != court.getId_prikey() && Constant.DELFLAG.equals(court.getDelFlag())) {
                            indictmentCourtDaoService.removeOne(court.getId_prikey());
                        }else if (null == court.getId_prikey()){
                            if ("".equals(court.getCourtDate())) {
                                court.setCourtDate(null);
                            }
                            court.setIndictmentId(String.valueOf(indictmentInfo.getId_prikey()));
                            indictmentCourtDaoService.addPrikey(court);

                                loanInfoFileService.handleLoanFile(court.getLoanFiles(), court.getId_prikey(), userId, LoanFileTypeEnum.LOAN_FILE_TYPE_INDICTMENT_COURT.getCode());

                        }
                    }
                }
            }
            if (YesOrNoEnum.YES_OR_NO_YES.getCode().equals(indictmentInfo.getExecutionFlag())) {
                //更新执行信息
                for (IndictmentExecution execution : indictmentExecutionList) {
                    if (null != execution) {
                        if (null != execution.getId_prikey() && !execution.getDelFlag().equals(Constant.DELFLAG)) {
                            if ("".equals(execution.getExecutionDate())) {
                                execution.setExecutionDate(null);
                            }
                            execution.setIndictmentId(String.valueOf(indictmentInfo.getId_prikey()));
                            indictmentExecutionDaoService.set(execution);

                                loanInfoFileService.handleLoanFile(execution.getLoanFiles(), execution.getId_prikey(), userId, LoanFileTypeEnum.LOAN_FILE_TYPE_INDICTMENT_EXECUTION.getCode());

                        }else if (null != execution.getId_prikey() && Constant.DELFLAG.equals(execution.getDelFlag())) {
                            indictmentExecutionDaoService.removeOne(execution.getId_prikey());
                        }else if (null == execution.getId_prikey()){
                            if ("".equals(execution.getExecutionDate())) {
                                execution.setExecutionDate(null);
                            }
                            execution.setIndictmentId(String.valueOf(indictmentInfo.getId_prikey()));
                            indictmentExecutionDaoService.addPrikey(execution);

                                loanInfoFileService.handleLoanFile(execution.getLoanFiles(), execution.getId_prikey(), userId, LoanFileTypeEnum.LOAN_FILE_TYPE_INDICTMENT_EXECUTION.getCode());

                        }
                    }
                }
            }
            result.setSuccessResult("保存成功");
        }else {
            result.setFailResult("未添加诉讼信息");
        }
        return result;
    }

    public Map<String, Object> selectIndictmentLoanInfoList(LoanInfoBean loanInfoBean) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer count = indictmentInfoDaoService.findPagingCountByLoanCon(loanInfoBean);
        List<LoanInfoBean> list = indictmentInfoDaoService.findPagingDataByLoanCon(loanInfoBean);
        for(LoanInfoBean loanInfoBean1 : list){
            loanInfoBean1.setIdNum(V1Pass.pass_decode(loanInfoBean1.getIdNum()));
            loanInfoBean1.setName(V1Pass.pass_decode(loanInfoBean1.getName()));
            loanInfoBean1.setMobile(V1Pass.pass_decode(loanInfoBean1.getMobile()));
            if(loanInfoBean1.getLoanBeginDate() != null){
                loanInfoBean1.setLoanBeginDateStr(DateUtil.formatDate(loanInfoBean1.getLoanBeginDate(),"yyyy-MM-dd"));
            }
            if(loanInfoBean1.getLoanEndDate() != null){
                loanInfoBean1.setLoanEndDateStr(DateUtil.formatDate(loanInfoBean1.getLoanEndDate(),"yyyy-MM-dd"));
            }
        }
        map.put("code", 0);
        map.put("msg", "获取成功");
        map.put("count", count);
        map.put("data", list);
        return map;
    }

    @Transactional
    public CallResult<String> deleteIndictmentInfo(IndictmentInfo indictmentInfo) throws Exception{
        CallResult result = new CallResult<>();
        if (null != indictmentInfo.getId_prikey()) {
            List<IndictmentInfo> indictmentInfos = indictmentInfoDaoService.getAll(indictmentInfo);
            if (null != indictmentInfos && indictmentInfos.size() > 0) {
                indictmentInfoDaoService.removeOne(indictmentInfo.getId_prikey());
            }

            //删除调解
            IndictmentMediation indictmentMediation = new IndictmentMediation();
            indictmentMediation.setIndictmentId(String.valueOf(indictmentInfo.getId_prikey()));
            List<IndictmentMediation> indictmentMediations = indictmentMediationDaoService.getAll(indictmentMediation);
            if (null != indictmentMediations && indictmentMediations.size() >0) {
                indictmentMediationDaoService.remove(indictmentMediation);
            }

            //删除开庭
            IndictmentCourt indictmentCourt = new IndictmentCourt();
            indictmentCourt.setIndictmentId(String.valueOf(indictmentInfo.getId_prikey()));
            List<IndictmentCourt> indictmentCourts = indictmentCourtDaoService.getAll(indictmentCourt);
            if (null != indictmentCourts && indictmentCourts.size() > 0) {
                indictmentCourtDaoService.remove(indictmentCourt);
            }

            //删除执行
            IndictmentExecution indictmentExecution = new IndictmentExecution();
            indictmentExecution.setIndictmentId(String.valueOf(indictmentInfo.getId_prikey()));
            List<IndictmentExecution> indictmentExecutions = indictmentExecutionDaoService.getAll(indictmentExecution);
            if (null != indictmentExecutions && indictmentExecutions.size() > 0) {
                indictmentExecutionDaoService.remove(indictmentExecution);
            }
            result.setSuccessResult("操作成功");
        }else {
            result.setFailResult("参数错误");
        }
        return result;
    }
}
