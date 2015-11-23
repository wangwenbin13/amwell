package com.amwell.vo.bc;

/**
 * 商户竞价枚举类
 * @author Administrator
 *
 */
public enum OfferStatusEnum {

	WAIT_OFFER("待报价", 0),FINISH_OFFER("已报价",1),EXPIRED("已过期",2);

	/**
	 * 显示文本		
	 */
	private String text;

	/**
	 * 实际存储值
	 */
	private int value;

	// 构造方法
	private OfferStatusEnum(String text, int value) {
		this.text = text;
		this.value = value;
	}

	// 普通方法
	public static String getText(int value) {
		for (OfferStatusEnum o : OfferStatusEnum.values()) {
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
