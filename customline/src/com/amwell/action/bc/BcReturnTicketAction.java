package com.amwell.action.bc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.commons.MyDate;
import com.amwell.entity.BcReturnHistoryVo;
import com.amwell.entity.BcReturnVo;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ICharteredLineService;
import com.amwell.service.ICharteredOrderService;
import com.amwell.service.IMarketingService;
import com.amwell.vo.MarketingVo;
import com.amwell.vo.MessageLogVo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.bc.BcReturnHistoryEntity;
import com.amwell.vo.bc.CharteredLineVo;
/**
 * 
 * @author wangwenbin
 *
 * 2014-12-1
 */
/**
 * 包车退票相关业务
 */
@ParentPackage("user-finit")
@Namespace("/bcReturnTicket")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class BcReturnTicketAction extends BaseAction{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1239414147916148762L;

	/**
	 * 包车订单
	 */
	@Autowired
	private ICharteredOrderService charteredOrderService;
	
	/**
	 * 金额合计
	 */
	private BigDecimal totalMoney;
	
	/**
	 * 每页显示的记录数
	 */
	private Integer pageSize = 10;
	
	/**
	 * 记录数的下标起始数 limit 0,10中的0
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 *集合大小 
	 */
	private Integer listSize = 0;
	
	/**
	 * 包车退票Vo
	 */
	private BcReturnVo bcReturnVo;
	
	/**
	 * 包车退票集合
	 */
	private List<BcReturnVo> bcReturnVos;
	
	/**
	 * 包车退票历史实体
	 */
	private BcReturnHistoryEntity bcReturnHistoryEntity;
	
	/**
	 * 包车退票历史Vo
	 */
	private BcReturnHistoryVo bcReturnHistoryVo;
	
	/**
	 * 短信消息
	 */
	@Autowired
	private IMarketingService iMarketingService;
	
	
	@Autowired
	private ICharteredLineService charteredLineService;
	/**
	 * 包车退票管理总页面
	 * @return
	 */
	@Action(value = "forwardBcReturnPage", results = { @Result(name = "success", location = "../../view/bcLine/forwardBcReturnPage.jsp") })
	public String forwardBcReturnPage(){
		// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
		Object theTab = super.session.get("theTab");
		if (theTab instanceof String) {
			super.request.setAttribute("theTab", theTab);
			super.session.remove("theTab");
		}
		return SUCCESS;
	}
	
	/**
	 * 包车退票管理-退票订单列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "getBcReturnOrderList", results = { @Result(name = "success", location = "../../view/bcLine/bcReturnOrderList.jsp") })
	public String getBcReturnOrderList(){
		if(null==session.get("toDetailCurrentPageIndex")){
			this.session.remove("search");
		}
		currentPageIndex = request.getParameter("currentPageIn" +
				"dex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		map = charteredOrderService.getBcReturnOrderList(search, currentPageIndex, pageSize);
		bcReturnVos = (List<BcReturnVo>) map.get("list");//数据对象
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null!=bcReturnVos && bcReturnVos.size()>0){
			for(BcReturnVo bcReturn : bcReturnVos){
				int result = MyDate.diffHours(MyDate.strToUTCLong(sdf.format(date), "yyyy-MM-dd HH:mm:ss"), MyDate.strToUTCLong(bcReturn.getStartTime()+":00", "yyyy-MM-dd HH:mm:ss"));
				if(!StringUtils.isEmpty(bcReturn.getNoReturn())){
					/**判断是否可以退票**/
					if(Float.parseFloat(bcReturn.getNoReturn())<=result){
						bcReturn.setReturnFlag(true);
					}else{
						bcReturn.setReturnFlag(false);
					}
				}
			}
		}
		page = (Page) map.get("page");//分页对象
		listSize = list==null?0:list.size();
		return SUCCESS;
	}
	
	/**
	 * 包车退票管理-包车订单列表-操作退票(详情)
	 * @return
	 */
	@Action(value = "getBcReturnedDetail", results = { @Result(name = "success", location = "../../view/bcLine/bcReturnedDetail.jsp") })
	public String getBcReturnedDetail(){
		bcReturnVo = charteredOrderService.getBcReturnDetail(bcReturnVo);
		if(null!=bcReturnVo && !StringUtils.isEmpty(bcReturnVo.getStartTime())){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			/**
			 * 判断退票的手续费率
			 */
			int result = MyDate.diffHours(MyDate.strToUTCLong(sdf.format(date), "yyyy-MM-dd HH:mm:ss"), MyDate.strToUTCLong(bcReturnVo.getStartTime()+":00", "yyyy-MM-dd HH:mm:ss"));
			if(result>=Float.parseFloat(bcReturnVo.getReturnOneTime())){
				bcReturnVo.setRealPer(bcReturnVo.getReturnOnePer());
			}else{
				bcReturnVo.setRealPer(bcReturnVo.getReturnTwoPer());
			}
			
		}
		return SUCCESS;
	}
	
	
	/**
	 * 检查该订单是否可以退票(如果包含优惠券,不与许退票)
	 */
	@Action(value="checkBcOrder",results = { @Result( type = "json") })
	public String checkBcOrder()throws Exception{
		getResponse().setContentType("text/html;charset=UTF-8");
		int statu = charteredOrderService.checkBcOrde(bcReturnVo);
		if(statu==1){
			getResponse().getWriter().print("error");
		}
		getResponse().getWriter().print("success");
		return null;
	}
	
	
	/**
	 * 执行包车退票操作
	 */
	@Action(value="doBcReturn",results = { @Result( type = "json") })
	public String doBcReturn()throws Exception{
		getResponse().setContentType("text/html;charset=UTF-8");
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo sysAdmin = (SysAdminVo) httpSession.getAttribute("userInfo");
		bcReturnHistoryEntity.setOperatorId(sysAdmin.getUserId());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bcReturnHistoryEntity.setOperateTime(sdf.format(date));
		int statu = charteredOrderService.doBcReturn(bcReturnHistoryEntity);
		if(statu>=1){
			
			/**
			 * 退票成功给商家发送一条短信
			 */
			CharteredLineVo charteredLineVo = this.charteredLineService.getCharteredLineDetail(bcReturnHistoryEntity.getBc_line_id());
			if(null!=charteredLineVo){
				String msgContext="原定于${startTime}出发，${beginAddress}-${endAddress}，订单号为：${orderNo}的包车订单已经退票，请知悉！";
				msgContext=msgContext.replace("${startTime}", charteredLineVo.getStartTime()).replace("${beginAddress}", charteredLineVo.getBeginAddress()).replace("${endAddress}", charteredLineVo.getEndAddress()).replace("${orderNo}", bcReturnHistoryEntity.getOrderNo());
				MarketingVo marketingVo=new MarketingVo();
				marketingVo.setMsgContext(msgContext);
				marketingVo.setTheModule(2);
				marketingVo.setMsgType(0);
				marketingVo.setMsgSubType(0);
				marketingVo.setMsgTarget(2);
				marketingVo.setMsgTiTLE("退票通知");
				marketingVo.setCreateBy(sysAdmin.getUserName());
				marketingVo.setCreateOn(sdf.format(date));
				marketingVo.setIssend("1");
				
				String msgId=iMarketingService.addMarketing(marketingVo);//记录短信消息发送信息	
				
				MessageLogVo messageLogVo=new MessageLogVo();
				String telephone = request.getParameter("telephone");
				messageLogVo.setSendPhoneNo(telephone);
				messageLogVo.setMsgId(msgId);
				//系统时间（发送时间）
				messageLogVo.setSendTime(MyDate.getMyDateLong());
				messageLogVo.setUserId(bcReturnHistoryEntity.getBusinessId());
				iMarketingService.showMarketingLog(messageLogVo);
				iMarketingService.sendAPPMsg(msgId);
				getResponse().getWriter().print("success");
			}else{
				getResponse().getWriter().print("error");
			}
		}else{
			getResponse().getWriter().print("error");
		}
		return null;
	}
	
	/**
	 * 包车退票管理-包车退票记录列表
	 * @return
	 */
	@Action(value = "getBcReturnedList", results = { @Result(name = "success", location = "../../view/bcLine/bcReturnedList.jsp") })
	public String getBcReturnedList(){
		if(null==session.get("toDetailCurrentPageIndex")){
			this.session.remove("search");
		}
		Object cpi = super.session.get("toDetailCurrentPageIndex");
		if (null != cpi) {
			if (cpi instanceof Integer) {
				this.currentPageIndex = (Integer) cpi;
				super.session.remove("toDetailCurrentPageIndex");
			}
		} else {
			currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
					: Integer
							.parseInt(request.getParameter("currentPageIndex"));
		}
//		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		
		/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
		search = (Search) (search == null?session.get("search"):search);
		if(null==search){
			search=new Search();
		}
		
		map = charteredOrderService.getBcReturnHistoryList(search, currentPageIndex, pageSize);
		this.session.put("search",search);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		listSize = list==null?0:list.size();
		totalMoney =(BigDecimal) map.get("totalMoney");
		return SUCCESS;
	}
	
	/**
	 * 包车退票管理-包车退票记录列表-退票详情
	 * @return
	 */
	@Action(value = "getBcReturnOrderDetail", results = { @Result(name = "success", location = "../../view/bcLine/bcReturnOrderDetail.jsp") })
	public String getBcReturnOrderDetail(){
		bcReturnHistoryVo = charteredOrderService.getBcReturnHistoryDetail(bcReturnHistoryVo);
		super.getSession().put("toDetailCurrentPageIndex",this.currentPageIndex);
		// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
		String theTab = super.request.getParameter("theTab");
		if (StringUtils.isNotBlank(theTab)) {
			super.getSession().put("theTab", theTab);
		}
		return SUCCESS;
	}
	

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public Integer getListSize() {
		return listSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}

	public BcReturnVo getBcReturnVo() {
		return bcReturnVo;
	}

	public void setBcReturnVo(BcReturnVo bcReturnVo) {
		this.bcReturnVo = bcReturnVo;
	}

	public List<BcReturnVo> getBcReturnVos() {
		return bcReturnVos;
	}

	public void setBcReturnVos(List<BcReturnVo> bcReturnVos) {
		this.bcReturnVos = bcReturnVos;
	}

	public BcReturnHistoryEntity getBcReturnHistoryEntity() {
		return bcReturnHistoryEntity;
	}

	public void setBcReturnHistoryEntity(BcReturnHistoryEntity bcReturnHistoryEntity) {
		this.bcReturnHistoryEntity = bcReturnHistoryEntity;
	}

	public BcReturnHistoryVo getBcReturnHistoryVo() {
		return bcReturnHistoryVo;
	}

	public void setBcReturnHistoryVo(BcReturnHistoryVo bcReturnHistoryVo) {
		this.bcReturnHistoryVo = bcReturnHistoryVo;
	}

}
