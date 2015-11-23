package com.amwell.action.sys;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.SecurityCode;
import com.amwell.commons.SecurityImage;
import com.amwell.service.IPermissionService;
import com.amwell.service.ISysAdminService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysPermissionVo;

/**
 * 登陆(所有不需要拦截的action都继承no-interceptor)
 * @author 胡双
 *
 */
@SuppressWarnings("all")
@ParentPackage("no-interceptor")
@Namespace("/login")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SystemLoginAction extends BaseAction {
	
	
	private String loginName;
	private String password;
	private String vcode;
	// >> 右侧默认显示的页面 
	private String rightDefaultUrl;
	// >> 右上角的系统设置
	private String rightSysUrl;
	/**
	 * 验证码流
	 */
	private InputStream imageInputStream;
	
	@Autowired
	private ISysAdminService sysAdminService;
	@Autowired
	private IPermissionService permissionService;
	
	
	/**
	 * 去登陆
	 */
	@Action(value="toLogin",results={@Result(name="success",location="/WEB-INF/view/main.jsp"),
			@Result(name="toLoginPage",type="redirect",location="/login.jsp")})
	public String toLogin()throws Exception{
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		boolean flag = false;
		//检查用户名或密码
		SysAdminVo sysAdmin = (SysAdminVo) httpSession.getAttribute("userInfo");
		if(null == sysAdmin){
			return "toLoginPage";
		}else{
			// >> 获得右侧默认访问的路径
			List<SysPermissionVo> mainMenu = permissionService.queryLeftMenu(0,"-1",sysAdmin.getUserId());
			if(mainMenu!=null){
				// 定义是否显示右上角的系统设置
				String iShow = "no";
				//判断有没有系统设置权限
				for(int i=0;i<mainMenu.size();i++){
					SysPermissionVo spv = mainMenu.get(i);
					if("sys".equals(spv.getCode())){
						iShow = "yes";
						//获取系统管理的第一个菜单url
						List<SysPermissionVo> childPermission = permissionService.queryLeftMenu(0,spv.getCode(),sysAdmin.getUserId());
						rightSysUrl = childPermission.get(0).getUrl();
						break;
					}
				}
				request.setAttribute("iShow",iShow);
				//获取右侧默认的第一个url
				if(null != mainMenu && mainMenu.size()>0){
					List<SysPermissionVo> childPermission = permissionService.queryLeftMenu(0,mainMenu.get(0).getCode(),sysAdmin.getUserId());
					if(null != childPermission && childPermission.size()>0){
						rightDefaultUrl = childPermission.get(0).getUrl();
					}
				}
				//登陆成功
				//获取用户可以访问的权限集合(通过userId)
			}
			return SUCCESS;
		}
	}
	
	/**
	 * 加载验证码
	 */
	@Action(value="securityCodeImage")
	public void securityCodeImage()throws Exception{
		//获取默认难度和长度的验证码
		String securityCode = SecurityCode.getSecurityCode();
		BufferedImage image = SecurityImage.generate(securityCode);
		 
		//将验证码放入session中(后面写)
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		httpSession.setAttribute("securityCode", securityCode);
		
		HttpServletResponse response = getResponse();
		try {
			ImageIO.write(image, "jpeg", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成验证码Base64字符串
	 */
	@Action(value = "securityCodeStr", results = { @Result(type = "json") })
	public void securityCodeStr() throws Exception {
		String securityCode = SecurityCode.getSecurityCode();
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("security", "data:image/gif" + securityCode);
		JsonWriter.write(obj);
	}
	
	/**
	 * 验证登陆信息
	 */
	@Action(value="checkLogin",results={@Result(name="success",type="json")})
	public String checkLogin(){
		try{
			HttpServletResponse response = getResponse();
			response.setContentType("text/html;charset=GBK");
			PrintWriter pw = response.getWriter();
			//验证码校验
			HttpSession httpSession = ServletActionContext.getRequest().getSession();
			String securityCode = (String) httpSession.getAttribute("securityCode");
			
			//验证码
			
			if(null == vcode || !vcode.equalsIgnoreCase(securityCode)){
				//验证码不正确
				pw.print("vCodeIsErorr");
				return null;
			}
			
			
			//对账号和密码做非空验证
			if(null == loginName || null == password){
				pw.print("loginNameOrPasswordIsNull");
				return null;
			}
			
			//检查用户名或密码
			boolean flag = false;//掉后台进行账号密码校验
			
			//Map<String, String> maps = new HashMap<String, String>();
			//maps.put("loginName", loginName);
			//maps.put("passWord", EncryptionUtils.encrypt(loginName+password));
			SysAdminVo sysAdmin = sysAdminService.queryAdminByLoginNameAndPassword(loginName,password,0);
			
			
			
			if(null == sysAdmin){
				pw.print("loginNameOrPasswordError");//用户名或密码错误
				return null;
			}
			
			if(sysAdmin.getStatus()==0){
				pw.print("ZHGQ");//账号无效
				return null;
			}
			
			if(sysAdmin.getStatus()==1){
				//登陆成功
				pw.print("OK");
				httpSession.setAttribute("userInfo",sysAdmin);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 登出
	 */
	@Action(value="toLoginOut",results={@Result(name="success",type="redirect",location="/login.jsp")})
	public String toLoginOut()throws Exception{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getSession(false) != null){
			request.getSession().invalidate();
		}
		return SUCCESS;
	}
	
	/**
	 * 维持登录
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	@Action(value = "keepLogin", results = { @Result(type = "json") })
	public String keepLogin() throws IOException, JSONException{
//		SysAdminVo sysAdmin = (SysAdminVo) session.get("userInfo");
		String result = "1";
//		if(sysAdmin!=null){
//			session.put("userInfo", sysAdmin);
//			result = "1";
//		}else{
//			result = "0";
//		}
		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(result);
		return null;
	}
	
	//========================GET,SET方法================================
	

	public InputStream getImageInputStream() {
		return imageInputStream;
	}

	public void setImageInputStream(InputStream imageInputStream) {
		this.imageInputStream = imageInputStream;
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


	public String getRightDefaultUrl() {
		return rightDefaultUrl;
	}


	public void setRightDefaultUrl(String rightDefaultUrl) {
		this.rightDefaultUrl = rightDefaultUrl;
	}


	public String getRightSysUrl() {
		return rightSysUrl;
	}


	public void setRightSysUrl(String rightSysUrl) {
		this.rightSysUrl = rightSysUrl;
	}
	
	
	//============================================================
}
