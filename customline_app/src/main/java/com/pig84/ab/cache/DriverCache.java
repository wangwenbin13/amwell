package com.pig84.ab.cache;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;

import com.pig84.ab.dao.impl.DriverDaoImpl;
import com.pig84.ab.utils.ApplicationContextHolder;
import com.pig84.ab.utils.CookieUtil;
import com.pig84.ab.utils.RedisUtil;
import com.pig84.ab.utils.Utils;
import com.pig84.ab.vo.Driver;
import com.pig84.ab.vo.driver.DriverInfoEntity;

/**
 * 处理司机登录信息cache
 * @author zhangqiang
 *
 */
public class DriverCache {

	private static final String KEY_PREFIX = "appDriver#";
	private static int COOKIE_MAXAGE = 31536000; //cookie最大有效时间（1年），以秒为单位
	private static int CACHE_MAXAGE = 1800; //cache最大有效时间(30分钟)，以秒为单位
	
	/**
	 * 存入appUser
	 * @param passenger
	 */
	public static void setDriver(DriverInfoEntity driver){
		try (Jedis cache = RedisUtil.getJedis()) {
			saveDriverCache(driver, cache);
		}
		String cookie = driver.getDriverId()+"#"+driver.getSessionid();
		CookieUtil.addCookie("did", cookie, COOKIE_MAXAGE);
	}
	
	/**
	 * 获取appUser
	 * @return
	 */
	public static Driver getDriver(){
		Cookie cookie = CookieUtil.getCookieByName("did");
		if (cookie == null) {
			return null;
		}
		String tokens[] = cookie.getValue().split("#");
		if (tokens.length != 2) {
			return null;
		}
		String driverId = tokens[0];
		String sessionId = tokens[1];
		
		try (Jedis cache = RedisUtil.getJedis()) {
			String cacheKey = KEY_PREFIX + driverId;
			Map<String, String> map = cache.hgetAll(cacheKey);
			
			if (map == null || map.isEmpty()) {
				DriverDaoImpl dao = ApplicationContextHolder.getBean(DriverDaoImpl.class);
				DriverInfoEntity driver = dao.getDriverById(driverId);
				
				if(driver==null){
					return null;
				}
				if(!StringUtils.equals(driver.getSessionid(), sessionId)){
					return null;
				}
				saveDriverCache(driver, cache);
				map = cache.hgetAll(cacheKey);
			}
			else if (!StringUtils.equals(map.get("sessionid"), sessionId)) {
				return null;
			}
			
			Driver user = new Driver();
			user.setDriverId(map.get("driverid"));
			user.setBusinessId(map.get("businessid"));
			user.setHeaderPicUrl(map.get("headerpicurl"));
			user.setSessionid(map.get("sessionid"));
			user.setTelephone(map.get("telephone"));
			user.setDriverName(map.get("drivername"));
			return user;
		}
	}
	
	
	private static void saveDriverCache(DriverInfoEntity driver, Jedis cache) {
		Map<String, String> user = new HashMap<String, String>();
		user.put("driverid",driver.getDriverId());
		user.put("telephone",driver.getTelephone());
		user.put("sessionid",driver.getSessionid());
		user.put("headerpicurl",driver.getHeaderPicUrl());
		user.put("businessid",driver.getBusinessId());
		user.put("drivername",driver.getDriverName());
		
		//存入cahce
		String key = KEY_PREFIX + user.get("driverid");
		cache.hmset(key, Utils.removeEmpty(user));
		cache.expire(key, CACHE_MAXAGE);
	}
}
