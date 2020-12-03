package com.hbsoft.common.enumtype;

import java.util.HashMap;
import java.util.Map;

public enum LoanFileFormatEnum {

	LOAN_FILE_FORMAT_IMG("1", "图片"),
	LOAN_FILE_FORMAT_VIDEO("2", "视频"),
	LOAN_FILE_FORMAT_VOICE("3", "音频"),
	LOAN_FILE_FORMAT_DOCUMENT("4", "文档"),
	LOAN_FILE_FORMAT_LETTER("5", "催收函");


	private static Map<String, LoanFileFormatEnum> nameMap = new HashMap<String, LoanFileFormatEnum>(10);
	private static Map<String, LoanFileFormatEnum> codeMap = new HashMap<String, LoanFileFormatEnum>(10);

	static {
		LoanFileFormatEnum[] allValues = LoanFileFormatEnum.values();
		for (LoanFileFormatEnum obj : allValues) {
			nameMap.put(obj.getName(), obj);
			codeMap.put(obj.getCode(), obj);
		}
	}

	private String name;
	private String code;

	private LoanFileFormatEnum(String code, String name) {
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

	public static LoanFileFormatEnum parseByName(String name) {
		return nameMap.get(name);
	}

	public static LoanFileFormatEnum parseByCode(String code) {
		return codeMap.get(code);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
