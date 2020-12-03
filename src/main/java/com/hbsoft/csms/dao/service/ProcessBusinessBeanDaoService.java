package com.hbsoft.csms.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.ProcessBusinessBeanDao;
import com.hbsoft.csms.bean.ProcessBusinessBean;

@Service
public class ProcessBusinessBeanDaoService {
	@Autowired
	private ProcessBusinessBeanDao processBusinessBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param processBusinessBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(ProcessBusinessBean processBusinessBean) throws Exception {
		Integer i = processBusinessBeanDao.insert(processBusinessBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param processBusinessBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(ProcessBusinessBean processBusinessBean) throws Exception {
		Integer prikey = null;
		Integer i = processBusinessBeanDao.insertPrikey(processBusinessBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = processBusinessBean.getId_prikey();
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
	public Integer addBatch(List<ProcessBusinessBean> list) throws Exception {
		Integer i = processBusinessBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param processBusinessBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(ProcessBusinessBean processBusinessBean) throws Exception {
		Integer i = processBusinessBeanDao.delete(processBusinessBean);
		if ( i==0 ) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 按主键删除单条记录
	 * 
	 * @param id
	 * @return 返回是否删除成功
	 * @throws Exception
	 */
	public Boolean removeOne(Integer id) throws Exception {
		Integer i = processBusinessBeanDao.deleteOne(id);
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
		Integer i = processBusinessBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param processBusinessBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(ProcessBusinessBean processBusinessBean) throws Exception {
		Integer i = processBusinessBeanDao.update(processBusinessBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param processBusinessBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(ProcessBusinessBean processBusinessBean) throws Exception {
		Integer i = processBusinessBeanDao.updateEmpty(processBusinessBean);
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
	public ProcessBusinessBean getById(Integer id) throws Exception {
		return processBusinessBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param processBusinessBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public ProcessBusinessBean getByField(ProcessBusinessBean processBusinessBean) throws Exception {
		return processBusinessBeanDao.findByField(processBusinessBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param processBusinessBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<ProcessBusinessBean> getAll(ProcessBusinessBean processBusinessBean) throws Exception {
		return processBusinessBeanDao.findAll(processBusinessBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param processBusinessBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(ProcessBusinessBean processBusinessBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = processBusinessBeanDao.findPagingCount(processBusinessBean);
		List<ProcessBusinessBean> list = processBusinessBeanDao.findPagingData(processBusinessBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param processBusinessBean
	 * @return
	 * @throws Exception
	 */
	public List<ProcessBusinessBean> getTreeAll(ProcessBusinessBean processBusinessBean) throws Exception {
		return processBusinessBeanDao.findTreeData(processBusinessBean);
	}

	public ProcessBusinessBean findByProcessStatusDingAndBack(ProcessBusinessBean processBusinessBean){
		return processBusinessBeanDao.findByProcessStatusDingAndBack(processBusinessBean);
	}

	public List<ProcessBusinessBean> findNotDoingAndCancelAll(ProcessBusinessBean pb){
		return processBusinessBeanDao.findNotDoingAndCancelAll(pb);
	}
}
