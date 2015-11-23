package com.amwell.commons;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyDate {

	/**
	 * 以2006-11-05 11:05的形式返回时间
	 * 
	 * @return
	 */
	public static String getMyDate() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	public static String getMyDateLong() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	/**
	 * 以2006-11-05 11:05的形式返回时间
	 * 
	 * @return
	 */
	public static String getMyDateShort() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	/**
	 * 以11:05:33的形式返回时间
	 * 
	 * @return
	 */
	public static String getMyTime() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	/**
	 * 返回java.sql.Date类型的日期
	 * 
	 * @return
	 * @throws Exception
	 */
	public static java.sql.Date getMyDateD() throws Exception {

		String TimeString = "";
		java.util.Date st = new java.util.Date();
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowtime = new java.util.Date();
		TimeString = TimeFormat.format(nowtime);
		st = TimeFormat.parse(TimeString.toString());
		java.sql.Date tvalue = new java.sql.Date(st.getTime());
		return tvalue;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */

	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 转换Tue May 24 00:00:00 UTC +0800 2011 为正常格式
	 * 
	 * @param strDate
	 * @return
	 */
	public static String longDatetoStr(String strDate) {
		// strDate="Tue May 24 00:00:00 UTC +0800 2011";
		strDate = strDate.substring(0, strDate.indexOf("UTC") - 1);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss", Locale.US);
		// ParsePosition pos = new ParsePosition(0);
		Date strtodate = null;
		String TimeString = "";
		try {
			strtodate = sdf.parse(strDate);
			SimpleDateFormat TimeFormat = new SimpleDateFormat("MM-dd");
			TimeString = TimeFormat.format(strtodate);
			SimpleDateFormat TimeFormat1 = new SimpleDateFormat("yyyy");
			Date nowtime = new Date();
			TimeString = TimeFormat1.format(nowtime) + "-" + TimeString;
			return TimeString;
		} catch (Exception e) {
		}
		return TimeString;
	}

	// 11:05:33的形式返回时间
	public static String getMyDate5() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	/**
	 * 以yyyyMMddHHmm的格式返回
	 * 
	 * @return
	 */
	public static String getMyDate7() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	public static String getMyDate8() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	/**
	 * 以20061105的形式返回时间
	 * 
	 * @return
	 */
	public static String getMyDate2() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyyMMdd");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	// 将2006-11-05 11:05:33转换为20061105
	public static String changeStyle1(String dateString) {
		SimpleDateFormat TimeFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat TimeFormat2 = new SimpleDateFormat("yyyyMMdd");
		Date forTime = null;
		try {
			forTime = TimeFormat1.parse(dateString);
		} catch (Exception e) {
			;
		}
		dateString = TimeFormat2.format(forTime);
		return dateString;
	}

	// 将2006-11-05 11:05:33转换为2006-11-05
	public static String changeStyle2(String dateString) {
		SimpleDateFormat TimeFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat TimeFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		Date forTime = null;
		try {
			forTime = TimeFormat1.parse(dateString);
		} catch (Exception e) {
			;
		}
		dateString = TimeFormat2.format(forTime);
		return dateString;
	}

	/**
	 * 以2006-11-05的形式返回时间
	 * 
	 * @return
	 */
	public static String getMyDate3() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	/**
	 * 返回当前的月份
	 * 
	 * @return
	 */
	public static String getMonth() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyyMM");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	/**
	 * 返回当前年份
	 * 
	 * @return
	 */
	public static String getYear() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	// 以2006-11-05的形式返回时间的前一天时间
	public static String getMyDateBefore() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		String TimeString = "";
		Date nowtime = new Date();
		nowtime.setDate(nowtime.getDate() - 1);
		/*
		 * Calendar rightNow = Calendar.getInstance();
		 * rightNow.add(Calendar.DAY_OF_MONTH,-1);
		 */

		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	// 以20061105的形式返回时间的前一天时间
	public static String getMyDateBefore1() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyyMMdd");
		String TimeString = "";
		Date nowtime = new Date();
		nowtime.setDate(nowtime.getDate() - 1);
		/*
		 * Calendar rightNow = Calendar.getInstance();
		 * rightNow.add(Calendar.DAY_OF_MONTH,-1);
		 */

		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}

	/**
	 * 以20061105返回日期
	 * 
	 * @return
	 */
	public static String getMyDate4() {
		String TimeString = "";
		Calendar calendar = Calendar.getInstance();
		TimeString = String.valueOf(calendar.get(Calendar.YEAR));
		if (String.valueOf(calendar.get(Calendar.MONTH) + 1).length() == 1)
			TimeString += "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		else
			TimeString += String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).length() == 1)
			TimeString += "0" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		else
			TimeString += String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		return TimeString;
	}

	public static String getRandomFileName() {
		// 根据时间随机得文件名
		Calendar calendar = Calendar.getInstance();
		String randomFileName = String.valueOf(calendar.getTimeInMillis());
		return randomFileName;
	}

	// OFFSET表示与当天相差的分钟，取得与当前相隔多少分钟之前或之后的时间。因此offset也可以为负数
	public static String getPriorMinute(int offset) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar theday = Calendar.getInstance();
		theday.add(Calendar.MINUTE, offset);
		return TimeFormat.format(theday.getTime());
	}

	// OFFSET表示与当天相差的天数，取得与当前相隔多少天之前或之后的日期。因此offset也可以为负数
	public static String getPriorDay(int offset) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar theday = Calendar.getInstance();
		theday.add(Calendar.DATE, offset);
		return TimeFormat.format(theday.getTime());
	}

	// OFFSET表示与当天相差的月数
	public static String getPriorMonth(int offset) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar theday = Calendar.getInstance();
		theday.add(Calendar.MONTH, offset);
		return TimeFormat.format(theday.getTime());
	}

	// 获取指定时间是否是当前的n个分钟之前的后面,如首次充值时间是否在当前时间的一个月前的前面。addMinute可以为负数
	public static boolean isAfterNMinute(String datetime, int addMinute) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date forTime = null;
		try {
			forTime = TimeFormat.parse(datetime);
		} catch (Exception e) {
			;
		}
		Date aftTime = null;
		try {
			aftTime = TimeFormat.parse(getPriorMinute(addMinute));
		} catch (Exception e) {
			;
		}
		if (aftTime == null) {
			return false;
		}
		if (forTime.after(aftTime)) {
			return true;
		} else {
			return false;
		}
	}

	// 获取指定时间是否是当前的n个月之前的后面,如首次充值时间是否在当前时间的一个月前的前面。addMonth可以为负数
	public static boolean isAfterNMonth(String datetime, int addmonth) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date forTime = null;
		try {
			forTime = TimeFormat.parse(datetime);
		} catch (Exception e) {
			;
		}
		Date aftTime = null;
		try {
			aftTime = TimeFormat.parse(getPriorMonth(addmonth));
		} catch (Exception e) {
			;
		}
		if (aftTime == null) {
			return false;
		}
		if (forTime.after(aftTime)) {
			return true;
		} else {
			return false;
		}
	}

	// 以20070301获取本月的第一天
	public static String getFirstDayOfMonth() {
		String timeString = "";
		Calendar calendar = Calendar.getInstance();
		timeString = String.valueOf(calendar.get(Calendar.YEAR));
		if (String.valueOf(calendar.get(Calendar.MONTH) + 1).length() == 1)
			timeString += "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		else
			timeString += String.valueOf(calendar.get(Calendar.MONTH) + 1);
		timeString += "01";
		return timeString;
	}

	/**
	 * 按月份相加
	 * 
	 * @param str
	 *            数据库中存储的时间 yyyy-MM-dd HH:mm:ss
	 * @param month
	 *            要相加的月份
	 * @return
	 */

	public static String adddate(String str, String month) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = sdf.parse(str, new ParsePosition(0));
		int y = Integer.valueOf(month).intValue();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.MONTH, y);// 你要加减的日期
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

	/**
	 * 按月份相加
	 * 
	 * @param str
	 *            数据库中存储的时间 yyyy-MM-dd
	 * @param month
	 *            要相加的月份
	 * @return
	 */

	public static String adddate2(String str, String month) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = sdf.parse(str, new ParsePosition(0));
		int y = Integer.valueOf(month).intValue();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.MONTH, y);// 你要加减的日期
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

	public static Date getDate() {
		return new Date();
	}

	/**
	 * 相差天数
	 * 
	 * @param fDate
	 * @param oDate
	 *            现在
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}

	/**
	 * 将string类型的时间转换成Date类型 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param stringdate
	 * @return
	 */
	public static Date Stringtodate(String stringdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(stringdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将string类型的时间转换成Date类型 yyyy-MM-dd
	 * 
	 * @param stringdate
	 * @return
	 */
	public static Date Stringtodate2(String stringdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(stringdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * ID生成规则
	 * 
	 * @param
	 * @return
	 */
	public static String createzhuceid() {
		java.util.Date now = new java.util.Date();
		Random rd = new Random();
		SimpleDateFormat dd1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String st = "";
		for (int i = 0; i < 4; i++) {
			st += rd.nextInt(10);
		}
		return dd1.format(now) + st;
	}

	// 将unicode转换为中文
	public static String unescape(String src) {
		if (src == null)
			return "";
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	// 获取星座则是直接根据月份做索引，然后根据日期边界判断是本月的星座还是上月的。
	// 星座根据阳历判断,生肖根据农历判断
	public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };

	public static final String[] constellationArr = { "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
			"天蝎座", "射手座", "魔羯座" };

	public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };

	// 根据阳历取得的生肖
	/**
	 * 根据日期获取生肖
	 * 
	 * @return
	 */
	// public static String date2Zodica(Calendar time) {
	// return zodiacArr[time.get(Calendar.YEAR) % 12];
	// }

	/**
	 * 根据日期获取星座
	 * 
	 * @param time
	 * @return
	 */
	public static String date2Constellation(Calendar time) {
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return 魔羯
		return constellationArr[11];
	}

	// 阴历算法
	private int year;
	private int month;
	private int day;
	private boolean leap;
	final static String chineseNumber[] = { "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "腊" };
	final static String chineseNumber1[] = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二" };
	static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
	final static long[] lunarInfo = new long[] { 0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0,
			0x09ad0, 0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
			0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0,
			0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0,
			0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573,
			0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950,
			0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
			0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970,
			0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50,
			0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0,
			0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
			0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
			0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0 };

	// ====== 传回农历 y年的总天数
	final private static int yearDays(int y) {
		int i, sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((lunarInfo[y - 1900] & i) != 0)
				sum += 1;
		}
		return (sum + leapDays(y));
	}

	// ====== 传回农历 y年闰月的天数
	final private static int leapDays(int y) {
		if (leapMonth(y) != 0) {
			if ((lunarInfo[y - 1900] & 0x10000) != 0)
				return 30;
			else
				return 29;
		} else
			return 0;
	}

	// ====== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
	final private static int leapMonth(int y) {
		return (int) (lunarInfo[y - 1900] & 0xf);
	}

	// ====== 传回农历 y年m月的总天数
	final private static int monthDays(int y, int m) {
		if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
			return 29;
		else
			return 30;
	}

	// ====== 传回农历 y年的生肖
	final public String animalsYear() {
		final String[] Animals = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" };
		return Animals[(year - 4) % 12];
	}

	// ====== 传入 月日的offset 传回干支, 0=甲子
	final private static String cyclicalm(int num) {
		final String[] Gan = new String[] { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
		final String[] Zhi = new String[] { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥" };
		return (Gan[num % 10] + Zhi[num % 12]);
	}

	// ====== 传入 offset 传回干支, 0=甲子
	final public String cyclical() {
		int num = year - 1900 + 36;
		return (cyclicalm(num));
	}

	public static String getChinaDayString(int day) {
		String chineseTen[] = { "初", "十", "廿", "卅" };
		int n = day % 10 == 0 ? 9 : day % 10 - 1;
		if (day > 30)
			return "";
		if (day == 10)
			return "初十";
		else
			return chineseTen[day / 10] + chineseNumber1[n];
	}

	public String toString() {
		return (leap ? "闰" : "") + chineseNumber[month - 1] + "月" + getChinaDayString(day);
	}

	public String getChinaWeekdayString(String weekday) {
		if (weekday.equals("Mon"))
			return "一";
		if (weekday.equals("Tue"))
			return "二";
		if (weekday.equals("Wed"))
			return "三";
		if (weekday.equals("Thu"))
			return "四";
		if (weekday.equals("Fri"))
			return "五";
		if (weekday.equals("Sat"))
			return "六";
		if (weekday.equals("Sun"))
			return "日";
		else
			return "";

	}
	// public static void main(String[] args) throws ParseException{
	// Calendar today = Calendar.getInstance();
	// today.setTime(new java.util.Date());//加载当前日期
	// //today.setTime(chineseDateFormat.parse("2006年10月30日"));//加载自定义日期
	// MyDate mydate = new MyDate(today);
	// System.out.print(mydate.cyclical()+"年");//计算输出阴历年份
	// System.out.println(mydate.toString());//计算输出阴历日期
	// System.out.println(mydate.animalsYear());//计算输出属相
	// System.out.println(new java.sql.Date(today.getTime().getTime()));//输出阳历日期
	// System.out.println("星期"+mydate.getChinaWeekdayString(today.getTime().toString().substring(0,3)));//计算输出星期几
	// }
	//

	/**
	 * 得到星期几
	 * 
	 * @param djrq
	 * @return
	 */
	public static String getweekofday(String djrq) {
		final String dayNames[] = { "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();

		try {
			date = sdfInput.parse(djrq);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String result = dayNames[dayOfWeek - 1];

		return result;
	}

	/**
	 * 将时间转化为 多少 秒、分、时、天前模式，如（2分钟前、2小时前等）
	 * 
	 * @param time
	 *            时间
	 * @param format
	 *            格式
	 * @return
	 * @throws ParseException
	 */
	public static String showTime(String time, String format) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date ctime = sdf.parse(time);

		String r = "";
		if (ctime == null)
			return r;
		if (format == null)
			format = "yyyy-MM-dd HH:mm";

		long nowtimelong = System.currentTimeMillis();
		long ctimelong = ctime.getTime();
		long result = Math.abs(nowtimelong - ctimelong);

		if (result < 60000) // 秒
		{
			long seconds = result / 1000;
			r = seconds + "秒钟前";
		} else if (result >= 60000 && result < 3600000) // 分
		{
			long seconds = result / 60000;
			r = seconds + "分钟前";
		} else if (result >= 3600000 && result < 86400000) // 时
		{
			long seconds = result / 3600000;
			r = seconds + "小时前";
		} else if (result >= 86400000 && result < 2073600000) // 天
		{
			long seconds = result / 86400000;
			r = seconds + "天前";
		} else// 日期格式
		{
			r = ctime.toLocaleString();
		}
		return r;
	}

}
