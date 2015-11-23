package com.action.weixin;

import java.util.HashMap;
import java.util.Map;

import com.util.common.PostHttpClient;
import com.util.weixin.WeixinContentUtil;

import net.sf.json.JSONObject;

/**
 * 微信推送信息
 * @author 胡双
 */
public class WeixinPushMessage {
	
	/**
	 * 推送文本信息
	 * 
	 * @param touser 发送给哪个微信openId
	 * @param content 发送的文本内容
	 */
	public static boolean textMessage(String touser,String content){
		
		if(null == touser || "".equals(touser)){
			throw new RuntimeException("微信openId不能为空");
		}
		if(null == content || "".equals(content)){
			throw new RuntimeException("发送的文本内容不能为空");
		}
		
		//获取access_token
		String accessToken = WeixinContentUtil.getWeixinToken();
		
		//构建文本json
		JSONObject obj = new JSONObject();
		obj.put("touser",touser);
		obj.put("msgtype", "text");
		JSONObject subObj = new JSONObject();
		subObj.put("content", content);
		obj.put("text", subObj);
		
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
		Map<String, Object> maps = new HashMap<String, Object>();
		try{
			maps = PostHttpClient.postHttpReq(url, obj.toString());
			Integer statusCode =  (Integer) maps.get("statusCode");
			
			if(null != statusCode && 200 == statusCode){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("发送文本信息失败");
		}
		return false;
	}
}
