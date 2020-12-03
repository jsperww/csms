package com.hbsoft.csms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.DictBean;

@Mapper
public interface DictBeanDao {
	/**
	 * 插入一条数据
	 * 
	 * @param dictBean
	 * @return
	 * @throws Exception
	 */
	public Integer insert(DictBean dictBean) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param dictBean
	 * @throws Exception
	 */
	public Integer insertPrikey(DictBean dictBean) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<DictBean> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param dictBean
	 * @return
	 * @throws Exception
	 */
	public Integer delete(DictBean dictBean) throws Exception;

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
	 * @param dictBean
	 * @return
	 * @throws Exception
	 */
	public Integer update(DictBean dictBean) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param dictBean
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(DictBean dictBean) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DictBean findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param dictBean
	 * @return
	 * @throws Exception
	 */
	public DictBean findByField(DictBean dictBean) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param dictBean
	 * @return
	 * @throws Exception
	 */
	public List<DictBean> findAll(DictBean dictBean) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param dictBean
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(DictBean dictBean) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param dictBean
	 * @return
	 * @throws Exception
	 */
	public List<DictBean> findPagingData(DictBean dictBean) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param dictBean
	 * @return
	 * @throws Exception
	 */
	public List<DictBean> findTreeData(DictBean dictBean) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;


	void createDictTable(@Param("tableName") String tableName);

	void updateDictTable(DictBean dictBean);

	void insertDictTable(DictBean dictBean);
	 
	void delDictTable(DictBean dictBean);

	List<DictBean> selectDictTableContent(@Param("tableName") String tableName);

	DictBean  selectDictDetailByCode(@Param("tableName") String tableName,@Param("code") String code);

	DictBean selectDictDetailByName(@Param("tableName") String tableName,@Param("name") String name);
}
