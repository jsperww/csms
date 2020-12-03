package com.hbsoft.csms.dao.service;

import com.hb.util.DateUtil;
import com.hb.util.V1Pass;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.vo.LoanInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.LoanVisitBeanDao;
import com.hbsoft.csms.bean.LoanVisitBean;

@Service
public class LoanVisitBeanDaoService {
	@Autowired
	private LoanVisitBeanDao loanVisitBeanDao;


	/**
	 * 插入一条数据
	 * 
	 * @param loanVisitBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(LoanVisitBean loanVisitBean) throws Exception {
		Integer i = loanVisitBeanDao.insert(loanVisitBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanVisitBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(LoanVisitBean loanVisitBean) throws Exception {
		Integer prikey = null;
		Integer i = loanVisitBeanDao.insertPrikey(loanVisitBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = loanVisitBean.getId_prikey();
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
	public Integer addBatch(List<LoanVisitBean> list) throws Exception {
		Integer i = loanVisitBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanVisitBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(LoanVisitBean loanVisitBean) throws Exception {
		Integer i = loanVisitBeanDao.delete(loanVisitBean);
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
		Integer i = loanVisitBeanDao.deleteOne(id);
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
		Integer i = loanVisitBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param loanVisitBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(LoanVisitBean loanVisitBean) throws Exception {
		Integer i = loanVisitBeanDao.update(loanVisitBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanVisitBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(LoanVisitBean loanVisitBean) throws Exception {
		Integer i = loanVisitBeanDao.updateEmpty(loanVisitBean);
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
	public LoanVisitBean getById(Integer id) throws Exception {
		return loanVisitBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanVisitBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public LoanVisitBean getByField(LoanVisitBean loanVisitBean) throws Exception {
		return loanVisitBeanDao.findByField(loanVisitBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanVisitBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<LoanVisitBean> getAll(LoanVisitBean loanVisitBean) throws Exception {
		return loanVisitBeanDao.findAll(loanVisitBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param loanVisitBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(LoanVisitBean loanVisitBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanVisitBeanDao.findPagingCount(loanVisitBean);
		List<LoanVisitBean> list = loanVisitBeanDao.findPagingData(loanVisitBean);
		for(LoanVisitBean lv : list){
			if(lv.getVisitDate() != null){
				lv.setVisitDateStr(DateUtil.formatDate(lv.getVisitDate(),"yyyy-MM-dd"));
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
	 * @param loanVisitBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanVisitBean> getTreeAll(LoanVisitBean loanVisitBean) throws Exception {
		return loanVisitBeanDao.findTreeData(loanVisitBean);
	}


	/**
	 * 我的外访
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMyVisitPagingData(LoanInfoVo vo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanVisitBeanDao.findMyVisitPagingCount(vo);
		List<LoanInfoVo> list = loanVisitBeanDao.findMyVisitPagingData(vo);
		for(LoanInfoVo loanInfoBean1 : list){
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
	 * 未回访客户
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMyUnVisitPagingData(LoanInfoVo vo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanVisitBeanDao.findMyUnVisitPagingCount(vo);
		List<LoanInfoVo> list = loanVisitBeanDao.findMyUnVisitPagingData(vo);
		for(LoanInfoVo loanInfoBean1 : list){
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


	public Integer getVisitNum(LoanInfoVo param) {
		return loanVisitBeanDao.findMyVisitPagingCount(param);
	}

}
