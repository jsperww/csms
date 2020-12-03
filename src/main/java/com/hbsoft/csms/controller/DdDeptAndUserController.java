package com.hbsoft.csms.controller;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.DDUserAll;
import com.hbsoft.csms.bean.DdDeptAndUserBean;
import com.hbsoft.csms.dao.service.DDUserAllDaoService;
import com.hbsoft.csms.dao.service.DdDeptAndUserBeanDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.H;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class DdDeptAndUserController extends ABaseController {

    @Autowired
    DdDeptAndUserBeanDaoService ddDeptAndUserBeanDaoService;
    @Autowired
    DDUserAllDaoService ddUserAllDaoService;

    /**
     * 获取树状结构
     * @param ddDeptAndUserBean
     * @return
     * sys
     */
    @PostMapping("getDdDeptAll")
    public String getDdDeptAll(DdDeptAndUserBean ddDeptAndUserBean) {
        CallResult<List<DdDeptAndUserBean>> result = new CallResult<>();
        try {
            List<DdDeptAndUserBean> ddDeptAllTree = ddDeptAndUserBeanDaoService.getAll(ddDeptAndUserBean);
            result.setSuccessResult(ddDeptAllTree);
        } catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 树状图信息
     * @param ddUserAll
     * @return
     * sys
     */
    @PostMapping("getDdUserAll")
    public String getDdUserAll(DDUserAll ddUserAll) {
        CallResult result = new CallResult<>();
        Map<String,Object> map = new HashMap<>();
        try {
            map = ddUserAllDaoService.getPagingData(ddUserAll);
        } catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

}
