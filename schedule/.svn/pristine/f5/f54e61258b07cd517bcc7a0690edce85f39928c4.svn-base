package com.amwell.vo.bc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.amwell.entity.base.BaseEntity;

/**
 * 商家已报价列表
 * @author Administrator
 *
 */
public class AlreadyQuoteLineVo extends BaseEntity{
	
	
	private static final long serialVersionUID = 6987083809477482524L;
	
	private String id;
	//业务类型
	private String businessType;
	//乘客人数
	private int totalPassengers;
	//车辆数量
	private int totalCars;
	//发布时间
	private String createOn;
	//出车时间
	private String startTime;
	//返程时间
	private String returnTime;
	//车龄
	private int carAge;
	//起点
	private String beginAddress;
	//终点
	private String endAddress;
	//包车方式
	private int charteredMode;
	//行程备注
	private String journeyRemark;
	//是否需要发票
	private int needInvoice;
	//发票抬头
	private String invoiceHeader;
	// 商家报价状态：0：待报价 1:已报价 2:已过期
	private int offerStatus;
	
	/** 报价总额 **/
	// 基本费用
	private BigDecimal basicCost;
	
	// 司机餐费
	private BigDecimal driverMealCost;
	
	// 司机住宿费用
	private BigDecimal driverHotelCost;
	
	// 油费
	private BigDecimal oilCost;
	
	// 报价总额
	private BigDecimal totalCost;
	
	// 司机工作时间
	private BigDecimal driverWorkHour;
	
	//司机超时工作的超时费
	private BigDecimal overtimeCost;
	
	public String getInvoiceHeader() {
		return invoiceHeader;
	}

	public void setInvoiceHeader(String invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}

	// 每天行驶的公里数
	private BigDecimal limitMileage;
	
	// 超出公里的费用
	private BigDecimal overmileageCost;
	
	// 退票关联ID
	private String returnRuleId;
	
	/** 车辆信息 **/
	private List<BusinessCarInfo> carInfo = new ArrayList<BusinessCarInfo>();
	
	/** 退票须知 **/
	// 不退票的时间
	private String noReturn;
	
	// 退款时间一
	private String returnOneTime;
	
	// 退款时间一的百分比
	private String returnOnePer;
	
	// 退款时间二
	private String returnTwoTime;
	
	// 退款时间二的百分比
	private String returnTwoPer;
	
	// 附加服务
	private String additionalServices;
	
	//商家备注
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public int getTotalPassengers() {
		return totalPassengers;
	}

	public void setTotalPassengers(int totalPassengers) {
		this.totalPassengers = totalPassengers;
	}

	public int getTotalCars() {
		return totalCars;
	}

	public void setTotalCars(int totalCars) {
		this.totalCars = totalCars;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public int getCarAge() {
		return carAge;
	}

	public void setCarAge(int carAge) {
		this.carAge = carAge;
	}

	public String getBeginAddress() {
		return beginAddress;
	}

	public void setBeginAddress(String beginAddress) {
		this.beginAddress = null!=beginAddress?beginAddress.trim().replaceAll("[\\t\\n\\r]", ""):beginAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = null!=endAddress?endAddress.trim().replaceAll("[\\t\\n\\r]", ""):endAddress;
	}

	public int getCharteredMode() {
		return charteredMode;
	}

	public void setCharteredMode(int charteredMode) {
		this.charteredMode = charteredMode;
	}

	public String getJourneyRemark() {
		return journeyRemark;
	}

	public void setJourneyRemark(String journeyRemark) {
		this.journeyRemark = journeyRemark;
	}

	public BigDecimal getBasicCost() {
		return basicCost;
	}

	public void setBasicCost(BigDecimal basicCost) {
		this.basicCost = basicCost;
	}

	public BigDecimal getDriverMealCost() {
		return driverMealCost;
	}

	public void setDriverMealCost(BigDecimal driverMealCost) {
		this.driverMealCost = driverMealCost;
	}

	public BigDecimal getDriverHotelCost() {
		return driverHotelCost;
	}

	public void setDriverHotelCost(BigDecimal driverHotelCost) {
		this.driverHotelCost = driverHotelCost;
	}

	public BigDecimal getOilCost() {
		return oilCost;
	}

	public void setOilCost(BigDecimal oilCost) {
		this.oilCost = oilCost;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getDriverWorkHour() {
		return driverWorkHour;
	}

	public void setDriverWorkHour(BigDecimal driverWorkHour) {
		this.driverWorkHour = driverWorkHour;
	}

	public BigDecimal getOvertimeCost() {
		return overtimeCost;
	}

	public void setOvertimeCost(BigDecimal overtimeCost) {
		this.overtimeCost = overtimeCost;
	}

	public BigDecimal getLimitMileage() {
		return limitMileage;
	}

	public void setLimitMileage(BigDecimal limitMileage) {
		this.limitMileage = limitMileage;
	}

	public BigDecimal getOvermileageCost() {
		return overmileageCost;
	}

	public void setOvermileageCost(BigDecimal overmileageCost) {
		this.overmileageCost = overmileageCost;
	}

	public List<BusinessCarInfo> getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(List<BusinessCarInfo> carInfo) {
		this.carInfo = carInfo;
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

	public String getReturnRuleId() {
		return returnRuleId;
	}

	public void setReturnRuleId(String returnRuleId) {
		this.returnRuleId = returnRuleId;
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

	public int getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(int offerStatus) {
		this.offerStatus = offerStatus;
	}

	public int getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(int needInvoice) {
		this.needInvoice = needInvoice;
	}
	
	
}
