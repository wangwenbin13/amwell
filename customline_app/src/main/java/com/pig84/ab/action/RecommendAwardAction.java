package com.pig84.ab.action;


import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.cache.UserCache;
import com.pig84.ab.service.IRecommendAwardService;
import com.pig84.ab.utils.Html;
import com.pig84.ab.vo.User;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_2;

/**
 * 老拉新推荐有奖相关
 * @author gongxueting
 *
 */
@ParentPackage("no-interceptor")
@Namespace("/app_recommendAward")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class RecommendAwardAction extends BaseAction {

	@Autowired
	private IRecommendAwardService recommendAwardService;
	
	/**
	 * 获取老拉新有奖推荐开关标识(推荐有奖开关：0.关 1.开)
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getRecommendAwardSwitch",results={@Result(type="json")})
	public String getRecommendAwardSwitch() throws IOException{
		AppVo_1 vo1 = recommendAwardService.getRecommendAwardSwitch();
		User appUser = UserCache.getUser();
		if(null==appUser){
			vo1.setA1("-2");//未登录
		}
		write(vo1);
		return null;
	}
	
	/**
	 * 获取老拉新推荐有奖活动规则
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getRecommendAwardRole",results={@Result(type="json")})
	public String getRecommendAwardRole() throws IOException{
		AppVo_2 vo=new AppVo_2();
		User appUser = UserCache.getUser();
		if(null==appUser){
			vo.setA1("-2");//未登录
		}
		else{
			vo=recommendAwardService.getRecommendAwardRole();
		}
		write(vo);
		return null;
	}
	
	/**
	 * 添加老拉新推荐有奖信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "addRecommendAward",results={@Result(name = "success", location = "/recommendAwardShareTwo.jsp")})
	public String addRecommendAward() throws IOException{
		String oldUserPhone = request.getParameter("oldUserPhone");//推荐用户电话
		if(!StringUtils.isEmpty(oldUserPhone)){
			oldUserPhone = Html.purge(oldUserPhone);
		}
		String newUserPhone = request.getParameter("newUserPhone");//被推荐用户电话
		if(!StringUtils.isEmpty(newUserPhone)){
			newUserPhone = Html.purge(newUserPhone);
		}
		String result = recommendAwardService.addRecommendAward(oldUserPhone, newUserPhone);
		request.setAttribute("result",result);
		if("1".equals(result)){
			request.setAttribute("newUserPhone",newUserPhone);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 推荐有奖分享
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "recommendAwardShare", results = { @Result(name = "success", location = "/recommendAwardShare.jsp") })
	public String recommendAwardShare(){
		String telephone = request.getParameter("telephone");//分享用户的电话
		telephone = Html.purge(telephone);
		//将电话号码进行解密
		AppVo_1 vo1=this.recommendAwardService.getPassengerName(telephone);
		if(null!=vo1){
			request.setAttribute("nickName",vo1.getA1());
			request.setAttribute("oldUserPhone",telephone);
		}
		//查询礼品面值和活动规则
		AppVo_2 vo2=this.recommendAwardService.getRecommendAwardRole();
		if(vo2!=null){
			request.setAttribute("giftValue",vo2.getA2());
			request.setAttribute("actionRule",vo2.getA1());
		}
		//推荐有奖开关：0.关 1.开
		AppVo_1 vo = recommendAwardService.getRecommendAwardSwitch();
		if(null!=vo&&StringUtils.isNotBlank(vo.getA1())){
			request.setAttribute("recommendAwardSwitch",vo.getA1());
		}
		return SUCCESS;
	}
}
