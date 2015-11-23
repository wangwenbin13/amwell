package com.amwell.ftp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
	public static final String defaultCharset = "UTF-8";

	// return /2014/0309
	public static String datetimeFolder() {
		String n = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String[] dt = n.split("-");
		StringBuilder sb = new StringBuilder();
		sb.append("/");
		sb.append(dt[0]);
		sb.append("/");
		sb.append(dt[1]);
		sb.append(dt[2]);
		return sb.toString();
	}

	// return 20131012115212
	public static String datetimeFileName() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddHHmmss");
		String name = sdf.format(d);
		return name;
	}

	//

	// 获取文件名后缀
	public static String suffixFileName(String fileName) {
		int pos = fileName.lastIndexOf(".");
		String suffix = fileName.substring(pos, fileName.length());
		return suffix.trim();
	}

	public static String decodeToUTF8(String i) {
		try {
			return new String(i.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public static String decodeToUTF8(byte[] i) {
		try {
			return new String(i, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(i);
	}

	public static String URLDecodeToUTF8(String s) {
		try {
			return URLDecoder.decode(s, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public static String decode(ByteBuffer buffer, String code) {
		Charset charset = Charset.forName(code);
		CharsetDecoder cd = charset.newDecoder();
		CharBuffer charBuffer = null;
		try {
			charBuffer = cd.decode(buffer);
			return charBuffer.toString();
		} catch (CharacterCodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 判断字符串是否为空
	public static boolean empty(String s) {
		if (s == null || s.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	// 字符串转换为 _unicode
	public static String toUnicode(String s) {
		String as[] = new String[s.length()];
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			as[i] = Integer.toHexString(s.charAt(i) & 0xffff);
			s1 = s1 + "\\u" + as[i].toUpperCase();
		}
		return s1;
	}

}
