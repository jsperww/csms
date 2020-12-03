package com.hbsoft.csms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hbsoft.csms.vo.RepayStatisticsVo;

@Mapper
public interface DistributeStatisticsDao {

	Integer getDistributeNumById(@Param("id")String id,@Param("type")String type);
	
	Integer getCustomerAll();
	

	

}
