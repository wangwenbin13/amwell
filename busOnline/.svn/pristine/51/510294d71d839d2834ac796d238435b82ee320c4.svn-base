package com.action.v1.views;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.constants.PropertiesConfig;

/**
 *  /\_/\
 * (=+_+=)
 *   ( ).
 * @author sunjiaxiang
 * 貌似这是一个提供各种和线路有关的接口类哟。
 */
@Controller
@RequestMapping("/passengerInfo")
public class CSHAction {
	
	public static String CSH_CHANNEL = "CaiShengHuo";
	
	/**
	 * 分享到微信后的招募
	 * @param id：招募线路ID;
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/cshLogin")
	public void shareByApp(String mobile, String password, HttpServletResponse response) throws IOException {
		StringBuffer url = new StringBuffer();
		url.append(PropertiesConfig.SERVER_IP);
		url.append(PropertiesConfig.PROJECT_NAME);
		url.append("/v1/line/workday?client_id=");
		url.append(CSH_CHANNEL);
		url.append("&access_token=");
		url.append(mobile);
		url.append("&mobile=");
		url.append(mobile);
		response.sendRedirect(url.toString());
	}
}
