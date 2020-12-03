package com.hbsoft.csms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.LoanInfoBean;

@Mapper
public interface LoanInfoBeanDao {
	/**
	 * 插入一条数据
	 * 
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
	public Integer insert(LoanInfoBean loanInfoBean) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanInfoBean
	 * @throws Exception
	 */
	public Integer insertPrikey(LoanInfoBean loanInfoBean) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<LoanInfoBean> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
	public Integer delete(LoanInfoBean loanInfoBean) throws Exception;

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
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
	public Integer update(LoanInfoBean loanInfoBean) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanInfoBean
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(LoanInfoBean loanInfoBean) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LoanInfoBean findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
	public LoanInfoBean findByField(LoanInfoBean loanInfoBean) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanInfoBean> findAll(LoanInfoBean loanInfoBean) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(LoanInfoBean loanInfoBean) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanInfoBean> findPagingData(LoanInfoBean loanInfoBean) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanInfoBean> findTreeData(LoanInfoBean loanInfoBean) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;

	/**
	 * 添加数据备份
	 * @param loanInfoBean
	 * @return
	 */
    Integer addSourceLoan(LoanInfoBean loanInfoBean) throws Exception;
    
    
    List<LoanInfoBean> findAllData(LoanInfoBean loanInfoBean) throws Exception;

	/**
	 * 根据传入合同号更新表
	 * @param loanInfoBean
	 * @return
	 * @throws Exception
	 */
    Integer updateByConNum(LoanInfoBean loanInfoBean) throws Exception;


	List<LoanInfoBean>  findSharePagingData(LoanInfoBean loanInfoBean) throws Exception;

	Integer	findSharePagingCount(LoanInfoBean loanInfoBean) throws Exception;
}
