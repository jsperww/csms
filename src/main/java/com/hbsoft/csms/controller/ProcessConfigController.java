package com.hbsoft.csms.controller;

import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.ProcessOperatorConfigBean;
import com.hbsoft.csms.dao.service.ProcessOperatorConfigBeanDaoService;
import com.hbsoft.csms.service.ProcessConfigService;
import com.hbsoft.csms.service.SessionService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author yj
 * @description 流程配置
 * @date 2020/5/28
 */
@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class ProcessConfigController extends ABaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ProcessOperatorConfigBeanDaoService processOperatorConfigBeanDaoService;

    @Autowired
    ProcessConfigService processConfigService;

    @Autowired
    SessionService sessionService;

    /**
     * 添加流程控制配置
     * @param processOperatorConfigBean
     * @return
     */
    @RequestMapping("addProcessConfig")
    public String addProcessConfig(ProcessOperatorConfigBean processOperatorConfigBean, HttpSession session) {
        CallResult<String> result = new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            processOperatorConfigBean.setCreateOn(new Date());
            processOperatorConfigBean.setCreateBy(hb_user.getName());
            processOperatorConfigBeanDaoService.add(processOperatorConfigBean);
            result.setSuccessResult();
        }catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 删除流程控制配置
     * @param id_prikey
     * @return
     */
    @RequestMapping("deleteProcessConfig")
    public String deleteProcessConfig(Integer id_prikey) {
        CallResult<String> result = new CallResult<>();
        try {
            if (null == id_prikey) {
                result.setFailResult("主键参数错误");
                return gson.toJson(result);
            }
            processOperatorConfigBeanDaoService.removeOne(id_prikey);
            result.setSuccessResult();
        }catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 更新流程控制配置
     * @param processOperatorConfigBean
     * @return
     */
    @RequestMapping("updateProcessConfig")
    public String updateProcessConfig(ProcessOperatorConfigBean processOperatorConfigBean,HttpSession session) {
        CallResult<String> result = new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            processOperatorConfigBean.setUpdateBy(hb_user.getName());
            processOperatorConfigBean.setUpdateOn(new Date());
            processOperatorConfigBeanDaoService.set(processOperatorConfigBean);
            result.setSuccessResult();
        }catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 分页查询流程配置
     * @param processOperatorConfigBean
     * @return
     */
    @RequestMapping("selectProcessConfigPagData")
    public String selectProcessConfigPagData(ProcessOperatorConfigBean processOperatorConfigBean) {
        Map<String, Object> map = null;
        try {
            map = processOperatorConfigBeanDaoService.getPagingData(processOperatorConfigBean);
            if (null == map) {
                map = new HashMap<>();
                map.put("code",400);
                map.put("msg","暂无数据");
            }else {
                return gson.toJson(map);
            }
        }catch (Exception e) {
            map = new HashMap<>();
            map.put("code",400);
            map.put("msg","获取失败");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 获取流程配置详情
     * @param id_prikey
     * @return
     */
    @RequestMapping("selectProcessConfigById")
    public String selectProcessConfigById(Integer id_prikey) {
        CallResult<ProcessOperatorConfigBean> result = new CallResult<>();
        try {
            if (null == id_prikey) {
                result.setFailResult("主键参数错误");
                return gson.toJson(result);
            }
            ProcessOperatorConfigBean processConfig = processOperatorConfigBeanDaoService.getById(id_prikey);
            if (null != processConfig) {
                result.setSuccessResult(processConfig);
            }else {
                result.setFailResult("暂无数据");
            }
        }catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 根据字段获取流程控制详情
     * @param pC
     * @return
     */
    @RequestMapping("selectProcessConfigByField")
    public String selectProcessConfigByField(ProcessOperatorConfigBean pC) {
        CallResult<List<ProcessOperatorConfigBean>> result = new CallResult<>();
        try {
            List<ProcessOperatorConfigBean> processOperatorConfigBeans = processOperatorConfigBeanDaoService.getAll(pC);
            if (null != processOperatorConfigBeans) {
                result.setSuccessResult(processOperatorConfigBeans);
            }else {
                result.setFailResult("暂无数据");
            }
        }catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

}
