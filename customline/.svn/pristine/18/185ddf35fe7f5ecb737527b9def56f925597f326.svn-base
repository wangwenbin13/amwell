package com.amwell.action.sys;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.entity.Page;
import com.amwell.service.ISysAppSendService;
import com.amwell.service.ISysAreaService;
import com.amwell.service.ISysParamService;
import com.amwell.utils.JSONUtil;
import com.amwell.vo.SysAppSend;
import com.amwell.vo.SysArea;
import com.amwell.vo.SysParamVo;

@ParentPackage("no-interceptor")
@Namespace("/sysAppSend")
@SuppressWarnings("all")
@Scope("prototype")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SysAppSendAction extends BaseAction {

	@Autowired
	private ISysAppSendService sysAppSendService;

	/**
	 * 查询短信发送商家
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getMessageBusiness", results = { @Result(type = "json") })
	public String getMessageBusiness() throws Exception {
		System.out.println("<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>");
		SysAppSend sas=this.sysAppSendService.getMessageBusiness();
		String business=null;
		if(null==sas||StringUtils.isBlank(sas.getBusiness())){
			business="1";//默认为亿美
		}
		else{
			business=sas.getBusiness();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(business);
		return null;
	}
}
