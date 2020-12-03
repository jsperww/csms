package com.hbsoft.csms.bean;

import java.util.Date;
import java.util.List;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import com.hbsoft.csms.vo.LoanInfoUpdateContentVo;

import lombok.Data;

@Data
@TableName("k_loan_info_update")
public class LoanInfoUpdateContent extends APojo {

	@FieldParam(fieldType = FieldType.prikeyIncrement)
	private Integer id_prikey;

	@FieldParam("贷款合同号")
	private String loanContractNum;
	
	@FieldParam("日志id")
	private String logId;


	@FieldParam("原始内容")
	private String oldContent;

	@FieldParam("修改内容")
	private String updateContent;

	@FieldParam(association = true)
	private String status;
	
	@FieldParam(association = true)
	private Integer nodeId;
	
	@FieldParam(association = true)
	private String remark;
}
