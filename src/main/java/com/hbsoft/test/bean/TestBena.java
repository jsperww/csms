package com.hbsoft.test.bean;


import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@TableName("k_test")
public class TestBena extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @NotBlank(message = "请输入姓名")
    @FieldParam("姓名")
    private String name;

    @FieldParam("年龄")
    private Integer age;

    @FieldParam("创建时间")
    private Date createOn;

}
