package com.hbsoft.csms.controller;

import com.hb.bean.CallResult;
import com.hb.controller.ABaseController;
import com.hb.util.StringUtil;
import com.hbsoft.common.constant.Constant;
import com.hbsoft.csms.bean.ProcessBean;
import com.hbsoft.csms.bean.ProcessDetailBean;
import com.hbsoft.csms.dao.service.ProcessBeanDaoService;
import com.hbsoft.csms.dao.service.ProcessDetailBeanDaoService;
import com.hbsoft.csms.service.CommonService;
import com.hbsoft.csms.service.ProcessInfoService;
import com.hbsoft.csms.vo.DictVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class ProcessHandleController extends ABaseController {



}
