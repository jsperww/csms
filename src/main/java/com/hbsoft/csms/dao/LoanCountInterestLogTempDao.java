package com.hbsoft.csms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.LoanCountInterestLogTemp;

@Mapper
public interface LoanCountInterestLogTempDao {
	/**
	 * 插入一条数据
	 * 
	 * @param loanCountInterestLogTemp
	 * @return
	 * @throws Exception
	 */
	public Integer insert(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanCountInterestLogTemp
	 * @throws Exception
	 */
	public Integer insertPrikey(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<LoanCountInterestLogTemp> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanCountInterestLogTemp
	 * @return
	 * @throws Exception
	 */
	public Integer delete(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception;

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
	 * @param loanCountInterestLogTemp
	 * @return
	 * @throws Exception
	 */
	public Integer update(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanCountInterestLogTemp
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LoanCountInterestLogTemp findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanCountInterestLogTemp
	 * @return
	 * @throws Exception
	 */
	public LoanCountInterestLogTemp findByField(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanCountInterestLogTemp
	 * @return
	 * @throws Exception
	 */
	public List<LoanCountInterestLogTemp> findAll(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param loanCountInterestLogTemp
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param loanCountInterestLogTemp
	 * @return
	 * @throws Exception
	 */
	public List<LoanCountInterestLogTemp> findPagingData(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param loanCountInterestLogTemp
	 * @return
	 * @throws Exception
	 */
	public List<LoanCountInterestLogTemp> findTreeData(LoanCountInterestLogTemp loanCountInterestLogTemp) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;
}
