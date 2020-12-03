package com.hbsoft.csms.controller;

import com.hb.annotation.LoginNotRequired;
import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hb.controller.ABaseController;
import com.hbsoft.config.SysConfigInfo;
import com.hbsoft.csms.service.DeptUserService;
import com.hbsoft.csms.service.SessionService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class DeptUserController extends ABaseController {

	@Autowired
	DeptUserService deptUserService;
	@Autowired
	SysConfigInfo sysConfigInfo;
	@Autowired
	SessionService sessionService;

	@PostMapping("selectDeptTree")
	public String selectDeptTree(String pId) {
		CallResult<List<Map<String, Object>>> result = new CallResult<>();
		try {
			if (isEmpty(pId)) {
				Map<String, Object> deptFather = deptUserService.selectDeptById(sysConfigInfo.getLoanDept());
				String id = (String) deptFather.get("id");
				List<Map<String, Object>> deptSon = deptUserService.selectDeptSon(id);
				if (deptSon != null && deptSon.size() != 0) {
					deptFather.put("isParent", true);
				} else {
					deptFather.put("isParent", false);
				}
				List<Map<String, Object>> list = new ArrayList<>();
				list.add(deptFather);
				result.setSuccessResult(list);
			} else {
				List<Map<String, Object>> list = deptUserService.selectDeptSon(pId);
				for (Map<String, Object> map : list) {
					List<Map<String, Object>> deptSon = deptUserService.selectDeptSon((String) map.get("id"));
					if (deptSon != null && deptSon.size() != 0) {
						map.put("isParent", true);
					} else {
						map.put("isParent", false);
					}
				}
				result.setSuccessResult(list);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 获取部门树
	 * @param pId
	 * @return
	 */
	@LoginNotRequired
	@PostMapping("selectDeptTreeZz")
	public String selectDeptTreeZz(String pId) {
        CallResult<List<Map<String, Object>>> result = new CallResult<>();
		try {
			if (isEmpty(pId)) {
				List<Map<String, Object>> deptFathers = deptUserService.selectDept(pId);
				for (Map<String, Object> deptFather : deptFathers) {
					String id = (String) deptFather.get("id");
					List<Map<String, Object>> deptSon = deptUserService.selectDeptSon(id);
					if (deptSon != null && deptSon.size() != 0) {
						deptFather.put("isParent", true);
					} else {
						deptFather.put("isParent", false);
					}
					result.setSuccessResult(deptFathers);
				}
			} else {
				List<Map<String, Object>> list = deptUserService.selectDeptSon(pId);
				for (Map<String, Object> map : list) {
					List<Map<String, Object>> deptSon = deptUserService.selectDeptSon((String) map.get("id"));
					if (deptSon != null && deptSon.size() != 0) {
						map.put("isParent", true);
					} else {
						map.put("isParent", false);
					}
				}
                result.setSuccessResult(list);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
            result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	@PostMapping("selectDeptUser")
	public String selectDeptUser(String id) {
		CallResult<List<Map<String, Object>>> result = new CallResult<>();
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			if (isEmpty(id)) {
				list = deptUserService.selectDeptAllUser(sysConfigInfo.getLoanDept());
			} else {
				list = deptUserService.selectDeptUser(id);
			}
			result.setSuccessResult(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	@PostMapping("selectMyDeptUser")
	public String selectMyDeptUser(HttpSession session) {
		CallResult<List<Map<String, Object>>> result = new CallResult<>();
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			list = deptUserService.selectDeptUser(hb_user.getDeptId());
			result.setSuccessResult(list);
		} catch (Exception e) {
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
}
