package com.pig84.ab.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.service.ReturnMoneyService;
import com.pig84.ab.vo.bean.AppVo_1;

/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 退款相关
 */
@ParentPackage("no-interceptor")
@Namespace("/app_return")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ReturnMoneyAction extends BaseAction {

	@Autowired
	private ReturnMoneyService returnMoneyService;
	
	/**
	 * 财付通退款
	 * @return
	 * @throws IOException
	 */
	@Action(value = "returnFromCFT",results={@Result(type="json")})
	public String returnFromCFT() throws IOException{
		String leaseOrderNo = request.getParameter("leaseOrderNo");//订单号
		String realReturn = request.getParameter("realReturn");//实际退票金额
		HttpServletResponse response = ServletActionContext.getResponse();
		AppVo_1 vo = new AppVo_1();
		int statu = returnMoneyService.returnFromCFT(leaseOrderNo,realReturn,request,response);
		vo.setA1("1");
		write(vo);
		return null;
	}
}
