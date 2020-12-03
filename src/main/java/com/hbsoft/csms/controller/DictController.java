package com.hbsoft.csms.controller;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hb.util.StringUtil;
import com.hbsoft.csms.bean.DictBean;
import com.hbsoft.csms.dao.DictBeanDao;
import com.hbsoft.csms.dao.service.DictBeanDaoService;
import com.hbsoft.csms.service.DictService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DictController Package: com.hbsoft.csms.controller Description:
 * Datetime: 2020/3/28 15:41
 * Author: hwl
 */
@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class DictController extends ABaseController {

	@Autowired
	DictBeanDaoService dictBeanDaoService;

	@Autowired
	DictService dictService;

	@Autowired
	DictBeanDao dictBeanDao;

	@RequestMapping("dictListPage")
	public String dictListPage(DictBean dictBean) {
		Map<String, Object> map = new HashMap<>();
		try {
			map = dictBeanDaoService.getPagingData(dictBean);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 400);
			map.put("msg", "操作异常");
		}
		return gson.toJson(map);
	}

	@RequestMapping("getDictDetail")
	public String getDictDetail(String detailDictName) {
		CallResult<List<DictBean>> result = new CallResult<>();
		try {
			if (StringUtil.sqlVer(detailDictName)) {
				result.setFailResult("参数错误");
				return gson.toJson(result);
			}
			List<DictBean> list = dictBeanDao.selectDictTableContent(detailDictName);
			result.setSuccessResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailResult();
		}
		return gson.toJson(result);
	}

	@PostMapping("addDict")
	public String addDict(@Validated DictBean dictBean) {
		CallResult<String> result = new CallResult<>();
		try {
			if (StringUtil.sqlVer(dictBean.getCode())) {
				result.setFailResult("参数错误");
				return gson.toJson(result);
			}
			DictBean param = new DictBean();
			param.setCode(dictBean.getCode());
			DictBean dict = dictBeanDaoService.getByField(param);
			if (ObjectUtils.isEmpty(dict)) {
				result = dictService.addDict(dictBean);
			} else {
				result.setFailResult("字典表已存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailResult();
		}
		return gson.toJson(result);
	}

	@PostMapping("addDictDetail")
	public String addDictDetail(@Validated DictBean dictBean) {
		CallResult<String> result = new CallResult<>();
		try {
			if (StringUtil.sqlVer(dictBean.getDetailDictName())) {
				result.setFailResult("参数错误");
				return gson.toJson(result);
			}
			DictBean dict = dictBeanDao.selectDictDetailByCode(dictBean.getDetailDictName(), dictBean.getCode());
			if (ObjectUtils.isEmpty(dict)) {
				dictBeanDao.insertDictTable(dictBean);
				result.setSuccessResult();
			} else {
				result.setFailResult("字典项已存在");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	@PostMapping("updateDictDetail")
	public String updateDictDetail(DictBean dictBean) {
		CallResult<String> result = new CallResult<>();
		try {
			if (StringUtil.sqlVer(dictBean.getDetailDictName())) {
				result.setFailResult("参数错误");
				return gson.toJson(result);
			}
			if (ObjectUtils.isEmpty(dictBean.getId_prikey())) {
				result.setFailResult("主键参数有误");
			} else {
				dictBeanDao.updateDictTable(dictBean);
				result.setSuccessResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
	
	@PostMapping("delDictDetail")
	public String delDictDetail(DictBean dictBean) {
		CallResult<String> result = new CallResult<>();
		try {
			if(isEmpty(dictBean.getDetailDictName())){
				result.setFailResult("表名不能为空");
				return gson.toJson(result);
			}
			if (ObjectUtils.isEmpty(dictBean.getId_prikey())) {
				result.setFailResult("主键参数有误");
			} 
			if (StringUtil.sqlVer(dictBean.getDetailDictName())) {
				result.setFailResult("参数错误");
				return gson.toJson(result);
			}
			DictBean db = new DictBean();
			db.setCode(dictBean.getDetailDictName());
			DictBean db2 = dictBeanDaoService.getByField(db);
			if(db2==null){
				result.setFailResult("不存在此表");
				return gson.toJson(result);
			}
			dictBeanDao.delDictTable(dictBean);
			result.setSuccessResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
}
