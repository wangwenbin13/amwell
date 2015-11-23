package com.amwell.infrastructure.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.amwell.commons.PropertyManage;

public class HolidayConfig {
	
	private static Logger logger = Logger.getLogger(HolidayConfig.class);
	
	private final static List<String> holidayList = new ArrayList<String>();
	
	static{
		Properties properties = PropertyManage.getHolidayProperty();
		if(properties!=null){
			String holidayStr = properties.getProperty("holiday");
			if(!StringUtils.isEmpty(holidayStr)){
				String[] holidayArray = holidayStr.split(",");
				Collections.addAll(holidayList, holidayArray);
			}
		}
	}
	
	/**判断是否为假日*/
	public static boolean isHoliday(String orderDate){
		boolean isHoliday = false;
		for(String date : holidayList){
			if(date.equals(orderDate)){
				isHoliday = true;
				break;
			}
		}
		return isHoliday;
	}
	
	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	private static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	
	/**判断是否为周末*/
	public static boolean isWeekend(String pTime){
		boolean isWeekend = false;
		try{
			int dayForWeek = dayForWeek(pTime);
			if(dayForWeek==6||dayForWeek==7){
				isWeekend = true;
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return isWeekend;
	}
}
