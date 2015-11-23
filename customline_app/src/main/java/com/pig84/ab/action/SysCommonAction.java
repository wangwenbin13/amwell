package com.pig84.ab.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.service.IISheKouService;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.bean.AppVo_4;

/**
 * 系统相关业务
 * @author zhangqiang
 *
 */
@ParentPackage("no-interceptor")
@Namespace("/sysCommon")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SysCommonAction extends BaseAction {

	@Autowired
	private IISheKouService ishekou;
	
	/**
	 * 获取初始化数据
	 * @return
	 */
	@Action(value="getCommonData",results = {@Result(type = "json")})
	public String getCommonData(){
		String nowTime = MyDate.Format.DATETIME.now();//当前系统时间
		String ticketLimit = PropertyManage.get("init.ticketLimit");	//车票显示标识
		String checkTicketSTime = PropertyManage.get("init.checkTicketSTime");//发车前多少分钟显示车票
		String checkTicketETime = PropertyManage.get("init.checkTicketETime");//发车后多少分钟显示车票
		
		AppVo_4 vo = new AppVo_4();
		vo.setA1(nowTime);
		vo.setA2(ticketLimit);
		vo.setA3(checkTicketSTime);
		vo.setA4(checkTicketETime);
		write(vo);
		
		return null;
	}
	
}
