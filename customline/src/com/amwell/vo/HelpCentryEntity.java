package com.amwell.vo;
/**
 * 
 * @author wangwenbin
 *
 * 2014-10-24
 */
/**
 * 帮助中心实体类
 */
public class HelpCentryEntity {

	/**
	 * 帮助ID
	 */
	private String helpId;
	
	/**
	 * 帮助类型 1:预定常见问题  2:支付常见问题 3:乘车常见问题 4:退款常见问题 
	 */
	private Integer helpType;
	
	/**
	 * 内容
	 */
	private String content;

	public String getHelpId() {
		return helpId;
	}

	public void setHelpId(String helpId) {
		this.helpId = helpId;
	}

	public Integer getHelpType() {
		return helpType;
	}

	public void setHelpType(Integer helpType) {
		this.helpType = helpType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
