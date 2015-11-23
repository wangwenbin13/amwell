package com.amwell.commons;

import java.security.MessageDigest;

public class EncryptionUtils {
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
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 测试加密的方法
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(encrypt("1232224").length());
		String currTimeStr = System.currentTimeMillis()+"";
		System.out.println(MD5Util.md5(currTimeStr).length());
	}
}
