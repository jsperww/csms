package com.hbsoft.dingding.controller;

import com.hb.annotation.LoginNotRequired;
import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import com.hbsoft.dingding.service.DDservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * ClassName:    TestController
 * Package:    com.hbsoft.dingding
 * Description:
 * Datetime:    2020/4/8   15:44
 * Author:  hwl
 */
@RestController
@RequestMapping(value = "dingDing", produces = "application/json;charset=UTF-8")
public class DingDingTestController extends ABaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    DDservice ddService;


    @RequestMapping("test")
    @LoginNotRequired
    public String test(HttpSession session){
        DingDingUserInfo dingDingUserInfo = new DingDingUserInfo();
        dingDingUserInfo.setHbUserId("cs6");
        dingDingUserInfo.setHbUserDeptId("001001010");
        dingDingUserInfo.setNameCut("测试");
        dingDingUserInfo.setName("测试");
        dingDingUserInfo.setAvatar("123");
        session.setAttribute("dingDingUserInfo",dingDingUserInfo);
        return gson.toJson(dingDingUserInfo);
    }


    @RequestMapping("test1")
    @LoginNotRequired
    public String test1(HttpSession session){
        DingDingUserInfo dingDingUserInfo = new DingDingUserInfo();
        dingDingUserInfo.setHbUserId("454704");
        dingDingUserInfo.setHbUserDeptId("467002");
        dingDingUserInfo.setNameCut("测试");
        dingDingUserInfo.setName("测试123");
        dingDingUserInfo.setAvatar("123");
        session.setAttribute("dingDingUserInfo",dingDingUserInfo);
        return gson.toJson(dingDingUserInfo);
    }

    @LoginNotRequired
    @RequestMapping("DdGetUser")
    @ResponseBody
    public String DdGetUser() {
        CallResult<String> result = new CallResult<>();
        try {
            ddService.getDepts();
            result.setSuccessResult();
        }catch (Exception e) {
            result.setFailResult("操作失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    @LoginNotRequired
    @RequestMapping("ddGetJSSDKSign")
    @ResponseBody
    public String getJSSDKSign(String url) {
        String jssdkSign = "";
        try {
            jssdkSign = ddService.getJSSDKSign(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jssdkSign;
    }

    @LoginNotRequired
    @RequestMapping("ddGetJSSDKSignZz")
    @ResponseBody
    public String ddGetJSSDKSignZz(String url) {
        String jssdkSign = "";
        try {
            jssdkSign = ddService.getJSSDKSignZz(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jssdkSign;
    }
}
