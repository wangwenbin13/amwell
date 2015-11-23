package com.amwell.vo.coupon;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合券信息
 * @author gxt
 *
 */
public class CouponGroup {
	private String id;//int(11) NOT NULL主键ID
	private String couponGroupID;//int(10) NOT NULL组合券批次号
	private String couponGroupName;//varchar(35) NOT NULL组合券名称
	private String couponGroupCount;//int(10) NOT NULL组合券总数
	private String averageNum;//int(10) NULL组合券人均领取量
	private String createBy;//varchar(35) NOT NULL创建人
	private String userName;//用户名称
	private String createOn;//varchar(19) NOT NULL创建时间
	private String updateBy;//varchar(35) NOT NULL修改人
	private String updateOn;//varchar(19) NOT NULL修改时间
	
	private String couponIds;//存页面所选优惠券id及优惠券个数（格式：couponId1,Id1个数;couponId2,Id2个数;couponId3,Id3个数）
	private String totalAmount;//总金额
	
	private List<CouponInfo> couponList=new ArrayList<CouponInfo>();
	
	private List<CouponStatisticsVo> statisticsList=new ArrayList<CouponStatisticsVo>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCouponGroupCount() {
		return couponGroupCount;
	}
	public void setCouponGroupCount(String couponGroupCount) {
		this.couponGroupCount = couponGroupCount;
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
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}
	public String getCouponGroupName() {
		return couponGroupName;
	}
	public void setCouponGroupName(String couponGroupName) {
		this.couponGroupName = couponGroupName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCouponIds() {
		return couponIds;
	}
	public void setCouponIds(String couponIds) {
		this.couponIds = couponIds;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCouponGroupID() {
		return couponGroupID;
	}
	public void setCouponGroupID(String couponGroupID) {
		this.couponGroupID = couponGroupID;
	}
	public List<CouponInfo> getCouponList() {
		return couponList;
	}
	public void setCouponList(List<CouponInfo> couponList) {
		this.couponList = couponList;
	}
	public String getAverageNum() {
		return averageNum;
	}
	public void setAverageNum(String averageNum) {
		this.averageNum = averageNum;
	}
	public List<CouponStatisticsVo> getStatisticsList() {
		return statisticsList;
	}
	public void setStatisticsList(List<CouponStatisticsVo> statisticsList) {
		this.statisticsList = statisticsList;
	}
}
