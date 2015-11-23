package com.amwell.commons.baiduAPI;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.amwell.commons.PostHttpClient;
import com.amwell.utils.StringUtils;
import com.google.gson.Gson;

public class BaiduAPIUtils {

	/**
	 * 百度开发者AK
	 */
	private static String BAIDU_AK = "F7357a7157ab851601e477dfb05cadb2";

	/**
	 * 百度坐标系转换（GPSto百度） HttpClient
	 */
	private static String BAIDU_GEOCONV = "http://api.map.baidu.com/geoconv/v1/?ak="
			+ BAIDU_AK + "&from=1&to=5";

	/**
	 * 将GPS坐标系转换为百度坐标系
	 * 
	 * @param lng
	 *            经度
	 * @param lat
	 *            纬度
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Point convertToBaidu(Double lng, Double lat) {
		Point point = null;
		try {
			String url = BAIDU_GEOCONV + "&coords=" + lng + "," + lat;
			String responseMsg = PostHttpClient.postHttpReq(url,new String());
			if (!StringUtils.isEmpty(responseMsg)) {
				Gson gson = new Gson();
				Map<String, Object> jsonData = gson.fromJson(responseMsg, Map.class); 
				double status = (Double)jsonData.get("status");
				if (status == 0) {
					List<Map<String,Object>> result = (List<Map<String,Object>>)jsonData.get("result");
					Map<String, Object> pointObject = result.get(0);
					point = new Point();
					point.setLng((Double)pointObject.get("x"));
					point.setLat((Double)pointObject.get("y"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return point;
	}

	/**
	 * 把百度坐标系转换为GPS坐标系
	 * 
	 * @param lng
	 *            经度
	 * @param lat
	 *            纬度
	 * @return
	 */
	public static Point convertToGPS(Double lng, Double lat) {

		Point targetPoint = new Point();
		targetPoint.setLat(0.0);
		targetPoint.setLng(0.0);
		try {
			Point middlePoint = convertToBaidu(lng, lat);
			if (middlePoint != null) {
				double targetLng = formatDouble(2 * lng - middlePoint.getLng());
				double targetLat = formatDouble(2 * lat - middlePoint.getLat());
				targetPoint = new Point();
				targetPoint.setLng(targetLng);
				targetPoint.setLat(targetLat);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return targetPoint;
	}

	public static Double formatDouble(Double value) {
		BigDecimal bg = new BigDecimal(value);
		double f1 = bg.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

	public static void main(String[] args) {
		Point point = BaiduAPIUtils.convertToBaidu(114.21892734521,
				29.575429778924);
		System.out.println(point.getLng());
		System.out.println(point.getLat());
		Point targetPoint = BaiduAPIUtils.convertToGPS(point.getLng(), point
				.getLat());
		System.out.println(targetPoint.getLng());
		System.out.println(targetPoint.getLat());
	}

}
