package com.hbsoft.dingding.bean;

import com.hb.bean.APojo;
import lombok.Data;

/**
 * @author yj
 * @description 信息
 * @date 2020/5/26
 */
@Data
public class DDUserInfo extends APojo {
    private Integer errcode;
    private String errmsg;
    private String userid;
    private String deviceId;
    private Boolean is_sys;
    private Integer sys_level;
}
