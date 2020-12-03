package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum LoanClaimEnum {

	LOAN_CLAIM_NO_ENUM("1", "未认领"),
	LOAN_CLAIM_DOING_ENUM("1", "认领中"),
	LOAN_CLAIM_YES_ENUM("2","已认领");


	private static Map<String, LoanClaimEnum> nameMap = new HashMap<String, LoanClaimEnum>(10);
	private static Map<String, LoanClaimEnum> codeMap = new HashMap<String, LoanClaimEnum>(10);

	static {
		LoanClaimEnum[] allValues = LoanClaimEnum.values();
		for (LoanClaimEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private LoanClaimEnum(String code, String name) {
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

	public static LoanClaimEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static LoanClaimEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
