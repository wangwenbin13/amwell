package com.pig84.ab.utils;
import redis.clients.jedis.Jedis;

public class CacheUtil {
    /**
     * 存入cache
     * @param key
     * @param value
     * @param seconds
     */
    public static void setCache(String key,String value,int seconds){
    	try (Jedis jedis = RedisUtil.getJedis()) {
    		jedis.set(key, value);
    		jedis.expire(key, seconds);
    	}
    }
    
    
    /**
     * 取出cache
     * @param key
     * @return
     */
    public static String getCache(String key){
    	try (Jedis jedis = RedisUtil.getJedis()) {
    		return jedis.get(key);
    	}
    }
    
    /**
     * 删除cache
     * @param key
     */
    public static void removeCache(String key){
    	try (Jedis jedis = RedisUtil.getJedis()) {
    		jedis.del(key);
    	}
    }
    
    /**
     * 更新数据
     * @param key
     * @param value
     */
    public static void updateCache(String key,String value){
    	try (Jedis jedis = RedisUtil.getJedis()) {
    		jedis.set(key, value);
    	}
    }
    
 }
