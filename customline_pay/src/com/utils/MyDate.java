package com.utils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * 获取时间类
 * @author zhangqiang
 *
 */
public class MyDate {
	
	
	public static void main(String[] args) {
		Map<String,String> map =getFristDayAndLastDay("2014-02");
		System.out.println(map.get("frist_S"));
		System.out.println(map.get("last_S"));
		System.out.println(map.get("frist_L"));
		System.out.println(map.get("last_L"));
	}

	 public static Date getDate() {
			SimpleDateFormat TimeFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String TimeString = "";
			Date nowtime = new Date();

			return nowtime;
		}
	
	/**
	 * 某一个月第一天和最后一天
	 * @param time yyyy-MM
	 * @return  map.get("frist_S") 
	 *  map.get("last_S")
	 *  map.get("frist_L")
	 *  map.get("last_L")
	 */
	public static Map<String,String> getFristDayAndLastDay(String time){
		String Mtime = time+"-01";
		Date lday = getLastDayOfMonth(MyDate.strToDate(Mtime));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		String str=sdf.format(lday);
		String lastTime = str+" 23:59:59";

		Map<String, String> map = new HashMap<String, String>();
		map.put("frist_S", Mtime+" 00:00:00");
		map.put("last_S", lastTime);
		map.put("frist_L", String.valueOf(strToUTCLong(Mtime+" 00:00:00", "yyyy-MM-dd HH:mm:ss")));
		map.put("last_L", String.valueOf(strToUTCLong(lastTime, "yyyy-MM-dd HH:mm:ss")));
		
		return map;
	}
	
	
	/**
     * 获取最后一天
     * @param date
     * @return
     */
	public static Date getLastDayOfMonth(Date sDate1) {
	    Calendar cDay1 = Calendar.getInstance();
	    cDay1.setTime(sDate1);
	    final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
	    Date lastDate = cDay1.getTime();
	    lastDate.setDate(lastDay);
	    return lastDate;
	}
	

