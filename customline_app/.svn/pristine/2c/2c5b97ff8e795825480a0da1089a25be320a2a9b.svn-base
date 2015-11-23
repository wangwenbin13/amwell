package com.pig84.ab.action;

import java.io.IOException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.pig84.ab.service.IDriverService;
import com.pig84.ab.service.ILoginAndRegisterService;
import com.pig84.ab.utils.CacheUtil;
import com.pig84.ab.utils.Event;
import com.pig84.ab.utils.Html;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.utils.Message;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.Sha1Utils;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.thirdParties.Hlc;

import cn.jpush.api.utils.StringUtils;

/**
 * 新版优惠券相关
 * 
 * @author gongxueting
 * 
 */
@ParentPackage("no-interceptor")
@Namespace("/app_hlc")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class HlcAction extends BaseAction {
	
	@Autowired
	private ILoginAndRegisterService loginAndRegisterService;
	
	@Autowired
	private IDriverService driverService;
	
	/**
	 * 跳转到汇理财处理页面
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "toHlcPage",results={@Result(name = "success", location = "/index.jsp")})
	public String toHlcPage() throws IOException{
		return SUCCESS;
	}
	
	/**
	 * 发送验证码
	 */
	@Action(value = "sendHlcMsg", results = { @Result(type = "json") })
	public String sendHlcMsg() {
		String tel = request.getParameter("tel");
		
		//防止重复发送
		String key_time = "hlc_"+tel;
		String cacheCode = CacheUtil.getCache(key_time);
		if(cacheCode==null || "".equals(cacheCode)){
			
			String random = RandomStringUtils.randomNumeric(4);
			CacheUtil.setCache(key_time, random, 80);//标识80秒内只发送一次
			new Message("【小猪巴士】您的验证码为：%s，2分钟内有效，请尽快验证。", random).sendTo(tel);
			CacheUtil.setCache(tel, random, 120);
		}
		
		AppVo_1 vo = new AppVo_1();
		vo.setA1("1");
		write(vo);
		return null;
	}
	
	/**
	 * 输入验证码并验证
	 */
	@Action(value = "validateHlcMsg",results={@Result(type="json")})
	public String validateHlcTel() throws Exception {
		String tel = request.getParameter("tel");
		String code = request.getParameter("code");
		
		AppVo_1 vo = new AppVo_1();
		if(StringUtils.isEmpty(tel)||StringUtils.isEmpty(code)){
			vo.setA1("0");//参数为空
			write(vo);
			return null;
		}
		//获取缓存中的验证码
		String cacheCode = CacheUtil.getCache(tel);
		if (cacheCode == null || "".equals(cacheCode)) {
			vo.setA1("-1");// 验证码失效
		} else if (!cacheCode.equals(code)) {
			vo.setA1("-2");// 验证码 错误      
			
			//只允许验证3次
			String key_check = "check_hlc_"+tel;
			String times = CacheUtil.getCache(key_check);
			if(times == null || "".equals(times)){
				CacheUtil.setCache(key_check, "1", 120);//验证次数，第一次
			}else{
				int count = Integer.valueOf(times);
				if(count>=3){
					CacheUtil.removeCache(tel);
					CacheUtil.removeCache(key_check);
				}else{
					count += 1;
					CacheUtil.updateCache(key_check, String.valueOf(count));
				}
			}
			
		} else if (cacheCode.equals(code)) {
			vo.setA1("1");//验证通过
			CacheUtil.removeCache(tel);
			
			//1.判断手机号码是否已存在
			//2.如果不存在则先注册小猪用户，然后调用汇理财注册接口，再给对应用户发10元优惠券
			//3.如果存在则调用汇理财注册接口并给对应用户发10元优惠券
			PassengerInfo appUser = loginAndRegisterService.getPassengerByTel(tel);
			if(null!=appUser&&"1".equals(appUser.getFlag())){//用户不存在
				appUser = registUser(tel);	
			}
			
			//1：校验不过，非法调用
			//2：手机号码已经注册
			//3：程序处理异常
			//0000：成功
			String result=Hlc.hlc_regist(appUser);
			if("0000".equals(result)){//注册成功，则发放优惠券
				Event.SINGLEHANDLE.triggerAsynchronous("user", appUser);
			}
			vo.setA1("h"+result);
		}
		
		write(vo);
		return null;
	}
	
	/**
	 * 注册小猪用户
	 * @param tel
	 * @return
	 * @throws Exception
	 */
	private PassengerInfo registUser(String tel) throws Exception {
		String registerTime = MyDate.Format.DATETIME.now(); // 注册时间
		String twoCodeValue = IdGenerator.seq(); // 二维码唯一标识
		PassengerInfo user = new PassengerInfo();
		user.setNickName(tel);
		user.setSex("0");
		user.setTelephone(tel);
		user.setPassWord(Sha1Utils.encrypt(tel + ""));// 密码加密规则：手机号码+"&"+密码
		user.setComments(tel);
		user.setRegisterTime(registerTime);
		user.setTwoCodeValue(twoCodeValue);

		String flag = loginAndRegisterService.register(user);
		AppVo_1 vo = new AppVo_1();
		vo.setA1(flag);

		if ("1".equals(flag)) {// 注册成功,添加im用户
			this.driverService.addImUser(user.getPassengerId(), "1");// 保存乘客信息

			// 调用优惠券规则检查及发放
			if (!"1".equals(user.getSex())) {
				user.setSex("0");
			}
			Event.REGISTER.triggerAsynchronous("user", user);
			
			return user;
		}
		return null;
	}
}