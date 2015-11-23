package com.util.common;

import javax.servlet.http.HttpServletRequest;

public class BusCommonUtil {
	/**
	 * 根据星期编号来生成星期名称
	 * 
	 * @param code
	 *            星期编号
	 * @return String
	 */
	public static String trandWeek(String code) {
		if ("1".equals(code)) {
			return "周一";
		} else if ("2".equals(code)) {
			return "周二";
		} else if ("3".equals(code)) {
			return "周三";
		} else if ("4".equals(code)) {
			return "周四";
		} else if ("5".equals(code)) {
			return "周五";
		} else if ("6".equals(code)) {
			return "周六";
		} else {
			return "周日";
		}
	}
	
	/** 检测客户端是否为微信； */
	public static boolean checkWeixin(HttpServletRequest request) {
		return request.getHeader("user-agent").toLowerCase().indexOf("micromessenger") != -1;
	}
}
