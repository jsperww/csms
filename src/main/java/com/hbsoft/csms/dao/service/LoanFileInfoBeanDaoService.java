package com.hbsoft.csms.dao.service;

import com.hb.bean.CallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.LoanFileInfoBeanDao;
import com.hbsoft.csms.bean.LoanFileInfoBean;

@Service
public class LoanFileInfoBeanDaoService {
	@Autowired
	private LoanFileInfoBeanDao loanFileInfoBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param loanFileInfoBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(LoanFileInfoBean loanFileInfoBean) throws Exception {
		Integer i = loanFileInfoBeanDao.insert(loanFileInfoBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanFileInfoBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(LoanFileInfoBean loanFileInfoBean) throws Exception {
		Integer prikey = null;
		Integer i = loanFileInfoBeanDao.insertPrikey(loanFileInfoBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = loanFileInfoBean.getId_prikey();
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
	public Integer addBatch(List<LoanFileInfoBean> list) throws Exception {
		Integer i = loanFileInfoBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanFileInfoBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(LoanFileInfoBean loanFileInfoBean) throws Exception {
		Integer i = loanFileInfoBeanDao.delete(loanFileInfoBean);
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
		Integer i = loanFileInfoBeanDao.deleteOne(id);
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
		Integer i = loanFileInfoBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param loanFileInfoBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(LoanFileInfoBean loanFileInfoBean) throws Exception {
		Integer i = loanFileInfoBeanDao.update(loanFileInfoBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanFileInfoBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(LoanFileInfoBean loanFileInfoBean) throws Exception {
		Integer i = loanFileInfoBeanDao.updateEmpty(loanFileInfoBean);
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
	public LoanFileInfoBean getById(Integer id) throws Exception {
		return loanFileInfoBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanFileInfoBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public LoanFileInfoBean getByField(LoanFileInfoBean loanFileInfoBean) throws Exception {
		return loanFileInfoBeanDao.findByField(loanFileInfoBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanFileInfoBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<LoanFileInfoBean> getAll(LoanFileInfoBean loanFileInfoBean) throws Exception {
		return loanFileInfoBeanDao.findAll(loanFileInfoBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param loanFileInfoBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(LoanFileInfoBean loanFileInfoBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanFileInfoBeanDao.findPagingCount(loanFileInfoBean);
		List<LoanFileInfoBean> list = loanFileInfoBeanDao.findPagingData(loanFileInfoBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}

	public CallResult<List<LoanFileInfoBean>> getPagingDataNew(LoanFileInfoBean loanFileInfoBean) throws Exception {
		CallResult<List<LoanFileInfoBean>> result = new CallResult<>();
		Integer count = loanFileInfoBeanDao.findPagingCount(loanFileInfoBean);
		List<LoanFileInfoBean> list = loanFileInfoBeanDao.findPagingData(loanFileInfoBean);
		result.setSuccessResult();
		result.setCount(count);
		result.setData(list);
		return result;
	}
	/**
	 * 获取树形数据
	 * 
	 * @param loanFileInfoBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanFileInfoBean> getTreeAll(LoanFileInfoBean loanFileInfoBean) throws Exception {
		return loanFileInfoBeanDao.findTreeData(loanFileInfoBean);
	}
}
