package com.hbsoft.csms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbsoft.csms.dao.ProcessDetailBeanDao;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.util.DateUtil;
import com.hb.util.V1Pass;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.bean.LoanInfoUpdateContent;
import com.hbsoft.csms.dao.ProcessHandleDao;
import com.hbsoft.csms.vo.LoanInfoVo;

/**
 * ClassName:    ProcessService
 * Package:    com.hbsoft.csms.service
 * Description:
 * Datetime:    2020/4/11   10:50
 * Author:  hwl
 */
@Service
public class ProcessService {
	 
	@Autowired
	ProcessHandleDao processHandleDao;

	@Autowired
	ProcessDetailBeanDao processDetailBeanDao;


	/**
	* @author hwl
	* @description 进行中的列表 未审核  已退回
	* @date 2020/5/15
	*/
	public Map<String, Object> findProcessBusinessPagingData(LoanInfoVo vo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(vo.getUserId())){
			map.put("code",400);
			map.put("msg","操作人员有误");
			return map;
		}

		Integer count = processHandleDao.findProcessBusinessPagingCount(vo);
		List<LoanInfoVo> list = processHandleDao.findProcessBusinessPagingData(vo);
		for(LoanInfoVo loanInfoBean1 : list){
			loanInfoBean1.setIdNum(V1Pass.pass_decode(loanInfoBean1.getIdNum()));
			loanInfoBean1.setName(V1Pass.pass_decode(loanInfoBean1.getName()));
			loanInfoBean1.setMobile(V1Pass.pass_decode(loanInfoBean1.getMobile()));
			if(loanInfoBean1.getLoanBeginDate() != null){
				loanInfoBean1.setLoanBeginDateStr(DateUtil.formatDate(loanInfoBean1.getLoanBeginDate(),"yyyy-MM-dd"));
			}
			if(loanInfoBean1.getLoanEndDate() != null){
				loanInfoBean1.setLoanEndDateStr(DateUtil.formatDate(loanInfoBean1.getLoanEndDate(),"yyyy-MM-dd"));
			}
		}
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}

	/**
	* @author hwl
	* @description 操作过的列表
	* @date 2020/5/15
	*/
	public Map<String, Object> findProcessBusinessOperatePagingData(LoanInfoVo vo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> nodeMap = processDetailBeanDao.findMaxNodeAndMinNodeByNodeType(vo.getProcessAttribute());
		if(ObjectUtils.isEmpty(nodeMap)){
			map.put("code", 400);
			map.put("msg", "参数有误");
		}
		vo.setNotNodeId(String.valueOf(nodeMap.get("minNode")));
		Integer count = processHandleDao.findProcessBusinessOperatePagingCount(vo);
		List<LoanInfoVo> list = processHandleDao.findProcessBusinessOperatePagingData(vo);
		for(LoanInfoVo loanInfoBean1 : list){
			loanInfoBean1.setIdNum(V1Pass.pass_decode(loanInfoBean1.getIdNum()));
			loanInfoBean1.setName(V1Pass.pass_decode(loanInfoBean1.getName()));
			loanInfoBean1.setMobile(V1Pass.pass_decode(loanInfoBean1.getMobile()));
			if(loanInfoBean1.getLoanBeginDate() != null){
				loanInfoBean1.setLoanBeginDateStr(DateUtil.formatDate(loanInfoBean1.getLoanBeginDate(),"yyyy-MM-dd"));
			}
			if(loanInfoBean1.getLoanEndDate() != null){
				loanInfoBean1.setLoanEndDateStr(DateUtil.formatDate(loanInfoBean1.getLoanEndDate(),"yyyy-MM-dd"));
			}
		}
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}

	public Map<String, Object> findMyProcessBusinessPagingData (LoanInfoVo vo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = processHandleDao.findMyProcessBusinessPagingCount(vo);
		List<LoanInfoVo> list = processHandleDao.findMyProcessBusinessPagingData(vo);
		for(LoanInfoVo loanInfoBean1 : list){
			loanInfoBean1.setIdNum(V1Pass.pass_decode(loanInfoBean1.getIdNum()));
			loanInfoBean1.setName(V1Pass.pass_decode(loanInfoBean1.getName()));
			loanInfoBean1.setMobile(V1Pass.pass_decode(loanInfoBean1.getMobile()));
			if(loanInfoBean1.getLoanBeginDate() != null){
				loanInfoBean1.setLoanBeginDateStr(DateUtil.formatDate(loanInfoBean1.getLoanBeginDate(),"yyyy-MM-dd"));
			}
			if(loanInfoBean1.getLoanEndDate() != null){
				loanInfoBean1.setLoanEndDateStr(DateUtil.formatDate(loanInfoBean1.getLoanEndDate(),"yyyy-MM-dd"));
			}
		}
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
}
