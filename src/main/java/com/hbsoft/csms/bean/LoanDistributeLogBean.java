package com.hbsoft.csms.bean;

import java.util.Date;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;

import lombok.Data;

@Data
@TableName("k_loan_distribute_log")
public class LoanDistributeLogBean extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("操作类型")
    private String operationType;

    @FieldParam("角色编号")
    private String number;

    @FieldParam("贷款合同号")
    private String loanContractNum;

    @FieldParam("角色类型")
    private String numberType;

    @FieldParam("期限")
    private Integer limitMonth;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("创建者")
    private String createBy;

}
