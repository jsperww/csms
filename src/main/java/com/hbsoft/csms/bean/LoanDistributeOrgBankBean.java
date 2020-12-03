package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;

/**
 * @Author: yj
 * @Date: 2020/3/26 17:00
 */
@Data
@TableName("k_loan_distribute_org_bank")
public class LoanDistributeOrgBankBean extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("银行编号")
    private String bankNumber;

    @FieldParam("组织号")
    private String organizationNum;
    
    @FieldParam("编号类型")
    private String numberType;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("修改时间")
    private Date updateOn;

    @FieldParam("创建者")
    private String createBy;

    @FieldParam("修改者")
    private String updateBy;
}
