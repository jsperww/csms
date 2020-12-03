package com.hbsoft.csms.controller;

import java.util.HashMap;
import java.util.Map;

import com.hb.bean.hbcmsql.HB_User;
import com.hbsoft.csms.service.SessionService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.service.DeptUserService;
import com.hbsoft.csms.service.RepayStatisticsService;
import com.hbsoft.csms.vo.RepayStatisticsVo;

import javax.servlet.http.HttpSession;

@RestController
public class RepayStatisticsController extends ABaseController{

	@Autowired
	RepayStatisticsService repayStatisticsService;
	@Autowired
	DeptUserService deptUserService;
	@Autowired
	SessionService sessionService;
	
	@PostMapping("repayGeneralPeopleAndMoney")
	public String repayGeneralPeopleAndMoney(RepayStatisticsVo vo){
		CallResult<Map<String,Object>> result = new CallResult<>();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String money = repayStatisticsService.getRepayMoneySum(vo);
			String people = repayStatisticsService.getRepayPeople(vo);
			String peopleNum = repayStatisticsService.getRepayPeopleNum(vo);
			map.put("money", money);
			map.put("people", people);
			map.put("peopleNum", peopleNum);
			result.setSuccessResult(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
	
	@PostMapping("repayNetpointNum")
	public String repayNetpointPeopleNum(RepayStatisticsVo vo){
		Map<String,Object>map = new HashMap<>();
		try {
			map = repayStatisticsService.getRepayNetpointNum(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	@PostMapping("repayNumByManager")
	public String repayNumByManager(String date, HttpSession session){
		CallResult<Map<String,Object>> result = new CallResult<>();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			String groupId = deptUserService.selectDeptByUserId(hb_user.getName());
			Map<String, Object> managerMap = repayStatisticsService.getRepayManagerNum(hb_user.getName(),date);
			Integer managerPeople = repayStatisticsService.getRepayManagerPeopleNum(hb_user.getName(), date);
			managerMap.put("peopleNum", managerPeople);
			Map<String, Object> groupMap = repayStatisticsService.getRepayManagerNum(groupId,date);
			Integer groupPeople = repayStatisticsService.getRepayManagerPeopleNum(groupId, date);
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
}
