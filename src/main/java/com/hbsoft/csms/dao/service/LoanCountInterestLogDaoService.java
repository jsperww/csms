package com.hbsoft.csms.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.LoanCountInterestLogDao;
import com.hbsoft.csms.bean.LoanCountInterestLog;

@Service
public class LoanCountInterestLogDaoService {
	@Autowired
	private LoanCountInterestLogDao loanCountInterestLogDao;

	/**
	 * 插入一条数据
	 * 
	 * @param loanCountInterestLog
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(LoanCountInterestLog loanCountInterestLog) throws Exception {
		Integer i = loanCountInterestLogDao.insert(loanCountInterestLog);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanCountInterestLog
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(LoanCountInterestLog loanCountInterestLog) throws Exception {
		Integer prikey = null;
		Integer i = loanCountInterestLogDao.insertPrikey(loanCountInterestLog);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = loanCountInterestLog.getId_prikey();
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
	public Integer addBatch(List<LoanCountInterestLog> list) throws Exception {
		Integer i = loanCountInterestLogDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanCountInterestLog
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(LoanCountInterestLog loanCountInterestLog) throws Exception {
		Integer i = loanCountInterestLogDao.delete(loanCountInterestLog);
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
		Integer i = loanCountInterestLogDao.deleteOne(id);
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
		Integer i = loanCountInterestLogDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param loanCountInterestLog
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(LoanCountInterestLog loanCountInterestLog) throws Exception {
		Integer i = loanCountInterestLogDao.update(loanCountInterestLog);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanCountInterestLog
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(LoanCountInterestLog loanCountInterestLog) throws Exception {
		Integer i = loanCountInterestLogDao.updateEmpty(loanCountInterestLog);
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
	public LoanCountInterestLog getById(Integer id) throws Exception {
		return loanCountInterestLogDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanCountInterestLog
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public LoanCountInterestLog getByField(LoanCountInterestLog loanCountInterestLog) throws Exception {
		return loanCountInterestLogDao.findByField(loanCountInterestLog);
	}


	public LoanCountInterestLog findByLoanContractNumAndCountDate(LoanCountInterestLog loanCountInterestLog){
		return loanCountInterestLogDao.findByLoanContractNumAndCountDate(loanCountInterestLog);
	}
	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanCountInterestLog
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<LoanCountInterestLog> getAll(LoanCountInterestLog loanCountInterestLog) throws Exception {
		return loanCountInterestLogDao.findAll(loanCountInterestLog);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param loanCountInterestLog
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(LoanCountInterestLog loanCountInterestLog) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanCountInterestLogDao.findPagingCount(loanCountInterestLog);
		List<LoanCountInterestLog> list = loanCountInterestLogDao.findPagingData(loanCountInterestLog);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param loanCountInterestLog
	 * @return
	 * @throws Exception
	 */
	public List<LoanCountInterestLog> getTreeAll(LoanCountInterestLog loanCountInterestLog) throws Exception {
		return loanCountInterestLogDao.findTreeData(loanCountInterestLog);
	}

	public void insertDataFromLoanCountInterestLogTemp(String loanContractNum){
		loanCountInterestLogDao.insertDataFromLoanCountInterestLogTemp(loanContractNum);
	}

	public Integer deleteByLoanContractNumAndCountDate(LoanCountInterestLog loanCountInterestLog){
		return loanCountInterestLogDao.deleteByLoanContractNumAndCountDate(loanCountInterestLog);
	}
}
