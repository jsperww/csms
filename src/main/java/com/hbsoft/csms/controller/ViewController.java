package com.hbsoft.csms.controller;

import com.hb.bean.hbcmsql.HB_User;
import com.hbsoft.common.constant.Constant;
import com.hbsoft.csms.dao.HbcmUserDao;
import com.hbsoft.csms.service.SessionService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hb.controller.ABaseViewController;

import javax.servlet.http.HttpSession;

@Controller
public class ViewController extends ABaseViewController{

	@Autowired
	HbcmUserDao hbcmUserDao;
	@Autowired
	SessionService sessionService;

	@RequestMapping("viewPage")
	public String viewPage(String page){
		if(isEmpty(page)){
			return "400";
		}
		return page;
	}

	@RequestMapping("myCustomer")
	public String myCustomer(Model model, HttpSession session){
		HB_User hb_user = sessionService.getHbUserBySession(session);
		if(ObjectUtils.isEmpty(hb_user)){
			return "400";
		}
		String manager = hbcmUserDao.findViewByUserId(hb_user.getDeptId());
		if (StringUtils.isNotBlank(manager)) {
			if (manager.equals(hb_user.getName())) {
				model.addAttribute("auth", Constant.AUTH);
			}
		}
		return "myCustomer";
	}

	@RequestMapping("ddIndex")
	public String ddIndex(Model model,HttpSession session) {
		model.addAttribute("ddUserInfo",session.getAttribute("dingDingUserInfo"));
		return "ddIndex";
	}
}
