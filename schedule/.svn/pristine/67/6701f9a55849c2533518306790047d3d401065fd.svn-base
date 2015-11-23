package com.amwell.action.sys;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.SecurityCode;
import com.amwell.commons.SecurityImage;
import com.amwell.service.IPermissionService;
import com.amwell.service.ISysAdminService;
import com.amwell.vo.SysMgrAdminVo;
import com.amwell.vo.SysPermissionVo;

@SuppressWarnings("all")
@ParentPackage("no-interceptor")
@Namespace("/login")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SystemLoginAction extends BaseAction {

	private static Logger logger = Logger.getLogger(SystemLoginAction.class);

	private String loginName;
	private String password;
	private String vcode;
	// 存放菜单
	public List<SysPermissionVo> mainMenu = new ArrayList<SysPermissionVo>();

	@Autowired
	private ISysAdminService sysAdminService;
	@Autowired
	private IPermissionService permissionService;

	/**
	 * 登陆后进入首页
	 */
	@Action(value = "toLogin", results = { @Result(name = "success", location = "/WEB-INF/view/main.jsp"),
			@Result(name = "toLoginPage", type = "redirect", location = "/login.jsp") })
	public String toLogin() throws Exception {
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		boolean flag = false;
		// 检查用户名或密码
		SysMgrAdminVo sysAdmin = (SysMgrAdminVo) httpSession.getAttribute("userInfo");
		if (null == sysAdmin) {
			return "toLoginPage";
		} else {
			// >> 加载左侧菜单
			mainMenu = permissionService.queryLeftMenu(1, "-1", sysAdmin.getUserId());
			// 获得对应菜单的子菜单
			for (int i = 0; i < mainMenu.size(); i++) {
				List<SysPermissionVo> childPermission = permissionService.queryLeftMenu(1, mainMenu.get(i).getCode(),
						sysAdmin.getUserId());
				mainMenu.get(i).setChildPermission(childPermission);
			}
			// 登陆成功
			// 获取用户可以访问的权限集合(通过userId)
			return SUCCESS;
		}
	}

	/**
	 * 加载验证码
	 */
	@Action(value = "securityCodeImage")
	public void securityCodeImage() {
		// 获取默认难度和长度的验证码
		String securityCode = SecurityCode.getSecurityCode();
		BufferedImage image = SecurityImage.generate(securityCode);
		// 将验证码放入session中(后面写)
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		httpSession.setAttribute("securityCode", securityCode);
		HttpServletResponse response = getResponse();
		try {
			ImageIO.write(image, "jpeg", response.getOutputStream());
		} catch (IOException e) {
			logger.error("Generate security image failed.", e);
		}
	}

	/**
	 * 验证登陆信息
	 */
	@Action(value = "checkLogin", results = { @Result(name = "success", type = "json") })
	public String checkLogin() throws Exception {
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		// 验证码校验
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		String securityCode = (String) httpSession.getAttribute("securityCode");
		// 验证码
		if (null == vcode || !vcode.equalsIgnoreCase(securityCode)) {
			// 验证码不正确
			pw.print("vCodeIsErorr");
			return null;
		}
		// >> 验证完验证码以后，清除验证码
		httpSession.removeAttribute("securityCode");
		// 对账号和密码做非空验证
		if (null == loginName || null == password) {
			pw.print("loginNameOrPasswordIsNull");
			return null;
		}
		// 检查用户名或密码
		boolean flag = false;// 掉后台进行账号密码校验
		SysMgrAdminVo sysAdmin = sysAdminService.queryAdminByLoginNameAndPassword(loginName, password, 1);
		if (null == sysAdmin) {
			pw.print("loginNameOrPasswordError");// 用户名或密码错误
			return null;
		}
		if (sysAdmin.getStatus() == 0) {
			pw.print("ZHGQ");// 账号无效
			return null;
		}
		if (sysAdmin.getStatus() == 1) {
			// 登陆成功
			pw.print("OK");
			httpSession.setAttribute("userInfo", sysAdmin);
			return null;
		}
		return null;
	}

	/**
	 * 踢掉已登录的商户账号
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "kickOutSysMgr", results = { @Result(name = "success", type = "json") })
	public String kickOutSysMgr() throws Exception {
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		HttpServletRequest request = ServletActionContext.getRequest();
		// >> 获取登陆账号
		String businessId = request.getParameter("businessId");
		List<SysMgrAdminVo> currentClickAdmins = sysAdminService.queryAdminByBusinessId(businessId, 1);
		String rStr = "none";
		// 说明存在
		if (currentClickAdmins != null && currentClickAdmins.size() > 0) {
			ServletContext context = request.getSession().getServletContext();
			Map<SysMgrAdminVo, HttpSession> users = (Map<SysMgrAdminVo, HttpSession>) context.getAttribute("usermap");
			for (int i = 0; i < currentClickAdmins.size(); i++) {
				HttpSession session = users.get(currentClickAdmins.get(i));
				if (session != null) {
					session.invalidate();
					rStr = "kiclkOutSuccess";
				}
			}
		}
		pw.write(rStr);
		return null;
	}

	/**
	 * 登出
	 */
	@Action(value = "toLoginOut", results = { @Result(name = "success", type = "redirect", location = "/login.jsp") })
	public String toLoginOut() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getSession(false) != null) {
			request.getSession().invalidate();
		}
		return SUCCESS;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public List<SysPermissionVo> getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(List<SysPermissionVo> mainMenu) {
		this.mainMenu = mainMenu;
	}

}
