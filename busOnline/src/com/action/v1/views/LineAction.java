package com.action.v1.views;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.service.v1.LineService;
import com.service.v1.LocationService;
import com.service.v1.UserService;
import com.util.constants.ErrorCode;
import com.util.constants.PropertiesConfig;
import com.util.weixin.WeixinMenuUtil;

/**
 *  /\_/\
 * (=+_+=)
 *   ( ).
 * @author sunjiaxiang
 * 貌似这是一个提供各种和线路有关的接口类哟。
 */
@Controller
@RequestMapping("/v1/line")
public class LineAction {
	
	public final String RESULT_JSON_KEY_STATUS = "a1";
	public final String RESULT_JSON_KEY_DATA = "a2";
	
	@Autowired
	private LineService lineService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LocationService locationService;
	
	/**
	 * 前往上下班页面
	 * 微信相关
	 * @param code 微信授权码
	 * 第三方合作相关
	 * @param client_id: 第三方合作用的渠道ID;
	 * @param access_token: 第三方合作用的用户唯一标识;
	 * @param client_id: 第三方合作用的手机号码;
	 * @param openId: 这个玩意纯属为了测试；
	 * @param lon：经度
	 * @param lat：纬度
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/workday", produces = { "text/html;charset=utf-8" })
	public ModelAndView workday(String code, String client_id, String access_token, String mobile, 
			String lon, String lat, String singnature,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		ModelAndView mv = new ModelAndView("v1/workday");
		if (!userService.initUserInfo(code, client_id, access_token, singnature, mv) && !StringUtils.isEmpty(code)) {
			response.sendRedirect(WeixinMenuUtil.getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/v1/line/workday"));
			return null;
		}
		
		String sid = (String) mv.getModel().get("sid");
		response.addCookie(new Cookie("sid", sid));
		
		if (!StringUtils.isEmpty(lon) && !StringUtils.isEmpty(lat)) {
			mv.addObject("lon", lon);
			mv.addObject("lat", lat);
		}
		// 初始化所在城市
		String cityName = locationService.findCityByIp(request.getRemoteAddr());
		mv.addObject("cityName", cityName);
		mv.addObject("code", code);
		return mv;
	}
	
	/**
	 * 分享到微信后的招募
	 * @param id：招募线路ID;
	 * @return
	 */
	@RequestMapping("/shareByApp")
	public ModelAndView shareByApp(String id) {
		ModelAndView mv = new ModelAndView("v1/appShare");
		try {
			mv.addObject("lineInfo", lineService.getSignupLine(id).toString(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/** 招募报名； */
	@RequestMapping(value = "/signupLine", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String signupLine(String id, String uid) {
		try {
			return lineService.signupLine(id, uid) ? "" : "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";
	}
	
	/**
	 * 微信班次列表
	 * @param id: 线路Id
	 * @param sid: 拆分Id
	 * @param uid: 用户Id
	 * @return ModelAndView
	 */
	@RequestMapping("/detail")
	public ModelAndView detail(String id, String sid, String uid) {
		if (StringUtils.isEmpty(id)) {
			throw new RuntimeException("id is null");
		}
		ModelAndView mv = new ModelAndView("v1/lineDetail");
		// 获取线路详情
		mv.addObject("lineInfo", lineService.getLineDetailById(id, sid, uid));
		return mv;
	}
	
	/**
	 * 一个包车的入口页面；
	 * @return ModelAndView
	 */
	@RequestMapping("/baoche")
	public ModelAndView baoche(String id, String sid, String uid) {
		return new ModelAndView("v1/baoche");
	}
	
	/**
	 * 通过关键词获取可用站点列表
	 * @param kw: 关键词
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/getStations", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String getAvailableStationList(String kw, String city) throws JSONException {
		JSONObject resultData = new JSONObject();
		try {
			net.sf.json.JSONArray availableStationList = lineService.getStationsByKeyword(kw, city);
			resultData.put("a1", ErrorCode.SUCCESS);
			resultData.put("a2", availableStationList);
		} catch (Exception e) {
			e.printStackTrace();
			resultData.put("a1", ErrorCode.OTHER_ERROR);
		}
		return resultData.toString(2);
	}
	
	/**
	 * 加载线路途径站点
	 * @param id : 线路id
	 */
	@RequestMapping(value = "/getStationByLine", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String getStationByLineBaseId(String id) {
		StringBuffer lineInfo = new StringBuffer();
		try {
			String result = lineService.getLineById(id, null, null);
			if (StringUtils.isEmpty(result))
				throw new Exception("线路信息不存在");
			JSONObject object = JSONObject.fromObject(result);
			JSONArray sites = object.getJSONArray("list");
			for (int i = 0, n = sites.size(); i < n; ) {
				JSONObject site = sites.getJSONObject(i);
				lineInfo.append("<div style='float:left;'>");
				lineInfo.append(site.getString("a2"));
				if (++i < n)
					lineInfo.append("&nbsp;&gt;&nbsp;");
				lineInfo.append("</div>");
			}
			lineInfo.append("<div style='clear:both;'></div>");
		} catch (Exception e1) {
			e1.printStackTrace();
			lineInfo.append(e1.getMessage());
		}
		return lineInfo.toString();
	}
	
	/**
	 * 获取上下班线路（分页）
	 * @param lineSubType: 子线路类型
	 * @param cityName: 城市名称
	 * @param currentPage: 当前页
	 * @param startStation: 起始站（途经点站）
	 * @param endStation: 终点站
	 */
	@RequestMapping(value = "/getLines", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String toList(String lineSubType, String cityName, String lat, String lon, String elat, String elon,
			HttpServletRequest request, Integer currentPage, String startStation, String endStation) throws JSONException {
		JSONObject resultData = new JSONObject();
		try {
			// 城市名称转换；
			cityName = URLDecoder.decode(cityName, "utf-8");
			JSONArray listArr = null;
			if (StringUtils.isEmpty(startStation) && StringUtils.isEmpty(endStation)) {
				listArr = JSONArray.fromObject(lineService.getLineByPoint(cityName, currentPage + "", lat, lon));
			} else {
				listArr = JSONArray.fromObject(lineService.getLineByStation(cityName, currentPage + "", startStation, lat, lon, endStation, elat, elon));
			}
			JSONObject infoData = new JSONObject();
			infoData.put("page", currentPage);
			infoData.put("type", lineSubType);
			infoData.put("list", listArr);
			
			resultData.put(RESULT_JSON_KEY_STATUS, ErrorCode.SUCCESS);
			resultData.put(RESULT_JSON_KEY_DATA, infoData);
		} catch (Exception e1) {
			e1.printStackTrace();
			resultData.put(RESULT_JSON_KEY_STATUS, ErrorCode.OTHER_ERROR);
		}
		return resultData.toString(2);
	}
}
