package com.amwell.vo;

/**
 * 
 * @author wangwenbin
 *
 * 2014-9-23
 */
/**
 * 错误信息记录表
 */
public class Errlog {

	/**
	 * 主键ID
	 */
	private String errId;
	
	/**
	 * 
	 */
	private String cmdId;
	
	/**
	 * 
	 */
	private Integer idType;
	
	/**
	 * 记录时间
	 */
	private String rectime;

	public String getErrId() {
		return errId;
	}

	public void setErrId(String errId) {
		this.errId = errId;
	}

	public String getCmdId() {
		return cmdId;
	}

	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getRectime() {
		return rectime;
	}

	public void setRectime(String rectime) {
		this.rectime = rectime;
	}
	
	
}
