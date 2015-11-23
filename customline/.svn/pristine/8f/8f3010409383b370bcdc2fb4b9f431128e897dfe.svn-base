package com.amwell.action.line;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.service.IPublishLineService;

/**
 * 批处理脚本
 * 
 * @author shawn.zheng
 */
@SuppressWarnings("all")
@ParentPackage("no-interceptor")
@Namespace("/batch")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class BatchHandlerAction extends BaseAction {

	@Autowired
	private IPublishLineService publishLineService;
	
	@Action(value="toSplitStation",results={@Result(name="success",location="/WEB-INF/view/batchSplitAllStation.jsp")})
	public String toSplitStation(){
		return "success";
	}

	/**
	 * 拆分站点
	 */
	@Action(value = "splitAllLineStation")
	public void splitAllLineStation() throws IOException {
		try {
			publishLineService.splitAllLineStation();
			getResponse().getWriter().println("success");
		} catch (Exception e) {
			getResponse().getWriter().println(e.getMessage());
		}
	}
}
