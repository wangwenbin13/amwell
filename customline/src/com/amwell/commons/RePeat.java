package com.amwell.commons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author wangwenbin
 *
 */
public class RePeat {

	/**去重**/
	public static String quChong(String str){
		String valueString = "";
		Map<String,String> maps = new HashMap<String,String>();
		if(!StringUtils.isEmpty(str)){
			String strs[] = str.split(",");
			if(strs.length>0){
				for(String s:strs){
					if(!StringUtils.isEmpty(s)){
						maps.put(s, "1");
					}
				}
			}
			/**去重**/
			if(null!=maps){
				 //获取map集合中的所有键，存入到Set集合中，  
		        Set<Map.Entry<String,String>> entry = maps.entrySet(); 
		        //通过迭代器取出map中的键值关系，迭代器接收的泛型参数应和Set接收的一致  
		        Iterator<Map.Entry<String,String>> it = entry.iterator();  
		        while (it.hasNext()){  
		            //将键值关系取出存入Map.Entry这个映射关系集合接口中  
		            Map.Entry<String,String>  me = it.next();  
		            valueString += me.getKey()+",";
		        }
			}
		}
		return valueString;
	}
}
