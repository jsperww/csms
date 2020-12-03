package com.hbsoft.csms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.LoanGuarantee;

@Mapper
public interface LoanGuaranteeDao {
	/**
	 * 插入一条数据
	 * 
	 * @param loanGuarantee
	 * @return
	 * @throws Exception
	 */
	public Integer insert(LoanGuarantee loanGuarantee) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanGuarantee
	 * @throws Exception
	 */
	public Integer insertPrikey(LoanGuarantee loanGuarantee) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<LoanGuarantee> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanGuarantee
	 * @return
	 * @throws Exception
	 */
	public Integer delete(LoanGuarantee loanGuarantee) throws Exception;

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
	 * @param loanGuarantee
	 * @return
	 * @throws Exception
	 */
	public Integer update(LoanGuarantee loanGuarantee) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanGuarantee
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(LoanGuarantee loanGuarantee) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LoanGuarantee findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanGuarantee
	 * @return
	 * @throws Exception
	 */
	public LoanGuarantee findByField(LoanGuarantee loanGuarantee) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanGuarantee
	 * @return
	 * @throws Exception
	 */
	public List<LoanGuarantee> findAll(LoanGuarantee loanGuarantee) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param loanGuarantee
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(LoanGuarantee loanGuarantee) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param loanGuarantee
	 * @return
	 * @throws Exception
	 */
	public List<LoanGuarantee> findPagingData(LoanGuarantee loanGuarantee) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param loanGuarantee
	 * @return
	 * @throws Exception
	 */
	public List<LoanGuarantee> findTreeData(LoanGuarantee loanGuarantee) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;
}
