package com.hbsoft.dingding.controller;

import com.hb.annotation.LoginNotRequired;
import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hb.controller.ABaseController;
import com.hb.util.V1Pass;
import com.hbsoft.config.SysConfigInfo;
import com.hbsoft.csms.bean.DDLoginLogBean;
import com.hbsoft.csms.bean.LoanRepayBean;
import com.hbsoft.csms.dao.service.DDLoginLogBeanDaoService;
import com.hbsoft.csms.dao.service.LoanRepayBeanDaoService;
import com.hbsoft.csms.service.DeptUserService;
import com.hbsoft.csms.service.LoanRepayService;
import com.hbsoft.csms.vo.RepayLoanSecondVo;
import com.hbsoft.csms.vo.RepayVo;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import com.hbsoft.dingding.service.DDservice;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "dingDing",produces = "application/json;charset=UTF-8")
public class DingDingRepayController extends ABaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LoanRepayService loanRepayService;

	@Autowired
	private LoanRepayBeanDaoService loanRepayBeanDaoService;

	/**
	 * @author hwl
	 * @description 根据还款金额计算结欠本金和利息
	 * @date 2020/4/1
	 */
	@RequestMapping("dingDingReCountLoan")
	public String reCountLoan(@Validated RepayVo vo){
		CallResult<LoanRepayBean> result =  new CallResult<>();
		try {
			result = loanRepayService.reCountRepay(vo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailResult();
		}
		return  gson.toJson(result);
	}

	@RequestMapping("dingDingRePayLoanFirst")
	public String rePayLoanFirst(@Validated RepayVo vo,HttpSession session){
		CallResult<LoanRepayBean> countResult =  new CallResult<>();
		try {
			CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
			if(!ddResult.isExec()){
				return gson.toJson(ddResult);
			}
			DingDingUserInfo dingDingUserInfo = ddResult.getData();
			logger.info("dingDingUserInfo：",dingDingUserInfo.toString());
			HB_User hb_user = new HB_User();
			hb_user.setName(dingDingUserInfo.getHbUserId());
			hb_user.setShowName(dingDingUserInfo.getName());
			countResult = loanRepayService.rePayLoanFirst(vo,hb_user);
		} catch (Exception e) {
			e.printStackTrace();
			countResult.setFailResult("操作异常");
		}
		return  gson.toJson(countResult);
	}

	@RequestMapping("dingDingLoanRepayPagingData")
	public String dingDingLoanRepayPagingData(LoanRepayBean loanRepayBean, HttpSession session){
		Map<String, Object> map = new HashMap<>();
		try {

			CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
			if(!ddResult.isExec()){
				return gson.toJson(ddResult);
			}
			loanRepayBean.setCheckFirst(ddResult.getData().getHbUserId());
			if(StringUtils.isNotBlank(loanRepayBean.getName())){
				loanRepayBean.setName(V1Pass.pass_encode(loanRepayBean.getName()));
			}
			if(StringUtils.isNotBlank(loanRepayBean.getIdNum())){
				loanRepayBean.setIdNum(V1Pass.pass_encode(loanRepayBean.getIdNum()));
			}
			if(StringUtils.isNotBlank(loanRepayBean.getMobile())){
				loanRepayBean.setMobile(V1Pass.pass_encode(loanRepayBean.getMobile()));
			}
			map = loanRepayBeanDaoService.getPagingData(loanRepayBean);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code",400);
			map.put("msg","操作异常");
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
