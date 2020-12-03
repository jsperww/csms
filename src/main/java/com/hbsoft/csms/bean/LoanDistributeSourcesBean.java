package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@TableName("k_loan_distribute_sources")
public class LoanDistributeSourcesBean extends APojo {
    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("编号")
    private String number;

    @FieldParam("贷款合同号")
    private String loanContractNum;

    @FieldParam("编号类型")
    private String numberType;

    @FieldParam("负责人")
    private String manager;

    @FieldParam("组织分配号")
    private String orgNum;

    @FieldParam("期限")
    private Integer limitMonth;

    @FieldParam("操作类型")
    private String operationType;

    @FieldParam("来源方式")
    private String sourcesWay;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("修改时间")
    private Date updateOn;

    @FieldParam("创建者")
    private String createBy;

    @FieldParam("修改者")
    private String updateBy;

    @FieldParam("relId")
    private Integer relId;
}
