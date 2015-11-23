/**
 * 文件名：com.amwell.utils.RandomUtls.java
 * 版权：Copyright 2003 amwell.Shenzhen, Inc. All rights reserved.
 * 创建人：Ryan.Bian
 * 创建时间：2014-4-9
 * 修改内容：新增
 * 修改内容：
 */
package com.util.common;

import java.util.Random;

/**
 * 随机数生成规则
 * 
 * @author Ryan.Bian
 * @version V1.0 @2014-4-9[版本号, YYYY-MM-DD]
 * @see [相关类/方法]
 * @since Application Service System.
 */
public class RandomUtls {

	/**
	 * 获取六位随机数
	 * 
	 * @return
	 */
	public static String random6number() {
		Random random = new Random();
		int n = 0;
		while (n < 100000 || !handle(n, 6)) {
			n = random.nextInt(1000000);
		}
		return String.valueOf(n);
	}
	
	public static boolean handle(int n, int size) {
		int[] list = new int[size];
		for (int i = 0; i < size; i++) {
			list[i] = n % 10;
			n = n / 10;
		}
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (list[i] == list[j])
					return false;
			}
		}
		return true;
	}

	public static boolean handle(int n) {
		int[] list = new int[5];
		for (int i = 0; i < 5; i++) {
			list[i] = n % 10;
			n = n / 10;
		}
		for (int i = 0; i < 5; i++) {
			for (int j = i + 1; j < 5; j++) {
				if (list[i] == list[j])
					return false;
			}
		}
		return true;
	}

	public static String orderNumber() {
		String number = MyDate.formatTime(System.currentTimeMillis(),
				"yyyyMMdd")
				+ random6number();
		return number;

	}

	/**
	 * 产生0～max的随机整数 包括0 不包括max
	 * 
	 * @param max
	 *            随机数的上限
	 * @return
	 */
	public static long getRandomLong(long max) {
		long randNum = (long) (Math.random() * max);// + minId;
		return randNum;
	}

	/**
	 * 产生 min～max的随机整数 包括 min 但不包括 max
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static long getRandomLong(long min, long max) {
		long r = getRandomLong(max - min);
		return r + min;
	}
}
