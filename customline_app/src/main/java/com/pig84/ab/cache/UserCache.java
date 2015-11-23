package com.pig84.ab.cache;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pig84.ab.dao.impl.PassengerInfoDaoImpl;
import com.pig84.ab.utils.ApplicationContextHolder;
import com.pig84.ab.utils.CookieUtil;
import com.pig84.ab.utils.RedisUtil;
import com.pig84.ab.utils.Utils;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.User;

import redis.clients.jedis.Jedis;

/**
 * 处理用户登录信息cache
 * @author zhangqiang
 *
 */
public class UserCache {

    private static Logger logger = LoggerFactory.getLogger(UserCache.class);
	private static final String KEY_PREFIX = "appUser#";
	private static int COOKIE_MAXAGE = 31536000; //cookie最大有效时间（1年），以秒为单位
	private static int CACHE_MAXAGE = 1800; //cache最大有效时间(30分钟)，以秒为单位
	
	/**
	 * 存入appUser
	 * @param passenger
	 */
	public static void setUser(PassengerInfo passenger){
		try (Jedis cache = RedisUtil.getJedis()) {
			savePassengerToCache(passenger, cache);
		}
		String cookie = passenger.getPassengerId()+"#"+passenger.getSessionId();
		CookieUtil.addCookie("sid", cookie, COOKIE_MAXAGE);
	}

	/**
	 * 获取appUser
	 * @return
	 */
	public static User getUser(){
		Cookie cookie = CookieUtil.getCookieByName("sid");
		if (cookie == null) {
			return null;
		}
		String tokens[] = cookie.getValue().split("#");
		if (tokens.length != 2) {
			return null;
		}
		String userId = tokens[0];
		String sessionId = tokens[1];
		try (Jedis cache = RedisUtil.getJedis()) {
			String cacheKey = KEY_PREFIX + userId;
			Map<String, String> map = cache.hgetAll(cacheKey);
			if (map == null || map.isEmpty()) {
				PassengerInfoDaoImpl dao = ApplicationContextHolder.getBean(PassengerInfoDaoImpl.class);
				PassengerInfo passenger = dao.getPassengerById(userId, null);
				if (passenger == null) {
					logger.warn("Passenger not found with ID: {}", userId);
					return null;
				}
				if (!StringUtils.equals(passenger.getSessionId(), sessionId)) {
					return null;
				}
				savePassengerToCache(passenger, cache);
				map = cache.hgetAll(cacheKey);
			}
			else if (!StringUtils.equals(map.get("sessionid"), sessionId)) {
				return null;
			}
			User user = new User();
			user.setOpenId(map.get("openid"));
			user.setPassengerId(map.get("passengerid"));
			user.setSessionId(map.get("sessionid"));
			user.setTelephone(map.get("telephone"));
			user.setHeaderPicUrl(map.get("headerpicurl"));
			user.setCaishenghuoremark(map.get("caishenghuoremark"));
			return user;
		}
	}
	
	private static void savePassengerToCache(PassengerInfo passenger, Jedis cache) {
		Map<String, String> user = new HashMap<String, String>();
		user.put("passengerid",passenger.getPassengerId());
		user.put("telephone",passenger.getTelephone());
		user.put("sessionid",passenger.getSessionId());
		user.put("openid",passenger.getOpenId());
		user.put("headerpicurl", passenger.getHeaderPicUrl());
		
		//存入cahce
		String key = KEY_PREFIX + user.get("passengerid");
		cache.hmset(key, Utils.removeEmpty(user));
		cache.expire(key, CACHE_MAXAGE);
	}

}
