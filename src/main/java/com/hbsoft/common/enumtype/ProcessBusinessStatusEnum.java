package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum ProcessBusinessStatusEnum {

	PROCESS_NODE_STATUS_DOING("1", "审批中"),
	PROCESS_NODE_STATUS_FINISH("2","已完成"),
	PROCESS_NODE_STATUS_BACK("3","已退回"),
	PROCESS_NODE_STATUS_CANCEL("4","已撤销"),;


	private static Map<String, ProcessBusinessStatusEnum> nameMap = new HashMap<String, ProcessBusinessStatusEnum>(10);
	private static Map<String, ProcessBusinessStatusEnum> codeMap = new HashMap<String, ProcessBusinessStatusEnum>(10);

	static {
		ProcessBusinessStatusEnum[] allValues = ProcessBusinessStatusEnum.values();
		for (ProcessBusinessStatusEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private ProcessBusinessStatusEnum(String code, String name) {
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

	public static ProcessBusinessStatusEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static ProcessBusinessStatusEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
