package com.hbsoft.csms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.ConfigurationBean;

@Mapper
public interface ConfigurationBeanDao {
	/**
	 * 插入一条数据
	 * 
	 * @param configurationBean
	 * @return
	 * @throws Exception
	 */
	public Integer insert(ConfigurationBean configurationBean) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param configurationBean
	 * @throws Exception
	 */
	public Integer insertPrikey(ConfigurationBean configurationBean) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<ConfigurationBean> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param configurationBean
	 * @return
	 * @throws Exception
	 */
	public Integer delete(ConfigurationBean configurationBean) throws Exception;

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
	 * @param configurationBean
	 * @return
	 * @throws Exception
	 */
	public Integer update(ConfigurationBean configurationBean) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param configurationBean
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(ConfigurationBean configurationBean) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ConfigurationBean findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param configurationBean
	 * @return
	 * @throws Exception
	 */
	public ConfigurationBean findByField(ConfigurationBean configurationBean) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param configurationBean
	 * @return
	 * @throws Exception
	 */
	public List<ConfigurationBean> findAll(ConfigurationBean configurationBean) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param configurationBean
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(ConfigurationBean configurationBean) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param configurationBean
	 * @return
	 * @throws Exception
	 */
	public List<ConfigurationBean> findPagingData(ConfigurationBean configurationBean) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param configurationBean
	 * @return
	 * @throws Exception
	 */
	public List<ConfigurationBean> findTreeData(ConfigurationBean configurationBean) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;

    String selectMaxCode();
}
