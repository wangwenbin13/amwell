package com.amwell.action.balance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.commons.MyDate;
import com.amwell.commons.ValidateHelper;
import com.amwell.entity.Page;
import com.amwell.service.IBalanceSetService;
import com.amwell.vo.BalanceSetVo;
import com.amwell.vo.SysAdminVo;

import net.sf.oval.ConstraintViolation;

/**
 * 结算设置Action
 * @author Administrator
 *
 */
@ParentPackage("user-finit")
@Namespace("/balanceSet")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class BalanceSetAction extends BaseAction {

	private static final Logger log = Logger.getLogger(BalanceSetAction.class);
	
	@Autowired
	private IBalanceSetService balanceSetService;
	
	
	private int currentPageIndex=0;
	
	private int pageSize = 10;
	
	private static final String KEY_SESSION_USER="userInfo";
	
	
	/**
	 * 查询保底分成列表
	 */
	private static final long serialVersionUID = 5948279204452071636L;
	
	@Action(value = "getBalanceSetList", results = { @Result(name = "success", location = "../../view/balance/balanceSetList.jsp") })
	public String getBalanceSetList(){
		map =  balanceSetService.getBalanceSetList(search,currentPageIndex,this.pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		return SUCCESS;
	}
	
	@Action(value = "forwardAddBalance", results = { @Result(name = "success", location = "../../view/balance/addBalance.jsp") })
	public String forwardAddBalance(){
		return SUCCESS;
	}

	@Action(value = "addBalance",results = { @Result( type = "json") })
	public String addBalance() throws IOException{
		String json ="error";
		BalanceSetVo balanceSet = this.createBalanceSetVo();
		List<ConstraintViolation> violationList= ValidateHelper.validate(balanceSet);
		if(CollectionUtils.isEmpty(violationList)){
			//验证通过
			int flag = balanceSetService.getSetNameCount(balanceSet);
			if(flag==0){
				int count=balanceSetService.addBalance(balanceSet);
				if(count>0){
					json="success";
				}
			}
		}else{
			//验证未通过
			json ="validateError";
		}
		log.debug("json:"+json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	private BalanceSetVo createBalanceSetVo() {
		BalanceSetVo vo = new BalanceSetVo();
		vo.setSetName(super.request.getParameter("setName"));
		vo.setMinimumNum(StringUtils.hasText(super.request.getParameter("minimumNum"))?Integer.parseInt(super.request.getParameter("minimumNum")):0);
		vo.setPlatformPercent(StringUtils.hasText(super.request.getParameter("platformPercent"))?new BigDecimal(super.request.getParameter("platformPercent")):null);
		vo.setMerchantPercent(StringUtils.hasText(super.request.getParameter("merchantPercent"))?new BigDecimal(super.request.getParameter("merchantPercent")):null);
		vo.setCreateBy(getSessionUserId());
		vo.setCreateOn(MyDate.getMyDateLong());
		return vo;
	}
	
	private String getSessionUserId(){
		SysAdminVo sysAdmin= (SysAdminVo) request.getSession().getAttribute(KEY_SESSION_USER);
		return null==sysAdmin?"":sysAdmin.getUserId();
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
