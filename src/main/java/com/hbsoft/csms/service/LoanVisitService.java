package com.hbsoft.csms.service;

import com.google.gson.reflect.TypeToken;
import com.hb.bean.CallResult;
import com.hb.util.GsonUtil;
import com.hbsoft.common.constant.Constant;
import com.hbsoft.common.enumtype.LoanFileFormatEnum;
import com.hbsoft.common.enumtype.LoanFileTypeEnum;
import com.hbsoft.common.enumtype.LoanWarnEnum;
import com.hbsoft.common.enumtype.YesOrNoEnum;
import com.hbsoft.csms.bean.LoanFileInfoBean;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.bean.LoanVisitBean;
import com.hbsoft.csms.bean.LoanWarnInfo;
import com.hbsoft.csms.dao.service.LoanFileInfoBeanDaoService;
import com.hbsoft.csms.dao.service.LoanInfoBeanDaoService;
import com.hbsoft.csms.dao.service.LoanVisitBeanDaoService;
import com.hbsoft.csms.dao.service.LoanWarnInfoDaoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ClassName:    LoanVisitService
 * Package:    com.hbsoft.csms.service
 * Description:
 * Datetime:    2020/4/7   11:16
 * Author:  hwl
 */
@Service
public class LoanVisitService {

    @Autowired
    LoanVisitBeanDaoService loanVisitBeanDaoService;
    @Autowired
    LoanFileInfoBeanDaoService loanFileInfoBeanDaoService;
    @Autowired
    LoanInfoService loanInfoService;
    @Autowired
    LoanInfoBeanDaoService loanInfoBeanDaoService;
    @Autowired
    LoanWarnInfoDaoService loanWarnInfoDaoService;

    @Transactional
    public CallResult<String> addLoanVisit(LoanVisitBean loanVisitBean, String userId) throws  Exception{
        CallResult<String> result = new CallResult<>();
        List<LoanFileInfoBean> loanFileList = GsonUtil.gson.fromJson(loanVisitBean.getLoanFiles(),new TypeToken<List<LoanFileInfoBean>>(){}.getType());
        if(loanVisitBean.getWarnFalg() != null && YesOrNoEnum.YES_OR_NO_YES.getCode().equals(String.valueOf(loanVisitBean.getWarnFalg()))){
            if(loanVisitBean.getWarnContent() == null || "".equals(loanVisitBean.getWarnContent())){
                result.setFailResult("请输入提醒内容");
                return  result;
            }
            if(loanVisitBean.getWarnDate() == null || "".equals(loanVisitBean.getWarnDate())){
                result.setFailResult("请选择提醒时间");
                return  result;
            }
        }
        if(loanVisitBean.getPeyMentFlag() != null && YesOrNoEnum.YES_OR_NO_YES.getCode().equals(String.valueOf(loanVisitBean.getPeyMentFlag()))){
            if(StringUtils.isBlank(loanVisitBean.getPeyMentPerson())){
                result.setFailResult("请选择催收对象");
                return  result;
            }
            //判断催收函
            boolean peyMentFlag = false;
            for(LoanFileInfoBean lf : loanFileList){
                if(LoanFileFormatEnum.LOAN_FILE_FORMAT_LETTER.getCode().equals(lf.getFileType())){
                    peyMentFlag = true;
                    continue;
                }
            }
            if(!peyMentFlag){
                result.setFailResult("请上传催收函照片");
                return  result;
            }
            LoanInfoBean loanInfoBean = loanInfoService.getLoanInfoByLoanContractNum(loanVisitBean.getLoanContractNum());
            LoanInfoBean record = new LoanInfoBean();
            record.setId_prikey(loanInfoBean.getId_prikey());
            record.setCollectionDate(loanVisitBean.getVisitDate());
            loanInfoBeanDaoService.set(record);
        }

        //loanVisitBean.setVisitDate(new Date());
        loanVisitBean.setCreateBy(userId);
        loanVisitBean.setCreateOn(new Date());
        loanVisitBean.setUpdateBy(userId);
        loanVisitBean.setUpdateOn(new Date());
        Integer loanVisitId = loanVisitBeanDaoService.addPrikey(loanVisitBean);

        if (YesOrNoEnum.YES_OR_NO_YES.getCode().equals(String.valueOf(loanVisitBean.getWarnFalg()))) {
            LoanWarnInfo loanWarnInfo = new LoanWarnInfo();
            loanWarnInfo.setWarnContent(loanVisitBean.getWarnContent());
            loanWarnInfo.setWarnDate(loanVisitBean.getWarnDate());
            loanWarnInfo.setWarnStatus(Integer.valueOf(LoanWarnEnum.LOAN_WARN_ENDDATE_ENUM.getCode()));
            loanWarnInfo.setWarnWay(Integer.valueOf(LoanWarnEnum.LOAN_WARN_VISIT_ENUM.getCode()));
            loanWarnInfo.setWarnId(loanVisitId);
            loanWarnInfo.setLoanContractNum(loanVisitBean.getLoanContractNum());
            loanWarnInfo.setWarnTargetType(Integer.valueOf(LoanWarnEnum.LOAN_WARN_ENDDATE_ENUM.getCode()));
            loanWarnInfo.setWarnTarget(userId);
            loanWarnInfo.setCreateBy(userId);
            loanWarnInfo.setCreateOn(new Date());
            loanWarnInfo.setUpdateOn(new Date());
            loanWarnInfo.setUpdateBy(userId);
            loanWarnInfo.setSendMsgFlag(null);
            loanWarnInfoDaoService.add(loanWarnInfo);
        }
        for(LoanFileInfoBean lf : loanFileList){
            lf.setType(LoanFileTypeEnum.LOAN_FILE_TYPE_VISIT.getCode());
            lf.setRelId(loanVisitId);
            lf.setCreateBy(userId);
            lf.setCreateOn(new Date());
            loanFileInfoBeanDaoService.add(lf);
        }
        result.setSuccessResult();
        return  result;
    }

