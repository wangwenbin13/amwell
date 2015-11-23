package com.amwell.action.bc;

import com.amwell.entity.base.BaseEntity;

/**
 * 商户报价表单
 * 
 * @author Administrator
 *
 */
public class BusinessBiddingForm extends BaseEntity {

	private static final long serialVersionUID = -2984753372333460787L;

	/**
	 * 商户竞价id
	 */
	private String businessBiddingId;

	/**
	 * 商户ID
	 */
	private String businessId;

	/**
	 * 线路ID
	 */
	private String bc_line_id;

	/**
	 * 线路过期时间
	 */
	private String expireTime;

	/******************************************** 车辆信息 *******************************************/
	private String[] carType;

	private String[] carSeats;

	private String[] carAge;

	private String[] totalCars;

	/******************************************* 报价信息 ******************************************/
	private String basicCost = "0.00";

	private String driverMealCost = "0.00";

	private String driverHotelCost = "0.00";

	private String oilCost = "0.00";

	private String totalCost = "0.00";

	/******************************************* 乘客须知 ******************************************/
	private String driverWorkHour;

	private String overtimeCost;

	private String limitMileage;

	private String overmileageCost;

	/******************************************* 退票须知 ******************************************/
	private String noReturn;

	private String returnOneTime;

	private String returnOnePer;

	private String returnTwoTime;

	private String returnTwoPer;

	/******************************************** 其它 *******************************************/
	private String additionalServices;

	private String remark;

	/**
	 * 乘客须知
	 */
	private String passengerNotice;

	/**
	 * 退票须知
	 */
	private String returnNotice;

	/******************************************* 共用 ********************************************/
	private String createBy;

	private String createOn;

	public String getBusinessBiddingId() {
		return businessBiddingId;
	}

	public void setBusinessBiddingId(String businessBiddingId) {
		this.businessBiddingId = businessBiddingId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBc_line_id() {
		return bc_line_id;
	}

	public void setBc_line_id(String bc_line_id) {
		this.bc_line_id = bc_line_id;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String[] getCarType() {
		return carType;
	}

	public void setCarType(String[] carType) {
		this.carType = carType;
	}

	public String[] getCarSeats() {
		return carSeats;
	}

	public void setCarSeats(String[] carSeats) {
		this.carSeats = carSeats;
	}

	public String[] getCarAge() {
		return carAge;
	}

	public void setCarAge(String[] carAge) {
		this.carAge = carAge;
	}

	public String[] getTotalCars() {
		return totalCars;
	}

	public void setTotalCars(String[] totalCars) {
		this.totalCars = totalCars;
	}

	public String getBasicCost() {
		return basicCost;
	}

	public void setBasicCost(String basicCost) {
		this.basicCost = basicCost;
	}

	public String getDriverMealCost() {
		return driverMealCost;
	}

	public void setDriverMealCost(String driverMealCost) {
		this.driverMealCost = driverMealCost;
	}

	public String getDriverHotelCost() {
		return driverHotelCost;
	}

	public void setDriverHotelCost(String driverHotelCost) {
		this.driverHotelCost = driverHotelCost;
	}

	public String getOilCost() {
		return oilCost;
	}

	public void setOilCost(String oilCost) {
		this.oilCost = oilCost;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getDriverWorkHour() {
		return driverWorkHour;
	}

	public void setDriverWorkHour(String driverWorkHour) {
		this.driverWorkHour = driverWorkHour;
	}

	public String getOvertimeCost() {
		return overtimeCost;
	}

	public void setOvertimeCost(String overtimeCost) {
		this.overtimeCost = overtimeCost;
	}

	public String getLimitMileage() {
		return limitMileage;
	}

	public void setLimitMileage(String limitMileage) {
		this.limitMileage = limitMileage;
	}

	public String getOvermileageCost() {
		return overmileageCost;
	}

	public void setOvermileageCost(String overmileageCost) {
		this.overmileageCost = overmileageCost;
	}

	public String getNoReturn() {
		return noReturn;
	}

	public void setNoReturn(String noReturn) {
		this.noReturn = noReturn;
	}

	public String getReturnOneTime() {
		return returnOneTime;
	}

	public void setReturnOneTime(String returnOneTime) {
		this.returnOneTime = returnOneTime;
	}

	public String getReturnOnePer() {
		return returnOnePer;
	}

	public void setReturnOnePer(String returnOnePer) {
		this.returnOnePer = returnOnePer;
	}

	public String getReturnTwoTime() {
		return returnTwoTime;
	}

	public void setReturnTwoTime(String returnTwoTime) {
		this.returnTwoTime = returnTwoTime;
	}

	public String getReturnTwoPer() {
		return returnTwoPer;
	}

	public void setReturnTwoPer(String returnTwoPer) {
		this.returnTwoPer = returnTwoPer;
	}

	public String getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(String additionalServices) {
		this.additionalServices = additionalServices;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPassengerNotice() {
		return passengerNotice;
	}

	public void setPassengerNotice(String passengerNotice) {
		this.passengerNotice = passengerNotice;
	}

	public String getReturnNotice() {
		return returnNotice;
	}

	public void setReturnNotice(String returnNotice) {
		this.returnNotice = returnNotice;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}
}
