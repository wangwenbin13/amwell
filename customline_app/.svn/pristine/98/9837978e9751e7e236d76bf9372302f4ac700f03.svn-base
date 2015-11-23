package com.pig84.ab.utils;
import java.util.Iterator;
import java.util.Map;

public class Utils {
	
	/**
	 * 去除map中的空对象
	 * @param map
	 * @return
	 */
	public static Map<String,String> removeEmpty(Map<String,String> map){
       Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();   
        while(it.hasNext()){   
            Map.Entry<String, String> entry=it.next();   
            String value=entry.getValue();   
            if("".equals(value) || value == null){   
                it.remove();         
            }   
        }   
		return map;
	}
	
 }
