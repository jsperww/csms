package com.hbsoft.csms.bean;

import java.util.Date;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ClassName:    LoanFileInfoBean
 * Package:    com.hbsoft.csms.bean
 * Description:
 * Datetime:    2020/4/2   10:35
 * Author:  hwl
 */
@Data
@TableName("k_loan_file")
public class LoanFileInfoBean extends APojo{

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @NotNull(message = "id有误")
    @FieldParam("关系id")
    private Integer relId;

    @NotBlank(message = "文件类别有误")
    @FieldParam("类别")
    private String type;

    @FieldParam("文件类型")
    private String fileType;

    @FieldParam("文件名称")
    private String fileName;

    @FieldParam("文件")
    private String filePath;

    @FieldParam("创建时间")
    private Date createOn;

    @FieldParam("创建者")
    private String createBy;

    private Integer delFlag;
}
