package com.bean.games;

/**
 *  /\_/\
 * (=+_+=)
 *   ( ).
 * 周年庆的购票记录
 * @author sjx
 */
public class Anniversary {
	// 用户Id
	private String uid;
	// 用户手机号
	private String tel;
	// 创建时间
	private long createTime;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
