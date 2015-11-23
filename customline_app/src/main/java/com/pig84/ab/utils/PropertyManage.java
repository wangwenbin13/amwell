package com.pig84.ab.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置管理类
 * @author zhangqiang
 */
public class PropertyManage {
	
	private static final String FILE = "info.properties";

	private static final Logger logger = LoggerFactory.getLogger(PropertyManage.class);
	
	private static Properties props = null;
	
	/**
	 * 获取info配置文件
	 * @return
	 */
	private static Properties parse()  {
		Properties props = new Properties();
		ClassLoader loader = PropertyManage.class.getClassLoader();
		try (InputStream is = loader.getResourceAsStream(FILE)) {
			props.load(is);
		} catch (IOException e) {
			logger.error("Load info.properties failed.", e);
		}
		return props ;
	}
	
	/**
	 * 从info配置文件中获取text
	 * @param name
	 * @return
	 */
	public static String get(String name) {
		if (props == null) {
			props = PropertyManage.parse();
		}
		Object v = props.get(name);
		return v == null ? null : v.toString();
	}
	
	public static int getInt(String name) {
		return Integer.parseInt(get(name));
	}
	
}

