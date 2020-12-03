package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum LoanTypeEnum {

	LOAN_TYPE_DB("1", "打包"),
	LOAN_TYPE_HX("2","核销"),
	LOAN_TYPE_ZH("3","置换");

	private static Map<String, LoanTypeEnum> nameMap = new HashMap<String, LoanTypeEnum>(10);
	private static Map<String, LoanTypeEnum> codeMap = new HashMap<String, LoanTypeEnum>(10);

	static {
		LoanTypeEnum[] allValues = LoanTypeEnum.values();
		for (LoanTypeEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private LoanTypeEnum(String code, String name) {
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

	public static LoanTypeEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static LoanTypeEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
