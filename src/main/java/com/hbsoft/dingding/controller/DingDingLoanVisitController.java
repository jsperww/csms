package com.hbsoft.dingding.controller;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hb.util.DateUtil;
import com.hb.util.V1Pass;
import com.hbsoft.common.enumtype.LoanFileTypeEnum;
import com.hbsoft.csms.bean.LoanFileInfoBean;
import com.hbsoft.csms.bean.LoanVisitBean;
import com.hbsoft.csms.dao.service.LoanFileInfoBeanDaoService;
import com.hbsoft.csms.dao.service.LoanVisitBeanDaoService;
import com.hbsoft.csms.service.LoanVisitService;
import com.hbsoft.csms.vo.LoanInfoVo;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:    RepayController
 * Package:    com.hbsoft.csms.controller
 * Description:
 * Datetime:    2020/3/27   9:40
 * Author:  hwl
 */
@RestController
@RequestMapping(value = "dingDing", produces = "application/json;charset=UTF-8")
public class DingDingLoanVisitController extends ABaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public LoanVisitBeanDaoService loanVisitBeanDaoService;
    @Autowired
    public LoanFileInfoBeanDaoService loanFileInfoBeanDaoService;
    @Autowired
    public LoanVisitService loanVisitService;


    @RequestMapping("ddMyLoanVisitPagingData")
    public String  ddMyLoanVisitPagingData(LoanInfoVo vo,HttpSession session){
        Map<String, Object> map = null;
        CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
        if(!ddResult.isExec()){
            return gson.toJson(ddResult);
        }
        DingDingUserInfo dingDingUserInfo = ddResult.getData();
        vo.setName(V1Pass.pass_encode(vo.getName()));
        vo.setCreateBy(dingDingUserInfo.getHbUserId());
        vo.setUserId(dingDingUserInfo.getHbUserId());
        vo.setDeptId(String.valueOf(dingDingUserInfo.getHbUserDeptId()));
        try {
            if(StringUtils.isBlank(vo.getVisitFlag())){
                map = loanVisitBeanDaoService.getMyUnVisitPagingData(vo);
            }else{
                map = loanVisitBeanDaoService.getMyVisitPagingData(vo);
            }
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

    /**
     * 回访详情
     * @param loanVisitBean
     * @return
     */
    @RequestMapping("ddLoanVisitPagingData")
    public String  ddLoanVisitPagingData(LoanVisitBean loanVisitBean){
        Map<String, Object> map = null;
        try {
            map = loanVisitBeanDaoService.getPagingData(loanVisitBean);
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


    @RequestMapping("ddLoanVisitFilePagingData")
    public String  ddLoanVisitFilePagingData(LoanFileInfoBean loanFileInfoBean){
        Map<String, Object> map  = new HashMap<>();
        try {
            if(ObjectUtils.isEmpty(loanFileInfoBean.getRelId())){
                map.put("code",400);
                map.put("msg","relId有误");
                return gson.toJson(map);
            }
            LoanVisitBean param = new LoanVisitBean();
            param.setId_prikey(Integer.valueOf(loanFileInfoBean.getRelId()));
            LoanVisitBean loanVisitBean = loanVisitBeanDaoService.getByField(param);
            if(ObjectUtils.isNotEmpty(loanVisitBean)){
                if(ObjectUtils.isNotEmpty(loanVisitBean.getVisitDate())){
                    loanVisitBean.setVisitDateStr(DateUtil.formatDate(loanVisitBean.getVisitDate(),"yyyy-MM-dd"));
                }
                if(ObjectUtils.isNotEmpty(loanVisitBean.getWarnDate())){
                    loanVisitBean.setWarnDateStr(DateUtil.formatDate(loanVisitBean.getWarnDate(),"yyyy-MM-dd"));
                }
            }

            loanFileInfoBean.setType(LoanFileTypeEnum.LOAN_FILE_TYPE_VISIT.getCode());
            map = loanFileInfoBeanDaoService.getPagingData(loanFileInfoBean);
            if (null != map) {
                map.put("loanVisit",loanVisitBean);
                return gson.toJson(map);
            }else {
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

    @RequestMapping("ddAddLoanVisit")
    public String ddAddLoanVisit(@Validated LoanVisitBean loanVisitBean,HttpSession session){
        CallResult<String> result = new CallResult<>();
        try {
            CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
            if(!ddResult.isExec()){
                return gson.toJson(ddResult);
            }
            DingDingUserInfo dingDingUserInfo = ddResult.getData();
            result = loanVisitService.addLoanVisit(loanVisitBean,dingDingUserInfo.getHbUserId());
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult();
        }

        return  gson.toJson(result);
    }

    @RequestMapping("ddUpdateLoanVisit")
    public String ddUpdateLoanVisit(@Validated LoanVisitBean loanVisitBean,HttpSession session){
        CallResult<String> result = new CallResult<>();
        try {
            CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
            if(!ddResult.isExec()){
                return gson.toJson(ddResult);
            }
            DingDingUserInfo dingDingUserInfo = ddResult.getData();
            result = loanVisitService.updateLoanVisit(loanVisitBean,dingDingUserInfo.getHbUserId());
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult();
        }
        return  gson.toJson(result);
    }

    @RequestMapping("ddDeleteLoanVisit")
    public String deleteLoanVisit(Integer id_prikey,HttpSession session){
        CallResult<String> result = new CallResult<>();
        if(id_prikey == null){
            result.setFailResult("参数有误");
            return  gson.toJson(result);
        }
        CallResult<DingDingUserInfo> ddResult = getDingDingUserInfoBySession(session);
        if(!ddResult.isExec()){
            return gson.toJson(ddResult);
        }
        try {
            result = loanVisitService.deleteLoanVisitByIdPrikey(id_prikey);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult();
        }
        return  gson.toJson(result);
    }


    public CallResult<DingDingUserInfo> getDingDingUserInfoBySession(HttpSession session){
        CallResult<DingDingUserInfo> result  = new CallResult<>();
        DingDingUserInfo dingDingUserInfo = (DingDingUserInfo) session.getAttribute("dingDingUserInfo");
        if(ObjectUtils.isEmpty(dingDingUserInfo)){
            result.setFailResult("登录超时，请重新登录");
        }else {
            result.setSuccessResult(dingDingUserInfo);
        }
        return result;
    }

}
