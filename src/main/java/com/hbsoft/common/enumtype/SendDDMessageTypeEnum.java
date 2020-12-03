package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum SendDDMessageTypeEnum {

	MESSAGE_TYPE_WARN("1", "个人");

	private static Map<String, SendDDMessageTypeEnum> nameMap = new HashMap<String, SendDDMessageTypeEnum>(10);
	private static Map<String, SendDDMessageTypeEnum> codeMap = new HashMap<String, SendDDMessageTypeEnum>(10);

	static {
		SendDDMessageTypeEnum[] allValues = SendDDMessageTypeEnum.values();
		for (SendDDMessageTypeEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private SendDDMessageTypeEnum(String code, String name) {
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

	public static SendDDMessageTypeEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static SendDDMessageTypeEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
