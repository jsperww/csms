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
public class RepayLoanSecondVo {

    @NotBlank(message = "贷款合同编号有误")
    String loanContractNum;

    @NotNull(message = "类型有误")
    String type;

    @NotBlank(message = "复审人员有误")
    String userId;

}
