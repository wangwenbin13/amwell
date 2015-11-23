package com.amwell.listener;

import java.util.LinkedHashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import com.amwell.vo.SysAdminVo;


public class OnLineUserListener implements ServletContextListener{
	
	
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		context.setAttribute("usermap", new LinkedHashMap<SysAdminVo, HttpSession>());
	}

}
