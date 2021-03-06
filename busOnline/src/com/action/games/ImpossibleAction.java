package com.action.games;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.service.games.ImpossibleService;
import com.service.v1.LocationService;
import com.service.v1.UserService;
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
@RequestMapping("/games/impossible")
public class ImpossibleAction {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ImpossibleService impossibleService;
	
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
	@RequestMapping(value = "/index", produces = { "text/html;charset=utf-8" })
	public ModelAndView index(String code, String client_id, String access_token, String mobile, String singnature, 
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		ModelAndView mv = new ModelAndView("games/impossible");
		if (!userService.initUserInfo(code, client_id, access_token, singnature, mv) && !StringUtils.isEmpty(code)) {
			response.sendRedirect(WeixinMenuUtil.getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/games/impossible/index"));
			return null;
		}
		// 初始化所在城市
		String cityName = locationService.findCityByIp(request.getRemoteAddr());
		mv.addObject("cityName", cityName);
		mv.addObject("code", code);
		mv.addObject("isActivate", impossibleService.isActivate());
		return mv;
	}
	
	/** 检测用户是否参加游戏，如果未参加返回-1,参加返回对应结果； */
	@RequestMapping(value = "/login", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String saveResult(String uid, String tel, Integer prize) {
		impossibleService.saveResult(uid, tel, prize);
		return "1";
	}
	
	/** 检测用户是否参加游戏，如果未参加返回-1,参加返回对应结果； */
	@RequestMapping(value = "/result", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String checkResult(String uid) {
		int res = -1;
		if (!StringUtils.isEmpty(uid))
			res = impossibleService.checkResult(uid);
		JSONObject result = new JSONObject();
		if (res == -1) {
			result.put("isNew", 1);
			result.put("res", impossibleService.getResult());
		} else {
			result.put("isNew", 0);
			result.put("res", res);
		}
		return result.toString(2);
	}
}
