package com.hbsoft.dingding.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

/**
 * @author yj
 * @description 钉钉部门信息
 * @date 2020/5/20
 */
@Data
@TableName("k_dd_dept")
public class DingDingDeptInfo extends APojo {

    @FieldParam("ext")
    private String ext;

    @FieldParam(association = true)
    private Boolean createDeptGroup;

    @FieldParam("createDeptGroupIn")
    private Integer createDeptGroupIn;

    @FieldParam("name")
    private String name;

    @FieldParam("id")
    private Integer id;

    @FieldParam(association = true)
    private Boolean autoAddUser;

    @FieldParam("autoAddUserIn")
    private Integer autoAddUserIn;

    @FieldParam("parentid")
    private Integer parentid;

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;
}
