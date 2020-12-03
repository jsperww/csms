package com.hbsoft.csms.vo;

import com.hb.annotation.FieldParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ClassName:    ProcessVo
 * Package:    com.hbsoft.csms.vo
 * Description:
 * Datetime:    2020/4/11   11:04
 * Author:  hwl
 */
@Data
public class ProcessVo {

    //业务id
    @NotBlank(message = "业务id有误")
    private String businessId;

    @NotBlank(message = "业务编号有误")
    private String businessCode;

    //流程属性
    @NotBlank(message = "流程属性有误")
    private String processAttribute;

    //节点id
    @NotNull(message = "节点id有误")
    private Integer nodeId;

    //状态
    @NotNull(message = "节点状态有误")
    private String nodeSatus;

    private String remark;

    @NotBlank(message = "操作人有误")
    private String userId;

    private String filePath;

    private String branch;
}
