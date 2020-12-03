package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ClassName:    LoanInfoBean
 * Package:    com.hbsoft.csms.bean
 * Description:
 * Datetime:    2020/3/26   14:47
 * Author:   hwl
 */

@Data
@TableName("k_loan_info")
public class LoanInfoBean extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("是否共享")
    private String isShare;

    @FieldParam(association = true)
    private String isShareName;
    
    @NotBlank(message = "证件号码不能为空")
    @FieldParam("证件号码")
    private String idNum;

    @FieldParam("证件类型")
    private String idType;
    
    @FieldParam(association = true,forkeyId = "证件类型",forkeyName = "项目",forkeyRelationId = "代码",forkeyTableName = "d_idType")
    private String idTypeName;

    @FieldParam("名称")
    private String name;

    @FieldParam("联系电话")
    private String mobile;

    @FieldParam("贷款账号")
    private String loanAccountNum;

    @FieldParam("贷款合同号")
    private String loanContractNum;

    @FieldParam("首贷金额")
    private BigDecimal earliestLoanAmount;

    @FieldParam("原利率")
    private BigDecimal loanOldMonthRate;

    @FieldParam("执行利率")
    private BigDecimal loanNewMonthRate;

    @FieldParam("贷款类型")
    private Integer loanType;

    private String loanTypeName;

    @FieldParam("核销日期")
    private Date hxDate;

    @FieldParam("类别")
    private Integer loanMold;

    private String loanMoldName;

    @FieldParam("贷款方式")
    private Integer loanWay;

    private String loanWayName;

    @FieldParam("借款日期")
    private Date loanBeginDate;

    private String loanBeginDateStr;

    @FieldParam("到期日期")
    private Date loanEndDate;

    private String loanEndDateStr;

    @FieldParam("导入结欠本金")
    private BigDecimal sourceLeftCapitalAmount;

    @FieldParam("导入结欠利息")
    private BigDecimal sourceLeftInterestAmount;

    @FieldParam("结欠本金")
    private BigDecimal leftCapitalAmount;

    @FieldParam("结欠利息")
    private BigDecimal leftInterestAmount;

    @FieldParam("导入核销时欠息")
    private BigDecimal sourceHeXiaoLeftInterestAmount;

    @FieldParam("导入表外欠息")
    private BigDecimal sourceBiaoWaiLeftInterestAmount;

    @FieldParam("核销时欠息")
    private BigDecimal heXiaoLeftInterestAmount;

    @FieldParam("表外欠息")
    private BigDecimal biaoWaiLeftInterestAmount;

    @FieldParam("行业")
    private String loanBusiness;

    @FieldParam("贷款用途")
    private String loanUsed;

    @FieldParam("贷款状态")
    private Integer loanStatus;

    private String loanStatusName;

    @FieldParam("所属机构")
    private String org;

    private String orgName;

    @FieldParam("县")
    private String county;

    private String countyName;

    @FieldParam("镇")
    private String town;

    private String townName;

    @FieldParam("村")
    private String village;

    private String villageName;

    @FieldParam("家庭地址")
    private String homeAddr;

    @FieldParam("家庭地址坐标")
    private String homeAddrCoordinate;

    private String homeAddrImg;

    @FieldParam("单位地址")
    private String companyAddr;

    @FieldParam("单位地址坐标")
    private String companyAddrCoordinate;

    private String companyAddrImg;

    @FieldParam("其他地址")
    private String otherAddr;

    @FieldParam("其他地址坐标")
    private String otherAddrCoordinate;

    private String otherAddrImg;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("创建者")
    private String createBy;

    @FieldParam("修改时间")
    private Date updateOn;

    @FieldParam("修改者")
    private String updateBy;

    @FieldParam("导入日期")
    private Date importDate;

    @FieldParam("催收函日期")
    private Date collectionDate;

    @FieldParam("认领标识")
    private String claimFlag;

    @FieldParam("借据号")
    private String receiptNo;

    @FieldParam("计息日")
    private Date interestStartDate;

    @FieldParam("打包本金金额")
    private BigDecimal dbCapitalAmount;

    @FieldParam("打包利息金额")
    private BigDecimal dbInterestAmount;

    @FieldParam("贷款余额")
    private BigDecimal loanLeftAmount;

    @FieldParam("打包前归还金额")
    private BigDecimal dbBeforeBackAmount;

    @FieldParam(association = true)
    private String claimFlagName;

    private String collectionDateStr;

    @FieldParam(association = true)
    private String userId;

    @FieldParam(association = true)
    private String deptId;

    @FieldParam(association = true)
    private String manager;

    @FieldParam(association = true)
    private String managerName;

    @FieldParam(association = true)
    private String numberType;

    @FieldParam(association = true)
    private String number;

    @FieldParam(association = true)
    private String numberName;

    @FieldParam(association = true)
    private String distributeFlag;

    @FieldParam(association = true)
    private Integer distributeFlagInt;
    
    @FieldParam(association = true)
    private List<LoanMortgage> mortgageList;
    
    @FieldParam(association = true)
    private List<LoanGuarantee> guaranteeList;

    @FieldParam(association = true)
    private List<LoanGuarantee> mateList;

    @FieldParam(association = true)
    private IndictmentInfo indictmentInfo;

    @FieldParam(association = true)
    private List<IndictmentExecution> executions;

    @FieldParam(association = true)
    private List<IndictmentMediation> mediations;

    @FieldParam(association = true)
    private List<IndictmentCourt> courts;

    @FieldParam(association = true)
    private String  mates;

    @FieldParam(association = true)
    private  String mortgages;

    @FieldParam(association = true)
    private String guarantees;

    @FieldParam(association = true)
    private String vistCreateBy;

    private String remark;

    @FieldParam(association = true)
    private String isDistrue;

    private String operationType;
}
