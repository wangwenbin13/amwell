package com.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.utils.Json;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	
	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	private static final long serialVersionUID = 3965580311016942065L;

	protected HttpServletRequest request;

	protected HttpServletResponse response;
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		this.response.setContentType(CONTENT_TYPE);
	}

	/**
	 * Write empty JSON object.
	 */
	protected void write() {
		write("{}");
	}

	/**
	 * Write JSON object.
	 * @param obj Object to JSON
	 */
	protected void write(Object obj) {
		send(obj == null ? "{}" : Json.toJson(obj));
	}
	
	/**
	 * Write JSON array.
	 * @param list List to JSON
	 */
	protected <T> void write(List<T> list) {
		send(list == null ? "[]" : Json.toJson(list));
	}
	
	/**
	 * Write pure JSON.
	 * @param json Pure JSON string
	 */
	protected void write(String json) {
		send(StringUtils.isEmpty(json) ? "{}" : json);
	}
	
	private void send(String entity) {
		try {
			response.getWriter().print(entity);
		} catch (IOException e) {
		}
	}

}
