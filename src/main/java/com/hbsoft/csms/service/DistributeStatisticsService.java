package com.hbsoft.csms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsoft.csms.dao.DistributeStatisticsDao;

@Service
public class DistributeStatisticsService {

	@Autowired
	DistributeStatisticsDao distributeStatisticsDao;
	
	public Integer getDistributeNumById(String id,String type) throws Exception{
		return distributeStatisticsDao.getDistributeNumById(id, type);
	}
	
	public Integer getCustomerAll()throws Exception{
		return distributeStatisticsDao.getCustomerAll();
	}
	
	
}
