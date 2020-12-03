package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum DistributeOperationTypeEnum {

	DISTRIBUTE_MANAGER("1", "管理员分配个人"),
	DISTRIBUTE_GROUP("2","管理员分配给组"),
	DISTRIBUTE_GROUP_MEMBER("3","组内分配"),
	DISTRIBUTE_SHARE("4","共享"),
	DISTRIBUTE_NOSHARE("8","取消共享"),
	DISTRIBUTE_CLAIM("5","认领"),
	DISTRIBUTE_VILLAGE("6","整村分配"),
	DISTRIBUTE_BANK("7","支行分配");


	private static Map<String, DistributeOperationTypeEnum> nameMap = new HashMap<String, DistributeOperationTypeEnum>(10);
	private static Map<String, DistributeOperationTypeEnum> codeMap = new HashMap<String, DistributeOperationTypeEnum>(10);

	static {
		DistributeOperationTypeEnum[] allValues = DistributeOperationTypeEnum.values();
		for (DistributeOperationTypeEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private DistributeOperationTypeEnum(String code, String name) {
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

	public static DistributeOperationTypeEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static DistributeOperationTypeEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
