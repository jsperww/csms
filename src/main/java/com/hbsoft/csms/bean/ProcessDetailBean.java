package com.hbsoft.csms.bean;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@TableName("k_process_detail")
public class ProcessDetailBean extends APojo {

	@FieldParam(value = "ID_PRIKEY", fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam(value = "节点")
    private Integer nodeId;

    @NotBlank(message = "请输入节点名称")
    @FieldParam(value = "节点名称")
    private String nodeName;

    @NotBlank(message = "请输入节点类型")
    @FieldParam(value = "节点类型")
    private String nodeType;

    @FieldParam(value = "下一节点")
    private String nexstNode;

    @FieldParam(value = "下一节点条件")
    private String nexstNodeCondition;

    @FieldParam(value = "上一节点")
    private String lastNode;

    @FieldParam(value = "上一节点条件")
    private String lastNodeCondition;

    @FieldParam(value = "节点前置条件")
    private String precondition;

    @FieldParam(value = "节点处理方法")
    private String disposeMethod;
    
    @FieldParam("创建时间")
	private Date createOn;

	@FieldParam("创建者")
	private String createBy;

	@FieldParam("修改时间")
	private Date updateOn;

	@FieldParam("修改者")
	private String updateBy;
}
