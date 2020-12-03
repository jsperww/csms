package com.hbsoft.csms.dao.service;

import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.bean.IndictmentInfo;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.dao.IndictmentInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndictmentInfoDaoService {
	@Autowired
	private IndictmentInfoDao indictmentInfoDao;

	/**
	 * 插入一条数据
	 * 
	 * @param indictmentInfo
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(IndictmentInfo indictmentInfo) throws Exception {
		Integer i = indictmentInfoDao.insert(indictmentInfo);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param indictmentInfo
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(IndictmentInfo indictmentInfo) throws Exception {
		Integer prikey = null;
		Integer i = indictmentInfoDao.insertPrikey(indictmentInfo);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = indictmentInfo.getId_prikey();
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
	public Integer addBatch(List<IndictmentInfo> list) throws Exception {
		Integer i = indictmentInfoDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param indictmentInfo
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(IndictmentInfo indictmentInfo) throws Exception {
		Integer i = indictmentInfoDao.delete(indictmentInfo);
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
		Integer i = indictmentInfoDao.deleteOne(id);
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
		Integer i = indictmentInfoDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param indictmentInfo
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(IndictmentInfo indictmentInfo) throws Exception {
		Integer i = indictmentInfoDao.update(indictmentInfo);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param indictmentInfo
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(IndictmentInfo indictmentInfo) throws Exception {
		Integer i = indictmentInfoDao.updateEmpty(indictmentInfo);
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
	public IndictmentInfo getById(Integer id) throws Exception {
		return indictmentInfoDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param indictmentInfo
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public IndictmentInfo getByField(IndictmentInfo indictmentInfo) throws Exception {
		return indictmentInfoDao.findByField(indictmentInfo);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param indictmentInfo
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<IndictmentInfo> getAll(IndictmentInfo indictmentInfo) throws Exception {
		return indictmentInfoDao.findAll(indictmentInfo);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param indictmentInfo
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(IndictmentInfo indictmentInfo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = indictmentInfoDao.findPagingCount(indictmentInfo);
		List<IndictmentInfo> list = indictmentInfoDao.findPagingData(indictmentInfo);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param indictmentInfo
	 * @return
	 * @throws Exception
	 */
	public List<IndictmentInfo> getTreeAll(IndictmentInfo indictmentInfo) throws Exception {
		return indictmentInfoDao.findTreeData(indictmentInfo);
	}

	public Integer findPagingCountByLoanCon(LoanInfoBean loanInfoBean) throws Exception{
		return indictmentInfoDao.findPagingCountByLoanCon(loanInfoBean);
	}

	public List<LoanInfoBean> findPagingDataByLoanCon(LoanInfoBean loanInfoBean) throws Exception{
		return indictmentInfoDao.findPagingDataByLoanCon(loanInfoBean);
	}
}
