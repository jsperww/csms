package com.hbsoft.csms.controller;

import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.LoanFileInfoBean;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.service.LoanInfoBeanService;
import com.hbsoft.csms.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoanInfoBeanController extends ABaseController {

    @Autowired
    LoanInfoBeanService loanInfoBeanService;
    @Autowired
    SessionService sessionService;

    /**
     * 根据id_prikey删除
     */
    @PostMapping("deleteLoanFileInfoByIdPrikey")
    public Map<String,Object> deleteLoanFileInfoByIdPrikey(Integer id_prikey){
        Map<String, Object> map = new HashMap<>();
        if(id_prikey==null){
            map.put("code",400);
            map.put("msg","id_prikey不能为空");
            return map;
        }
        map=loanInfoBeanService.deleteLoanFileInfoByIdPrikey(id_prikey);
        return map;
    }
    /**
     * 根据id_prikey保存
     */
    @PostMapping("updateLoanFileInfoByIdPrikey")
    public Map<String,Object> updateLoanFileInfoByIdPrikey(LoanFileInfoBean loanFileInfoBean){
        Map<String, Object> map = new HashMap<>();
        if(loanFileInfoBean.getId_prikey()==null){
            map.put("code",400);
            map.put("msg","id_prikey不能为空");
            return map;
        }
        map=loanInfoBeanService.updateLoanFileInfoByIdPrikey(loanFileInfoBean);
        return map;
    }
    /**
     * 根据id_prikey查询
     */
    @PostMapping("selectLoanFileInfoByIdPrikey")
    public Map<String,Object> selectLoanFileInfoByIdPrikey(Integer id_prikey){
        Map<String, Object> map = new HashMap<>();
        if(id_prikey==null){
            map.put("code",400);
            map.put("msg","id_prikey不能为空");
            return map;
        }
        map=loanInfoBeanService.selectLoanFileInfoByIdPrikey(id_prikey);
        return map;
    }
    /**
     * 新增
     */
    @PostMapping("insertLoanFileInfo")
    public Map<String, Object> insertLoanFileInfo(LoanFileInfoBean loanFileInfoBean, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if(isEmpty(loanFileInfoBean.getFilePath())){
            map.put("code",400);
            map.put("msg","文件路径不能为空");
            return map;
        }
        HB_User hb_user = sessionService.getHbUserBySession(session);
        loanFileInfoBean.setCreateBy(hb_user.getName());
        loanFileInfoBean.setCreateOn(new Date());
        map = loanInfoBeanService.insertLoanFileInfo(loanFileInfoBean);
        return map;
    }

    /**
     * 分页查询
     *
     * @param
     * @return
     */
    @PostMapping("selectLoanFileInfo")
    public String selectLoanFileInfo(LoanFileInfoBean loanFileInfoBean) {
        CallResult<LoanFileInfoBean> result = loanInfoBeanService.selectLoanFileInfo(loanFileInfoBean);
        return gson.toJson(result);
    }

}
