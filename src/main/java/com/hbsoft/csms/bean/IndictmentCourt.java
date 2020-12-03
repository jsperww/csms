package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 开庭
 */
@Data
@TableName("k_indicment_court")
public class IndictmentCourt extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("indictment_id")
    private String indictmentId;

    @FieldParam("上诉方人员")
    private String appellors;

    @FieldParam("被告方人员")
    private String accusedPersons;

    @FieldParam("开庭时间")
    private String courtDate;

    @FieldParam("结果")
    private String courtResult;

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
