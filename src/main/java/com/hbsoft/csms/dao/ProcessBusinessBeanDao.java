package com.hbsoft.csms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.ProcessBusinessBean;

@Mapper
public interface ProcessBusinessBeanDao {
	/**
	 * 插入一条数据
	 * 
	 * @param processBusinessBean
	 * @return
	 * @throws Exception
	 */
	public Integer insert(ProcessBusinessBean processBusinessBean) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param processBusinessBean
	 * @throws Exception
	 */
	public Integer insertPrikey(ProcessBusinessBean processBusinessBean) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<ProcessBusinessBean> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param processBusinessBean
	 * @return
	 * @throws Exception
	 */
	public Integer delete(ProcessBusinessBean processBusinessBean) throws Exception;

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
	 * @param processBusinessBean
	 * @return
	 * @throws Exception
	 */
	public Integer update(ProcessBusinessBean processBusinessBean) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param processBusinessBean
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(ProcessBusinessBean processBusinessBean) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProcessBusinessBean findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param processBusinessBean
	 * @return
	 * @throws Exception
	 */
	public ProcessBusinessBean findByField(ProcessBusinessBean processBusinessBean) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param processBusinessBean
	 * @return
	 * @throws Exception
	 */
	public List<ProcessBusinessBean> findAll(ProcessBusinessBean processBusinessBean) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param processBusinessBean
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(ProcessBusinessBean processBusinessBean) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param processBusinessBean
	 * @return
	 * @throws Exception
	 */
	public List<ProcessBusinessBean> findPagingData(ProcessBusinessBean processBusinessBean) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param processBusinessBean
	 * @return
	 * @throws Exception
	 */
	public List<ProcessBusinessBean> findTreeData(ProcessBusinessBean processBusinessBean) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;


	public ProcessBusinessBean findByProcessStatusDingAndBack(ProcessBusinessBean processBusinessBean);


	public List<ProcessBusinessBean> findNotDoingAndCancelAll(ProcessBusinessBean pb);
}
