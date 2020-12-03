package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * ClassName:    LoanVisitBean
 * Package:    com.hbsoft.csms.bean
 * Description:
 * Datetime:    2020/4/2   10:35
 * Author:  hwl
 */
@Data
@TableName("k_loan_visit")
public class LoanVisitBean extends APojo{

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @NotBlank(message = "参数有误")
    @FieldParam("贷款合同号")
    private String loanContractNum;

    @NotBlank(message = "请选择回访对象")
    @FieldParam("回访对象类型")
    private String visitPersonType;

    @NotBlank(message = "请选择回访对象")
    @FieldParam("回访对象")
    private String visitPerson;

    @NotNull(message = "请选择回访类型")
    @FieldParam("回访类型")
    private Integer visitType;

    @FieldParam(association = true,forkeyId = "回访类型",forkeyName = "项目",forkeyRelationId = "代码",forkeyTableName = "d_loanvisittype")
    private String visitTypeName;

    @NotBlank(message = "请输入回访内容")
    @FieldParam("回访结果")
    private String visitResult;

    @NotNull(message = "请选择回访时间")
    @FieldParam("回访时间")
    private Date visitDate;

    @FieldParam(association = true)
    private String visitDateStr;

    @FieldParam("回访地点")
    private String visitPlace;

    @FieldParam("回访坐标")
    private String visitCoordinate;

    @FieldParam("催收函标识")
    private Integer peyMentFlag;
    
    @FieldParam("催收函对象类型")
    private Integer peyMentPersonType;
    
    @FieldParam("催收函对象")
    private String peyMentPerson;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("创建者")
    private String createBy;

    @FieldParam("修改时间")
    private Date updateOn;

    @FieldParam("修改者")
    private String updateBy;

    @FieldParam(association = true)
    private String remark;

    @FieldParam(association = true)
    private String userId;

    @FieldParam(association = true)
    private String deptId;

    @FieldParam(association = true)
    public List<LoanFileInfoBean> loanFileList;

    @FieldParam(association = true)
    public String  loanFiles;

    @FieldParam("提醒标识")
    private Integer warnFalg;

    @FieldParam("提醒日期")
    private Date warnDate;

    private String warnDateStr;

    @FieldParam("提醒内容")
    private String warnContent;

}
