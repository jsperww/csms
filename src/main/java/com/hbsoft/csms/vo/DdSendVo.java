package com.hbsoft.csms.vo;

import lombok.Data;

@Data
public class DdSendVo {

	Integer agent_id;
	
	String userid_list;
	
	DdOAMsgVo msg;
}
