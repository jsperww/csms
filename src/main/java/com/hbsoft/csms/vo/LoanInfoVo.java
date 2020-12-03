package com.hbsoft.csms.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import com.hbsoft.csms.bean.LoanGuarantee;
import com.hbsoft.csms.bean.LoanMortgage;

import lombok.Data;

/**
 * ClassName:    LoanInfoBean
 * Package:    com.hbsoft.csms.bean
 * Description:
 * Datetime:    2020/3/26   14:47
 * Author:   hwl
 */

@Data
public class LoanInfoVo extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;


    
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

    @FieldParam("类别")
    private Integer loanMold;

    private String loanMoldName;

    @FieldParam("贷款方式")
    private Integer loanWay;

    private String loanWayName;

    @FieldParam("借款日期")
    private Date loanBeginDate;

    @FieldParam("到期日期")
    private Date loanEndDate;

    @FieldParam("结欠本金")
    private BigDecimal leftCapitalAmount;

    @FieldParam("结欠利息")
    private BigDecimal leftInterestAmount;

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

    @FieldParam("详细住址")
    private String addr;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("创建者")
    private String createBy;

    @FieldParam("修改时间")
    private Date updateOn;

    @FieldParam("修改者")
    private String updateBy;
    
    @FieldParam("操作时间")
    private Date operateOn;

    @FieldParam("操作人")
    private String operateBy;

    @FieldParam("导入日期")
    private Date importDate;

    @FieldParam("催收函日期")
    private Date collectionDate; 
   
    private String loanBeginDateStr;

    private String loanEndDateStr;


    private String numberType;

    private String number;
    
    @FieldParam(association = true)
    private String managerName;
    
    private String remark;
    
    @FieldParam(value = "流程属性")
	private String processAttribute;

	@FieldParam(value = "流程状态")
	private String processStatus;

    private String processStatusName;

    private String nodeName;
	
	private Integer logId;

	private Integer operateType;

	private String operator;

    private String lastOperate;

    private Date lastOperateOn;

    private String lastOperateBy;

    private String lastRemark;

    private String operateTypeName;

    private String processName;

    private String notProcessAttribute;

    private String notNodeId;

    private Integer nodeId;

    private Integer pb_id_prikey;

    private String deptId;

    private String userId;

    private String nodeOperator;

    private String visitFlag;
}
