package com.hbsoft.dingding.service;

import com.hb.bean.CallResult;
import com.hbsoft.csms.dao.service.DingDingDeptInfoDaoService;
import com.hbsoft.csms.dao.service.DingDingUserInfoDaoService;
import com.hbsoft.dingding.bean.DingDingDeptInfo;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import com.hbsoft.dingding.util.DingDingUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yj
 * @description 钉钉基础功能
 * @date 2020/5/25
 */
@Service
public class DDservice {

    @Autowired
    DingDingUtil ddUtil;

    @Autowired
    DingDingDeptInfoDaoService dingDingDeptInfoDaoService;

    @Autowired
    DingDingUserInfoDaoService dingDingUserInfoDaoService;


    @Transactional
    public void getDepts() throws  Exception{
        CallResult<List<DingDingDeptInfo>> result =  ddUtil.getDepts();
        if(result.isExec()){
            List<DingDingDeptInfo> list = result.getData();
            for(DingDingDeptInfo dept : list){
                DingDingDeptInfo param = new DingDingDeptInfo();
                param.setId(dept.getId());
                DingDingDeptInfo dt = dingDingDeptInfoDaoService.getByField(param);
                if(dt == null){
                    dingDingDeptInfoDaoService.add(dept);
                }else{
                    dept.setId_prikey(dt.getId_prikey());
                    dingDingDeptInfoDaoService.set(dept);
                }

                CallResult<List<DingDingUserInfo>> userResult = ddUtil.getUsers(dept.getId());
                if(userResult.isExec()){
                    List<DingDingUserInfo> userList = userResult.getData();
                    for(DingDingUserInfo ddUser : userList){
                        DingDingUserInfo userParam = new DingDingUserInfo();
                        userParam.setUserid(ddUser.getUserid());
                        userParam.setDeptId(dept.getId());
                        DingDingUserInfo  du = dingDingUserInfoDaoService.getByField(userParam);
                        ddUser.setDeptId(dept.getId());
                        ddUser.setDeptName(dept.getName());
                        if(du == null){
                            dingDingUserInfoDaoService.add(ddUser);
                        }else{
                            ddUser.setId_prikey(du.getId_prikey());
                            dingDingUserInfoDaoService.set(ddUser);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取钉钉用户id
     * @param code
     * @return
     * @throws Exception
     */
    public CallResult<String> getDingDingUserId(String code) throws Exception{
        CallResult<String> result = new CallResult<>();
        try {
            result = ddUtil.getDingDingUserId(code);
        }catch (Exception e) {
            result.setFailResult("操作失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取钉钉用户信息
     * @param userid
     * @return
     * @throws Exception
     */
    public DingDingUserInfo getDingDingUser(String userid) throws Exception{
        DingDingUserInfo dingDingUserInfo = new DingDingUserInfo();
        DingDingUserInfo dingDingUserInfoParam = new DingDingUserInfo();
        dingDingUserInfoParam.setUserid(userid);
        List<DingDingUserInfo> list =  dingDingUserInfoDaoService.getAll(dingDingUserInfoParam);
        if(ObjectUtils.isNotEmpty(list)){
            for(DingDingUserInfo dui : list){
                if(StringUtils.isNotBlank(dui.getJobnumber())){
                    return  dui;
                }
                dingDingUserInfo = dui;
            }
            return dingDingUserInfo;
        }else {
            return  null;
        }
    }

    public String getJSSDKSign(String url) throws Exception {
        return ddUtil.getJSSDKSign(url);
    }

    public String getJSSDKSignZz(String url) throws Exception {
        return ddUtil.getJSSDKSignZz(url);
    }
}
