package com.hbsoft.csms.controller;

import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hb.controller.ABaseController;
import com.hbsoft.csms.bean.IndictmentInfo;
import com.hbsoft.csms.bean.LoanFileInfoBean;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.dao.service.*;
import com.hbsoft.csms.service.IndicmentService;
import com.hbsoft.csms.service.LoanInfoService;
import com.hbsoft.csms.service.SessionService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 诉讼管理
 * @Author: yj
 * @Date: 2020/6/8 15:29
 */
@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class IndictmentController extends ABaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IndicmentService indicmentService;

    @Autowired
    LoanInfoService loanInfoService;

    @Autowired
    LoanInfoBeanDaoService loanInfoBeanDaoService;

    @Autowired
    IndictmentMediationDaoService indictmentMediationDaoService;

    @Autowired
    IndictmentCourtDaoService indictmentCourtDaoService;

    @Autowired
    IndictmentExecutionDaoService indictmentExecutionDaoService;

    @Autowired
    LoanFileInfoBeanDaoService loanFileInfoBeanDaoService;

    @Autowired
    SessionService sessionService;

    /**
     * 查询诉讼客户列表
     * @param loanInfoBean
     * @return
     */
    @PostMapping("selectIndictmentLoanInfoList")
    public String selectIndictmentLoanInfoList(LoanInfoBean loanInfoBean) {
        Map<String, Object> map = null;
        logger.info("查询客户信息列表开始");
        loanInfoService.loanInfoV1PassHandle(loanInfoBean);
        try {
            map = indicmentService.selectIndictmentLoanInfoList(loanInfoBean);
            if (null != map) {
                return gson.toJson(map);
            }else {
                map = new HashMap<>();
                map.put("code",400);
                map.put("msg","暂无数据");
            }
        }catch (Exception e) {
            map = new HashMap<>();
            map.put("code",400);
            map.put("msg","获取失败");
            e.printStackTrace();
        }
        logger.info("查询客户信息列表结束 {}" + loanInfoBean.toString());
        return gson.toJson(map);
    }

    /**
     * 获取诉讼客户信息
     * @param loanContractNum
     * @return
     */
    @PostMapping("selectIndictmentLoanInfo")
    public String selectIndictmentLoanInfo(String loanContractNum,Integer id_prikey) {
        CallResult<LoanInfoBean> result = null;
        try {
            if (StringUtils.isNotEmpty(loanContractNum)) {
                result = indicmentService.selectIndictmentLoanInfo(loanContractNum,id_prikey);
            }else {
                result = new CallResult<>();
                result.setFailResult("主键参数错误");
            }
            if (null == id_prikey) {
                result.setFailResult("主键参数错误");
            }
        }catch (Exception e) {
            result = new CallResult<>();
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 添加诉讼信息
     * @param indictmentInfo
     * @return
     */
    @PostMapping("addIndictmentLoanInfo")
    public String addIndictmentLoanInfo(IndictmentInfo indictmentInfo, HttpSession session) {
        CallResult<String> result =null;
        try {

            HB_User hb_user = sessionService.getHbUserBySession(session);
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            //诉讼费用验证
            if(null != indictmentInfo && indictmentInfo.getCourtCost()!=null){
                int i = indictmentInfo.getCourtCost().compareTo(BigDecimal.ZERO);
                if (i==-1){
                    result = new CallResult<>();
                    result.setFailResult("诉讼费用不能小于零");
                    return gson.toJson(result);
                }
            }
            //执行费用验证
            if(null != indictmentInfo && indictmentInfo.getExecutionCost()!=null){
                int i = indictmentInfo.getExecutionCost().compareTo(BigDecimal.ZERO);
                if (i==-1){
                    result = new CallResult<>();
                    result.setFailResult("执行费用不能小于零");
                    return gson.toJson(result);
                }
            }
            if (null == indictmentInfo.getLoanContractNum() || "".equals(indictmentInfo.getLoanContractNum())) {
                result = new CallResult<>();
                result.setFailResult("参数错误");
                return gson.toJson(result);
            }
            result = indicmentService.updateIndictmentLoanInfo(indictmentInfo,hb_user.getName());
            return gson.toJson(result);
        }catch (Exception e) {
            result = new CallResult<>();
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 删除诉讼信息
     * @param indictmentInfo
     * @return
     */
    @PostMapping("deleteIndictmentInfo")
    public String deleteIndictmentInfo(IndictmentInfo indictmentInfo) {
        CallResult<String> result = null;
        try {
            result = indicmentService.deleteIndictmentInfo(indictmentInfo);
        }catch (Exception e) {
            result = new CallResult<>();
            result.setFailResult("删除失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }


    @RequestMapping("loanFilePagingData")
    public String  loanVisitPagingData(@Validated LoanFileInfoBean loanVisitBean){
        Map<String, Object> map = null;
        try {
            map = loanFileInfoBeanDaoService.getPagingData(loanVisitBean);
            if (null != map) {
                return gson.toJson(map);
            }else {
                map = new HashMap<>();
                map.put("code",400);
                map.put("msg","暂无数据");
            }
        }catch (Exception e) {
            map = new HashMap<>();
            map.put("code",400);
            map.put("msg","获取失败");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }
}
