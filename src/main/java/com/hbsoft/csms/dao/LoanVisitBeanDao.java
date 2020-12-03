package com.hbsoft.csms.dao;

import java.util.List;

import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.vo.LoanInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hbsoft.csms.bean.LoanVisitBean;

@Mapper
public interface LoanVisitBeanDao {
	/**
	 * 插入一条数据
	 * 
	 * @param loanVisitBean
	 * @return
	 * @throws Exception
	 */
	public Integer insert(LoanVisitBean loanVisitBean) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param loanVisitBean
	 * @throws Exception
	 */
	public Integer insertPrikey(LoanVisitBean loanVisitBean) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<LoanVisitBean> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param loanVisitBean
	 * @return
	 * @throws Exception
	 */
	public Integer delete(LoanVisitBean loanVisitBean) throws Exception;

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
	 * @param loanVisitBean
	 * @return
	 * @throws Exception
	 */
	public Integer update(LoanVisitBean loanVisitBean) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param loanVisitBean
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(LoanVisitBean loanVisitBean) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LoanVisitBean findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param loanVisitBean
	 * @return
	 * @throws Exception
	 */
	public LoanVisitBean findByField(LoanVisitBean loanVisitBean) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param loanVisitBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanVisitBean> findAll(LoanVisitBean loanVisitBean) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param loanVisitBean
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(LoanVisitBean loanVisitBean) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param loanVisitBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanVisitBean> findPagingData(LoanVisitBean loanVisitBean) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param loanVisitBean
	 * @return
	 * @throws Exception
	 */
	public List<LoanVisitBean> findTreeData(LoanVisitBean loanVisitBean) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;



	public List<LoanInfoVo> findMyVisitPagingData(LoanInfoVo vo);

	Integer findMyVisitPagingCount(LoanInfoVo vo);

	public List<LoanInfoVo> findMyUnVisitPagingData(LoanInfoVo vo);

	Integer findMyUnVisitPagingCount(LoanInfoVo vo);

	public Integer getVisitNumById(@Param("id") String id) throws Exception;
}
