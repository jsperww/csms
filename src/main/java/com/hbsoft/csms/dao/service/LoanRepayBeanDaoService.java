package com.hbsoft.csms.dao.service;

import com.hb.bean.CallResult;
import com.hb.util.DateUtil;
import com.hb.util.V1Pass;
import com.hbsoft.csms.bean.LoanInfoBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.LoanRepayBeanDao;
import com.hbsoft.csms.bean.LoanRepayBean;

@Service
public class LoanRepayBeanDaoService {
	@Autowired
	private LoanRepayBeanDao loanRepayBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param loanRepayBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(LoanRepayBean loanRepayBean) throws Exception {
		Integer i = loanRepayBeanDao.insert(loanRepayBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanRepayBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(LoanRepayBean loanRepayBean) throws Exception {
		Integer prikey = null;
		Integer i = loanRepayBeanDao.insertPrikey(loanRepayBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = loanRepayBean.getId_prikey();
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
	public Integer addBatch(List<LoanRepayBean> list) throws Exception {
		Integer i = loanRepayBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanRepayBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(LoanRepayBean loanRepayBean) throws Exception {
		Integer i = loanRepayBeanDao.delete(loanRepayBean);
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
		Integer i = loanRepayBeanDao.deleteOne(id);
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
		Integer i = loanRepayBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param loanRepayBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(LoanRepayBean loanRepayBean) throws Exception {
		Integer i = loanRepayBeanDao.update(loanRepayBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanRepayBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(LoanRepayBean loanRepayBean) throws Exception {
		Integer i = loanRepayBeanDao.updateEmpty(loanRepayBean);
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
	public LoanRepayBean getById(Integer id) throws Exception {
		return loanRepayBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanRepayBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public LoanRepayBean getByField(LoanRepayBean loanRepayBean) throws Exception {
		return loanRepayBeanDao.findByField(loanRepayBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanRepayBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<LoanRepayBean> getAll(LoanRepayBean loanRepayBean) throws Exception {
		return loanRepayBeanDao.findAll(loanRepayBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param loanRepayBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(LoanRepayBean loanRepayBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanRepayBeanDao.findPagingCount(loanRepayBean);
		List<LoanRepayBean> list = loanRepayBeanDao.findPagingData(loanRepayBean);
		for(LoanRepayBean l : list){
			l.setName(V1Pass.pass_decode(l.getName()));
			l.setIdNum(V1Pass.pass_decode(l.getIdNum()));
			l.setCheckFirstDateStr(DateUtil.formatDate(l.getCheckFirstDate(),"yyyy-MM-dd"));
			l.setCurrentDateStr(DateUtil.formatDate(new Date(),"yyyy-MM-dd"));
		}
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param loanRepayBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanRepayBean> getTreeAll(LoanRepayBean loanRepayBean) throws Exception {
		return loanRepayBeanDao.findTreeData(loanRepayBean);
	}


	public Map<String, Object> getRepayPagingData(LoanInfoBean loanInfoBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanRepayBeanDao.findRepayPagingCount(loanInfoBean);
		List<LoanInfoBean> list = loanRepayBeanDao.findRepayPagingData(loanInfoBean);
		for(LoanInfoBean l : list){
			l.setName(V1Pass.pass_decode(l.getName()));
			l.setIdNum(V1Pass.pass_decode(l.getIdNum()));
			l.setMobile(V1Pass.pass_decode(l.getMobile()));
		}
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}

	public LoanRepayBean findLatestRepayRecord(@Param("loanContractNum") String loanContractNum){
		return  loanRepayBeanDao.findLatestRepayRecord(loanContractNum);
	}

	public LoanRepayBean findLatestRepayByLoanContractNumAndRepayType(LoanRepayBean loanRepayBean){
		return loanRepayBeanDao.findLatestRepayByLoanContractNumAndRepayType(loanRepayBean);
	}
}
