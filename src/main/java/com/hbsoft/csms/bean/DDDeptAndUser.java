package com.hbsoft.csms.bean;

import com.hbsoft.dingding.bean.DingDingDeptInfo;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import lombok.Data;

import java.util.List;

/**
 * @author yj
 * @description 接收钉钉部门人员
 * @date 2020/5/25
 */
@Data
public class DDDeptAndUser {
    private Integer errcode;
    private String errmsg;
    private List<DingDingDeptInfo> department;
    private List<DingDingUserInfo> userlist;
}
