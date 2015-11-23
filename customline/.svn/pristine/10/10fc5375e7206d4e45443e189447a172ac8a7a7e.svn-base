package com.amwell.vo.gf;

import java.math.BigDecimal;

import com.amwell.entity.base.BaseEntity;

/**
 * 礼品实体类
 * @author huxiaojun
 *
 */
public class GiftVo extends BaseEntity {

	private static final long serialVersionUID = 8447943841032584246L;

	private String giftPriId;// 主键ID
	
	private String giftId;// 礼品ID
	
	private String giftName;// 礼品名称
	
	private int giftType;// 礼类类型:1:通用型礼品（班车、包车、拼车通用）2:上下班班车礼品 3:包车礼品 4:拼车礼品
	
	private Integer giftValue;// = new BigDecimal("0.00");// 礼品面值
	
	private Integer giftCon;// = new BigDecimal("0.00");// 约束条件
	
	private int state = 0;// 使用状态 0:未使用 1:使用中
	
	private String operatorId;// 操作人ID
	
	private String operateTime;// 操作时间
	
	private String loginName; // 管理员名称
	
	private String giftConStr;//约束条件字段
	
	private String giftValueStr;//面值字段
	
	private String giftTypeStr;//类型字段
	
	private String stateStr;//状态字段

	public String getGiftPriId() {
		return giftPriId;
	}

	public void setGiftPriId(String giftPriId) {
		this.giftPriId = giftPriId;
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
		if(giftType==1){
			this.giftTypeStr="通用型礼品";
		}else if(giftType==2){
			this.giftTypeStr="上下班班车礼品";
		}else if(giftType==3){
			this.giftTypeStr="包车礼品";
		}else if(giftType==4){
			this.giftTypeStr="拼车礼品";
		}else if(giftType==5){
			this.giftTypeStr="推荐有奖礼品";
		}
	}

	public Integer getGiftValue() {
		return giftValue;
	}

	public void setGiftValue(Integer giftValue) {
		this.giftValue = giftValue;
		this.giftValueStr="面值"+giftValue+"元";
	}

	public Integer getGiftCon() {
		return giftCon;
	}

	public void setGiftCon(Integer giftCon) {
		this.giftCon = giftCon;
		if(giftCon<1){
			this.giftConStr="无";
		}else{
			this.giftConStr="满"+giftCon+"元可用";
		}
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		if(state==0){
			this.stateStr="未使用";
		}else if(state==1){
			this.stateStr="使用中";
		}
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getGiftConStr() {
		return giftConStr;
	}

	public void setGiftConStr(String giftConStr) {
		this.giftConStr = giftConStr;
	}

	public String getGiftValueStr() {
		return giftValueStr;
	}

	public void setGiftValueStr(String giftValueStr) {
		this.giftValueStr = giftValueStr;
	}

	public String getGiftTypeStr() {
		return giftTypeStr;
	}

	public void setGiftTypeStr(String giftTypeStr) {
		this.giftTypeStr = giftTypeStr;
	}

	public String getStateStr() {
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	
}
