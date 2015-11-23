package com.amwell.commons;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 工具类
 * @author gxt
 *
 */
@Component
@Lazy(false)
public class ApplicationContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext ctx;

	public static Object getBean(String beanName){
		return ctx.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return ctx.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		ApplicationContextHolder.ctx = ctx;
	}

}
