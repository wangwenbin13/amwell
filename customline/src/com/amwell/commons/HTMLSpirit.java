package com.amwell.commons;

import java.util.regex.Matcher; 
import java.util.regex.Pattern; 

public class HTMLSpirit{ 
    public static String delHTMLTag(String htmlStr){ 
    	String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
        String regEx_str = "\\s*|\t|\r|\n";
        htmlStr = htmlStr.replaceAll("\"", "’");  
        htmlStr = htmlStr.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");  
        htmlStr = htmlStr.replaceAll("[(/>)<]", "");  
        
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 
         
        Pattern p_str=Pattern.compile(regEx_str,Pattern.CASE_INSENSITIVE); 
        Matcher m_str=p_str.matcher(htmlStr); 
        htmlStr=m_str.replaceAll(""); 
        return htmlStr.trim(); //返回文本字符串 s
    } 
}   