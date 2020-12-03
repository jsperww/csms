package com.hbsoft.csms.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.ProcessBusinessOperatorBeanDao;
import com.hbsoft.csms.bean.ProcessBusinessOperatorBean;

@Service
public class ProcessBusinessOperatorBeanDaoService {
	@Autowired
	private ProcessBusinessOperatorBeanDao processBusinessOperatorBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param processBusinessOperatorBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(ProcessBusinessOperatorBean processBusinessOperatorBean) throws Exception {
		Integer i = processBusinessOperatorBeanDao.insert(processBusinessOperatorBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param processBusinessOperatorBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(ProcessBusinessOperatorBean processBusinessOperatorBean) throws Exception {
		Integer prikey = null;
		Integer i = processBusinessOperatorBeanDao.insertPrikey(processBusinessOperatorBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = processBusinessOperatorBean.getId_prikey();
		if (o instanceof Integer) {
			prikey = (Integer) o;
		}
		return prikey;
	}

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return 返回影响行数
	 * @throws Exception
	 */
	public Integer addBatch(List<ProcessBusinessOperatorBean> list) throws Exception {
		Integer i = processBusinessOperatorBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param processBusinessOperatorBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(ProcessBusinessOperatorBean processBusinessOperatorBean) throws Exception {
		Integer i = processBusinessOperatorBeanDao.delete(processBusinessOperatorBean);
		if ( i==0 ) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}


	public Integer deleteByPdIdAndNode(ProcessBusinessOperatorBean processBusinessOperatorBean){
		return processBusinessOperatorBeanDao.deleteByPdIdAndNode(processBusinessOperatorBean);
	}

	/**
	 * 按主键删除单条记录
	 * 
	 * @param id
	 * @return 返回是否删除成功
	 * @throws Exception
	 */
	public Boolean removeOne(Integer id) throws Exception {
		Integer i = processBusinessOperatorBeanDao.deleteOne(id);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return true;
	}

	/**
	 * 按主键删除多条记录
	 * 
	 * @param ids
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer removeBatch(List<Integer> ids) throws Exception {
		Integer i = processBusinessOperatorBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param processBusinessOperatorBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(ProcessBusinessOperatorBean processBusinessOperatorBean) throws Exception {
		Integer i = processBusinessOperatorBeanDao.update(processBusinessOperatorBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param processBusinessOperatorBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(ProcessBusinessOperatorBean processBusinessOperatorBean) throws Exception {
		Integer i = processBusinessOperatorBeanDao.updateEmpty(processBusinessOperatorBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public ProcessBusinessOperatorBean getById(Integer id) throws Exception {
		return processBusinessOperatorBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param processBusinessOperatorBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public ProcessBusinessOperatorBean getByField(ProcessBusinessOperatorBean processBusinessOperatorBean) throws Exception {
		return processBusinessOperatorBeanDao.findByField(processBusinessOperatorBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param processBusinessOperatorBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<ProcessBusinessOperatorBean> getAll(ProcessBusinessOperatorBean processBusinessOperatorBean) throws Exception {
		return processBusinessOperatorBeanDao.findAll(processBusinessOperatorBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param processBusinessOperatorBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(ProcessBusinessOperatorBean processBusinessOperatorBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = processBusinessOperatorBeanDao.findPagingCount(processBusinessOperatorBean);
		List<ProcessBusinessOperatorBean> list = processBusinessOperatorBeanDao.findPagingData(processBusinessOperatorBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param processBusinessOperatorBean
	 * @return
	 * @throws Exception
	 */
	public List<ProcessBusinessOperatorBean> getTreeAll(ProcessBusinessOperatorBean processBusinessOperatorBean) throws Exception {
		return processBusinessOperatorBeanDao.findTreeData(processBusinessOperatorBean);
	}
}
