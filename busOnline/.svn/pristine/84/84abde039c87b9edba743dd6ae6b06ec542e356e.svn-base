package com.util.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author wangwenbin
 *
 * 2014-9-27
 */
/**
 * session工具类
 */
public class SessionUtil {

	/**
	 * 
	 */
	public static void addAttributeAndRemove(HttpServletRequest request,
			String key, Map<String, Object> maps) {
		String[] keys = { "loginRedirectMap", "book", "loginRedirectMap",
				"loginRedirectMap", "loginRedirectMap" };
		for (int i = 0; i < keys.length; i++) {
			request.getSession().removeAttribute(keys[i]);
		}
		request.getSession().setAttribute(key, maps);
	}
}
