package com.pig84.ab.action;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.cache.UserCache;
import com.pig84.ab.service.IISheKouService;
import com.pig84.ab.service.ILoginAndRegisterService;
import com.pig84.ab.utils.Http;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_12;
import com.pig84.ab.vo.bean.AppVo_3;

/**
 * I蛇口相关业务
 * @author zhangqiang
 *
 */
@ParentPackage("no-interceptor")
@Namespace("/app_shekou")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SheKouAcion extends BaseAction {

	@Autowired
	private IISheKouService ishekou;
	
	@Autowired
	private ILoginAndRegisterService loginAndRegister;
	
	/**
	 * 蛇口登录业务
	 * @return
	 * @throws Exception 
	 */
	@Action(value = "sheKouLogin",results={@Result(type="json")})
	public String sheKouLogin() throws Exception{
		
		String ST = request.getParameter("ST");//用户登录标识
		String httpsUrl=PropertyManage.get("ishekou_checkST_url");//验证地址
		String res = Http.get(httpsUrl, "ticket", ST);//（测试用http）
		String telephone = "";//用户手机号码
		
		if(res.contains("authenticationFailure")){//验证失败
			AppVo_1 vo = new AppVo_1();
			vo.setA1("1");
			write(vo);
			return null;
		}
		
		//获取返回的手机号码
		telephone = res.substring(res.indexOf("<cas:mobile>"), res.indexOf("</cas:mobile>"));
		telephone = telephone.substring(21, telephone.lastIndexOf("]")-1);
		
		//校验手机号码
		boolean isMobile = isMobileNo(telephone);
		if(!isMobile){
			AppVo_1 vo = new AppVo_1();
			vo.setA1("1");
			write(vo);
			return null;
		}
		
		PassengerInfo appUser = ishekou.login(telephone);
		
		if(appUser!=null){//登录成功
//			session.put("appUser", appUser);//登录用户保存session
			String sessionid = loginAndRegister.updateSessionIdByTel(telephone);
			appUser.setSessionId(sessionid);
			UserCache.setUser(appUser);
			AppVo_12 vo = new AppVo_12();
			vo.setA1("0");
			vo.setA2(appUser.getPassengerId());		//用户IDq
			vo.setA3(appUser.getDisplayId());		//回显ID
			vo.setA4(appUser.getNickName());		//昵称
			vo.setA5(appUser.getSex());				//性别
			vo.setA6(appUser.getTelephone());		//手机号码
			String url = PropertyManage.get("http.root.url");  //ftp地址
			if(appUser.getHeaderPicUrl()!=null && !"".equals(appUser.getHeaderPicUrl())){
				vo.setA7(url+"/"+appUser.getHeaderPicUrl());	//头像URL
			}
			vo.setA8(appUser.getPassWord());		//密码
			vo.setA9(appUser.getRegisterTime());	//注册时间
			vo.setA10(appUser.getTwoCodeValue());	//二维码唯一标识
			vo.setA11(appUser.getTwoCodePicUrl());	//二维码图片地址 
			vo.setA12(appUser.getSourcefrom());		//用户来源
			write(vo);
		}else{//登录失败
			
			AppVo_1 vo = new AppVo_1();
			vo.setA1("1");
			write(vo);
		}
		
		return null;
	}
	
	/**获取蛇口APP最新版本
	 * @throws IOException **/
	@Action(value = "getVersion",results={@Result(type="json")})
	public String getVersion() throws IOException{
		
		AppVo_3 vo = ishekou.getVersion();
		String opic = PropertyManage.get("ftp.image.root.opic");
		String root = PropertyManage.get("http.root.url");
		String url = root+"/busonline_ishekou/"+opic+"/"+vo.getA2();
		write(vo);
		return null;
	}
	
	/**
	 * 验证手机号码
	 * @param mobile
	 * @return
	 */
	public boolean isMobileNo(String mobile){
		String regExp = "^((13[0-9])|(14[0-9])|(15([0-9]))|(17([0-9]))|(18[0-9]))\\d{8}$"; 
		Pattern p = Pattern.compile(regExp);  
		Matcher m = p.matcher(mobile);  
		return m.find();
	}
}
