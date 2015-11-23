package com.amwell.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlUtils {

	public static final String CHARSET_UTF_8 = "utf-8";

	private UrlUtils() {

	}

	public static String encode(String s, String charset) {
		if (null == s) {
			return s;
		}
		try {
			return URLEncoder.encode(s, charset);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	public static String decode(String s, String charset) {
		if (null == s) {
			return s;
		}
		try {
			return URLDecoder.decode(s, charset);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

}
