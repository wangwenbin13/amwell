package com.utils;

import com.google.gson.Gson;

/**
 * JSON Processor.
 * 
 * @author GuoLin
 */
public class Json {
	
	private static final Gson G = new Gson();
	
	public static String toJson(Object obj) {
		return obj == null ? "{}" : G.toJson(obj);
	}
	
	public static <T> T fromJson(String json, Class<T> clazz) {  
		return G.fromJson(json, clazz);
	}  
	
}
