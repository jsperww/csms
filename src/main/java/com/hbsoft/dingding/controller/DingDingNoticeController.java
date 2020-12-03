package com.hbsoft.dingding.controller;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.NoticeBean;
import com.hbsoft.csms.dao.service.NoticeBeanDaoService;
import com.hbsoft.csms.service.DistributeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉公告
 * sys
 */
@RestController
@RequestMapping(value = "dingDing", produces = "application/json;charset=UTF-8")
public class DingDingNoticeController extends ABaseController {

    @Autowired
    NoticeBeanDaoService noticeBeanDaoService;

    /**
     * 获取发布公告
     * sys
     * @return
     */
    @RequestMapping("ddNotice")
    public String ddNotice(NoticeBean noticeBean) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = noticeBeanDaoService.getPagingData(noticeBean);
        }catch (Exception e) {
            map.put("code",0);
            map.put("msg","获取失败");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 获取公告详情
     * @param noticeBean
     * @return
     */
    @RequestMapping("ddNoticeDetails")
    public String ddNoticeDetails(NoticeBean noticeBean) {
        CallResult result = new CallResult<>();
        try {
            if (null == noticeBean.getId_prikey()) {
                result.setFailResult("主键不能为空");
                return gson.toJson(result);
            }
            NoticeBean byid = noticeBeanDaoService.getById(noticeBean.getId_prikey());
            if(byid != null){
                result.setSuccessResult(byid);
            }else {
                result.setFailResult("获取参数有误");
            }
        }catch (Exception e) {
            result.setFailResult("获取失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }





}
