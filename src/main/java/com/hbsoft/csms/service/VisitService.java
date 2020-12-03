package com.hbsoft.csms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsoft.csms.dao.service.LoanVisitBeanDaoService;

@Service
public class VisitService {

	@Autowired
	LoanVisitBeanDaoService loanVisitBeanDaoService;
}
