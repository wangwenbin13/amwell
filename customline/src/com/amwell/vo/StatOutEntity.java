package com.amwell.vo;

import java.math.BigDecimal;

/**
 * @author wangwenbin
 *
 * 2014-8-20
 */
/**
 * 支出记录
 */
public class StatOutEntity {
	
	private String statOutId;
	
	//-------------  line_class_info -------
	/**
	 * 时间
	 */
	private String orderDate;
	
	/**
	 * 班次
	 */
	private String orderStartTime;
	
	/**
	 * 班次ID
	 */
	private String lineClassId;
	
	
	//----------- line_base_info -----------
	/**
	 * 线路类型
	 * 0:上下班  1:旅游  (自由行)
	 */
	private Integer lineSubType;
	
	private String lineSubType_s;
	
	/**
	 * 线路名称
	 */
	private String lineName;
	
	/**
	 * 线路ID
	 */
	private String lineBaseId;
	
	
	//--------------- passenger_info ---------
	/**
	 * 乘客回显ID
	 */
	private String displayId;
	
	/**
	 * 乘客昵称
	 */
	private String nickName;
	
	/**
	 * 乘客ID
	 */
	private String passengerId;
	
	/**
	 * 联系方式
	 */
	private String telephone;
	
	
	//-------------- mgr_business ------------
	/**
	 * 商家  商户简称
	 */
	private String brefName;
	
	/**
	 * 商户ID
	 */
	private String businessId;
	
	
	//-------------- driver_info -------------
	/**
	 * 司机  司机姓名
	 */
	private String driverName;
	
	/**
	 * 司机ID
	 */
	private String driverId;
	
	
	/**
	 * 支出额度
	 */
	private BigDecimal outMoney;
	
	
	//----------------  vehicle_info --------
	/**
	 * 车辆(车牌号)
	 */
	private String vehicleNumber;
	
	/**
	 * 车辆类型 0:大巴 1:中巴 2:小巴
	 */
	private String vehicleType;
	
	/**
	 * 品牌
	 */
	private String vehicleBrand;
	
	/**
	 * 车辆ID
	 */
	private String vehicleId;
	
	/**
	 * 操作时间
	 * @return
	 */
	private String operatTime;
	
	/**
	 * 操作人ID
	 * @return
	 */
	private String operater;
	
	/**
	 * 操作人
	 */
	private String loginName;
	
	/**
	 * 订单号
	 * @return
	 */
	private String leaseOrderNo;
	
	/**
	 * 支出类型  0:全部  1:退票   2:改签
	 * @return
	 */
	private Integer outType;
	
	private String outType_s;



	public String getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}


	public String getOrderStartTime() {
		return orderStartTime;
	}


	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}


	public Integer getLineSubType() {
		return lineSubType;
	}


	public void setLineSubType(Integer lineSubType) {
		this.lineSubType = lineSubType;
	}


	public String getLineName() {
		return lineName;
	}


	public void setLineName(String lineName) {
		this.lineName = lineName;
	}


	public String getDisplayId() {
		return displayId;
	}


	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getBrefName() {
		return brefName;
	}


	public void setBrefName(String brefName) {
		this.brefName = brefName;
	}


	public String getDriverName() {
		return driverName;
	}


	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}


	public String getVehicleNumber() {
		return vehicleNumber;
	}


	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}


	public String getBusinessId() {
		return businessId;
	}


	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}


	public String getVehicleType() {
		return vehicleType;
	}


	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}


	public String getVehicleBrand() {
		return vehicleBrand;
	}


	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}


	public String getStatOutId() {
		return statOutId;
	}


	public void setStatOutId(String statOutId) {
		this.statOutId = statOutId;
	}


	public String getOperatTime() {
		return operatTime;
	}


	public void setOperatTime(String operatTime) {
		this.operatTime = operatTime;
	}


	public String getOperater() {
		return operater;
	}


	public void setOperater(String operater) {
		this.operater = operater;
	}


	public String getLeaseOrderNo() {
		return leaseOrderNo;
	}


	public void setLeaseOrderNo(String leaseOrderNo) {
		this.leaseOrderNo = leaseOrderNo;
	}


	public String getPassengerId() {
		return passengerId;
	}


	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}


	public String getLineClassId() {
		return lineClassId;
	}


	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
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


	public BigDecimal getOutMoney() {
		return outMoney;
	}


	public void setOutMoney(BigDecimal outMoney) {
		this.outMoney = outMoney;
	}


	public String getVehicleId() {
		return vehicleId;
	}


	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}


	public Integer getOutType() {
		return outType;
	}


	public void setOutType(Integer outType) {
		this.outType = outType;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getLineSubType_s() {
		return lineSubType_s;
	}


	public void setLineSubType_s(String lineSubTypeS) {
		lineSubType_s = lineSubTypeS;
	}


	public String getOutType_s() {
		return outType_s;
	}


	public void setOutType_s(String outTypeS) {
		outType_s = outTypeS;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	
}
