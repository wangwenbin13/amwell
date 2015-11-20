package com.action.text;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/** 
* @author 作者 : wangwenbin
* @version 创建时间：2015年11月11日 上午11:48:25 
* 类说明 
*/

public class BookFacadeProxy implements InvocationHandler{

	private Object target;//目标对象(被代理对象)
	
	public BookFacadeProxy(Object target){
		this.target = target;
		
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		System.out.println("开始");
		//com.sun.proxy.$Proxy0 
		System.out.println(proxy.getClass().getName());//proxy是代理对象

		//这里是要调用目标方法，不调用proxy   result:目标方法的返回值
		result = method.invoke(target, args);
		System.out.println("结束");
		System.out.println("result:"+result);
		return result;
	}

}
