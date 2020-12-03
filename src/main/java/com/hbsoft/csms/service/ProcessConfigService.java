package com.hbsoft.csms.service;

import com.hb.bean.CallResult;
import com.hbsoft.common.enumtype.ProcessOperatorRoleTypeEnum;
import com.hbsoft.csms.bean.DictBean;
import com.hbsoft.csms.bean.ProcessOperatorConfigBean;
import com.hbsoft.csms.dao.DictBeanDao;
import com.hbsoft.csms.dao.service.DictBeanDaoService;
import com.hbsoft.csms.dao.service.ProcessOperatorConfigBeanDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yj
 * @description 流程配置
 * @date 2020/5/28
 */
@Service
public class ProcessConfigService {

    @Autowired
    ProcessOperatorConfigBeanDaoService processOperatorConfigBeanDaoService;

    @Autowired
    DictBeanDao dictBeanDao;


    public CallResult<String> addProcessConfig(ProcessOperatorConfigBean processOperatorConfigBean) throws Exception {
        CallResult<String> result = new CallResult<>();
        String roleType = processOperatorConfigBean.getRoleType();
        if (null != roleType && !"".equals(roleType)) {
            if (ProcessOperatorRoleTypeEnum.PROCESS_OPERATOR_ROLE_DETAIL.getCode().equals(roleType)) {
                //具体的人
                processOperatorConfigBeanDaoService.add(processOperatorConfigBean);
            }else if (ProcessOperatorRoleTypeEnum.PROCESS_OPERATOR_ROLE_TYPE_TEAM_MANAGER.getCode().equals(roleType)) {
                //组织内的人
                DictBean dictBean1 = dictBeanDao.selectDictDetailByCode("d_role",processOperatorConfigBean.getRole());
                if (null != dictBean1) {
                    processOperatorConfigBean.setRole(dictBean1.getName());
                    processOperatorConfigBeanDaoService.add(processOperatorConfigBean);
                }else {
                    result.setFailResult("暂无记录");
                }
            }
            result.setSuccessResult();
        }else  {
            result.setFailResult("参数错误");
        }
        return result;
    }
}
