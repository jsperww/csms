package com.hbsoft.csms.controller;

import java.util.Date;
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
import com.hbsoft.csms.bean.MesageInfoBean;
import com.hbsoft.csms.dao.MesageInfoBeanDao;
import com.hbsoft.csms.dao.service.MesageInfoBeanDaoService;

import javax.servlet.http.HttpSession;

@RestController
public class MesageInfoController extends ABaseController {

	@Autowired
	MesageInfoBeanDaoService mesageInfoBeanDaoService;
	@Autowired
	MesageInfoBeanDao mesageInfoBeanDao;
	@Autowired
	SessionService sessionService;

	/**
	 * 添加信息
	 * 
	 * @param mib
	 * @return
	 */
	@PostMapping("addMesage")
	public String addMesage(MesageInfoBean mib, HttpSession session) {
		CallResult<String> result = new CallResult<>();
		HB_User hb_user = sessionService.getHbUserBySession(session);;
		if(ObjectUtils.isEmpty(hb_user)){
			result.setFailResult("请重新登录");
			return gson.toJson(result);
		}
		if (isEmpty(mib.getContent())) {
			result.setFailResult("内容不能为空");
			return gson.toJson(result);
		}
		try {
			Date date = new Date();
			mib.setCreateBy(hb_user.getName());
			mib.setCreateOn(date);
			mib.setUpdateBy(hb_user.getName());
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
	 * 通过主键删除信息
	 * 
	 * @param id_prikey
	 * @return
	 */
	@PostMapping("delMesageById")
	public String delMesageById(Integer id_prikey) {
		CallResult<String> result = new CallResult<>();
		if (id_prikey == null) {
			result.setFailResult("主键不能为空");
			return gson.toJson(result);
		}
		try {
			MesageInfoBean byId = mesageInfoBeanDaoService.getById(id_prikey);
			if (byId == null) {
				result.setFailResult("参数有误");
				return gson.toJson(result);
			}
			mesageInfoBeanDaoService.removeOne(id_prikey);
			result.setSuccessResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 修改信息
	 * 
	 * @param
	 * @return
	 */
	@PostMapping("updateMesageById")
	public String updateMesageById(MesageInfoBean mib,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		HB_User hb_user = sessionService.getHbUserBySession(session);;
		if(ObjectUtils.isEmpty(hb_user)){
			result.setFailResult("请重新登录");
			return gson.toJson(result);
		}
		if (mib.getId_prikey() == null) {
			result.setFailResult("主键不能为空");
			return gson.toJson(result);
		}
		if (isEmpty(mib.getContent())) {
			result.setFailResult("内容不能为空");
			return gson.toJson(result);
		}
		try {
			Date date = new Date();
			mib.setUpdateBy(hb_user.getName());
			mib.setUpdateOn(date);
			mesageInfoBeanDaoService.set(mib);
			result.setSuccessResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 通过主键获取单条信息
	 * 
	 * @param id_prikey
	 * @return
	 */
	@PostMapping("getMesageById")
	public String getMesageById(Integer id_prikey) {
		CallResult<MesageInfoBean> result = new CallResult<>();
		if (id_prikey == null) {
			result.setFailResult("主键不能为空");
			return gson.toJson(result);
		}
		try {
			MesageInfoBean byId = mesageInfoBeanDaoService.getById(id_prikey);
			result.setSuccessResult(byId);
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
	@PostMapping("getMesagePage")
	public String getMesagePage(MesageInfoBean mib, Integer flag,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			if(flag == null){
				map.put("code", 400);
				map.put("message", "flag参数有误");
				return gson.toJson(map);
			}
			if (flag == 0) {
				map = mesageInfoBeanDaoService.getPagingData(mib);
			} else if (flag == 1) {
				mib.setCreateBy(hb_user.getName());
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
	
	@PostMapping("getMessageCount")
	public String getMessageCount(HttpSession session){
		CallResult<Map<String,Object>> result = new CallResult<>();
		Map<String,Object> map = new HashMap<>();
		MesageInfoBean mesageInfoBean = new MesageInfoBean();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			Integer all = mesageInfoBeanDao.findPagingCount(mesageInfoBean);
			mesageInfoBean.setCreateBy(hb_user.getName());
			Integer my = mesageInfoBeanDao.findPagingCount(mesageInfoBean);
			map.put("all", all);
			map.put("my", my);
			result.setSuccessResult(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
}
