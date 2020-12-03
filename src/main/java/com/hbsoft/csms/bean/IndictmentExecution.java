package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 执行
 */
@Data
@TableName("k_indicment_execution")
public class IndictmentExecution extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("indictment_id")
    private String indictmentId;

    @FieldParam("执行人员")
    private String appellors;

    @FieldParam("被执行人员")
    private String accusedPersons;

    @FieldParam("执行时间")
    private String executionDate;

    @FieldParam("结果")
    private String executionResult;

    @FieldParam("备注")
    private String remark;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("创建者")
    private String createBy;

    @FieldParam("修改时间")
    private Date updateOn;

    @FieldParam("修改者")
    private String updateBy;

    @FieldParam(association = true)
    private Integer delFlag;

    @FieldParam(association = true)
    private String  loanFiles;

}
