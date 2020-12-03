package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum EnabledAndDisabledEnum {

	ENABLED("0", "启用"),
	DISABLED("1","停用");
	

	private static Map<String, EnabledAndDisabledEnum> nameMap = new HashMap<String, EnabledAndDisabledEnum>(10);
	private static Map<String, EnabledAndDisabledEnum> codeMap = new HashMap<String, EnabledAndDisabledEnum>(10);

	static {
		EnabledAndDisabledEnum[] allValues = EnabledAndDisabledEnum.values();
		for (EnabledAndDisabledEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private EnabledAndDisabledEnum(String code, String name) {
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

	public static EnabledAndDisabledEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static EnabledAndDisabledEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
