package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("k_login_log")
public class DDLoginLogBean extends APojo {

    @FieldParam(fieldType= FieldType.prikeyIncrement)
    private Integer id_prikey;
    @FieldParam("名称")
    private String ddName;
    @FieldParam("钉钉id")
    private String ddId;
    @FieldParam("登录时间")
    private Date logintime;
}
