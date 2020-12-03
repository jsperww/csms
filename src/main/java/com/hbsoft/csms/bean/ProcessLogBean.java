package com.hbsoft.csms.bean;

import java.util.Date;
import java.util.List;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;

import lombok.Data;

@Data
@TableName("k_process_log")
public class ProcessLogBean extends APojo{
	 
	@FieldParam(value = "ID_PRIKEY", fieldType = FieldType.prikeyIncrement)
	    private Integer id_prikey;

		@FieldParam(value = "流程id")
	    private Integer pd_id_prikey;

	    @FieldParam(value = "业务id")
	    private String businessId;
	    
	    @FieldParam(value = "节点id")
	    private Integer nodeId;

	   @FieldParam(association = true)
	    private String nodeName;

		@FieldParam(value = "流程属性")
		private String processAttribute;

		@FieldParam(value = "下一节点")
		private Integer nexstNode;

	    @FieldParam(value = "操作类型")
	    private Integer operateType;

	    @FieldParam(association = true)
	    private String operateTypeName;
	    
	    @FieldParam(value = "备注")
	    private String  remark;

	    @FieldParam(value = "操作人")
	    private String operator;
	    
	    @FieldParam(value = "操作时间")
	    private Date operateTime;

	    @FieldParam(association = true)
	    private List<ProcessFileBean> processFileList;
}
