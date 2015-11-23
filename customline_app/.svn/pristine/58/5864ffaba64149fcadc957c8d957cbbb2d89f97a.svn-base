package com.pig84.ab.utils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * ajax跨域header过滤
 * @author gongxueting
 *
 */
public class CrossDomanFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res=(HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin","*");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
