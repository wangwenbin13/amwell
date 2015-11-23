package com.pig84.ab.vo;

/**
 * 错误日志表
 */

@SuppressWarnings("all")
public class ErrLog implements java.io.Serializable {

	private String errId;
	private String cmdId;
	private String idType;
	private String detail;
	private String rectime;
	
	
	
	public String getRectime() {
		return rectime;
	}
	public void setRectime(String rectime) {
		this.rectime = rectime;
	}
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
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
