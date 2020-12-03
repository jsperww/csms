package com.hbsoft.dingding.controller;

import com.hb.annotation.LoginNotRequired;
import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.dao.service.*;
import com.hbsoft.csms.service.LoanInfoService;
import com.hbsoft.csms.service.SessionService;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yj
 * @Date: 2020/3/26 15:59
 */
@RestController
@RequestMapping(value = "dingDing", produces = "application/json;charset=UTF-8")
public class DingDingLoanInfoController extends ABaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LoanInfoService loanInfoService;

    @Autowired
    LoanInfoBeanDaoService loanInfoBeanDaoService;

    @Autowired
    LoanDistributeBeanDaoService loanDistributeBeanDaoService;
    
    @Autowired
    LoanGuaranteeDaoService loanGuaranteeDaoService;
    
    @Autowired
    LoanMortgageDaoService loanMortgageDaoService;
    
    @Autowired
    LoanDistributeOrgVillvgeBeanDaoService loanDistributeOrgVillvgeBeanDaoService;



    /**
    * @author hwl
    * @description 我的客户列表
    * @date 2020/4/2
     * data[loanAccountNum];
     * data[name];
     * data[town];
     * data[village];
    */
    @LoginNotRequired
    @RequestMapping("ddMyLoanInfoPagingData")
    public String myLoanInfoPagingData(LoanInfoBean loanInfoBean,HttpSession session){
        Map<String, Object> map = null;
        try {
            CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
            if(!ddResult.isExec()){
                return gson.toJson(ddResult);
            }
            DingDingUserInfo dingDingUserInfo = ddResult.getData();
            loanInfoService.loanInfoV1PassHandle(loanInfoBean);
            loanInfoBean.setUserId(dingDingUserInfo.getHbUserId());
            loanInfoBean.setDeptId(String.valueOf(dingDingUserInfo.getHbUserDeptId()));
            map = loanInfoBeanDaoService.getPagingData(loanInfoBean);
            if (null != map) {
                return gson.toJson(map);
            }else {
                map = new HashMap<>();
                map.put("code",400);
                map.put("msg","暂无数据");
            }
        }catch (Exception e) {
            map = new HashMap<>();
            map.put("code",400);
            map.put("msg","获取失败");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * @author hwl
     * @description 客户列表
     * @date 2020/4/2
     * data[loanAccountNum];
     * data[name];
     * data[town];
     * data[village];
     */
    @LoginNotRequired
    @RequestMapping("ddLoanInfoPagingData")
    public String ddLoanInfoPagingData(LoanInfoBean loanInfoBean,HttpSession session){
        Map<String, Object> map = null;
        try {
            CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
            if(!ddResult.isExec()){
                return gson.toJson(ddResult);
            }
            DingDingUserInfo dingDingUserInfo = ddResult.getData();
            loanInfoService.loanInfoV1PassHandle(loanInfoBean);
            map = loanInfoBeanDaoService.getPagingData(loanInfoBean);
            if (null != map) {
                return gson.toJson(map);
            }else {
                map = new HashMap<>();
                map.put("code",400);
                map.put("msg","暂无数据");
            }
        }catch (Exception e) {
            map = new HashMap<>();
            map.put("code",400);
            map.put("msg","获取失败");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 获取客户信息
     * @param loanContractNum
     * @return
     */
    @LoginNotRequired
    @RequestMapping("ddSelectLoanInfo")
    public String selectLoanInfo(String loanContractNum) {
        CallResult<LoanInfoBean> result = new CallResult<>();
        try {
            if (StringUtils.isNotEmpty(loanContractNum)) {
                result = loanInfoService.selectLoanInfo(loanContractNum);
            } else {
                result.setFailResult("主键参数错误");
            }
        }catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 修改客户信息
     * @param loanInfoBean
     * @return
     */
    @LoginNotRequired
    @RequestMapping("ddUpdateLoanInfo")
    public String ddUpdateLoanInfo(LoanInfoBean loanInfoBean,HttpSession session) {
        CallResult<String> result = new CallResult<>();
        try {
            CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
            if(!ddResult.isExec()){
                return gson.toJson(ddResult);
            }
            DingDingUserInfo dingDingUserInfo = ddResult.getData();
            if (null == loanInfoBean.getId_prikey()) {
                result.setFailResult("主键参数错误");
            }else {
                loanInfoBean.setUserId(dingDingUserInfo.getHbUserId());
                result = loanInfoService.updateLoanInfo(loanInfoBean,dingDingUserInfo.getHbUserId());
            }
        }catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    public CallResult<DingDingUserInfo> getDingDingUserInfoBySession(HttpSession session){
        CallResult<DingDingUserInfo> result  = new CallResult<>();
        DingDingUserInfo dingDingUserInfo = (DingDingUserInfo) session.getAttribute("dingDingUserInfo");
        if(ObjectUtils.isEmpty(dingDingUserInfo)){
            result.setFailResult("登录超时，请重新登录");
        }else {
            result.setSuccessResult(dingDingUserInfo);
        }
        return result;
    }

}
