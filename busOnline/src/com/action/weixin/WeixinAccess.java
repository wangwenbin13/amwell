package com.action.weixin;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bean.system.SysAppSetting;
import com.service.system.SysAppSettingService;
import com.util.common.SHA1;
import com.util.constants.MsgType;
import com.util.constants.PropertiesConfig;
import com.util.weixin.MessageUtil;
import com.util.weixin.WeixinContentUtil;

/**
 * 微信公众平台接入
 * 
 * @author shawn.zheng
 */
@Controller
@RequestMapping("/weixinAccess")
public class WeixinAccess {
	// 日志
	private Logger log = Logger.getLogger(WeixinAccess.class.getName());

	@Autowired
	private SysAppSettingService sysAppSettingService;

	/**
	 * 开启开发者模式
	 * @param signature: 微信加密签名
	 * @param timestamp: 时间戳
	 * @param nonce: 随机数
	 * @param echostr: 随机字符串
	 * @return
	 */
	@RequestMapping(value = "/go", method = RequestMethod.GET)
	@ResponseBody
	public String go(String signature, String timestamp, String nonce, String echostr) {
		try {
			// 构造参数列表
			List<String> params = new ArrayList<String>();
			params.add(PropertiesConfig.WEIXIN_TOKEN);
			params.add(timestamp);
			params.add(nonce);
			// 将token、timestamp、nonce三个参数进行字典序排序
			Collections.sort(params, new Comparator<String>() {
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));
log.error("===temp:" + temp);
log.error("===signature:" + signature);
			// 将经过加密的字符串与微信加密签名进行比较，若相匹配，则通过真实性认证
			if (temp.equals(signature)) {
log.error("ok");
				return echostr;
			} else {
log.error("error");
				return "failure";
			}
		} catch (Exception e) {
log.error(e.getMessage());
			return "failure";
		}
	}

	/**
	 * 处理微信的post请求
	 * 
	 * @param request: request请求
	 * @param response: response响应
	 * @throws Exception
	 */
	@RequestMapping(value = "/go", method = RequestMethod.POST)
	@ResponseBody
	public void go(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			// 解析XML信息
			Map<String, String> dataMap = MessageUtil.parseXml(request);
			if (dataMap != null) {
				// 获取消息类型
				String responseXML = null;
				String msgType = dataMap.get("MsgType");
				if (msgType.equals(MsgType.EVENT)) {
					String EventKey = dataMap.get("EventKey");
					StringBuffer content = null;
					if (!StringUtils.isEmpty(EventKey) && EventKey.equals("downloadApp")) {
						// 下载app推荐文本消息
						content = WeixinContentUtil.createDownloadAppMessage();
					} else {
						// 回复普通文本消息
						content = WeixinContentUtil.createHelpMessage();
						//若运营平台有配置回复文本，则取运营平台的文本
						List<SysAppSetting> sysAppSettingList = sysAppSettingService.list(null);
						if(sysAppSettingList != null&&!sysAppSettingList.isEmpty()){
							SysAppSetting sysAppSetting = sysAppSettingList.get(0);
							String WXreplay = sysAppSetting.getWXreply();
							if(!StringUtils.isEmpty(WXreplay)){
								content = new StringBuffer(sysAppSetting.getWXreply());
							}
						}
						String event = dataMap.get("Event");
						if (!StringUtils.isEmpty(event) && event.equals("subscribe")) {
							// 关注成功，统计用户关注记录
							// passengerInfoService.syncSubscribeRecord(dataMap.get("FromUserName"));
						}
					}
					responseXML = WeixinContentUtil.createTextMessage(dataMap, content.toString());
				} else {
					// 若为其他类型，则生成对应的客服消息
					responseXML = WeixinContentUtil.createCustomServiceMessage(dataMap);
				}
				if (!StringUtils.isEmpty(responseXML)) {
					out.write(responseXML.toString());
				}
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
