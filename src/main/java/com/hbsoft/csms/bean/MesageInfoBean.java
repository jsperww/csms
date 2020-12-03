package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("k_mesage_info")
public class MesageInfoBean extends APojo {

	@FieldParam(fieldType = FieldType.prikeyIncrement)
	private Integer id_prikey;

	@FieldParam("内容")
	private String content;

	@FieldParam("创建时间")
	private Date createOn;

	@FieldParam("创建者")
	private String createBy;

	@FieldParam(association = true, forkeyId = "创建者", forkeyName = "描述", forkeyRelationId = "名称", forkeyTableName = "hbcm.._USER")
	private String createByName;

	@FieldParam("修改时间")
	private Date updateOn;

	@FieldParam("修改者")
	private String updateBy;

	@FieldParam(association = true, forkeyId = "修改者", forkeyName = "描述", forkeyRelationId = "名称", forkeyTableName = "hbcm.._USER")
	private String updateByName;

	@FieldParam(association = true)
	private String remark;
}
