package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;

/**
 * ClassName:    LoanWarnInfo
 * Package:    com.hbsoft.csms.bean
 * Description:
 * Datetime:    2020/4/8   11:07
 * Author:  hwl
 */
@Data
@TableName("k_warn_info")
public class LoanWarnInfo extends APojo {

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;
    @FieldParam("提醒内容")
    private String warnContent;
    @FieldParam("提醒时间")
    private Date warnDate;

    @FieldParam(association = true)
    private String warnDateStr;

    @FieldParam("提醒状态")
    private Integer warnStatus;
    @FieldParam("提醒类型")
    private Integer warnWay;
    @FieldParam(association = true)
    private String warnWayName;
    @FieldParam("提醒内容id")
    private Integer warnId;
    @FieldParam("贷款合同号")
    private String loanContractNum;
    @FieldParam("提醒对象类型")
    private Integer warnTargetType;
    @FieldParam("提醒对象")
    private String warnTarget;
    @FieldParam("处理状态")
    private Integer handleStatus;
    @FieldParam("处理结果")
    private String result;
    @FieldParam("备注")
    private String remark;
    @FieldParam("发送消息标识")
    private String sendMsgFlag;
    @FieldParam("创建时间")
    private Date createOn;
    @FieldParam("创建者")
    private String createBy;
    @FieldParam("修改时间")
    private Date updateOn;
    @FieldParam("修改者")
    private String updateBy;
    @FieldParam(association = true)
    private String name;
    @FieldParam(association = true)
    private Integer flag;

}
