package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum UpdateLogTypeEnum {

	UPDATElOG_TYPE_UPDATE("1", "修改"),
	UPDATElOG_TYPE_DELETE("2", "删除");
	

	private static Map<String, UpdateLogTypeEnum> nameMap = new HashMap<String, UpdateLogTypeEnum>(10);
	private static Map<String, UpdateLogTypeEnum> codeMap = new HashMap<String, UpdateLogTypeEnum>(10);

	static {
		UpdateLogTypeEnum[] allValues = UpdateLogTypeEnum.values();
		for (UpdateLogTypeEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private UpdateLogTypeEnum(String code, String name) {
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

	public static UpdateLogTypeEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static UpdateLogTypeEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
