package com.amwell.interceptor;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class FinitUser2 extends MethodFilterInterceptor {
	private static final long serialVersionUID = -6476050103091194410L;
	private static final Logger logger = Logger.getLogger(FinitUser2.class);

	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// 判断用户是否有访问的权限
		String nameSpace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String path = ".." + nameSpace + "/" + actionName;
		logger.info("==================================================================");
		logger.info(path);
		return invocation.invoke();
	}
}
