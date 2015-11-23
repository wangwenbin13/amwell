package com.amwell.vo.bc;

/**
 * 订单状态枚举类
 * @author Administrator
 *
 */
public enum OrderStatusEnum {

	WAIT_SCHEDULE("待调度", 1),FINISH_SCHEDULE("已调度",2),ALREADY_FINISH("已完成",3),ALREADY_RETURN_TICKET("已退票",4);

	/**
	 * 显示文本		
	 */
	private String text;

	/**
	 * 实际存储值
	 */
	private int value;

	// 构造方法
	private OrderStatusEnum(String text, int value) {
		this.text = text;
		this.value = value;
	}

	// 普通方法
	public static String getText(int value) {
		for (OrderStatusEnum o : OrderStatusEnum.values()) {
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
