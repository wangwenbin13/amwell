package com.pig84.ab.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.service.ICaiShengHuoService;
import com.pig84.ab.vo.bean.AppVo_1;

/**
 * 彩生活相关业务
 * @author zhangqiang
 *
 */
@ParentPackage("no-interceptor")
@Namespace("/app_caishenghuo")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CaiShengHuoAcion extends BaseAction {

	@Autowired
	private ICaiShengHuoService iCaishenghuo;
	
	/**
	 * 彩生活登录业务
	 * @return 0：内部错误  1:手机号码格式错误    2：密码错误    其他：用户ID
	 * @throws Exception 
	 */
	@Action(value = "caiShengHuoLogin",results={@Result(type="json")})
	public String caiShengHuoLogin() throws Exception{
		AppVo_1 vo = new AppVo_1();
		String username = request.getParameter("username");		//用户名 (需要URL解码)
		String mobile = request.getParameter("mobile");			//手机号码
		String password = request.getParameter("password");		//校验码
		String cityName = request.getParameter("cityName");		//所在城市
		
		boolean isMobileNo = isMobileNo(mobile);
		if(isMobileNo){
			String result = iCaishenghuo.login(username,mobile,password,cityName);
			vo.setA1(result);
			
		}else{
			vo.setA1("1"); //手机号码格式错误
		}
		write(vo);
		return null;
	}
	
	/**
	 * 验证手机号码
	 * @param mobile
	 * @return
	 */
	public static boolean isMobileNo(String mobile){
		String regExp = "^((13[0-9])|(14[0-9])|(15([0-9]))|(17([0-9]))|(18[0-9]))\\d{8}$"; 
		Pattern p = Pattern.compile(regExp);  
		Matcher m = p.matcher(mobile);  
		return m.find();
	}
}
