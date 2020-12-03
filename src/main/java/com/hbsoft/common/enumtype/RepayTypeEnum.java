package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum RepayTypeEnum {

	REPAY_TYPE_NORMAL("1", "正常还款"),
	REPAY_TYPE_ADD("2","补录还款"),
	REPAY_TYPE_REVOKE("3","还款抹账");

	private static Map<String, RepayTypeEnum> nameMap = new HashMap<String, RepayTypeEnum>(10);
	private static Map<String, RepayTypeEnum> codeMap = new HashMap<String, RepayTypeEnum>(10);

	static {
		RepayTypeEnum[] allValues = RepayTypeEnum.values();
		for (RepayTypeEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private RepayTypeEnum(String code, String name) {
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

	public static RepayTypeEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static RepayTypeEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
