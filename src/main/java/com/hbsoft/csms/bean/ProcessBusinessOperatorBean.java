package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;


@Data
@TableName("k_process_business_operator")
public class ProcessBusinessOperatorBean extends APojo{

	@FieldParam(value = "ID_PRIKEY", fieldType = FieldType.prikeyIncrement)
	private Integer id_prikey;

	@FieldParam("业务流程id")
	private int pbId;

	@FieldParam("节点")
	private Integer node;

	@FieldParam("操作人")
	private String operator;
}
