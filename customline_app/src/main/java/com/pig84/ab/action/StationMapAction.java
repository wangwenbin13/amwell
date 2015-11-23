package com.pig84.ab.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pig84.ab.cache.UserCache;
import com.pig84.ab.service.ILoginAndRegisterService;
import com.pig84.ab.service.IMapService;
import com.pig84.ab.utils.Env;
import com.pig84.ab.utils.Http;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.Coord;
import com.pig84.ab.vo.User;
import com.pig84.ab.vo.bean.AppVo_1_list;
import com.pig84.ab.vo.bean.AppVo_5;

/**
 * 站点地图.
 * 
 * @author wangwenbin
 *
 * 2015-3-23
 */
@ParentPackage("no-interceptor")
@Namespace("/app_stationMap")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class StationMapAction extends BaseAction {

	@Autowired
	private IMapService mapService;

	@Autowired
	private ILoginAndRegisterService loginAndRegisterService;
	
	private static final JsonParser parser = new JsonParser();

	/**
	 * 获取某个点某个范围内的附近站点位置点
	 * 
	 * @throws IOException
	 * @throws IOException
	 */
	@Action(value = "queryNearStation", results = { @Result(type = "json") })
	public String queryNearStation() throws IOException {

		String lon = request.getParameter("lon");// 经度
		String lat = request.getParameter("lat");// 纬度
		Coord coord = Coord.valueOf(lon, lat);
		if (coord == null) {
			write();
			return null;
		}
		String cityName = request.getParameter("cityName");// 城市名称
		if (cityName == null || "".equals(cityName)) {
			cityName = Env.DEFAULT_CITYNAME;
		}

		User appUser = UserCache.getUser();// 当前登录用户信息
		String userid = null;
		boolean flag = false;
		if (null != appUser && !StringUtils.isEmpty(appUser.getPassengerId())) {
			userid = appUser.getPassengerId();
			/** 判断是不是华为员工 **/
			flag = loginAndRegisterService
					.judgeUserType(appUser.getTelephone());
		}

		AppVo_1_list apps = mapService.queryNearStation(coord, cityName.replace("市", ""), userid, flag);
		write(apps);
		return null;
	}

	/** 根据城市，站点关键字获取已开通的具体站点的名字和对应的经纬度 **/
	@Action(value = "queryStationsByStation", results = { @Result(type = "json") })
	public String queryStationsByStation() throws IOException {
		String stationName = request.getParameter("stationName");// 站点名称--关键字
		String cityName = request.getParameter("cityName");// 城市名称
		if (StringUtils.isEmpty(cityName)) {
			cityName = Env.DEFAULT_CITYNAME;
		}
		if (StringUtils.isEmpty(stationName)) {
			write("[]");
			return null;
		}
		stationName = stationName.trim().replace("'", "");
		List<AppVo_5> stations = findStationsOnBaidu(stationName, cityName, 20);
		write(stations);
		return null;
	}
	
	@Action(value = "queryGoogleStationsByName", results = { @Result(type = "json") })
	public String queryGoogleStationsByName() throws IOException {
		String location = request.getParameter("location");
		String query = request.getParameter("query");
		List<AppVo_5> stations = findStationsOnGoogle(location, query);
		write(stations);
		return null;
	}

	private List<AppVo_5> findStationsOnGoogle(String location, String query) {
		try {
			query = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// Safe ignore
		}
		String url = PropertyManage.get("google_place_url");
		String result = Http.getWithSSL(url, "query", query, "location", location);
		
		List<AppVo_5> stations = new ArrayList<AppVo_5>();
		
		try {
			JsonObject root = parser.parse(result).getAsJsonObject();
			JsonArray arr = root.get("results").getAsJsonArray();
			for (int i = 0; i < arr.size(); i++) {
				JsonElement el = arr.get(i);
				if (!el.isJsonObject()) {
					continue;
				}
				JsonObject o = el.getAsJsonObject();

				String name = o.get("name").getAsString(); // 名字
				String address = o.get("formatted_address").getAsString();// 地址 formatted_address
				if (!o.get("geometry").isJsonObject()) {
					continue;
				}
				JsonObject geo = o.get("geometry").getAsJsonObject();
				if (!geo.get("location").isJsonObject()) {
					continue;
				}
				JsonObject loc = geo.get("location").getAsJsonObject();
				String lat = loc.get("lat").isJsonPrimitive() ? loc.get("lat").getAsString() : "0";
				String lng = loc.get("lng").isJsonPrimitive() ? loc.get("lng").getAsString() : "0";

				AppVo_5 vo = new AppVo_5();
				vo.setA1(name);
				vo.setA2(lng);
				vo.setA3(lat);
				vo.setA4(address);
				vo.setA5("0");
				stations.add(vo);
			}
		} catch (Exception e) {
			logger.error("Find stations from Google failed.", e);
		}
		return stations;
	}

	private List<AppVo_5> findStationsOnBaidu(String stationName, String cityName, int count) {
		try {
			stationName = URLEncoder.encode(stationName, "UTF-8");
			cityName = URLEncoder.encode(cityName, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// Safe ignore
		}
		String url = PropertyManage.get("search_by_region_suggestion");
		String result = Http.get(url, "query", stationName, "region", cityName);
		
		List<AppVo_5> stations = new ArrayList<AppVo_5>();// count个
		try {
			JsonObject root = parser.parse(result).getAsJsonObject();
			JsonArray arr = root.getAsJsonArray("result");

			int needCount = 0; // 需要取的数量
			/** 判断返回的数组是否大于count **/
			if (count >= arr.size()) {
				/** 全部取百度返回的值 **/
				needCount = arr.size();
			} else {
				/** 取count条数据 **/
				needCount = count;
			}
			for (int i = 0; i < needCount; i++) {
				JsonElement el = arr.get(i);
				if (!el.isJsonObject()) {
					continue;
				}
				JsonObject o = el.getAsJsonObject();
				String name = o.get("name").getAsString(); // 站点名字
				String address = null;
				if(null!=o.get("address")){
					address = o.get("address").getAsString(); // 地址
				}
				if (StringUtils.isEmpty(address)) {
					address = o.get("city").getAsString() + o.get("district").getAsString();
				}
				if (null==o.get("location") || !o.get("location").isJsonObject()) {
					continue;
				}
				JsonObject loc = o.get("location").getAsJsonObject();
				JsonElement jsonLat = loc.get("lat");
				JsonElement jsonLng = loc.get("lng");
				String lat = jsonLat.isJsonPrimitive() ? jsonLat.getAsString() : "0"; // 纬度
				String lng = jsonLng.isJsonPrimitive() ? jsonLng.getAsString() : "0"; // 经度

				AppVo_5 vo = new AppVo_5();
				vo.setA1(name);
				vo.setA2(lng);
				vo.setA3(lat);
				vo.setA4(address);
				vo.setA5("0");
				stations.add(vo);
			}
		} catch (Exception e) {
			logger.error("Find stations from Baidu failed.", e);
		}
		return stations;
	}

}
