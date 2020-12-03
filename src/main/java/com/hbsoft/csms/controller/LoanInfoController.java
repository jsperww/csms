package com.hbsoft.csms.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hb.annotation.LoginNotRequired;
import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hb.controller.ABaseController;
import com.hb.util.V1Pass;
import com.hbsoft.common.enumtype.DistributeOperationTypeEnum;
import com.hbsoft.common.enumtype.ProcessBusinessEnum;
import com.hbsoft.common.enumtype.YesOrNoEnum;
import com.hbsoft.csms.bean.*;
import com.hbsoft.csms.dao.DictBeanDao;
import com.hbsoft.csms.dao.HbcmUserDao;
import com.hbsoft.csms.dao.service.*;
import com.hbsoft.csms.service.CommonService;
import com.hbsoft.csms.service.LoanInfoService;
import com.hbsoft.csms.service.ProcessHandleService;
import com.hbsoft.csms.service.ProcessService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: yj
 * @Date: 2020/3/26 15:59
 */
@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class LoanInfoController extends ABaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LoanInfoService loanInfoService;

	@Autowired
	LoanInfoBeanDaoService loanInfoBeanDaoService;

	@Autowired
	LoanDistributeBeanDaoService loanDistributeBeanDaoService;

	@Autowired
	LoanGuaranteeDaoService loanGuaranteeDaoService;

	@Autowired
	LoanMortgageDaoService loanMortgageDaoService;

	@Autowired
	LoanDistributeOrgVillvgeBeanDaoService loanDistributeOrgVillvgeBeanDaoService;

	@Autowired
	CommonService commonService;

	@Autowired
	DictBeanDao dictBeanDao;

	@Autowired
	HbcmUserDao hbcmUserDao;

	@Autowired
	LoanInfoImportErrorDaoService loanInfoImportErrorDaoService;

	@Autowired
	LoanInfoUpdateContentDaoService loanInfoUpdateContentDaoService;
	
	@Autowired
	LoanFileInfoBeanDaoService loanFileInfoBeanDaoService;
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	ProcessHandleService processHandleService;

	@Autowired
	ProcessBusinessBeanDaoService processBusinessBeanDaoService;

	@Autowired
	LoanDistributeSourcesBeanDaoService loanDistributeSourcesBeanDaoService;


	/**
	 * 添加新贷款客户
	 *
	 * @param loanInfoBean
	 * @return
	 */
	@PostMapping("addLoanInfo")
	public String addLoanInfo(LoanInfoBean loanInfoBean,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		logger.info("添加贷款客户开始");
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			// 添加客户及备份客户信息
			loanInfoService.addLoanInfoAndSource(loanInfoBean, hb_user.getName());
			result.setCode(0);
			result.setMsg("添加成功");
		} catch (Exception e) {
			result.setCode(400);
			result.setMsg("添加失败");
			e.printStackTrace();
			logger.info("添加客户异常", e);
		}
		logger.info("添加贷款客户结束" + loanInfoBean.toString());
		return gson.toJson(result);
	}

	/**
	 * 删除客户
	 *
	 * @param loanInfoBean
	 * @return
	 */
	@PostMapping("deleteLoanInfo")
	public String deleteLoanInfo(LoanInfoBean loanInfoBean,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		logger.info("删除客户开始");
		try {
			if (loanInfoBean.getId_prikey() == null) {
				result.setCode(400);
				result.setMsg("主键不能为空");
				return gson.toJson(result);
			}
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			result = loanInfoService.delLoanInfo(loanInfoBean.getId_prikey(), hb_user.getName());
		} catch (Exception e) {
			result.setCode(400);
			result.setMsg("删除失败");
			e.printStackTrace();
		}
		logger.info("删除客户结束" + loanInfoBean.toString());
		return gson.toJson(result);
	}

	/**
	 * 批量删除客户
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("deleteBacthLoanInfo")
	public String deleteBacthLoanInfo(HttpSession session,Integer... ids) {
		CallResult<String> result = new CallResult<>();
		logger.info("批量删除客户开始");
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			if (null != ids && ids.length > 0) {
				List<Integer> list = Arrays.asList(ids);
				if (null != list && list.size() > 0) {
					result = loanInfoService.delBacthLoanInfo(list, hb_user.getName());
				} else {
					result.setCode(400);
					result.setMsg("参数错误");
				}
			} else {
				result.setCode(400);
				result.setMsg("参数错误");
			}
		} catch (Exception e) {
			result.setCode(400);
			result.setMsg("批量删除失败");
			e.printStackTrace();
		}
		logger.info("批量删除客户结束 {} " + ids.length);
		return gson.toJson(result);
	}

	/**
	 * 更新客户信息
	 *
	 * @param loanInfoBean
	 * @return
	 */
	@PostMapping("updateLoanInfo")
	public String updateLoanInfo(LoanInfoBean loanInfoBean,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		logger.info("更新客户信息开始"+loanInfoBean.getMortgages());
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			if (loanInfoBean.getId_prikey() == null) {
				result.setFailResult("主键参数错误");
				return gson.toJson(result);
			}
			result = loanInfoService.updateLoanInfo(loanInfoBean, hb_user.getName());
		} catch (Exception e) {
			result.setCode(400);
			result.setMsg("更新失败");
			e.printStackTrace();
		}
		logger.info("更新客户信息结束 {} " + loanInfoBean.toString());
		return gson.toJson(result);
	}

	/**
	 * 查询客户列表
	 *
	 * @param loanInfoBean
	 * @return
	 */
	@LoginNotRequired
	@RequestMapping("selectLoanInfoList")
	public String selectLoanInfoList(LoanInfoBean loanInfoBean) {
		Map<String, Object> map = null;
		logger.info("查询客户信息列表开始");
		loanInfoService.loanInfoV1PassHandle(loanInfoBean);
		try {
			map = loanInfoBeanDaoService.getPagingData(loanInfoBean);
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
		logger.info("查询客户信息列表结束 {} " + loanInfoBean.toString());
		return gson.toJson(map);
	}

	/**
	 * @author hwl
	 * @description 我的客户列表  分配和组内
	 * @date 2020/4/2
	 */
	@RequestMapping("myLoanInfoPagingData")
	public String myLoanInfoPagingData(LoanInfoBean loanInfoBean, HttpSession session) {
		Map<String, Object> map = null;
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			loanInfoBean.setUserId(hb_user.getName());
			loanInfoBean.setDeptId(hb_user.getDeptId());
			map = loanInfoBeanDaoService.getPagingData(loanInfoBean);
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
	 * @author hwl
	 * @description 我的客户列表 分配和负责的
	 * @date 2020/4/2
	 */
	@RequestMapping("myResponsiblyLoanInfoPagingData")
	public String myResponsiblyLoanInfoPagingData(LoanInfoBean loanInfoBean,HttpSession session) {
		Map<String, Object> map = null;
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			loanInfoBean.setUserId(hb_user.getName());
			loanInfoBean.setManager(hb_user.getName());
			map = loanInfoBeanDaoService.getPagingData(loanInfoBean);
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
	 * 获取客户信息
	 *
	 * @param loanContractNum
	 * @return
	 */
	@PostMapping("selectLoanInfo")
	public String selectLoanInfo(String loanContractNum) {
		CallResult<LoanInfoBean> result = new CallResult<>();
		try {
			if (StringUtils.isNotEmpty(loanContractNum)) {
				result = loanInfoService.selectLoanInfo(loanContractNum);
			} else {
				result.setFailResult("主键参数错误");
			}
		} catch (Exception e) {
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 管理员 分配客户
	 *
	 * @return
	 */
	@PostMapping("distributeLoanInfo")
	public String distributeLoanInfo(@Validated LoanDistributeBean loanDistributeBean,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			result = loanInfoService.distributeLoanInfo(loanDistributeBean, hb_user.getName());
		} catch (Exception e) {
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 管理员批量 分配客户
	 *
	 * @return
	 */
	@PostMapping("distributeLoanInfoBatch")
	public String distributeLoanInfoBatch(@Validated LoanDistributeBean loanDistributeBean,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			result = loanInfoService.distributeLoanInfoBatch(loanDistributeBean, hb_user.getName());
		} catch (Exception e) {
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	* @author hwl
	* @description 组内分配
	* @date 2020/4/29
	*/
	@PostMapping("updateDistributeManager")
	public String updateDistributeManager(@Validated LoanDistributeBean loanDistributeBean,HttpSession session) {
		CallResult<String> callResult = new CallResult<>();
		try {
			if (StringUtils.isBlank(loanDistributeBean.getManager())) {
				callResult.setFailResult("负责人有误");
				return gson.toJson(callResult);
			}
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				callResult.setFailResult("请重新登录");
				return gson.toJson(callResult);
			}
			callResult = loanInfoService.updateDistributeManager(loanDistributeBean, hb_user.getName());
		} catch (Exception e) {
			e.printStackTrace();
			callResult.setFailResult();
		}
		return gson.toJson(callResult);
	}
	
	/**
	 * 批量组内分配
	 * @param manager
	 * @param loanContractNums
	 * @return
	 */
	@PostMapping("updateBatchDistributeManager")
	public String updateBatchDistributeManager(String manager,HttpSession session,String... loanContractNums) {
		CallResult<String> result = new CallResult<>();
		if(isEmpty(manager)){
			result.setFailResult("负责人有误");
			return gson.toJson(result);
		}
		try {

			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			if (null != loanContractNums && loanContractNums.length > 0) {
				List<String> list = Arrays.asList(loanContractNums);
				if (null != list && list.size() > 0) {
					result = loanInfoService.updateBatchDistributeManager(list,manager ,hb_user.getName());
				} else {
					result.setCode(400);
					result.setMsg("参数错误");
				}
			} else {
				result.setCode(400);
				result.setMsg("参数错误");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		
		return gson.toJson(result);
	}

	/**
	 * 整村分配
	 *
	 * @param areaCode
	 * @param number
	 * @param ditributeType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("distributeLoanArea")
	public String distributeLoanArea(String areaCode, String number, String ditributeType,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		if (isEmpty(areaCode)) {
			result.setFailResult("区域代码不能为空");
			gson.toJson(result);
		}
		if (isEmpty(number)) {
			result.setFailResult("分配人号码不能为空");
			gson.toJson(result);
		}
		if (isEmpty(ditributeType)) {
			result.setFailResult("分配类型不能为空");
			gson.toJson(result);
		}
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			result = loanInfoService.distributeLoanArea(areaCode, number, ditributeType, hb_user.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 获取组织所包村的代码数组
	 *
	 * @param organizationNum
	 * @return
	 */
	@PostMapping("getAreaCodeByDistributeArea")
	public String getAreaCodeByDistributeArea(String organizationNum) {
		CallResult<Set<String>> result = new CallResult<>();
		try {
			result = commonService.getAreaCodeByDistributeArea(organizationNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	@PostMapping("distributeVillageList")
	public String distributeVillageList() {
		CallResult<List<String>> result = new CallResult<>();
		List<String> list = new ArrayList<>();
		try {
			List<LoanDistributeOrgVillvgeBean> all = loanDistributeOrgVillvgeBeanDaoService.getAll(null);
			if (all != null && all.size() != 0) {
				for (LoanDistributeOrgVillvgeBean ldov : all) {
					list.add(ldov.getAreaNumber());
				}
			}
			result.setSuccessResult(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 支行分配
	 *
	 * @param bankCode
	 * @param number
	 * @param ditributeType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("distributeLoanBank")
	public String distributeLoanBank(String bankCode, String number, String ditributeType,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		if (isEmpty(bankCode)) {
			result.setFailResult("支行代码不能为空");
			gson.toJson(result);
		}
		if (isEmpty(number)) {
			result.setFailResult("分配人号码不能为空");
			gson.toJson(result);
		}
		if (isEmpty(ditributeType)) {
			result.setFailResult("分配类型不能为空");
			gson.toJson(result);
		}
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			result = loanInfoService.distributeLoanBank(bankCode, number, ditributeType, hb_user.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 获取组织所包支行的代码数组
	 *
	 * @param organizationNum
	 * @return
	 */
	@PostMapping("getBankCodeByDistributeBank")
	public String getBankCodeByDistributeBank(String organizationNum) {
		CallResult<Set<String>> result = new CallResult<>();
		try {
			result = commonService.getBankCodeByDistributeBank(organizationNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 获取共享分配的列表
	 * 
	 * @param loanInfoBean
	 * @return
	 */
	@PostMapping("loanShareList")
	public String loanShareList(LoanInfoBean loanInfoBean) {
		Map<String, Object> map = new HashMap<>();
		try {
			loanInfoBean.setIsShare(YesOrNoEnum.YES_OR_NO_YES.getCode());
			map = loanInfoBeanDaoService.getPagingData(loanInfoBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("msg", "获取失败");
			e.printStackTrace();
		}
		return gson.toJson(map);

	}

	@RequestMapping("importLoanInfo")
	public String importLoanInfo(String filePath,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		if (StringUtils.isBlank(filePath)) {
			result.setFailResult("文件未上传成功");
			return gson.toJson(result);
		}
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			result = loanInfoService.importLoanInfo(V1Pass.pass_decode(filePath), hb_user.getName());
		} catch (Exception e) {
			e.printStackTrace();
			result.setFailResult();
			logger.info("导入异常", e);
		}
		return gson.toJson(result);
	}

	@PostMapping("selectLoanInfoImportError")
	public String selectLoanInfoImportError(LoanInfoImportError li) {
		Map<String, Object> map = new HashMap<>();
		try {
			map = loanInfoImportErrorDaoService.getPagingData(li);
		} catch (Exception e) {
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	/**
	 * 根据id_prikey删除导入失败的客户
	 * 
	 * @param loanAccountNum
	 * @return
	 */
	@PostMapping("delLoanInfoImportError")
	public String delLoanInfoImportError(String loanAccountNum) {
		CallResult<String> result = new CallResult<>();
		try {
			LoanInfoImportError lie = new LoanInfoImportError();
			lie.setLoanAccountNum(loanAccountNum);
			List<LoanInfoImportError> list = loanInfoImportErrorDaoService.getAll(lie);
			if (null != list && list.size() != 0) {
				loanInfoImportErrorDaoService.remove(lie);
			}
			result.setSuccessResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 将客户共享
	 * 
	 * @param id_prikey
	 * @return
	 */
	/*@PostMapping("updateLoanInfoShare")
	public String updateLoanInfoShare(Integer id_prikey) {
		CallResult<String> result = new CallResult<>();
		try {
			result = loanInfoService.updateLoanInfoShare(id_prikey, hb_user.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}*/

	/**
	 * 批量将客户共享
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("updateBatchLoanInfoShare")
	public String updateBatchLoanInfoShare(HttpSession session,Integer... ids) {
		CallResult<String> result = new CallResult<>();
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}

			if (null != ids && ids.length > 0) {
				List<Integer> list = Arrays.asList(ids);
				if (null != list && list.size() > 0) {
					result = loanInfoService.updateBatchLoanInfoShare(list, hb_user.getName());
				} else {
					result.setCode(400);
					result.setMsg("参数错误");
				}
			} else {
				result.setCode(400);
				result.setMsg("参数错误");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 将客户取消共享
	 *
	 * @param id_prikey
	 * @return
	 */
	/*@PostMapping("updateLoanInfoNoShare")
	public String updateLoanInfoNoShare(Integer id_prikey) {
		CallResult<String> result = new CallResult<>();
		try {
			result = loanInfoService.updateLoanInfoNoShare(id_prikey, hb_user.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
*/
	/**
	 * 批量将客户取消共享
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("updateBatchLoanInfoNoShare")
	public String updateBatchLoanInfoNoShare(HttpSession session,Integer... ids) {
		CallResult<String> result = new CallResult<>();
		try {

			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}

			if (null != ids && ids.length > 0) {
				List<Integer> list = Arrays.asList(ids);
				if (null != list && list.size() > 0) {
					result = loanInfoService.updateBatchLoanInfoNoShare(list, hb_user.getName());
				} else {
					result.setCode(400);
					result.setMsg("参数错误");
				}
			} else {
				result.setCode(400);
				result.setMsg("参数错误");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	@PostMapping("loanClaim")
	public String loanClaim(String loanContractNum,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		try {
			HB_User hb_user = getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}

			if(StringUtils.isBlank(loanContractNum)){
				result.setFailResult("参数有误");
				return gson.toJson(result);
			}


			if(hb_user == null || StringUtils.isBlank(hb_user.getName())){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			result = loanInfoService.loanClaim(loanContractNum, hb_user.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * loan修改申请
	 * 
	 * @param files
	 * @return
	 */
	@PostMapping("loanUpdateApply")
	public String loanUpdateApply(LoanInfoBean lib,String files,Integer businessId,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		HB_User hb_user = getHbUserBySession(session);;
		if(ObjectUtils.isEmpty(hb_user)){
			result.setFailResult("请重新登录");
			return gson.toJson(result);
		}

		if(lib.getId_prikey()==null){
			result.setFailResult("主键不能为空");
			return gson.toJson(result);
		}
		if(isEmpty(lib.getLoanContractNum())){
			result.setFailResult("贷款合同号不能为空");
			return gson.toJson(result);
		}
		if(isEmpty(lib.getRemark())){
			result.setFailResult("修改理由不能为空");
			return gson.toJson(result);
		}
		//首贷金额验证
		if(lib.getEarliestLoanAmount()!=null){
			int i = lib.getEarliestLoanAmount().compareTo(BigDecimal.ZERO);
			if (i==-1){
				result.setFailResult("首贷金额不能小于零");
				return gson.toJson(result);
			}
		}

		//结欠本金验证
		if(lib.getLeftCapitalAmount()!=null){
			int i = lib.getLeftCapitalAmount().compareTo(BigDecimal.ZERO);
			if (i<0){
				result.setFailResult("结欠本金不能小于零");
				return gson.toJson(result);
			}
		}

		//结欠利息验证
		if(lib.getLeftInterestAmount()!=null){
			int i = lib.getLeftInterestAmount().compareTo(BigDecimal.ZERO);
			if (i<0){
				result.setFailResult("结欠利息不能小于零");
				return gson.toJson(result);
			}
		}

		//原利率验证
		if(lib.getLoanOldMonthRate()!=null){
			int i = lib.getLoanOldMonthRate().compareTo(BigDecimal.ZERO);
			if (i<=0){
				result.setFailResult("原利率不能小于或等于零");
				return gson.toJson(result);
			}
		}

		//执行利率验证
		if(lib.getLoanNewMonthRate()!=null){
			int i = lib.getLoanNewMonthRate().compareTo(BigDecimal.ZERO);
			if (i<=0){
				result.setFailResult("执行利率不能小于或等于零");
				return gson.toJson(result);
			}
		}
		try {
			result = loanInfoService.loanUpdateApply2(lib,files,businessId,hb_user.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult(e.getMessage());
			e.printStackTrace();
		}

		return gson.toJson(result);
	}

	/**
	 * 通过主键获取单条修改
	 *
	 * @param id_prikey
	 * @return
	 */
	@PostMapping("loanUpdateApplyFlagById")
	public String loanUpdateApplyById(Integer id_prikey) {
		CallResult<String> result = new CallResult<>();
		if (id_prikey == null) {
			result.setFailResult("主键不能为空");
			return gson.toJson(result);
		}
		try {
			ProcessBusinessBean processBusinessBean = processBusinessBeanDaoService.getById(id_prikey);
			if (processBusinessBean == null) {
				result.setFailResult("主键有误");
				return gson.toJson(result);
			}

			LoanInfoUpdateContent byId = loanInfoUpdateContentDaoService.getById(Integer.valueOf(processBusinessBean.getBusinessId()));
			if (byId == null) {
				result.setFailResult("参数有误");
				return gson.toJson(result);
			}

			if (isEmpty(byId.getOldContent())) {
				result.setData("1");
			}else{
				result.setData("2");
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}


	/**
	 * 通过主键获取单条修改
	 * 
	 * @param id_prikey
	 * @return
	 */
	@PostMapping("loanUpdateApplyById")
	public String loanUpdateApplyById(Integer id_prikey,String flag) {
		CallResult<Map<String,Object>> result = new CallResult<>();
		Map<String,Object>map = new HashMap<>();
		if (id_prikey == null) {
			result.setFailResult("主键不能为空");
			return gson.toJson(result);
		}
		try {
			ProcessBusinessBean processBusinessBean = processBusinessBeanDaoService.getById(id_prikey);
			if(processBusinessBean==null){
				result.setFailResult("主键有误");
				return gson.toJson(result);
			}

			LoanInfoUpdateContent byId = loanInfoUpdateContentDaoService.getById(Integer.valueOf(processBusinessBean.getBusinessId()));
			if (byId == null) {
				result.setFailResult("参数有误");
				return gson.toJson(result);
			}

			//获取修改内容
			LoanInfoBean loanInfoBean = null;
//			if(isEmpty(byId.getOldContent())){
			if("2".equals(flag)){
				loanInfoBean = gson.fromJson(byId.getOldContent(), LoanInfoBean.class);
			}else{
				loanInfoBean = gson.fromJson(byId.getUpdateContent(),LoanInfoBean.class);
				loanInfoService.DictCodeToName(loanInfoBean);
			}

			List<LoanGuarantee> mateList = new Gson().fromJson(loanInfoBean.getMates(),
					new TypeToken<List<LoanGuarantee>>() {
					}.getType());
			loanInfoBean.setMateList(mateList);
			List<LoanGuarantee> guaranteeList = new Gson().fromJson(loanInfoBean.getGuarantees(),
					new TypeToken<List<LoanGuarantee>>() {
					}.getType());
			loanInfoBean.setGuaranteeList(guaranteeList);
			List<LoanMortgage> mortgageList = new Gson().fromJson(loanInfoBean.getMortgages(),
					new TypeToken<List<LoanMortgage>>() {
					}.getType());
			loanInfoBean.setMortgageList(mortgageList);
//			loanInfoBean.setIdNum(V1Pass.pass_decode(loanInfoBean.getIdNum()));
//			loanInfoBean.setName(V1Pass.pass_decode(loanInfoBean.getName()));
//			loanInfoBean.setMobile(V1Pass.pass_decode(loanInfoBean.getMobile()));
			map.put("loanInfoBean", loanInfoBean);
			
			CallResult<ProcessBusinessBean> processInfoResult = processHandleService.productProcessInfo(id_prikey, ProcessBusinessEnum.PROCESS_BUSINESS_LOANINFO_UPDATE.getCode());
			if(processInfoResult.isExec()){
				map.put("processInfo", processInfoResult.getData());
				 ProcessBusinessBean pbb = processInfoResult.getData();
				 List<ProcessLogBean> logList = pbb.getLogList();
				Integer i = 0;
				 for(ProcessLogBean l:logList){
					 if (l.getNodeId()==1 && l.getId_prikey()>i){
					 	i = l.getId_prikey();
					 }
				 }
				for(ProcessLogBean l:logList){
					if (l.getId_prikey()==i){
						map.put("file",l.getProcessFileList());
					}
				}
			}
			result.setSuccessResult(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	@PostMapping("loanUpdateAudit")
	public String loanUpdateAudit(LoanInfoUpdateContent liuc,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		HB_User hb_user = getHbUserBySession(session);;
		if(ObjectUtils.isEmpty(hb_user)){
			result.setFailResult("请重新登录");
			return gson.toJson(result);
		}
		if (isEmpty(liuc.getStatus())) {
			result.setFailResult("状态不能为空");
			return gson.toJson(result);
		}
		if (liuc.getId_prikey()==null) {
			result.setFailResult("主键不能为空");
			return gson.toJson(result);
		}
		if (isEmpty(liuc.getLoanContractNum())) {
			result.setFailResult("合同号不能为空");
			return gson.toJson(result);
		}
		try {
			result = loanInfoService.loanUpdateAudit2(liuc,hb_user.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}


	/**
	 * @author hwl
	 * @description 我的客户列表  分配和组内
	 * @date 2020/4/2
	 */
	@RequestMapping("myShareLoanInfoPagingData")
	public String myShareLoanInfoPagingData(LoanInfoBean loanInfoBean,HttpSession session) {
		Map<String, Object> map = null;
		try {
			HB_User hb_user = getHbUserBySession(session);
			if(ObjectUtils.isEmpty(hb_user)){
				map.put("code", 400);
				map.put("msg", "请重新登录");
				return gson.toJson(map);
			}
			loanInfoService.loanInfoV1PassHandle(loanInfoBean);
			loanInfoBean.setUserId(hb_user.getName());
			loanInfoBean.setDeptId(hb_user.getDeptId());
			loanInfoBean.setOperationType(DistributeOperationTypeEnum.DISTRIBUTE_SHARE.getCode());
			map = loanInfoBeanDaoService.getPageShareDate(loanInfoBean);
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
	 * 获取修改后客户信息列表
	 * @return
	 */
	@PostMapping("alterLoanInfoList")
	public String alterLoanInfoList(UpdateLogBean updateLogBean) {
		Map<String, Object> map = null;
		try {
			map = loanInfoService.getAlterLoanInfoList(updateLogBean);
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

	@RequestMapping("exprotAlterLoanInfoList")
	public void exprotAlterLoanInfoList(HttpServletResponse response) throws Exception {
		loanInfoService.exprotAlterLoanInfoList(response);
	}

	public HB_User getHbUserBySession(HttpSession session){
		return  (HB_User) session.getAttribute("hb_user");
	}

}
