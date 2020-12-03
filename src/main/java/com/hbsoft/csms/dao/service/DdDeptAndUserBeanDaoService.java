package com.hbsoft.csms.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.DdDeptAndUserBeanDao;
import com.hbsoft.csms.bean.DdDeptAndUserBean;

@Service
public class DdDeptAndUserBeanDaoService {
	@Autowired
	private DdDeptAndUserBeanDao ddDeptAndUserBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param ddDeptAndUserBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(DdDeptAndUserBean ddDeptAndUserBean) throws Exception {
		Integer i = ddDeptAndUserBeanDao.insert(ddDeptAndUserBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param ddDeptAndUserBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(DdDeptAndUserBean ddDeptAndUserBean) throws Exception {
		Integer prikey = null;
		Integer i = ddDeptAndUserBeanDao.insertPrikey(ddDeptAndUserBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = ddDeptAndUserBean.getId_prikey();
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
	public Integer addBatch(List<DdDeptAndUserBean> list) throws Exception {
		Integer i = ddDeptAndUserBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param ddDeptAndUserBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(DdDeptAndUserBean ddDeptAndUserBean) throws Exception {
		Integer i = ddDeptAndUserBeanDao.delete(ddDeptAndUserBean);
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
		Integer i = ddDeptAndUserBeanDao.deleteOne(id);
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
		Integer i = ddDeptAndUserBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param ddDeptAndUserBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(DdDeptAndUserBean ddDeptAndUserBean) throws Exception {
		Integer i = ddDeptAndUserBeanDao.update(ddDeptAndUserBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param ddDeptAndUserBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(DdDeptAndUserBean ddDeptAndUserBean) throws Exception {
		Integer i = ddDeptAndUserBeanDao.updateEmpty(ddDeptAndUserBean);
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
	public DdDeptAndUserBean getById(Integer id) throws Exception {
		return ddDeptAndUserBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param ddDeptAndUserBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public DdDeptAndUserBean getByField(DdDeptAndUserBean ddDeptAndUserBean) throws Exception {
		return ddDeptAndUserBeanDao.findByField(ddDeptAndUserBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param ddDeptAndUserBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<DdDeptAndUserBean> getAll(DdDeptAndUserBean ddDeptAndUserBean) throws Exception {
		return ddDeptAndUserBeanDao.findAll(ddDeptAndUserBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param ddDeptAndUserBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(DdDeptAndUserBean ddDeptAndUserBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = ddDeptAndUserBeanDao.findPagingCount(ddDeptAndUserBean);
		List<DdDeptAndUserBean> list = ddDeptAndUserBeanDao.findPagingData(ddDeptAndUserBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param ddDeptAndUserBean
	 * @return
	 * @throws Exception
	 */
	public List<DdDeptAndUserBean> getTreeAll(DdDeptAndUserBean ddDeptAndUserBean) throws Exception {
		return ddDeptAndUserBeanDao.findTreeData(ddDeptAndUserBean);
	}
}
