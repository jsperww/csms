package com.hbsoft.csms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.controller.ABaseController;
import com.hbsoft.csms.dao.service.LoanVisitBeanDaoService;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class VisitController extends ABaseController{

	@Autowired
	LoanVisitBeanDaoService loanVisitBeanDaoService;
	
	
}
