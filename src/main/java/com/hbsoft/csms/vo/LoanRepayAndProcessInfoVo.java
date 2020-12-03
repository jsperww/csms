package com.hbsoft.csms.vo;

import com.hb.annotation.FieldParam;
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
public class LoanRepayAndProcessInfoVo {

    @NotBlank(message = "流程属性有误")
    String processAttribute;

    @NotNull(message = "id_prikey有误")
    private Integer id_prikey;



}
