package com.hbsoft.csms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hb.bean.hbcmsql.HB_User;
import com.hb.util.DateUtil;
import com.hbsoft.common.enumtype.*;
import com.hbsoft.csms.dao.service.ProcessBusinessBeanDaoService;
import com.hbsoft.csms.service.*;
import com.hbsoft.csms.bean.*;
import com.hbsoft.csms.vo.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hb.util.BasePassUtil;
import com.hb.util.V1Pass;
import com.hbsoft.csms.dao.HbcmUserDao;
import com.hbsoft.csms.dao.service.LoanDistributeBeanDaoService;
import com.hbsoft.csms.dao.service.LoanRepayBeanDaoService;

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
public class RepayController extends ABaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LoanInfoService loanInfoService;

    @Autowired
    private LoanRepayService loanRepayService;

    @Autowired
    private LoanRepayBeanDaoService loanRepayBeanDaoService;

    @Autowired
    private HbcmUserDao hbcmUserDao;

    @Autowired
    private DeptUserService deptUserService;

    @Autowired
    private LoanDistributeBeanDaoService loanDistributeBeanDaoService;

    @Autowired
    ProcessService processService;

    @Autowired
    ProcessHandleService processHandleService;

    @Autowired
    ProcessBusinessBeanDaoService processBusinessBeanDaoService;
    @Autowired
    SessionService sessionService;

    @RequestMapping("loanRepayPagingData")
    public String loanRepayPagingData(LoanRepayBean loanRepayBean, HttpSession session){
        Map<String, Object> map = new HashMap<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                map.put("code", 400);
                map.put("msg", "请重新登录");
                return gson.toJson(map);
            }
            loanRepayBean.setUserId(hb_user.getName());
            loanRepayBean.setDeptId(hb_user.getDeptId());
            if(StringUtils.isNotBlank(loanRepayBean.getName())){
                loanRepayBean.setName(V1Pass.pass_encode(loanRepayBean.getName()));
            }
            if(StringUtils.isNotBlank(loanRepayBean.getIdNum())){
                loanRepayBean.setIdNum(V1Pass.pass_encode(loanRepayBean.getIdNum()));
            }
            if(StringUtils.isNotBlank(loanRepayBean.getMobile())){
                loanRepayBean.setMobile(V1Pass.pass_encode(loanRepayBean.getMobile()));
            }
            map = loanRepayBeanDaoService.getPagingData(loanRepayBean);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",400);
            map.put("msg","操作异常");
        }
        return gson.toJson(map);
    }

    @RequestMapping("loanRepayAllPagingData")
    public String loanRepayAllPagingData(LoanRepayBean loanRepayBean){
        Map<String, Object> map = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(loanRepayBean.getName())){
                loanRepayBean.setName(V1Pass.pass_encode(loanRepayBean.getName()));
            }
            if(StringUtils.isNotBlank(loanRepayBean.getIdNum())){
                loanRepayBean.setIdNum(V1Pass.pass_encode(loanRepayBean.getIdNum()));
            }
            if(StringUtils.isNotBlank(loanRepayBean.getMobile())){
                loanRepayBean.setMobile(V1Pass.pass_encode(loanRepayBean.getMobile()));
            }
            map = loanRepayBeanDaoService.getPagingData(loanRepayBean);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",400);
            map.put("msg","操作异常");
        }
        return gson.toJson(map);
    }

    /**
    * @author hwl
    * @description 补打列表
    * @date 2020/5/21
    */
    @RequestMapping("loanRepayPrintPagingData")
    public String loanRepayPrintPagingData(LoanRepayBean loanRepayBean,HttpSession session){
        Map<String, Object> map = new HashMap<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                map.put("code", 400);
                map.put("msg", "请重新登录");
                return gson.toJson(map);
            }
            loanRepayBean.setUserId(hb_user.getName());
            loanRepayBean.setDeptId(hb_user.getDeptId());
            loanRepayBean.setRepayType(Integer.valueOf(RepayTypeEnum.REPAY_TYPE_NORMAL.getCode()));
            loanRepayBean.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_SENCOND.getCode());
            loanRepayBean.setLoanType(Integer.valueOf(LoanTypeEnum.LOAN_TYPE_DB.getCode()));
            if(StringUtils.isNotBlank(loanRepayBean.getName())){
                loanRepayBean.setName(V1Pass.pass_encode(loanRepayBean.getName()));
            }
            if(StringUtils.isNotBlank(loanRepayBean.getIdNum())){
                loanRepayBean.setIdNum(V1Pass.pass_encode(loanRepayBean.getIdNum()));
            }
            if(StringUtils.isNotBlank(loanRepayBean.getMobile())){
                loanRepayBean.setMobile(V1Pass.pass_encode(loanRepayBean.getMobile()));
            }
            map = loanRepayBeanDaoService.getPagingData(loanRepayBean);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",400);
            map.put("msg","操作异常");
        }
        return gson.toJson(map);
    }

    @RequestMapping("loanResponsiblyRepayPagingData")
    public String loanResponsiblyRepayPagingData(LoanRepayBean loanRepayBean,HttpSession session){
        Map<String, Object> map = new HashMap<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                map.put("code", 400);
                map.put("msg", "请重新登录");
                return gson.toJson(map);
            }
            loanRepayBean.setUserId(hb_user.getName());
            loanRepayBean.setDeptId(hb_user.getDeptId());
            map = loanRepayBeanDaoService.getPagingData(loanRepayBean);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",400);
            map.put("msg","操作异常");
        }
        return gson.toJson(map);
    }

    @RequestMapping("loanRepayInfo")
    public String loanRepayInfo(Integer id_prikey){
        CallResult<LoanRepayBean> result = new CallResult<>();
        if(ObjectUtils.isEmpty(id_prikey)){
            result.setFailResult("参数有误");
            return gson.toJson(result);
        }
        try {
            LoanRepayBean loanRepayBean = loanRepayBeanDaoService.getById(id_prikey);
            if(loanRepayBean.getLoanBeginDate() != null){
                loanRepayBean.setLoanBeginDateStr(DateUtil.formatDate(loanRepayBean.getLoanBeginDate(),"yyyy-MM-dd"));
            }
            if(loanRepayBean.getLoanEndDate() != null){
                loanRepayBean.setLoanEndDateStr(DateUtil.formatDate(loanRepayBean.getLoanEndDate(),"yyyy-MM-dd"));
            }
            if(loanRepayBean.getCheckSecondDate() != null){
                loanRepayBean.setCheckSecondDateStr(DateUtil.formatDate(loanRepayBean.getCheckSecondDate(),"yyyy-MM-dd"));
            }
            if(loanRepayBean.getCheckFirstDate() != null){
                loanRepayBean.setCheckFirstDateStr(DateUtil.formatDate(loanRepayBean.getCheckFirstDate(),"yyyy-MM-dd"));
            }

            loanRepayBean.setName(V1Pass.pass_decode(loanRepayBean.getName()));
            result.setSuccessResult(loanRepayBean);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult("操作异常");
        }
        return gson.toJson(result);
    }

    /**
    * @author hwl
    * @description 获取还款信息和流程信息
    * @date 2020/5/15
    */
    @RequestMapping("loanRepayAndProcessInfo")
    public String loanRepayAndProcessInfo(@Validated  LoanRepayAndProcessInfoVo vo){
        CallResult<LoanRepayBean> result = new CallResult<>();
        try {
            ProcessBusinessBean processBusinessBean = processBusinessBeanDaoService.getById(vo.getId_prikey());
            if(ObjectUtils.isEmpty(processBusinessBean)){
                result.setFailResult("未查询到流程");
                return gson.toJson(result);
            }
            LoanRepayBean loanRepayBean = loanRepayBeanDaoService.getById(Integer.valueOf(processBusinessBean.getBusinessId()));
            if(loanRepayBean.getLoanBeginDate() != null){
                loanRepayBean.setLoanBeginDateStr(DateUtil.formatDate(loanRepayBean.getLoanBeginDate(),"yyyy-MM-dd"));
            }
            if(loanRepayBean.getLoanEndDate() != null){
                loanRepayBean.setLoanEndDateStr(DateUtil.formatDate(loanRepayBean.getLoanEndDate(),"yyyy-MM-dd"));
            }
            if(loanRepayBean.getCheckSecondDate() != null){
                loanRepayBean.setCheckSecondDateStr(DateUtil.formatDate(loanRepayBean.getCheckSecondDate(),"yyyy-MM-dd"));
            }
            if(loanRepayBean.getCheckFirstDate() != null){
                loanRepayBean.setCheckFirstDateStr(DateUtil.formatDate(loanRepayBean.getCheckFirstDate(),"yyyy-MM-dd"));
            }
            loanRepayBean.setName(V1Pass.pass_decode(loanRepayBean.getName()));
            CallResult<ProcessBusinessBean>  processResult = processHandleService.productProcessInfo(vo.getId_prikey(),vo.getProcessAttribute());
            if(processResult.isExec()){
                loanRepayBean.setProcessBusinessBean(processResult.getData());
            }
            result.setSuccessResult(loanRepayBean);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult("操作异常");
        }
        return gson.toJson(result);
    }



    /**
    * @author hwl
    * @description 还款记录
    * @date 2020/4/1
    */
    @RequestMapping("rePayLoanInfoLog")
    public String rePayLoanInfoLog(String loanContractNum){
        Map<String, Object> map = new HashMap<>();
        try {
            if(StringUtils.isEmpty(loanContractNum)){
                map.put("code",400);
                map.put("msg","参数有误");
                return gson.toJson(map);
            }
            LoanRepayBean loanRepayBean = new LoanRepayBean();
            loanRepayBean.setLoanContractNum(loanContractNum);
            map = loanRepayBeanDaoService.getPagingData(loanRepayBean);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",400);
            map.put("msg","操作异常");
        }
        return gson.toJson(map);
    }

    /**
    * @author hwl
    * @description 根据还款金额计算结欠本金和利息
    * @date 2020/4/1
    */
    @RequestMapping("reCountLoan")
    public String reCountLoan(@Validated RepayVo vo){
        CallResult<LoanRepayBean> result =  new CallResult<>();
        try {
            result = loanRepayService.reCountRepay(vo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult();
        }
        return  gson.toJson(result);
    }

    /*
     * @Author hwl
     * @Description 补录还款计算
     * @Date 2020/5/13
     * @Param [vo]
     * @return java.lang.String
     **/
    @RequestMapping("reCountAddRepay")
    public String reCountAddRepay(@Validated RepayVo vo){
        CallResult<LoanRepayBean> result =  new CallResult<>();
        try {
            result = loanRepayService.reCountAddRepay(vo,false);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult();
        }
        return  gson.toJson(result);
    }

    /**
    * @author hwl
    * @description 还款初审，只保存记录，不更新loanInfo
    * @date 2020/4/1
    */
    @RequestMapping("rePayLoanFirst")
    public String rePayLoanFirst(@Validated RepayVo vo,HttpSession session){
        CallResult<LoanRepayBean> countResult =  new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                countResult.setFailResult("请重新登录");
                return gson.toJson(countResult);
            }
            countResult = loanRepayService.rePayLoanFirst(vo,hb_user);
        } catch (Exception e) {
            e.printStackTrace();
            countResult.setFailResult("操作异常");
        }
        return  gson.toJson(countResult);
    }

    /**
    * @author hwl
    * @description 复核时认证
    * @date 2020/4/2
    */
    @RequestMapping("rePayLoanLogin")
    public String rePayLoanLogin(String userName, String password){
        CallResult<String> result =  new CallResult<>();
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            result.setFailResult("用户名或密码错误");
            return gson.toJson(result);
        }
        try {
            HbcmUser param = new HbcmUser();
            param.setName(userName);
            HbcmUser hbcmUser = hbcmUserDao.findByField(param);
            if(hbcmUser != null) {
                String pass = BasePassUtil.transformationPass(userName, hbcmUser.getPassword());
                if (!password.equalsIgnoreCase(pass)) {
                    result.setFailResult("用户名或密码错误!");
                } else {
                    result.setSuccessResult();
                    result.setData(hbcmUser.getName());
                }
            }else {
                result.setFailResult("用户不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult();
        }
        return gson.toJson(result);
    }

    /**
    * @author hwl
    * @description 复审，更新loanInfo
    * @date 2020/4/1
    */
    @RequestMapping("rePayLoanSecond")
    public String rePayLoanSecond(@Validated  RepayLoanSecondVo vo,HttpSession session){
        CallResult<String> result =  new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            if("0".equals(vo.getUserId())){
                vo.setUserId(hb_user.getName());
            }
            result = loanRepayService.rePayLoanSecond(vo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFailResult();
        }
        return  gson.toJson(result);
    }

    /**
    * @author hwl
    * @description 复审列表
    * @date 2020/4/1
    */
    @RequestMapping("rePayLoanSecondPagingData")
    public String rePayLoanSecondPagingData(LoanRepayBean loanRepayBean,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                map.put("code", 400);
                map.put("msg", "请重新登录");
                return gson.toJson(map);
            }
            if(StringUtils.isNotBlank(loanRepayBean.getName())){
                loanRepayBean.setName(V1Pass.pass_encode(loanRepayBean.getName()));
            }
            if(StringUtils.isNotBlank(loanRepayBean.getIdNum())){
                loanRepayBean.setIdNum(V1Pass.pass_encode(loanRepayBean.getIdNum()));
            }
            if(ObjectUtils.isEmpty(loanRepayBean.getStatus())){
                loanRepayBean.setStatus(CheckRepayStatusEnum.CHECK_REPAY_STATUS_FIRST.getCode());
            }
            System.out.println(hb_user+"------------------");
            loanRepayBean.setUserId(hb_user.getName());
            loanRepayBean.setDeptId(hb_user.getDeptId());
            map = loanRepayBeanDaoService.getPagingData(loanRepayBean);
        } catch (Exception e) {
            map.put("code",400);
            map.put("msg","暂无数据");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    @RequestMapping("repayPagingData")
    public String repayPagingData(LoanInfoBean loanInfoBean){
        Map<String, Object> map = null;
        try {
            loanInfoService.loanInfoV1PassHandle(loanInfoBean);
            map = loanRepayBeanDaoService.getRepayPagingData(loanInfoBean);
            if (null != map) {
                return gson.toJson(map);
            } else {
                map = new HashMap<>();
                map.put("code", 400);
                map.put("msg", "暂无数据");
            }
        } catch (Exception e) {
            map = new HashMap<>();
            map.put("code", 400);
            map.put("msg", "获取失败");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 抹账申请发起
     * @param loanRepayBean
     * @return
     */
    @RequestMapping("revokeRepay")
    public String revokeRepay(LoanRepayBean loanRepayBean,HttpSession session){
        CallResult<ProcessHandleBean> result = new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }

            if (null == loanRepayBean.getLoanContractNum() || "".equals(loanRepayBean.getLoanContractNum())) {
                result.setCode(400);
                result.setMsg("参数错误");
                return gson.toJson(result);
            }
            if (null == loanRepayBean.getId_prikey()) {
                result.setCode(400);
                result.setMsg("主键参数错误");
                return gson.toJson(result);
            }
            LoanRepayBean loanRepayBeanRel = loanRepayBeanDaoService.getById(loanRepayBean.getId_prikey());
            if (loanRepayBeanRel.getStatus() != 2 || loanRepayBeanRel.getRepayType() != 1) {
                result.setCode(400);
                result.setMsg("只有正常还款且通过复审才可抹账");
                return gson.toJson(result);
            }
            loanRepayBean.setRevokeDate(new Date());
            loanRepayBean.setUserId(hb_user.getName());
            loanRepayBean.setDeptId(hb_user.getDeptId());

            result = loanRepayService.revokeRepay(loanRepayBean);

        }catch (Exception e) {
            e.printStackTrace();
            result.setFailResult(e.getMessage());
        }
        return gson.toJson(result);
    }

    /**
     * 获取抹账申请信息
     * @param loanContractNum
     * @return
     */
    @PostMapping("revokeRepayInfo")
    public String revokeRepayInfo(String loanContractNum) {
        CallResult<LoanRepayBean> result = new CallResult<>();
        try {
            if (StringUtils.isNotEmpty(loanContractNum)) {
                result = loanRepayService.revokeRepayInfo(loanContractNum);
            }else {
                result.setFailResult("主键参数错误");
            }
        }catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    /**
     * 抹账审核
     * @param loanRepayBean
     * @return
     */
    @PostMapping("examRevokeRepay")
    public String examRevokeRepay(LoanRepayBean loanRepayBean,HttpSession session) {
        CallResult<ProcessHandleBean> result = new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            if (null == loanRepayBean.getOperateType()) {
                result.setFailResult("主键参数错误");
                return gson.toJson(result);
            }
            if (null == loanRepayBean.getLoanContractNum() || "".equals(loanRepayBean.getLoanContractNum())) {
                result.setFailResult("主键参数错误");
                return gson.toJson(result);
            }
            if (null == loanRepayBean.getId_prikey()) {
                result.setFailResult("主键参数错误");
                return gson.toJson(result);
            }
            loanRepayBean.setRevokeDate(new Date());
            loanRepayBean.setUserId(hb_user.getName());
            loanRepayBean.setDeptId(hb_user.getDeptId());
            result = loanRepayService.examRevokeRepay(loanRepayBean);
        }catch (Exception e) {
            result.setFailResult();
            e.printStackTrace();
        }
        return gson.toJson(result);

    }

    /**
     * 抹账撤销
     * @param id_prikey
     * @return
     */
    @PostMapping("revokeRepayCancel")
    public String revokeRepayCancel(Integer id_prikey,HttpSession session) {
        CallResult<String> result = new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }

            if (null == id_prikey) {
                result.setFailResult("主键参数有误");
                return gson.toJson(result);
            }
            result = loanRepayService.revokeRepayCancel(id_prikey,hb_user.getName());
        }catch (Exception e) {
            result.setFailResult("操作失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    //补录还款
    @RequestMapping("addRepay")
    public String addRepay(@Validated  RepayVo vo,HttpSession session){
        CallResult<String> result = new CallResult<>();

        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            result = loanRepayService.addRepay(vo,hb_user.getName());
        } catch (Exception e) {
            result.setCode(400);
            result.setMsg("操作失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    @PostMapping("addRepayCheck")
    public String  addRepayCheck(@Validated RepayAddVo vo,HttpSession session){
        CallResult<ProcessHandleBean> result = new CallResult<>();
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            result = loanRepayService.addRepayCheck(vo,hb_user.getName());
        } catch (Exception e) {
            result.setCode(400);
            result.setMsg("操作失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }


    //补录还款
    @RequestMapping("repayPrint")
    public String repayPrint(Integer id_prikey,HttpSession session){
        CallResult<ProcessHandleBean> result = new CallResult<>();
        if(ObjectUtils.isEmpty(id_prikey)){
            result.setFailResult("参数有误");
            return gson.toJson(result);
        }
        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            result = loanRepayService.repayPrint(id_prikey,hb_user.getName());
        } catch (Exception e) {
            result.setCode(400);
            result.setMsg("操作失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    @PostMapping("repayPrintCheck")
    public String  repayPrintCheck(@Validated RepayAddVo vo,HttpSession session){
        CallResult<ProcessHandleBean> result = new CallResult<>();

        try {
            HB_User hb_user = sessionService.getHbUserBySession(session);;
            if(ObjectUtils.isEmpty(hb_user)){
                result.setFailResult("请重新登录");
                return gson.toJson(result);
            }
            result = loanRepayService.addRepayCheck(vo,hb_user.getName());
        } catch (Exception e) {
            result.setCode(400);
            result.setMsg("操作失败");
            e.printStackTrace();
        }
        return gson.toJson(result);
    }
}
