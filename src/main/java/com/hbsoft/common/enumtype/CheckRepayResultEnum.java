package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum CheckRepayResultEnum {

	ENABLED("1", "通过"),
	DISABLED("2","退回");


	private static Map<String, CheckRepayResultEnum> nameMap = new HashMap<String, CheckRepayResultEnum>(10);
	private static Map<String, CheckRepayResultEnum> codeMap = new HashMap<String, CheckRepayResultEnum>(10);

	static {
		CheckRepayResultEnum[] allValues = CheckRepayResultEnum.values();
		for (CheckRepayResultEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private CheckRepayResultEnum(String code, String name) {
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

	public static CheckRepayResultEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static CheckRepayResultEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
