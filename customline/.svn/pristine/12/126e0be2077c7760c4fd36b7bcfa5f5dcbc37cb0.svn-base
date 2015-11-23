package com.amwell.commons;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class CalendarHelper {

	private CalendarHelper() {

	}

	public static List<String> getQuarterMonthsFromNow() {
		List<String> result = new ArrayList<String>();
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		result.add(sdf.format(ca.getTime()));
		ca.add(Calendar.MONTH, 1);
		result.add(sdf.format(ca.getTime()));
		ca.add(Calendar.MONTH, 1);
		result.add(sdf.format(ca.getTime()));
		return result;
	}
	
	public static String getMonthByIndex(int j) {
		Calendar ca = Calendar.getInstance();
		if(j==0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			return sdf.format(ca.getTime());
		}else if(j==1){
			ca.add(Calendar.MONTH,1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			return sdf.format(ca.getTime());
		}else if(j==2){
			ca.add(Calendar.MONTH,2);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			return sdf.format(ca.getTime());
		}
		return null;
	}

	public static void main(String[] args) {
		List<String> result = getQuarterMonthsFromNow();
		for(String s : result){
			System.out.println(s);
		}
	}
}
