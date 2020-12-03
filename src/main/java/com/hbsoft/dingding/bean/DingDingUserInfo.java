package com.hbsoft.dingding.bean;

import com.hb.annotation.FieldParam;
import com.hb.annotation.TableName;
import com.hb.bean.APojo;
import com.hb.hbenum.FieldType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ClassName:    DingDingUserInfo
 * Package:    com.hbsoft.dingding.vo
 * Description:
 * Datetime:    2020/4/8   15:48
 * Author:  hwl
 */
@Data
@TableName("k_dd_user")
public class DingDingUserInfo extends APojo {

    @FieldParam("userid")
    private String userid;

    @FieldParam("unionid")
    private String unionid;

    @FieldParam("mobile")
    private String mobile;

    @FieldParam("tel")
    private String tel;

    @FieldParam("workPlace")
    private String workPlace;

    @FieldParam("remark")
    private String remark;

    @FieldParam(association = true)
    private Boolean isAdmin;

    @FieldParam("isAdminIn")
    private Integer isAdminIn;

    @FieldParam(association = true)
    private Boolean isBoss;

    @FieldParam("isBossIn")
    private Integer isBossIn;

    @FieldParam(association = true)
    private Boolean isHide;

    @FieldParam("isHideIn")
    private Integer isHideIn;

    @FieldParam(association = true)
    private Boolean isLeader;

    @FieldParam("isLeaderIn")
    private Integer isLeaderIn;

    @FieldParam("name")
    private String name;

    @FieldParam(association = true)
    private String nameCut;

    @FieldParam(association = true)
    private Boolean active;

    @FieldParam("activeIn")
    private Integer activeIn;

    @FieldParam("position")
    private String position;

    @FieldParam("email")
    private String email;

    @FieldParam("avatar")
    private String avatar;

    @FieldParam("jobnumber")
    private String jobnumber;

    @FieldParam("deptId")
    private Integer deptId;

    @FieldParam("deptName")
    private String deptName;

    @FieldParam(association = true)
    private List<Integer> department;

    @FieldParam(association = true)
    private Map<String, Object> extattr;

    @FieldParam(association = true)
    private String hbUserDeptId;

    @FieldParam(association = true)
    private String hbUserId;

    @FieldParam(fieldType = FieldType.prikeyIncrement)
    private Integer id_prikey;
}
