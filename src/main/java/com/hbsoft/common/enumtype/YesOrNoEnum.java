package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum YesOrNoEnum {

	YES_OR_NO_YES("1", "是"),
    YES_OR_NO_NO("2","否");


	private static Map<String, YesOrNoEnum> nameMap = new HashMap<String, YesOrNoEnum>(10);
	private static Map<String, YesOrNoEnum> codeMap = new HashMap<String, YesOrNoEnum>(10);

	static {
		YesOrNoEnum[] allValues = YesOrNoEnum.values();
		for (YesOrNoEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private YesOrNoEnum(String code, String name) {
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

	public static YesOrNoEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static YesOrNoEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
