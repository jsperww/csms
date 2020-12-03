package com.hbsoft.dingding.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hb.bean.CallResult;
import com.hb.util.BasePassUtil;
import com.hb.util.GsonUtil;
import com.hb.util.HttpUtil;
import com.hbsoft.common.enumtype.DingDingUseWayEnum;
import com.hbsoft.common.enumtype.LoanBankEnum;
import com.hbsoft.csms.bean.DDDeptAndUser;
import com.hbsoft.csms.vo.DdOALinkVo;
import com.hbsoft.csms.vo.DdOAMsgVo;
import com.hbsoft.csms.vo.DdSendVo;
import com.hbsoft.dingding.bean.DDUserInfo;
import com.hbsoft.dingding.bean.DingDingDeptInfo;
import com.hbsoft.dingding.bean.DingDingUserInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class DingDingUtil {

	@Value("${hb.dingDing.corpId}")
	String corpId;
	@Value("${hb.dingDing.agentId}")
	String agentId;
	@Value("${hb.dingDing.appKey}")
	String appKey;
	@Value("${hb.dingDing.appSecret}")
	String appSecret;
	@Value("${hb.dingMessage.picUrl:}")
	String picUrl;
	@Value("${hb.dingMessage.messageUrl:}")
	String messageUrl;
	@Value(("${hb.dingDing.key}"))
	private String key;
	@Value(("${hb.dingDing.ddUrl}"))
	private String ddUrl;
	@Value("${hb.dingDing.type:1}")
	private String type;
	@Value("${hb.loanBank}")
	private String loanBank;



	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static String getTokenUrl = "https://oapi.dingtalk.com/gettoken";
	
	private static String getUserUrl = "https://oapi.dingtalk.com/user/getuserinfo";
	
	private static String getTicketUrl = "https://oapi.dingtalk.com/get_jsapi_ticket";

	private String oaMessageURL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token=";

	public  String getAccessToken() {
		if(DingDingUseWayEnum.DING_DING_USE_WAY_DINGDING.getCode().equals(type)){
			StringBuffer sb = new StringBuffer();
			sb.append(getTokenUrl + "?");
			sb.append("appkey=" + appKey);
			sb.append("&appsecret=" + appSecret);
			String sendGet = HttpUtil.sendGet(sb.toString());
			logger.info("获取token 入参:{},出参：{}",sb.toString(),sendGet);
			Map<String, Object> map = new Gson().fromJson(sendGet, Map.class);
			String access_token = (String) map.get("access_token");
			System.out.println(access_token + "access_token");
			return access_token;
		}else if(DingDingUseWayEnum.DING_DING_USE_WAY_INTERFACE.getCode().equals(type)){
			if(LoanBankEnum.LOAN_BANK_463_ENUM.getCode().equals(loanBank)){
				return getAccessTokenZz();
			}
		}
		return null;
	}

	public  String getAccessTokenZz() {
		Map<String, Object> map = new HashMap<>();
		map.put("function","getDingToken(token,timestamp)");
		String sign = getTokenTZz();
		map.put("token",sign);
		long timestamp = System.currentTimeMillis()/1000;
		map.put("timestamp",timestamp);
		String param = null;
		param = GsonUtil.gson.toJson(map);
		logger.info("获取token传入参数： {},url：{}",param,ddUrl);
		String str = HttpUtil.sendJSONPost(this.ddUrl,param);
		logger.info("获取token结果 {}",str);
		Map<String, Object> resultMap = GsonUtil.gson.fromJson(str,Map.class);
		if (null != str && !"".equals(str)) {
			if ("获取成功".equals(resultMap.get("ok"))) {
				return String.valueOf(resultMap.get("token"));
			}
		}
		return null;
	}


	public String getTicket(String access_token) {
		StringBuffer sb = new StringBuffer();
		sb.append(getTicketUrl + "?");
		sb.append("access_token=" + access_token);
		String sendGet = HttpUtil.sendGet(sb.toString());
		Map<String, Object> map = new Gson().fromJson(sendGet, Map.class);
		String ticket = (String) map.get("ticket");
		System.out.println(map);
		return ticket;
	}
	
	/**
	 * 计算签名
	 * @param ticket
	 * @param nonceStr
	 * @param timeStamp
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String sign(String ticket, String nonceStr, long timeStamp, String url) throws Exception {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
				+ "&url=" + url;
		MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
		sha1.reset();
		sha1.update(plain.getBytes("UTF-8"));
		return bytesToHex(sha1.digest());
	}

	/**
	 * 随机字符串
	 * @param length
	 * @return
	 */
	public String nonce_str(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * byte数组转16进制
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() < 2) {
				sb.append(0);
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * 获取页面js初始化所有参数
	 * 
	 * @date 2018年6月28日上午11:21:12
	 * @param
	 *
	 * @return
	 */

	public String getJSSDKSign(String url) throws Exception {
		if(DingDingUseWayEnum.DING_DING_USE_WAY_DINGDING.getCode().equals(type)){
			String access_token = getAccessToken();
			String ticket = getTicket(access_token);
			String nonceStr = nonce_str(10);
			long timeStamp = System.currentTimeMillis();
			String signature = sign(ticket, nonceStr, timeStamp, url);
			Map<String, Object> configValue = new HashMap<>();
			configValue.put("signature", signature);
			configValue.put("nonceStr", nonceStr);
			configValue.put("timeStamp", timeStamp);
			configValue.put("corpId", corpId);
			configValue.put("agentId", agentId);
			System.out.println("前台传入的url {} " + url);
			System.out.println("前端js初始化参数 {} " + configValue.toString());
			return new Gson().toJson(configValue);
		}else if(DingDingUseWayEnum.DING_DING_USE_WAY_DINGDING.getCode().equals(type)){
			if(LoanBankEnum.LOAN_BANK_463_ENUM.getCode().equals(loanBank)){
				return getJSSDKSignZz(url);
			}
		}
		return  null;
	}

	public String getJSSDKSignZz(String url) {
		logger.info("前台传入参数{}" + url);
		Map<String, Object> configValue = new HashMap<>();
		String param = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("function","getDingConfig(url,token,timestamp)");
		map.put("url",url);
		String token = getTokenTZz();
		map.put("token",token);
		long timeStamp = System.currentTimeMillis()/1000;
		map.put("timestamp",timeStamp);
		param = GsonUtil.gson.toJson(map);
		String str =HttpUtil.sendJSONPost(this.ddUrl,param);
		Map<String, Object> resultMap = GsonUtil.gson.fromJson(str, Map.class);
		if (null != resultMap) {
			if ("获取成功".equals(String.valueOf(resultMap.get("ok")))) {
				configValue.put("signature", resultMap.get("signature"));
				configValue.put("nonceStr", resultMap.get("nonceStr"));
				configValue.put("timeStamp", resultMap.get("timeStamp"));
				configValue.put("corpId", resultMap.get("corpId"));
				configValue.put("agentId", resultMap.get("agentId"));
			}
		}
		logger.info("钉钉鉴权Zz {}" +resultMap.toString());
		return GsonUtil.gson.toJson(configValue);
	}

	public String getTokenTZz() {
		String sign = BasePassUtil.md5(this.key + System.currentTimeMillis()/1000);
		return sign.toUpperCase();
	}


	/**
	 * 获取钉钉id
	 * 
	 * @date 2018年6月28日上午11:20:22
	 * @param
	 * @return
	 */
	public  CallResult<String> getDingDingUserId(String code) {
		CallResult<String> result = new CallResult<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_token", getAccessToken());
		params.put("code", code);
		String u = HttpUtil.sendGet("https://oapi.dingtalk.com/user/getuserinfo", params);
		DDUserInfo userInfo = new Gson().fromJson(u, DDUserInfo.class);
		if (userInfo.getErrcode() == 0) {
			result.setCode(0);
			result.setMsg(userInfo.getErrmsg());
			result.setData(userInfo.getUserid());
		} else {
			result.setCode(400);
			result.setMsg(userInfo.getErrmsg());
		}
		System.out.println("钉钉用户id" + userInfo.getUserid());
		return result;
	}

	
	 /**
     * 获取所有部门
     * @return
     */
    public CallResult<List<DingDingDeptInfo>> getDepts() {
        CallResult<List<DingDingDeptInfo>> result = new CallResult<>();
		String accessToken = getAccessToken();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("access_token", accessToken);
        String json = HttpUtil.sendGet("https://oapi.dingtalk.com/department/list", params);
		DDDeptAndUser ddDeptAndUser = new Gson().fromJson(json, new TypeToken<DDDeptAndUser>() {
		}.getType());
		List<DingDingDeptInfo> list = ddDeptAndUser.getDepartment();

		for (int i=0; i<list.size(); i++) {
			if (list.get(i).getCreateDeptGroup()) {
				list.get(i).setCreateDeptGroupIn(1);
			}else {
				list.get(i).setCreateDeptGroupIn(0);
			}
			if (list.get(i).getAutoAddUser()) {
				list.get(i).setAutoAddUserIn(1);
			}else {
				list.get(i).setAutoAddUserIn(0);
			}
		}
		result.setCode(ddDeptAndUser.getErrcode());
		result.setMsg(ddDeptAndUser.getErrmsg());
		result.setData(ddDeptAndUser.getDepartment());
        return result;
    }


	/**
	 * 获取部门下所有人员姓名和id
	 * 
	 * @date 2018年6月28日下午7:11:43
	 * @param deptId
	 * @return
	 */
	public  CallResult<List<DingDingUserInfo>> getUsers(Integer deptId) {
		CallResult<List<DingDingUserInfo>> result = new CallResult<List<DingDingUserInfo>>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_token", getAccessToken());
		params.put("department_id", deptId);
		String json = HttpUtil.sendGet("https://oapi.dingtalk.com/user/list", params);
		DDDeptAndUser ddDeptAndUser = new Gson().fromJson(json, new TypeToken<DDDeptAndUser>() {
		}.getType());
		List<DingDingUserInfo> list = ddDeptAndUser.getUserlist();
		for (int i=0; i<list.size(); i++) {
			if (list.get(i).getIsAdmin()) {
				list.get(i).setIsAdminIn(1);
			}else {
				list.get(i).setIsAdminIn(0);
			}
			if (list.get(i).getIsBoss()) {
				list.get(i).setIsBossIn(1);
			}else {
				list.get(i).setIsBossIn(0);
			}
			if (list.get(i).getIsHide()) {
				list.get(i).setIsHideIn(1);
			}else {
				list.get(i).setIsHideIn(0);
			}
			if (list.get(i).getIsLeader()) {
				list.get(i).setIsLeaderIn(1);
			}else {
				list.get(i).setIsLeaderIn(0);
			}
			if (list.get(i).getActive()) {
				list.get(i).setActiveIn(1);
			}else {
				list.get(i).setActiveIn(0);
			}
		}
		result.setCode(ddDeptAndUser.getErrcode());
		result.setMsg(ddDeptAndUser.getErrmsg());
		result.setData(ddDeptAndUser.getUserlist());
		return result;
	}


	public CallResult<String> sendDingdingMsgByLink(String userId,String title,String content){
		DdOALinkVo linkVo = new DdOALinkVo();
		DdOAMsgVo msgVo = new DdOAMsgVo();
		DdSendVo sendVo = new DdSendVo();
		linkVo.setPicUrl(picUrl);
		linkVo.setMessageUrl(messageUrl);
		linkVo.setTitle(title);
		linkVo.setText(content);
		msgVo.setLink(linkVo);
		msgVo.setMsgtype("link");
		sendVo.setMsg(msgVo);
		sendVo.setAgent_id(Integer.valueOf(agentId));
		sendVo.setUserid_list(userId);
		String sendMsg = new Gson().toJson(sendVo);
		CallResult result = sendDingdingMsg(sendMsg);
		return result;
	}




	public CallResult<String> sendDingdingMsg(String msg){
		CallResult result = new CallResult<>();
		String token = getAccessToken();
		if(StringUtils.isBlank(token)){
			result.setFailResult("tokan获取失败");
			return  result;
		}
		String url = this.oaMessageURL + token;
		String sendJSONPost = HttpUtil.sendJSONPost(url, msg);
		logger.info("发送工作通知消息入参：{},出参：{}",msg,sendJSONPost);
		Map<String, Object> map = new Gson().fromJson(sendJSONPost, Map.class);
		if(ObjectUtils.isNotEmpty(map) && "0.0".equals(String.valueOf(map.get("errcode")))){
			result.setSuccessResult(String.valueOf(map.get("errmsg")));
		}else {
			result.setFailResult(String.valueOf(map.get("errmsg")));
		}
		return result;
	}
}
