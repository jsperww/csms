package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum DistributeTypeEnum {

	DISTRIBUTE_TYPE_PERSON("1", "个人"),
	DISTRIBUTE_TYPE_GROUP("2","组");


	private static Map<String, DistributeTypeEnum> nameMap = new HashMap<String, DistributeTypeEnum>(10);
	private static Map<String, DistributeTypeEnum> codeMap = new HashMap<String, DistributeTypeEnum>(10);

	static {
		DistributeTypeEnum[] allValues = DistributeTypeEnum.values();
		for (DistributeTypeEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private DistributeTypeEnum(String code, String name) {
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

	public static DistributeTypeEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static DistributeTypeEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
