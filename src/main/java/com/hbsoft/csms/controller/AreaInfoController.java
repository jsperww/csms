package com.hbsoft.csms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hb.bean.hbcmsql.HB_User;
import com.hbsoft.csms.service.SessionService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hb.util.StringUtil;
import com.hbsoft.csms.bean.AreaInfoBean;
import com.hbsoft.csms.dao.service.AreaInfoBeanDaoService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class AreaInfoController extends ABaseController {

	@Autowired
	AreaInfoBeanDaoService areaInfoBeanDaoService;
	@Autowired
	SessionService sessionService;

	/**
	 * 获取区域树结构异步
	 * @param pId
	 * @return
	 */
	@PostMapping("selectAreaTree")
	@ResponseBody
	public String selectAreaTree(String pId) {
		CallResult<List<Map<String, Object>>> result = new CallResult<>();
		try {
			if (isEmpty(pId)) {
				List<Map<String, Object>> fatherList = areaInfoBeanDaoService.selectMin();
				for (Map<String, Object> map : fatherList) {
					List<Map<String, Object>> selectSon = areaInfoBeanDaoService.selectSon((String) map.get("id"));
					if (null == selectSon || selectSon.size() == 0) {
						map.put("isParent", false);
					}else{
						map.put("isParent", true);
					}
				}
				result.setSuccessResult(fatherList);
			} else {
				List<Map<String, Object>> sonList = areaInfoBeanDaoService.selectSon(pId);
				if (null != sonList && sonList.size() != 0) {
					for (Map<String, Object> sonMap : sonList) {
						Boolean isParent = areaInfoBeanDaoService.isParent((String) sonMap.get("id"));
						sonMap.put("isParent", isParent);
					}
				}
				result.setSuccessResult(sonList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 获取区域树结构同步
	 * @param
	 * @return
	 */
	@PostMapping("selectAreaTree2")
	@ResponseBody
	public String selectAreaTree2() {
		CallResult<List<Map<String, Object>>> result = new CallResult<>();
		try {
			List<Map<String, Object>> treeAll = areaInfoBeanDaoService.getTreeAll(null);
			for(Map<String,Object>map:treeAll){
				String id = (String)map.get("id");
				if(id.length()==6){
					map.put("pId", "");
					map.put("isParent",true);
				}
				if(id.length()==9){
					map.put("isParent",true);
				}
				if(id.length()==12){
					map.put("isParent",false);
				}
			}
			result.setSuccessResult(treeAll);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
	/**
	 * 区域树结构添加节点
	 * @param id
	 * @param pId
	 * @param name
	 * @return
	 */
	@PostMapping("addArea")
	public String addArea(String id, String pId, String name, HttpSession session) {
		CallResult<String> result = new CallResult<>();
		AreaInfoBean area = new AreaInfoBean();
		if (isEmpty(name)) {
			result.setFailResult("名称不能为空");
			return gson.toJson(result);
		}
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}

			String maxCode = areaInfoBeanDaoService.getTreeNextMax(pId);
			String newCode = "";
			if (isEmpty(maxCode)) {
				if (isEmpty(id)) {
					newCode = pId + "001";
				} else {
					newCode = id;
				}
			} else {
				newCode = StringUtil.getNextCode(maxCode.length(), maxCode);
			}
			Date date = new Date();
			area.setName(name);
			List<AreaInfoBean> list = areaInfoBeanDaoService.getAll(area);
			if(list!=null&&list.size()!=0){
				result.setFailResult("区域名称重复");
				return gson.toJson(result);
			}
			area.setCode(newCode);
			area.setCreateBy(hb_user.getName());
			area.setCreateOn(date);
			area.setUpdateBy(hb_user.getName());
			area.setUpdateOn(date);
			areaInfoBeanDaoService.add(area);
			result.setSuccessResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 区域树结构修改节点
	 * @param id
	 * @param name
	 * @return
	 */
	@PostMapping("updateArea")
	public String updateArea(String id, String name,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		AreaInfoBean area = new AreaInfoBean();
		AreaInfoBean area2 = new AreaInfoBean();
		if (isEmpty(name)) {
			result.setFailResult("名称不能为空");
			return gson.toJson(result);
		}
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			area2.setName(name);
			List<AreaInfoBean> list = areaInfoBeanDaoService.getAll(area2);
			if(list!=null&&list.size()!=0){
				result.setFailResult("区域名称重复");
				return gson.toJson(result);
			}
			area.setCode(id);
			AreaInfoBean byField = areaInfoBeanDaoService.getByField(area);
			if (null == byField) {
				result.setFailResult("id有误");
				return gson.toJson(result);
			}
			byField.setName(name);
			byField.setUpdateBy(hb_user.getName());
			byField.setUpdateOn(new Date());
			areaInfoBeanDaoService.set(byField);
			result.setSuccessResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	
	/**
	 * 区域树结构删除节点
	 * 
	 */
	@PostMapping("delArea")
	public String delArea(String id) {
		CallResult<String> result = new CallResult<>();
		AreaInfoBean area = new AreaInfoBean();
		try {
			area.setCode(id);
			AreaInfoBean byField = areaInfoBeanDaoService.getByField(area);
			if (null == byField) {
				result.setFailResult("id有误");
				return gson.toJson(result);
			}
			areaInfoBeanDaoService.delArea(id);
			result.setSuccessResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
	
	/**
	 * 获取区域下拉列表
	 * @param code
	 * @return
	 */
	@PostMapping("selectAreaPull")
	public String selectAreaPull(String code){
		CallResult<List<Map<String, Object>>> result = new CallResult<>();
		List<Map<String,Object>> list = new ArrayList<>();
		try {
			if(isEmpty(code)){
				list = areaInfoBeanDaoService.findCounty();
			}else{
				list = areaInfoBeanDaoService.findTownVillage(code);
			}
			result.setSuccessResult(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

}
