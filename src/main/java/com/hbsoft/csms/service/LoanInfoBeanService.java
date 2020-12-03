package com.hbsoft.csms.service;


import com.hb.bean.CallResult;
import com.hbsoft.common.enumtype.LoanFileTypeEnum;
import com.hbsoft.csms.bean.LoanFileInfoBean;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.dao.service.LoanFileInfoBeanDaoService;
import com.hbsoft.csms.dao.service.LoanInfoBeanDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoanInfoBeanService {


    @Autowired
    LoanFileInfoBeanDaoService loanFileInfoBeanDaoService;


    public Map<String,Object>deleteLoanFileInfoByIdPrikey(Integer id_prikey) {
        Map<String, Object> map = new HashMap<>();
        try {
            loanFileInfoBeanDaoService.removeOne(id_prikey);
            map.put("code",0);
            map.put("msg","删除成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("msg","系统异常");
            return map;
        }
    }

    public Map<String,Object>updateLoanFileInfoByIdPrikey(LoanFileInfoBean loanFileInfoBean) {
        Map<String, Object> map = new HashMap<>();
        try {
            loanFileInfoBeanDaoService.set(loanFileInfoBean);
            map.put("code",0);
            map.put("msg","修改成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("msg","系统异常");
            return map;
        }
    }


    public Map<String,Object>selectLoanFileInfoByIdPrikey(Integer id_prikey){
        Map<String,Object> map=new HashMap<>();
        try {
            LoanFileInfoBean loanFileInfoBean=loanFileInfoBeanDaoService.getById(id_prikey);
            map.put("code",0);
            map.put("msg","查询成功");
            map.put("data",loanFileInfoBean);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("msg","系统异常");
            return map;
        }
    }

    public Map<String,Object>insertLoanFileInfo(LoanFileInfoBean loanFileInfoBean){
        Map<String,Object> map= new HashMap<>();
        try {
            loanFileInfoBean.setType(LoanFileTypeEnum.LOAN_FILE_TYPE_TEMPLATE.getCode());
            loanFileInfoBean.setFileType("4");
            loanFileInfoBeanDaoService.add(loanFileInfoBean);
            map.put("code",0);
            map.put("msg","添加成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("msg","系统异常");
            return map;
        }
    }
    public CallResult<LoanFileInfoBean> selectLoanFileInfo(LoanFileInfoBean loanFileInfoBean){
        CallResult result = new CallResult<>();
        try {
            loanFileInfoBean.setType(LoanFileTypeEnum.LOAN_FILE_TYPE_TEMPLATE.getCode());
            loanFileInfoBean.setFileType("4");
            result =loanFileInfoBeanDaoService.getPagingDataNew(loanFileInfoBean);
            result.setCode(0);
            result.setMsg("查询成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("系统异常");;
            return result;
        }
    }
}
