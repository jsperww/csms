package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

@Data
@TableName("hbcm.._user")
public class HbcmUser extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("名称")
    private String name;

    @FieldParam("密码")
    private String password;
}