    @Transactional
    public CallResult<String> updateLoanVisit(LoanVisitBean loanVisitBean, String userId) throws  Exception{
        CallResult<String> result = new CallResult<>();
        loanVisitBean.setVisitDate(new Date());
        loanVisitBean.setUpdateBy(userId);
        loanVisitBean.setUpdateOn(new Date());
        List<LoanFileInfoBean> loanFileList = GsonUtil.gson.fromJson(loanVisitBean.getLoanFiles(),new TypeToken<List<LoanFileInfoBean>>(){}.getType());
        if(loanVisitBean.getWarnFalg() != null && YesOrNoEnum.YES_OR_NO_YES.getCode().equals(String.valueOf(loanVisitBean.getWarnFalg()))){
            if(loanVisitBean.getWarnContent() == null || "".equals(loanVisitBean.getWarnContent())){
                result.setFailResult("请输入提醒内容");
                return  result;
            }
            if(loanVisitBean.getWarnDate() == null || "".equals(loanVisitBean.getWarnDate())){
                result.setFailResult("请选择提醒时间");
                return  result;
            }
        }
        if(loanVisitBean.getId_prikey() != null){
            if(loanVisitBean.getPeyMentFlag() != null && YesOrNoEnum.YES_OR_NO_YES.getCode().equals(String.valueOf(loanVisitBean.getPeyMentFlag()))){
                if(StringUtils.isBlank(loanVisitBean.getPeyMentPerson())){
                    result.setFailResult("请选择催收对象");
                    return  result;
                }
                //判断催收函
                boolean peyMentFlag = false;
                for(LoanFileInfoBean lf : loanFileList){
                    if(LoanFileFormatEnum.LOAN_FILE_FORMAT_LETTER.getCode().equals(lf.getFileType())){
                        peyMentFlag = true;
                        continue;
                    }
                }
                if(!peyMentFlag){
                    result.setFailResult("请上传催收函照片");
                    return  result;
                }

                LoanInfoBean loanInfoBean = loanInfoService.getLoanInfoByLoanContractNum(loanVisitBean.getLoanContractNum());
                LoanInfoBean record = new LoanInfoBean();
                record.setId_prikey(loanInfoBean.getId_prikey());
                record.setCollectionDate(loanVisitBean.getVisitDate());
                loanInfoBeanDaoService.set(record);
            }

            loanVisitBeanDaoService.set(loanVisitBean);

            LoanWarnInfo param = new LoanWarnInfo();
            param.setWarnId(loanVisitBean.getId_prikey());
            LoanWarnInfo loanWarnInfo = loanWarnInfoDaoService.getByField(param);
            //更新提醒表
            if (YesOrNoEnum.YES_OR_NO_NO.getCode().equals(String.valueOf(loanVisitBean.getWarnFalg()))) {
                if (null != loanWarnInfo) {
                    loanWarnInfoDaoService.removeOne(loanWarnInfo.getId_prikey());
                }
            }else if (YesOrNoEnum.YES_OR_NO_YES.getCode().equals(String.valueOf(loanVisitBean.getWarnFalg()))) {
                if (null != loanWarnInfo) {
                    Boolean wcBoolean = !loanWarnInfo.getWarnContent().equals(loanVisitBean.getWarnContent());
                    Boolean wdBoolean = !loanWarnInfo.getWarnDate().equals(loanVisitBean.getWarnDate());
                    if (wcBoolean || wdBoolean) {
                        loanWarnInfo.setWarnContent(loanVisitBean.getWarnContent());
                        loanWarnInfo.setWarnDate(loanVisitBean.getWarnDate());
                        loanWarnInfo.setWarnStatus(Integer.valueOf(LoanWarnEnum.LOAN_WARN_ENDDATE_ENUM.getCode()));
                        loanWarnInfo.setWarnWay(Integer.valueOf(LoanWarnEnum.LOAN_WARN_VISIT_ENUM.getCode()));
                        loanWarnInfo.setWarnId(loanVisitBean.getId_prikey());
                        loanWarnInfo.setLoanContractNum(loanVisitBean.getLoanContractNum());
                        loanWarnInfo.setWarnTargetType(Integer.valueOf(LoanWarnEnum.LOAN_WARN_ENDDATE_ENUM.getCode()));
                        loanWarnInfo.setWarnTarget(userId);
                        loanWarnInfo.setUpdateOn(new Date());
                        loanWarnInfo.setUpdateBy(userId);
                        loanWarnInfo.setSendMsgFlag(null);
                        loanWarnInfoDaoService.set(loanWarnInfo);
                    }
                }else {
                    LoanWarnInfo loanWarnInfo1 = new LoanWarnInfo();
                    loanWarnInfo1.setWarnContent(loanVisitBean.getWarnContent());
                    loanWarnInfo1.setWarnDate(loanVisitBean.getWarnDate());
                    loanWarnInfo1.setWarnStatus(Integer.valueOf(LoanWarnEnum.LOAN_WARN_ENDDATE_ENUM.getCode()));
                    loanWarnInfo1.setWarnWay(Integer.valueOf(LoanWarnEnum.LOAN_WARN_VISIT_ENUM.getCode()));
                    loanWarnInfo1.setWarnId(loanVisitBean.getId_prikey());
                    loanWarnInfo1.setLoanContractNum(loanVisitBean.getLoanContractNum());
                    loanWarnInfo1.setWarnTargetType(Integer.valueOf(LoanWarnEnum.LOAN_WARN_ENDDATE_ENUM.getCode()));
                    loanWarnInfo1.setWarnTarget(userId);
                    loanWarnInfo1.setCreateBy(userId);
                    loanWarnInfo1.setCreateOn(new Date());
                    loanWarnInfo1.setUpdateOn(new Date());
                    loanWarnInfo1.setUpdateBy(userId);
                    loanWarnInfo1.setSendMsgFlag(null);
                    loanWarnInfoDaoService.add(loanWarnInfo1);
                }

            }
        }

        for(LoanFileInfoBean lf : loanFileList){
            if(lf.getId_prikey() != null){
                if(lf.getDelFlag() == Constant.DELFLAG){
                    loanFileInfoBeanDaoService.removeOne(lf.getId_prikey());
                }else{
                    loanFileInfoBeanDaoService.set(lf);
                }
            }else{
                lf.setType(LoanFileTypeEnum.LOAN_FILE_TYPE_VISIT.getCode());
                lf.setRelId(loanVisitBean.getId_prikey());
                lf.setCreateBy(userId);
                lf.setCreateOn(new Date());
                loanFileInfoBeanDaoService.add(lf);
            }
        }
        result.setSuccessResult();
        return  result;
    }

    @Transactional
    public CallResult<String>  deleteLoanVisitByIdPrikey(Integer id_prikey) throws  Exception{
        CallResult<String> result = new CallResult<>();
        if(id_prikey == null){
            result.setFailResult("参数有误");
            return  result;
        }
        loanVisitBeanDaoService.removeOne(id_prikey);
        LoanFileInfoBean param = new LoanFileInfoBean();
        param.setType(LoanFileTypeEnum.LOAN_FILE_TYPE_VISIT.getCode());
        param.setRelId(id_prikey);
        loanFileInfoBeanDaoService.remove(param);
        result.setSuccessResult();
        return result;
    }
}
