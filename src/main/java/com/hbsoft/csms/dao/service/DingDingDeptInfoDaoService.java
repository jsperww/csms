package com.hbsoft.csms.dao.service;

import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.DingDingDeptInfoDao;
import com.hbsoft.dingding.bean.DingDingDeptInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DingDingDeptInfoDaoService {
	@Autowired
	private DingDingDeptInfoDao dingDingDeptInfoDao;

	/**
	 * 插入一条数据
	 * 
	 * @param dingDingDeptInfo
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(DingDingDeptInfo dingDingDeptInfo) throws Exception {
		Integer i = dingDingDeptInfoDao.insert(dingDingDeptInfo);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param dingDingDeptInfo
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(DingDingDeptInfo dingDingDeptInfo) throws Exception {
		Integer prikey = null;
		Integer i = dingDingDeptInfoDao.insertPrikey(dingDingDeptInfo);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = dingDingDeptInfo.getId_prikey();
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
	public Integer addBatch(List<DingDingDeptInfo> list) throws Exception {
		Integer i = dingDingDeptInfoDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param dingDingDeptInfo
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(DingDingDeptInfo dingDingDeptInfo) throws Exception {
		Integer i = dingDingDeptInfoDao.delete(dingDingDeptInfo);
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
		Integer i = dingDingDeptInfoDao.deleteOne(id);
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
		Integer i = dingDingDeptInfoDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param dingDingDeptInfo
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(DingDingDeptInfo dingDingDeptInfo) throws Exception {
		Integer i = dingDingDeptInfoDao.update(dingDingDeptInfo);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param dingDingDeptInfo
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(DingDingDeptInfo dingDingDeptInfo) throws Exception {
		Integer i = dingDingDeptInfoDao.updateEmpty(dingDingDeptInfo);
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
	public DingDingDeptInfo getById(Integer id) throws Exception {
		return dingDingDeptInfoDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param dingDingDeptInfo
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public DingDingDeptInfo getByField(DingDingDeptInfo dingDingDeptInfo) throws Exception {
		return dingDingDeptInfoDao.findByField(dingDingDeptInfo);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param dingDingDeptInfo
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<DingDingDeptInfo> getAll(DingDingDeptInfo dingDingDeptInfo) throws Exception {
		return dingDingDeptInfoDao.findAll(dingDingDeptInfo);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param dingDingDeptInfo
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(DingDingDeptInfo dingDingDeptInfo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = dingDingDeptInfoDao.findPagingCount(dingDingDeptInfo);
		List<DingDingDeptInfo> list = dingDingDeptInfoDao.findPagingData(dingDingDeptInfo);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param dingDingDeptInfo
	 * @return
	 * @throws Exception
	 */
	public List<DingDingDeptInfo> getTreeAll(DingDingDeptInfo dingDingDeptInfo) throws Exception {
		return dingDingDeptInfoDao.findTreeData(dingDingDeptInfo);
	}
}
