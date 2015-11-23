package com.pig84.ab.utils;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class ExceptionInterceptor extends MethodFilterInterceptor {

	private Logger logger = Logger.getLogger(ExceptionInterceptor.class);

	protected String doIntercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			Conn.close();
		}
	}

}
