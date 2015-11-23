package com.amwell.commons;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工具类：日期处理类
 * 
 * @author zhangqiang
 */
public class DateUtil {
	private static String DateTimeFormat = "yyyy-MM-dd";
	private static String DateAllFormat = "yyyy-MM-dd HH:mm:ss";
	private static String TimeFormat = "HH:mm:ss";

	public DateUtil() {

	}

	/**
	 * 获取当前系统日期
	 * 
	 * @return 返回当前系统时间,精确到日.
	 */
	public static Date getSystemDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		Date d = new Date(cal.getTimeInMillis());
		return d;
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @return 返回当前系统时间,精确到毫秒
	 */
	public static Date getSystemDateTime() {
		Calendar cal = Calendar.getInstance();
		Date d = new Date(cal.getTimeInMillis());
		return d;
	}

	/**
	 * 获取当前系统时间,返回指定格式的时间字符串
	 * 
	 * @param format
	 *            格式参数
	 * @return
	 */
	public static String getSystemDateTimeStr(String format) {
		Calendar cal = Calendar.getInstance();
		Date d = new Date(cal.getTimeInMillis());
		String str = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
			dateFormat.setLenient(false);
			str = dateFormat.format(d);
		} catch (Exception e) {
			DateFormat dateFormat = new SimpleDateFormat(DateTimeFormat, Locale.ENGLISH);
			dateFormat.setLenient(false);
			str = dateFormat.format(d);
		}
		return str;
	}

	/**
	 * 将传入的Date类型的参数格式化为指定格式的日期字符串
	 * 
	 * @param d
	 *            传入的日期参数
	 * @param format
	 *            指定的日期格式
	 * @return 返回日期格式字符串
	 */
	public static String getDateTimeStr(Date d, String format) {

		String str = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
			dateFormat.setLenient(false);
			str = dateFormat.format(d);
		} catch (Exception e) {
			DateFormat dateFormat = new SimpleDateFormat(DateTimeFormat, Locale.ENGLISH);
			dateFormat.setLenient(false);
			str = dateFormat.format(d);
		}
		return str;
	}

	/**
	 * 获取当前系统时间的星期数
	 * 
	 * @return
	 */
	public static String getLocaleDayOfWeek() {
		Locale usersLocale = Locale.getDefault();
		DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
		String weekdays[] = dfs.getWeekdays();
		return weekdays[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)];
	}

	/**
	 * 获取年份
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

	/**
	 * 获取系统日期
	 * 
	 * @return
	 */
	public static String getDate() {
		String strDate = "";
		strDate = DateUtil.getDateTimeStr(DateUtil.getSystemDateTime(), DateTimeFormat);
		return strDate;
	}

	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public static String getDateTime() {
		String strDate = "";
		strDate = DateUtil.getDateTimeStr(DateUtil.getSystemDateTime(), DateAllFormat);
		return strDate;
	}

	/**
	 * 获取时间
	 * 
	 * @return
	 */
	public static String getTime() {
		String time = "";
		time = DateUtil.getDateTimeStr(DateUtil.getSystemDateTime(), TimeFormat);
		return time;
	}

	/**
	 * 获取中文日期
	 * 
	 * @return
	 */
	public static String getCnDate(String date) {
		String cnDate = "";
		if (!"".equals(date)) {
			cnDate = date.substring(0, 4) + "年" + date.substring(5, 7) + "月" + date.substring(8, 10) + "日";
		}

		return cnDate;
	}

	/**
	 * 获取月日
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearMonth(String date) {
		String cnDate = "";
		if (!"".equals(date)) {
			cnDate = date.substring(5, 7) + "-" + date.substring(8, 10);
		}

		return cnDate;
	}

	/**
	 * 获取月日
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearMonth_xbkc(String date) {
		String cnDate = "";
		if (!"".equals(date)) {
			cnDate = date.substring(5, 7) + "/" + date.substring(8, 10);
		}

		return cnDate;
	}

	public static String getsimpleDate() {
		String date = getDate();
		String cnDate = "";
		if (!"".equals(date)) {
			String my_month = date.substring(5, 7);
			if (my_month.startsWith("0") && my_month.length() == 2) {
				my_month = my_month.substring(1);
			}
			String my_day = date.substring(8, 10);
			if (my_day.startsWith("0") && my_day.length() == 2) {
				my_day = my_day.substring(1);
			}
			cnDate = my_month + "." + my_day;
		}
		return cnDate;
	}

	/**
	 * 获取星期几
	 * 
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		try {
			Date dt = sdf.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);

			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0)
				w = 0;
			return weekDays[w];
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 获取日期是当年的第几周
	 * 
	 * @param dt
	 * @return
	 */
	public static int getNumWeekOfYear(String date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date dat = format.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(dat);
			int str = calendar.get(Calendar.WEEK_OF_YEAR);
			return str;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}

	}
}
