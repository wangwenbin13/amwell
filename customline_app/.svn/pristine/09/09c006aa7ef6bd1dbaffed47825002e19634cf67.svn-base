package com.pig84.ab.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {

	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	// Redis服务器IP
	private static String ADDR = PropertyManage.get("redis.addr");

	// Redis的端口号
	private static int PORT = Integer.valueOf(PropertyManage.get("redis.port"));

	// 访问密码
	private static String AUTH = PropertyManage.get("redis.auth");

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = Integer.valueOf(PropertyManage.get("redis.max_idle"));

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int max_tolal = Integer.valueOf(PropertyManage.get("redis.max_tolal"));

	private static int TIMEOUT = Integer.valueOf(PropertyManage.get("redis.timeout"));

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(max_tolal);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(TIMEOUT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
		} catch (Exception e) {
			logger.error("Initialize Jedis pool failed.", e);
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public static Jedis getJedis() {
		return jedisPool != null ? jedisPool.getResource() : null;
	}
}
