package com.hbsoft.csms.vo;

import com.hb.bean.APojo;

import lombok.Data;

@Data
public class RepayStatisticsVo extends APojo{

	private String date;
	
	private String deptCode;
	
	private String deptName;

	private String beginDate;

	private String endDate;
}
