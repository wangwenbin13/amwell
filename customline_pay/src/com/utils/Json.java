package com.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.gson.Gson;

/**
 * JSON Processor.
 * 
 * @author GuoLin
 */
public class Json {
	
	private static final Gson G = new Gson();
	
	public static String toJson(Object obj) {
		setNullPropertyToEmptyString(obj);
		return obj == null ? "{}" : G.toJson(obj);
	}
	
	public static <T> T fromJson(String json, Class<T> clazz) {  
		return G.fromJson(json, clazz);
	}  

	private static void setNullPropertyToEmptyString(Object obj) {
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(obj.getClass());
		for (PropertyDescriptor pd : pds) {
			Method getter = pd.getReadMethod();
			Method setter = pd.getWriteMethod();
			if (getter == null || setter == null) {
				continue;
			}
			try {
				Object value = getter.invoke(obj);
				if (value == null) {
					setter.invoke(obj, "");
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				continue;
			}
		}
	}

}
