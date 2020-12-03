package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum ProcessBusinessEnum {

	PROCESS_BUSINESS_LOANINFO_UPDATE("loanInfo_update", "信息修改"),
	PROCESS_BUSINESS_REPAY_ADD("repay_add", "还款补录"),
	PROCESS_BUSINESS_REPAY_REVOKE("repay_revoke", "还款抹账"),
	PROCESS_BUSINESS_REPAY_PRINT("repay_print", "还款补打"),
	PROCESS_BUSINESS_CLAIM("loan_claim","认领");


	private static Map<String, ProcessBusinessEnum> nameMap = new HashMap<String, ProcessBusinessEnum>(10);
	private static Map<String, ProcessBusinessEnum> codeMap = new HashMap<String, ProcessBusinessEnum>(10);

	static {
		ProcessBusinessEnum[] allValues = ProcessBusinessEnum.values();
		for (ProcessBusinessEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private ProcessBusinessEnum(String code, String name) {
		this.name = name;
		this.code = code;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static ProcessBusinessEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static ProcessBusinessEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
