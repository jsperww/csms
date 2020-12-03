package com.hbsoft.csms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.bean.CallResult;
import com.hbsoft.csms.bean.ProcessBean;
import com.hbsoft.csms.bean.ProcessDetailBean;
import com.hbsoft.csms.dao.service.ProcessBeanDaoService;
import com.hbsoft.csms.dao.service.ProcessDetailBeanDaoService;

@Service
public class ProcessInfoService {
	@Autowired
	ProcessBeanDaoService processBeanDaoService;
	@Autowired
	ProcessDetailBeanDaoService processDetailBeanDaoService;

	@Transactional
	public CallResult<String> delProcess(Integer id_prikey) throws Exception {
		CallResult<String> result = new CallResult<>();
		ProcessBean byId = processBeanDaoService.getById(id_prikey);
		if (byId == null) {
			result.setCode(400);
			result.setMsg("主键参数有误");
			return result;
		}
		ProcessDetailBean pdb = new ProcessDetailBean();
		pdb.setNodeType(byId.getCode());
		List<ProcessDetailBean> all = processDetailBeanDaoService.getAll(pdb);
		if (all != null && all.size() != 0) {
			processDetailBeanDaoService.remove(pdb);
		}
		processBeanDaoService.removeOne(id_prikey);
		result.setCode(0);
		result.setMsg("操作成功");
		return result;
	}
}
