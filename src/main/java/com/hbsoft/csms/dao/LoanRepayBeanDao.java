package com.hbsoft.csms.dao;

import java.util.List;

import com.hb.bean.CallResult;
import com.hbsoft.csms.bean.LoanInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.LoanRepayBean;

@Mapper
public interface LoanRepayBeanDao {
	/**
	 * 插入一条数据
	 * 
	 * @param loanRepayBean
	 * @return
	 * @throws Exception
	 */
	public Integer insert(LoanRepayBean loanRepayBean) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanRepayBean
	 * @throws Exception
	 */
	public Integer insertPrikey(LoanRepayBean loanRepayBean) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<LoanRepayBean> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanRepayBean
	 * @return
	 * @throws Exception
	 */
	public Integer delete(LoanRepayBean loanRepayBean) throws Exception;

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
	 * @param loanRepayBean
	 * @return
	 * @throws Exception
	 */
	public Integer update(LoanRepayBean loanRepayBean) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanRepayBean
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(LoanRepayBean loanRepayBean) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LoanRepayBean findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanRepayBean
	 * @return
	 * @throws Exception
	 */
	public LoanRepayBean findByField(LoanRepayBean loanRepayBean) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanRepayBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanRepayBean> findAll(LoanRepayBean loanRepayBean) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param loanRepayBean
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(LoanRepayBean loanRepayBean) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param loanRepayBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanRepayBean> findPagingData(LoanRepayBean loanRepayBean) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param loanRepayBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanRepayBean> findTreeData(LoanRepayBean loanRepayBean) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;


	public List<LoanInfoBean> findRepayPagingData(LoanInfoBean loanInfoBean);


	public Integer findRepayPagingCount(LoanInfoBean loanInfoBean);


    public LoanRepayBean findLatestRepayRecord(@Param("loanContractNum") String loanContractNum);


	public LoanRepayBean findLatestRepayByLoanContractNumAndRepayType(LoanRepayBean loanRepayBean);
}
