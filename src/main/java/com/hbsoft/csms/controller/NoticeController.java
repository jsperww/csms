package com.hbsoft.csms.controller;

import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.NoticeBean;
import com.hbsoft.csms.dao.service.NoticeBeanDaoService;
import com.hbsoft.csms.service.NoticeService;
import com.hbsoft.csms.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class NoticeController extends ABaseController {

    @Autowired
    NoticeService noticeService;
    @Autowired
    NoticeBeanDaoService noticeBeanDaoService;
    @Autowired
    SessionService sessionService;

    /**
     * 获取发布公告
     * sys
     * @return
     */
    @RequestMapping("getIssueNotice")
    public String issueNotice(NoticeBean noticeBean) {
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
     * 添加公告
     * sys
     * @param noticeBean
     * @return
     */
    @RequestMapping("addNotice")
    public String addNotice(NoticeBean noticeBean, HttpSession session) {
        CallResult result = new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);
            noticeBean.setTitle(HtmlUtils.htmlEscape(noticeBean.getTitle()));
            noticeBean.setContent(HtmlUtils.htmlEscape(noticeBean.getContent()));
            noticeBean.setIssName(hb_user.getName());
            noticeBean.setIssueTime(new Date());
            noticeBeanDaoService.add(noticeBean);
            result.setSuccessResult("添加成功");
        }catch (Exception e) {
            result.setFailResult("添加失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }
    /**
     * 删除公告
     * sys
     * @param noticeBean
     * @return
     */
    @RequestMapping("deleteNotice")
    public String deleteNotice(NoticeBean noticeBean) {
        CallResult result = new CallResult<>();
        try {
            if (null == noticeBean.getId_prikey()) {
                result.setFailResult("主键不能为空");
                return gson.toJson(result);
            }
            noticeBeanDaoService.removeOne(noticeBean.getId_prikey());
            result.setSuccessResult("删除成功");
        }catch (Exception e) {
            result.setFailResult("删除失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 编辑公告
     * sys
     * @param noticeBean
     * @return
     */
    @RequestMapping("updateNotice")
    public String updateNotice(NoticeBean noticeBean,HttpSession session) {
        CallResult result = new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);
            if (null == noticeBean.getId_prikey()) {
                result.setFailResult("主键不能为空");
                return gson.toJson(result);
            }
            noticeBean.setTitle(HtmlUtils.htmlEscape(noticeBean.getTitle()));
            noticeBean.setContent(HtmlUtils.htmlEscape(noticeBean.getContent()));
            noticeBean.setIssName(hb_user.getName());
            noticeBean.setIssueTime(new Date());
            noticeBeanDaoService.set(noticeBean);
            result.setSuccessResult("编辑成功");
        }catch (Exception e) {
            result.setFailResult("编辑失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }


}
