package com.hbsoft.csms.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.LoanDistributeBeanDao;
import com.hbsoft.csms.bean.LoanDistributeBean;

@Service
public class LoanDistributeBeanDaoService {
	@Autowired
	private LoanDistributeBeanDao loanDistributeBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param loanDistributeBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(LoanDistributeBean loanDistributeBean) throws Exception {
		Integer i = loanDistributeBeanDao.insert(loanDistributeBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanDistributeBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(LoanDistributeBean loanDistributeBean) throws Exception {
		Integer prikey = null;
		Integer i = loanDistributeBeanDao.insertPrikey(loanDistributeBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = loanDistributeBean.getId_prikey();
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
	public Integer addBatch(List<LoanDistributeBean> list) throws Exception {
		Integer i = loanDistributeBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanDistributeBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(LoanDistributeBean loanDistributeBean) throws Exception {
		Integer i = loanDistributeBeanDao.delete(loanDistributeBean);
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
		Integer i = loanDistributeBeanDao.deleteOne(id);
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
		Integer i = loanDistributeBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param loanDistributeBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(LoanDistributeBean loanDistributeBean) throws Exception {
		Integer i = loanDistributeBeanDao.update(loanDistributeBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}
	public Boolean setAllFiled(LoanDistributeBean loanDistributeBean) throws Exception {
		Integer i = loanDistributeBeanDao.updateAllFiled(loanDistributeBean);

		return true;
	}


	/**
	 * 根据主键清空字段
	 * 
	 * @param loanDistributeBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(LoanDistributeBean loanDistributeBean) throws Exception {
		Integer i = loanDistributeBeanDao.updateEmpty(loanDistributeBean);
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
	public LoanDistributeBean getById(Integer id) throws Exception {
		return loanDistributeBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanDistributeBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public LoanDistributeBean getByField(LoanDistributeBean loanDistributeBean) throws Exception {
		return loanDistributeBeanDao.findByField(loanDistributeBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanDistributeBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<LoanDistributeBean> getAll(LoanDistributeBean loanDistributeBean) throws Exception {
		return loanDistributeBeanDao.findAll(loanDistributeBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param loanDistributeBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(LoanDistributeBean loanDistributeBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanDistributeBeanDao.findPagingCount(loanDistributeBean);
		List<LoanDistributeBean> list = loanDistributeBeanDao.findPagingData(loanDistributeBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param loanDistributeBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanDistributeBean> getTreeAll(LoanDistributeBean loanDistributeBean) throws Exception {
		return loanDistributeBeanDao.findTreeData(loanDistributeBean);
	}
	
	public Integer delAllDistributeArea(String number) throws Exception{
		return loanDistributeBeanDao.delAllDistributeArea(number);
	}
	
	public Integer delAllDistributeBank(String number) throws Exception{
		return loanDistributeBeanDao.delAllDistributeBank(number);
	}
}
