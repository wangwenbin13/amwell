package com.pig84.ab.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.pig84.ab.vo.coupon.CouponListener;

/**
 * Listen to the context (eg. Tomcat) lifecycle.
 * 
 * @author GuoLin
 *
 */
public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		CouponListener.init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
