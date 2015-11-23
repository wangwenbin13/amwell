package com.utils;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 处理字符串的工具类
 * 
 * @author zhangqiang
 */

public class StringUtil {

    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
    private final static Format dateFormat = new SimpleDateFormat("yyMMddHHmmssSS");
    private final static NumberFormat numberFormat = new DecimalFormat("000");
    private static int seq = 0;
    private static final int MAX = 999;
    
    /**
     * 时间格式生成序列
     * @return String
     */
    public static synchronized String generateSequenceNo() { 

        Calendar rightNow = Calendar.getInstance();

        StringBuffer sb = new StringBuffer();

        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);

        numberFormat.format(seq, sb, HELPER_POSITION);

        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }

        return sb.toString();
    }
    
    /**
     * 获取圆角分
     * @param price
     * @return
     */
    public static String getMoneyByYJF(String price){
    	if(price.contains(".")){
    		String temp = price.substring(price.lastIndexOf(".")+1,price.length());
    		if (temp.length()==1){
    			return price+"0";
			}else{
				String temp1 = price.substring(0,price.lastIndexOf(".")+3);
				return temp1;
			}
    	}else{
    		return price;
    	}
		
    }
    
	/**
	 * 对象转换成字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}
	
	/**
	 * 清除html标签
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr){
		if(htmlStr==null || "".equals(htmlStr)){
			return "";
		}
		htmlStr = htmlStr.replaceAll("&nbsp;", "");
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    } 
	
	  /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String getUUID(){ 
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        return uuid;
    } 
}
