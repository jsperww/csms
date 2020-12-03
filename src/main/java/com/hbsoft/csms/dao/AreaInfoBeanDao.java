package com.hbsoft.csms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.AreaInfoBean;

@Mapper
public interface AreaInfoBeanDao {
	/**
	 * 插入一条数据
	 * 
	 * @param areaInfoBean
	 * @return
	 * @throws Exception
	 */
	public Integer insert(AreaInfoBean areaInfoBean) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param areaInfoBean
	 * @throws Exception
	 */
	public Integer insertPrikey(AreaInfoBean areaInfoBean) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<AreaInfoBean> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param areaInfoBean
	 * @return
	 * @throws Exception
	 */
	public Integer delete(AreaInfoBean areaInfoBean) throws Exception;

	/**
	 * 按主键删除单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleteOne(Integer id) throws Exception;

	/**
	 * 按主键删除多条记录
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Integer deleteBatch(@Param("ids") List<Integer> ids) throws Exception;

	/**
	 * 根据主键或者外键修改记录
	 * 
	 * @param areaInfoBean
	 * @return
	 * @throws Exception
	 */
	public Integer update(AreaInfoBean areaInfoBean) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param areaInfoBean
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(AreaInfoBean areaInfoBean) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AreaInfoBean findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param areaInfoBean
	 * @return
	 * @throws Exception
	 */
	public AreaInfoBean findByField(AreaInfoBean areaInfoBean) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param areaInfoBean
	 * @return
	 * @throws Exception
	 */
	public List<AreaInfoBean> findAll(AreaInfoBean areaInfoBean) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param areaInfoBean
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(AreaInfoBean areaInfoBean) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param areaInfoBean
	 * @return
	 * @throws Exception
	 */
	public List<AreaInfoBean> findPagingData(AreaInfoBean areaInfoBean) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param areaInfoBean
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> findTreeData(AreaInfoBean areaInfoBean) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;
	
	public List<Map<String,Object>> selectMin() throws Exception;
	
	public List<Map<String,Object>> selectSon(@Param("pId") String pId) throws Exception;
	
	public Integer delArea(@Param("id") String id);
	
	public List<Map<String,Object>> findCounty();
	
	public List<Map<String,Object>> findTownVillage(@Param("code") String code);
	
}
