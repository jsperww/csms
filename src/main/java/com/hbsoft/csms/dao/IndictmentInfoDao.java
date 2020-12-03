package com.hbsoft.csms.dao;

import com.hbsoft.csms.bean.IndictmentInfo;
import com.hbsoft.csms.bean.LoanInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IndictmentInfoDao {
	/**
	 * 插入一条数据
	 * 
	 * @param indictmentInfo
	 * @return
	 * @throws Exception
	 */
	public Integer insert(IndictmentInfo indictmentInfo) throws Exception;

	/**
	 * 插入一条数据返回主键,主键在传入的对象里面
	 * 
	 * @param indictmentInfo
	 * @throws Exception
	 */
	public Integer insertPrikey(IndictmentInfo indictmentInfo) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertBatch(List<IndictmentInfo> list) throws Exception;

	/**
	 * 按给定条件删除记录
	 * 
	 * @param indictmentInfo
	 * @return
	 * @throws Exception
	 */
	public Integer delete(IndictmentInfo indictmentInfo) throws Exception;

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
	 * @param indictmentInfo
	 * @return
	 * @throws Exception
	 */
	public Integer update(IndictmentInfo indictmentInfo) throws Exception;

	/**
	 * 根据主键清空字段
	 * 
	 * @param indictmentInfo
	 *            传入字段非空的清空,主键不能为空
	 * @return
	 * @throws Exception
	 */
	public Integer updateEmpty(IndictmentInfo indictmentInfo) throws Exception;

	/**
	 * 根据主键查询单条记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IndictmentInfo findById(Integer id) throws Exception;

	/**
	 * 根据传入字段查询单条记录
	 * 
	 * @param indictmentInfo
	 * @return
	 * @throws Exception
	 */
	public IndictmentInfo findByField(IndictmentInfo indictmentInfo) throws Exception;

	/**
	 * 根据传入字段查询多条记录
	 * 
	 * @param indictmentInfo
	 * @return
	 * @throws Exception
	 */
	public List<IndictmentInfo> findAll(IndictmentInfo indictmentInfo) throws Exception;

	/**
	 * 分页获取总数
	 * 
	 * @param indictmentInfo
	 * @return
	 * @throws Exception
	 */
	public Integer findPagingCount(IndictmentInfo indictmentInfo) throws Exception;

	/**
	 * 分页获取数据
	 * 
	 * @param indictmentInfo
	 * @return
	 * @throws Exception
	 */
	public List<IndictmentInfo> findPagingData(IndictmentInfo indictmentInfo) throws Exception;

	/**
	 * 获取树形格式数据
	 * 
	 * @param indictmentInfo
	 * @return
	 * @throws Exception
	 */
	public List<IndictmentInfo> findTreeData(IndictmentInfo indictmentInfo) throws Exception;

	/**
	 * 获取树形下级最大值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findTreeNextMax(@Param("id") String id) throws Exception;

    Integer findPagingCountByLoanCon(LoanInfoBean loanInfoBean) throws Exception;

	List<LoanInfoBean> findPagingDataByLoanCon(LoanInfoBean loanInfoBean) throws Exception;
}
