package com.amwell.action.user;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.amwell.action.BaseAction;

/**
 * 用户管理
 * @author 张欢
 *
 */
@ParentPackage("user-finit")
@Namespace("/user")
public class UserAction extends BaseAction {
	
	/**
	 * 乘客管理
	 */
	@Action(value="getCustomList",results={@Result(name="success",location="../../view/user/customManagerList.jsp")})
	public String getCustomList()throws Exception{
		
		
		return SUCCESS;
	}
	
	/**
	 * 乘客详情
	 */
	@Action(value="getCustomDetail",results={@Result(name="success",location="../../view/user/customDetail.jsp")})
	public String getCustomDetail()throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 司机管理
	 */
	@Action(value="getDriverList",results={@Result(name="success",location="../../view/user/driverManagerList.jsp")})
	public String getDriverList()throws Exception{
		
		
		return SUCCESS;
	}
	
	/**
	 * 司机详情
	 */
	@Action(value="getDriverDetail",results={@Result(name="success",location="../../view/user/driverDetail.jsp")})
	public String getDriverDetail()throws Exception{
		
		return SUCCESS;
	}
}
