package com.hbsoft.csms.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;

import lombok.Data;

@Data
@TableName("k_process_business")
public class ProcessBusinessBean extends APojo {

	@FieldParam(fieldType = FieldType.prikeyIncrement)
	private Integer id_prikey;

	@FieldParam(value = "业务id")
	private String businessId;

	@FieldParam(value = "业务编号")
	private String businessCode;

	@FieldParam(value = "流程属性")
	private String processAttribute;

	@FieldParam(value = "当前节点")
	private Integer nodeId;

	@FieldParam(value = "节点前置条件")
	private String precondition;

	@FieldParam(value = "流程状态")
	private String processStatus;

	@FieldParam("创建时间")
	private Date createOn;

	@FieldParam("创建者")
	private String createBy;

	@FieldParam("修改时间")
	private Date updateOn;

	@FieldParam("修改者")
	private String updateBy;

	@FieldParam(association = true)
	private List<ProcessLogBean> logList;

	@FieldParam(association = true)
	private String notProcessStatus;

	@FieldParam(association = true)
	private String processAttributeName;
}
