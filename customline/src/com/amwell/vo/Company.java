package com.amwell.vo;

import java.util.ArrayList;
import java.util.List;

import com.amwell.entity.base.BaseEntity;

/**
 * 客户专线公司
 * @author 胡双
 *
 */
public class Company extends BaseEntity{
	
	/**
	 * 公司ID
	 */
	private String companyId;
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 省编号
	 */
	private String companyProvince;
	/**
	 * 城市编号
	 */
	private String companyCity;
	
	/**
	 * 线路总数
	 */
	private Integer lineCount;
	
	/**
	 * 线路ids
	 * @return
	 */
	private List<String> lineIdList = new ArrayList<String>();
	
	/**
	 * 员工总数
	 */
	private Integer passengerCount;
	
	/**
	 * 员工总数list
	 * @return
	 */
	private List<String> passengerTelList = new ArrayList<String>();
	
	/**
	 * 创建人
	 * @return
	 */
	private String createBy;
	
	/**
	 * 创建时间
	 * @return
	 */
	private String createOn;
	
	/**
	 * 线路的ID
	 * @return
	 */
	private String lineBaseId;
	
	/**
	 * 乘客的电话号码
	 * @return
	 */
	private String telephone;
	
	/**
	 * 返回线路对象包括ID，名称，起点终点的字符串
	 * @return
	 */
	private String lineStr;
	
	/**
	 * 修改人
	 */
	private String updateBy;
	
	/**
	 * 修改时间
	 */
	private String updateOn;
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCity() {
		return companyCity;
	}

	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}

	

	public Integer getLineCount() {
		return lineCount;
	}

	public void setLineCount(Integer lineCount) {
		this.lineCount = lineCount;
	}

	public Integer getPassengerCount() {
		return passengerCount;
	}

	public void setPassengerCount(Integer passengerCount) {
		this.passengerCount = passengerCount;
	}

	public String getCompanyProvince() {
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	public List<String> getLineIdList() {
		return lineIdList;
	}

	public void setLineIdList(List<String> lineIdList) {
		this.lineIdList = lineIdList;
	}

	public List<String> getPassengerTelList() {
		return passengerTelList;
	}

	public void setPassengerTelList(List<String> passengerTelList) {
		this.passengerTelList = passengerTelList;
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

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLineStr() {
		return lineStr;
	}

	public void setLineStr(String lineStr) {
		this.lineStr = lineStr;
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
	
	
	
}
