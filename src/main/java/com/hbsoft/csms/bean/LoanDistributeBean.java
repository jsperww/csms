package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Author: yj
 * @Date: 2020/3/26 17:00
 */
@Data
@TableName("k_loan_distribute")
public class LoanDistributeBean extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @NotBlank(message = "编号有误")
    @FieldParam("编号")
    private String number;

    @NotBlank(message = "贷款合同号有误")
    @FieldParam("贷款合同号")
    private String loanContractNum;
    
    @FieldParam("编号类型")
    @NotBlank(message = "编号类型有误")
    private String numberType;
    
    @FieldParam("负责人")
    private String manager;
    
    @FieldParam("组织分配号")
    private String orgNum;

    @FieldParam("操作类型")
    private String operationType;

    @FieldParam("期限")
    private Integer limitMonth;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("修改时间")
    private Date updateOn;

    @FieldParam("创建者")
    private String createBy;

    @FieldParam("修改者")
    private String updateBy;
}
