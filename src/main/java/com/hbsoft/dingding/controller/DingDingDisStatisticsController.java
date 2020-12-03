package com.hbsoft.dingding.controller;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.LoanVisitBean;
import com.hbsoft.csms.dao.service.LoanVisitBeanDaoService;
import com.hbsoft.csms.service.DeptUserService;
import com.hbsoft.csms.service.DistributeStatisticsService;
import com.hbsoft.csms.service.RepayStatisticsService;
import com.hbsoft.csms.vo.LoanInfoVo;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yj
 * @description 钉钉还款信息统计
 * @date 2020/5/28
 */
@RestController
@RequestMapping(value = "dingDing", produces = "application/json;charset=UTF-8")
public class DingDingDisStatisticsController extends ABaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DistributeStatisticsService distributeStatisticsService;

    @Autowired
    RepayStatisticsService repayStatisticsService;

    @Autowired
    LoanVisitBeanDaoService loanVisitBeanDaoService;

    @Autowired
    DeptUserService deptUserService;

    /**
     * 钉钉获取客户统计信息
     * @return
     */
    @PostMapping("ddDistributeNumById")
    public String ddDistributeNumById(HttpSession session){
        CallResult<Map<String,Object>> result = new CallResult<>();
        Map<String,Object> map = new HashMap<String,Object>();
        CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
        logger.info(ddResult.getMsg() + "{钉钉用户名 ：}" + ddResult.getData().getHbUserId());
        if(!ddResult.isExec()){
            return gson.toJson(ddResult);
        }
        DingDingUserInfo dingDingUserInfo = ddResult.getData();
        try {
            Integer managerNum = distributeStatisticsService.getDistributeNumById(dingDingUserInfo.getHbUserId(), "1");
            Integer groupNum = distributeStatisticsService.getDistributeNumById(dingDingUserInfo.getHbUserDeptId(), "2");
            Integer customerAll = distributeStatisticsService.getCustomerAll();
            LoanInfoVo param = new LoanInfoVo();
            param.setCreateBy(dingDingUserInfo.getHbUserId());
            Integer visitNum = loanVisitBeanDaoService.getVisitNum(param);
            map.put("manager", managerNum);
            map.put("group", groupNum);
            map.put("customerAll", customerAll);
            map.put("visitNum",visitNum);
            result.setSuccessResult(map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 钉钉获取还款统计信息
     * @param date
     * @return
     */
    @PostMapping("ddRepayNumByManager")
    public String ddRepayNumByManager(String date,HttpSession session){
        CallResult<Map<String,Object>> result = new CallResult<>();
        Map<String,Object> map = new HashMap<String,Object>();
        CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
        logger.info(ddResult.getMsg() + "{钉钉用户名 ：}" + ddResult.getData().getHbUserId());
        if(!ddResult.isExec()){
            return gson.toJson(ddResult);
        }
        DingDingUserInfo dingDingUserInfo = ddResult.getData();
        try {
            Map<String, Object> managerMap = repayStatisticsService.getRepayManagerNum(dingDingUserInfo.getHbUserId(),date);
            Integer managerPeople = repayStatisticsService.getRepayManagerPeopleNum(dingDingUserInfo.getHbUserId(), date);
            managerMap.put("peopleNum", managerPeople);
            Map<String, Object> groupMap = repayStatisticsService.getRepayManagerNum(dingDingUserInfo.getHbUserDeptId(),date);
            Integer groupPeople = repayStatisticsService.getRepayManagerPeopleNum(dingDingUserInfo.getHbUserDeptId(), date);
            groupMap.put("peopleNum", groupPeople);
            map.put("manager", managerMap);
            map.put("group", groupMap);
            result.setSuccessResult(map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
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
