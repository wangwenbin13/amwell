package com.action.text;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/** 
* @author 作者 : wangwenbin
* @version 创建时间：2015年11月11日 上午11:56:46 
* 类说明 
*/

public class TestProxy {
	public static void main(String[] args) {
		//被代理对象--必须实现接口
		BookFacadeImpl c = new BookFacadeImpl();
		//实现java.lang.reflect.InvocationHandler接口，会调用invoke方法
		InvocationHandler h = new BookFacadeProxy(c);
		BookFacade bookFacade = (BookFacade) Proxy.newProxyInstance(
				c.getClass().getClassLoader(), c.getClass().getInterfaces(), h);
		//com.sun.proxy.$Proxy0 代理对象
		System.out.println(bookFacade.getClass().getName());
		bookFacade.addBook();
	}
}
