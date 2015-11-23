package com.amwell.action.user;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.amwell.action.BaseAction;

/**
 * 代理商管理
 * @author 张欢
 *
 */
@ParentPackage("user-finit")
@Namespace("/agent")
public class AgentAction extends BaseAction {
	
	/**
	 * 代理商管理
	 */
	@Action(value="getAgentList",results={@Result(name="success",location="../../view/user/agentManagerList.jsp")})
	public String getAgentList()throws Exception{
		
		
		return SUCCESS;
	}
	
	/**
	 * 代理商增加
	 */
	@Action(value="addAgent",results={@Result(name="success",location="../../view/user/addAgent.jsp")})
	public String addAgent()throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 代理商详情
	 */
	@Action(value="getAgentDetail",results={@Result(name="success",location="../../view/user/agentDetail.jsp")})
	public String getAgentDetail()throws Exception{
		
		return SUCCESS;
	}
}
