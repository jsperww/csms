package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

@Data
@TableName("k_dd_user")
public class DDUserAll extends APojo {
    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("position")
    private String position;

    @FieldParam("name")
    private String name;

    @FieldParam("userid")
    private String userid;

    @FieldParam("deptName")
    private String deptName;

    @FieldParam("deptId")
    private String deptId;

    @FieldParam("jobnumber")
    private String jobnumber;
}
