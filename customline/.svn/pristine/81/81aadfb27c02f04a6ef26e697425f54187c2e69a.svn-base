package com.amwell.action.help;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.service.IHelpService;
import com.amwell.vo.HelpCentryEntity;

/**
 * 帮助中心
 * @author 
 *
 */
@ParentPackage("user-finit")
@Namespace("/helpCenter")
@SuppressWarnings("rawtypes")
public class HelpCenterAction extends BaseAction {
	
	@Autowired 
	private IHelpService helpService;//注入业务类
	
	/**
	 * 帮助实体
	 */
	private HelpCentryEntity helpCentryEntity;
	
	/**
	 * 列表页面
	 */
	@Action(value = "list", results = { @Result(name = "success", location = "../../view/help/helpCenter.jsp") })
	public String list(){
		if(null==helpCentryEntity){
			helpCentryEntity = new HelpCentryEntity();
			helpCentryEntity.setHelpType(1);
		}
		HelpCentryEntity hc=helpService.getHelpCentry(helpCentryEntity);
		if(null!=hc){
			helpCentryEntity = hc;
		}
		else{
			helpCentryEntity.setHelpId(null);
			helpCentryEntity.setContent(null);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加/修改帮助信息 
	 * @return
	 */
	@Action(value="addUpdateHelpCentry",results = { @Result( type = "json") })
	public String addUpdateHelpCentry()throws Exception{
		int statu = helpService.addUpdateHelpCentry(helpCentryEntity);
		getResponse().setContentType("text/html;charset=UTF-8");
		if(1==statu){
			getResponse().getWriter().print("success");
		}else{
			getResponse().getWriter().print("error");
		}
		return null;
	}

	public HelpCentryEntity getHelpCentryEntity() {
		return helpCentryEntity;
	}

	public void setHelpCentryEntity(HelpCentryEntity helpCentryEntity) {
		this.helpCentryEntity = helpCentryEntity;
	}
	
}
