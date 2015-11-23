package com.pig84.ab.weixin.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static String getJsonValue(String rescontent, String key) {
		try {
			return new JSONObject(rescontent).getString(key);
		} catch (JSONException e1) {
			logger.warn("Parse content failed: " + rescontent, e1);
			return null;
		}
	}
}
