package com.hbsoft.csms.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.ProcessBeanDao;
import com.hbsoft.csms.bean.ProcessBean;

@Service
public class ProcessBeanDaoService {
	@Autowired
	private ProcessBeanDao processBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param processBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(ProcessBean processBean) throws Exception {
		Integer i = processBeanDao.insert(processBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param processBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(ProcessBean processBean) throws Exception {
		Integer prikey = null;
		Integer i = processBeanDao.insertPrikey(processBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = processBean.getId_prikey();
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
	public Integer addBatch(List<ProcessBean> list) throws Exception {
		Integer i = processBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param processBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(ProcessBean processBean) throws Exception {
		Integer i = processBeanDao.delete(processBean);
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
		Integer i = processBeanDao.deleteOne(id);
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
		Integer i = processBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param processBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(ProcessBean processBean) throws Exception {
		Integer i = processBeanDao.update(processBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param processBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(ProcessBean processBean) throws Exception {
		Integer i = processBeanDao.updateEmpty(processBean);
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
	public ProcessBean getById(Integer id) throws Exception {
		return processBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param processBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public ProcessBean getByField(ProcessBean processBean) throws Exception {
		return processBeanDao.findByField(processBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param processBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<ProcessBean> getAll(ProcessBean processBean) throws Exception {
		return processBeanDao.findAll(processBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param processBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(ProcessBean processBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = processBeanDao.findPagingCount(processBean);
		List<ProcessBean> list = processBeanDao.findPagingData(processBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param processBean
	 * @return
	 * @throws Exception
	 */
	public List<ProcessBean> getTreeAll(ProcessBean processBean) throws Exception {
		return processBeanDao.findTreeData(processBean);
	}
}
