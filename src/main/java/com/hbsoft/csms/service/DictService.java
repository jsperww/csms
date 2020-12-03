package com.hbsoft.csms.service;

import com.hb.bean.CallResult;
import com.hbsoft.csms.bean.DictBean;
import com.hbsoft.csms.dao.DictBeanDao;
import com.hbsoft.csms.dao.service.DictBeanDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName:    DictService
 * Package:    com.hbsoft.csms.service
 * Description:
 * Datetime:    2020/3/28   16:55
 * Author:  hwl
 */
@Service
public class DictService {

    @Autowired
    DictBeanDaoService dictBeanDaoService;

    @Autowired
    DictBeanDao dictBeanDao;

    @Transactional
    public CallResult<String> addDict(DictBean dictBean) throws  Exception{
        CallResult<String> result = new CallResult<>();
        dictBeanDaoService.add(dictBean);
        dictBeanDao.createDictTable(dictBean.getCode());
        result.setSuccessResult();
        return result;
    }
}
