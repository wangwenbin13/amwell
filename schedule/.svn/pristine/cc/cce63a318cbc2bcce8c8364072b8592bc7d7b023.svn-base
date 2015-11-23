package com.amwell.action.sys;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.amwell.action.BaseAction;

/**
 * 个人信息
 * 
 * @author 胡双
 *
 *
 */
@ParentPackage("user-finit")
@Namespace("/personalInfo")
@SuppressWarnings("all")
public class SystemPersonalInfoAction extends BaseAction {

	@Action(value = "show", results = { @Result(name = "success", location = "/WEB-INF/view/syssetting/selfInfo.jsp") })
	public String toShow() throws Exception {
		return SUCCESS;
	}
}
