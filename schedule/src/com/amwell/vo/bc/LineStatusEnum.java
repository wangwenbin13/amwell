package com.amwell.vo.bc;

/**
 * 线路状态枚举
 * 
 */
public enum LineStatusEnum {

	WAIT_AUDIT("待审核", 0), NOT_PASS_AUDIT("未通过", 1), WAIT_OFFER("待报价", 2), FINISH_OFFER("已报价", 3), EXPIRED("已过期", 4);
	
	/**
	 * 显示文本		
	 */
	private String text;

	/**
	 * 实际存储值
	 */
	private int value;

	// 构造方法
	private LineStatusEnum(String text, int value) {
		this.text = text;
		this.value = value;
	}

	// 普通方法
	public static String getText(int value) {
		for (LineStatusEnum o : LineStatusEnum.values()) {
			if (value == o.getValue()) {
				return o.getText();
			}
		}
		return null;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
