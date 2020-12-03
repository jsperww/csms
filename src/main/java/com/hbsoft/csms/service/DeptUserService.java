package com.hbsoft.csms.service;

import com.hbsoft.csms.dao.DeptUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeptUserService {

	@Autowired
	DeptUserDao deptUserDao;
	
	public Map<String, Object> selectDeptById(String id) throws Exception{
		return deptUserDao.selectDeptById(id);
	}
	
	public List<Map<String, Object>> selectDeptSon(String pId) throws Exception{
		 return deptUserDao.selectDeptSon(pId);
	}
	
	public List<Map<String, Object>> selectDeptUser(String id) throws Exception{
		return deptUserDao.selectDeptUser(id);
	}
	
	public List<Map<String, Object>> selectDeptAllUser(String id) throws Exception{
		return deptUserDao.selectDeptAllUser(id);
	}
	
	public String selectDeptByUserId(String id) throws Exception{
		return deptUserDao.selectDeptByUserId(id);
	}

    public Map<String, Object> selectUserByUserId(String userid) throws Exception{
		return deptUserDao.selectUserByUserId(userid);
    }

    public List<Map<String, Object>> selectDept(String pId) throws Exception{
		return deptUserDao.selectDept(pId);
    }
}
