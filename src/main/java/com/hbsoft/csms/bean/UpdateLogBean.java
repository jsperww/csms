package com.hbsoft.csms.bean;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;

import lombok.Data;

@Data
@Mapper
@TableName("k_update_log")
public class UpdateLogBean extends APojo{

	@FieldParam(fieldType = FieldType.prikeyIncrement)
	private Integer id_prikey;
	
	@FieldParam("类型")
	private String type;
	
	@FieldParam("原始内容")
	private String oldContent;
	
	@FieldParam("修改内容")
	private String newContent;
	
	@FieldParam("创建时间")
	private Date createOn;

	@FieldParam("创建者")
	private String createBy;
}
