package com.hbsoft.csms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.LoanCountInterestLog;

@Mapper
public interface LoanCountInterestLogDao {
	/**
	 * 插入一条数据
	 * 
	 * @param loanCountInterestLog
	 * @return
	 * @throws Exception
	 */
	public Integer insert(LoanCountInterestLog loanCountInterestLog) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanCountInterestLog
	 * @throws Exception
	 */
	public Integer insertPrikey(LoanCountInterestLog loanCountInterestLog) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<LoanCountInterestLog> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanCountInterestLog
	 * @return
	 * @throws Exception
	 */
	public Integer delete(LoanCountInterestLog loanCountInterestLog) throws Exception;

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
	 * @param loanCountInterestLog
	 * @return
	 * @throws Exception
	 */
	public Integer update(LoanCountInterestLog loanCountInterestLog) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanCountInterestLog
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(LoanCountInterestLog loanCountInterestLog) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LoanCountInterestLog findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanCountInterestLog
	 * @return
	 * @throws Exception
	 */
	public LoanCountInterestLog findByField(LoanCountInterestLog loanCountInterestLog) throws Exception;


	public LoanCountInterestLog findByLoanContractNumAndCountDate(LoanCountInterestLog loanCountInterestLog);

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanCountInterestLog
	 * @return
	 * @throws Exception
	 */
	public List<LoanCountInterestLog> findAll(LoanCountInterestLog loanCountInterestLog) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param loanCountInterestLog
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(LoanCountInterestLog loanCountInterestLog) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param loanCountInterestLog
	 * @return
	 * @throws Exception
	 */
	public List<LoanCountInterestLog> findPagingData(LoanCountInterestLog loanCountInterestLog) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param loanCountInterestLog
	 * @return
	 * @throws Exception
	 */
	public List<LoanCountInterestLog> findTreeData(LoanCountInterestLog loanCountInterestLog) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;


	void insertDataFromLoanCountInterestLogTemp(@Param("loanContractNum") String idloanContractNum);

	public Integer deleteByLoanContractNumAndCountDate(LoanCountInterestLog loanCountInterestLog);
}
