package com.amwell.action.help;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.MyDate;
import com.amwell.service.IHelpService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.UserAgreementVo;

/**
 * 用户协议
 * @author 胡双
 *
 */
@ParentPackage("user-finit")
@Namespace("/userAgreement")
public class UserAgreementAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 用户协议对象
	private UserAgreementVo userAgreementVo;
	
	private List<UserAgreementVo> userAgreements;
	
	@Autowired(required=false) 
	private IHelpService helpService;//注入业务类
	
	
	
	@Action(value = "toList", results = { @Result(name = "success", location = "/WEB-INF/view/help/userAgreement.jsp") })
	public String toList()throws Exception{
		
		
		// 查出所有协议的标题
		userAgreements = helpService.getAllUserAgreement();
		// 如果所有协议标题不存在，
		if(null != userAgreements && userAgreements.size() > 0 ){
			
			request.setAttribute("userAgreements", userAgreements);
			//设置内容
			userAgreementVo = userAgreements.get(0);
		}else{
			
			userAgreements = new ArrayList<UserAgreementVo>();
			//userAgreements.add(new UserAgreementVo("1","定制班车用户协议","协议内容"));
			
			userAgreementVo = new UserAgreementVo();
			//userAgreementVo.setAgreementContent(userAgreements.get(0).getAgreementContent());
		}
		
		
		
		return SUCCESS;
	}
	
	/**
	 * 增加/修改协议
	 * @return
	 * @throws Exception
	 */
	@Action(value="addUserAgreement",results = {@Result(type="json")})
	public String addUserAgreement()throws Exception{
		
		getResponse().setContentType("text/html;charset=UTF-8");
		System.out.println("==================================");
		String agreementType = request.getParameter("agreementType");
		if(null != userAgreementVo){
			HttpSession httpSession = ServletActionContext.getRequest().getSession();
			SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
			
			// 修改
			if(null != agreementType && "1".equals(agreementType)){
				userAgreementVo.setAgreementTitle(null);//特殊处理
				userAgreementVo.setAgreementUpdateBy(admin.getUserId());
				userAgreementVo.setAgreementUpdateOn(MyDate.getMyDateLong());
			}else{
				
				// 增加
				userAgreementVo.setAgreementId(null);//特殊处理
				userAgreementVo.setAgreementCreateBy(admin.getUserId());
				userAgreementVo.setAgreementCreateOn(MyDate.getMyDateLong());
				userAgreementVo.setAgreementUpdateBy(admin.getUserId());
				userAgreementVo.setAgreementUpdateOn(MyDate.getMyDateLong());
			}
			
			userAgreementVo = helpService.saveUserAgreement(userAgreementVo);
			//协议标题不为空，说明保存成功
			if(null != userAgreementVo){
				getResponse().getWriter().print("success");
			}else{
				getResponse().getWriter().print("fail");
			}
		}
		return null;
	}
	
	/**
	 * 查询协议名称是否存在
	 * @return
	 */
	@Action(value="userAgreementTitleIsExist",results = {@Result(type="json")})
	public String userAgreementTitleIsExist()throws Exception{
		
		getResponse().setContentType("text/html;charset=UTF-8");
		
		if(!"".equals(userAgreementVo.getAgreementTitle())){
			userAgreementVo = helpService.getAllUserAgreementByCondition(userAgreementVo);
			
			if(null == userAgreementVo){
				getResponse().getWriter().print("no");
			}else{
				getResponse().getWriter().print("yes");
			}
		}else{
			getResponse().getWriter().print("error");
		}
		return null;
	}
	
	/**
	 * 根据协议ID查询协议内容
	 * 
	 * @return
	 */
	@Action(value="queryUserAgreement",results = {@Result(type="json")})
	public String queryUserAgreementt()throws Exception{
		
		getResponse().setContentType("text/html;charset=UTF-8");
		if(!"".equals(userAgreementVo.getAgreementId())){
			userAgreementVo.setAgreementTitle("");
			userAgreementVo = helpService.getAllUserAgreementByCondition(userAgreementVo);
			
			if(null != userAgreementVo){
				getResponse().getWriter().print(userAgreementVo.getAgreementContent());
			}
		}else{
			getResponse().getWriter().print("");
		}
		return null;
	}
	// 
	public UserAgreementVo getUserAgreementVo() {
		return userAgreementVo;
	}


	public void setUserAgreementVo(UserAgreementVo userAgreementVo) {
		this.userAgreementVo = userAgreementVo;
	}


	public List<UserAgreementVo> getUserAgreements() {
		return userAgreements;
	}


	public void setUserAgreements(List<UserAgreementVo> userAgreements) {
		this.userAgreements = userAgreements;
	}
	
	
}
