package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum PeyMentPersonEnum {

	BORROWER("1", "借款人"),
	GUARANTEE("2","担保人");


	private static Map<String, PeyMentPersonEnum> nameMap = new HashMap<String, PeyMentPersonEnum>(10);
	private static Map<String, PeyMentPersonEnum> codeMap = new HashMap<String, PeyMentPersonEnum>(10);

	static {
		PeyMentPersonEnum[] allValues = PeyMentPersonEnum.values();
		for (PeyMentPersonEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private PeyMentPersonEnum(String code, String name) {
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

	public static PeyMentPersonEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static PeyMentPersonEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
