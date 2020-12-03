package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum LoanFileTypeEnum {

	LOAN_FILE_TYPE_VISIT("1", "回访"),
	LOAN_FILE_TYPE_TEMPLATE("2","模板"),
	LOAN_FILE_TYPE_UPDATE("3","修改"),
	LOAN_FILE_TYPE_INDICTMENT("4", "诉讼"),
	LOAN_FILE_TYPE_INDICTMENT_MEDIATION("5", "调解"),
	LOAN_FILE_TYPE_INDICTMENT_COURT("6", "开庭"),
	LOAN_FILE_TYPE_INDICTMENT_EXECUTION("7", "执行"),
	LOAN_FILE_TYPE_ADDR_HOME("8","家庭"),
	LOAN_FILE_TYPE_ADDR_COMPANY("9","单位"),
	LOAN_FILE_TYPE_ADDR_OTHER("10","其他");


	private static Map<String, LoanFileTypeEnum> nameMap = new HashMap<String, LoanFileTypeEnum>(10);
	private static Map<String, LoanFileTypeEnum> codeMap = new HashMap<String, LoanFileTypeEnum>(10);

	static {
		LoanFileTypeEnum[] allValues = LoanFileTypeEnum.values();
		for (LoanFileTypeEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private LoanFileTypeEnum(String code, String name) {
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

	public static LoanFileTypeEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static LoanFileTypeEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
