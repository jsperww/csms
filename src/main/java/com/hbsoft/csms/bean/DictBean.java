package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@TableName("d_dict")
public class DictBean extends APojo {

	@FieldParam(fieldType = FieldType.prikeyIncrement)
	private Integer id_prikey;

	@NotBlank(message = "请输入表名")
	@FieldParam("代码")
	private String code;

	@NotBlank(message = "请输入名称")
	@FieldParam("项目")
	private String name;

	@FieldParam("拼音")
	private String py;

	@FieldParam("备注")
	private String remark;

	@FieldParam("顺序")
	private Integer sort;

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

	@FieldParam(association = true)
	private String detailDictName;

}
