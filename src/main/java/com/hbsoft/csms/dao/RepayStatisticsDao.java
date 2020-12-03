package com.hbsoft.csms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hbsoft.csms.vo.RepayStatisticsVo;

@Mapper
public interface RepayStatisticsDao {

	//按月 统计总还款人次
	String getRepayPeople(RepayStatisticsVo vo);
	
	//按月统计总还款人数
	String getRepayPeopleNum(RepayStatisticsVo vo);
	
	//按月 统计总还款金额
	String getRepayMoneySum(RepayStatisticsVo vo);
	
	//按月 统计各网点还款人次及金额
	List<Map<String,Object>> getRepayNetpointNum(RepayStatisticsVo vo);
	
	//按月 统计网点总数
	Integer getRepayNetpointCount(RepayStatisticsVo vo);
	
//	通过客户经理/组id 统计还款人次及还款金额
	Map<String,Object> getRepayManagerNum(@Param("id")String id,@Param("date")String date);
	
	//通过客户经理/组id 统计还款人数及还款金额
	Integer  getRepayManagerPeopleNum(@Param("id")String id,@Param("date")String date);
	

	

}
