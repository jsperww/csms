package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName:    LoanRepayBean
 * Package:    com.hbsoft.csms.bean
 * Description:
 * Datetime:    2020/3/26   17:10
 * Author:  hwl
 */
@Data
@TableName("k_loan_repay")
public class LoanRepayBean extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("贷款合同号")
    private String loanContractNum;

    @FieldParam("还款方式")
    private Integer repayWay;

    @FieldParam("贷款类型")
    private Integer loanType;
    //还款方式
    private String repayWayName;

    @FieldParam("还款金额")
    private BigDecimal repayAmount;

    @FieldParam("还款本金")
    private BigDecimal repayCapitalAmount;

    @FieldParam("还款利息")
    private BigDecimal repayInterestAmount;

    @FieldParam("还款核销时欠息")
    private BigDecimal repayHeXiaoLeftInterestAmount;

    @FieldParam("还款表外欠息")
    private BigDecimal repayBiaoWaiLeftInterestAmount;

    @FieldParam("结欠本金")
    private BigDecimal leftCapitalAmount;

    @FieldParam("结欠利息")
    private BigDecimal leftInterestAmount;

    @FieldParam("核销时欠息")
    private BigDecimal heXiaoLeftInterestAmount;

    @FieldParam("表外欠息")
    private BigDecimal biaoWaiLeftInterestAmount;

    @FieldParam("原核销时欠息")
    private BigDecimal sourceHeXiaoLeftInterestAmount;

    @FieldParam("原表外欠息")
    private BigDecimal sourceBiaoWaiLeftInterestAmount;

    @FieldParam("原结欠本金")
    private BigDecimal sourceLeftCapitalAmount;

    @FieldParam("原结欠利息")
    private BigDecimal sourceLeftInterestAmount;

    @FieldParam("执行利率")
    private BigDecimal loanNewMonthRate;

    @FieldParam("客户经理")
    private String customerManage;

    private String customerManageName;

    @FieldParam("客户经理类型")
    private String customerManageType;

    @FieldParam("审核状态")
    private Integer status;

    @FieldParam("初审人员")
    private String checkFirst;

    @FieldParam(association = true)
    private String checkFirstName;

    @FieldParam("初审时间")
    private Date checkFirstDate;

    private String checkFirstDateStr;

    @FieldParam("复审人员")
    private String checkSecond;

    private String checkSecondName;

    @FieldParam("复审时间")
    private Date checkSecondDate;

    private String checkSecondDateStr;

    @FieldParam("抹账人员")
    private String revokeFirst;

    @FieldParam("抹账时间")
    private Date revokeDate;

    @FieldParam("还款类型")
    private Integer repayType;

    @FieldParam(association = true)
    private String repayTypeName;

    @FieldParam("还款机构")
    private String org;

    private String orgName;

    @FieldParam(value = "负责人", association = true)
    private String manager;

    @FieldParam(association = true)
    private String userId;

    @FieldParam(association = true)
    private String deptId;

    @FieldParam(association = true)
    private String statusName;

    @FieldParam(association = true)
    private String idNum;

    @FieldParam(association = true)
    private String idType;
    
    @FieldParam(association = true)
    private String idTypeName;

    @FieldParam(association = true)
    private String name;

    //贷款金额
    @FieldParam(association = true)
    private BigDecimal earliestLoanAmount;

    //账号
    private String loanAccountNum;

    private Date loanBeginDate;

    private Date loanEndDate;

    //借款日期
    private String loanBeginDateStr;

    //到期日期
    private String loanEndDateStr;

    ProcessBusinessBean processBusinessBean;


    //操作类型
    private Integer operateType;

    //当前节点
    private Integer nodeId;

    private String businessId;

    private String remark;

    private String files;

    private String loanTypeName;

    private String processStatusName;

    private String currentDateStr;

    private String mobile;

    private String loanStatus;

    private String loanWay;
}
