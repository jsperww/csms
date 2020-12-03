package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum ProcessOperatorRoleTypeEnum {

	PROCESS_OPERATOR_ROLE_DETAIL("1", "具体的人"),
	PROCESS_OPERATOR_ROLE_TYPE_TEAM_MANAGER("2", "组织内的人");


	private static Map<String, ProcessOperatorRoleTypeEnum> nameMap = new HashMap<String, ProcessOperatorRoleTypeEnum>(10);
	private static Map<String, ProcessOperatorRoleTypeEnum> codeMap = new HashMap<String, ProcessOperatorRoleTypeEnum>(10);

	static {
		ProcessOperatorRoleTypeEnum[] allValues = ProcessOperatorRoleTypeEnum.values();
		for (ProcessOperatorRoleTypeEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private ProcessOperatorRoleTypeEnum(String code, String name) {
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

	public static ProcessOperatorRoleTypeEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static ProcessOperatorRoleTypeEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
