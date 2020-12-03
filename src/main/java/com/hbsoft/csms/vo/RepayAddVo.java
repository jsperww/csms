package com.hbsoft.csms.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ClassName:    RepayLoanSecondVo
 * Package:    com.hbsoft.csms.vo
 * Description:
 * Datetime:    2020/4/1   15:05
 * Author:  hwl
 */
@Data
public class RepayAddVo {

    @NotBlank(message = "业务id有误")
    String id_prikey;

    @NotNull(message = "流程状态有误")
    String processStatus;

    @NotBlank(message = "流程状态节点有误")
    String  nodeId;

    String files;

    String  remark;

}
