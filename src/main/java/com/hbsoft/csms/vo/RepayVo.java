package com.hbsoft.csms.vo;

import com.hb.annotation.FieldParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName:    RepayVo
 * Package:    com.hbsoft.csms.vo
 * Description:
 * Datetime:    2020/3/27   9:48
 * Author:  hwl
 */
@Data
public class RepayVo {

    //贷款合同号
    @NotBlank(message = "请输入贷款合同号")
    private String loanContractNum;

    //还款金额
    private BigDecimal repayAmount;

    //还款本金
    @NotNull(message = "请输入还款本金")
    private BigDecimal repayCapitalAmount;

    @FieldParam("还款核销时欠息")
    private BigDecimal repayHeXiaoLeftInterestAmount;

    @FieldParam("还款表外欠息")
    private BigDecimal repayBiaoWaiLeftInterestAmount;

    //还款利息
    @NotNull(message = "请输入还款利息")
    private BigDecimal repayInterestAmount;

    @NotBlank(message = "请选择还款支行")
    private String org;

    @NotNull(message = "请选择还款方式")
    private Integer repayWay;

    @FieldParam(value = "流程属性")
    private String processAttribute;

    @FieldParam(value = "流程状态")
    private String processStatus;

    @FieldParam("核销时欠息")
    private BigDecimal heXiaoLeftInterestAmount;

    @FieldParam("表外欠息")
    private BigDecimal biaoWaiLeftInterestAmount;

    private Integer logId;

    private Integer operateType;

    private String name;

    //补录日期
    private Date addRepayDate;

    private String remark;

    private String files;

    private String businessId;

    private Integer nodeId;
}
