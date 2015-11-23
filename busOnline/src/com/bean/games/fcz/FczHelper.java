package com.bean.games.fcz;

/**
 * 参与活动的帮手
 * 
 * @author shawn.zheng
 * 
 */
public class FczHelper {
	/**
	 * 主键
	 */
	private String storeId;

	/**
	 * 盟主的微信用户Id
	 */
	private String ownerOpenId;
	/**
	 * 帮手的微信用户Id
	 */
	private String helperOpenId;
	/**
	 * 帮手的昵称
	 */
	private String nickname;

	/**
	 * 帮助者的微信头像
	 */
	private String photoImage;

	/**
	 * 帮忙获得的分数
	 */
	private int point;
	/**
	 * 帮忙时间
	 */
	private String helpTime;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getOwnerOpenId() {
		return ownerOpenId;
	}

	public void setOwnerOpenId(String ownerOpenId) {
		this.ownerOpenId = ownerOpenId;
	}

	public String getHelperOpenId() {
		return helperOpenId;
	}

	public void setHelperOpenId(String helperOpenId) {
		this.helperOpenId = helperOpenId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getHelpTime() {
		return helpTime;
	}

	public void setHelpTime(String helpTime) {
		this.helpTime = helpTime;
	}

	public String getPhotoImage() {
		return photoImage;
	}

	public void setPhotoImage(String photoImage) {
		this.photoImage = photoImage;
	}

}
