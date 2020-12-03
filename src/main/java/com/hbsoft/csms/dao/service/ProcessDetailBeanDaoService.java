package com.hbsoft.csms.dao.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.ProcessDetailBeanDao;
import com.hbsoft.csms.bean.ProcessDetailBean;

@Service
public class ProcessDetailBeanDaoService {
	@Autowired
	private ProcessDetailBeanDao processDetailBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param processDetailBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(ProcessDetailBean processDetailBean) throws Exception {
		Integer i = processDetailBeanDao.insert(processDetailBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param processDetailBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(ProcessDetailBean processDetailBean) throws Exception {
		Integer prikey = null;
		Integer i = processDetailBeanDao.insertPrikey(processDetailBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = processDetailBean.getId_prikey();
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
	public Integer addBatch(List<ProcessDetailBean> list) throws Exception {
		Integer i = processDetailBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param processDetailBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(ProcessDetailBean processDetailBean) throws Exception {
		Integer i = processDetailBeanDao.delete(processDetailBean);
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
		Integer i = processDetailBeanDao.deleteOne(id);
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
		Integer i = processDetailBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param processDetailBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(ProcessDetailBean processDetailBean) throws Exception {
		Integer i = processDetailBeanDao.update(processDetailBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param processDetailBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(ProcessDetailBean processDetailBean) throws Exception {
		Integer i = processDetailBeanDao.updateEmpty(processDetailBean);
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
	public ProcessDetailBean getById(Integer id) throws Exception {
		return processDetailBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param processDetailBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public ProcessDetailBean getByField(ProcessDetailBean processDetailBean) throws Exception {
		return processDetailBeanDao.findByField(processDetailBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param processDetailBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<ProcessDetailBean> getAll(ProcessDetailBean processDetailBean) throws Exception {
		return processDetailBeanDao.findAll(processDetailBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param processDetailBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(ProcessDetailBean processDetailBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = processDetailBeanDao.findPagingCount(processDetailBean);
		List<ProcessDetailBean> list = processDetailBeanDao.findPagingData(processDetailBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param processDetailBean
	 * @return
	 * @throws Exception
	 */
	public List<ProcessDetailBean> getTreeAll(ProcessDetailBean processDetailBean) throws Exception {
		return processDetailBeanDao.findTreeData(processDetailBean);
	}

	/**
	* @author hwl
	* @description 获取流程最下节点
	* @date 2020/4/11
	*/
	public ProcessDetailBean findMixNodeIdByNodeType(String nodeType) {
		return processDetailBeanDao.findMinNodeIdByNodeType(nodeType);
	}
}
