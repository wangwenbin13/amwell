package com.amwell.vo;

public class LineOperateLogVo {
	private String operationId; 
	private String lineBaseId;
	private String content;     //操作内容
	private String updateOn; 
	private String updateBy;
	private String userName;
	
	
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getLineBaseId() {
		return lineBaseId;
	}
	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
             
             
         

}
