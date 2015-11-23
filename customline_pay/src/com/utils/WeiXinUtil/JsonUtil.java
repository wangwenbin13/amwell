package com.utils.WeiXinUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

	public static String getJsonValue(String rescontent, String key) {
		try {
			return new JSONObject(rescontent).getString(key);
		} catch (JSONException e1) {
			return null;
		}
	}
}