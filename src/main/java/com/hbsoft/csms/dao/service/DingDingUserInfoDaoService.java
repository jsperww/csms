package com.hbsoft.csms.dao.service;

import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.DingDingUserInfoDao;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DingDingUserInfoDaoService {
	@Autowired
	private DingDingUserInfoDao dingDingUserInfoDao;

	/**
	 * 插入一条数据
	 * 
	 * @param dingDingUserInfo
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(DingDingUserInfo dingDingUserInfo) throws Exception {
		Integer i = dingDingUserInfoDao.insert(dingDingUserInfo);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param dingDingUserInfo
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(DingDingUserInfo dingDingUserInfo) throws Exception {
		Integer prikey = null;
		Integer i = dingDingUserInfoDao.insertPrikey(dingDingUserInfo);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = dingDingUserInfo.getId_prikey();
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
	public Integer addBatch(List<DingDingUserInfo> list) throws Exception {
		Integer i = dingDingUserInfoDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param dingDingUserInfo
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(DingDingUserInfo dingDingUserInfo) throws Exception {
		Integer i = dingDingUserInfoDao.delete(dingDingUserInfo);
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
		Integer i = dingDingUserInfoDao.deleteOne(id);
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
		Integer i = dingDingUserInfoDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param dingDingUserInfo
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(DingDingUserInfo dingDingUserInfo) throws Exception {
		Integer i = dingDingUserInfoDao.update(dingDingUserInfo);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param dingDingUserInfo
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(DingDingUserInfo dingDingUserInfo) throws Exception {
		Integer i = dingDingUserInfoDao.updateEmpty(dingDingUserInfo);
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
	public DingDingUserInfo getById(Integer id) throws Exception {
		return dingDingUserInfoDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param dingDingUserInfo
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public DingDingUserInfo getByField(DingDingUserInfo dingDingUserInfo) throws Exception {
		return dingDingUserInfoDao.findByField(dingDingUserInfo);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param dingDingUserInfo
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<DingDingUserInfo> getAll(DingDingUserInfo dingDingUserInfo) throws Exception {
		return dingDingUserInfoDao.findAll(dingDingUserInfo);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param dingDingUserInfo
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(DingDingUserInfo dingDingUserInfo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = dingDingUserInfoDao.findPagingCount(dingDingUserInfo);
		List<DingDingUserInfo> list = dingDingUserInfoDao.findPagingData(dingDingUserInfo);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param dingDingUserInfo
	 * @return
	 * @throws Exception
	 */
	public List<DingDingUserInfo> getTreeAll(DingDingUserInfo dingDingUserInfo) throws Exception {
		return dingDingUserInfoDao.findTreeData(dingDingUserInfo);
	}
}
