package com.hbsoft.csms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommonDao {

	//获取k_process表中最大code
	String getMaxCode();
	//获取k_process_detail表中最大code
	Integer getMaxCode2(@Param("nodeType") String nodeType);
	
	List<String> getAreaCodeByDistributeArea(@Param("organizationNum")String organizationNum);
	
	List<String> getBankCodeByDistributeBank(@Param("organizationNum")String organizationNum);

	String getHbDeptIdByLoanInfoId(@Param("id_prikey")Integer id_prikey);

	
}
