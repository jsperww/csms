package com.hbsoft.dingding.controller;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.MesageInfoBean;
import com.hbsoft.csms.dao.service.MesageInfoBeanDaoService;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yj
 * @description 信息收集
 * @date 2020/5/29
 */
@RestController
@RequestMapping(value = "dingDing", produces = "application/json;charset=UTF-8")
public class DingDingMessageInfoController extends ABaseController {

    @Autowired
    MesageInfoBeanDaoService mesageInfoBeanDaoService;

    /**
     * 添加信息
     *
     * @param mib
     * @return
     */
    @PostMapping("ddAddMesage")
    public String addMesage(MesageInfoBean mib,HttpSession session) {
        CallResult<String> result = new CallResult<>();
        CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
        if(!ddResult.isExec()){
            return gson.toJson(ddResult);
        }
        DingDingUserInfo dingDingUserInfo = ddResult.getData();
        if (isEmpty(mib.getContent())) {
            result.setFailResult("内容不能为空");
            return gson.toJson(result);
        }
        try {
            Date date = new Date();
            mib.setCreateBy(dingDingUserInfo.getHbUserId());
            mib.setCreateOn(date);
            mib.setUpdateBy(dingDingUserInfo.getHbUserId());
            mib.setUpdateOn(date);
            mesageInfoBeanDaoService.add(mib);
            result.setSuccessResult();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 获取信息分页列表
     *
     * @param mib
     * @return
     */
    @PostMapping("ddGetMesagePage")
    public String getMesagePage(MesageInfoBean mib, Integer flag,HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        try {
            CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
            if(!ddResult.isExec()){
                return gson.toJson(ddResult);
            }
            DingDingUserInfo dingDingUserInfo = ddResult.getData();
            if(flag == null){
                map.put("code", 400);
                map.put("message", "flag参数有误");
                return gson.toJson(map);
            }

            if (flag == 0) {
                map = mesageInfoBeanDaoService.getPagingData(mib);
            } else if (flag == 1) {
                mib.setCreateBy(dingDingUserInfo.getHbUserId());
                map = mesageInfoBeanDaoService.getPagingData(mib);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            map.put("code", 400);
            map.put("message", "操作失败");
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
