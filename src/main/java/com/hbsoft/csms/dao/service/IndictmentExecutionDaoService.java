package com.hbsoft.csms.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.IndictmentExecutionDao;
import com.hbsoft.csms.bean.IndictmentExecution;

@Service
public class IndictmentExecutionDaoService {
	@Autowired
	private IndictmentExecutionDao indictmentExecutionDao;

	/**
	 * 插入一条数据
	 * 
	 * @param indictmentExecution
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(IndictmentExecution indictmentExecution) throws Exception {
		Integer i = indictmentExecutionDao.insert(indictmentExecution);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param indictmentExecution
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(IndictmentExecution indictmentExecution) throws Exception {
		Integer prikey = null;
		Integer i = indictmentExecutionDao.insertPrikey(indictmentExecution);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = indictmentExecution.getId_prikey();
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
	public Integer addBatch(List<IndictmentExecution> list) throws Exception {
		Integer i = indictmentExecutionDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param indictmentExecution
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(IndictmentExecution indictmentExecution) throws Exception {
		Integer i = indictmentExecutionDao.delete(indictmentExecution);
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
		Integer i = indictmentExecutionDao.deleteOne(id);
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
		Integer i = indictmentExecutionDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param indictmentExecution
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(IndictmentExecution indictmentExecution) throws Exception {
		Integer i = indictmentExecutionDao.update(indictmentExecution);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param indictmentExecution
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(IndictmentExecution indictmentExecution) throws Exception {
		Integer i = indictmentExecutionDao.updateEmpty(indictmentExecution);
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
	public IndictmentExecution getById(Integer id) throws Exception {
		return indictmentExecutionDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param indictmentExecution
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public IndictmentExecution getByField(IndictmentExecution indictmentExecution) throws Exception {
		return indictmentExecutionDao.findByField(indictmentExecution);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param indictmentExecution
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<IndictmentExecution> getAll(IndictmentExecution indictmentExecution) throws Exception {
		return indictmentExecutionDao.findAll(indictmentExecution);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param indictmentExecution
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(IndictmentExecution indictmentExecution) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = indictmentExecutionDao.findPagingCount(indictmentExecution);
		List<IndictmentExecution> list = indictmentExecutionDao.findPagingData(indictmentExecution);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param indictmentExecution
	 * @return
	 * @throws Exception
	 */
	public List<IndictmentExecution> getTreeAll(IndictmentExecution indictmentExecution) throws Exception {
		return indictmentExecutionDao.findTreeData(indictmentExecution);
	}
}
