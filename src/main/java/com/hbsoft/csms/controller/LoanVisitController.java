package com.hbsoft.csms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hb.bean.hbcmsql.HB_User;
import com.hbsoft.csms.service.SessionService;
import com.hbsoft.csms.vo.LoanInfoVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hb.util.V1Pass;
import com.hbsoft.common.enumtype.LoanFileTypeEnum;
import com.hbsoft.common.enumtype.PeyMentPersonEnum;
import com.hbsoft.csms.bean.LoanFileInfoBean;
import com.hbsoft.csms.bean.LoanGuarantee;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.bean.LoanVisitBean;
import com.hbsoft.csms.dao.service.LoanFileInfoBeanDaoService;
import com.hbsoft.csms.dao.service.LoanGuaranteeDaoService;
import com.hbsoft.csms.dao.service.LoanInfoBeanDaoService;
import com.hbsoft.csms.dao.service.LoanVisitBeanDaoService;
import com.hbsoft.csms.service.LoanInfoService;
import com.hbsoft.csms.service.LoanVisitService;

import javax.servlet.http.HttpSession;

/**
 * ClassName:    RepayController
 * Package:    com.hbsoft.csms.controller
 * Description:
 * Datetime:    2020/3/27   9:40
 * Author:  hwl
 */
@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class LoanVisitController extends ABaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public LoanVisitBeanDaoService loanVisitBeanDaoService;
    @Autowired
    public LoanFileInfoBeanDaoService loanFileInfoBeanDaoService;
    @Autowired
    public LoanVisitService loanVisitService;
    @Autowired
    public LoanInfoBeanDaoService loanInfoBeanDaoService;
    @Autowired
    public LoanGuaranteeDaoService loanGuaranteeDaoService;
    @Autowired
    public LoanInfoService loanInfoService;
    @Autowired
    SessionService sessionService;

    @RequestMapping("loanVisitPagingData")
    public String  loanVisitPagingData(LoanVisitBean loanVisitBean){
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

    @RequestMapping("loanVisitFilePagingData")
    public String  loanVisitPagingData(LoanFileInfoBean loanVisitBean){
        Map<String, Object> map = null;
        try {
            loanVisitBean.setType(LoanFileTypeEnum.LOAN_FILE_TYPE_VISIT.getCode());
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

    @PostMapping("getPeyMentPerson")
    public String getPeyMentPerson(LoanInfoBean lib){
    	 CallResult<List<Map<String,Object>>> result = new CallResult<>();
    	 List<Map<String,Object>>list = new ArrayList<>();
    	 if(isEmpty(lib.getLoanContractNum())){
    		 result.setFailResult("贷款合同号不能为空");
    		 return gson.toJson(result);
    	 }
    	 try {
			LoanInfoBean info = loanInfoBeanDaoService.getByField(lib);
			
			Map<String,Object>map = new HashMap<>();
			map.put("name", V1Pass.pass_decode(info.getName()));
			map.put("idNum", V1Pass.pass_decode(info.getIdNum()));
			map.put("type", PeyMentPersonEnum.BORROWER.getCode());
			map.put("id_prikey", info.getId_prikey());
			list.add(map);
			LoanGuarantee guarantee = new LoanGuarantee();
			guarantee.setLoanContractNum(lib.getLoanContractNum());
			guarantee.setRelType("2");
			List<LoanGuarantee> lgList = loanGuaranteeDaoService.getAll(guarantee);
			for(LoanGuarantee lg:lgList){
				Map<String,Object>map2 = new HashMap<>();
				map2.put("name", lg.getName());
				map2.put("idNum", lg.getIdNum());
				map2.put("type", PeyMentPersonEnum.GUARANTEE.getCode());
				map2.put("id_prikey", lg.getId_prikey());
				list.add(map2);
			}
			result.setSuccessResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailResult();
		}
    	 return gson.toJson(result);
    }
    
    @RequestMapping("addLoanVisit")
    public String addLoanVisit(@Validated  LoanVisitBean loanVisitBean, HttpSession session){
        CallResult<String> result = new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            result = loanVisitService.addLoanVisit(loanVisitBean,hb_user.getName());
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult();
        }

        return  gson.toJson(result);
    }

    @RequestMapping("updateLoanVisit")
    public String updateLoanVisit(@Validated LoanVisitBean loanVisitBean,HttpSession session){
        CallResult<String> result = new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            result = loanVisitService.updateLoanVisit(loanVisitBean,hb_user.getName());
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult();
        }
        return  gson.toJson(result);
    }

    @RequestMapping("myLoanVisitPagingData")
    public String  myLoanVisitPagingData(LoanInfoVo vo,HttpSession session){
        Map<String, Object> map = null;
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);
            if(ObjectUtils.isEmpty(hb_user)){
                map.put("code",400);
                map.put("msg","请重新登录");
                return gson.toJson(map);
            }
            LoanInfoBean loanInfoBean = new LoanInfoBean();
            loanInfoBean.setName(vo.getName());
            loanInfoBean.setIdNum(vo.getIdNum());
            loanInfoBean.setMobile(vo.getMobile());
            loanInfoService.loanInfoV1PassHandle(loanInfoBean);

            vo.setName(loanInfoBean.getName());
            vo.setIdNum(loanInfoBean.getIdNum());
            vo.setMobile(loanInfoBean.getMobile());
            vo.setCreateBy(hb_user.getName());
            vo.setUserId(hb_user.getName());
            vo.setDeptId(hb_user.getDeptId());
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

    @RequestMapping("deleteLoanVisit")
    public String deleteLoanVisit(Integer id_prikey){
        CallResult<String> result = new CallResult<>();
        if(id_prikey == null){
            result.setFailResult("参数有误");
            return  gson.toJson(result);
        }
        try {
            result = loanVisitService.deleteLoanVisitByIdPrikey(id_prikey);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult();
        }
        return  gson.toJson(result);
    }

}
