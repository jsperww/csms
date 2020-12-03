package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 诉讼
 */
@Data
@TableName("k_indictment_info")
public class IndictmentInfo extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("贷款合同号")
    private String loanContractNum;

    @FieldParam("上诉人")
    private String appellor ;

    @FieldParam("被告人")
    private String accusedPerson;

    @FieldParam("律师")
    private String lawyer;

    @FieldParam("法院地区")
    private String courtArea;

    @FieldParam("法院")
    private String court;

    @FieldParam("诉讼费用")
    private BigDecimal courtCost;

    @FieldParam("执行费用")
    private BigDecimal executionCost;

    @FieldParam("调解标识")
    private String mediationFlag;

    @FieldParam("开庭标识")
    private String courtFlag;

    @FieldParam("执行标识")
    private String executionFlag;

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
    private List<IndictmentExecution> executionList;

    @FieldParam(association = true)
    private List<IndictmentMediation> mediationList;

    @FieldParam(association = true)
    private List<IndictmentCourt> courtList;

    @FieldParam(association = true)
    private String executions;

    @FieldParam(association = true)
    private String mediations;

    @FieldParam(association = true)
    private String courts;

    @FieldParam(association = true)
    private Integer delFlag;

    @FieldParam(association = true)
    private String loanFiles;

    @FieldParam(association = true)
    List<LoanFileInfoBean> loanFileList;
}
