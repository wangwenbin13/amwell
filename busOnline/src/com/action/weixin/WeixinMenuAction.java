package com.action.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.common.PostHttpClient;
import com.util.weixin.WeixinConstants;
import com.util.weixin.WeixinContentUtil;
import com.util.weixin.WeixinMenuUtil;

/**
 * 微信菜单定制类
 * 
 * @author shawn.zheng
 * 
 */
@Controller
@RequestMapping("/weixinMenu")
public class WeixinMenuAction {

	/**
	 * 创建微信自定义菜单
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping("/createMenu")
	@ResponseBody
	public String createMenu() throws ClientProtocolException, IOException {
		// 获取微信令牌
		String access_token = WeixinContentUtil.getWeixinToken();
		// 创建微信菜单
		String url = WeixinConstants.WEIXIN_CREATE_MENU_URL + access_token;
		JSONObject json = WeixinMenuUtil.createMenuJSON();
		Map<String, Object> resultMap = PostHttpClient.postHttpReq(url, json.toString());
		if ((Integer) resultMap.get("statusCode") == 200)
			return (String) resultMap.get("responseMsg");
		return null;
	}

	@RequestMapping(value="/showMenu",produces={"text/html;charset=utf-8"})
	@ResponseBody
	public String showMenu() throws UnsupportedEncodingException {
		StringBuffer html = new StringBuffer("<html><body><pre>");
		JSONObject json = WeixinMenuUtil.createMenuJSON();
		html.append(json.toString(2));
		html.append("</pre></body></html>");
		return html.toString();
	}
}
