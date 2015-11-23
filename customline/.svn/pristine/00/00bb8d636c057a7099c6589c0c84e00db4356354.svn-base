package com.amwell.action.gf;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.MyDate;
import com.amwell.entity.Page;
import com.amwell.service.IGiftService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.gf.GiftVo;

/**
 * 礼品相关Action
 * @author huxiaojun
 */
@ParentPackage("user-finit")
@Namespace("/gift")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class GiftAction extends BaseAction {

	private static final long serialVersionUID = -7088866005996552755L;

	@Autowired
	private IGiftService giftService;
	
	private int currentPageIndex=0;
	
	private int pageSize = 10;
	
	private String giftId;
	
	
	/**
	 * 查询礼品列表
	 * @return
	 */
	@Action(value = "forwardGiftListPage", results = { @Result(name = "success", location = "../../view/gf/giftList.jsp") })
	public String forwardGiftListPage(){
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		map = giftService.listByPage(search, currentPageIndex, pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		return SUCCESS;
	}

	/**
	 * 跳转到添加礼品页面
	 * @return
	 */
	@Action(value = "forwardAddGiftPage", results = { @Result(name = "success", location = "../../view/gf/addGift.jsp") })
	public String forwardAddGiftPage(){
		//礼品ID
		giftId=giftService.getGiftId();
		if(false==StringUtils.hasText(giftId)){
			giftId="10000";
		}else{
			giftId=String.valueOf(Integer.valueOf(giftId)+1);
		}
		return SUCCESS;
	}
	/**
	 * 删除礼品弹窗
	 * @return
	 */
	@Action(value="forwardDelGiftPage",results={@Result(name="success",location="../../view/gf/pop-delGf.jsp")})
	public String toList(){
		
		return SUCCESS;
	}
	
	
	/**
	 * 添加礼品
	 * @throws JSONException 
	 * @throws IOException 
	 */
	@Action(value = "addGiftPage", results = { @Result( type = "json") })
	public void addGiftPage() throws IOException{
		String json="error";
		GiftVo giftModel =new GiftVo();
		HttpSession httpSession =ServletActionContext.getRequest().getSession();
		SysAdminVo adminModel=(SysAdminVo)httpSession.getAttribute("userInfo");
		int price = Integer.parseInt(request.getParameter("giftPrice"));
		String giftName=request.getParameter("giftName");
		int onlyName = giftService.getGiftName(giftName);
		if(price>0 && onlyName<1){
			giftModel.setOperatorId(adminModel.getUserId());
			giftModel.setOperateTime(MyDate.getMyDate());
			giftModel.setGiftId(request.getParameter("giftId"));
			giftModel.setGiftName(giftName);
			giftModel.setGiftType(Integer.parseInt(request.getParameter("giftSelect")));
			giftModel.setGiftValue(price);
			int giftCon = 0;
			if(!org.apache.commons.lang3.StringUtils.isEmpty(request.getParameter("giftConstraint"))){
				giftCon = Integer.parseInt(request.getParameter("giftConstraint"));
			}
			giftModel.setGiftCon(giftCon);
			int flag=giftService.addGiftPage(giftModel);
			if(flag>0){
				json="success";
			}
			giftService.addSysLog(flag,adminModel.getUserId(),"礼品添加");
		}
		JsonWriter.write(json);
	}
	
	/**
	 * 删除礼品
	 * @throws JSONException 
	 * @throws IOException 
	 */
	@Action(value = "deleteGiftPage", results = { @Result( type = "json") })
	public void deleteGiftPage() throws IOException, JSONException{
		String json="error";
		HttpSession httpSession =ServletActionContext.getRequest().getSession();
		SysAdminVo adminModel=(SysAdminVo)httpSession.getAttribute("userInfo");
		String id = request.getParameter("id");
		int flag = giftService.deleteGiftPage(id);
		if(flag>0){
			json="success";
		}
		giftService.addSysLog(flag, adminModel.getUserId(), "礼品删除");
		JsonWriter.write(json);
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

	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}


	
	
	
}
