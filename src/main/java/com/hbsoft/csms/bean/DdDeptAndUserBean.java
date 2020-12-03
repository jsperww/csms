package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;


@Data
@TableName("k_dd_dept")
public class DdDeptAndUserBean extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("createDeptGroupIn")
    private String createDeptGroupIn;

    @FieldParam("name")
    private String name;

    @FieldParam("id")
    private String id;

    @FieldParam("autoAddUserIn")
    private String autoAddUserIn;

    @FieldParam("parentid")
    private String parentid;
}
