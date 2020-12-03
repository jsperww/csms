package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum LoanStatusEnum {

	LOAN_STATUS_PROSECUTION("1", "起诉"),
	LOAN_STATUS_DEATH ("2","死亡"),
	LOAN_STATUS_SETTLE("3","结清");

	private static Map<String, LoanStatusEnum> nameMap = new HashMap<String, LoanStatusEnum>(10);
	private static Map<String, LoanStatusEnum> codeMap = new HashMap<String, LoanStatusEnum>(10);

	static {
		LoanStatusEnum[] allValues = LoanStatusEnum.values();
		for (LoanStatusEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private LoanStatusEnum(String code, String name) {
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

	public static LoanStatusEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static LoanStatusEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
