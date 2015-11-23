package com.amwell.vo.gf;

import com.amwell.entity.base.BaseEntity;

/**
 * 优惠券实体
 */
public class CouponVo extends BaseEntity {

	private static final long serialVersionUID = -5334048689784498575L;

	private String couponId;// 主键ID

	private String[] giftIds;// 礼品ID

	private String link;// 链接地址

	private String couponName;// 优惠劵名称

	private int couponType;// 推广位置 0:全部渠道 1:APP渠道 2:微信渠道

	private String couponTypeStr;

	private int sendCouponType;// 发放方式 1:用户领取 2:系统发放

	private String sendCouponTypeStr;

	private int selectPass;// 选择用户 0:全部用户 1:新用户 2:15日未登录用户 3:自定义用户

	private String selectPassStr;

	private String teles;// 用户手机号码，账号和账号之间用；隔开

	private String upLineTime;// 上线时间

	private String downLineTime;// 下线时间

	private String effectiveTime;// 有效时间

	private String expirationTime;// 过期时间

	private String validity;// 有效期

	private long issueNum;// 发行数量

	private int limitNum;// 限领数量

	private int provinceCode;

	private int cityCode;

	private String cityName;

	private String createBy;// 创建人

	private String userName;

	private String createOn;// 创建时间

	private int giftCon;

	private String giftConStr;

	private String giftPriId;// 礼品主键ID

	private String giftId;

	private String giftName;

	private int giftType;

	private String giftTypeStr;

	private int giftValue;

	private String telephone;

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String[] getGiftIds() {
		return giftIds;
	}

	public void setGiftIds(String[] giftIds) {
		this.giftIds = giftIds;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public int getCouponType() {
		return couponType;
	}

	public void setCouponType(int couponType) {
		this.couponType = couponType;
		// 0:全部渠道 1:APP渠道 2:微信渠道
		if (0 == couponType) {
			this.couponTypeStr = "全部渠道";
		} else if (1 == couponType) {
			this.couponTypeStr = "APP渠道";
		} else if (2 == couponType) {
			this.couponTypeStr = "微信渠道";
		}
	}

	public int getSendCouponType() {
		return sendCouponType;
	}

	public void setSendCouponType(int sendCouponType) {
		this.sendCouponType = sendCouponType;
		// 发放方式 1:用户领取 2:系统发放
		if (1 == sendCouponType) {
			this.sendCouponTypeStr = "用户领取";
		} else if (2 == sendCouponType) {
			this.sendCouponTypeStr = "系统发放";
		}
	}

	public int getSelectPass() {
		return selectPass;
	}

	public void setSelectPass(int selectPass) {
		this.selectPass = selectPass;
		// 0:全部用户 1:新用户 2:15日未登录用户 3:自定义用户
		if (0 == selectPass) {
			this.selectPassStr = "全部用户";
		} else if (1 == selectPass) {
			this.selectPassStr = "新用户";
		} else if (2 == selectPass) {
			this.selectPassStr = "15日未登录用户 ";
		} else if (3 == selectPass) {
			this.selectPassStr = "自定义用户";
		} else if (4 == selectPass) {
			this.selectPassStr = "推荐有奖用户";
		}
	}

	public String getTeles() {
		return teles;
	}

	public void setTeles(String teles) {
		this.teles = teles;
	}

	public String getUpLineTime() {
		return upLineTime;
	}

	public void setUpLineTime(String upLineTime) {
		this.upLineTime = upLineTime;
	}

	public String getDownLineTime() {
		return downLineTime;
	}

	public void setDownLineTime(String downLineTime) {
		this.downLineTime = downLineTime;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
		this.validity = this.effectiveTime + "至" + this.expirationTime;
	}

	public long getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(long issueNum) {
		this.issueNum = issueNum;
	}

	public int getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}

	public int getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(int provinceCode) {
		this.provinceCode = provinceCode;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getCouponTypeStr() {
		return couponTypeStr;
	}

	public void setCouponTypeStr(String couponTypeStr) {
		this.couponTypeStr = couponTypeStr;
	}

	public String getSendCouponTypeStr() {
		return sendCouponTypeStr;
	}

	public void setSendCouponTypeStr(String sendCouponTypeStr) {
		this.sendCouponTypeStr = sendCouponTypeStr;
	}

	public String getSelectPassStr() {
		return selectPassStr;
	}

	public void setSelectPassStr(String selectPassStr) {
		this.selectPassStr = selectPassStr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public int getGiftCon() {
		return giftCon;
	}

	public void setGiftCon(int giftCon) {
		this.giftCon = giftCon;
		if (0 == giftCon) {
			this.giftConStr = "无";
		} else if (giftCon > 0) {
			this.giftConStr = "满" + giftCon + "可用";
		}

	}

	public String getGiftConStr() {
		return giftConStr;
	}

	public void setGiftConStr(String giftConStr) {
		this.giftConStr = giftConStr;
	}

	public String getGiftPriId() {
		return giftPriId;
	}

	public void setGiftPriId(String giftPriId) {
		this.giftPriId = giftPriId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public int getGiftType() {
		return giftType;
	}

	public void setGiftType(int giftType) {
		this.giftType = giftType;
		// 1:通用型礼品（班车、包车、拼车通用）2:上下班班车礼品 3:包车礼品 4:拼车礼品'5.推荐有奖礼品
		switch (giftType) {
		case 1:
			this.giftTypeStr = "通用型礼品";
			break;
		case 2:
			this.giftTypeStr = "上下班班车礼品";
			break;
		case 3:
			this.giftTypeStr = "包车礼品";
			break;
		case 4:
			this.giftTypeStr = "拼车礼品";
			break;
		case 5:
			this.giftTypeStr = "推荐有奖礼品";
			break;
		default:
			this.giftTypeStr = "未定义类型";
			break;
		}
	}

	public int getGiftValue() {
		return giftValue;
	}

	public void setGiftValue(int giftValue) {
		this.giftValue = giftValue;
	}

	public String getGiftTypeStr() {
		return giftTypeStr;
	}

	public void setGiftTypeStr(String giftTypeStr) {
		this.giftTypeStr = giftTypeStr;
	}

}
