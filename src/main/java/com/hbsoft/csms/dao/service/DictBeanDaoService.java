package com.hbsoft.csms.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.DictBeanDao;
import com.hbsoft.csms.bean.DictBean;

@Service
public class DictBeanDaoService {
	@Autowired
	private DictBeanDao dictBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param dictBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(DictBean dictBean) throws Exception {
		Integer i = dictBeanDao.insert(dictBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param dictBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(DictBean dictBean) throws Exception {
		Integer prikey = null;
		Integer i = dictBeanDao.insertPrikey(dictBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = dictBean.getId_prikey();
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
	public Integer addBatch(List<DictBean> list) throws Exception {
		Integer i = dictBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param dictBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(DictBean dictBean) throws Exception {
		Integer i = dictBeanDao.delete(dictBean);
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
		Integer i = dictBeanDao.deleteOne(id);
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
		Integer i = dictBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param dictBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(DictBean dictBean) throws Exception {
		Integer i = dictBeanDao.update(dictBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param dictBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(DictBean dictBean) throws Exception {
		Integer i = dictBeanDao.updateEmpty(dictBean);
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
	public DictBean getById(Integer id) throws Exception {
		return dictBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param dictBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public DictBean getByField(DictBean dictBean) throws Exception {
		return dictBeanDao.findByField(dictBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param dictBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<DictBean> getAll(DictBean dictBean) throws Exception {
		return dictBeanDao.findAll(dictBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param dictBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(DictBean dictBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = dictBeanDao.findPagingCount(dictBean);
		List<DictBean> list = dictBeanDao.findPagingData(dictBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param dictBean
	 * @return
	 * @throws Exception
	 */
	public List<DictBean> getTreeAll(DictBean dictBean) throws Exception {
		return dictBeanDao.findTreeData(dictBean);
	}
}
