package com.util.common;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import com.util.constants.WebServiceConstants;

/**
 * 下发短信工具类
 * @author shawn.zheng
 */
public class SendMessageUtil {

	/**
	 * 下发短信
	 * @param content: 短信内容
	 */
	public static boolean sendMessage(String mobile, String content) {
		String result = null;
		try {
			result = PostHttpClient.sendMsg(WebServiceConstants.SEND_MESSAGE_URL, content, mobile);
System.out.println("sendMessage : " + result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isEmpty(result)) {
			String[] resultArray = result.split(",");
			return resultArray[0].equals("03");
		} else {
			return false;
		}
	}
}
