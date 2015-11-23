package com.amwell.vo.bc;

public class BcBiddingCarsLineVo {
	
	private String schedule_id;//
	
	private String bidding_id;//竞价ID
	
	private String carType;//车辆类型：大巴、中巴、小巴、商务车、过港车、轿车、其他
	
	private Integer carSeats;//车辆座位数
	
	private Integer carAge;//车龄
	
	private Integer totalCars;//车辆数
	
	private String createBy;//创建人，报价的商户ID
	
	private String createOn;//创建时间

	public String getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(String schedule_id) {
		this.schedule_id = schedule_id;
	}

	public String getBidding_id() {
		return bidding_id;
	}

	public void setBidding_id(String bidding_id) {
		this.bidding_id = bidding_id;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Integer getCarSeats() {
		return carSeats;
	}

	public void setCarSeats(Integer carSeats) {
		this.carSeats = carSeats;
	}

	public Integer getCarAge() {
		return carAge;
	}

	public void setCarAge(Integer carAge) {
		this.carAge = carAge;
	}

	public Integer getTotalCars() {
		return totalCars;
	}

	public void setTotalCars(Integer totalCars) {
		this.totalCars = totalCars;
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
