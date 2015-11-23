package com.amwell.commons;

import java.util.List;

import com.google.gson.Gson;

/**
 * 工具类：JSON 
 * 
 * @author zhangqiang
 */
public class JSONUtil {
	
	private static final Gson gson = new Gson();
	
	/**
	 * 将包含一组实体Bean的List转换成JSON格式的字符串
	 * @param beanList
	 * @return
	 */
	public static String formObjectsToJSONStr(List<?> beanList) {
		return gson.toJson(beanList);
	}

	/**
	 * 将实体Bean转换成JSON格式的字符串
	 * @param obj
	 * @return
	 */
	public static String formObjectToJSONStr(Object obj) {
		return gson.toJson(obj);
	}

}
