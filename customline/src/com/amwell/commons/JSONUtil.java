package com.amwell.commons;

import java.util.List;

import com.google.gson.Gson;

/**
 * 工具类：JSON 
 * 
 * @author zhangqiang
 */
public class JSONUtil {
	
	/**
	 * 将包含一组实体Bean的List转换成JSON格式的字符串
	 * @param beanList
	 * @return
	 */
	public static String formObjectsToJSONStr(List<?> beanList) {
		Gson gson = new Gson();
		return beanList.size()>0?gson.toJson(beanList):"[]";
	}

	/**
	 * 将实体Bean转换成JSON格式的字符串
	 * @param obj
	 * @return
	 */
	public static String formObjectToJSONStr(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	/**
	 * App专用
	 * @param obj
	 * @return
	 */
	public static String formObjectToJsonObject(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	/**
	 * 将一组实体转换为Ext.data.Reader所需的JSON字符串格式
	 * @param list
	 * @return
	 */
	public static String formObjectsToJSONReaderStr(List<?> list) {
		if(list==null){
			return null;
		}
		int size = list.size();
		if (list != null && list.size() > 0) {
			if (list.get(list.size() - 1).getClass().getName().equals(
					"java.lang.Integer")) {
				size = (Integer) list.get(list.size() - 1);
				list.remove(list.size() - 1);
			}
		}		
		StringBuffer sbf = new StringBuffer("");
		sbf.append("{ \"total\": ");
		sbf.append(size);
		sbf.append(", \"rows\": ");
		sbf.append(formObjectsToJSONStr(list));
		sbf.append("}");
		return sbf.toString();
	}
	
	
	
	/**
	 * 带分页的JSON字符串处理
	 * @param list
	 * @param count 记录的数量（通过页面传过来，方便分页）
	 * @return
	 */
	public static String formObjectsToJSONPageStr(List<?> list, int count) {
		StringBuffer sbf = new StringBuffer("");
		sbf.append("{ \"total\": ");
		sbf.append(count);
		sbf.append(", \"rows\": ");
		sbf.append(formObjectsToJSONStr(list));
		sbf.append("}");
		return sbf.toString();
	}
	
	
	/**
	 * 将单个实体转换为Ext.data.Reader所需的JSON字符串格式
	 * @param obj
	 * @return
	 */
	public static String formObjectToJSONReaderStr(Object obj) {
		StringBuffer sbf = new StringBuffer("");
		sbf.append("{ \"total\": 1, \"rows\": ");
		sbf.append(formObjectToJSONStr(obj));
		sbf.append("}");
		return sbf.toString();
	}
}
