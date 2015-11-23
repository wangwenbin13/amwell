package com.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SidFilter implements Filter {

	private static final ThreadLocal<String> HOLDER = new ThreadLocal<String>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String sid = findSid(req);
		HOLDER.set(sid);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
	
	public static String get() {
		return HOLDER.get();
	}

	private String findSid(HttpServletRequest req) {
		if(req==null)
			return null;
		if(req.getCookies()==null){
			return null;
		}
		for (Cookie cookie : req.getCookies()) {
			if ("sid".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
