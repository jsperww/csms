package com.hbsoft.csms.dao;

import com.hbsoft.csms.vo.LoanReportVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoanReportDao {

	List<Map<String,Object>> loanInfoReportByTeam(LoanReportVo vo);

	List<Map<String,Object>> loanRepayReportByTeam(LoanReportVo vo);

	Map<String,Object> loanRepayReportTotal(LoanReportVo vo);

	List<Map<String,Object>> loanVisitReportByTeam(LoanReportVo vo);

	Map<String,Object> loanVisitReportTotal(LoanReportVo vo);

	Map<String,Object> loanInfoReportTotal();

	List<Map<String, Object>> loanUpdateReportByTeam(LoanReportVo vo);

	Map<String,Object> loanUpdateReportTotal();

	Map<String,Object> loanInfoReportCountByTeam(LoanReportVo vo);

	
}
