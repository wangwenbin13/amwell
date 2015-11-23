package com.amwell.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.service.IPermissionService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysPermissionVo;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class FinitUser extends MethodFilterInterceptor {

	// 后台增加权限菜单入口
	private static final String PERMISSION = "/permission";
	// 注入权限Service
	@Autowired
	private IPermissionService permissionService;

	private List<SysPermissionVo> threeLevelMenu;
	private String defultUrl = "";

	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// 拦截器主要作用，从request中获取登录状态
		HttpServletRequest request = (HttpServletRequest) invocation
				.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		SysAdminVo sysAdmin = (SysAdminVo) request.getSession().getAttribute("userInfo");
		String nameSpace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String path = ".." + nameSpace + "/" + actionName;
		if (PERMISSION.equals(nameSpace)) {
			return invocation.invoke();
		}
		// 判断用户是否登陆
		if (sysAdmin == null) {
			return "login";
		} else {
			// 判断用户是否有访问的权限
			// 对左侧菜单特别处理
			if ("../changePassWord/toLeft".equals(path)) {
				return invocation.invoke();
			}
			int count = permissionService.queryPermissionByNameSpace(path, 0);
			if (0 < count) {
				// >> 当前登录人的权限
				List<SysPermissionVo> userPermissions = sysAdmin.getSysPermissions();
				for (int i = 0; i < userPermissions.size(); i++) {
					SysPermissionVo sysPermissionVo = userPermissions.get(i);
					if (sysPermissionVo.getUrl().equals(path)) {
						// 通过这个权限的code查找三级
						String level = request.getParameter("level");
						if (null != level && "two".equals(level)) { // 是二级菜单
							// 获取用户的三级菜单
							threeLevelMenu = permissionService.queryLeftMenu(0,
									sysPermissionVo.getCode(), sysAdmin.getUserId());
							if (null != threeLevelMenu && threeLevelMenu.size() > 0) {
								request.setAttribute("threeLevelMenu", threeLevelMenu);
								defultUrl = threeLevelMenu.get(0).getUrl();
								request.setAttribute("defultUrl", defultUrl);
							} else {
								// 查询系统该2级权限是否有三级权限
								SysPermissionVo vo = permissionService
										.queryPermissionByCode(sysPermissionVo.getCode(), 0);
								if (vo == null) {
									return invocation.invoke();
								} else {
									return "noPermission";
								}
							}
						}
						return invocation.invoke();
					}
				}
				return "login";
			} else {
				// >> 不需要拦截的请求，直接放行
				return invocation.invoke();
			}
		}
	}
}
