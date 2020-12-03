package com.hbsoft.csms.dao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.exception.SqlNotChangeException;
import com.hb.util.DateUtil;
import com.hb.util.V1Pass;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.dao.LoanInfoBeanDao;

@Service
public class LoanInfoBeanDaoService {
	@Autowired
	private LoanInfoBeanDao loanInfoBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param loanInfoBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(LoanInfoBean loanInfoBean) throws Exception {
		Integer i = loanInfoBeanDao.insert(loanInfoBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanInfoBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(LoanInfoBean loanInfoBean) throws Exception {
		Integer prikey = null;
		Integer i = loanInfoBeanDao.insertPrikey(loanInfoBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = loanInfoBean.getId_prikey();
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
	public Integer addBatch(List<LoanInfoBean> list) throws Exception {
		Integer i = loanInfoBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanInfoBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(LoanInfoBean loanInfoBean) throws Exception {
		Integer i = loanInfoBeanDao.delete(loanInfoBean);
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
		Integer i = loanInfoBeanDao.deleteOne(id);
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
		Integer i = loanInfoBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param loanInfoBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(LoanInfoBean loanInfoBean) throws Exception {
		Integer i = loanInfoBeanDao.update(loanInfoBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanInfoBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(LoanInfoBean loanInfoBean) throws Exception {
		Integer i = loanInfoBeanDao.updateEmpty(loanInfoBean);
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
	public LoanInfoBean getById(Integer id) throws Exception {
		return loanInfoBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanInfoBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public LoanInfoBean getByField(LoanInfoBean loanInfoBean) throws Exception {
		return loanInfoBeanDao.findByField(loanInfoBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanInfoBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<LoanInfoBean> getAll(LoanInfoBean loanInfoBean) throws Exception {
		return loanInfoBeanDao.findAll(loanInfoBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param loanInfoBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(LoanInfoBean loanInfoBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanInfoBeanDao.findPagingCount(loanInfoBean);
		List<LoanInfoBean> list = loanInfoBeanDao.findPagingData(loanInfoBean);
		for(LoanInfoBean loanInfoBean1 : list){
			loanInfoBean1.setIdNum(V1Pass.pass_decode(loanInfoBean1.getIdNum()));
			loanInfoBean1.setName(V1Pass.pass_decode(loanInfoBean1.getName()));
			loanInfoBean1.setMobile(V1Pass.pass_decode(loanInfoBean1.getMobile()));
			if(loanInfoBean1.getLoanBeginDate() != null){
				loanInfoBean1.setLoanBeginDateStr(DateUtil.formatDate(loanInfoBean1.getLoanBeginDate(),"yyyy-MM-dd"));
			}
			if(loanInfoBean1.getLoanEndDate() != null){
				loanInfoBean1.setLoanEndDateStr(DateUtil.formatDate(loanInfoBean1.getLoanEndDate(),"yyyy-MM-dd"));
			}
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
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanInfoBean> getTreeAll(LoanInfoBean loanInfoBean) throws Exception {
		return loanInfoBeanDao.findTreeData(loanInfoBean);
	}

	/**
	 * 添加数据备份
	 * @param loanInfoBean
	 */
	public Boolean addSourceLoan(LoanInfoBean loanInfoBean) throws Exception {
		Integer i = loanInfoBeanDao.addSourceLoan(loanInfoBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}
	
	/**
	 * 获取全部数据（不分页）
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanInfoBean> getAllData(LoanInfoBean loanInfoBean) throws Exception {
		return loanInfoBeanDao.findAllData(loanInfoBean);
	}
	
	public Map<String, Object> getPageShareDate(LoanInfoBean loanInfoBean)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanInfoBeanDao.findSharePagingCount(loanInfoBean);
		List<LoanInfoBean> list = loanInfoBeanDao.findSharePagingData(loanInfoBean);
		for(LoanInfoBean loanInfoBean1 : list){
			loanInfoBean1.setIdNum(V1Pass.pass_decode(loanInfoBean1.getIdNum()));
			loanInfoBean1.setName(V1Pass.pass_decode(loanInfoBean1.getName()));
			loanInfoBean1.setMobile(V1Pass.pass_decode(loanInfoBean1.getMobile()));
			if(loanInfoBean1.getLoanBeginDate() != null){
				loanInfoBean1.setLoanBeginDateStr(DateUtil.formatDate(loanInfoBean1.getLoanBeginDate(),"yyyy-MM-dd"));
			}
			if(loanInfoBean1.getLoanEndDate() != null){
				loanInfoBean1.setLoanEndDateStr(DateUtil.formatDate(loanInfoBean1.getLoanEndDate(),"yyyy-MM-dd"));
			}
		}
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}

    public void updateByConNum(LoanInfoBean loanInfoBean) throws Exception {
		loanInfoBeanDao.updateByConNum(loanInfoBean);
    }

}
