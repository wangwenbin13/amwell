package com.amwell.vo;

/**
 * 短信模板
 * @author Administrator
 *
 */
public class MsgTemplateVo {
	
	private String id;                            
	private String template_name;             //模板名称
	private int template_type;                //模板类型 1:短信
	private String template_content;          //模板内容
	private int template_status;              //模板状态0:无效 1:有效
	private String createBy;
	private String createOn;
	private String updateBy;
	private String updateOn;
	private String remark;                     //备注
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public int getTemplate_type() {
		return template_type;
	}
	public void setTemplate_type(int template_type) {
		this.template_type = template_type;
	}
	public String getTemplate_content() {
		return template_content;
	}
	public void setTemplate_content(String template_content) {
		this.template_content = template_content;
	}
	public int getTemplate_status() {
		return template_status;
	}
	public void setTemplate_status(int template_status) {
		this.template_status = template_status;
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
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
