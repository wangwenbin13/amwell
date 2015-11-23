package com.amwell.action.sys;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.Sha1Utils;
import com.amwell.service.IPermissionService;
import com.amwell.service.ISysAdminService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysPermissionEntity;
import com.amwell.vo.SysPermissionVo;


/**
 * 修改密码
 * @author 胡双
 *
 */
@ParentPackage("user-finit")
@Namespace("/changePassWord")
@SuppressWarnings("all")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SystemChangePassWordAction extends BaseAction{
	
	@Autowired
	private IPermissionService permissionService;
	
	//存放菜单
	public List<SysPermissionVo> mainMenu = new ArrayList<SysPermissionVo>();
	
	// >>注入管理员Service
	@Autowired
	private ISysAdminService sysAdminService;
	
	/**
	 * TO_修改密码页面
	 */
	@Action(value="toChangePassWord",results={@Result(name="success",location="/WEB-INF/view/syssetting/changePassword.jsp")})
	public String toChangePassWord()throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 修改密码
	 */
	@Action(value="updateUserPassWord",results = { @Result( type = "json") })
	public String updateUserPassWord()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		//获取用户的登陆信息
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
		
		String newpassword = request.getParameter("newpwd1");

		if(null != newpassword && !"".equals(newpassword)){
			// >>
			boolean flag = sysAdminService.updateUserPassWord(newpassword,0,admin);
			if(flag){
				pw.print("yes");
				return null;
			}else{
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
	@Action(value="oldPassWordIsRight",results = { @Result( type = "json") })
	public String oldPassWordIsRight()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		//获取用户的登陆信息
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
		
		String oldPwd = request.getParameter("oldPwd");
		if(null != oldPwd && !"".equals(oldPwd)){
			
			SysAdminVo sysAdminVo = sysAdminService.queryAdminByUserIdAndSysId(admin.getUserId(), 0);
			
			String pwdStr = Sha1Utils.encrypt(sysAdminVo.getLoginName()+oldPwd);
			if(pwdStr.equals(sysAdminVo.getPassWord())){
				pw.print("yes");
				return null;
			}else{
				// >> 不相同
				pw.print("no");
				return null;
			}
			
		}
		pw.print("旧密码不能为空");
		return null;
	}
	
	
	
	/**
	 * 加载左侧菜单
	 */
	@Action(value="toLeft",results={@Result(name="success",location="/WEB-INF/view/leftmenu.jsp")})
	public String toLeft()throws Exception{
		
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo sysAdmin = (SysAdminVo) httpSession.getAttribute("userInfo");
		
		//获取菜单列表
		mainMenu = permissionService.queryLeftMenu(0,"-1",sysAdmin.getUserId());
		
		//获得对应菜单的子菜单
		for(int i=0;i<mainMenu.size();i++){
			List<SysPermissionVo> childPermission = permissionService.queryLeftMenu(0,mainMenu.get(i).getCode(),sysAdmin.getUserId());
			mainMenu.get(i).setChildPermission(childPermission);
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 个人信息
	 * @return
	 */
	@Action(value="personInfo",results={@Result(name="success",location="/WEB-INF/view/syssetting/selfInfo.jsp")})
	public String personInfo(){
		
		return SUCCESS;
	}
	
	
	public List<SysPermissionVo> getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(List<SysPermissionVo> mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	
}