	/**
	 * String类型转TimeStamp
	 */
	public static Timestamp strToTimeStamp(String time){
	    Timestamp ts = new Timestamp(System.currentTimeMillis());  
            ts = Timestamp.valueOf(time);  
			return ts;
	}
	
//	/**
//	 * TimeStamp类型转String
//	 */
//	public static String TimeStampTostr(Timestamp time,String type){
//		SimpleDateFormat df = new SimpleDateFormat(type);//定义格式，不显示毫秒
//		Timestamp now = new Timestamp(time);//获取系统当前时间
//		String str = df.format(now);
//		return ts;
//	}
	
	
	/**
	 * 获取timestamp类型日期
	 * @return
	 */
	public static Timestamp getTimeStamp(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date().getTime());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}
	
	
	/**
	 * 字符串类型转long类型
	 * @param strTime yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long strToUTCLong(String strTime,String type) {// 2011-03-10 12:18:00
		SimpleDateFormat TIMESTAMP_FORMAT;
		TIMESTAMP_FORMAT = new SimpleDateFormat(type);//

		Date date = null;
		long time = 0l;
		try {
			date = TIMESTAMP_FORMAT.parse(strTime);
			time = date.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}
	
	/**
	 * long类型转String类型
	 * @param time
	 * @param format
	 * @return
	 */
	public static String formatTime(long time, String format) {
		String result = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		result = sdf.format(calendar.getTime());
		return result;
	}

	/**
	 * 以2006-11-05 11:05:33的形式返回时间
	 * 
	 * @return
	 */
	public static String getMyDate() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}
	
	public static String getMyDateLong() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}
	
	/**
	 * 以140826的形式返回时间
	 * @return
	 */
	public static String getMyDateForOrder() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
				"yyMMdd");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}
	
	/** 
	 * 以2006-11-05 11:05的形式返回时间
	 * @return
	 */
	public static String getMyDateShort() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}
	
	/**以11:05:33的形式返回时间
	 * @return
	 */
	public static String getMyTime() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
				"HH:mm:ss");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}
	
	/** 返回java.sql.Date类型的日期
	 * @return 
	 * @throws Exception
	 */
	public static java.sql.Date getMyDateD() throws Exception {
		
		String TimeString = "";
		java.util.Date st=new java.util.Date();
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
		Date nowtime = new java.util.Date();
		TimeString = TimeFormat.format(nowtime);
		st=TimeFormat.parse(TimeString.toString());
		java.sql.Date tvalue = new java.sql.Date(st.getTime());
		return tvalue;
	}
	
	/**
	  * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	  */

	public static String getNextDay(String nowdate, String delay) {
	  try{
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	  String mdate = "";
	  Date d = strToDate(nowdate);
	  long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
	  d.setTime(myTime * 1000);
	  mdate = format.format(d);
	  return mdate;
	  }catch(Exception e){
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
	
	 /** 转换Tue May 24 00:00:00 UTC +0800 2011 为正常格式
	 * @param strDate
	 * @return
	 */
	public static String longDatetoStr(String strDate)
	 {
		 //strDate="Tue May 24 00:00:00 UTC +0800 2011";
		 strDate=strDate.substring(0,strDate.indexOf("UTC")-1);
		  SimpleDateFormat sdf=new SimpleDateFormat("EEE MMM dd HH:mm:ss", Locale.US); 
		 // ParsePosition pos = new ParsePosition(0);
		  Date strtodate=null;
		  String TimeString = "";
		  try {
			  strtodate = sdf.parse(strDate);
			  SimpleDateFormat TimeFormat = new SimpleDateFormat("MM-dd");
		   	  TimeString = TimeFormat.format(strtodate);
		   	SimpleDateFormat TimeFormat1 = new SimpleDateFormat("yyyy");
		   	Date nowtime = new Date();
		   	TimeString=TimeFormat1.format(nowtime)+"-"+TimeString;
			  return TimeString;
		} catch (Exception e) {
			// TODO: handle exception
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

	/** 以yyyyMMddHHmm的格式返回
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
		SimpleDateFormat TimeFormat1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
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
		SimpleDateFormat TimeFormat1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
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
	
	/** 返回当前的月份
	 * @return
	 */
	public static String getMonth() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyyMM");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}
	
	/** 返回当前的年月（YYYY-MM）
	 * @return
	 */
	public static String getYearAndMonth() {
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM");
		String TimeString = "";
		Date nowtime = new Date();
		TimeString = TimeFormat.format(nowtime);
		return TimeString;
	}
	
	/** 返回当前年份
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

	/** 以20061105返回日期
	 * @return
	 */
	public static String getMyDate4() {
		String TimeString = "";
		Calendar calendar = Calendar.getInstance();
		TimeString = String.valueOf(calendar.get(Calendar.YEAR));
		if (String.valueOf(calendar.get(Calendar.MONTH) + 1).length() == 1)
			TimeString += "0"
					+ String.valueOf(calendar.get(Calendar.MONTH) + 1);
		else
			TimeString += String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).length() == 1)
			TimeString += "0"
					+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
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
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Calendar theday = Calendar.getInstance();
		theday.add(Calendar.MINUTE, offset);
		return TimeFormat.format(theday.getTime());
	}

	// OFFSET表示与当天相差的天数，取得与当前相隔多少天之前或之后的日期。因此offset也可以为负数
	public static String getPriorDay(int offset) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Calendar theday = Calendar.getInstance();
		theday.add(Calendar.DATE, offset);
		return TimeFormat.format(theday.getTime());
	}
	
	// OFFSET表示与当天相差的天数，取得与当前相隔多少天之前或之后的日期。因此offset也可以为负数
	public static String getPriorDay1(int offset) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm");
		Calendar theday = Calendar.getInstance();
		theday.add(Calendar.DATE, offset);
		return TimeFormat.format(theday.getTime());
	}

	// OFFSET表示与当天相差的月数
	public static String getPriorMonth(int offset) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Calendar theday = Calendar.getInstance();
		theday.add(Calendar.MONTH, offset);
		return TimeFormat.format(theday.getTime());
	}
	// OFFSET表示与当天相差的月数
	public static String getPriorMonth1(int offset) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm");
		Calendar theday = Calendar.getInstance();
		theday.add(Calendar.MONTH, offset);
		return TimeFormat.format(theday.getTime());
	}

	// 获取指定时间是否是当前的n个分钟之前的后面,如首次充值时间是否在当前时间的一个月前的前面。addMinute可以为负数
	public static boolean isAfterNMinute(String datetime, int addMinute) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
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
		if (forTime.after(aftTime)) {
			return true;
		} else {
			return false;
		}
	}

	// 获取指定时间是否是当前的n个月之前的后面,如首次充值时间是否在当前时间的一个月前的前面。addMonth可以为负数
	public static boolean isAfterNMonth(String datetime, int addmonth) {
		SimpleDateFormat TimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
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
		if (forTime.after(aftTime)) {
			return true;
		} else {
			return false;
		}
	}

	// 以20070301获取本月的第一天
	public static String getFirstDayOfMonth(String date) throws ParseException {
		String timeString = "";
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date forTime = TimeFormat.parse(date);
		 if (String.valueOf(forTime.getMonth()+1).length() == 1)
			timeString += "0"
					+ String.valueOf(forTime.getMonth()+1);
		else
			timeString += String.valueOf(forTime.getMonth()+1);
		timeString += "01";
		return timeString;
	}
	
	
		/**
		 * 按月份相加
		 * @param str 数据库中存储的时间 yyyy-MM-dd HH:mm:ss
		 * @param month 要相加的月份
		 * @return
		 */
	    
	    public static String adddate(String str,String month){
	    	  SimpleDateFormat   sdf=new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	    	  Date   dt=sdf.parse(str,new   ParsePosition(0));
	    	  int y = Integer.valueOf(month).intValue();
	    	  Calendar   rightNow   =   Calendar.getInstance();   
	    	  rightNow.setTime(dt);   
	    	  rightNow.add(Calendar.MONTH,y);//你要加减的日期     
	    	  Date   dt1=rightNow.getTime();   
	    	  String   reStr=sdf.format(dt1);
	    	  return reStr;
	    }
	    
	    /**
	     * 按月份相加
	     * @param str 数据库中存储的时间 yyyy-MM
	     * @param month 要相加的月份
	     * @return
	     */
	    
	    public static String adddate2(String str,String month){
	    	SimpleDateFormat   sdf=new   SimpleDateFormat("yyyy-MM");   
	    	Date   dt=sdf.parse(str,new   ParsePosition(0));
	    	int y = Integer.valueOf(month).intValue();
	    	Calendar   rightNow   =   Calendar.getInstance();   
	    	rightNow.setTime(dt);   
	    	rightNow.add(Calendar.MONTH,y);//你要加减的日期     
	    	Date   dt1=rightNow.getTime();   
	    	String   reStr=sdf.format(dt1);
	    	return reStr;
	    }
	    
		/**
		 * 将string类型的时间转换成Date类型 yyyy-MM-dd HH:mm:ss
		 * @param stringdate
		 * @return
		 */
		public static Date Stringtodate(String stringdate){
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
		 * 将string类型的时间转换成Date类型  yyyy-MM-dd
		 * @param stringdate
		 * @return
		 */
		public static Date Stringtodate2(String stringdate){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(stringdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
		
		/** ID生成规则
		 * @param 
		 * @return
		 */
		   public static String createzhuceid() {
		        java.util.Date now = new java.util.Date();
		        Random rd = new Random();
		        SimpleDateFormat dd1 = new SimpleDateFormat("yyyyMMddHHmmss");
		        String st = "";
		        for(int i=0;i<4;i++){
		        	st += rd.nextInt(10);
		        }
		        return dd1.format(now)+st;
		    }
		//将unicode转换为中文
		   public static String unescape(String src) {
				if (src==null) return "";
				StringBuffer tmp = new StringBuffer();
				tmp.ensureCapacity(src.length());
				int lastPos = 0, pos = 0;
				char ch;
				while (lastPos < src.length()) {
					pos = src.indexOf("%", lastPos);
					if (pos == lastPos) {
						if (src.charAt(pos + 1) == 'u') {
							ch = (char) Integer.parseInt(src
									.substring(pos + 2, pos + 6), 16);
							tmp.append(ch);
							lastPos = pos + 6;
						} else {
							ch = (char) Integer.parseInt(src
									.substring(pos + 1, pos + 3), 16);
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
		  
		  
		  //获取星座则是直接根据月份做索引，然后根据日期边界判断是本月的星座还是上月的。
          //星座根据阳历判断,生肖根据农历判断
		   public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };    
		   
		   public static final String[] constellationArr = { "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",    
		           "天蝎座", "射手座", "魔羯座" };    
		      
		   public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };    
		   
		   
		   //根据阳历取得的生肖
		   /**   
		    * 根据日期获取生肖   
		    * @return   
		    */   
//		   public static String date2Zodica(Calendar time) {    
//		       return zodiacArr[time.get(Calendar.YEAR) % 12];    
//		   }    
		      
		   /**   
		    * 根据日期获取星座   
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
		       //default to return 魔羯    
		       return constellationArr[11];    
		   }   


		//阴历算法
		       private int year;
		       private int month;
		       private int day;
		       private boolean leap;
		       final static String chineseNumber[] = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "腊"};
		       final static String chineseNumber1[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
		       static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		       final static long[] lunarInfo = new long[]
		       {0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
		        0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
		        0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
		        0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
		        0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
		        0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
		        0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
		        0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
		        0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
		        0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
		        0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
		        0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
		        0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
		        0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
		        0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};

		   //====== 传回农历 y年的总天数
		       final private static int yearDays(int y) {
		           int i, sum = 348;
		           for (i = 0x8000; i > 0x8; i >>= 1) {
		               if ((lunarInfo[y - 1900] & i) != 0) sum += 1;
		           }
		           return (sum + leapDays(y));
		       }

		   //====== 传回农历 y年闰月的天数
		       final private static int leapDays(int y) {
		           if (leapMonth(y) != 0) {
		               if ((lunarInfo[y - 1900] & 0x10000) != 0)
		                   return 30;
		               else
		                   return 29;
		           } else
		               return 0;
		       }

		   //====== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
		       final private static int leapMonth(int y) {
		           return (int) (lunarInfo[y - 1900] & 0xf);
		       }

		   //====== 传回农历 y年m月的总天数
		       final private static int monthDays(int y, int m) {
		           if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
		               return 29;
		           else
		               return 30;
		       }

		   //====== 传回农历 y年的生肖
		       final public String animalsYear() {
		           final String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
		           return Animals[(year - 4) % 12];
		       }

		   //====== 传入 月日的offset 传回干支, 0=甲子
		       final private static String cyclicalm(int num) {
		           final String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
		           final String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
		           return (Gan[num % 10] + Zhi[num % 12]);
		       }

		   //====== 传入 offset 传回干支, 0=甲子
		       final public String cyclical() {
		           int num = year - 1900 + 36;
		           return (cyclicalm(num));
		       }

		       /**
		        * 传出y年m月d日对应的农历.
		        * yearCyl3:农历年与1864的相差数 ?
		        * monCyl4:从1900年1月31日以来,闰月数
		        * dayCyl5:与1900年1月31日相差的天数,再加40 ?
		        *
		        * @param cal
		        * @return
		        */
		       public MyDate(Calendar cal) {
		           int yearCyl, monCyl, dayCyl;
		           int leapMonth = 0;
		           Date baseDate = null;
		           try {
		               baseDate = chineseDateFormat.parse("1900年1月31日");
		           } catch (ParseException e) {
		               e.printStackTrace(); //To change body of catch statement use Options | File Templates.
		           }

		   //求出和1900年1月31日相差的天数
		           int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) / 86400000L);
		           dayCyl = offset + 40;
		           monCyl = 14;

		   //用offset减去每农历年的天数
		   // 计算当天是农历第几天
		   //i最终结果是农历的年份
		   //offset是当年的第几天
		           int iYear, daysOfYear = 0;
		           for (iYear = 1900; iYear < 2050 && offset > 0; iYear++) {
		               daysOfYear = yearDays(iYear);
		               offset -= daysOfYear;
		               monCyl += 12;
		           }
		           if (offset < 0) {
		               offset += daysOfYear;
		               iYear--;
		               monCyl -= 12;
		           }
		   //农历年份
		           year = iYear;

		           yearCyl = iYear - 1864;
		           leapMonth = leapMonth(iYear); //闰哪个月,1-12
		           leap = false;

		   //用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
		           int iMonth, daysOfMonth = 0;
		           for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
		   //闰月
		               if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
		                   --iMonth;
		                   leap = true;
		                   daysOfMonth = leapDays(year);
		               } else
		                   daysOfMonth = monthDays(year, iMonth);

		               offset -= daysOfMonth;
		   //解除闰月
		               if (leap && iMonth == (leapMonth + 1)) leap = false;
		               if (!leap) monCyl++;
		           }
		   //offset为0时，并且刚才计算的月份是闰月，要校正
		           if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
		               if (leap) {
		                   leap = false;
		               } else {
		                   leap = true;
		                   --iMonth;
		                   --monCyl;
		               }
		           }
		   //offset小于0时，也要校正
		           if (offset < 0) {
		               offset += daysOfMonth;
		               --iMonth;
		               --monCyl;
		           }
		           month = iMonth;
		           day = offset + 1;
		       }

		       public static String getChinaDayString(int day) {
		           String chineseTen[] = {"初", "十", "廿", "卅"};
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
		       public String getChinaWeekdayString(String weekday){
		        if(weekday.equals("Mon"))
		         return "一";
		        if(weekday.equals("Tue"))
		         return "二";
		        if(weekday.equals("Wed"))
		         return "三";
		        if(weekday.equals("Thu"))
		         return "四";
		        if(weekday.equals("Fri"))
		         return "五";
		        if(weekday.equals("Sat"))
		         return "六";
		        if(weekday.equals("Sun"))
		         return "日";
		        else
		         return "";
		        
		       }
//		       public static void main(String[] args) throws ParseException{
//		        Calendar today  = Calendar.getInstance();
//		        today.setTime(new java.util.Date());//加载当前日期
//		        //today.setTime(chineseDateFormat.parse("2006年10月30日"));//加载自定义日期
//		        MyDate mydate = new MyDate(today);
//		        System.out.print(mydate.cyclical()+"年");//计算输出阴历年份
//		        System.out.println(mydate.toString());//计算输出阴历日期
//		        System.out.println(mydate.animalsYear());//计算输出属相
//		        System.out.println(new java.sql.Date(today.getTime().getTime()));//输出阳历日期
//		        System.out.println("星期"+mydate.getChinaWeekdayString(today.getTime().toString().substring(0,3)));//计算输出星期几
//		       }
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
		   	 * 得到星期几
		   	 * 
		   	 * @param djrq
		   	 * @return
		   	 */
		   	public static String getweekofdayByShot(String djrq) {
		   		final String dayNames[] = { "末", "一", "二", "三", "四", "五", "六" };
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
		   	 * 计算两个日期中间相差的天数
		   	 * @param beginTime 起始日期
		   	 * @param endTime 终止日期
		   	 * @return
		   	 */
		   	public static long getQuot(String beginTime,String endTime){
		   	  long quot = 0;
		   	  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		   	  try {
		   	   Date date1 = ft.parse( endTime );
		   	   Date date2 = ft.parse( beginTime );
		   	   quot = date1.getTime() - date2.getTime();
		   	   quot = quot / 1000 / 60 / 60 / 24;
		   	  } catch (ParseException e) {
		   	   e.printStackTrace();
		   	  }
		   	  return quot;
		   	 }
		   	
		   	/**
			 * @param 要转换的毫秒数
			 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
			 * @return
			 */
			public static String formatDuring(long beginTime, long endTime) {
				long mss = (endTime - beginTime);
				long days = mss / (1000 * 60 * 60 * 24);
				long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
				long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
				long seconds = (mss % (1000 * 60)) / 1000;
				if (days == 0) {
					if (hours == 0) {
						if (minutes == 0) {
							return " 0 天 1 小时";
						} else {
							return " 0 天 1 小时";
						}
					} else {
						return " 0 天 " +hours + " 小时";
					}
				} else {
					return " "+days + " 天  " + hours + " 小时 ";
				}
			}
			
			//获取往后延时的时间  延时单位分钟
			public static Date getDateDelay(int timeDelay){
				Calendar rightNow = Calendar.getInstance(); 
				rightNow.roll(Calendar.MINUTE, timeDelay);
				Date date = rightNow.getTime();
				return date;
				
			}
		   	
}
