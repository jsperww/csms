package com.hbsoft.csms.service;

import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hb.util.DateUtil;
import com.hbsoft.common.enumtype.*;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.bean.LoanRepayBean;
import com.hbsoft.csms.dao.service.LoanInfoBeanDaoService;
import com.hbsoft.csms.dao.service.LoanRepayBeanDaoService;
import com.hbsoft.csms.vo.ProcessVo;
import com.hbsoft.csms.bean.*;
import com.hbsoft.csms.dao.service.*;
import com.hbsoft.csms.vo.RepayAddVo;
import com.hbsoft.csms.vo.RepayLoanSecondVo;
import com.hbsoft.csms.vo.RepayVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName:    LoanRepayService
 * Package:    com.hbsoft.csms.service
 * Description:
 * Datetime:    2020/3/28   11:16
 * Author:  hwl
 */
@Service
public class LoanRepayService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LoanRepayBeanDaoService loanRepayBeanDaoService;

    @Autowired
    LoanRepayService loanRepayService;

    @Autowired
    LoanInfoService loanInfoService;

    @Autowired
    LoanInfoBeanDaoService loanInfoBeanDaoService;

    @Autowired
    LoanCountInterestLogDaoService loanCountInterestLogDaoService;

    @Autowired
    LoanCountInterestLogTempDaoService loanCountInterestLogTempDaoService;

    @Autowired
    LoanDistributeBeanDaoService loanDistributeBeanDaoService;

    @Autowired
    ProcessHandleService processHandleService;

    @Autowired
    ProcessBusinessBeanDaoService processBusinessBeanDaoService;


    public LoanRepayBean getLoanRepayByLoanContractNumAndCheckStatus(String loanContractNum,Integer checkStatus) throws  Exception{
        if(StringUtils.isEmpty(loanContractNum) || ObjectUtils.isEmpty(checkStatus)){
            return  null;
        }
        LoanRepayBean param = new LoanRepayBean();
        param.setLoanContractNum(loanContractNum);
        param.setStatus(checkStatus);
        return loanRepayBeanDaoService.getByField(param);
    }

    @Transactional
    public CallResult<LoanRepayBean> rePayLoanFirst(RepayVo vo, HB_User hb_user) throws  Exception{
        CallResult<LoanRepayBean> countResult =  new CallResult<>();
            synchronized (this) {
                LoanInfoBean loanInfoBean = loanInfoService.getLoanInfoByLoanContractNum(vo.getLoanContractNum());
                if (loanInfoBean != null) {
                    LoanRepayBean loanRepayBean = loanRepayService.getLoanRepayByLoanContractNumAndCheckStatus(vo.getLoanContractNum(), CheckRepayStatusEnum.CHECK_REPAY_STATUS_FIRST.getCode());
                    if (ObjectUtils.isEmpty(loanRepayBean)) {
                        countResult = loanRepayService.reCountRepay(vo);
                        if (countResult.isExec()) {

                            LoanDistributeBean param = new LoanDistributeBean();
                            param.setLoanContractNum(vo.getLoanContractNum());
                            LoanDistributeBean loanDistributeBean = loanDistributeBeanDaoService.getByField(param);

                            LoanRepayBean countLoan = countResult.getData();
                            LoanRepayBean record = new LoanRepayBean();
                            BeanUtils.copyProperties(countLoan, record);
                            record.setOrg(vo.getOrg());
                            record.setSourceLeftCapitalAmount(loanInfoBean.getLeftCapitalAmount());
                            record.setSourceLeftInterestAmount(loanInfoBean.getLeftInterestAmount());
                            record.setSourceBiaoWaiLeftInterestAmount(loanInfoBean.getBiaoWaiLeftInterestAmount());
                            record.setSourceHeXiaoLeftInterestAmount(loanInfoBean.getHeXiaoLeftInterestAmount());
                            record.setLoanType(loanInfoBean.getLoanType());
                            record.setLoanNewMonthRate(loanInfoBean.getLoanNewMonthRate());
                            record.setCheckFirst(hb_user.getName());
                            record.setCheckFirstDate(new Date());
                            record.setRepayWay(vo.getRepayWay());
                            record.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_FIRST.getCode());
                            record.setRepayType(Integer.valueOf(RepayTypeEnum.REPAY_TYPE_NORMAL.getCode()));
                            if (loanDistributeBean != null) {
                                record.setCustomerManage(loanDistributeBean.getNumber());
                                record.setCustomerManageType(loanDistributeBean.getNumberType());
                            }
                            loanRepayBeanDaoService.add(record);
                            countResult.getData().setCheckFirstName(hb_user.getShowName());
                        }
                    } else {
                        countResult.setFailResult("已有未复核的还款记录，请复核后再操作");
                    }
                } else {
                    countResult.setFailResult("合同编号有误");
                }
            }
        return  countResult;
    }

    @Transactional
    public CallResult<String> rePayLoanSecond(RepayLoanSecondVo vo) throws Exception {
        CallResult<String> result = new CallResult<>();
        synchronized (this) {
            LoanRepayBean loanRepayBean = loanRepayService.getLoanRepayByLoanContractNumAndCheckStatus(vo.getLoanContractNum(), CheckRepayStatusEnum.CHECK_REPAY_STATUS_FIRST.getCode());
            LoanInfoBean loanInfoBean = loanInfoService.getLoanInfoByLoanContractNum(vo.getLoanContractNum());
            if (ObjectUtils.isNotEmpty(loanRepayBean) && ObjectUtils.isNotEmpty(loanInfoBean)) {
                if (loanRepayBean.getCheckFirst().equals(vo.getUserId())) {
                    result.setFailResult("初审人员和复审人员不能为同一个人");
                    return result;
                }
                if (CheckRepayResultEnum.ENABLED.getCode().equals(vo.getType())) {
                    if (loanRepayBean.getCheckFirstDate() != null) {
                        if (!DateUtil.formatDate(new Date(), "yyyy-MM-dd").equals(DateUtil.formatDate(loanRepayBean.getCheckFirstDate(), "yyyy-MM-dd"))) {
                            result.setFailResult("初审已过期");
                            return result;
                        }
                    } else {
                        result.setFailResult("未初审");
                        return result;
                    }

                    LoanInfoBean record = new LoanInfoBean();
                    record.setId_prikey(loanInfoBean.getId_prikey());
                    record.setLeftCapitalAmount(loanRepayBean.getLeftCapitalAmount());
                    record.setLeftInterestAmount(loanRepayBean.getLeftInterestAmount());
                    if (LoanTypeEnum.LOAN_TYPE_HX.getCode().equals(String.valueOf(loanRepayBean.getLoanType()))) {
                        if (ObjectUtils.isNotEmpty(loanRepayBean.getHeXiaoLeftInterestAmount())) {
                            record.setHeXiaoLeftInterestAmount(loanRepayBean.getHeXiaoLeftInterestAmount());
                        }
                        if (ObjectUtils.isNotEmpty(loanRepayBean.getBiaoWaiLeftInterestAmount())) {
                            record.setBiaoWaiLeftInterestAmount(loanRepayBean.getBiaoWaiLeftInterestAmount());
                        }
                    }
                    if (LoanTypeEnum.LOAN_TYPE_ZH.getCode().equals(String.valueOf(loanRepayBean.getLoanType()))) {
                        if( new BigDecimal(0).compareTo(loanRepayBean.getLeftInterestAmount()) > 0){
                            record.setLeftInterestAmount(new BigDecimal(0));
                        }
                    }
                    loanInfoBeanDaoService.set(record);

                    LoanRepayBean repayRecord = new LoanRepayBean();
                    repayRecord.setId_prikey(loanRepayBean.getId_prikey());
                    repayRecord.setCheckSecond(String.valueOf(vo.getUserId()));
                    repayRecord.setCheckSecondDate(new Date());
                    repayRecord.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_SENCOND.getCode());
                    loanRepayBeanDaoService.set(repayRecord);
                } else if (CheckRepayResultEnum.DISABLED.getCode().equals(vo.getType())) {
                    loanRepayBeanDaoService.removeOne(loanRepayBean.getId_prikey());
                }
                result.setCode(0);
                if (LoanTypeEnum.LOAN_TYPE_DB.getCode().equals(String.valueOf(loanRepayBean.getLoanType()))) {
                    result.setData(String.valueOf(loanRepayBean.getId_prikey()));
                }
            } else {
                result.setFailResult("合同编号有误");
            }
        }
        return result;
    }

    /**
    * @author hwl
    * @description 还款计算
    * 结欠利息+结欠本金*贷款月利率/30*天数/1000-还款利息
    *结欠本金-还款本金
    * @date 2020/4/17 
    */
    public  CallResult<LoanRepayBean> reCountRepay(RepayVo vo) throws  Exception{
        CallResult<LoanRepayBean> result = new CallResult<>();

        LoanInfoBean loanInfoBean = loanInfoService.getLoanInfoByLoanContractNum(vo.getLoanContractNum());
        if(loanInfoBean != null){
            BigDecimal leftCapitalAmount = loanInfoBean.getLeftCapitalAmount().subtract(vo.getRepayCapitalAmount());
            BigDecimal leftInterestAmount = loanInfoBean.getLeftInterestAmount().subtract(vo.getRepayInterestAmount());
            if(!LoanTypeEnum.LOAN_TYPE_ZH.getCode().equals(String.valueOf(loanInfoBean.getLoanType()))) {
                if (leftCapitalAmount.compareTo(new BigDecimal(0)) < 0 || leftInterestAmount.compareTo(new BigDecimal(0)) < 0) {
                    result.setFailResult("还款本金或利息多于结欠本金或利息");
                    return result;
                }
            }
            LoanRepayBean loanRepayBean = new LoanRepayBean();
            loanRepayBean.setLeftCapitalAmount(leftCapitalAmount.setScale(2,BigDecimal.ROUND_UP));
            loanRepayBean.setLeftInterestAmount(leftInterestAmount.setScale(2,BigDecimal.ROUND_UP));
            loanRepayBean.setLoanContractNum(vo.getLoanContractNum());
            loanRepayBean.setRepayAmount(vo.getRepayCapitalAmount().add(vo.getRepayInterestAmount()));
            loanRepayBean.setRepayCapitalAmount(vo.getRepayCapitalAmount());
            loanRepayBean.setRepayInterestAmount(vo.getRepayInterestAmount());
            if(LoanTypeEnum.LOAN_TYPE_DB.getCode().equals(String.valueOf(loanInfoBean.getLoanType()))) {

            }else if(LoanTypeEnum.LOAN_TYPE_HX.getCode().equals(String.valueOf(loanInfoBean.getLoanType())))  {
                if(ObjectUtils.isEmpty(vo.getRepayBiaoWaiLeftInterestAmount()) && ObjectUtils.isEmpty(vo.getRepayHeXiaoLeftInterestAmount())){
                    result.setFailResult("请输入核销时利息或表外欠息");
                    return result;
                }
                //先还核销时欠息，再还表外欠息
                BigDecimal heXiaoLeftInterestAmount = new BigDecimal(0);
                BigDecimal biaoWaiLeftInterestAmount = new BigDecimal(0);
                if(ObjectUtils.isNotEmpty(loanInfoBean.getHeXiaoLeftInterestAmount())) {
                    if (loanInfoBean.getHeXiaoLeftInterestAmount().compareTo(new BigDecimal(0)) > 0) {
                        if (ObjectUtils.isEmpty(vo.getRepayHeXiaoLeftInterestAmount())) {
                            result.setFailResult("核销时欠息未还，请先还核销时欠息");
                            return result;
                        }
                        heXiaoLeftInterestAmount = loanInfoBean.getHeXiaoLeftInterestAmount().subtract(vo.getRepayHeXiaoLeftInterestAmount());
                        biaoWaiLeftInterestAmount = loanInfoBean.getBiaoWaiLeftInterestAmount();

                    } else if (loanInfoBean.getHeXiaoLeftInterestAmount().compareTo(new BigDecimal(0)) == 0) {
                        if (ObjectUtils.isEmpty(vo.getRepayBiaoWaiLeftInterestAmount())) {
                            result.setFailResult("核销时欠息已还清，请还表外欠息");
                            return result;
                        }
                        heXiaoLeftInterestAmount = loanInfoBean.getHeXiaoLeftInterestAmount();
                        biaoWaiLeftInterestAmount = loanInfoBean.getBiaoWaiLeftInterestAmount().subtract(vo.getRepayBiaoWaiLeftInterestAmount());
                    }
                }
                if(ObjectUtils.isNotEmpty(heXiaoLeftInterestAmount)){
                    heXiaoLeftInterestAmount = heXiaoLeftInterestAmount.setScale(2,BigDecimal.ROUND_UP);
                }
                if(ObjectUtils.isNotEmpty(biaoWaiLeftInterestAmount)){
                    biaoWaiLeftInterestAmount = biaoWaiLeftInterestAmount.setScale(2,BigDecimal.ROUND_UP);
                }
                loanRepayBean.setHeXiaoLeftInterestAmount(heXiaoLeftInterestAmount);
                loanRepayBean.setBiaoWaiLeftInterestAmount(biaoWaiLeftInterestAmount);
                loanRepayBean.setRepayBiaoWaiLeftInterestAmount(vo.getRepayBiaoWaiLeftInterestAmount());
                loanRepayBean.setRepayHeXiaoLeftInterestAmount(vo.getRepayHeXiaoLeftInterestAmount());
                loanRepayBean.setRepayAmount(vo.getRepayCapitalAmount().add(vo.getRepayInterestAmount()));

            }
            result.setSuccessResult(loanRepayBean);
        }else{
            result.setFailResult("未查询到贷款记录");
        }
        return result;
    }

    @Transactional
    public CallResult<LoanRepayBean> reCountAddRepay(RepayVo vo, boolean flag) throws Exception {
        CallResult<LoanRepayBean> result = new CallResult<>();
        if (ObjectUtils.isEmpty(vo.getAddRepayDate())) {
            result.setFailResult("补录时间有误");
            return result;
        }
        LoanRepayBean lrParam = new LoanRepayBean();
        lrParam.setLoanContractNum(vo.getLoanContractNum());
        LoanRepayBean loanRepayBeanDateVolatile = loanRepayBeanDaoService.findLatestRepayByLoanContractNumAndRepayType(lrParam);
        if(!ObjectUtils.isEmpty(loanRepayBeanDateVolatile)){
            if(!String.valueOf(loanRepayBeanDateVolatile.getId_prikey()).equals(vo.getBusinessId())){
                if(loanRepayBeanDateVolatile.getCheckFirstDate().getTime()-vo.getAddRepayDate().getTime() > 0){
                    result.setFailResult("补录时间内，已有还款记录，不能补录");
                    return  result;
                }
            }
        }
        LoanCountInterestLog param = new LoanCountInterestLog();
        param.setLoanContractNum(vo.getLoanContractNum());
        param.setCountDate(DateUtil.formatDate(vo.getAddRepayDate(), "yyyy-MM-dd"));
        LoanCountInterestLog log = loanCountInterestLogDaoService.findByLoanContractNumAndCountDate(param);
        if (ObjectUtils.isNotEmpty(log)) {
            //结欠本金
            BigDecimal leftCapitalAmount = log.getLeftCapitalAmount().subtract(vo.getRepayCapitalAmount());
            int days = DateUtil.daysBetween(new Date(), vo.getAddRepayDate()) + 1;
            //结欠利息
            BigDecimal leftInterestAmoun1t = null;
            //表外利息
            BigDecimal biaoWaiLeftInterestAmount = null;

            BigDecimal currentHeXiaoLeftInterestAmount = null;
            BigDecimal currentBiaoWaiLeftInterestAmount = new BigDecimal(0);
            if (LoanTypeEnum.LOAN_TYPE_DB.getCode().equals(log.getLoanType())) {

            } else if (LoanTypeEnum.LOAN_TYPE_HX.getCode().equals(String.valueOf(log.getLoanType()))) {
                if (ObjectUtils.isEmpty(vo.getRepayBiaoWaiLeftInterestAmount()) && ObjectUtils.isEmpty(vo.getRepayHeXiaoLeftInterestAmount())) {
                    result.setFailResult("请输入核销时利息或表外欠息");
                    return result;
                }
                if (ObjectUtils.isNotEmpty(log.getHeXiaoLeftInterestAmount())) {
                    if (log.getHeXiaoLeftInterestAmount().compareTo(new BigDecimal(0)) > 0) {
                        if (ObjectUtils.isEmpty(vo.getRepayHeXiaoLeftInterestAmount())) {
                            result.setFailResult("核销时欠息未还，请先还核销时欠息");
                            return result;
                        }
                        currentHeXiaoLeftInterestAmount = log.getHeXiaoLeftInterestAmount().subtract(vo.getRepayHeXiaoLeftInterestAmount());
                        currentBiaoWaiLeftInterestAmount = log.getBiaoWaiLeftInterestAmount();

                    } else if (log.getHeXiaoLeftInterestAmount().compareTo(new BigDecimal(0)) == 0) {
                        if (ObjectUtils.isEmpty(vo.getRepayBiaoWaiLeftInterestAmount())) {
                            result.setFailResult("核销时欠息已还清，请还表外欠息");
                            return result;
                        }
                        currentHeXiaoLeftInterestAmount = log.getHeXiaoLeftInterestAmount();
                        currentBiaoWaiLeftInterestAmount = log.getBiaoWaiLeftInterestAmount().subtract(vo.getRepayBiaoWaiLeftInterestAmount());
                    }
                }
            }
            LoanCountInterestLogTemp removeParam = new LoanCountInterestLogTemp();
            removeParam.setLoanContractNum(vo.getLoanContractNum());
            loanCountInterestLogTempDaoService.remove(removeParam);
            //还款后结欠利息
            BigDecimal currentLeftInterestAmount = log.getLeftInterestAmount().subtract(vo.getRepayInterestAmount());
            //原结欠利息
            BigDecimal beforeLeftInterestAmount = log.getLeftInterestAmount();
            //原表外欠息
            BigDecimal beforeBiaoWaiLeftInterestAmount = log.getBiaoWaiLeftInterestAmount();
            for (int i = 1; i < days; i++) {
                Date countDate = dateAddDays(vo.getAddRepayDate(), i);
                BigDecimal countinterestDay = leftCapitalAmount
                        .multiply(log.getLoanNewMonthRate())
                        .divide(new BigDecimal("30"), 6)
                        .divide(new BigDecimal("1000"), 6);
                currentLeftInterestAmount = currentLeftInterestAmount.add(countinterestDay);
                LoanCountInterestLogTemp temp = new LoanCountInterestLogTemp();
                BeanUtils.copyProperties(log, temp);
                temp.setId_prikey(null);
                temp.setLeftCapitalAmount(leftCapitalAmount);
                temp.setLeftInterestAmount(currentLeftInterestAmount);
                temp.setMakeInterestAmount(countinterestDay);
                if (LoanTypeEnum.LOAN_TYPE_HX.getCode().equals(String.valueOf(log.getLoanType()))) {
                    currentBiaoWaiLeftInterestAmount = currentBiaoWaiLeftInterestAmount.add(countinterestDay);
                    temp.setBiaoWaiLeftInterestAmount(currentBiaoWaiLeftInterestAmount);
                    temp.setBeforeBiaoWaiLeftInterestAmount(beforeBiaoWaiLeftInterestAmount);
                    temp.setHeXiaoLeftInterestAmount(currentHeXiaoLeftInterestAmount);
                    temp.setBeforeHeXiaoLeftInterestAmount(log.getHeXiaoLeftInterestAmount());
                    beforeBiaoWaiLeftInterestAmount = temp.getBiaoWaiLeftInterestAmount();
                    biaoWaiLeftInterestAmount = temp.getBiaoWaiLeftInterestAmount();
                }
                temp.setBeforeLeftInterestAmount(beforeLeftInterestAmount);
                beforeLeftInterestAmount = currentLeftInterestAmount;
                temp.setCountDate(countDate);
                temp.setOperateDate(new Date());
                if (flag) {
                    loanCountInterestLogTempDaoService.add(temp);
                }
                leftInterestAmoun1t = temp.getLeftInterestAmount();

            }
            LoanRepayBean loanRepayBean = new LoanRepayBean();
            loanRepayBean.setLoanType(log.getLoanType());
            loanRepayBean.setRepayAmount(vo.getRepayCapitalAmount().add(vo.getRepayInterestAmount()).setScale(2,BigDecimal.ROUND_UP));
            loanRepayBean.setLeftCapitalAmount(leftCapitalAmount.setScale(2,BigDecimal.ROUND_UP));
            loanRepayBean.setLeftInterestAmount(leftInterestAmoun1t.setScale(2,BigDecimal.ROUND_UP));
            loanRepayBean.setRepayCapitalAmount(vo.getRepayCapitalAmount());
            loanRepayBean.setRepayInterestAmount(vo.getRepayInterestAmount());
            loanRepayBean.setLoanContractNum(vo.getLoanContractNum());
            loanRepayBean.setSourceLeftInterestAmount(log.getLeftInterestAmount());
            loanRepayBean.setSourceLeftCapitalAmount(log.getLeftCapitalAmount());
            loanRepayBean.setSourceBiaoWaiLeftInterestAmount(log.getBiaoWaiLeftInterestAmount());
            loanRepayBean.setSourceHeXiaoLeftInterestAmount(log.getHeXiaoLeftInterestAmount());
            loanRepayBean.setLoanNewMonthRate(log.getLoanNewMonthRate());
            if (LoanTypeEnum.LOAN_TYPE_HX.getCode().equals(String.valueOf(log.getLoanType()))) {
                if (ObjectUtils.isNotEmpty(biaoWaiLeftInterestAmount)) {
                    biaoWaiLeftInterestAmount = biaoWaiLeftInterestAmount.setScale(2, BigDecimal.ROUND_UP);
                    loanRepayBean.setRepayBiaoWaiLeftInterestAmount(vo.getRepayBiaoWaiLeftInterestAmount());
                }
                loanRepayBean.setBiaoWaiLeftInterestAmount(biaoWaiLeftInterestAmount);
                if (ObjectUtils.isNotEmpty(currentHeXiaoLeftInterestAmount)) {
                    currentHeXiaoLeftInterestAmount = currentHeXiaoLeftInterestAmount.setScale(2, BigDecimal.ROUND_UP);
                    loanRepayBean.setRepayHeXiaoLeftInterestAmount(vo.getRepayHeXiaoLeftInterestAmount());
                }
                loanRepayBean.setHeXiaoLeftInterestAmount(currentHeXiaoLeftInterestAmount);
                loanRepayBean.setBiaoWaiLeftInterestAmount(biaoWaiLeftInterestAmount);
            }

            result.setSuccessResult(loanRepayBean);
        } else {
            result.setFailResult("数据有误");
        }
        return result;
    }

    @Transactional
    public CallResult<ProcessHandleBean> revokeRepay(LoanRepayBean loanRepayBean) throws Exception{
        CallResult<ProcessHandleBean> result = new CallResult<>();
        synchronized (this){
        LoanRepayBean loanRepayBean1 = loanRepayBeanDaoService.findLatestRepayRecord(loanRepayBean.getLoanContractNum());
        if (null == loanRepayBean1) {
            result.setFailResult("只能抹除当天还款记录");
            return result;
        }

        if((loanRepayBean.getId_prikey()-loanRepayBean1.getId_prikey()) != 0) {
            result.setFailResult("只能抹除当天最新还款记录");
            return result;
        }
        //流程参数
        ProcessVo processVo = new ProcessVo();
        processVo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_REVOKE.getCode());
        processVo.setNodeSatus(ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode());
        processVo.setRemark(loanRepayBean.getRemark());
        if (null != loanRepayBean.getNodeId() && loanRepayBean.getNodeId() != 0) {
            processVo.setNodeId(loanRepayBean.getNodeId());
        }else {
            processVo.setNodeId(0);
        }
        processVo.setBusinessId(String.valueOf(loanRepayBean.getId_prikey()));
        processVo.setBusinessCode(loanRepayBean.getLoanContractNum());
        processVo.setUserId(loanRepayBean.getUserId());
        processVo.setFilePath(loanRepayBean.getFiles());
        result = processHandleService.processHandle(processVo);
        if (!result.isExec()) {
            throw new RuntimeException(result.getMsg());
        }
        //更新repay审核状态
        LoanRepayBean param = new LoanRepayBean();
        param.setId_prikey(loanRepayBean.getId_prikey());
        param.setRepayType(Integer.valueOf(RepayTypeEnum.REPAY_TYPE_REVOKE.getCode()));
        param.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_REVOKE_FIRST.getCode());
        loanRepayBeanDaoService.set(param);
        }
        return result;
    }

    @Transactional
    public CallResult<ProcessHandleBean> examRevokeRepay(LoanRepayBean loanRepayBean) throws Exception {
        CallResult<ProcessHandleBean> result = new CallResult<>();

        //流程参数准备
        ProcessVo processVo = new ProcessVo();
        processVo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_REVOKE.getCode());
        processVo.setNodeId(loanRepayBean.getNodeId());
        processVo.setBusinessId(String.valueOf(loanRepayBean.getId_prikey()));
        processVo.setBusinessCode(loanRepayBean.getLoanContractNum());
        processVo.setUserId(loanRepayBean.getUserId());
        processVo.setNodeSatus(String.valueOf(loanRepayBean.getOperateType()));
        processVo.setRemark(loanRepayBean.getRemark());
        processVo.setFilePath(loanRepayBean.getFiles());
        result = processHandleService.processHandle(processVo);
        if (!result.isExec()) {
            throw new RuntimeException();
        }
        if (null != loanRepayBean.getOperateType()) {
            LoanRepayBean loanRepayBean1 = loanRepayBeanDaoService.getById(loanRepayBean.getId_prikey());
            if (ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode().equals(String.valueOf(loanRepayBean.getOperateType()))) {
                if (LoanTypeEnum.LOAN_TYPE_DB.getCode().equals(String.valueOf(loanRepayBean1.getLoanType()))) {
                    //更新loanRepay
                    loanRepayBean.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_REVOKE_SENCOND.getCode());
                    loanRepayBeanDaoService.set(loanRepayBean);
                    //更新loanInfo
                    if (null != loanRepayBean1) {
                        LoanInfoBean loanInfoBean = new LoanInfoBean();
                        loanInfoBean.setLoanContractNum(loanRepayBean1.getLoanContractNum());
                        loanInfoBean.setLeftCapitalAmount(loanRepayBean1.getSourceLeftCapitalAmount());
                        loanInfoBean.setLeftInterestAmount(loanRepayBean1.getSourceLeftInterestAmount());
                        loanInfoBean.setUpdateOn(new Date());
                        loanInfoBeanDaoService.updateByConNum(loanInfoBean);
                    }
                }else if (LoanTypeEnum.LOAN_TYPE_HX.getCode().equals(String.valueOf(loanRepayBean1.getLoanType()))){
                    //更新loanRepay
                    loanRepayBean.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_REVOKE_SENCOND.getCode());
                    loanRepayBeanDaoService.set(loanRepayBean);
                    //更新loanInfo
                    if (null != loanRepayBean1) {
                        LoanInfoBean loanInfoBean = new LoanInfoBean();
                        loanInfoBean.setLoanContractNum(loanRepayBean1.getLoanContractNum());
                        loanInfoBean.setLeftCapitalAmount(loanRepayBean1.getSourceLeftCapitalAmount());
                        loanInfoBean.setLeftInterestAmount(loanRepayBean1.getSourceLeftInterestAmount());
                        loanInfoBean.setHeXiaoLeftInterestAmount(loanRepayBean1.getSourceHeXiaoLeftInterestAmount());
                        loanInfoBean.setBiaoWaiLeftInterestAmount(loanRepayBean1.getSourceBiaoWaiLeftInterestAmount());
                        loanInfoBean.setUpdateOn(new Date());
                        loanInfoBeanDaoService.updateByConNum(loanInfoBean);
                    }
                }else if (LoanTypeEnum.LOAN_TYPE_ZH.getCode().equals(String.valueOf(loanRepayBean1.getLoanType()))){
                    //更新loanRepay
                    loanRepayBean.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_REVOKE_SENCOND.getCode());
                    loanRepayBeanDaoService.set(loanRepayBean);
                    //更新loanInfo
                    if (null != loanRepayBean1) {
                        LoanInfoBean loanInfoBean = new LoanInfoBean();
                        loanInfoBean.setLoanContractNum(loanRepayBean1.getLoanContractNum());
                        loanInfoBean.setLeftCapitalAmount(loanRepayBean1.getSourceLeftCapitalAmount());
                        loanInfoBean.setLeftInterestAmount(loanRepayBean1.getSourceLeftInterestAmount());
                        loanInfoBean.setUpdateOn(new Date());
                        loanInfoBeanDaoService.updateByConNum(loanInfoBean);
                    }
                } else {
                    result.setFailResult("贷款类型参数错误");
                    throw new RuntimeException();
                }

            }else {
                loanRepayBean.setRepayType(Integer.valueOf(RepayTypeEnum.REPAY_TYPE_NORMAL.getCode()));
                loanRepayBean.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_SENCOND.getCode());
                loanRepayBeanDaoService.set(loanRepayBean);
            }
        }else {

            result.setFailResult("参数错误");
            throw new RuntimeException();
        }
        return result;
    }

    @Transactional
    public CallResult<LoanRepayBean> revokeRepayInfo(String loanContractNum) throws Exception{
        CallResult<LoanRepayBean> result = new CallResult<>();

        if (StringUtils.isNotEmpty(loanContractNum)) {
            LoanRepayBean loanRepayBean = getLoanRepayByLoanContractNum(loanContractNum);
            if (null != loanRepayBean) {
                result.setSuccessResult(loanRepayBean);    
            }else {
                result.setFailResult();
            }
            
        }else{
            result.setFailResult("主键参数错误");
        }
        return result;

    }

    public LoanRepayBean getLoanRepayByLoanContractNum(String loanContractNum) throws Exception {
        if (StringUtils.isNotEmpty(loanContractNum)) {
            return loanRepayBeanDaoService.findLatestRepayRecord(loanContractNum);
        }else {
            return null;
        }
    }


    @Transactional
    public  CallResult<String> addRepay(RepayVo vo,String userId) throws Exception{
        CallResult<String> result = new CallResult<>();
        if(ObjectUtils.isEmpty(vo.getAddRepayDate())){
            result.setFailResult("补录时间有误");
            return  result;
        }
        LoanRepayBean lrParam = new LoanRepayBean();
        lrParam.setLoanContractNum(vo.getLoanContractNum());
        LoanRepayBean loanRepayBean = loanRepayBeanDaoService.findLatestRepayByLoanContractNumAndRepayType(lrParam);
        if(!ObjectUtils.isEmpty(loanRepayBean)){
            if(!String.valueOf(loanRepayBean.getId_prikey()).equals(vo.getBusinessId())) {
                if (loanRepayBean.getCheckFirstDate().getTime() - vo.getAddRepayDate().getTime() > 0) {
                    result.setFailResult("补录时间内，已有还款记录，不能补录");
                    return result;
                }
            }
        }
        CallResult<LoanRepayBean> repayResult = reCountAddRepay(vo,true);
        if(repayResult.isExec()){
            LoanRepayBean countLoan = repayResult.getData();
            LoanDistributeBean param = new LoanDistributeBean();
            param.setLoanContractNum(vo.getLoanContractNum());
            LoanDistributeBean loanDistributeBean = loanDistributeBeanDaoService.getByField(param);
            LoanRepayBean record = new LoanRepayBean();
            BeanUtils.copyProperties(countLoan,record);
            record.setOrg(vo.getOrg());
            record.setLoanNewMonthRate(countLoan.getLoanNewMonthRate());
            record.setRepayWay(vo.getRepayWay());
            record.setCheckFirstDate(vo.getAddRepayDate());
            record.setRepayType(Integer.valueOf(RepayTypeEnum.REPAY_TYPE_ADD.getCode()));
            record.setStatus(Integer.valueOf(CheckRepayStatusEnum.CHECK_REPAY_STATUS_ADD_FIRST.getCode()));
            record.setCheckFirst(userId);
            if(loanDistributeBean != null){
                record.setCustomerManage(loanDistributeBean.getNumber());
                record.setCustomerManageType(loanDistributeBean.getNumberType());
            }
            if(LoanTypeEnum.LOAN_TYPE_HX.getCode().equals(countLoan.getLoanType())){
                record.setBiaoWaiLeftInterestAmount(countLoan.getBiaoWaiLeftInterestAmount());
                record.setSourceBiaoWaiLeftInterestAmount(countLoan.getSourceBiaoWaiLeftInterestAmount());
                record.setHeXiaoLeftInterestAmount(countLoan.getHeXiaoLeftInterestAmount());
                record.setSourceHeXiaoLeftInterestAmount(record.getSourceHeXiaoLeftInterestAmount());

            }
            Integer repayId = null;
            if(StringUtils.isNotBlank(vo.getBusinessId()) && ObjectUtils.isNotEmpty(vo.getNodeId())){
                record.setId_prikey(Integer.valueOf(vo.getBusinessId()));
                loanRepayBeanDaoService.set(record);
                repayId = Integer.valueOf(vo.getBusinessId());
            }else{
                repayId = loanRepayBeanDaoService.addPrikey(record);
            }

            ProcessVo processvo = new ProcessVo();
            processvo.setUserId(userId);
            processvo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_ADD.getCode());
            processvo.setNodeSatus(ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode());
            processvo.setBusinessId(String.valueOf(repayId));
            processvo.setNodeId(0);
            processvo.setRemark(vo.getRemark());
            processvo.setFilePath(vo.getFiles());
            processvo.setBusinessCode(vo.getLoanContractNum());
            CallResult<ProcessHandleBean> processResult = processHandleService.processHandle(processvo);
            if(!processResult.isExec()){
                throw  new RuntimeException(processResult.getMsg());
            }
            result.setSuccessResult();
        }else{
            result.setFailResult(repayResult.getMsg());
        }


        return result;
    }

    @Transactional
    public CallResult<ProcessHandleBean> addRepayCheck(RepayAddVo vo,String userId) throws  Exception{
        CallResult<ProcessHandleBean> result  = new CallResult<>();

        LoanRepayBean loanRepayBean = loanRepayBeanDaoService.getById(Integer.valueOf(vo.getId_prikey()));
        if(ObjectUtils.isEmpty(loanRepayBean)){
            result.setFailResult("数据有误");
            return result;
        }
        LoanInfoBean lbParam = new LoanInfoBean();
        lbParam.setLoanContractNum(loanRepayBean.getLoanContractNum());
        LoanInfoBean loanInfoBean = loanInfoBeanDaoService.getByField(lbParam);
        if(ObjectUtils.isEmpty(loanInfoBean)){
            result.setFailResult("数据有误");
            return result;
        }
        if(ObjectUtils.isNotEmpty(loanRepayBean)){
            ProcessVo processvo = new ProcessVo();
            processvo.setUserId(userId);
            processvo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_ADD.getCode());
            processvo.setNodeSatus(vo.getProcessStatus());
            processvo.setBusinessId(vo.getId_prikey());
            processvo.setBusinessCode(loanRepayBean.getLoanContractNum());
            processvo.setNodeId(Integer.valueOf(vo.getNodeId()));
            processvo.setRemark(vo.getRemark());
            processvo.setFilePath(vo.getFiles());
            CallResult<ProcessHandleBean> processResult = processHandleService.processHandle(processvo);
            if(!processResult.isExec()){
                return processResult;
            }
            if(vo.getProcessStatus().equals(ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode())){
                LoanInfoBean record = new LoanInfoBean();
                record.setId_prikey(loanInfoBean.getId_prikey());
                record.setLeftCapitalAmount(loanRepayBean.getLeftCapitalAmount());
                record.setLeftInterestAmount(loanRepayBean.getLeftInterestAmount());
                if(LoanTypeEnum.LOAN_TYPE_HX.getCode().equals(String.valueOf(loanRepayBean.getLoanType()))){
                    if(ObjectUtils.isNotEmpty(loanRepayBean.getHeXiaoLeftInterestAmount())){
                        record.setHeXiaoLeftInterestAmount(loanRepayBean.getHeXiaoLeftInterestAmount());
                    }
                    if(ObjectUtils.isNotEmpty(loanRepayBean.getBiaoWaiLeftInterestAmount())){
                        record.setBiaoWaiLeftInterestAmount(loanRepayBean.getBiaoWaiLeftInterestAmount());
                    }
                }
                loanInfoBeanDaoService.set(record);

                LoanRepayBean repayRecord = new LoanRepayBean();
                repayRecord.setId_prikey(loanRepayBean.getId_prikey());
                repayRecord.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_ADD_SENCODN.getCode());
                repayRecord.setCheckSecondDate(new Date());
                repayRecord.setCheckSecond(userId);
                loanRepayBeanDaoService.set(repayRecord);

                LoanCountInterestLog removeParam = new LoanCountInterestLog();
                removeParam.setLoanContractNum(loanRepayBean.getLoanContractNum());
                removeParam.setCountDate(DateUtil.formatDate(loanRepayBean.getCheckFirstDate(),"yyyy-MM-dd"));
                loanCountInterestLogDaoService.deleteByLoanContractNumAndCountDate(removeParam);
                LoanCountInterestLog logParam = new LoanCountInterestLog();
                logParam.setLoanContractNum(loanRepayBean.getLoanContractNum());
                logParam.setCountDate(DateUtil.formatDate(loanRepayBean.getCheckFirstDate(),"yyyy-MM-dd"));
                LoanCountInterestLog loanCountInterestLog = loanCountInterestLogDaoService.getByField(logParam);
                if(loanCountInterestLog != null){
                    LoanCountInterestLog logRecord = new LoanCountInterestLog();
                    logRecord.setId_prikey(loanCountInterestLog.getId_prikey());
                    logRecord.setLeftCapitalAmount(record.getLeftCapitalAmount());
                    logRecord.setLeftInterestAmount(record.getLeftInterestAmount());
                    if(ObjectUtils.isNotEmpty(loanRepayBean.getHeXiaoLeftInterestAmount())){
                        logRecord.setHeXiaoLeftInterestAmount(loanRepayBean.getHeXiaoLeftInterestAmount());
                    }
                    if(ObjectUtils.isNotEmpty(loanRepayBean.getBiaoWaiLeftInterestAmount())){
                        logRecord.setBiaoWaiLeftInterestAmount(loanRepayBean.getBiaoWaiLeftInterestAmount());
                    }
                    loanCountInterestLogDaoService.set(logRecord);
                }
                loanCountInterestLogDaoService.insertDataFromLoanCountInterestLogTemp(loanRepayBean.getLoanContractNum());
            }else{
                LoanRepayBean repayRecord = new LoanRepayBean();
                repayRecord.setId_prikey(loanRepayBean.getId_prikey());
                repayRecord.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_ADD_FIRST.getCode());
                repayRecord.setCheckSecondDate(new Date());
                loanRepayBeanDaoService.set(repayRecord);
            }
        }
        result.setSuccessResult();
        return result;
    }

    public static Date dateAddDays(Date startDate, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        return c.getTime();
    }

    @Transactional
    public CallResult<String> revokeRepayCancel(Integer id_prikey, String userId) throws Exception {
        CallResult<String> result = new CallResult<>();

        ProcessBusinessBean processBusinessBean = processBusinessBeanDaoService.getById(id_prikey);
        if (null != processBusinessBean) {
            //修改还款状态
            LoanRepayBean loanRepayBean = new LoanRepayBean();
            loanRepayBean.setRepayType(Integer.valueOf(RepayTypeEnum.REPAY_TYPE_NORMAL.getCode()));
            loanRepayBean.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_SENCOND.getCode());
            loanRepayBean.setId_prikey(Integer.valueOf(processBusinessBean.getBusinessId()));
            loanRepayBeanDaoService.set(loanRepayBean);

            //修改流程状态
            processHandleService.processHandleCancel(id_prikey,userId);
            result.setSuccessResult();
        }else {
            result.setFailResult("没有该流程");
        }
        return result;
    }

    public  CallResult<ProcessHandleBean> repayPrint(Integer id_prikey,String userId) throws  Exception{
        CallResult<ProcessHandleBean> result = new CallResult<>();
        LoanRepayBean loanRepayBean = loanRepayBeanDaoService.getById(id_prikey);
        if(ObjectUtils.isEmpty(loanRepayBean)){
            result.setFailResult("未查询到还款信息");
            return result;
        }
        ProcessVo processvo = new ProcessVo();
        processvo.setUserId(userId);
        processvo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_PRINT.getCode());
        processvo.setNodeSatus(ProcessNodeStatusEnum.PROCESS_NODE_STATUS_YES.getCode());
        processvo.setBusinessId(String.valueOf(id_prikey));
        processvo.setBusinessCode(loanRepayBean.getLoanContractNum());
        processvo.setNodeId(0);
        CallResult<ProcessHandleBean> processResult = processHandleService.processHandle(processvo);
        if(!processResult.isExec()){
            return processResult;
        }
        result.setSuccessResult();
        return result;
    }

}
