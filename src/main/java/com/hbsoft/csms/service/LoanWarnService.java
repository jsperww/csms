package com.hbsoft.csms.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.hb.util.DateUtil;
import com.hb.util.V1Pass;
import com.hbsoft.common.enumtype.YesOrNoEnum;
import com.hbsoft.csms.bean.DdsendmessageBean;
import com.hbsoft.csms.dao.service.*;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import com.hbsoft.dingding.util.DingDingUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.bean.CallResult;
import com.hbsoft.common.enumtype.LoanWarnEnum;
import com.hbsoft.csms.bean.LoanDistributeBean;
import com.hbsoft.csms.bean.LoanInfoBean;
import com.hbsoft.csms.bean.LoanWarnInfo;

@Service
public class LoanWarnService {

	@Autowired
	private LoanWarnInfoDaoService loanWarnInfoDaoService;
	@Autowired
	private LoanInfoBeanDaoService loanInfoBeanDaoService;
	@Autowired
	private LoanDistributeBeanDaoService loanDistributeBeanDaoService;
	@Autowired
	private DingDingUserInfoDaoService dingDingUserInfoDaoService;
	@Autowired
	private DingDingUtil dingDingUtil;
	@Autowired
	private DdsendmessageBeanDaoService ddsendmessageBeanDaoService;

	@Transactional
	public CallResult loanEndDateWarn() throws Exception {
		CallResult<String> result = new CallResult<>();
		LoanInfoBean loanInfoBean = new LoanInfoBean();
		LoanWarnInfo loanWarnInfo = new LoanWarnInfo();
		LoanDistributeBean LoanDistribute = new LoanDistributeBean();
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);
		date = calendar.getTime();
		loanInfoBean.setLoanEndDate(date);
		loanInfoBean.setDistributeFlag("1");
		List<LoanInfoBean> list = loanInfoBeanDaoService.getAll(loanInfoBean);
		for (LoanInfoBean lib : list) {
			LoanDistribute.setLoanContractNum(lib.getLoanContractNum());
			LoanDistributeBean ldb = loanDistributeBeanDaoService.getByField(LoanDistribute);
			loanWarnInfo.setWarnTarget(ldb.getNumber());
			loanWarnInfo.setWarnTargetType(Integer.valueOf(ldb.getNumberType()));
			loanWarnInfo.setWarnWay(Integer.valueOf(LoanWarnEnum.LOAN_WARN_ENDDATE_ENUM.getCode()));
			loanWarnInfo.setWarnContent("姓名："+lib.getName()+"身份证号："+lib.getIdNum()+"，贷款到期了");
			loanWarnInfoDaoService.add(loanWarnInfo);
		}
		result.setSuccessResult();
		return result;
	}

	@Scheduled(cron = "0 0 8 * * ? ")
	@Transactional
	public void sendWarnMessage() throws Exception{
			LoanWarnInfo loanWarnInfo = new LoanWarnInfo();
			loanWarnInfo.setWarnDateStr(DateUtil.formatDate(new Date(),"yyyy-MM-dd"));
			List<LoanWarnInfo> all = loanWarnInfoDaoService.findUnSendMsg(loanWarnInfo);
			if (all != null) {
				for (LoanWarnInfo lwi : all) {
					DingDingUserInfo user = new DingDingUserInfo();
					user.setJobnumber(lwi.getWarnTarget());
					List<DingDingUserInfo> list = dingDingUserInfoDaoService.getAll(user);
					StringBuffer userList = new StringBuffer();
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							if (i == list.size() - 1) {
								userList.append(list.get(i).getUserid());
							} else {
								userList.append(list.get(i).getUserid() + ",");
							}
						}
						if(StringUtils.isNotBlank(lwi.getName())){
							lwi.setName(V1Pass.pass_decode(lwi.getName()));
						}
						if (LoanWarnEnum.LOAN_WARN_ENDDATE_ENUM.getCode().equals(lwi.getWarnWay().toString())) {

						} else if (LoanWarnEnum.LOAN_WARN_VISIT_ENUM.getCode().equals(lwi.getWarnWay().toString())) {
							String content = "客户姓名：" + lwi.getName() + "\n" + "提醒内容：" + lwi.getWarnContent();
							CallResult<String> callResult =  dingDingUtil.sendDingdingMsgByLink(userList.toString(),LoanWarnEnum.LOAN_WARN_VISIT_ENUM.getName(),content);
							if(callResult.isExec()){
								LoanWarnInfo record = new LoanWarnInfo();
								record.setId_prikey(lwi.getId_prikey());
								record.setSendMsgFlag(YesOrNoEnum.YES_OR_NO_YES.getCode());
								loanWarnInfoDaoService.set(record);
								DdsendmessageBean message = new DdsendmessageBean();
								message.setAccepter(userList.toString());
								message.setBusinessId(lwi.getId_prikey().toString());
								message.setBusinessType(LoanWarnEnum.LOAN_WARN_VISIT_ENUM.getCode());
								message.setCreateOn(new Date());
								message.setContent(content);
								ddsendmessageBeanDaoService.add(message);
							}
						}

					}
				}
			}
	}
}
