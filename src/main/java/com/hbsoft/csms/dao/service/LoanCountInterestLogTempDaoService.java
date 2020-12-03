package com.hbsoft.csms.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.LoanCountInterestLogTempDao;
import com.hbsoft.csms.bean.LoanCountInterestLogTemp;

@Service
public class LoanCountInterestLogTempDaoService {
	@Autowired
	private LoanCountInterestLogTempDao loanCountInterestLogTempDao;

	/**
	 * 插入一条数据
	 * 
	 * @param loanCountInterestLogTemp
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception {
		Integer i = loanCountInterestLogTempDao.insert(loanCountInterestLogTemp);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanCountInterestLogTemp
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception {
		Integer prikey = null;
		Integer i = loanCountInterestLogTempDao.insertPrikey(loanCountInterestLogTemp);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = loanCountInterestLogTemp.getId_prikey();
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
	public Integer addBatch(List<LoanCountInterestLogTemp> list) throws Exception {
		Integer i = loanCountInterestLogTempDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanCountInterestLogTemp
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception {
		Integer i = loanCountInterestLogTempDao.delete(loanCountInterestLogTemp);
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
		Integer i = loanCountInterestLogTempDao.deleteOne(id);
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
		Integer i = loanCountInterestLogTempDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param loanCountInterestLogTemp
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception {
		Integer i = loanCountInterestLogTempDao.update(loanCountInterestLogTemp);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanCountInterestLogTemp
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception {
		Integer i = loanCountInterestLogTempDao.updateEmpty(loanCountInterestLogTemp);
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
	public LoanCountInterestLogTemp getById(Integer id) throws Exception {
		return loanCountInterestLogTempDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanCountInterestLogTemp
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public LoanCountInterestLogTemp getByField(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception {
		return loanCountInterestLogTempDao.findByField(loanCountInterestLogTemp);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanCountInterestLogTemp
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<LoanCountInterestLogTemp> getAll(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception {
		return loanCountInterestLogTempDao.findAll(loanCountInterestLogTemp);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param loanCountInterestLogTemp
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanCountInterestLogTempDao.findPagingCount(loanCountInterestLogTemp);
		List<LoanCountInterestLogTemp> list = loanCountInterestLogTempDao.findPagingData(loanCountInterestLogTemp);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param loanCountInterestLogTemp
	 * @return
	 * @throws Exception
	 */
	public List<LoanCountInterestLogTemp> getTreeAll(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception {
		return loanCountInterestLogTempDao.findTreeData(loanCountInterestLogTemp);
	}
}
