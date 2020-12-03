package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;

/**
 * ClassName:    LoanGuarantee
 * Package:    com.hbsoft.csms.bean
 * Description: 担保人
 * Datetime:    2020/3/30   11:19
 * Author:  hwl
 */
@TableName("k_loan_guarantee")
@Data
public class LoanGuarantee extends APojo  {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("贷款合同号")
    private String loanContractNum;

    @FieldParam("关系类型")
    private String relType;

    @FieldParam("证件号码")
    private String idNum;

    @FieldParam("证件类型")
    private String idType;

    @FieldParam("名称")
    private String name;

    @FieldParam("联系电话")
    private String mobile;

    @FieldParam("配偶证件号码")
    private String idNumMate;

    @FieldParam("配偶名称")
    private String nameMate;

    @FieldParam("配偶联系电话")
    private String mobileMate;

    @FieldParam("详细住址")
    private String addr;

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
