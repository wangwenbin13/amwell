package com.amwell.commons;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MD5Util 
{
	/**
	 * 将字节数组进行MD5转换
	 * 
	 * @param data
	 *            字符数组
	 * @return
	 */
	public static byte[] md5(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");// 管理的是MD5加密
			md.update(data);// 更新指定的字节数组
			return md.digest();// 字节数组生成的散列值
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new byte[] {};
	}

	/**
	 * 将字符串转换为MD5
	 * 
	 * @param data
	 *            需要转换的字符串
	 * @return
	 */
	public static String md5(String data) {
		try {
			byte[] md5 = md5(data.getBytes("utf-8"));// 转换为MD5的字节数组
			return toHexString(md5).toUpperCase();// 返回16进制
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 转换为16进制字符串
	 * 
	 * @param md5
	 *            字节数组
	 * @return
	 */
	public static String toHexString(byte[] md5) {
		StringBuilder buf = new StringBuilder();
		for (byte b : md5) {
			buf.append(leftPad(Integer.toHexString(b & 0xff), '0', 2));
		}
		return buf.toString();
	}

	public static String leftPad(String hex, char c, int size) {
		char[] cs = new char[size];
		Arrays.fill(cs, c);
		System.arraycopy(hex.toCharArray(), 0, cs, cs.length - hex.length(),
				hex.length());
		return new String(cs);
	}

	public static void main(String[] args) {
		System.out.println(md5("张强"));
		// DD67A5943781369DDD7C594E231E9E70　
	}
}

