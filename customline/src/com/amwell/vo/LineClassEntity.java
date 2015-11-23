package com.amwell.vo;

import com.amwell.commons.ApplicationContextHolder;
import com.amwell.dao.IPublishLineDao;
import com.amwell.entity.base.BaseEntity;

/**
 * 线路班次实体类
 * 
 * @author Administrator
 * 
 */
public class LineClassEntity  extends BaseEntity {

	private static final long serialVersionUID = 4559177461827914978L;

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
	 * 车辆ID
	 */
	private String vehicleId;
	
	/**
	 * 班次状态  0-正常 1-删除
	 */
	private int delFlag;
	
	/**
	 * 班次价格
	 */
	private String price;
	
	/**车牌**/
	private String vehicleNumber;
	
	/**司机名称**/
	private String driverName;
	
	/**当天是否有排班**/
	private String schedule;
	/**建议一**/
	private String advOne;
	/**建议二**/
	private String advTwo;
	/**建议三**/
	private String advThree;

	public LineClassEntity() {

	}
	
	public void save(){
		IPublishLineDao publishLineDao = ApplicationContextHolder.getBean(IPublishLineDao.class);
		publishLineDao.syncLineClassInfo(this);
	}
	
	public static LineClassEntity getMaxDateClassEntity(String lineBaseId){
		IPublishLineDao publishLineDao = ApplicationContextHolder.getBean(IPublishLineDao.class);
		return publishLineDao.getMaxDateClassEntity(lineBaseId);
	}
	
	public static LineClassEntity getByLineBaseIdAndOrderDate(String lineBaseId,String orderDate){
		IPublishLineDao publishLineDao = ApplicationContextHolder.getBean(IPublishLineDao.class);
		return publishLineDao.getByLineBaseIdAndOrderDate(lineBaseId, orderDate);
	}

	public String getLineClassId() {
		return lineClassId;
	}

	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
	}

	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public int getOrderSeats() {
		return orderSeats;
	}

	public void setOrderSeats(int orderSeats) {
		this.orderSeats = orderSeats;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLineBusinessId() {
		return lineBusinessId;
	}

	public void setLineBusinessId(String lineBusinessId) {
		this.lineBusinessId = lineBusinessId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getAdvOne() {
		return advOne;
	}

	public void setAdvOne(String advOne) {
		this.advOne = advOne;
	}

	public String getAdvTwo() {
		return advTwo;
	}

	public void setAdvTwo(String advTwo) {
		this.advTwo = advTwo;
	}

	public String getAdvThree() {
		return advThree;
	}

	public void setAdvThree(String advThree) {
		this.advThree = advThree;
	}

}
