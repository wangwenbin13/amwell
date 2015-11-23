package com.amwell.commons;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter {

	private final static Logger logger = Logger.getLogger(JsonWriter.class);

	/**
	 * 输出vo类型json
	 * 
	 * @param obj
	 */
	public static void write(Object obj) {
		String jsonStr = "";
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		if (obj == null) {
			jsonStr = "{}";
		} else {
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			jsonStr = gsonbuilder.create().toJson(obj);
		}
		try {
			response.getWriter().print(jsonStr);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public static String toJsonString(Object obj){
		GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
		return gsonbuilder.create().toJson(obj);
	}

	/**
	 * 输出list类型json
	 * 
	 * @param list
	 */
	public static <T> void write(List<T> list) {
		String jsonStr = "";
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		if (list == null) {
			jsonStr = "[]";
		} else {
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			jsonStr = gsonbuilder.create().toJson(list);
		}
		try {
			response.getWriter().print(jsonStr);
		} catch (IOException e) {
			logger.info(e.getMessage(),e);
		}
	}

	/**
	 * 输出vo类型json
	 * 
	 * @param obj
	 */
	public static void writeString(String str) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		if (StringUtils.isEmpty(str)) {
			str = "{}";
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e.getMessage(),e);
		}
	}
}
