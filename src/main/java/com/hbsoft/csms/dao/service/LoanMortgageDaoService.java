package com.hbsoft.csms.dao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.bean.LoanMortgage;
import com.hbsoft.csms.dao.LoanMortgageDao;

@Service
public class LoanMortgageDaoService {
	@Autowired
	private LoanMortgageDao loanMortgageDao;

	/**
	 * 插入一条数据
	 * 
	 * @param loanMortgage
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(LoanMortgage loanMortgage) throws Exception {
		Integer i = loanMortgageDao.insert(loanMortgage);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanMortgage
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(LoanMortgage loanMortgage) throws Exception {
		Integer prikey = null;
		Integer i = loanMortgageDao.insertPrikey(loanMortgage);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = loanMortgage.getId_prikey();
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
	public Integer addBatch(List<LoanMortgage> list) throws Exception {
		Integer i = loanMortgageDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanMortgage
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(LoanMortgage loanMortgage) throws Exception {
		Integer i = loanMortgageDao.delete(loanMortgage);
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
		Integer i = loanMortgageDao.deleteOne(id);
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
		Integer i = loanMortgageDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param loanMortgage
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(LoanMortgage loanMortgage) throws Exception {
		Integer i = loanMortgageDao.update(loanMortgage);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanMortgage
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(LoanMortgage loanMortgage) throws Exception {
		Integer i = loanMortgageDao.updateEmpty(loanMortgage);
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
	public LoanMortgage getById(Integer id) throws Exception {
		return loanMortgageDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanMortgage
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public LoanMortgage getByField(LoanMortgage loanMortgage) throws Exception {
		return loanMortgageDao.findByField(loanMortgage);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanMortgage
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<LoanMortgage> getAll(LoanMortgage loanMortgage) throws Exception {
		return loanMortgageDao.findAll(loanMortgage);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param loanMortgage
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(LoanMortgage loanMortgage) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanMortgageDao.findPagingCount(loanMortgage);
		List<LoanMortgage> list = loanMortgageDao.findPagingData(loanMortgage);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param loanMortgage
	 * @return
	 * @throws Exception
	 */
	public List<LoanMortgage> getTreeAll(LoanMortgage loanMortgage) throws Exception {
		return loanMortgageDao.findTreeData(loanMortgage);
	}
}
