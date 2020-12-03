package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("k_ddsendmessage")
public class DdsendmessageBean extends APojo {

    @FieldParam("接收人")
    private String accepter;

    @FieldParam("业务id")
    private String businessId;

    @FieldParam("业务类型")
    private String businessType;

    @FieldParam("内容")
    private String content;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("创建者")
    private String createBy;

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;


}
