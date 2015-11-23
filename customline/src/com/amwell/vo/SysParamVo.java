package com.amwell.vo;


import com.amwell.entity.base.BaseEntity;

public class SysParamVo extends BaseEntity {

	private static final long serialVersionUID = 1514558203686230072L;

	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 参数名称
	 */
	private String paramName;

	/**
	 * 参数值
	 */
	private String paramValue;

	/**
	 * 参数类型
	 */
	private short paramType;

	/**
	 * 参数状态 0:无效 1:有效 默认为有效
	 */
	private short paramStatus=1;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 创建时间
	 */
	private String createOn;
	
	/**
	 * 更新人
	 */
	private String updateBy;
	
	/**
	 * 更新时间
	 */
	private String updateOn;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public short getParamType() {
		return paramType;
	}

	public void setParamType(short paramType) {
		this.paramType = paramType;
	}

	public short getParamStatus() {
		return paramStatus;
	}

	public void setParamStatus(short paramStatus) {
		this.paramStatus = paramStatus;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
