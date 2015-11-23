package com.amwell.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.service.IPermissionService;
import com.amwell.vo.SysMgrAdminVo;
import com.amwell.vo.SysPermissionVo;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;


public class FinitUser extends MethodFilterInterceptor {
	
	private static final long serialVersionUID = -6476050103091194410L;
	
	private static final Logger logger = Logger.getLogger(FinitUser.class);
	
	//注入权限Service
	@Autowired
	private IPermissionService permissionService;
	
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		
		//拦截器主要作用，从request中获取登录状态
		 HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		 SysMgrAdminVo sysAdmin= (SysMgrAdminVo) request.getSession().getAttribute("userInfo");
		 //判断用户是否有访问的权限
		 String nameSpace = invocation.getProxy().getNamespace();
		 String actionName = invocation.getProxy().getActionName();
		 String path = ".."+nameSpace+"/"+actionName;
		 logger.info("==================================================================");
		 logger.info(path);
	
		 //判断用户是否登陆
		 if(sysAdmin ==null){
			 return "login";
		 }else{
			
			 
			 //对左侧菜单特别处理
			// if("../changePassWord/toLeft".equals(path)){
				// return invocation.invoke();
			// }
			 
			 //确认该请求是在需要拦截的范围内
			// List<SysPermissionVo> permissionsAll = (List<SysPermissionVo>) ServletActionContext.getServletContext().getAttribute("permissionsAll");
			 // >> 根据请求路径和系统类型查询权限
			 //SysPermissionVo permission = permissionService.queryPermissionInfoByUrlAndSysType(path,0);
			 // >> 判断该请求的命名空间是否在权限的控制范围内
			 int count = permissionService.queryPermissionByNameSpace(".."+nameSpace,0);
			 
			 if(0 < count){
				 // >> 当前登录人的权限
				 List<SysPermissionVo> userPermissions = sysAdmin.getSysPermissions();
				 for(int i = 0;i<userPermissions.size();i++){
					
					SysPermissionVo sysPermissionVo = userPermissions.get(i);
					if(sysPermissionVo.getUrl().startsWith(".."+nameSpace)){
						return invocation.invoke();
					}
				 }
				 return "login";
			 }else{
				 // >> 不需要拦截的请求，直接放行
				 return invocation.invoke();
			 }
			
		 }
		
    
    }
}
