package com.service.v1;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.util.common.PostHttpClient;
import com.util.common.StringUtil;
import com.util.constants.PropertiesConfig;

/**
 *  /\_/\
 * (=+_+=)
 *   ( ).
 * @author sunjiaxiang
 * 貌似这是一个提供各种和线路有关数据获取处理的类哟。
 */
@Service
@Transactional
public class LocationService {
	
	// 搜索地点的api
	public static String FIND_CITY_API = "http://api.map.baidu.com/location/ip?ak=2zxiYgQK3DGAd2VS51sd4acL&coor=bd09ll&ip=";
	// 获取城市列表的api
	public static String CITY_LIST_API = "/app_lineInfo/getCityList.action";
	
	public List<String> getCitys() {
		StringBuffer url = new StringBuffer();
		url.append(PropertiesConfig.API_SERVER_URL);
		url.append(CITY_LIST_API);
		String result = PostHttpClient.getHttpReq(url.toString());
		JSONArray resultData = JSONArray.fromObject(result);
		List<String> list = new ArrayList<String>();
		for (int i = 0, n = resultData.size(); i < n; i ++) {
			JSONObject item = resultData.getJSONObject(i);
			if (item.has("a1")) {
				list.add(item.getString("a1"));
			}
		}
		return list;
	}
	
	/** 通过IP获取城市； */
	public String findCityByIp(String ip) {
		StringBuffer url = new StringBuffer(FIND_CITY_API);
		url.append("&ip=" + ip);
		try {
			String result = PostHttpClient.getHttpReq(url.toString());
			if (!StringUtils.isEmpty(result)) {
				JSONObject json = JSONObject.fromObject(result);
				if (json.containsKey("content")) {
					JSONObject content = json.getJSONObject("content");
					if (content.containsKey("address_detail")) {
						JSONObject address = content.getJSONObject("address_detail");
						if (address.containsKey("city")) {
							String city = content.getJSONObject("address_detail").getString("city");
							String cityValue = StringUtil.decodeUnicode(city);
							return cityValue;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "深圳市";
	}
}
