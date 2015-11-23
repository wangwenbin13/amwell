package com.amwell.commons;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class AdminAuth extends MethodFilterInterceptor{
	protected String doIntercept(ActionInvocation invocation) throws Exception {        
        Map session = invocation.getInvocationContext().getSession();
        if(session.get("sessAdmin")!=null){
            //将数据流转给真正的Action
            return invocation.invoke();
        }else{
            //返回一个全局登录失败的result
            return "login";
        }
    }
}
