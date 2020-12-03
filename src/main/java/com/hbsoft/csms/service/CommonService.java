package com.hbsoft.csms.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.bean.CallResult;
import com.hbsoft.csms.dao.CommonDao;

@Service
public class CommonService {

	@Autowired
	CommonDao commonDao;

	public String getMaxCode() {
		return commonDao.getMaxCode();
	}

	public Integer getMaxCode2(String nodeType) {
		return commonDao.getMaxCode2(nodeType);
	}

	public CallResult getAreaCodeByDistributeArea(String organizationNum) throws Exception {
		CallResult<Set<String>> result = new CallResult<>();
		Set<String> set = new HashSet<>();
		List<String> areaCode = commonDao.getAreaCodeByDistributeArea(organizationNum);
		for (String code : areaCode) {
			set.add(code);
			String substring = "";
			substring = code.substring(0, code.length() - 3);
			while (true) {
				set.add(substring);
				substring = substring.substring(0, substring.length() - 3);
				if (substring.length() < 6) {
					break;
				}
			}
		}
		result.setSuccessResult(set);
		return result;
	}
	
	public CallResult getBankCodeByDistributeBank(String organizationNum) throws Exception {
		CallResult<List<String>> result = new CallResult<>();
		List<String> bankCode = commonDao.getBankCodeByDistributeBank(organizationNum);
		result.setSuccessResult(bankCode);
		return result;
	}

	public static void main(String[] args) {
		String s = "123456789123";
		Set<String> set = new HashSet<>();
		String substring = "";

		substring = s.substring(0, s.length() - 3);
		while (true) {
			set.add(substring);
			substring = substring.substring(0, substring.length() - 3);
			System.out.println(substring + "**");

			if (substring.length() < 3) {
				break;
			}
		}

		System.out.println(set);
	}
}
