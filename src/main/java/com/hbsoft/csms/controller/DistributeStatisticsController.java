package com.hbsoft.csms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hb.bean.hbcmsql.HB_User;
import com.hbsoft.csms.dao.LoanReportDao;
import com.hbsoft.csms.service.SessionService;
import com.hbsoft.csms.vo.LoanReportVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.service.DeptUserService;
import com.hbsoft.csms.service.DistributeStatisticsService;

import javax.servlet.http.HttpSession;

@RestController
public class DistributeStatisticsController extends ABaseController {
	@Autowired
	DistributeStatisticsService distributeStatisticsService;
	@Autowired
	DeptUserService deptUserService;
	@Autowired
	LoanReportDao loanReportDao;
	@Autowired
	SessionService sessionService;


	
	@PostMapping("distributeNumById")
	public String distributeNumById(HttpSession session){
		CallResult<Map<String,Object>> result = new CallResult<>();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code",400);
				map.put("msg","请重新登录");
				return gson.toJson(map);
			}
			String groupId = deptUserService.selectDeptByUserId(hb_user.getName());
			Integer managerNum = distributeStatisticsService.getDistributeNumById(hb_user.getName(), "1");
			//Integer groupNum = distributeStatisticsService.getDistributeNumById(groupId, "2");
			LoanReportVo vo = new LoanReportVo();
			vo.setDeptId(groupId);
			Map<String,Object> groupNumMap = loanReportDao.loanInfoReportCountByTeam(vo);
			Integer groupNum = 0;
			if(ObjectUtils.isNotEmpty(groupNumMap)){
				groupNum = Integer.valueOf(String.valueOf(groupNumMap.get("cusNum")));
			}
			Integer customerAll = distributeStatisticsService.getCustomerAll();
			map.put("manager", managerNum);
			map.put("group", groupNum);
			map.put("customerAll", customerAll);
			result.setSuccessResult(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
}
