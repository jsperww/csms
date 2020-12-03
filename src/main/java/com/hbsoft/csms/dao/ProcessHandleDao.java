package com.hbsoft.csms.dao;

import java.util.List;

import com.hbsoft.csms.bean.ProcessBusinessBean;
import org.apache.ibatis.annotations.Mapper;

import com.hbsoft.csms.vo.LoanInfoVo;

@Mapper
public interface ProcessHandleDao {

	public List<LoanInfoVo> findProcessBusinessPagingData(LoanInfoVo vo);
	
	public Integer findProcessBusinessPagingCount(LoanInfoVo vo);

	public List<LoanInfoVo> findProcessBusinessOperatePagingData(LoanInfoVo vo);

	public Integer findProcessBusinessOperatePagingCount(LoanInfoVo vo);

	public List<LoanInfoVo> findMyProcessBusinessPagingData(LoanInfoVo vo);

	public Integer findMyProcessBusinessPagingCount(LoanInfoVo vo);

	List<ProcessBusinessBean> findAllUnfinishedProcess();
}
