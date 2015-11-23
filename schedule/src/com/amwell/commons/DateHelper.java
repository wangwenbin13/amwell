package com.amwell.commons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 日期帮助类
 * 
 * @author Administrator
 *
 */
public class DateHelper {

	public static final long PER_ONE_MINUTE_SECONDS = 60;

	public static final long PER_ONE_HOUR_SECONDS = 60 * PER_ONE_MINUTE_SECONDS;

	public static final long PER_ONE_DAY_SECONDS = 24 * PER_ONE_HOUR_SECONDS;

	public static final String FORMAT_FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";

	public static final String FORMAT_FULL_TIME = "HH:mm:ss";

	public static final String FORMAT_MINUTE_TIME = "HH:mm";

	private DateHelper() {

	}

	/**
	 * 计算两个时间相减毫秒数
	 * 
	 * @param date1
	 *            此值不能为空，且必须晚于date2
	 * @param date2
	 *            此值不能为空
	 * @return 返回两个时间相减毫秒数
	 */
	public static Long getDatesDiffMillisecond(Date date1, Date date2) {
		if (null == date1 || null == date2) {
			throw new IllegalArgumentException("date1 or date2 can't be null.");
		}
		if (date1.before(date2)) {
			throw new IllegalArgumentException("date1 must after date2.");
		}
		return date1.getTime() - date2.getTime();
	}

	/**
	 * 计算两个时间相减毫秒数
	 * 
	 * @param s1
	 *            此值不能为空，且按dateFormat解析后必须晚于s2解析后的值
	 * @param s2
	 *            此值不能为空
	 * @param dateFormat
	 *            时间格式
	 * @return 返回两个时间相减毫秒数
	 */
	public static Long getDatesDiffMillisecond(String s1, String s2, String dateFormat) {
		if (StringUtils.hasText(s1) && StringUtils.hasText(s2) && StringUtils.hasText(dateFormat)) {
			Date d1 = null;
			Date d2 = null;
			try {
				if(s1!=null){
					d1 = new SimpleDateFormat(dateFormat).parse(s1);
				}
				if(s2!=null){
					d2 = new SimpleDateFormat(dateFormat).parse(s2);
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("parse date error");
			}
			if (null != s1 && null != s2) {
				return getDatesDiffMillisecond(d1, d2);
			} else {
				return null;
			}
		} else {
			throw new IllegalArgumentException("s1,s2,dateFormat can't be null.");
		}
	}

	/**
	 * 计算两个时间之差，返回相差的天数(key=days)，小时数(key=hours)，分数(key=minutes)，秒数(key=seconds)
	 * 
	 * @param date1
	 *            此值不能为空，且必须晚于date2
	 * @param date2
	 *            此值不能为空
	 * @return TreeMap
	 */
	public static TreeMap<String, Long> getDatesDiff(Date date1, Date date2) {
		if (null == date1 || null == date2) {
			throw new IllegalArgumentException("date1 or date2 can't be null.");
		}
		if (date1.before(date2)) {
			throw new IllegalArgumentException("date1 must after date2.");
		}
		TreeMap<String, Long> res = new TreeMap<String, Long>();
		long diffSeconds = (date1.getTime() - date2.getTime()) / 1000;
		long days = diffSeconds / PER_ONE_DAY_SECONDS;
		res.put("days", days);
		long hours = (diffSeconds % PER_ONE_DAY_SECONDS) / PER_ONE_HOUR_SECONDS;
		res.put("hours", hours);
		long minutes = diffSeconds % PER_ONE_DAY_SECONDS % PER_ONE_HOUR_SECONDS / PER_ONE_MINUTE_SECONDS;
		res.put("minutes", minutes);
		long seconds = diffSeconds % PER_ONE_DAY_SECONDS % PER_ONE_HOUR_SECONDS % PER_ONE_MINUTE_SECONDS;
		res.put("seconds", seconds);
		return res;
	}

	/**
	 * 获取两个时间之差
	 * 
	 * @param date1
	 *            此值不能为空，且必须晚于date2
	 * @param date2
	 *            此值不能为空
	 * @return 两个日期相差字符串，如3天2小时5分10秒
	 */
	public static String getDatesDiffString(Date date1, Date date2) {
		Map<String, Long> res = getDatesDiff(date1, date2);
		if (false == CollectionUtils.isEmpty(res)) {
			return res.get("days") + "天" + res.get("hours") + "小时" + res.get("minutes") + "分钟" + res.get("seconds")
					+ "秒";
		}
		return null;
	}

	public static String getDatesDiffString(String s1, String s2, String dateFormat) {
		if (StringUtils.hasText(s1) && StringUtils.hasText(s2) && StringUtils.hasText(dateFormat)) {
			Date d1 = null;
			Date d2 = null;
			try {
				if(s1!=null){
					d1 = new SimpleDateFormat(dateFormat).parse(s1);
				}
				if(s2!=null){
					d2 = new SimpleDateFormat(dateFormat).parse(s2);
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("parse date error");
			}
			if (null != s1 && null != s2) {
				return getDatesDiffString(d1, d2);
			} else {
				return null;
			}
		} else {
			throw new IllegalArgumentException("s1,s2,dateFormat can't be null.");
		}

	}

	public static void main(String[] args) {
		String s1 = "2014-11-31 00:00:00";
		String s2 = "2014-11-28 09:26:37";
		System.out.println(getDatesDiffString(s1, s2, FORMAT_FULL_DATE_TIME));
	}
}
