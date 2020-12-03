package com.hbsoft.csms.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.hb.bean.hbcmsql.HB_User;
import com.hbsoft.common.enumtype.LoanWarnEnum;
import com.hbsoft.common.enumtype.SendDDMessageTypeEnum;
import com.hbsoft.csms.bean.DdsendmessageBean;
import com.hbsoft.csms.dao.service.DdsendmessageBeanDaoService;
import com.hbsoft.csms.dao.service.DingDingUserInfoDaoService;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import com.hbsoft.dingding.util.DingDingUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.LoanWarnInfo;
import com.hbsoft.csms.dao.service.LoanWarnInfoDaoService;
import com.hbsoft.csms.service.LoanWarnService;

import javax.servlet.http.HttpSession;

/**
 * ClassName:    LoanWarnController
 * Package:    com.hbsoft.csms.controller
 * Description:
 * Datetime:    2020/4/10   9:27
 * Author:  hwl
 */
@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class LoanWarnController extends ABaseController {

    @Autowired
    private LoanWarnInfoDaoService loanWarnInfoDaoService;
    @Autowired
    private LoanWarnService loanWarnService;
    @Autowired
    private DingDingUtil dingDingUtil;
    @Autowired
    private DingDingUserInfoDaoService dingDingUserInfoDaoService;
    @Autowired
    private DdsendmessageBeanDaoService ddsendmessageBeanDaoService;


    @RequestMapping("myLoanWarnPagingData")
    public String myLoanWarnPagingData(LoanWarnInfo loanWarnInfo,HttpSession session) {
        Map<String, Object> map = null;
        try {
            HB_User hb_user = getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                map.put("code", 400);
                map.put("msg", "请重新登录");
                return gson.toJson(map);
            }
            loanWarnInfo.setWarnTarget(hb_user.getName());
            if (StringUtils.isBlank(hb_user.getName())) {
                map.put("code", 400);
                map.put("msg", "权限不足");
                return gson.toJson(map);
            }
            map = loanWarnInfoDaoService.getPagingData(loanWarnInfo);
            if (null != map) {
                return gson.toJson(map);
            } else {
                map = new HashMap<>();
                map.put("code", 400);
                map.put("msg", "暂无数据");
            }
        } catch (Exception e) {
            map = new HashMap<>();
            map.put("code", 400);
            map.put("msg", "获取失败");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    @RequestMapping("loanEndDateWarn")
    public String loanEndDateWarn() {
        CallResult<String> result = new CallResult<>();
        try {
            result = loanWarnService.loanEndDateWarn();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    public HB_User getHbUserBySession(HttpSession session){
        return  (HB_User) session.getAttribute("hb_user");
    }

}
