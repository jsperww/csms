package com.hbsoft.dingding.controller;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.LoanWarnInfo;
import com.hbsoft.csms.dao.service.LoanWarnInfoDaoService;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:    LoanWarnController
 * Package:    com.hbsoft.csms.controller
 * Description:
 * Datetime:    2020/4/10   9:27
 * Author:  hwl
 */
@RestController
@RequestMapping(value = "dingDing", produces = "application/json;charset=UTF-8")
public class DingDingLoanWarnController extends ABaseController {

    @Autowired
    private LoanWarnInfoDaoService loanWarnInfoDaoService;


    @RequestMapping("ddMyLoanWarnPagingData")
    public String  myLoanWarnPagingData(LoanWarnInfo loanWarnInfo,HttpSession session){
        Map<String, Object> map = null;
        try {
            CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
            if(!ddResult.isExec()){
                return gson.toJson(ddResult);
            }
            DingDingUserInfo dingDingUserInfo = ddResult.getData();
            loanWarnInfo.setWarnTarget(dingDingUserInfo.getHbUserId());
            map = loanWarnInfoDaoService.getPagingData(loanWarnInfo);
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
