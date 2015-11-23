package com.amwell.vo;

import java.util.ArrayList;
import java.util.List;

import com.amwell.entity.base.BaseEntity;
import com.amwell.utils.StringUtil;

public class UserLineEntity extends BaseEntity {

	private static final long serialVersionUID = -7211427578895405783L;

	private String applicationId;

	private String applicationTime;

	private String passengerId;

	private String nickName;

	private String telephone;

	private int lineType;

	private String lineTypeStr;

	private String startAddress;

	private String endAddress;

	private String startTime;

	private String remark;

	private int callbackStatus;

	private int auditStatus;// 审核状态；0:未审核 1:已审核

	private String callbackStatusStr;

	private String callbackContent;

	private String callbackLoginName;

	private String callbackDateTime;

	private int applyCount;

	/** 站点信息 **/
	private String stationNames;

	private String stationIds;

	private List<String> stationIdList;

	private List<StationInfo> stationList;
	
	private String cityName;//城市名称

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getLineType() {
		return lineType;
	}

	public void setLineType(int lineType) {
		this.lineType = lineType;
	}

	public String getLineTypeStr() {
		return lineTypeStr;
	}

	public void setLineTypeStr(String lineTypeStr) {
		this.lineTypeStr = lineTypeStr;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCallbackStatus() {
		return callbackStatus;
	}

	public void setCallbackStatus(int callbackStatus) {
		this.callbackStatus = callbackStatus;
	}

	public String getCallbackStatusStr() {
		return callbackStatusStr;
	}

	public void setCallbackStatusStr(String callbackStatusStr) {
		this.callbackStatusStr = callbackStatusStr;
	}

	public String getCallbackContent() {
		return callbackContent;
	}

	public void setCallbackContent(String callbackContent) {
		this.callbackContent = callbackContent;
	}

	public String getCallbackLoginName() {
		return callbackLoginName;
	}

	public void setCallbackLoginName(String callbackLoginName) {
		this.callbackLoginName = callbackLoginName;
	}

	public String getCallbackDateTime() {
		return callbackDateTime;
	}

	public void setCallbackDateTime(String callbackDateTime) {
		this.callbackDateTime = callbackDateTime;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public int getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(int applyCount) {
		this.applyCount = applyCount;
	}

	public String getStationNames() {
		return stationNames;
	}

	public void setStationNames(String stationNames) {
		this.stationNames = stationNames;
	}

	public void initStationList() {
		if (!StringUtil.isEmpty(this.stationNames)
				&& !StringUtil.isEmpty(this.stationIds)) {
			stationList = new ArrayList<StationInfo>();
			String[] stationNameArray = this.stationNames.split("-");
			String[] stationIdArray = this.stationIds.split(",");
			if(stationNameArray.length!=stationIdArray.length){
				System.out.println("====站点名称个数和站点id个数不一致，请检查占名称是是否包含‘-’特殊符号====");
			}
			for (int i = 0; i < stationNameArray.length; i++) {
				StationInfo lineStationInfo = new StationInfo();
				lineStationInfo.setId(stationIdArray[i]);
				lineStationInfo.setName(stationNameArray[i]);
				stationList.add(lineStationInfo);
			}
		}
	}

	public List<StationInfo> getStationList() {
		return stationList;
	}

	public void setStationList(List<StationInfo> stationList) {
		this.stationList = stationList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStationIds() {
		return stationIds;
	}

	public void setStationIds(String stationIds) {
		this.stationIds = stationIds;
	}

	public List<String> getStationIdList() {
		return stationIdList;
	}

	public void setStationIdList(List<String> stationIdList) {
		this.stationIdList = stationIdList;
	}

}
