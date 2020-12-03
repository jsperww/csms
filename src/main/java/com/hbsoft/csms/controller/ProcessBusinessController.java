package com.hbsoft.csms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hbsoft.common.enumtype.ProcessBusinessStatusEnum;
import com.hbsoft.common.enumtype.YesOrNoEnum;
import com.hbsoft.csms.bean.ProcessBusinessBean;
import com.hbsoft.csms.bean.ProcessFileBean;
import com.hbsoft.csms.bean.ProcessHandleBean;
import com.hbsoft.csms.dao.service.LoanInfoBeanDaoService;
import com.hbsoft.csms.dao.service.ProcessBusinessBeanDaoService;
import com.hbsoft.csms.dao.service.ProcessFileBeanDaoService;
import com.hbsoft.csms.service.*;
import com.hbsoft.csms.vo.ProcessHandleVo;
import com.hbsoft.csms.vo.ProcessVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.controller.ABaseController;
import com.hbsoft.common.enumtype.ProcessBusinessEnum;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.vo.LoanInfoVo;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class ProcessBusinessController extends ABaseController {

	@Autowired
	LoanInfoService loanInfoService;
	
	@Autowired
	ProcessService processService;

	@Autowired
	LoanRepayService loanRepayService;

	@Autowired
	ProcessFileBeanDaoService processFileBeanDaoService;

	@Autowired
	ProcessHandleService processHandleService;

	@Autowired
	ProcessBusinessBeanDaoService processBusinessBeanDaoService;

	@Autowired
	LoanInfoBeanDaoService loanInfoBeanDaoService;

	@Autowired
	ProcessBusinessService processBusinessService;

	@Autowired
	SessionService sessionService;

	/**
	 * 所有未审核及已退回修改流程
	 * @param vo
	 * @return
	 */
	@PostMapping("loanUpdateApplyList")
	public String loanUpdateApplyList(LoanInfoVo vo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);

			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_LOANINFO_UPDATE.getCode());
			vo.setUserId(hb_user.getName());
			map = processService.findProcessBusinessPagingData(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

    /**
     * 我操作过的修改流程
     * @param vo
     * @return
     */
    @PostMapping("loanUpdateOperateList")
    public String loanUpdateOperateList(LoanInfoVo vo,HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
            LoanInfoBean loanInfoBean = new LoanInfoBean();
            loanInfoBean.setName(vo.getName());
            loanInfoBean.setIdNum(vo.getIdNum());
            loanInfoService.loanInfoV1PassHandle(loanInfoBean);

            vo.setName(loanInfoBean.getName());
            vo.setIdNum(loanInfoBean.getIdNum());
            vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_LOANINFO_UPDATE.getCode());
            vo.setOperator(hb_user.getName());
            map = processService.findProcessBusinessOperatePagingData(vo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            map.put("code", 400);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

	/**
	 * 我的修改申请进度
	 * @param vo
	 * @return
	 */
	@PostMapping("myLoanUpdateApplyList")
    public String myLoanUpdateApplyList(LoanInfoVo vo,HttpSession session){
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);

			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_LOANINFO_UPDATE.getCode());
			vo.setOperator(hb_user.getName());
			map = processService.findMyProcessBusinessPagingData(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	@PostMapping("repayRevokeApplyList")
	public String repayRevokeApplyList(LoanInfoVo vo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);

			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_REVOKE.getCode());
			vo.setDeptId(hb_user.getDeptId());
			vo.setUserId(hb_user.getName());
			map = processService.findProcessBusinessPagingData(vo);
		}catch (Exception e) {
			map.put("code",400);
			map.put("message","操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	/**
	 * @author yj
	 * @description 抹账操作过的 列表
	 * @date 2020/5/15
	 */
	@PostMapping("revokeRepayOperatePagingData")
	public String revokeRepayOperatePagingData(LoanInfoVo vo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_REVOKE.getCode());
			vo.setOperator(hb_user.getName());
			map = processService.findProcessBusinessOperatePagingData(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	//补录还款列表
	@PostMapping("addRepayPagingData")
	public String addRepayPagingData(LoanInfoVo vo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_ADD.getCode());
			vo.setUserId(hb_user.getName());
			map = processService.findProcessBusinessPagingData(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	//补录补打列表
	@PostMapping("repayPrintPagingData")
	public String repayPrintPagingData(LoanInfoVo vo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_PRINT.getCode());
			vo.setUserId(hb_user.getName());
			map = processService.findProcessBusinessPagingData(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}



	/**
	* @author hwl
	* @description 补录操作过的 列表
	* @date 2020/5/15
	*/
	@PostMapping("addRepayOperatePagingData")
	public String addRepayOperatePagingData(LoanInfoVo vo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_REPAY_ADD.getCode());
			vo.setOperator(hb_user.getName());
			map = processService.findProcessBusinessOperatePagingData(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}


	/**
	* @author hwl
	* @description 获取附件
	* @date 2020/5/15
	*/
	@RequestMapping("getAllAppendixByLogId")
	public String getAllAppendixByLogId(Integer id_prikey){
		CallResult<List<ProcessFileBean>> result = new CallResult<>();
		if(ObjectUtils.isEmpty(id_prikey)){
			result.setFailResult("参数有误");
			return gson.toJson(result);
		}
		try {
			ProcessFileBean param = new ProcessFileBean();
			param.setBatchCode(String.valueOf(id_prikey));
			List<ProcessFileBean> list = processFileBeanDaoService.getAll(param);
			result.setSuccessResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailResult("操作失败");
		}
		return gson.toJson(result);
	}

	@PostMapping("getMyProcessBusinessPagingData")
	public String getMyProcessBusinessPagingData(LoanInfoVo vo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setOperator(hb_user.getName());
			vo.setNotProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_LOANINFO_UPDATE.getCode());
			map = processService.findMyProcessBusinessPagingData(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	@PostMapping("processHandleCancel")
	public String processHandleCancel(Integer id_prikey,HttpSession session){
		CallResult<String> result = new CallResult<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			ProcessBusinessBean bean = processBusinessBeanDaoService.getById(id_prikey);
			if(ObjectUtils.isEmpty(bean)){
				result.setFailResult("未查询到流程");
				return gson.toJson(result);
			}
			if(ProcessBusinessStatusEnum.PROCESS_NODE_STATUS_FINISH.getCode().equals(bean.getProcessStatus())){
				result.setFailResult("本流程已完成，不可撤销");
				return gson.toJson(result);
			}
			result = processHandleService.processHandleCancel(id_prikey,hb_user.getName());
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailResult("操作异常");
		}
		return gson.toJson(result);
	}

	//认领审核列表
	@PostMapping("claimPagingData")
	public String claimPagingData(LoanInfoVo vo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_CLAIM.getCode());
			vo.setUserId(hb_user.getName());
			map = processService.findProcessBusinessPagingData(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	/**
	 * @author hwl
	 * @description 已审核列表 列表
	 * @date 2020/5/15
	 */
	@PostMapping("claimOperatePagingData")
	public String claimOperatePagingData(LoanInfoVo vo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setProcessAttribute(ProcessBusinessEnum.PROCESS_BUSINESS_CLAIM.getCode());
			vo.setOperator(hb_user.getName());
			map = processService.findProcessBusinessOperatePagingData(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	@PostMapping("getMyProcessBusinessOperatePagingData")
	public String getMyProcessBusinessOperatePagingData(LoanInfoVo vo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		HB_User hb_user = sessionService.getHbUserBySession(session);;
		if(ObjectUtils.isEmpty(hb_user)){
			map.put("code", 400);
			map.put("msg", "请重新登录");
			return gson.toJson(map);
		}

		if(StringUtils.isBlank(vo.getProcessAttribute())){
			map.put("code", 400);
			map.put("message", "流程属性有误");
			return gson.toJson(map);
		}
		try {
			LoanInfoBean loanInfoBean = new LoanInfoBean();
			loanInfoBean.setName(vo.getName());
			loanInfoBean.setIdNum(vo.getIdNum());
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			vo.setName(loanInfoBean.getName());
			vo.setIdNum(loanInfoBean.getIdNum());
			vo.setOperator(hb_user.getName());
			vo.setProcessAttribute(vo.getProcessAttribute());
			map = processService.findMyProcessBusinessPagingData(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	//认领审核
	@PostMapping("claimProcessHandle")
	public String claimProcessHandle(@Validated ProcessHandleVo vo,Integer limitMonth,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		HB_User hb_user = sessionService.getHbUserBySession(session);;
		if(ObjectUtils.isEmpty(hb_user)){
			result.setFailResult("请重新登录");
			return gson.toJson(result);
		}
		try {
			result = processBusinessService.claimProcessHandle(vo,limitMonth,hb_user.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult("操作异常");
			e.printStackTrace();
		}
		return gson.toJson(result);
	}


    @PostMapping("productProcessInfo")
    public String productProcessInfo(Integer id_prikey, String processAttribute){
        CallResult<ProcessBusinessBean> processResult = new CallResult<>();
        try {
            processResult = processHandleService.productProcessInfo(id_prikey,processAttribute);
        } catch (Exception e) {
            processResult.setFailResult("操作异常");
            e.printStackTrace();
        }
        return gson.toJson(processResult);
    }
}
