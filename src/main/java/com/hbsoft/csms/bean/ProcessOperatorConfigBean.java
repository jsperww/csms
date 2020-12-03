package com.hbsoft.csms.bean;

import java.util.Date;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;

import lombok.Data;
@Data
@TableName("k_process_operator_config")
public class ProcessOperatorConfigBean extends APojo{

	@FieldParam(value = "ID_PRIKEY", fieldType = FieldType.prikeyIncrement)
	private Integer id_prikey;

	@FieldParam(value = "节点id")
	private Integer nodeId;

	@FieldParam(value = "角色类型")
	private String roleType;

	@FieldParam(value = "角色")
	private String role;

	@FieldParam("创建时间")
	private Date createOn;

	@FieldParam("创建者")
	private String createBy;

	@FieldParam("修改时间")
	private Date updateOn;

	@FieldParam("修改者")
	private String updateBy;
}
