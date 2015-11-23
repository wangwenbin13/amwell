package com.amwell.vo;

/**
 * 推荐有奖设置表
 * @author gongxueting
 *
 */
public class RecommendAwardSet {
	private int id;//int(4) NOT NULL
	private String newUserGiftId;//varchar(35) NOT NULL拉新用户礼品ID
	private String oldUserGiftId;//varchar(35) NOT NULL老用户礼品ID
	private String actionRule;//text NOT NULL推荐有奖活动规则
	private String setBy;//varchar(64) NULL设置人Id
	private String setOn;//varchar(64) NULL设置时间
	
	private String recommendAwardSwitch;//推荐有奖开关：0.关 1.开
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNewUserGiftId() {
		return newUserGiftId;
	}
	public void setNewUserGiftId(String newUserGiftId) {
		this.newUserGiftId = newUserGiftId;
	}
	public String getOldUserGiftId() {
		return oldUserGiftId;
	}
	public void setOldUserGiftId(String oldUserGiftId) {
		this.oldUserGiftId = oldUserGiftId;
	}
	public String getActionRule() {
		return actionRule;
	}
	public void setActionRule(String actionRule) {
		this.actionRule = actionRule;
	}
	public String getSetBy() {
		return setBy;
	}
	public void setSetBy(String setBy) {
		this.setBy = setBy;
	}
	public String getSetOn() {
		return setOn;
	}
	public void setSetOn(String setOn) {
		this.setOn = setOn;
	}
	public String getRecommendAwardSwitch() {
		return recommendAwardSwitch;
	}
	public void setRecommendAwardSwitch(String recommendAwardSwitch) {
		this.recommendAwardSwitch = recommendAwardSwitch;
	}
}
