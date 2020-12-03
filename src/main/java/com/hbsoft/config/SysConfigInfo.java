package com.hbsoft.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Setter
@Getter
public class SysConfigInfo {

	@Value("${hb.taskFlag:false}")
	private boolean taskFlag;

	public static String uploadPath;

	@Value("${hb.uploadPath:}")
	public void setUploadPath(String uploadPath) {
		SysConfigInfo.uploadPath = uploadPath;
	}
	
	@Value("${hb.loan_dept:}")
	private String loanDept;

	@Value("${hb.dingDing.agentId}")
	private String ddAgentId;

	@Value("${hb.dingDing.appKey}")
	private String ddAppKey;

	@Value("${hb.dingDing.appSecret}")
	private String ddAppSecret;

	@Value("${hb.dingDing.corpId}")
	private String ddCorpId;

	@Value("${hb.loanBank}")
	private String loanBank;
}
