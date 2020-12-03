package com.hbsoft.csms.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeptUserDao {
	
	Map<String, Object> selectDeptById(@Param("id") String id) throws Exception;
	
	List<Map<String, Object>> selectDeptSon(@Param("pId") String pId) throws Exception;

	List<Map<String, Object>> selectDeptUser(@Param("id") String id) throws Exception;
	
	List<Map<String, Object>> selectDeptAllUser(@Param("id") String id) throws Exception;
	
	String selectDeptByUserId(@Param("id") String id) throws Exception;

    Map<String, Object> selectUserByUserId(@Param("id") String id) throws Exception;

    List<Map<String, Object>> selectDept(@Param("pId") String pId) throws Exception;
}
