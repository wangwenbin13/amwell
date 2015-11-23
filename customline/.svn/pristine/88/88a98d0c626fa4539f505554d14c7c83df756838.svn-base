package com.amwell.action.order;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.entity.Page;
import com.amwell.service.IChangeTicketService;
import com.amwell.service.ILineService;
import com.amwell.service.ISysLogService;
import com.amwell.vo.LineClassVo;
import com.amwell.vo.PassengerOrderStatusVo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.ticket.UserApplicationChangeVo;
import com.amwell.commons.JSONUtil;
import com.amwell.entity.Search;
/**
 * 改签Action
 * @author huxiaojun
 *
 */
@ParentPackage("user-finit")
@Namespace("/changeTicket")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class ChangeTicketAction extends BaseAction{

	private static final long serialVersionUID = -1233799624147218252L;

	@Autowired
	private IChangeTicketService changeTicketService;
	
	@Autowired
	private ILineService lineService;
	
	@Autowired
	private ISysLogService logService;
	
	private int currentPageIndex = 0;

	private int pageSize = 10;

	private static final String KEY_SESSION_USER="userInfo";
	
	private UserApplicationChangeVo changeVo ;
	/**
	 * 改签车票总页面
	 * @return
	 */
	@Action(value = "changeTicketMainPage", results = { @Result(name = "success", location = "../../view/order/managerOrderChange.jsp") })
	public String changeTicketMainPage(){
		return SUCCESS;
		
	}
	
	/**
	 * 获取待改签列表
	 * @return
	 */
	@Action(value = "getTicketList", results = { @Result(name = "success", location = "../../view/order/orderChangList.jsp") })
	public String getTicketList(){
		currentPageIndex = StringUtils.hasText(request.getParameter("currentPageIndex")) ?Integer.parseInt(request.getParameter("currentPageIndex")):0;
		if(null==search){
			search = new Search();
			Calendar ca = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			search.setField02(sdf.format(ca.getTime()));
		}
		if(StringUtils.hasText(search.getField01()) || StringUtils.hasText(search.getField04())){
			map = changeTicketService.getTicketList(search, currentPageIndex,this.pageSize);
		}else{
			map = new HashMap<String, Object>();
		}
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}
	
	@Action(value = "getPassengerOrderList",results = { @Result( type = "json") })
	public String getPassengerOrderList() throws IOException{
		String json="[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String passengerId = super.request.getParameter("passengerId");
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginOrderDate = sdf.format(ca.getTime());
		ca.add(Calendar.DAY_OF_MONTH, 60);
		String endOrderDate = sdf.format(ca.getTime());
		List<PassengerOrderStatusVo> orderList = changeTicketService.getPassengerOrderList(lineBaseId,orderStartTime,passengerId,beginOrderDate,endOrderDate);
		if(false==CollectionUtils.isEmpty(orderList)){
			json = JSONUtil.formObjectsToJSONStr(orderList);
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	@Action(value = "popChangeTicket", results = { @Result(name = "success", location = "../../view/order/pop-orderChange.jsp") })
	public String popChangeTicket(){
		return SUCCESS;
	}
	
	@Action(value = "getLineOrderStartTimes",results = { @Result( type = "json") })
	public String getLineOrderStartTimes() throws IOException{
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		if(StringUtils.hasText(lineBaseId)){
			 List<LineClassVo> lineClassList = lineService.getLineClassList(lineBaseId);
			 if(false==CollectionUtils.isEmpty(lineClassList)){
				 json = JSONUtil.formObjectsToJSONStr(lineClassList);
			 }
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	
	/**
	 * 改签车票
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "changeTicket",results = { @Result( type = "json") })
	public String changeTicket() throws IOException{
		String json ="error";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String leaseOrderId=super.request.getParameter("leaseOrderId");
		String passengerId=super.request.getParameter("passengerId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		String newLineBaseId = super.request.getParameter("newLineBaseId");
		String newOrderStartTime = super.request.getParameter("newOrderStartTime");
		String newOrderDate=super.request.getParameter("newOrderDate");
		if(StringUtils.hasText(lineBaseId)&&StringUtils.hasText(leaseOrderId)&&StringUtils.hasText(passengerId)&&StringUtils.hasText(orderStartTime)&&StringUtils.hasText(orderDate)&&StringUtils.hasText(newLineBaseId)&&StringUtils.hasText(newOrderStartTime)&&StringUtils.hasText(newOrderDate)){
			//判读改签后的时间不能早于系统当前时间
			String newOrderDateTime = newOrderDate+" "+newOrderStartTime;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try{
				if(sdf.parse(newOrderDateTime).before(new Date())){
					json="before";
				}else{
					int flag = changeTicketService.changeTicket(lineBaseId,leaseOrderId,passengerId,orderStartTime,orderDate,newLineBaseId,newOrderStartTime,newOrderDate,this.getSessionUserId());
					if(flag>0){
						json = "success";
						logService.appendLog(this.getSessionUserId(), super.getClientIp(request),"乘客["+passengerId+"]车票改签", "改签成功");
					}else{
						logService.appendLog(this.getSessionUserId(), super.getClientIp(request),"乘客["+passengerId+"]车票改签", "改签失败");
					}
				}
			}catch(Exception e){
				
			}
			
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	private String getSessionUserId(){
		SysAdminVo sysAdmin= (SysAdminVo) request.getSession().getAttribute(KEY_SESSION_USER);
		return null==sysAdmin?"":sysAdmin.getUserId();
	}
	
	/**
	 * 已改签列表
	 * @return
	 */
	@Action(value = "changedTicketList", results = { @Result(name = "success", location = "../../view/order/orderChangRecord.jsp") })
	public String changedTicketList(){
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0: Integer.parseInt(request.getParameter("currentPageIndex"));
		map = changeTicketService.changedTicketList(search, currentPageIndex,this.pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}
	
	/**
	 * 用户申请改签页面
	 * @return
	 */
	@Action(value = "userChangeTicket", results = { @Result(name = "success", location = "../../view/order/userOrderChangList.jsp") })
	public String userChangeTicket(){
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0: Integer.parseInt(request.getParameter("currentPageIndex"));
		map = changeTicketService.userChangeTicket(search, currentPageIndex,pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
		
	}
	
	/**
	 * 拒绝改签车票
	 * @return
	 * @throws IOException 
	 * @throws IOException 
	 */				
	@Action(value = "refuceReturnTicket",results = { @Result( type = "json") })
	public String refuceReturnTicket() throws IOException{
		String refusemess = request.getParameter("refusemess");      //拒绝改签理由
		String telephone = request.getParameter("telephone");        //手机号码
		String localId = request.getParameter("localId");            //line_business_order的序号ID
		int statu = changeTicketService.refuceReturnTicket(localId,refusemess,telephone);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(statu);
		return null;
	}
	
	/**
	 * 执行改签车票
	 * @return
	 */
	@Action(value = "doReturnTicket",results = { @Result( type = "json") })
	public String doReturnTicket() throws IOException{
		int statu = -1;
		if(null!=changeVo && StringUtils.hasText(getSessionUserId())){
			changeVo.setUserId(getSessionUserId());
			statu = changeTicketService.doReturnTicket(changeVo);
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(statu);
		return null;
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

	public UserApplicationChangeVo getChangeVo() {
		return changeVo;
	}

	public void setChangeVo(UserApplicationChangeVo changeVo) {
		this.changeVo = changeVo;
	}
	
}
