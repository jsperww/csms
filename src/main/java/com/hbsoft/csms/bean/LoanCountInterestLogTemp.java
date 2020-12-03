package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName:    LoanCountInterestLog
 * Package:    com.hbsoft.csms.bean
 * Description:
 * Datetime:    2020/5/13   15:00
 * Author:  hwl
 */
@Data
@TableName("k_loan_count_Interest_log_temp")
public class LoanCountInterestLogTemp extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("贷款合同号")
    private String loanContractNum;

    @FieldParam("贷款类型")
    private Integer loanType;

    @FieldParam("执行利率")
    private BigDecimal loanNewMonthRate;

    @FieldParam("结欠本金")
    private BigDecimal leftCapitalAmount;

    @FieldParam("原结欠利息")
    private BigDecimal beforeLeftInterestAmount;

    @FieldParam("原核销时欠息")
    private BigDecimal beforeHeXiaoLeftInterestAmount;

    @FieldParam("原表外欠息")
    private BigDecimal beforeBiaoWaiLeftInterestAmount;

    @FieldParam("产生利息")
    private BigDecimal makeInterestAmount;

    @FieldParam("结欠利息")
    private BigDecimal leftInterestAmount;

    @FieldParam("核销时欠息")
    private BigDecimal heXiaoLeftInterestAmount;

    @FieldParam("表外欠息")
    private BigDecimal biaoWaiLeftInterestAmount;

    @FieldParam("计算时间")
    private Date countDate;

    @FieldParam("操作时间")
    private Date operateDate;
}
