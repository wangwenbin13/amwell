package com.pig84.ab.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Process HTML tags.
 * 
 * @author GuoLin
 */

public class Html {

	/**
	 * Clear bad HTML tag.
	 * @param html HTML text to purge
	 * @return Purged HTML text
	 */
	public static String purge(String html){
		if(html==null || "".equals(html)){
			return "";
		}
		html = html.replaceAll("&nbsp;", "");
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; 
        String regEx_html="<[^>]+>";
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(html); 
        html=m_script.replaceAll(""); 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(html); 
        html=m_style.replaceAll(""); 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(html); 
        html=m_html.replaceAll(""); 

        return html.trim(); 
    } 

}
