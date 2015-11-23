package com.amwell.entity;

import java.io.Serializable;

public class UserVo implements Serializable {
	
	private static final long serialVersionUID = -8991192612173721036L;

	private String rec_id;
	private String username;
	private String pwd;

	private String realname;
	private String cdate;

	public String getRec_id() {
		return rec_id;
	}

	public void setRec_id(String recId) {
		rec_id = recId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

}
