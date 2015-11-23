package com.amwell.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.bind.PropertyException;

/**
 * 配置管理类
 * @author zhangqiang
 */
public class PropertyManage {
	
	/**
	 * 获取默认日志配置
	 * @return
	 * @throws PropertyException 
	 * @throws IOException 
	 * @throws PropertyException 
	 */
	public static Properties getLogProperty()  {
		Properties prop = new Properties(); ;
		InputStream is = PropertyManage.class.getClassLoader().getResourceAsStream("log4j.properties");    
		try {
			prop.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return prop ;
	}
	/**
	 * 获取info配置文件
	 * @return
	 */
	public static Properties getInfoProperty()  {
		Properties prop = new Properties(); ;
		InputStream is = PropertyManage.class.getClassLoader().getResourceAsStream("info.properties");    
		try {
			prop.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return prop ;
	}
	
	/**
	 * 从info配置文件中获取text
	 * @param configName
	 * @return
	 */
	public static String getInfoProperty(String configName){
		Properties properties = PropertyManage.getInfoProperty();
		Object configValue = properties.get(configName);
		return StringUtil.objectToString(configValue);
	}
}

