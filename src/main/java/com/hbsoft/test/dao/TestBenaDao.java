package com.hbsoft.test.dao;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.test.bean.TestBena;

@Mapper
public interface TestBenaDao {
	/**
	 * 插入一条数据
	 * 
	 * @param testBena
	 * @return
	 * @throws Exception
	 */
	public Integer insert(TestBena testBena) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param testBena
	 * @throws Exception
	 */
	public Integer insertPrikey(TestBena testBena) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<TestBena> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param testBena
	 * @return
	 * @throws Exception
	 */
	public Integer delete(TestBena testBena) throws Exception;

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
	 * @param testBena
	 * @return
	 * @throws Exception
	 */
	public Integer update(TestBena testBena) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param testBena
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(TestBena testBena) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TestBena findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param testBena
	 * @return
	 * @throws Exception
	 */
	public TestBena findByField(TestBena testBena) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param testBena
	 * @return
	 * @throws Exception
	 */
	public List<TestBena> findAll(TestBena testBena) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param testBena
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(TestBena testBena) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param testBena
	 * @return
	 * @throws Exception
	 */
	public List<TestBena> findPagingData(TestBena testBena) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param testBena
	 * @return
	 * @throws Exception
	 */
	public List<TestBena> findTreeData(TestBena testBena) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;
}
