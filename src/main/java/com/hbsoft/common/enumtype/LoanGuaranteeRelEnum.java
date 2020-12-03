package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum LoanGuaranteeRelEnum {

	LOAN_GUARANTEE_REL_MATE("1", "夫妻"),
	LOAN_GUARANTEE_REL_LOANGUARANTEE("2","担保人");


	private static Map<String, LoanGuaranteeRelEnum> nameMap = new HashMap<String, LoanGuaranteeRelEnum>(10);
	private static Map<String, LoanGuaranteeRelEnum> codeMap = new HashMap<String, LoanGuaranteeRelEnum>(10);

	static {
		LoanGuaranteeRelEnum[] allValues = LoanGuaranteeRelEnum.values();
		for (LoanGuaranteeRelEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private LoanGuaranteeRelEnum(String code, String name) {
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

	public static LoanGuaranteeRelEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static LoanGuaranteeRelEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
