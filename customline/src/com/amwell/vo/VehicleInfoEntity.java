package com.amwell.vo;
//default package

/**
 * VehicleInfo entity. @author MyEclipse Persistence Tools
 * 车辆信息表
 */

public class VehicleInfoEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8771992118186338135L;
	private String vehicleId;//车辆ID
	private String vehicleNumber;//车牌号
	private String vehicleNo;//车辆编号
	private Short vehicleType;//车辆类型 0:大把 1:中巴 2:小巴
	private String vehicleBrand;//品牌
	private String vehicleDepart;//车系
	private String vehicleModel;//车型
	private Integer vehicleSeats;//座位数
	private String vehicleColor;//颜色
	private String vehicleBuyTime;//购买日期
	private String remark;//备注
	private String vehicleUrl;//车辆图片URL
	private String businessId;//商户ID
	private String createBy;//创建人
	private String createOn;//创建时间
	private String updateBy;//修改人
	private String updateOn;//修改时间

	// Constructors

	/** default constructor */
	public VehicleInfoEntity() {
	}

	/** minimal constructor */
	public VehicleInfoEntity(String vehicleId, String vehicleNumber,
			String vehicleBrand, String vehicleModel, String businessId) {
		this.vehicleId = vehicleId;
		this.vehicleNumber = vehicleNumber;
		this.vehicleBrand = vehicleBrand;
		this.vehicleModel = vehicleModel;
		this.businessId = businessId;
	}

	/** full constructor */
	public VehicleInfoEntity(String vehicleId, String vehicleNumber,
			String vehicleNo, Short vehicleType, String vehicleBrand,
			String vehicleDepart, String vehicleModel, Integer vehicleSeats,
			String vehicleColor, String vehicleBuyTime, String remark,
			String vehicleUrl, String businessId, String createBy,
			String createOn, String updateBy, String updateOn) {
		this.vehicleId = vehicleId;
		this.vehicleNumber = vehicleNumber;
		this.vehicleNo = vehicleNo;
		this.vehicleType = vehicleType;
		this.vehicleBrand = vehicleBrand;
		this.vehicleDepart = vehicleDepart;
		this.vehicleModel = vehicleModel;
		this.vehicleSeats = vehicleSeats;
		this.vehicleColor = vehicleColor;
		this.vehicleBuyTime = vehicleBuyTime;
		this.remark = remark;
		this.vehicleUrl = vehicleUrl;
		this.businessId = businessId;
		this.createBy = createBy;
		this.createOn = createOn;
		this.updateBy = updateBy;
		this.updateOn = updateOn;
	}

	// Property accessors

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleNumber() {
		return this.vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleNo() {
		return this.vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Short getVehicleType() {
		return this.vehicleType;
	}

	public void setVehicleType(Short vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleBrand() {
		return this.vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleDepart() {
		return this.vehicleDepart;
	}

	public void setVehicleDepart(String vehicleDepart) {
		this.vehicleDepart = vehicleDepart;
	}

	public String getVehicleModel() {
		return this.vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public Integer getVehicleSeats() {
		return this.vehicleSeats;
	}

	public void setVehicleSeats(Integer vehicleSeats) {
		this.vehicleSeats = vehicleSeats;
	}

	public String getVehicleColor() {
		return this.vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleBuyTime() {
		return this.vehicleBuyTime;
	}

	public void setVehicleBuyTime(String vehicleBuyTime) {
		this.vehicleBuyTime = vehicleBuyTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVehicleUrl() {
		return this.vehicleUrl;
	}

	public void setVehicleUrl(String vehicleUrl) {
		this.vehicleUrl = vehicleUrl;
	}

	public String getBusinessId() {
		return this.businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateOn() {
		return this.createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateOn() {
		return this.updateOn;
	}

	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}

}