package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum ProcessOperatorRoleEnum {

	PROCESS_OPERATOR_ROLE_TEAM_LEADER("team_leader", "组长");


	private static Map<String, ProcessOperatorRoleEnum> nameMap = new HashMap<String, ProcessOperatorRoleEnum>(10);
	private static Map<String, ProcessOperatorRoleEnum> codeMap = new HashMap<String, ProcessOperatorRoleEnum>(10);

	static {
		ProcessOperatorRoleEnum[] allValues = ProcessOperatorRoleEnum.values();
		for (ProcessOperatorRoleEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private ProcessOperatorRoleEnum(String code, String name) {
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

	public static ProcessOperatorRoleEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static ProcessOperatorRoleEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
