package com.hbsoft.csms.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("K_Notice")
public class NoticeBean extends APojo {

    private static final long serialVersionUID = 1L;

    @FieldParam(value = "ID_PRIKEY", fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;

    @FieldParam("标题")
    private String title;

    @FieldParam("内容")
    private String content;

    @FieldParam("发布人")
    private String issName;

    @FieldParam("发布时间")
    private Date issueTime;

    @FieldParam(association = true)
    private String issueTimeStr;

    @FieldParam("到期日期")
    private String matureTime;

    @FieldParam("备注")
    private String remark;

    @FieldParam(value = "开始时间",association = true)
    private String startDate;

    @FieldParam(value = "结束时间",association = true)
    private String endDate;
}
