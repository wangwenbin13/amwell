package com.amwell.vo.coupon;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 工具类
 * @author gxt
 *
 */
public class RuleTool{

	public static Object getBean(String beanName){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");//配置文件放在src下面
		Object obj = null;
		if(ctx!=null){
			obj = ctx.getBean(beanName);
		}
		return obj;
	}

}
