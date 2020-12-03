package com.hbsoft.csms.bean;

import java.util.Date;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;

import lombok.Data;

@Data
@TableName("k_process_file")
public class ProcessFileBean extends APojo {

	@FieldParam(value = "ID_PRIKEY", fieldType = FieldType.prikeyIncrement)
	private Integer id_prikey;

	@FieldParam("业务id")
	private String businessId;

	@FieldParam("节点id")
	private Integer nodeId;

	@FieldParam("附件类型")
	private String appendixType;

	@FieldParam("附件")
	private String appendix;

	@FieldParam("批次代码")
	private String batchCode;
	
	@FieldParam("操作人")
    private String operator;

    @FieldParam("操作时间")
    private Date operateTime;
}
