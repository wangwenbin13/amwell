package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  @author:	zhangqiang
 *  @version:
 *	@function:	产生随机数字、随机字母、随机数字+字母。
 */
public class RandomUtil {
	
	/**
	 * 随机产生几位数字：例：maxLength=3,则结果可能是 012
	 */
	public static final int produceNumber(int maxLength){
		Random random = new Random();
		return random.nextInt(maxLength);
	}
	
	
	/**
	 * 随机产生区间数字：例：minNumber=1,maxNumber=2,则结果可能是 1、2,包括首尾。
	 */
	public static int produceRegionNumber(int minNumber,int maxNumber){
		return minNumber + produceNumber(maxNumber);
	}

	/**
	 * 随机产生几位字符串：例：maxLength=3,则结果可能是 aAz
	 * @param maxLength 传入数必须是正数。
	 */
	public static String produceString(int maxLength){
		String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		return doProduce(maxLength, source);
	}
	
	/**
	 * 随机产生随机数字+字母：例：maxLength=3,则结果可能是 1Az
	 * @param maxLength 传入数必须是正数。
	 */
	public static String produceStringAndNumber(int maxLength){
		String source = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		return doProduce(maxLength, source);
	}

	/**
	 * 自定义随机产生结果
	 */
	public static String produceResultByCustom(String customString,int maxLength){
		return doProduce(maxLength, customString);
	}
	
	/**
	 * 生产结果
	 */
	private static String doProduce(int maxLength, String source) {
		StringBuffer sb = new StringBuffer(100);
		for (int i = 0; i < maxLength; i++) {
			final int number =  produceNumber(source.length());
			sb.append(source.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 获取6位随机数
	 * @return
	 */
	public static String getRondomFor6(){
		List<Integer> list = new ArrayList<Integer>();
		Random random = new Random();
		while (true) {
		 int randomNum = random.nextInt(10);
		 boolean flag = false;
		 for(Integer in : list){
		 if(in == randomNum){
		 flag = true;
		 break;
		 }
		 }
		 if(!flag){
		 list.add(randomNum);
		 }
		 if(list.size()>=6){
		 break;
		 }
		}
		String randomStr = "";
		for (Integer in : list) {
		randomStr += in.toString();
		}
		return randomStr;
	}
	
	
	/**
	 * 获取随机数字
	 * @return
	 */
	public static String getRandomNum(){
		String randomNum = "";
		randomNum = StringUtil.objectToString(RandomUtil.produceRegionNumber(1000000000,999999999));
		return randomNum;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			System.out.println(RandomUtil.getRondomFor6());
		}

	}
}
