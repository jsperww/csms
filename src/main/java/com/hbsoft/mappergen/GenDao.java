package com.hbsoft.mappergen;

import com.hb.util.GenerateDao;

public class GenDao {
	public static void main(String[] args) throws Exception {
		GenerateDao.generateBeanDao("com.hbsoft.csms.bean.DDUserAll");
	}
}
