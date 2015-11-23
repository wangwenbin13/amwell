package com.action.v1.views;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.service.v1.LineService;
import com.service.v1.LocationService;
import com.service.v1.UserService;
import com.util.common.MyDate;
import com.util.constants.PropertiesConfig;
import com.util.weixin.WeixinMenuUtil;

/**
 *  /\_/\
 * (=+_+=)
 *   ( ).
 * @author sunjiaxiang
 * 貌似这是一个提供各种和用户有关接口处理的类哟。
 */
@Controller
@RequestMapping("/v1/user")
public class UserAction {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LineService lineService;
	
	@Autowired
	private LocationService locationService;
	
	/**
	 * 前往个人中心页面
	 * 微信相关
	 * @param code 微信授权码
	 * 第三方合作相关
	 * @param client_id: 第三方合作用的渠道ID;
	 * @param access_token: 第三方合作用的用户唯一标识;
	 * @param client_id: 第三方合作用的手机号码;
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/home", produces = { "text/html;charset=utf-8" })
	public ModelAndView workday(String code, String client_id, String access_token, String mobile, String singnature,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		ModelAndView mv = new ModelAndView("v1/home");
		if (!userService.initUserInfo(code, client_id, access_token, singnature, mv) && !StringUtils.isEmpty(code)) {
			response.sendRedirect(WeixinMenuUtil.getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/v1/user/home"));
			return null;
		}
		
		String sid = (String) mv.getModel().get("sid");
		response.addCookie(new Cookie("sid", sid));

		// 初始化所在城市
		String cityName = locationService.findCityByIp(request.getRemoteAddr());
		mv.addObject("cityName", cityName);
		mv.addObject("code", code);
		return mv;
	}
	
	/**
	 * 用户优惠券列表；
	 * @return String: 用户优惠券json;
	 */
	@RequestMapping(value = "/coupons", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String coupons(String uid, String page) {
		return userService.coupons(uid, page).toString(2);
	}

	/**
	 * 用户登录
	 * @return String: 用户信息json;
	 */
	@RequestMapping(value = "/login", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String login(String mobile, String code, String openId, String channel, HttpServletRequest request) {
		JSONObject res = null;
		int checkCode = userService.checkMessionCode(mobile, code);
		if (checkCode == 1 || checkCode == 3) {
			res = new JSONObject();
			res.put("a1", "验证码错误，请重新获取。");
			return res.toString(2);
		}
		if (checkCode == 2) {
			res = new JSONObject();
			res.put("a1", "验证码已过期，请重新获取。");
			return res.toString(2);
		}
		String pwd = mobile.substring(mobile.length() - 6);
		// 检测手机号是否使用过
		String uid = userService.hasTelephone(mobile);
		if (StringUtils.isEmpty(uid)) {
			try {
				res = userService.register(mobile, pwd, openId, channel);
			} catch (Exception e) {
				e.printStackTrace();
				res = new JSONObject();
				res.put("a1", e.getMessage());
			}
		} else {
			try {
				// sjx: 这里要改成直接帐号登录
				res = userService.getByPassengerId(uid);
				userService.bindOpenId(channel, uid, openId);
			} catch (Exception e) {
				e.printStackTrace();
				res = new JSONObject();
				res.put("a1", e.getMessage());
			}
		}
		return res.toString(2);
	}
	
	/**
	 * 展示票的信息
	 * @return String: 用户信息json;
	 */
	@RequestMapping(value = "/ticket", produces = { "text/html;charset=utf-8" })
	public ModelAndView ticket(String id,  String uid , String cid) {
		Logger.getLogger(UserAction.class).info("###############################################");
		Logger.getLogger(UserAction.class).info("v1/user/ticket  cid=" + cid);
		ModelAndView mv = new ModelAndView("v1/ticket");
		JSONObject order = checkOrder(id);
		try {
			if (order == null){
				throw new Exception();
			}
			JSONArray list = order.getJSONArray("list");
			long todayDate = todayDate();
			String targetCid = null;
			for(int index=0;index<list.size();index++){
				JSONObject classItem = list.getJSONObject(index);
				long orderDate = MyDate.strToUTCLong(classItem.getString("a2"), "yyyy-MM-dd");
				if(orderDate>=todayDate){
					targetCid = classItem.getString("a1");
				}
			}
			if(StringUtils.isEmpty(cid)){
				cid = targetCid;
			}
			Logger.getLogger(UserAction.class).info("final cid=" + cid);
			JSONObject ticket = userService.getTicket(id, cid, uid);
			if (ticket != null) {
				mv.addObject("ticket", formatTicket(ticket, order, cid));
			} else {
				mv.addObject("ticket", formatTicket(order, cid));
			}
			mv.addObject("lineDetail", lineService.getLineById(order.getString("a11"), order.getString("a12"), null));
		} catch(Exception exc) {
			JSONObject res = new JSONObject();
			res.put("a1", "小猪巴士");
			res.put("a2", "--:--");
			res.put("a3", "--");
			res.put("a4", "--.--");
			res.put("a5", "");
			res.put("a6", "");
			res.put("a7", "业务繁忙，请稍后查看车票。");
			res.put("a8", "0");
			mv.addObject("ticket", res.toString(2));
		}
		return mv;
	}
	
	private long todayDate(){
		return MyDate.strToUTCLong(MyDate.formatTime(System.currentTimeMillis(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}
	
	/** 等待支付的返回，因为支付返回不是同步的，会做5次尝试； */
	private JSONObject checkOrder(String id) {
		JSONObject order = null;
		int count = 5;
		try {
			while (order == null) {
				Thread.sleep(5000);
				order = userService.getOrder(id);
				if (count-- < 0)
					break;
			}
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		return order;
	}
	
	/** 格式化车票的输出格式(车票补完)； */
	private String formatTicket(JSONObject ticket, JSONObject order, String cid) {
		JSONObject res = new JSONObject();
		res.put("a1", ticket.getString("a11"));
		res.put("a2", order.getString("a5"));
		res.put("a3", order.getString("a7"));
		String time = getOrderTicketDate(order, cid);
		res.put("a4", formatTicketDate(time));
		res.put("a5", ticket.getString("a6"));
		res.put("a6", ticket.getString("a7"));
		res.put("a9", ticket.getString("a12"));
		res.put("a8", checkTicketShow(time + " " + order.getString("a5")));
		return res.toString(2);
	}
	
	/** 格式化车票的输出格式(订单 -> 车票)； */
	private String formatTicket(JSONObject order, String cid) {
		JSONObject res = new JSONObject();
		res.put("a1", "小猪巴士");
		res.put("a2", order.getString("a5"));
		res.put("a3", order.getString("a7"));
		String time = getOrderTicketDate(order, cid);
		res.put("a4", formatTicketDate(time));
		res.put("a5", order.getString("a9"));
		res.put("a6", order.getString("a10"));
		res.put("a8", 0);
		res.put("a9", "");
		return res.toString(2);
	}
	
	/** 检测车票是否显示； */
	private int checkTicketShow(String time) {
		try {
			JSONObject common = userService.getCommonData();
			int begin = -1800000, end = 7200000;
			if (common != null && common.has("a3")) {
				begin = common.getInt("a3") * -60000;
			}
			long cur = System.currentTimeMillis();
			long t = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time).getTime();
			long offset = cur - t;
			if (offset > begin && offset < end) {
				return 1;
			}
		} catch (ParseException e) {  
			System.out.println(e.getMessage());
		}
		return 0;
	}
	
	/** 获取车票时间； */
	private String getOrderTicketDate(JSONObject order, String cid) {
		JSONArray list = order.getJSONArray("list");
		if (cid == null) {
			return list.getJSONObject(0).getString("a2");
		}
		for (int i = 0; i < list.size(); i ++) {
			JSONObject ticket = list.getJSONObject(i);
			if (ticket.getString("a1").equals(cid)) {
				return ticket.getString("a2");
			}
		}
		return null;
	}
	
	/** 格式化输出车票时间； */
	private String formatTicketDate(String date) {
		if (StringUtils.isEmpty(date)) {
			return "--.--";
		}
		return date.substring(date.indexOf("-") + 1).replaceAll("-", ".");
	}
	
	/**
	 * 获取车票列表；
	 * @return String: 用户信息json;
	 */
	@RequestMapping(value = "/tickets", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String tickets(String page, String uid) {
		return userService.getTickets(uid, page).toString(2);
	}
	
	/**
	 * 发送短信验证码
	 * @return String: 用户信息json;
	 */
	@RequestMapping(value = "/sendMessageCode", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String sendMessionCode(String mobile, HttpServletResponse response) {
		if (StringUtils.isEmpty(mobile)) {
			return "-1";
		}
		return userService.sendMessionCode(mobile) ? "0" : "-1";
		/*
		// 自己发送验证码
		// 随机六位数
		String validateCode = RandomUtls.random6number();
		StringBuffer content = new StringBuffer();
		content.append("【小猪巴士】您的验证码为：");
		content.append(validateCode);
		content.append("，请勿泄露。");
		Cookie cookie = new Cookie(MSG_CODE, validateCode);
		response.addCookie(cookie);
		return SendMessageUtil.sendMessage(mobile, content.toString()) ? "0" : "-1";
		*/
	}
}
