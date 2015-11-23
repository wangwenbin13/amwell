package com.amwell.entity;

import java.math.BigDecimal;
import java.util.List;

import com.amwell.vo.LineClassEntity;

/**
 * 订单Vo
 * @author wangwenbin
 *
 * 2014-8-19
 */
public class LeaseOrderModelVo {

	//--------- lease_base_info ---------
	/**
	 * 订单ID
	 */
	private String leaseOrderId;
	
	/**
	 * 订单号
	 */
	private String leaseOrderNo;
	
	/**
	 * 线路Id
	 */
	private String lineBaseId;
	
	/**
	 * 购买类型 购买类型 
	 * 0:按次购买        1:包月购买
	 */
	private Integer buyType;
	private String buyType_s;
	
	/**
	 * 上车站点
	 */
	private String ustation;
	
	/**
	 * 下车站点
	 */
	private String dstation;
	
	/**
	 * 省份
	 */
	private String provinceCode;
	
	/**
	 * 城市
	 */
	private String cityCode;
	
	/**
	 * 支付状态
	 * 0:待支付          1:交易成功        2:已失效   
	 */
	private Integer payStatus; 
	private String payStatus_s;
	
	/**
	 * 费用总计
	 */
	 private BigDecimal totalCount;
	 
	 /**
	  * 乘车次数
	  */
	 private Integer ridesInfo;
	
	/**
	 * 订单时间
	 */
	private String leaseOrderTime;
	
	/**
	 * 订单来源
	 * 订单来源表 0:APP 1:微信 2：pc
	 */
	private Integer sourceFrom;
	private String sourceFrom_s;
	
	
	//--------------- mgr_business ------------
	/**
	 * 商家  商户简称
	 */
	private String brefName;
	
	
	//--------------- passenger_info ----------
	/**
	 * 乘客昵称
	 */
	private String nickName;
	
	/**
	 * 乘客回显ID
	 */
	private String displayId;
	
	/**
	 * 乘客电话
	 */
	private String telephone;
	
	
	
	//--------------- line_base_info ------------
	/**
	 * 线路类型  0:上下班  1:旅游    
	 */
	private String lineSubType;
	
	/**
	 * 线路名称
	 */
	private String lineName;
	
	//--------------- lease_pay ------------
	/**
	 * 支付方式   购买方式 
	 * 0:无              1:余额支付        2:财付通          3:银联            4:支付宝
	 */
	private Integer payModel;
	private String payModel_s;
	
	/**
	 * 支付额度  金额
	 */
	private BigDecimal payMoney;
	
	/**
	 * 乘车班次信息
	 * @return
	 */
	private List<LineClassEntity> lineClassEntities;
	
	/**
	 * 班次时间
	 * @return
	 */
	private String orderStartTime;
	
	/**
	 * 是否支付（0：否   1：是）
	 * @return
	 */
	private Integer ispay;
	
	/**站点信息**/
	private String stationNames;
	

	public String getLeaseOrderId() {
		return leaseOrderId;
	}

	public void setLeaseOrderId(String leaseOrderId) {
		this.leaseOrderId = leaseOrderId;
	}

	public String getLeaseOrderNo() {
		return leaseOrderNo;
	}

	public void setLeaseOrderNo(String leaseOrderNo) {
		this.leaseOrderNo = leaseOrderNo;
	}

	public Integer getBuyType() {
		return buyType;
	}

	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getBrefName() {
		return brefName;
	}

	public void setBrefName(String brefName) {
		this.brefName = brefName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public String getLineSubType() {
		return lineSubType;
	}

	public void setLineSubType(String lineSubType) {
		this.lineSubType = lineSubType;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Integer getPayModel() {
		return payModel;
	}

	public void setPayModel(Integer payModel) {
		this.payModel = payModel;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public String getLeaseOrderTime() {
		return leaseOrderTime;
	}

	public void setLeaseOrderTime(String leaseOrderTime) {
		this.leaseOrderTime = leaseOrderTime;
	}

	public String getBuyType_s() {
		return buyType_s;
	}

	public void setBuyType_s(String buyTypeS) {
		buyType_s = buyTypeS;
	}

	public String getPayStatus_s() {
		return payStatus_s;
	}

	public void setPayStatus_s(String payStatusS) {
		payStatus_s = payStatusS;
	}

	public String getPayModel_s() {
		return payModel_s;
	}

	public void setPayModel_s(String payModelS) {
		payModel_s = payModelS;
	}

	public Integer getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(Integer sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<LineClassEntity> getLineClassEntities() {
		return lineClassEntities;
	}

	public void setLineClassEntities(List<LineClassEntity> lineClassEntities) {
		this.lineClassEntities = lineClassEntities;
	}

	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public BigDecimal getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(BigDecimal totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getRidesInfo() {
		return ridesInfo;
	}

	public void setRidesInfo(Integer ridesInfo) {
		this.ridesInfo = ridesInfo;
	}

	public String getUstation() {
		return ustation;
	}

	public void setUstation(String ustation) {
		this.ustation = ustation;
	}

	public String getDstation() {
		return dstation;
	}

	public void setDstation(String dstation) {
		this.dstation = dstation;
	}

	public String getSourceFrom_s() {
		return sourceFrom_s;
	}

	public void setSourceFrom_s(String sourceFromS) {
		sourceFrom_s = sourceFromS;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getIspay() {
		return ispay;
	}

	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}

	public String getStationNames() {
		return stationNames;
	}

	public void setStationNames(String stationNames) {
		this.stationNames = stationNames;
	}

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}
	
}
