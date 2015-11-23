package com.amwell.action.order;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.action.zfb.AlipayConfig;
import com.amwell.action.zfb.util.AlipayNotify;
import com.amwell.action.zfb.util.AlipaySubmit;
import com.amwell.commons.MyDate;
import com.amwell.commons.PropertyManage;
import com.amwell.entity.Page;
import com.amwell.entity.ReturnTicketVo;
import com.amwell.entity.Search;
import com.amwell.service.ILeaseOrderService;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.ApplyReturn;
import com.amwell.vo.ReturnTicket;
import com.amwell.vo.SysAdminVo;
/**
 * ReturnTicketActionAction
 * @author zh
 *
 */
/**
 * 退票相关业务
 */
@ParentPackage("user-finit")
@Namespace("/returnTicket")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class ReturnTicketAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5455793542264544364L;

	/**
	 * 订单相关
	 */
	@Autowired
	private ILeaseOrderService iLeaseOrderService;
	
	/**
	 * 退票Vo
	 */
	private ReturnTicketVo returnTicketVo;
	
	/**
	 * 退票实体类
	 */
	private ReturnTicket returnTicket;
	
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
	 * 一个星期前的日期
	 */
	private String oneWeekAgo;
	
	/**
	 * 日期集合大小
	 */
	private Integer dateSize;
	
	/**
	 * 金额合计
	 */
	private BigDecimal totalMoney;
	
	/**用户退票申请实体**/
	private ApplyReturn applyReturn;
	
	String htmlText;
	
	
	/**
	 * 车票退票总页面
	 * @return
	 */
	@Action(value = "returnTicketMainPage", results = { @Result(name = "success", location = "../../view/order/managerOrderReturn.jsp") })
	public String changeTicketMainPage(){
		// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
		Object theTab = super.session.get("theTab");
		if(null==theTab){
			request.setAttribute("theTab", 1);
		}else{
			request.setAttribute("theTab", theTab);
		}
		return SUCCESS;
		
	}
	
	/**
	 * 获取车票退票列表
	 * @return
	 * @throws ParseException 
	 */
	@Action(value = "getReturnTicketList", results = { @Result(name = "success", location = "../../view/order/orderReturnList.jsp") })
	public String getTicketList() throws ParseException{
		if(null==session.get("toDetailCurrentPageIndex")){
			this.session.remove("search");
		}
		session.put("theTab", 1);
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		if(null==search){
			search = new Search();
		}
		if(StringUtils.hasText(search.getField01()) || StringUtils.hasText(search.getField02())){
			map = iLeaseOrderService.returnTicketListByPage(search, currentPageIndex, pageSize);
		}else{
			map = new HashMap<String, Object>();
		}
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		listSize = list==null?0:list.size();
		return SUCCESS;
	}
	
	/**
	 * 检查支付方式
	 * 包含余额支付的都不允许退票
	 * @throws IOException 
	 */
	@Action(value="checkLeaseOrder",results = { @Result( type = "json") })
	public String checkLeaseOrder() throws IOException{
		getResponse().setContentType("text/html;charset=UTF-8");
		String leaseOrderId = request.getParameter("leaseOrderId");
		int count = iLeaseOrderService.getLeasePayCount(leaseOrderId);
		if(count>=1){
			getResponse().getWriter().print("error");
		}else{
			getResponse().getWriter().print("success");
		}
		return null;
	}
	
	/**
	 * 车票退票详情
	 * @return
	 */
	@Action(value = "returnPopTicket", results = { @Result(name = "success", location = "../../view/order/pop-orderReturn.jsp") })   
	public String changeTicket(){
		returnTicketVo = iLeaseOrderService.getReturnTicket(returnTicketVo);
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
	    calendar.add(Calendar.DATE,-8);//一个星期以前
	    Date starttime=calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		oneWeekAgo = sdf.format(starttime);
		if(null!=returnTicketVo && null!=returnTicketVo.getOrderLocalVos() && returnTicketVo.getOrderLocalVos().size()>0){
			dateSize = returnTicketVo.getBuySize();
		}
		return SUCCESS;
	}
	
	/**
	 * 退票
	 * @throws IOException 
	 */
	@Action(value="doReturnTicket",results = { @Result( type = "json") })
	public String doReturnTicket() throws IOException{
		/**
		 * 线路_乘客班次表 的localIds
		 */
		String localIds = request.getParameter("localIds");
		/**
		 * 去掉最后一个","号
		 */
		localIds = localIds.substring(0, localIds.length()-1);
		/**
		 * 班次ID
		 */
		String lineClassIds = request.getParameter("lineClassIds");
		lineClassIds = lineClassIds.substring(0, lineClassIds.length()-1);
		/**
		 * 订单ID
		 */
		String leaseOrderId = request.getParameter("leaseOrderId");
		/**
		 * 原来预定的天数
		 */
		String dateSize = request.getParameter("dateSize");
//		returnTicket.setLineName(URLDecoder.decode(returnTicket.getLineName(),"utf-8"));
//		if("ceshi".equals(returnTicket.getNickName())){
//			returnTicket.setNickName("#");
//		}else{
//			returnTicket.setNickName(URLDecoder.decode(returnTicket.getNickName(),"utf-8"));
//		}
//		if("1".equals(returnTicket.getDisplayId())){
//			returnTicket.setDisplayId("#");
//		}
		/**
		 * 更改lease_base_info表
		 */
//		iLeaseOrderService.updateLeaseBaseInfo(leaseOrderId,dateSize,returnTicket);
		/**
		 * 更改lease_pay表  ---退票不再修改订单金额
		 */
		//iLeaseOrderService.updateLeasePay(returnTicket);
		/**
		 * 更改line_business_order表
		 */
		iLeaseOrderService.updateLineBusinessOrder(dateSize,returnTicket,localIds);
		/**
		 * 更改stat_passenger_recharge表
		 */
		iLeaseOrderService.updateStatPassengerRecharge(returnTicket);
		/**
		 * 更改stat_passenger_consum_lease表
		 */
		iLeaseOrderService.updateStatPassengerConsumLease(returnTicket);
		
		/**
		 * 添加退票记录
		 */
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		//检查用户名或密码
		SysAdminVo sysAdmin = (SysAdminVo) httpSession.getAttribute("userInfo");
		
		returnTicket.setOperTime(MyDate.getMyDateLong());
		returnTicket.setOperatior(sysAdmin.getUserName());
		int statu = iLeaseOrderService.addReturnTicket(returnTicket,sysAdmin.getUserId(),leaseOrderId,lineClassIds);
		getResponse().setContentType("text/html;charset=UTF-8");
		if(statu==1){
			getResponse().getWriter().print("success");
		}else{
			getResponse().getWriter().print("error");
		}
		return null;
	}
	
	/**
	 * 已退票列表
	 * @return
	 */
	@Action(value = "getRecordTicketList", results = { @Result(name = "success", location = "../../view/order/orderReturnRecord.jsp") })
	public String changedTicketList(){
		
		if(null==session.get("toDetailCurrentPageIndex")){
			this.session.remove("search");
		}
		session.put("theTab", 2);
		Object cpi = super.session.get("toDetailCurrentPageIndex");
		if (null != cpi) {
			if (cpi instanceof Integer) {
				this.currentPageIndex = (Integer) cpi;
				super.session.remove("toDetailCurrentPageIndex");
			}
		} else {
			currentPageIndex = request.getParameter("currentPageIndex") == null ? 0: Integer.parseInt(request.getParameter("currentPageIndex"));
		}
//		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		
		if(null==search){
			search=new Search();
		}
		
		map = iLeaseOrderService.hasReturnTicketListByPage(search, currentPageIndex, pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		listSize = list==null?0:list.size();
		totalMoney =(BigDecimal) map.get("totalMoney");
		return SUCCESS;
	}

	/**
	 * 车票退票详情
	 * @return
	 */
	@Action(value = "returnTicketDetail", results = { @Result(name = "success", location = "../../view/order/returnTicketDetail.jsp") })
	public String returnTicketDetail(){
		returnTicket = iLeaseOrderService.getReturnTicketDetail(returnTicket);
		super.getSession().put("toDetailCurrentPageIndex",this.currentPageIndex);
		return SUCCESS;
	}
	
	/***
	 * 乘客申请退票列表
	 * @return
	 */
	@Action(value = "applyReturnTicketList", results = { @Result(name = "success", location = "../../view/order/applyReturnTicketList.jsp") })
	public String applyReturnTicketList(){
		if(null==session.get("toDetailCurrentPageIndex")){
			this.session.remove("search");
		}
		session.put("theTab", 3);
		Object cpi = super.session.get("toDetailCurrentPageIndex");
		if (null != cpi) {
			if (cpi instanceof Integer) {
				this.currentPageIndex = (Integer) cpi;
				super.session.remove("toDetailCurrentPageIndex");
			}
		} else {
			currentPageIndex = request.getParameter("currentPageIndex") == null ? 0: Integer.parseInt(request.getParameter("currentPageIndex"));
		}
		
		/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
		search = (Search) (search == null?session.get("search"):search);
		if(null==search){
			search=new Search();
		}
		
		map = iLeaseOrderService.applyReturnTicketList(search, currentPageIndex, pageSize);
		this.session.put("search",search);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		listSize = list==null?0:list.size();
		return SUCCESS;
	}
	
	/**
	 * 修改退票申请状态
	 * @throws IOException 
	 */
	@Action(value="updateApplyReturnTicketType",results = { @Result( type = "json") })
	public String updateApplyReturnTicketType() throws IOException{
		getResponse().setContentType("text/html;charset=UTF-8");
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		//检查用户名或密码
		SysAdminVo sysAdmin = (SysAdminVo) httpSession.getAttribute("userInfo");
		applyReturn.setOperatior(sysAdmin.getUserId());
		int statu = iLeaseOrderService.updateApplyReturnTicketType(applyReturn);
		if(statu>=1){
			getResponse().getWriter().print("success");
		}else{
			getResponse().getWriter().print("error");
		}
		return null;
	}
	
	/**
	 * 导出 Excel
	 */
	@Action(value="exportExcel")
	public void exportExcel(){
		getResponse().setContentType("application/msexcel;charset=GBK");
		map = iLeaseOrderService.applyReturnTicketList(search, currentPageIndex, Integer.MAX_VALUE);
		String title = "乘客申请退票列表"; //导出列表头名称
	    String[] hearders = new String[] {
 	    		"订单号", "原退票的总价","手续费百分比","实际退票金额","手续费","退票日期","乘客电话","申请时间","退票状态","支付方式"};//表头数组
	    String[] fields = new String[] {
 	    		"leaseOrderNo", "oldReturn", "percent", "realReturn", "realPoundage","returnDates","telephone","applyTime","type","payModel"};//对象属性数组
	    List<ApplyReturn> lists = (List) map.get("list");
	    if(null!=lists && lists.size()>0){
	    	for(ApplyReturn vo :lists){
	    		if("0".equals(vo.getType())){
	    			vo.setType("未退票");
	    		}else if("1".equals(vo.getType())){
	    			vo.setType("已退票");
	    		}
	    	}
	    }
	    TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
	    JsGridReportBase report;
		try {
			report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td,agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/***
	 * (APP)支付宝退票列表
	 * @return
	 */
	@Action(value = "appZfbReturnTicketList", results = { @Result(name = "success", location = "../../view/order/appZfbReturnTicketList.jsp") })
	public String appZfbReturnTicketList(){
		if(null==session.get("toDetailCurrentPageIndex")){
			this.session.remove("search");
		}
		session.put("theTab", 4);
		Object cpi = super.session.get("toDetailCurrentPageIndex");
		if (null != cpi) {
			if (cpi instanceof Integer) {
				this.currentPageIndex = (Integer) cpi;
				super.session.remove("toDetailCurrentPageIndex");
			}
		} else {
			currentPageIndex = request.getParameter("currentPageIndex") == null ? 0: Integer.parseInt(request.getParameter("currentPageIndex"));
		}
		
		/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
		search = (Search) (search == null?session.get("search"):search);
		if(null==search){
			search=new Search();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null==search.getField01() || "".equals(search.getField01())){
			search.setField01(sdf.format(new Date()));
		}
		if(null==search.getField02() || "".equals(search.getField02())){
			search.setField02(sdf.format(new Date()));
		}
		
		map = iLeaseOrderService.appZfbReturnTicketList(search, currentPageIndex, pageSize);
		this.session.put("search",search);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		listSize = list==null?0:list.size();
		return SUCCESS;
	}
	
	
	/**
	 * 导出(APP)支付宝退票列表 Excel
	 */
	@Action(value="exportAppZfbExcel")
	public void exportAppZfbExcel(){
		getResponse().setContentType("application/msexcel;charset=GBK");
		if(null==search){
			search=new Search();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null==search.getField01() || "".equals(search.getField01())){
			search.setField01(sdf.format(new Date()));
		}
		if(null==search.getField02() || "".equals(search.getField02())){
			search.setField02(sdf.format(new Date()));
		}
		map = iLeaseOrderService.appZfbReturnTicketList(search, currentPageIndex, Integer.MAX_VALUE);
		String title = "支付宝退票列表"; //导出列表头名称
	    String[] hearders = new String[] {
 	    		"订单号", "支付宝交易号","实际退票金额","退款时间","退款状态"};//表头数组
	    String[] fields = new String[] {
 	    		"leaseOrderNo", "thirdparty", "realBackMoney","operTime","returnOrNot"};//对象属性数组
	    List<ReturnTicket> lists = (List) map.get("list");
	    TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
	    JsGridReportBase report;
		try {
			report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td,agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * 支付宝退款
	 * @return
	 */
	@Action(value = "toReturnMoneyFromZfb", results = { @Result(name = "success", location = "../../view/zfb/index.jsp") })
	public String toReturnMoneyFromZfb(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		returnTicket = new ReturnTicket();
		returnTicket.setOperTime(sdf.format(new Date()));
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		if(null==search.getField01() || "".equals(search.getField01())){
			search.setField01(sdf1.format(new Date()));
		}
		if(null==search.getField02() || "".equals(search.getField02())){
			search.setField02(sdf1.format(new Date()));
		}
		map = iLeaseOrderService.appZfbReturnTicketList(search, currentPageIndex, Integer.MAX_VALUE);
		List<ReturnTicket> lists = (List) map.get("list");
		String returnDates = "";//退票参数
		String returnIds = "";//退片主键ID
		if(null!=lists && lists.size()>0){
			for(ReturnTicket rt: lists){
				returnDates += rt.getThirdparty()+"^"+rt.getRealBackMoney()+"^returnTicket"+"#";
				returnIds += rt.getReturnId()+",";
			}
			returnTicket.setReturnDateNum(lists.size());
		}else{
			returnTicket.setReturnDateNum(0);
		}
		if(!"".equals(returnDates)){
			returnDates = returnDates.substring(0, returnDates.length()-1);
		}
		returnTicket.setReturnDates(returnDates);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		returnTicket.setOperatior(sdf2.format(new Date()));
		session.put("zfb_returnIds", returnIds);
		return SUCCESS;
	}
	
	/***
	 * 支付宝确认信息
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Action(value = "toMakeSure", results = { @Result(name = "success", location = "../../view/zfb/alipayapi.jsp") })
	public String toMakeSure() throws UnsupportedEncodingException{
		////////////////////////////////////请求参数//////////////////////////////////////

		//服务器异步通知页面路径
		String notify_url = PropertyManage.getInfoProperty("customline_root")+"zfbReturnTicket/zfbReturnMes.action";
		//需http://格式的完整路径，不允许加?id=123这类自定义参数

		//卖家支付宝帐户
		String seller_email = new String(request.getParameter("WIDseller_email").getBytes("ISO-8859-1"),"UTF-8");
		//必填

		//退款当天日期
		String refund_date = new String(request.getParameter("WIDrefund_date").getBytes("ISO-8859-1"),"UTF-8");
		//必填，格式：年[4位]-月[2位]-日[2位] 小时[2位 24小时制]:分[2位]:秒[2位]，如：2007-10-01 13:13:13

		//批次号
		String batch_no = new String(request.getParameter("WIDbatch_no").getBytes("ISO-8859-1"),"UTF-8");
		//必填，格式：当天日期[8位]+序列号[3至24位]，如：201008010000001

		//退款笔数
		String batch_num = new String(request.getParameter("WIDbatch_num").getBytes("ISO-8859-1"),"UTF-8");
		//必填，参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）

		//退款详细数据
		String detail_data = new String(request.getParameter("WIDdetail_data").getBytes("ISO-8859-1"),"UTF-8");
		//必填，具体格式请参见接口技术文档
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("refund_date", refund_date);
		sParaTemp.put("batch_no", batch_no);
		sParaTemp.put("batch_num", batch_num);
		sParaTemp.put("detail_data", detail_data);
		
		//建立请求
		htmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		
		//将该次需要退款的数据加上批次号
		String returnIds = (String) session.get("zfb_returnIds");
		if(null==returnIds || "".equals(returnIds)){
			return ERROR;
		}
		if(null==batch_no || "".equals(batch_no)){
			return ERROR;
		}
		int statu = iLeaseOrderService.addBatchNoToReturnTicket(returnIds,batch_no);
		if(1!=statu){
			return ERROR;
		}
		return SUCCESS;
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

	public ReturnTicketVo getReturnTicketVo() {
		return returnTicketVo;
	}

	public void setReturnTicketVo(ReturnTicketVo returnTicketVo) {
		this.returnTicketVo = returnTicketVo;
	}

	public String getOneWeekAgo() {
		return oneWeekAgo;
	}

	public void setOneWeekAgo(String oneWeekAgo) {
		this.oneWeekAgo = oneWeekAgo;
	}

	public Integer getDateSize() {
		return dateSize;
	}

	public void setDateSize(Integer dateSize) {
		this.dateSize = dateSize;
	}

	public ReturnTicket getReturnTicket() {
		return returnTicket;
	}

	public void setReturnTicket(ReturnTicket returnTicket) {
		this.returnTicket = returnTicket;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public ApplyReturn getApplyReturn() {
		return applyReturn;
	}

	public void setApplyReturn(ApplyReturn applyReturn) {
		this.applyReturn = applyReturn;
	}

	public String getHtmlText() {
		return htmlText;
	}

	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}


	
}
