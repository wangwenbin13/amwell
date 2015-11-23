package com.amwell.action.line;

import com.amwell.entity.base.BaseEntity;

/**
 * 线路查询表单
 * 
 * @author huxiaojun
 * 
 */
public class LineSearch extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3097192792072499493L;

	private String lineName;

	private String siteName;

	/**
	 * 商户简称
	 */
	private String brefName;

	private String lineType;
	
	private String lineStatus;

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getBrefName() {
		return brefName;
	}

	public void setBrefName(String brefName) {
		this.brefName = brefName;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}

}
