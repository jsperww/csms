package com.hbsoft.csms.dao;

import com.hbsoft.dingding.bean.DingDingUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DingDingUserInfoDao {
	/**
	 * 插入一条数据
	 * 
	 * @param dingDingUserInfo
	 * @return
	 * @throws Exception
	 */
	public Integer insert(DingDingUserInfo dingDingUserInfo) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param dingDingUserInfo
	 * @throws Exception
	 */
	public Integer insertPrikey(DingDingUserInfo dingDingUserInfo) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<DingDingUserInfo> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param dingDingUserInfo
	 * @return
	 * @throws Exception
	 */
	public Integer delete(DingDingUserInfo dingDingUserInfo) throws Exception;

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
	 * @param dingDingUserInfo
	 * @return
	 * @throws Exception
	 */
	public Integer update(DingDingUserInfo dingDingUserInfo) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param dingDingUserInfo
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(DingDingUserInfo dingDingUserInfo) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DingDingUserInfo findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param dingDingUserInfo
	 * @return
	 * @throws Exception
	 */
	public DingDingUserInfo findByField(DingDingUserInfo dingDingUserInfo) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param dingDingUserInfo
	 * @return
	 * @throws Exception
	 */
	public List<DingDingUserInfo> findAll(DingDingUserInfo dingDingUserInfo) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param dingDingUserInfo
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(DingDingUserInfo dingDingUserInfo) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param dingDingUserInfo
	 * @return
	 * @throws Exception
	 */
	public List<DingDingUserInfo> findPagingData(DingDingUserInfo dingDingUserInfo) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param dingDingUserInfo
	 * @return
	 * @throws Exception
	 */
	public List<DingDingUserInfo> findTreeData(DingDingUserInfo dingDingUserInfo) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;
}
