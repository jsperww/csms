package com.hbsoft.dingding.controller;

import com.hb.annotation.LoginNotRequired;
import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.config.SysConfigInfo;
import com.hbsoft.csms.bean.DDLoginLogBean;
import com.hbsoft.csms.dao.service.DDLoginLogBeanDaoService;
import com.hbsoft.csms.service.DeptUserService;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import com.hbsoft.dingding.service.DDservice;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;


@Controller
@RequestMapping(value = "",produces = "application/json;charset=UTF-8")
public class DingDingApplicationController extends ABaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	SysConfigInfo sysConfigInfo;

	@Autowired
	DDservice dDservice;

	@Autowired
	DeptUserService deptUserService;

	@Autowired
	DDLoginLogBeanDaoService ddLoginLogBeanDaoService;

	/**
	 * 钉钉首页
	 * @param model
	 * @return
	 */
	@LoginNotRequired
	@RequestMapping(value = "ddApplication")
	public String ddAppletLogin(Model model){
		model.addAttribute("ddCorpId",sysConfigInfo.getDdCorpId());
		logger.info("钉钉获取首页 {}",sysConfigInfo.getDdCorpId());
		return "index";
	}

	@LoginNotRequired
	@RequestMapping(value = "dingDing/ddGetddUserId")
	@ResponseBody
	public String getddUserId(String code,HttpSession session){
		logger.info("钉钉获取code {}",code);
		CallResult<String> result = new CallResult<>();
		try {
			result = dDservice.getDingDingUserId(code);
			if (result.isExec()) {
				String userid = result.getData();
				DingDingUserInfo dingDingUserInfo = dDservice.getDingDingUser(userid);
				if (null == dingDingUserInfo) {
					result.setFailResult("请联系管理员维护钉钉信息");
				}else {
					if(StringUtils.isBlank(dingDingUserInfo.getJobnumber())){
						result.setFailResult("请联系管理员维护钉钉的员工工号");
						return gson.toJson(result);
					}
					Map<String, Object> map = deptUserService.selectUserByUserId(dingDingUserInfo.getJobnumber());
					if (null == map) {
						result.setFailResult("请联系管理员更新钉钉信息");
					}else {
					    if (map.get("id") != null && map.get("deptId") != null) {
                            dingDingUserInfo.setHbUserId(map.get("id").toString());
                            dingDingUserInfo.setHbUserDeptId(map.get("deptId").toString());
                            if (dingDingUserInfo.getName() != null) {
                            	String name = dingDingUserInfo.getName();
								dingDingUserInfo.setNameCut(name.substring(name.length()-2,name.length()));
							}
							session.setAttribute("dingDingUserInfo",dingDingUserInfo);
                            DDLoginLogBean ddLoginLogBean = new DDLoginLogBean();
                            ddLoginLogBean.setDdName(dingDingUserInfo.getName());
                            ddLoginLogBean.setDdId(dingDingUserInfo.getUserid());
                            ddLoginLogBean.setLogintime(new Date());
							ddLoginLogBeanDaoService.add(ddLoginLogBean);
                        }else {
					        result.setFailResult("请联系管理员");
                        }
					}
				}
			}else {
				result.setFailResult("获取钉钉用户失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailResult();
		}
		return gson.toJson(result);
	}



	@LoginNotRequired
	@RequestMapping(value = "thirdPartyLogin")
	public String thirdPartyLogin(String dingId,HttpSession session,Model model){
		CallResult<String> result = new CallResult<>();
		if(StringUtils.isBlank(dingId)){
			result.setFailResult("参数有误");
			model.addAttribute("msg", result.getMsg());
			return "ddIndex";
		}
		synchronized (this) {
			try {
				DingDingUserInfo dingDingUserInfo = dDservice.getDingDingUser(dingId);
				if (null == dingDingUserInfo) {
					result.setFailResult("请联系管理员维护钉钉信息");
				} else {
					if (StringUtils.isBlank(dingDingUserInfo.getJobnumber())) {
						result.setFailResult("请联系管理员维护钉钉的员工工号");
					}
					Map<String, Object> map = deptUserService.selectUserByUserId(dingDingUserInfo.getJobnumber());
					if (null == map) {
						result.setFailResult("请联系管理员更新钉钉信息");
					} else {
						if (map.get("id") != null && map.get("deptId") != null) {
							dingDingUserInfo.setHbUserId(map.get("id").toString());
							dingDingUserInfo.setHbUserDeptId(map.get("deptId").toString());
							if (dingDingUserInfo.getName() != null) {
								String name = dingDingUserInfo.getName();
								dingDingUserInfo.setNameCut(name.substring(name.length() - 2, name.length()));
							}
							session.setAttribute("dingDingUserInfo", dingDingUserInfo);
							DDLoginLogBean ddLoginLogBean = new DDLoginLogBean();
							ddLoginLogBean.setDdName(dingDingUserInfo.getName());
							ddLoginLogBean.setDdId(dingDingUserInfo.getUserid());
							ddLoginLogBean.setLogintime(new Date());
							ddLoginLogBeanDaoService.add(ddLoginLogBean);
						} else {
							result.setFailResult("请联系管理员");
						}
					}
				}
				if (!result.isExec()) {
					model.addAttribute("msg", result.getMsg());
				} else {
					model.addAttribute("ddUserInfo", dingDingUserInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "操作异常");
			}
		}
		return "ddIndex";
	}
}
