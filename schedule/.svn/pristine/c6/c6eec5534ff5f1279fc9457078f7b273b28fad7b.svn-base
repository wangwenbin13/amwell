package com.amwell.action.Login;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.entity.UserVo;
import com.amwell.service.ILoginService;

@ParentPackage("json-default")
@Namespace("/user")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class IndexAction extends BaseAction {

	private static final long serialVersionUID = 7870389893342679883L;

	@Autowired
	private ILoginService biz;

	private UserVo user;

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}

	/**
	 * 添加与修改（跳转）
	 * 
	 * @return
	 */
	@Action(value = "add_forward", results = { @Result(name = "success", location = "../../user/add.jsp") })
	public String Login() {
		// 如果有ID，自动赋值
		String id = request.getParameter("id");
		if (id != null) {
			user = biz.getUserById(id);
		}
		return SUCCESS;
	}

	/**
	 * 保存
	 * 
	 * @return
	 */
	@Action(value = "add", results = { @Result(name = "success", location = "../../user/ok.jsp") })
	public String add() {
		int flag = biz.Add(user);// 如果主键存在，执行修改动作，主键为空则执行新增动作
		System.out.println(flag);
		return SUCCESS;
	}

	/**
	 * 登录(跳转)
	 * 
	 * @return
	 */
	@Action(value = "login_forward", results = { @Result(name = "success", location = "../../user/login.jsp") })
	public String login_forward() {
		return SUCCESS;
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	@Action(value = "login", results = {
			@Result(name = "success", type = "redirect", location = "/center/list.action") })
	public String login() {
		UserVo uservo = biz.Login(user);
		if (uservo != null) {
			session.put("uservo", uservo);
			return SUCCESS;
		} else {
			System.out.println("用户不存在");
			return null;
		}
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@Action(value = "del", results = { @Result(name = "success", type = "redirect", location = "/center/list.action") })
	public String del() {
		String id = request.getParameter("id");
		biz.delUserById(id);
		return SUCCESS;
	}

}
