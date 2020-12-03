package com.hbsoft.csms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsoft.csms.dao.RepayStatisticsDao;
import com.hbsoft.csms.vo.RepayStatisticsVo;

@Service
public class RepayStatisticsService {

	@Autowired
	RepayStatisticsDao repayStatisticsDao;
	
	public String getRepayPeople(RepayStatisticsVo vo) throws Exception{
		return repayStatisticsDao.getRepayPeople(vo);
	}
	
	public String getRepayPeopleNum(RepayStatisticsVo vo) throws Exception{
		return repayStatisticsDao.getRepayPeopleNum(vo);
	}
	
	public String getRepayMoneySum(RepayStatisticsVo vo) throws Exception{
		return repayStatisticsDao.getRepayMoneySum(vo);
	}
	
	public Map<String,Object> getRepayNetpointNum(RepayStatisticsVo vo) throws Exception{
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list = repayStatisticsDao.getRepayNetpointNum(vo);
		Integer count = repayStatisticsDao.getRepayNetpointCount(vo);
		map.put("code",0);
		map.put("data", list);
		map.put("count", count);
		return map;
	}
	
	public Map<String,Object> getRepayManagerNum(String id,String date)throws Exception{
		return repayStatisticsDao.getRepayManagerNum(id,date);
	}
	
	public Integer getRepayManagerPeopleNum(String id,String date)throws Exception{
		return repayStatisticsDao.getRepayManagerPeopleNum(id, date);
	}
	
}
