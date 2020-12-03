package com.hbsoft.csms.dao.service;

import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.bean.IndictmentMediation;
import com.hbsoft.csms.dao.IndictmentMediationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndictmentMediationDaoService {
	@Autowired
	private IndictmentMediationDao indictmentMediationDao;

	/**
	 * 插入一条数据
	 * 
	 * @param indictmentMediation
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(IndictmentMediation indictmentMediation) throws Exception {
		Integer i = indictmentMediationDao.insert(indictmentMediation);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param indictmentMediation
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(IndictmentMediation indictmentMediation) throws Exception {
		Integer prikey = null;
		Integer i = indictmentMediationDao.insertPrikey(indictmentMediation);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = indictmentMediation.getId_prikey();
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
	public Integer addBatch(List<IndictmentMediation> list) throws Exception {
		Integer i = indictmentMediationDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param indictmentMediation
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(IndictmentMediation indictmentMediation) throws Exception {
		Integer i = indictmentMediationDao.delete(indictmentMediation);
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
		Integer i = indictmentMediationDao.deleteOne(id);
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
		Integer i = indictmentMediationDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param indictmentMediation
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(IndictmentMediation indictmentMediation) throws Exception {
		Integer i = indictmentMediationDao.update(indictmentMediation);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param indictmentMediation
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(IndictmentMediation indictmentMediation) throws Exception {
		Integer i = indictmentMediationDao.updateEmpty(indictmentMediation);
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
	public IndictmentMediation getById(Integer id) throws Exception {
		return indictmentMediationDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param indictmentMediation
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public IndictmentMediation getByField(IndictmentMediation indictmentMediation) throws Exception {
		return indictmentMediationDao.findByField(indictmentMediation);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param indictmentMediation
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<IndictmentMediation> getAll(IndictmentMediation indictmentMediation) throws Exception {
		return indictmentMediationDao.findAll(indictmentMediation);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param indictmentMediation
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(IndictmentMediation indictmentMediation) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = indictmentMediationDao.findPagingCount(indictmentMediation);
		List<IndictmentMediation> list = indictmentMediationDao.findPagingData(indictmentMediation);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param indictmentMediation
	 * @return
	 * @throws Exception
	 */
	public List<IndictmentMediation> getTreeAll(IndictmentMediation indictmentMediation) throws Exception {
		return indictmentMediationDao.findTreeData(indictmentMediation);
	}
}
