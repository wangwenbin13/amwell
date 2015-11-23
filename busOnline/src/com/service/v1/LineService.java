package com.service.v1;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.util.common.PostHttpClient;
import com.util.constants.PropertiesConfig;

/**
 * /\_/\ (=+_+=) ( ).
 * 
 * @author sunjiaxiang 貌似这是一个提供各种和线路有关数据获取处理的类哟。
 */
@Service
@Transactional
public class LineService {

	// 搜索地点的api
	public static String SEARCH_STATION_BY_CITY_API = "/app_stationMap/queryStationsByStation.action";
	// 搜索目标点附近车次的api
	public static String SEARCH_KEYWORKS_LINE_API = "/app_lineInfo/searchLine.action?lineType=1&keywords=";
	// 站点详情
	public static String STATION_DETAIL_API = "/app_lineInfo/stationInfo.action";
	// 获取线路信息的API地址；
	public static String LINE_DETAIL = "/app_book/getLineAndClassInfo.action";
	// 通过坐标获取线路信息
	public static String LINE_POINT_API = "/app_lineInfo/getHistoryLine.action";
	// 获取线路
	public static String LINE_LISTS_API = "/app_lineInfo/getLineList.action";

	// 线路搜索；
	public static String LINE_SEARCH_API = "/app_lineInfo/queryLineAndUserAppListByPage.action";
	// 附近站点搜索
	public static String LINE_NEAR_SEARCH_API = "/app_lineInfo/getNearSplitLine.action";

	// 招募站点详情
	public static String LINE_SIGNUP_DETAIL_API = "/app_lineInfo/enrollLineInfo.action";
	// 招募找点报名；
	public static String LINE_SIGNUP_API = "/app_lineInfo/enrollLine.action";

	/** 获取招募线路信息； */
	public JSONObject getSignupLine(String id) throws Exception {
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("lineId", id);
		JSONObject res = JSONObject.fromObject(
				PostHttpClient.sendPost(PropertiesConfig.API_SERVER_URL + LINE_SIGNUP_DETAIL_API, postParam));
		res.put("a1", id);
		return res;
	}

	/** 招募线路报名； */
	public boolean signupLine(String id, String uid) throws Exception {
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("lineId", id);
		String result = PostHttpClient.sendPost(PropertiesConfig.API_SERVER_URL + LINE_SIGNUP_API, postParam);
		return result.indexOf("0") == -1;
	}

	/** 通过ID获取线路信息 */
	public String getLineById(String id, String sid, String uid) {
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("lineBaseId", id);
		postParam.put("slineId", sid);
		postParam.put("uid", uid);
		try {
			return PostHttpClient.sendPost(PropertiesConfig.API_SERVER_URL + LINE_DETAIL, postParam);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 搜索站点信息； */
	public JSONArray getStationsByKeyword(String keyword, String city) {
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("cityName", city);
		postParam.put("stationName", keyword);
		try {
			String result = PostHttpClient.sendPost(PropertiesConfig.API_SERVER_URL + SEARCH_STATION_BY_CITY_API,
					postParam);
			return JSONArray.fromObject(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 线路的格式化输出，针对通过站点和通过位置获取来的线路信息不同； 返回数据格式： a1: 线路ID a2: 起点名称 a3: 终点名称 a4: 价格
	 * a5: 行使时间 a6: 发车时间
	 * 
	 * @throws JSONException
	 */
	private JSONArray foramtLine(JSONArray lines, int type) throws JSONException {
		JSONArray out = new JSONArray();
		int i = 0, n = lines.size();
		while (i < n) {
			// 在循环中开启try会很消耗性能，用大循环进行开启，可以减少try块的消耗；
			try {
				for (; i < n; i++) {
					JSONObject infoData = lines.getJSONObject(i);
					if (infoData.has("a12") && infoData.getInt("a12") == 2) {
						continue;
					}
					JSONObject outInfo = new JSONObject();
					outInfo.put("a1", infoData.getString("a1"));
					outInfo.put("a7", infoData.getString("a9"));
					if (type == 1) {
						outInfo.put("a2", infoData.getString("a2"));
						outInfo.put("a3", infoData.getString("a3"));
						outInfo.put("a4", infoData.getString("a4"));
						outInfo.put("a5", infoData.getString("a5"));
						outInfo.put("a6", infoData.getString("a12"));
					} else if (type == 2) {
						outInfo.put("a2", infoData.getString("a5"));
						outInfo.put("a3", infoData.getString("a6"));
						outInfo.put("a4", infoData.getString("a4"));
						outInfo.put("a5", infoData.getString("a8"));
						outInfo.put("a6", infoData.getString("a3"));
					} else {
						outInfo.put("a2", infoData.getString("a5"));
						outInfo.put("a3", infoData.getString("a7"));
						outInfo.put("a4", infoData.getString("a11"));
						outInfo.put("a5", infoData.getString("a10"));
						outInfo.put("a6", infoData.getString("a3"));
					}
					out.add(outInfo);
				}
			} catch (Exception exc) {
				// 服务端数据不稳定会导致线路列表出不来；
				i++;
			}
		}
		return out;
	}

	/** 用站点获取线路； */
	public String getLineByStation(String city, String page, String start, String lat, String lon, String end,
			String eLat, String eLon) {
		JSONArray resArr = new JSONArray();
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("cityName", city);
		postParam.put("sName", start);
		postParam.put("eName", end);
		postParam.put("sLat", lat);
		postParam.put("sLon", lon);
		postParam.put("eLat", eLat);
		postParam.put("eLon", eLon);
		postParam.put("comeOrBack", "1");
		try {
			String result = PostHttpClient.sendPost(PropertiesConfig.API_SERVER_URL + LINE_SEARCH_API, postParam);
			JSONArray goArr = foramtLine(JSONObject.fromObject(result).getJSONArray("list"), 2);
			resArr.addAll(goArr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		postParam.put("comeOrBack", "2");
		try {
			String result = PostHttpClient.sendPost(PropertiesConfig.API_SERVER_URL + LINE_SEARCH_API, postParam);
			JSONArray backArr = foramtLine(JSONObject.fromObject(result).getJSONArray("list"), 2);
			resArr.addAll(backArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resArr.toString(2);
	}

	/** 用经纬度获取线路； */
	public String getLineByPoint(String city, String page, String lat, String lon) {
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("cityName", city);
		postParam.put("currentPage", page);
		postParam.put("pageSize", "5");
		postParam.put("lat", lat);
		postParam.put("lon", lon);
		try {
			String result = PostHttpClient.sendPost(PropertiesConfig.API_SERVER_URL + LINE_NEAR_SEARCH_API, postParam);
			Logger.getLogger(LineService.class).info("result=" + result);
			return foramtLine(JSONArray.fromObject(result), 2).toString(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 获取线路以及其相关信息
	 * 
	 * @param lineBaseId:
	 *            线路Id
	 * @return LineBaseInfo
	 */
	public String getLineDetailById(String id, String sid, String uid) {
		// sjx: 这里是获取了线路信息以后进行处理；
		if (StringUtils.isEmpty(id)) {
			throw new RuntimeException("id is null");
		}
		String lineDetal = getLineById(id, sid, uid);
		return lineDetal;
	}

}
