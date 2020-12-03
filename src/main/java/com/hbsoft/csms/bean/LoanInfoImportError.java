package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;

/**
 * ClassName:    LoanInfoImportError
 * Package:    com.hbsoft.csms.bean
 * Description:
 * Datetime:    2020/4/27   14:42
 * Author:  hwl
 */
@Data
@TableName("k_loan_info_import_error")
public class LoanInfoImportError extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("证件号码")
    private String idNum;

    @FieldParam("贷款账号")
    private String loanAccountNum;

    @FieldParam("导入状态")
    private String importStatus;
    
    @FieldParam("备注")
    private String remark;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("创建者")
    private String createBy;
    
    @FieldParam(association = true, forkeyId = "创建者", forkeyName = "描述", forkeyRelationId = "名称", forkeyTableName = "hbcm.._USER")
	private String createByName;
}
