package com.amwell.vo;

/**
 * 初始化数据
 * @author 胡双
 *
 */
public class InitializationAllData {
	
	/**
	 * 数据ID
	 */
	private String dataId;
	
	/**
	 * 数据别名
	 */
	private String dataAlias;
	
	/**
	 * 是否需要执行(N不执行，Y执行)
	 */
	private String isExecute;
	
	/**
	 * 系统类型
	 * @return
	 */
	private int sysType;
	

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getDataAlias() {
		return dataAlias;
	}

	public void setDataAlias(String dataAlias) {
		this.dataAlias = dataAlias;
	}

	public String getIsExecute() {
		return isExecute;
	}

	public void setIsExecute(String isExecute) {
		this.isExecute = isExecute;
	}

	public int getSysType() {
		return sysType;
	}

	public void setSysType(int sysType) {
		this.sysType = sysType;
	}

	
	
	
	
	
}
