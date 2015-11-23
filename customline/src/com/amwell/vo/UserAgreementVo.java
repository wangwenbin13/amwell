package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class UserAgreementVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 主键
	private String agreementId;
	               
	// 标题          
	private String agreementTitle;
	               
	// 内容          
	private String agreementContent;
	               
	// 创建人         
	private String agreementCreateBy;
	               
	// 创建时间        
	private String agreementCreateOn;
	               
	// 修改人         
	private String agreementUpdateBy;
	
	// 修改时间
	private String agreementUpdateOn;
	
	

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public String getAgreementTitle() {
		return agreementTitle;
	}

	public void setAgreementTitle(String agreementTitle) {
		this.agreementTitle = agreementTitle;
	}

	
	public String getAgreementContent() {
		return agreementContent;
	}

	public void setAgreementContent(String agreementContent) {
		this.agreementContent = agreementContent;
	}

	public String getAgreementCreateBy() {
		return agreementCreateBy;
	}

	public void setAgreementCreateBy(String agreementCreateBy) {
		this.agreementCreateBy = agreementCreateBy;
	}

	public String getAgreementCreateOn() {
		return agreementCreateOn;
	}

	public void setAgreementCreateOn(String agreementCreateOn) {
		this.agreementCreateOn = agreementCreateOn;
	}

	public String getAgreementUpdateBy() {
		return agreementUpdateBy;
	}

	public void setAgreementUpdateBy(String agreementUpdateBy) {
		this.agreementUpdateBy = agreementUpdateBy;
	}

	public String getAgreementUpdateOn() {
		return agreementUpdateOn;
	}

	public void setAgreementUpdateOn(String agreementUpdateOn) {
		this.agreementUpdateOn = agreementUpdateOn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserAgreementVo(String agreementId, String agreementTitle,
			String agreementContent) {
		super();
		this.agreementId = agreementId;
		this.agreementTitle = agreementTitle;
		this.agreementContent = agreementContent;
	}

	public UserAgreementVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	

	

}
