package com.hbsoft.csms.bean;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;

import lombok.Data;

@Data
@TableName("d_area")
public class AreaInfoBean extends APojo{
	@FieldParam(fieldType = FieldType.prikeyIncrement)
	private Integer id_prikey;

	@FieldParam("代码")
	private String code;

	@FieldParam("项目")
	private String name;

	@FieldParam("备注")
	private String remark;

	@FieldParam("顺序")
	private Integer sort;
	
	@FieldParam("拼音")
	private String spell;

	@FieldParam(value = "停用")
	private Integer stop;

	@FieldParam(value = "属性")
	private String attribute;

	@FieldParam(value = "属性名称")
	private String attributeName;

	@FieldParam("创建时间")
	private Date createOn;

	@FieldParam("创建者")
	private String createBy;

	@FieldParam("修改时间")
	private Date updateOn;

	@FieldParam("修改者")
	private String updateBy;
}
