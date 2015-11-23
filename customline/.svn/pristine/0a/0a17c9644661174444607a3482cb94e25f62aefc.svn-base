package com.amwell.commons;


import org.springframework.util.StringUtils;

import alex.zhrenjie04.wordfilter.WordFilterUtil;
import alex.zhrenjie04.wordfilter.result.FilteredResult;

/**
 * java敏感词过滤
 */
public class WordFilterUtils {

	public static final char REPLACE_CHAR='*';
	
	private WordFilterUtils(){
		
	}
	
	/**
	 * 返回过滤结果(FilteredResult:)
	 * FilteredResult对象方法说明：
	 * getBadWords():返回敏感词字符串,前台可根据此字段是否为空来判断是否包含敏感词
	 * getFilteredContent():返回过滤后的字符串内容
	 * getOriginalContent()：返回原始字符串内容
	 * @param originalString 原始字符串
	 * @param replaceChar 敏感词替换字符
	 * @return
	 */
	public static FilteredResult filteText(String originalString,char replaceChar){
		if(null!=originalString&&originalString.length()>0){
			return WordFilterUtil.filterText(originalString, replaceChar);
		}
		return null;
	}
	
	/**
	 * @param originalString 原始字符串
	 * @return 返回敏感词，若无则返回空
	 */
	public static String getBadWords(String originalString){
		if(null!=originalString&&originalString.length()>0){
			return filteText(originalString, REPLACE_CHAR).getBadWords();
		}
		return null;
	} 
	
	/**
	 * 
	 * @param originalString 原始字符串
	 * @return 返回敏感词，若输入为空或无敏感词则返回true,否则返回false
	 */
	public static boolean containBadWords(String originalString){
		return StringUtils.hasText(getBadWords(originalString));
	}
	
	public static void main(String[] args) {
		String originalString = "中国共产党";
		FilteredResult result = WordFilterUtils.filteText(originalString, REPLACE_CHAR);
		System.out.println("敏感词:"+result.getBadWords());
		System.out.println("过滤后:"+result.getFilteredContent());
		System.out.println("原始内容："+result.getOriginalContent());
		System.out.println(WordFilterUtils.getBadWords(originalString));
		System.out.println(WordFilterUtils.containBadWords(originalString));
		
		originalString="老毛";
		result = WordFilterUtils.filteText(originalString, REPLACE_CHAR);
		System.out.println("敏感词:"+result.getBadWords());
		System.out.println("过滤后:"+result.getFilteredContent());
		System.out.println("原始内容："+result.getOriginalContent());
		System.out.println(WordFilterUtils.getBadWords(originalString));
		System.out.println(WordFilterUtils.containBadWords(originalString));
	}
}
