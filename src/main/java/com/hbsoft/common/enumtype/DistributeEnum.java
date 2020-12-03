package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum DistributeEnum {

	DISTRIBUTE_ENUM_NO("1", "未分配"),
	DISTRIBUTE_ENUM_YES("2","已分配");


	private static Map<String, DistributeEnum> nameMap = new HashMap<String, DistributeEnum>(10);
	private static Map<String, DistributeEnum> codeMap = new HashMap<String, DistributeEnum>(10);

	static {
		DistributeEnum[] allValues = DistributeEnum.values();
		for (DistributeEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private DistributeEnum(String code, String name) {
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

	public static DistributeEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static DistributeEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
