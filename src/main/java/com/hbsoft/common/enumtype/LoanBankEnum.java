package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum LoanBankEnum {

	LOAN_BANK_463_ENUM("463", "漳泽"),
	LOAN_BANK_454_ENUM("454", "长子");


	private static Map<String, LoanBankEnum> nameMap = new HashMap<String, LoanBankEnum>(10);
	private static Map<String, LoanBankEnum> codeMap = new HashMap<String, LoanBankEnum>(10);

	static {
		LoanBankEnum[] allValues = LoanBankEnum.values();
		for (LoanBankEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private LoanBankEnum(String code, String name) {
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

	public static LoanBankEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static LoanBankEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
