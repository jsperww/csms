package com.hbsoft.csms.dao.service;

import com.hb.util.DateUtil;
import com.hb.util.V1Pass;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.LoanWarnInfoDao;
import com.hbsoft.csms.bean.LoanWarnInfo;

@Service
public class LoanWarnInfoDaoService {
	@Autowired
	private LoanWarnInfoDao loanWarnInfoDao;

	/**
	 * 插入一条数据
	 * 
	 * @param loanWarnInfo
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(LoanWarnInfo loanWarnInfo) throws Exception {
		Integer i = loanWarnInfoDao.insert(loanWarnInfo);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanWarnInfo
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(LoanWarnInfo loanWarnInfo) throws Exception {
		Integer prikey = null;
		Integer i = loanWarnInfoDao.insertPrikey(loanWarnInfo);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = loanWarnInfo.getId_prikey();
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
	public Integer addBatch(List<LoanWarnInfo> list) throws Exception {
		Integer i = loanWarnInfoDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanWarnInfo
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(LoanWarnInfo loanWarnInfo) throws Exception {
		Integer i = loanWarnInfoDao.delete(loanWarnInfo);
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
		Integer i = loanWarnInfoDao.deleteOne(id);
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
		Integer i = loanWarnInfoDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param loanWarnInfo
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(LoanWarnInfo loanWarnInfo) throws Exception {
		Integer i = loanWarnInfoDao.update(loanWarnInfo);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanWarnInfo
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(LoanWarnInfo loanWarnInfo) throws Exception {
		Integer i = loanWarnInfoDao.updateEmpty(loanWarnInfo);
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
	public LoanWarnInfo getById(Integer id) throws Exception {
		return loanWarnInfoDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanWarnInfo
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public LoanWarnInfo getByField(LoanWarnInfo loanWarnInfo) throws Exception {
		return loanWarnInfoDao.findByField(loanWarnInfo);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanWarnInfo
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<LoanWarnInfo> getAll(LoanWarnInfo loanWarnInfo) throws Exception {
		return loanWarnInfoDao.findAll(loanWarnInfo);
	}

	public List<LoanWarnInfo> findUnSendMsg(LoanWarnInfo loanWarnInfo) throws Exception{
		return loanWarnInfoDao.findUnSendMsg(loanWarnInfo);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param loanWarnInfo
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(LoanWarnInfo loanWarnInfo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = loanWarnInfoDao.findPagingCount(loanWarnInfo);
		List<LoanWarnInfo> list = loanWarnInfoDao.findPagingData(loanWarnInfo);
		for(LoanWarnInfo li : list){
			if(StringUtils.isNotBlank(li.getName())){
				li.setName(V1Pass.pass_decode(li.getName()));
			}
			if(ObjectUtils.isNotEmpty(li.getWarnDate())){
				li.setWarnDateStr(DateUtil.formatDate(li.getWarnDate(),"yyyy-MM-dd"));
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
	 * @param loanWarnInfo
	 * @return
	 * @throws Exception
	 */
	public List<LoanWarnInfo> getTreeAll(LoanWarnInfo loanWarnInfo) throws Exception {
		return loanWarnInfoDao.findTreeData(loanWarnInfo);
	}
}
