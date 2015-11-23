package com.amwell.action.sys;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.Sha1Utils;
import com.amwell.service.ISysAdminService;
import com.amwell.vo.SysMgrAdminVo;

/**
 * 修改密码
 * 
 * @author 胡双
 *
 */
@ParentPackage("user-finit")
@Namespace("/changePassWord")
@SuppressWarnings("all")
public class SystemChangePassWordAction extends BaseAction {

	@Autowired
	private ISysAdminService sysAdminService;

	@Action(value = "toChangePassWord", results = {
			@Result(name = "success", location = "/WEB-INF/view/syssetting/changePassword.jsp") })
	public String toChangePassWord() throws Exception {
		return SUCCESS;
	}

	/**
	 * 修改密码
	 */
	@Action(value = "updateUserPassWord", results = { @Result(type = "json") })
	public String updateUserPassWord() throws Exception {
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		// 获取用户的登陆信息
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysMgrAdminVo admin = (SysMgrAdminVo) httpSession.getAttribute("userInfo");
		String newpassword = request.getParameter("newpwd1");
		if (null != newpassword && !"".equals(newpassword)) {
			boolean flag = sysAdminService.updateUserPassWord(newpassword, 1, admin);
			if (flag) {
				pw.print("yes");
				return null;
			} else {
				pw.print("no");
				return null;
			}
		}
		return null;
	}

	/**
	 * 验证旧密码是否正确
	 * 
	 */
	@Action(value = "oldPassWordIsRight", results = { @Result(type = "json") })
	public String oldPassWordIsRight() throws Exception {
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		// 获取用户的登陆信息
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysMgrAdminVo admin = (SysMgrAdminVo) httpSession.getAttribute("userInfo");
		String oldPwd = request.getParameter("oldPwd");
		if (null != oldPwd && !"".equals(oldPwd)) {
			SysMgrAdminVo sysAdminVo = sysAdminService.queryAdminByUserIdAndSysId(admin.getUserId(), 1);
			String pwdStr = Sha1Utils.encrypt(sysAdminVo.getLoginName() + oldPwd);
			if (pwdStr.equals(sysAdminVo.getPassWord())) {
				pw.print("yes");
				return null;
			} else {
				pw.print("no");
				return null;
			}
		}
		pw.print("旧密码不能为空");
		return null;
	}
}
