package com.amwell.vo.pc;

import java.util.List;

import org.springframework.util.StringUtils;

import com.amwell.entity.base.BaseEntity;

public class PassengerLineVo extends BaseEntity {

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
	private String lineType;

	/**
	 * 1:单程 2：往返
	 */
	private String lineSubType;
	
    /**
     * 线路真实价格
     */
	private String realPrice;
	
	/**
	 * 去程时间
	 * 如果APP端显示的为上下班，只显示时分模式；长途类型显示日期+时间
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

	private String displayId;

	private String nickName;

	private String telephone;

	/**
	 * 线路状态 1：已发布 2：已删除 3.已过期
	 */
	private Integer lineStatus;
	
	private String lineStatusStr;
	
	/**
	 * 线路开关标识（0.已关闭  1.已开启）
	 */
	private Integer lineOnOff;
	
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

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		if(StringUtils.hasText(lineType)){
			if("1".equals(lineType)){
				this.lineType = "上下班";
			}else if("2".equals(lineType)){
				this.lineType="长途";
			}
		}
		
	}

	public String getLineSubType() {
		return lineSubType;
	}

	public void setLineSubType(String lineSubType) {
		if(StringUtils.hasText(lineSubType)){
			if("1".equals(lineSubType)){
				this.lineSubType="单程";
			}else if("2".equals(lineSubType)){
				this.lineSubType="往返";
			}
		}
	}
	

	public String getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
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
		if(false==StringUtils.hasText(returnTime)){
			this.returnTime="--";
		}else{
			this.returnTime = returnTime;
		}
	}

	public String getRepeatTime() {
		return repeatTime;
	}

	public void setRepeatTime(String repeatTime) {
		if(false==StringUtils.hasText(repeatTime)){
			this.repeatTime="--";
		}else{
			this.repeatTime = repeatTime;
		}
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

	public Integer getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(Integer lineStatus) {
		this.lineStatus = lineStatus;
		//1：已发布 2：已删除 3.已过期
		if(null!=lineStatus&&1==lineStatus){
			this.lineStatusStr="已发布";
		}
		if(null!=lineStatus&&2==lineStatus){
			this.lineStatusStr="已删除";
		}
		if(null!=lineStatus&&3==lineStatus){
			this.lineStatusStr="已过期";
		}
	}

	public Integer getLineOnOff() {
		return lineOnOff;
	}

	public void setLineOnOff(Integer lineOnOff) {
		this.lineOnOff = lineOnOff;
		if(null!=lineOnOff&&lineOnOff==0){
			this.lineOnOffStr="已关闭";
		}
		if(null!=lineOnOff&&lineOnOff==1){
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
