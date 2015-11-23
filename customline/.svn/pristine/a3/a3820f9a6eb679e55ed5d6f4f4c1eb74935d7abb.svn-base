package com.amwell.vo.pc;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.StringUtils;

import com.amwell.entity.base.BaseEntity;

public class CarOwnerLineVo extends BaseEntity {

	private static final long serialVersionUID = 1939389839833458697L;

	//站点id字符串
	private String stationInfoes;
	
	private String pcLineId;

	private int provinceCode;

	private String provinceName;

	private int cityCode;

	private String cityName;

	/**
	 * 线路类型 1：上下班 2：长途
	 */
	private int lineType;

	private String lineTypeStr;

	/**
	 * 1:单程 2：往返
	 */
	private int lineSubType;

	private String lineSubTypeStr;

	/**
	 * 去程时间
	 */
	private String goTime;

	/**
	 * 返程时间
	 */
	private String returnTime;

	/**
	 * 重复时间 多个日期之间以英文逗号分隔，如周一,周二则存值为1,2
	 */
	private String repeatTime;

	private String repeatTimeStr;

	private String carNumber;

	private String carModel;

	private int carSeats;

	private String passengerId;

	private String remark;

	private String createOn;
	/**
	 * 发布者标识 1：乘客 2：车主
	 */
	private int publisherFlag;

	private String startStationName;

	private String endStationName;

	private List<PcLineStationVo> stationList;

	private BigDecimal realPrice;// 报价

	private String displayId;// 用户ID

	private String nickName;// 用户昵称

	private String telephone;// 用户电话

	private String lineClass;// 线路类型

	private String lineSubClass;// 线路用途

	private Integer lineStatus;// 线路状态 1：已发布 2：已删除 3.已过期

	private String lineStatusStr;

	private Integer lineOnOff;// 线路开关标识（0.已关闭 1.已开启）

	private String lineOnOffStr;

	public String getPcLineId() {
		return pcLineId;
	}

	public void setPcLineId(String pcLineId) {
		this.pcLineId = pcLineId;
	}

	public int getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(int provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getLineType() {
		return lineType;
	}

	public void setLineType(int lineType) {
		this.lineType = lineType;
		if (lineType == 1) {
			this.lineTypeStr = "上下班";
		} else if (lineType == 2) {
			this.lineTypeStr = "长途";
		}
	}

	public int getLineSubType() {
		return lineSubType;
	}

	public void setLineSubType(int lineSubType) {
		this.lineSubType = lineSubType;
		if (lineSubType == 1) {
			this.lineSubTypeStr = "单程";
		} else if (lineSubType == 2) {
			this.lineSubTypeStr = "往返";
		}
	}

	public String getGoTime() {
		return goTime;
	}

	public void setGoTime(String goTime) {
		this.goTime = goTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		if (StringUtils.hasText(returnTime) == false) {
			this.returnTime = "－－";
		} else {
			this.returnTime = returnTime;
		}
	}

	public String getRepeatTime() {
		return repeatTime;
	}

	public void setRepeatTime(String repeatTime) {
		this.repeatTime = repeatTime;
		// if (StringUtils.hasText(repeatTime)) {
		// String[] repeatTimes = repeatTime.split(",");
		// StringBuffer result = new StringBuffer();
		// for (int i = 0; i < repeatTimes.length; i++) {
		// result.append(changeWeekDay(repeatTimes[i]));
		// if (i < repeatTimes.length - 1) {
		// result.append("、");
		// }
		// }
		// this.repeatTimeStr = result.toString();
		// } else {
		// this.repeatTimeStr = repeatTime;
		// }
		this.repeatTimeStr = repeatTime;
	}

	public String changeWeekDay(String s) {
		Integer intDay = Integer.parseInt(s);
		String result = null;
		switch (intDay) {
		case 1:
			result = "周一";
			break;
		case 2:
			result = "周二";
			break;
		case 3:
			result = "周三";
			break;
		case 4:
			result = "周四";
			break;
		case 5:
			result = "周五";
			break;
		case 6:
			result = "周六";
			break;
		case 7:
			result = "周日";
			break;
		default:
			result = "";
			break;
		}
		return result;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		if (null == carNumber || "".equals(carNumber)) {
			this.carNumber = "－－";
		} else {
			this.carNumber = carNumber;
		}
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		if (null == carModel || "".equals(carModel)) {
			this.carModel = "－－";
		} else {
			this.carModel = carModel;
		}

	}

	public int getCarSeats() {
		return carSeats;
	}

	public void setCarSeats(int carSeats) {
		this.carSeats = carSeats;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public int getPublisherFlag() {
		return publisherFlag;
	}

	public void setPublisherFlag(int publisherFlag) {
		this.publisherFlag = publisherFlag;
	}

	public String getStartStationName() {
		return startStationName;
	}

	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}

	public String getEndStationName() {
		return endStationName;
	}

	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}

	public List<PcLineStationVo> getStationList() {
		return stationList;
	}

	public void setStationList(List<PcLineStationVo> stationList) {
		this.stationList = stationList;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public String getLineClass() {
		return lineClass;
	}

	public void setLineClass(String lineClass) {
		this.lineClass = lineClass;
	}

	public String getLineSubClass() {
		return lineSubClass;
	}

	public void setLineSubClass(String lineSubClass) {
		this.lineSubClass = lineSubClass;
	}

	public String getLineTypeStr() {
		return lineTypeStr;
	}

	public void setLineTypeStr(String lineTypeStr) {
		this.lineTypeStr = lineTypeStr;
	}

	public String getLineSubTypeStr() {
		return lineSubTypeStr;
	}

	public void setLineSubTypeStr(String lineSubTypeStr) {
		this.lineSubTypeStr = lineSubTypeStr;
	}

	public String getRepeatTimeStr() {
		return repeatTimeStr;
	}

	public void setRepeatTimeStr(String repeatTimeStr) {
		this.repeatTimeStr = repeatTimeStr;
	}

	public Integer getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(Integer lineStatus) {
		this.lineStatus = lineStatus;
		if (lineStatus == 1) {
			lineStatusStr = "已发布";
		} else if (lineStatus == 2) {
			lineStatusStr = "已删除";
		} else if (lineStatus == 3) {
			lineStatusStr = "已过期";
		}
	}

	public Integer getLineOnOff() {
		return lineOnOff;
	}

	public void setLineOnOff(Integer lineOnOff) {
		this.lineOnOff = lineOnOff;
		if(null!=lineOnOff&&0==lineOnOff.intValue()){
			this.lineOnOffStr="已关闭";
		}
		if(null!=lineOnOff&&1==lineOnOff.intValue()){
			this.lineOnOffStr="已开启";
		}
	}

	public String getLineStatusStr() {
		return lineStatusStr;
	}

	public void setLineStatusStr(String lineStatusStr) {
		this.lineStatusStr = lineStatusStr;

	}

	public String getLineOnOffStr() {
		return lineOnOffStr;
	}

	public void setLineOnOffStr(String lineOnOffStr) {
		this.lineOnOffStr = lineOnOffStr;
	}

	public String getStationInfoes() {
		return stationInfoes;
	}

	public void setStationInfoes(String stationInfoes) {
		this.stationInfoes = stationInfoes;
	}
}
