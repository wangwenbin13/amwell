package com.amwell.action.recommendaward;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.service.IRecommendAwardService;
import com.amwell.vo.RecommendAwardSet;
import com.amwell.vo.SysAdminVo;

/**
 * 老拉新推荐有奖相关
 * @author gongxueting
 *
 */
@ParentPackage("user-finit")
@Namespace("/recommendAward")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class RecommendAwardAction extends BaseAction {

	@Autowired
	private IRecommendAwardService recommendAwardService;
	
	private RecommendAwardSet recommendAwardSet=new RecommendAwardSet();//推荐有奖设置表

	/**
	 * 跳转到推荐有奖设置页面
	 * @return
	 */
	@Action(value = "forwardSetPage", results = { @Result(name = "success", location = "../../view/recommendaward/recommendAwardSet.jsp") })
	public String forwardSetPage(){
		this.recommendAwardSet=this.recommendAwardService.getRecommendAwardSet();
		//查询设置对象
		return SUCCESS;
	}
	
	/**
	 * 推荐有奖设置
	 * @throws JSONException 
	 * @throws IOException 
	 */
	@Action(value = "addRecommendAwardSet", results = { @Result( type = "json") })
	public String addRecommendAwardSet() throws IOException, JSONException{
		HttpSession httpSession =ServletActionContext.getRequest().getSession();
		SysAdminVo adminModel=(SysAdminVo)httpSession.getAttribute("userInfo");
		this.recommendAwardSet.setSetBy(adminModel.getUserId());
		int flag=this.recommendAwardService.addRecommendAwardSet(this.recommendAwardSet);
		this.recommendAwardService.addSysLog(flag,adminModel.getUserId(),"推荐有奖设置");
	    getResponse().getWriter().print(flag);
		return null;
	}
	
	public RecommendAwardSet getRecommendAwardSet() {
		return recommendAwardSet;
	}
	public void setRecommendAwardSet(RecommendAwardSet recommendAwardSet) {
		this.recommendAwardSet = recommendAwardSet;
	}
}
