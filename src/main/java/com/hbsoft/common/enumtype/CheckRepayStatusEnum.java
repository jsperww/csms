package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum CheckRepayStatusEnum {

	CHECK_REPAY_STATUS_FIRST(1, "还款审核中"),
	CHECK_REPAY_STATUS_SENCOND(2,"还款已完成"),
	CHECK_REPAY_STATUS_REVOKE_FIRST(6,"还款撤销未完成"),
	CHECK_REPAY_STATUS_REVOKE_SENCOND(7,"还款撤销已完成"),
	CHECK_REPAY_STATUS_ADD_FIRST(4,"还款补录未完成"),
	CHECK_REPAY_STATUS_ADD_SENCODN(5,"还款补录已完成");


	private static Map<String, CheckRepayStatusEnum> nameMap = new HashMap<String, CheckRepayStatusEnum>(10);
	private static Map<Integer, CheckRepayStatusEnum> codeMap = new HashMap<Integer, CheckRepayStatusEnum>(10);

	static {
		CheckRepayStatusEnum[] allValues = CheckRepayStatusEnum.values();
		for (CheckRepayStatusEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private Integer code;

	private CheckRepayStatusEnum(Integer code, String name) {
		this.name = name;
		this.code = code;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public static CheckRepayStatusEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static CheckRepayStatusEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
