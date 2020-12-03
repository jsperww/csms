package com.hbsoft.csms.dao.service;

import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.bean.UpdateLogBean;
import com.hbsoft.csms.dao.UpdateLogBeanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateLogBeanDaoService {
	@Autowired
	private UpdateLogBeanDao updateLogBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param updateLogBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(UpdateLogBean updateLogBean) throws Exception {
		Integer i = updateLogBeanDao.insert(updateLogBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param updateLogBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(UpdateLogBean updateLogBean) throws Exception {
		Integer prikey = null;
		Integer i = updateLogBeanDao.insertPrikey(updateLogBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = updateLogBean.getId_prikey();
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
	public Integer addBatch(List<UpdateLogBean> list) throws Exception {
		Integer i = updateLogBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param updateLogBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(UpdateLogBean updateLogBean) throws Exception {
		Integer i = updateLogBeanDao.delete(updateLogBean);
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
		Integer i = updateLogBeanDao.deleteOne(id);
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
		Integer i = updateLogBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param updateLogBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(UpdateLogBean updateLogBean) throws Exception {
		Integer i = updateLogBeanDao.update(updateLogBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param updateLogBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(UpdateLogBean updateLogBean) throws Exception {
		Integer i = updateLogBeanDao.updateEmpty(updateLogBean);
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
	public UpdateLogBean getById(Integer id) throws Exception {
		return updateLogBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param updateLogBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public UpdateLogBean getByField(UpdateLogBean updateLogBean) throws Exception {
		return updateLogBeanDao.findByField(updateLogBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param updateLogBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<UpdateLogBean> getAll(UpdateLogBean updateLogBean) throws Exception {
		return updateLogBeanDao.findAll(updateLogBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param updateLogBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(UpdateLogBean updateLogBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = updateLogBeanDao.findPagingCount(updateLogBean);
		List<UpdateLogBean> list = updateLogBeanDao.findPagingData(updateLogBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param updateLogBean
	 * @return
	 * @throws Exception
	 */
	public List<UpdateLogBean> getTreeAll(UpdateLogBean updateLogBean) throws Exception {
		return updateLogBeanDao.findTreeData(updateLogBean);
	}

	public List<UpdateLogBean> alterLoanInfoList() throws Exception{
		return updateLogBeanDao.alterLoanInfoList();
	}
}
