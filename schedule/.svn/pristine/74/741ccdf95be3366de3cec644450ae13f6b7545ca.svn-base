package com.amwell.action.Login;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.StringUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.entity.UserVo;
import com.amwell.service.ILoginService;

@ParentPackage("user-finit")
@Namespace("/center")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
@SuppressWarnings("all")
public class CenterAction extends BaseAction {

	private static final long serialVersionUID = 8527679625442504045L;

	@Autowired
	private ILoginService biz;

	private UserVo user;

	/**
	 * 列表
	 * 
	 * @return
	 */

	@Action(value = "list", results = { @Result(name = "success", location = "../../user/list.jsp") })
	public String list() {
		/* 当前页 */
		int p = StringUtil.objectToInt(request.getParameter("p") != null ? request.getParameter("p") : "0");
		/* 将条件存到session,便于刷新后任然存在页面而不会丢失 */
		search = (Search) (search == null ? session.get("userList_Cond") : search);
		session.put("userList_Cond", search);
		/* 每页显示条数 */
		int pageSize = 3;
		// 集合对象
		map = biz.listByPage(search, p, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}
}
