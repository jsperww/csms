package com.hbsoft.csms.controller;

import com.hb.bean.CallResult;
import com.hb.bean.hbcmsql.HB_User;
import com.hb.controller.ABaseController;
import com.hb.util.StringUtil;
import com.hbsoft.common.constant.Constant;
import com.hbsoft.csms.bean.ProcessBean;
import com.hbsoft.csms.bean.ProcessDetailBean;
import com.hbsoft.csms.dao.service.ProcessBeanDaoService;
import com.hbsoft.csms.dao.service.ProcessDetailBeanDaoService;
import com.hbsoft.csms.service.CommonService;
import com.hbsoft.csms.service.ProcessInfoService;
import com.hbsoft.csms.service.SessionService;
import com.hbsoft.csms.vo.DictVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class ProcessInfoController extends ABaseController {

	@Autowired
	CommonService commonService;
	@Autowired
	ProcessInfoService processService;
	@Autowired
	ProcessBeanDaoService processBeanDaoService;
	@Autowired
	ProcessDetailBeanDaoService processDetailBeanDaoService;
	@Autowired
	SessionService sessionService;

	/**
	 * 添加流程
	 */
	@PostMapping("addProcess")
	public String addProcess(@Validated ProcessBean pb, HttpSession session) {
		CallResult<String> result = new CallResult<>();
		String maxCode = commonService.getMaxCode();
		if (isEmpty(maxCode)) {
			maxCode = Constant.STARTCODE;
		} else {
			maxCode = StringUtil.getNextCode(3, maxCode);
		}
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			Date date = new Date();
			pb.setCreateBy(hb_user.getName());
			pb.setCreateOn(date);
			pb.setUpdateBy(hb_user.getName());
			pb.setUpdateOn(date);
			pb.setCode(maxCode);
			processBeanDaoService.add(pb);
			result.setCode(0);
			result.setMsg("操作成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setCode(400);
			result.setMsg("操作失败");
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 删除流程及流程下的所有节点
	 * @param id_prikey
	 * @return
	 */
	@PostMapping("delProcessById")
	public String delProcessById(Integer id_prikey) {
		CallResult<String> result = new CallResult<>();
		if (id_prikey == null) {
			result.setCode(400);
			result.setMsg("主键不能为空");
			return gson.toJson(result);
		}
		try {
			result = processService.delProcess(id_prikey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setCode(400);
			result.setMsg("操作失败");
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 修改流程
	 * @return
	 */
	@PostMapping("updateProcessById")
	public String updateProcessById(@Validated ProcessBean pb,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		if (pb.getId_prikey() == null) {
			result.setCode(400);
			result.setMsg("主键不能为空");
			return gson.toJson(result);
		}
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);;
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			ProcessBean byId = processBeanDaoService.getById(pb.getId_prikey());
			if(byId==null){
				result.setCode(400);
				result.setMsg("主键参数有误");
				return gson.toJson(result);
			}
			Date date = new Date();
			pb.setCode(byId.getCode());
			pb.setUpdateBy(hb_user.getName());
			pb.setUpdateOn(date);
			processBeanDaoService.set(pb);
			result.setCode(0);
			result.setMsg("操作成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setCode(400);
			result.setMsg("操作失败");
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 获取单条流程
	 * @param id_prikey
	 * @return
	 */
	@PostMapping("getProcessById")
	public String getProcessById(Integer id_prikey) {
		CallResult<ProcessBean> result = new CallResult<>();
		if (id_prikey == null) {
			result.setCode(400);
			result.setMsg("主键不能为空");
			return gson.toJson(result);
		}
		try {
			ProcessBean byId = processBeanDaoService.getById(id_prikey);
			result.setCode(0);
			result.setMsg("操作成功");
			result.setData(byId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setCode(400);
			result.setMsg("操作失败");
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 分页获取流程
	 * @param pb
	 * @return
	 */
	@PostMapping("getProcessPage")
	public String getProcessPage(ProcessBean pb) {
		Map<String, Object> map = new HashMap<>();
		try {
			map = processBeanDaoService.getPagingData(pb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	/**
	 * 添加刘成下的节点
	 * @param pdb
	 * @return
	 */
	@PostMapping("addProcessDetail")
	public String addProcessDetail(@Validated ProcessDetailBean pdb,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			Date date = new Date();
			pdb.setCreateBy(hb_user.getName());
			pdb.setCreateOn(date);
			pdb.setUpdateBy(hb_user.getName());
			pdb.setUpdateOn(date);
			Integer maxCode = commonService.getMaxCode2(pdb.getNodeType());
			if(ObjectUtils.isEmpty(maxCode)){
				maxCode=10;
			}else{
				maxCode =maxCode+10;
			}
			pdb.setNodeId(maxCode);
			processDetailBeanDaoService.add(pdb);
			result.setCode(0);
			result.setMsg("操作成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setCode(400);
			result.setMsg("操作失败");
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 删除节点
	 * @param id_prikey
	 * @return
	 */
	@PostMapping("delProcessDetailById")
	public String delProcessDetailById(Integer id_prikey) {
		CallResult<String> result = new CallResult<>();
		if (id_prikey == null) {
			result.setCode(400);
			result.setMsg("主键不能为空");
			return gson.toJson(result);
		}
		try {
			processDetailBeanDaoService.removeOne(id_prikey);
			result.setCode(0);
			result.setMsg("操作成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setCode(400);
			result.setMsg("操作失败");
			e.printStackTrace();
		}
		return gson.toJson(result);
	}

	/**
	 * 修改节点
	 * @param pdb
	 * @return
	 */
	@PostMapping("updateProcessDetailById")
	public String updateProcessDetailById(ProcessDetailBean pdb,HttpSession session) {
		CallResult<String> result = new CallResult<>();
		if (pdb.getId_prikey() == null) {
			result.setCode(400);
			result.setMsg("主键不能为空");
			return gson.toJson(result);
		}
		if(isEmpty(pdb.getNodeName())){
			result.setCode(400);
			result.setMsg("节点名称不能为空");
			return gson.toJson(result);
		}
		try {
			HB_User hb_user = sessionService.getHbUserBySession(session);
			if(ObjectUtils.isEmpty(hb_user)){
				result.setFailResult("请重新登录");
				return gson.toJson(result);
			}
			ProcessDetailBean byId = processDetailBeanDaoService.getById(pdb.getId_prikey());
			if(byId==null){
				result.setCode(400);
				result.setMsg("主键参数有误");
				return gson.toJson(result);
			}
			Date date = new Date();
			pdb.setNodeId(byId.getNodeId());
			pdb.setNodeType(byId.getNodeType());
			pdb.setUpdateBy(hb_user.getName());
			pdb.setUpdateOn(date);
			processDetailBeanDaoService.set(pdb);
			result.setCode(0);
			result.setMsg("操作成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setCode(400);
			result.setMsg("操作失败");
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
	
	/**
	 * 获取单条节点
	 * @param id_prikey
	 * @return
	 */
	@PostMapping("getProcessDetailById")
	public String getProcessDetailById(Integer id_prikey) {
		CallResult<ProcessDetailBean> result = new CallResult<>();
		if (id_prikey == null) {
			result.setCode(400);
			result.setMsg("主键不能为空");
			return gson.toJson(result);
		}
		try {
			ProcessDetailBean byId = processDetailBeanDaoService.getById(id_prikey);
			result.setCode(0);
			result.setMsg("操作成功");
			result.setData(byId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setCode(400);
			result.setMsg("操作失败");
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
	
	/**
	 * 分页获取节点
	 * @param pdb
	 * @return
	 */
	@PostMapping("getProcessDetailPage")
	public String getProcessDetailPage(ProcessDetailBean pdb) {
		Map<String,Object> map = new HashMap<>();
		try {
			map = processDetailBeanDaoService.getPagingData(pdb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("code", 400);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return gson.toJson(map);
	}


    @PostMapping("getAllProcess")
    public String getAllProcess(){
	    CallResult<List<DictVo>> result= new CallResult<>();
        try {
            List<ProcessBean> proList = processBeanDaoService.getAll(null);
            if(proList != null){
                List<DictVo> dictList = new ArrayList<>();
                for(ProcessBean p : proList){
                    DictVo vo = new DictVo();
                    BeanUtils.copyProperties(p,vo);
                    dictList.add(vo);
                }
                result.setCode(0);
                result.setMsg("操作成功");
                result.setData(dictList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(400);
            result.setMsg("操作失败");
        }
        return gson.toJson(result);
    }

	/**
	 * 获取流程下所有节点
	 * @param processDetailBean
	 * @return
	 */
	@RequestMapping("getProcessByAttr")
    public String getProcessByAttr(ProcessDetailBean processDetailBean) {
		CallResult<List<ProcessDetailBean>> result = new CallResult<>();
		try {
			if (null != processDetailBean.getNodeType() && !"".equals(processDetailBean.getNodeType())) {
				List<ProcessDetailBean> processDetailBeans = processDetailBeanDaoService.getAll(processDetailBean);
				result.setSuccessResult(processDetailBeans);
			}else {
				result.setFailResult("参数错误");
			}
		}catch (Exception e) {
			result.setFailResult();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
}
