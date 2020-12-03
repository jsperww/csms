package com.hbsoft.csms.dao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.exception.SqlNotChangeException;
import com.hbsoft.csms.bean.AreaInfoBean;
import com.hbsoft.csms.dao.AreaInfoBeanDao;

@Service
public class AreaInfoBeanDaoService {
	@Autowired
	private AreaInfoBeanDao areaInfoBeanDao;

	/**
	 * 插入一条数据
	 * 
	 * @param areaInfoBean
	 * @return 返回是否插入成功
	 * @throws Exception
	 */
	public Boolean add(AreaInfoBean areaInfoBean) throws Exception {
		Integer i = areaInfoBeanDao.insert(areaInfoBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return true;
	}

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param areaInfoBean
	 * @return 返回主键
	 * @throws Exception
	 */
	public Integer addPrikey(AreaInfoBean areaInfoBean) throws Exception {
		Integer prikey = null;
		Integer i = areaInfoBeanDao.insertPrikey(areaInfoBean);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		Object o = areaInfoBean.getId_prikey();
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
	public Integer addBatch(List<AreaInfoBean> list) throws Exception {
		Integer i = areaInfoBeanDao.insertBatch(list);
		if (i == 0) {
			throw new SqlNotChangeException("插入失败");
		}
		return i;
	}

	/**
	 * 按给定条件删除记录
	 * 
	 * @param areaInfoBean
	 * @return 返回删除行数
	 * @throws Exception
	 */
	public Integer remove(AreaInfoBean areaInfoBean) throws Exception {
		Integer i = areaInfoBeanDao.delete(areaInfoBean);
		if (i == 0) {
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
		Integer i = areaInfoBeanDao.deleteOne(id);
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
		Integer i = areaInfoBeanDao.deleteBatch(ids);
		if (i == 0) {
			throw new SqlNotChangeException("删除失败");
		}
		return i;
	}

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param areaInfoBean
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean set(AreaInfoBean areaInfoBean) throws Exception {
		Integer i = areaInfoBeanDao.update(areaInfoBean);
		if (i == 0) {
			throw new SqlNotChangeException("修改失败");
		}
		return true;
	}

	/**
	 * 根据主键清空字段
	 * 
	 * @param areaInfoBean
	 *            传入字段非空的清空,主键不能为空
	 * @return 返回是否修改成功
	 * @throws Exception
	 */
	public Boolean setEmpty(AreaInfoBean areaInfoBean) throws Exception {
		Integer i = areaInfoBeanDao.updateEmpty(areaInfoBean);
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
	public AreaInfoBean getById(Integer id) throws Exception {
		return areaInfoBeanDao.findById(id);
	}

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param areaInfoBean
	 * @return 返回单条记录
	 * @throws Exception
	 */
	public AreaInfoBean getByField(AreaInfoBean areaInfoBean) throws Exception {
		return areaInfoBeanDao.findByField(areaInfoBean);
	}

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param areaInfoBean
	 * @return 返回多条记录
	 * @throws Exception
	 */
	public List<AreaInfoBean> getAll(AreaInfoBean areaInfoBean) throws Exception {
		return areaInfoBeanDao.findAll(areaInfoBean);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param areaInfoBean
	 * @return 返回分页数据
	 * @throws Exception
	 */
	public Map<String, Object> getPagingData(AreaInfoBean areaInfoBean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = areaInfoBeanDao.findPagingCount(areaInfoBean);
		List<AreaInfoBean> list = areaInfoBeanDao.findPagingData(areaInfoBean);
		map.put("code", 0);
		map.put("msg", "获取成功");
		map.put("count", count);
		map.put("data", list);
		return map;
	}

	/**
	 * 获取树形数据
	 * 
	 * @param areaInfoBean
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getTreeAll(AreaInfoBean areaInfoBean) throws Exception {
		return areaInfoBeanDao.findTreeData(areaInfoBean);
	}
	
	public String getTreeNextMax(String id) throws Exception{
		return areaInfoBeanDao.findTreeNextMax(id);
	}

	public List<Map<String, Object>> selectMin() throws Exception {
		return areaInfoBeanDao.selectMin();
	}

	public List<Map<String, Object>> selectSon(String pId) throws Exception {
		return areaInfoBeanDao.selectSon(pId);
	}
	

	public Boolean isParent(String pId) throws Exception {
		List<Map<String, Object>> list = selectSon(pId);
		if (null != list && list.size() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public Integer delArea(String id) throws Exception{
		return areaInfoBeanDao.delArea(id);
	}
	
	public List<Map<String,Object>> findCounty(){
		return areaInfoBeanDao.findCounty();
	}
	
	public List<Map<String,Object>> findTownVillage(String code){
		return areaInfoBeanDao.findTownVillage(code);
	}
}
