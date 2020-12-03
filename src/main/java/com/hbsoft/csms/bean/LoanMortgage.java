package com.hbsoft.csms.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;

import lombok.Data;

/**
 * ClassName:    LoanMortgage
 * Package:    com.hbsoft.csms.bean
 * Description: 抵押物
 * Datetime:    2020/3/30   11:28
 * Author:  hwl
 */
@TableName("k_loan_mortgage")
@Data
public class LoanMortgage extends APojo{

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("贷款合同号")
    private String loanContractNum;

    @FieldParam("名称")
    private String name;

    @FieldParam("单位")
    private String unit;

    @FieldParam("数量")
    private BigDecimal num;

    @FieldParam("估价")
    private BigDecimal estimate;

    @FieldParam("现状")
    private String currentStatus;

    @FieldParam("价值")
    private BigDecimal handleValue;

    @FieldParam("处理人")
    private String handleBy;

    @FieldParam("处理时间")
    private Date handleOn;

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
}
