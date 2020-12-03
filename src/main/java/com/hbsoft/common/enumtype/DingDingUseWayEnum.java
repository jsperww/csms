package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum DingDingUseWayEnum {

	DING_DING_USE_WAY_DINGDING("1", "钉钉"),
	DING_DING_USE_WAY_INTERFACE("2","接口");


	private static Map<String, DingDingUseWayEnum> nameMap = new HashMap<String, DingDingUseWayEnum>(10);
	private static Map<String, DingDingUseWayEnum> codeMap = new HashMap<String, DingDingUseWayEnum>(10);

	static {
		DingDingUseWayEnum[] allValues = DingDingUseWayEnum.values();
		for (DingDingUseWayEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private DingDingUseWayEnum(String code, String name) {
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

	public static DingDingUseWayEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static DingDingUseWayEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
