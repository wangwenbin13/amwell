package com.amwell.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.util.StringUtils;

import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.SysMgrAdminVo;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("all")
public class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware {

	private static final long serialVersionUID = 3965580311016942065L;

	protected HttpServletRequest request;

	protected Map<String,Object> session;

	protected List list;

	protected Search search;

	protected Page page;

	protected Map<String, Object> map = new HashMap<String, Object>();;

	public static final String KEY_SESSION_USER="userInfo";
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Map<String,Object> getSession() {
		return session;
	}

	public void setSession(Map<String,Object> session) {
		this.session = session;
	}

	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected SysMgrAdminVo getSessionUser() {
		Object obj = this.request.getSession().getAttribute(KEY_SESSION_USER);
		if(obj instanceof SysMgrAdminVo){
			return (SysMgrAdminVo) obj;
		}
		return null;
	}
	public String getClientIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public int getCurrentPageIndexFromPage(HttpServletRequest request){
		return  StringUtils.hasText(request.getParameter("currentPageIndex"))?Integer.parseInt(request.getParameter("currentPageIndex")):0;
	}
}
