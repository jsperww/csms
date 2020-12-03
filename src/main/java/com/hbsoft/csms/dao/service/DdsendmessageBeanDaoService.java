package com.hbsoft.csms.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.dao.DdsendmessageBeanDao;
import com.hbsoft.csms.bean.DdsendmessageBean;

@Service
public class DdsendmessageBeanDaoService {
	@Autowired
	private DdsendmessageBeanDao ddsendmessageBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param ddsendmessageBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(DdsendmessageBean ddsendmessageBean) throws Exception {
		Integer i = ddsendmessageBeanDao.insert(ddsendmessageBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param ddsendmessageBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(DdsendmessageBean ddsendmessageBean) throws Exception {
		Integer prikey = null;
		Integer i = ddsendmessageBeanDao.insertPrikey(ddsendmessageBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = ddsendmessageBean.getId_prikey();
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
	public Integer addBatch(List<DdsendmessageBean> list) throws Exception {
		Integer i = ddsendmessageBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param ddsendmessageBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(DdsendmessageBean ddsendmessageBean) throws Exception {
		Integer i = ddsendmessageBeanDao.delete(ddsendmessageBean);
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
		Integer i = ddsendmessageBeanDao.deleteOne(id);
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
		Integer i = ddsendmessageBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param ddsendmessageBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(DdsendmessageBean ddsendmessageBean) throws Exception {
		Integer i = ddsendmessageBeanDao.update(ddsendmessageBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param ddsendmessageBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(DdsendmessageBean ddsendmessageBean) throws Exception {
		Integer i = ddsendmessageBeanDao.updateEmpty(ddsendmessageBean);
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
	public DdsendmessageBean getById(Integer id) throws Exception {
		return ddsendmessageBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param ddsendmessageBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public DdsendmessageBean getByField(DdsendmessageBean ddsendmessageBean) throws Exception {
		return ddsendmessageBeanDao.findByField(ddsendmessageBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param ddsendmessageBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<DdsendmessageBean> getAll(DdsendmessageBean ddsendmessageBean) throws Exception {
		return ddsendmessageBeanDao.findAll(ddsendmessageBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param ddsendmessageBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(DdsendmessageBean ddsendmessageBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = ddsendmessageBeanDao.findPagingCount(ddsendmessageBean);
		List<DdsendmessageBean> list = ddsendmessageBeanDao.findPagingData(ddsendmessageBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取树形数据
	 * 
	 * @param ddsendmessageBean
	 * @return
	 * @throws Exception
	 */
	public List<DdsendmessageBean> getTreeAll(DdsendmessageBean ddsendmessageBean) throws Exception {
		return ddsendmessageBeanDao.findTreeData(ddsendmessageBean);
	}
}
