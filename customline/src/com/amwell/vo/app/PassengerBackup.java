package com.amwell.vo.app;

/**
 * 乘客_乘客信息表
 */
@SuppressWarnings("all")
public class PassengerBackup implements java.io.Serializable {


	private String backupId;	//主键
	private String passengerId;	//乘客ID
	private String lineBaseId;	//线路ID
	private String backupTime;	//收藏时间


	public String getBackupId() {
		return this.backupId;
	}

	public void setBackupId(String backupId) {
		this.backupId = backupId;
	}

	public String getPassengerId() {
		return this.passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getLineBaseId() {
		return this.lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getBackupTime() {
		return this.backupTime;
	}

	public void setBackupTime(String backupTime) {
		this.backupTime = backupTime;
	}

}