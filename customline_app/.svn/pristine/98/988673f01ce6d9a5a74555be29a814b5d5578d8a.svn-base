package com.pig84.ab.utils;

import java.security.MessageDigest;

public class Sha1Utils {
	/**
	 * sha1加密
	 * 
	 * @author hutao
	 * @version V1.0 @2014-6-11[版本号, YYYY-MM-DD]
	 * @see str:传入的参数
	 * 
	 * @since Application Service System.
	 */
	public static String encrypt(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(str.getBytes("UTF-8"));
			byte[] result = digest.digest();
			StringBuffer buf = new StringBuffer();
			for (byte b : result) {
				int i = b & 0xff;
				if (i < 0xf) {
					buf.append(0);
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().toUpperCase();
		} catch (Exception e) {
			// Safe to ignore
		}
		return null;
	}
}
