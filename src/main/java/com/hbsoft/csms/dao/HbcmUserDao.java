package com.hbsoft.csms.dao;

import com.hbsoft.csms.bean.HbcmUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HbcmUserDao {
	/**
	 * 插入一条数据
	 * 
	 * @param hbcmUser
	 * @return
	 * @throws Exception
	 */
	public Integer insert(HbcmUser hbcmUser) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param hbcmUser
	 * @throws Exception
	 */
	public Integer insertPrikey(HbcmUser hbcmUser) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<HbcmUser> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param hbcmUser
	 * @return
	 * @throws Exception
	 */
	public Integer delete(HbcmUser hbcmUser) throws Exception;

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
	 * @param hbcmUser
	 * @return
	 * @throws Exception
	 */
	public Integer update(HbcmUser hbcmUser) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param hbcmUser
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(HbcmUser hbcmUser) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HbcmUser findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param hbcmUser
	 * @return
	 * @throws Exception
	 */
	public HbcmUser findByField(HbcmUser hbcmUser) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param hbcmUser
	 * @return
	 * @throws Exception
	 */
	public List<HbcmUser> findAll(HbcmUser hbcmUser) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param hbcmUser
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(HbcmUser hbcmUser) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param hbcmUser
	 * @return
	 * @throws Exception
	 */
	public List<HbcmUser> findPagingData(HbcmUser hbcmUser) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param hbcmUser
	 * @return
	 * @throws Exception
	 */
	public List<HbcmUser> findTreeData(HbcmUser hbcmUser) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;

	public String findViewByUserId(@Param("deptId") String deptId);

	public Map<String,Object> findDeptMananger(@Param("userId") String userId);

	public Map<String,Object> findDeptByDeptName(@Param("deptName") String deptName);
}
