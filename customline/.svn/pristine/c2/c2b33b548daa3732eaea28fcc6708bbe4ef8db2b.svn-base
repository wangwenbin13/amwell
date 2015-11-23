package com.amwell.commons;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonUtil {
	public static Gson getGson() {
		return new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	}

	public static <T> List<T> toList(String json, Class<T> clazz) {
		JsonParser parser = new JsonParser();
		JsonElement e = parser.parse(json);
		JsonArray array = e.getAsJsonArray();
		List<T> list = new ArrayList<T>();
		for (JsonElement ee : array) {
			T r = GsonUtil.getGson().fromJson(ee, clazz);
			list.add(r);
		}
		return list;
	}

	public static <T> T toObj(String json, Class<T> clazz) {
		JsonParser parser = new JsonParser();
		JsonElement e = parser.parse(json);
		return GsonUtil.getGson().fromJson(e, clazz);
	}

	/**
	 * 得到格式化json数据 退格用\t 换行用\r
	 */
	public static String format(String jsonStr) {
		int level = 0;
		StringBuffer jsonForMatStr = new StringBuffer();
		for (int i = 0; i < jsonStr.length(); i++) {
			char c = jsonStr.charAt(i);
			if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
				jsonForMatStr.append(getLevelStr(level));
			}
			switch (c) {
			case '{':
			case '[':
				jsonForMatStr.append(c + "\n");
				level++;
				break;
			case ',':
				jsonForMatStr.append(c + "\n");
				break;
			case '}':
			case ']':
				jsonForMatStr.append("\n");
				level--;
				jsonForMatStr.append(getLevelStr(level));
				jsonForMatStr.append(c);
				break;
			default:
				jsonForMatStr.append(c);
				break;
			}
		}

		return jsonForMatStr.toString();

	}

	private static String getLevelStr(int level) {
		StringBuffer levelStr = new StringBuffer();
		for (int levelI = 0; levelI < level; levelI++) {
			levelStr.append("\t");
		}
		return levelStr.toString();
	}
}
