package com.hbsoft.csms.controller;


import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hb.controller.ABaseController;
import com.hb.util.StringUtil;
import com.hbsoft.common.constant.Constant;
import com.hbsoft.csms.bean.ConfigurationBean;
import com.hbsoft.csms.dao.ConfigurationBeanDao;
import com.hbsoft.csms.dao.service.ConfigurationBeanDaoService;
import com.hbsoft.csms.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class ConfigurationController extends ABaseController {
    @Autowired
    ConfigurationBeanDaoService configurationBeanDaoService;
    @Autowired
    ConfigurationBeanDao configurationBeanDao;
    @Autowired
    SessionService sessionService;
    /**
     * 添加
     * sys
     * @param configurationBean
     * @return
     */
    @PostMapping("addConfig")
    public String addConfig(ConfigurationBean configurationBean, HttpSession session) {
        CallResult result = new CallResult<>();
        try{
            HB_User hb_user = sessionService.getHbUserBySession(session);
            ConfigurationBean configurationBean1 = new ConfigurationBean();
            configurationBean1.setName(configurationBean.getName());
            ConfigurationBean byField = configurationBeanDaoService.getByField(configurationBean1);
            if (byField == null){
                String code = configurationBeanDao.selectMaxCode();
                if (isEmpty(code)) {
                    code = Constant.STARTCODE;
                } else {
                    code = StringUtil.getNextCode(3, code);
                }
                configurationBean.setCreateName(hb_user.getName());
                configurationBean.setModifyName(hb_user.getName());
                configurationBean.setCode(code);
                configurationBean.setCreate(new Date());
                configurationBean.setModify(new Date());
                configurationBeanDaoService.add(configurationBean);
                result.setSuccessResult("添加成功");
            }else {
                result.setFailResult("名称重复");
            }
        }catch (Exception e){
            result.setFailResult("添加失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 删除
     * sys
     * @param id_prikey
     * @return
     */
    @PostMapping("deleteConfig")
    public String deleteConfig(Integer id_prikey) {
        CallResult result = new CallResult<>();
        try{
            if (null != id_prikey){
            configurationBeanDaoService.removeOne(id_prikey);
            result.setSuccessResult("删除成功");
            }else {
                result.setFailResult("id不能为空");
            }
        }catch (Exception e){
            result.setFailResult("删除失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 修改
     * sys
     * @param configurationBean
     * @return
     */
    @PostMapping("updateConfig")
    public String updateConfig(ConfigurationBean configurationBean,HttpSession session) {
        CallResult result = new CallResult<>();
        try{
            HB_User hb_user = sessionService.getHbUserBySession(session);
            if (null != configurationBean.getId_prikey()){
                configurationBean.setModifyName(hb_user.getName());
                configurationBean.setModify(new Date());
                configurationBeanDaoService.set(configurationBean);
                result.setSuccessResult("修改成功");
            }else {
                result.setFailResult("id不能为空");
            }
        }catch (Exception e){
            result.setFailResult("修改失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 分页查询
     *sys
     * @param configurationBean
     * @return
     */
    @PostMapping("selectConfig")
    public String selectConfig(ConfigurationBean configurationBean) {
        Map<String,Object> map  =new HashMap<>();
        try {
            map = configurationBeanDaoService.getPagingData(configurationBean);
        } catch (Exception e) {
            map.put("code",400);
            map.put("msg","获取失败");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

}
