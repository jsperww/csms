package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum ProcessNodeStatusEnum {

	PROCESS_NODE_STATUS_YES("1", "通过"),
	PROCESS_NODE_STATUS_NO("2","退回"),
	PROCESS_NODE_STATUS_STOP("3","撤销");


	private static Map<String, ProcessNodeStatusEnum> nameMap = new HashMap<String, ProcessNodeStatusEnum>(10);
	private static Map<String, ProcessNodeStatusEnum> codeMap = new HashMap<String, ProcessNodeStatusEnum>(10);

	static {
		ProcessNodeStatusEnum[] allValues = ProcessNodeStatusEnum.values();
		for (ProcessNodeStatusEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private ProcessNodeStatusEnum(String code, String name) {
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

	public static ProcessNodeStatusEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static ProcessNodeStatusEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
