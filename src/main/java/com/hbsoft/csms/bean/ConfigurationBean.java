package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("d_config")
public class ConfigurationBean extends APojo {
    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("代码")
    private String code;

    @FieldParam("项目")
    private String name;

    @FieldParam("拼音")
    private String spell;

    @FieldParam("属性")
    private String attribute;

    @FieldParam("属性名称")
    private String attributeName;

    @FieldParam("备注")
    private String note;

    @FieldParam("顺序")
    private String order;

    @FieldParam("停用")
    private String disable;

    @FieldParam("创建时间")
    private Date create;

    @FieldParam("修改时间")
    private Date modify;

    @FieldParam("创建者")
    private String createName;

    @FieldParam("修改者")
    private String modifyName;



}
