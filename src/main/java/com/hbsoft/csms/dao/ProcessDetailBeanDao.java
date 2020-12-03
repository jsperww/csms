package com.hbsoft.csms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.ProcessDetailBean;

@Mapper
public interface ProcessDetailBeanDao {
	/**
	 * 插入一条数据
	 * 
	 * @param processDetailBean
	 * @return
	 * @throws Exception
	 */
	public Integer insert(ProcessDetailBean processDetailBean) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param processDetailBean
	 * @throws Exception
	 */
	public Integer insertPrikey(ProcessDetailBean processDetailBean) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<ProcessDetailBean> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param processDetailBean
	 * @return
	 * @throws Exception
	 */
	public Integer delete(ProcessDetailBean processDetailBean) throws Exception;

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
	 * @param processDetailBean
	 * @return
	 * @throws Exception
	 */
	public Integer update(ProcessDetailBean processDetailBean) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param processDetailBean
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(ProcessDetailBean processDetailBean) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProcessDetailBean findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param processDetailBean
	 * @return
	 * @throws Exception
	 */
	public ProcessDetailBean findByField(ProcessDetailBean processDetailBean) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param processDetailBean
	 * @return
	 * @throws Exception
	 */
	public List<ProcessDetailBean> findAll(ProcessDetailBean processDetailBean) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param processDetailBean
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(ProcessDetailBean processDetailBean) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param processDetailBean
	 * @return
	 * @throws Exception
	 */
	public List<ProcessDetailBean> findPagingData(ProcessDetailBean processDetailBean) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param processDetailBean
	 * @return
	 * @throws Exception
	 */
	public List<ProcessDetailBean> findTreeData(ProcessDetailBean processDetailBean) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;


	public ProcessDetailBean findMinNodeIdByNodeType(@Param("nodeType") String nodeType);

	public ProcessDetailBean findMaxNodeIdByNodeType(@Param("nodeType") String nodeType);

	public Map<String,Object> findMaxNodeAndMinNodeByNodeType(@Param("nodeType") String nodeType);
}
