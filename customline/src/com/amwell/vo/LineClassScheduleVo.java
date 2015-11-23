package com.amwell.vo;

public class LineClassScheduleVo {
	/**
	 * 班次ID
	 */
	private String lineClassId;
	/**
	 * 发车时间
	 */
	private String orderStartTime;
	/**
	 * 座位数,默认为50
	 */
	private int orderSeats = 50;
	/**
	 * 排班日期
	 */
	private String orderDate;
	
	/**
	 * 商家id
	 */
	private String lineBusinessId;

	/**
	 * 线路信息ID
	 */
	private String lineBaseId;

	/**
	 * 司机ID
	 */
	private String driverId;
	
	/**
	 * 司机姓名
	 */
	private String driverName;

	/**
	 * 车辆ID
	 */
	private String vehicleId;
	
	/**
	 * 车牌号
	 */
	private String vehicleNumber;
	
	/**
	 * 座位数
	 */
	private String vehicleSeats;
	
	/**
	 * 班次状态  0-正常 1-删除
	 */
	private int delFlag;
	
	/**
	 * 班次价格
	 */
	private String price;
}
