package com.amwell.action.line;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.MyDate;
import com.amwell.commons.QueryHelper;
import com.amwell.service.ILineService;
import com.amwell.service.IMgrbusinessService;
import com.amwell.service.IPublishLineService;
import com.amwell.service.ISpecialLineService;
import com.amwell.service.ISysAreaService;
import com.amwell.vo.Company;
import com.amwell.vo.CompanyLine;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineClassScheduleVo;
import com.amwell.vo.LineEntity;
import com.amwell.vo.LineOperateLogEnum;
import com.amwell.vo.LineOperateLogVo;
import com.amwell.vo.LineStationVo;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.PageBean;
import com.amwell.vo.StationInfo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.amwell.vo.app.LineBaseInfo;
import com.amwell.vo.app.bean.AppVo_6;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 发布线路
 * 
 * @author shawn.zheng
 */
@ParentPackage("user-finit")
@Namespace("/publishLine")
@Scope("prototype")
@SuppressWarnings("all")
public class PublishLineAction extends BaseAction {

	private static final String KEY_SESSION_USER = "userInfo";
	
	private Logger logger = Logger.getLogger(PublishLineAction.class);

	/**
	 * 发布线路业务
	 */
	@Autowired
	private IPublishLineService publishLineService;

	/**
	 * 区域服务
	 */
	@Autowired
	private ISysAreaService sysAreaService;

	/**
	 * 专线服务
	 */
	@Autowired
	private ISpecialLineService specialLineService;

	/**
	 * 商户服务
	 */
	@Autowired
	private IMgrbusinessService mgrbusinessService;

	/**
	 * 线路服务
	 */
	@Autowired
	private ILineService lineService;
	
	private String gobackSign;
	
	
	/**
	 * 1.跳转到编辑线路基本信息
	 * 
	 * @return
	 */
	@Action(value = "loadLineBaseInfo", results = { @Result(name = "success", location = "../../view/line/lines_plan.jsp") })
	public String loadLineBaseInfo() {
		try {
			// 编辑线路信息的时候的线路id,创建的时候不传此参数
			String lineBaseId = request.getParameter("lineBaseId");
			String toDetailCurrentPageIndex = request.getParameter("currentPageIndex");
			if(null!=toDetailCurrentPageIndex){
				session.put("toDetailCurrentPageIndex", Integer.parseInt(toDetailCurrentPageIndex));
			}
			//详情回跳保存查询分布信息
			if(null!=toDetailCurrentPageIndex){
				session.put("toDetailCurrentPageIndex", Integer.parseInt(toDetailCurrentPageIndex));
				session.put("goback","1");
				gobackSign="1";
			}
			// 加载省份数据
			SysArea sysArea = new SysArea();
			sysArea.setFdCode("-1");
			List<SysArea> proSysAreas = sysAreaService.querySysArea(sysArea);
			//查询客服数据
			List<SysAdminVo> userList = lineService.getAdminName();
			request.setAttribute("userList", userList);
			String isCopy = request.getParameter("isCopy");
			request.setAttribute("isCopy", isCopy);
			request.setAttribute("lineBaseId", lineBaseId);
			request.setAttribute("proSysAreas", proSysAreas);
		} catch (Exception e) {
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
		}
		return "success";
	}
	
	@Action(value = "getLineBelonger" , results = {@Result(type="json")})
	public String getLineBelonger(){
		//查询客服数据
		List<SysAdminVo> userList = lineService.getAdminName();
		JsonWriter.write(userList);
//		request.setAttribute("userList", userList);
		return null;
		
	}
	
	/**
	 * 1.创建线路-添加站点地图
	 */
	@Action(value = "addLineMap", results = { @Result(name = "success", location = "../../view/line/pop-addMap.jsp") })
	public String addLineMap() {
		// 站点文本域的id
		String id = request.getParameter("id");
		// 经度
		String lng = request.getParameter("lng");
		// 纬度
		String lat = request.getParameter("lat");
		if (StringUtils.isEmpty(id)) {
			throw new RuntimeException("id 为空");
		}
		if (StringUtils.isEmpty(lng)) {
			throw new RuntimeException("lng 为空");
		}
		if (StringUtils.isEmpty(lat)) {
			throw new RuntimeException("lat 为空");
		}

		// 加载省份数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		List<SysArea> proSysAreas = sysAreaService.querySysArea(sysArea);
		
		request.setAttribute("proSysAreas", proSysAreas);
		request.setAttribute("id", id);
		request.setAttribute("lng", lng);
		request.setAttribute("lat", lat);
		return SUCCESS;
	}
	
	/**
	 * 进入全景
	 * @return
	 */
	@Action(value="addPanorama",results={@Result(name="success",location="../../view/line/pop-addPanorama.jsp")})
	public String addPanorama(){
		String id = request.getParameter("id");
		String lng = request.getParameter("lng");
		String lat = request.getParameter("lat");
		request.setAttribute("id", id);
		request.setAttribute("lng", lng);
		request.setAttribute("lat", lat);
		return SUCCESS;
	}
	
	/**
	 * 1.显示线路计划
	 * 
	 * @return
	 */
	@Action(value = "popLinePlan", results = { @Result(name = "success", location = "../../view/line/pop-linePlan.jsp") })
	public String popLinePlan() {
		try {
			// 站点数据json
			String stationArr = request.getParameter("stationArr");
			if (StringUtils.isEmpty(stationArr)) {
				throw new RuntimeException("stationArr 为空");
			}
			stationArr = URLDecoder.decode(stationArr, "utf-8");
			stationArr = stationArr.replace("\"", "\\\"");

			request.setAttribute("stationArr", stationArr);
		} catch (Exception e) {
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 1.保存线路的基础信息
	 */
	@Action(value = "saveLineBaseInfo")
	public void saveLineBaseInfo() {
		Map<String, Object> resultData = new HashMap<String,Object>();
		try {
			// 站点信息JSON字符串
			String stationArrStr = request.getParameter("stationArr");
			// 填充线路BO
			LineBaseInfo lineBaseInfo = new LineBaseInfo();
			// 线路Id 添加线路的时候不用传
			lineBaseInfo.setLineBaseId(request.getParameter("lineBaseId"));
			//线路运营人员
			lineBaseInfo.setLineManager(request.getParameter("lineManager"));
			// 省份编码
			lineBaseInfo.setProvinceCode(request.getParameter("provinceCode"));
			// 城市编码
			lineBaseInfo.setCityCode(request.getParameter("cityCode"));
			// 线路名称
			lineBaseInfo.setLineName(request.getParameter("lineName"));
			// 线路全程里程
			lineBaseInfo.setLineKm(request.getParameter("lineKm"));
			// 线路全程预计时间
			lineBaseInfo.setLineTime(request.getParameter("lineTime"));
			// 线路通票价格
			lineBaseInfo.setOrderPrice(request.getParameter("orderPrice"));
			// 线路原价
			lineBaseInfo.setOriginalPrice(request.getParameter("originalPrice"));
			// 原价生效开关
			lineBaseInfo.setOriginalFlag(request.getParameter("originalFlag"));
			// 线路子类型 0:上下班 1:旅游
			lineBaseInfo.setLineSubType(request.getParameter("lineSubType"));
			// 线路的状态 添加线路的时候为0 修改线路为数据库的状态
			lineBaseInfo.setLineStatus(request.getParameter("lineStatus"));
			//保存操作记录
			int flag = checkUpdate1(lineBaseInfo.getLineBaseId(), lineBaseInfo,stationArrStr);
			//查询运营人员ID
			if(lineBaseInfo.getLineManager()!=null){
				SysAdminVo lineManagerVo = publishLineService.getlineManagerInfoByName(lineBaseInfo.getLineManager());
				lineBaseInfo.setLineManager(lineManagerVo.getUserId());
			}
			//保存线路的基础信息
			Map<String,String> resultMap = new HashMap<String,String>();
			String lineBaseId = publishLineService.saveLineBaseInfo(getSessionUserId(),stationArrStr, lineBaseInfo,resultMap);


			resultData.put("a1", "100");
			resultData.put("a2", lineBaseId);
			String isChangeFlag = (String)resultMap.get("isChangeFlag");
			resultData.put("a3", isChangeFlag);
		} catch (Exception e) {
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
			resultData.put("a1", "500");
			resultData.put("a2", e.getMessage());
		}
		try {
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			getResponse().getWriter().print(gsonbuilder.create().toJson(resultData));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改线路的时候加载线路信息
	 */
	@Action(value = "loadLineBaseInfoAjax")
	public void loadLineBaseInfoAjax(){
		Map<String,Object> resultData = new HashMap<String,Object>();
		try{
			// 线路id
			String lineBaseId = request.getParameter("lineBaseId");
			if(StringUtils.isEmpty(lineBaseId)){
				throw new RuntimeException("lineBaseId 为空");
			}
			
			// 线路信息
			LineBaseInfo lineBaseInfo = publishLineService.getLineBaseInfoById(lineBaseId);
//			if(lineBaseInfo!=null){
//				SysAdminVo adminVo = publishLineService.getManagerInfo(lineBaseInfo.getLineManager());
//				
//			}
			// 线路关联信息
			List<LineStationVo> lineStationVoList = publishLineService.getLineStationEntityListByLineInfo(lineBaseInfo);
			
			resultData.put("a1","100");
			resultData.put("a2", lineBaseInfo);
			resultData.put("a3", lineStationVoList);
		}catch(Exception e){
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
			resultData.put("a1", "500");
			resultData.put("a2", e.getMessage());
		}
		try{
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			response.getWriter().print(gsonbuilder.create().toJson(resultData));
		}catch(Exception e){
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 2.跳转到编辑班次基本信息
	 * @return
	 */
	@Action(value = "loadClassInfo", results = { @Result(name = "success", location = "../../view/line/line_class.jsp") })
	public String loadClassInfo(){
		//详情回跳保存查询分布信息
		String toDetailCurrentPageIndex = request.getParameter("currentPageIndex");
		if(null!=toDetailCurrentPageIndex){
			session.put("toDetailCurrentPageIndex", Integer.parseInt(toDetailCurrentPageIndex));
			session.put("goback","1");
			gobackSign="1";
		}
		// 线路id
		String lineBaseId = request.getParameter("lineBaseId");
		if(StringUtils.isEmpty(lineBaseId)){
			throw new RuntimeException("lineBaseId 为空");
		}
		Object lineSearch = super.session.get("lineSearch");
		
		Calendar ca = Calendar.getInstance();
		// 开始的月份
		String startYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		ca.add(Calendar.MONTH, 2);
		// 结束的月份
		String endYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		List<LineClassEntity> lineClassEntityList = publishLineService.getLineClassEntityList(lineBaseId);
		AppVo_6 lineTimeChange = lineService.getLineChangeInfo(lineBaseId);
		LineBaseInfo lineBaseInfo = publishLineService.getLineBaseInfoById(lineBaseId);
		if(lineBaseInfo==null){
			throw new RuntimeException("lineBaseInfo 为空");
		}
		
		request.setAttribute("lineBaseId",lineBaseId);
		request.setAttribute("lineBaseInfo",lineBaseInfo);
		request.setAttribute("startYearAndMonth",startYearAndMonth);
		request.setAttribute("endYearAndMonth",endYearAndMonth);
		request.setAttribute("lineTimeChange",lineTimeChange);
		if(lineClassEntityList!=null&&!lineClassEntityList.isEmpty()){
			LineClassEntity lineClassEntity = lineClassEntityList.get(0);
			request.setAttribute("orderStartTime",lineClassEntity.getOrderStartTime());
			request.setAttribute("orderSeats", lineClassEntity.getOrderSeats());
			request.setAttribute("lineClassEntityList", lineClassEntityList);
			StringBuffer orderDateArray = new StringBuffer();
			for(LineClassEntity lineClassEntityItem : lineClassEntityList){
				orderDateArray.append(lineClassEntityItem.getOrderDate()).append(";");
			}
			request.setAttribute("orderDateArray", orderDateArray.toString());
		}
		return "success";
	}
	
	/**
	 * 2.保存线路的班次信息
	 */
	@Action(value = "saveClassInfo")
	public void saveClassInfo(){
		Map<String, Object> resultData = new HashMap<String,Object>();
		try{
			// 线路Id
			String lineBaseId = request.getParameter("lineBaseId");
			// 班次时间
			String orderStartTime = request.getParameter("orderStartTime");
			// 座位数
			String orderSeats = request.getParameter("orderSeats");
			// 工作日序列
			String orderDateArr = request.getParameter("orderDateArr");
			//删除的班次日期
			String deletingOrderDates = request.getParameter("deletingOrderDates");
			logger.info("orderDateArr="+orderDateArr);
			HttpSession session = request.getSession(false);
			boolean orderSeatsCurrect = true;
			if(!StringUtils.isEmpty(orderSeats)){
				int maxOrderNum = publishLineService.getMaxOrderNumByLineId(lineBaseId);
				int orderSeatsNum = Integer.valueOf(orderSeats);
				if(orderSeatsNum<maxOrderNum){
					orderSeatsCurrect = false;
				}
			}
			if(orderSeatsCurrect){
				//线路操作记录
				int flag = checkUpdate2(lineBaseId, orderStartTime, orderSeats, orderDateArr);
				//保存线路的班次信息
				publishLineService.addLineClassInfoData(lineBaseId, orderStartTime, orderSeats,session,orderDateArr,deletingOrderDates);
				String autoPutTicket = request.getParameter("autoPutTicket");
				logger.info("autoPutTicket="+autoPutTicket);
				if(!StringUtils.isEmpty(autoPutTicket)){
					LineBaseInfo.setAutoPutTicket(lineBaseId, autoPutTicket);
				}else{
					LineBaseInfo.setAutoPutTicket(lineBaseId, "false");
				}
				resultData.put("a1", "100");
			}else{
				resultData.put("a1", "201");
			}
			
		}catch(Exception e){
			//if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			//}
			resultData.put("a1", "500");
		}
		try{
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			getResponse().getWriter().print(gsonbuilder.create().toJson(resultData));
		}catch(Exception e){
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 3.跳转到编辑线路班次价格
	 * @return
	 */
	@Action(value = "loadClassPrice", results = { @Result(name = "success", location = "../../view/line/line_setPrice.jsp") })
	public String loadClassPrice(){
		//详情回跳保存查询分布信息
		String toDetailCurrentPageIndex = request.getParameter("currentPageIndex");
		if(null!=toDetailCurrentPageIndex){
			session.put("toDetailCurrentPageIndex", Integer.parseInt(toDetailCurrentPageIndex));
			session.put("goback","1");
			gobackSign="1";
		}
		// 线路id
		String lineBaseId = request.getParameter("lineBaseId");
		if(StringUtils.isEmpty(lineBaseId)){
			throw new RuntimeException("lineBaseId 为空");
		}
		
		Calendar ca = Calendar.getInstance();
		// 开始的月份
		String startYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		ca.add(Calendar.MONTH, 2);
		// 结束的月份
		String endYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		List<LineClassEntity> lineClassEntityList = publishLineService.getLineClassEntityList(lineBaseId);
		LineBaseInfo lineBaseInfo = publishLineService.getLineBaseInfoById(lineBaseId);
		request.setAttribute("lineOrderPrice", lineBaseInfo.getOrderPrice());
		if(lineClassEntityList==null || lineClassEntityList.isEmpty()){
			request.setAttribute("classSize", "0");
		}else{
			List classPriceArray = new ArrayList();
			for(LineClassEntity lineClassEntity : lineClassEntityList){
				Map<String,Object> classPrice = new HashMap<String,Object>();
				classPrice.put("orderDate", lineClassEntity.getOrderDate());
				if(StringUtils.isEmpty(lineClassEntity.getPrice())){
					classPrice.put("price", lineBaseInfo.getOrderPrice());
				}else{
					classPrice.put("price", lineClassEntity.getPrice());
				}
				classPriceArray.add(classPrice);
			}
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			request.setAttribute("classPriceArrayJson",(gsonbuilder.create().toJson(classPriceArray)).replace("\"", "\\\""));
			request.setAttribute("orderStartTime", lineClassEntityList.get(0).getOrderStartTime());
		}
		request.setAttribute("lineBaseId",lineBaseId);
		request.setAttribute("linePrice",lineBaseInfo.getOrderPrice());
		request.setAttribute("startYearAndMonth",startYearAndMonth);
		request.setAttribute("endYearAndMonth",endYearAndMonth);
		return "success";
	}
	
	/**
	 * 3.保存线路的班次价格
	 */
	@Action(value = "saveClassPrice")
	public void saveClassPrice(){
		Map<String, Object> resultData = new HashMap<String,Object>();
		try{
			// 班次价格信息
			String classPriceInfoArrJson = request.getParameter("classPriceInfoArrJson");
			// 线路Id
			String lineBaseId = request.getParameter("lineBaseId");
			// 发车时间
			String orderStartTime = request.getParameter("orderStartTime");
			//线路操作记录
			int flag = checkUpdate3(classPriceInfoArrJson, lineBaseId);
			//保存线路的班次价格
			publishLineService.updateLineClassPrice(classPriceInfoArrJson,lineBaseId,orderStartTime);
			
			resultData.put("a1", "100");
		}catch(Exception e){
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
			resultData.put("a1", "500");
			resultData.put("a2", e.getMessage());
		}
		try {
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			getResponse().getWriter().print(gsonbuilder.create().toJson(resultData));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 4.跳转到编辑线路的商家
	 * @return
	 */
	@Action(value = "loadBusinessInfo", results = { @Result(name = "success", location = "../../view/line/line_setMerchant.jsp") })
	public String loadBusinessInfo(){
		// 线路Id
		String lineBaseId = request.getParameter("lineBaseId");
		if(StringUtils.isEmpty(lineBaseId)){
			throw new RuntimeException("lineBaseId 为空");
		}
		
		LineBaseInfo lineBaseInfo = publishLineService.getLineBaseInfoById(lineBaseId);
		if(lineBaseInfo==null){
			throw new RuntimeException("lineBaseInfo 为空");
		}
		if(!StringUtils.isEmpty(lineBaseInfo.getBusinessId())){
			MgrBusinessEntity merchant = mgrbusinessService.getMerchantDetail(lineBaseInfo.getBusinessId());
			if(null != merchant){
				request.setAttribute("merchant",merchant);
				request.setAttribute("businessId",merchant.getBusinessId());
				request.setAttribute("businessName",merchant.getBrefName());
			}
		}
		List<LineClassScheduleVo> lineClassScheduleVoList = publishLineService.getLineClassScheduleVoList(lineBaseId);
		List<LineClassEntity> lineClassEntityList = publishLineService.getLineClassEntityList(lineBaseId);
		if(lineClassEntityList!=null&&!lineClassEntityList.isEmpty()){
			LineClassEntity lineClassEntity = lineClassEntityList.get(0);
			request.setAttribute("orderStartTime",lineClassEntity.getOrderStartTime());
			request.setAttribute("orderSeats", lineClassEntity.getOrderSeats());
			request.setAttribute("lineClassEntityList", lineClassEntityList);
			StringBuffer orderDateArray = new StringBuffer();
			for(LineClassEntity lineClassEntityItem : lineClassEntityList){
				orderDateArray.append(lineClassEntityItem.getOrderDate()).append(";");
			}
			request.setAttribute("orderDateArray", orderDateArray.toString());
			request.getSession().setAttribute("orderDateArray", orderDateArray.toString());
		}
		
		request.setAttribute("lineBaseId", lineBaseId);
		request.setAttribute("lineClassScheduleVoList", lineClassScheduleVoList);
		return "success";
	}
	
	/**
	 * 加载商家列表内容
	 */
	@Action(value = "loadBusinessListAjax")
	public void loadBusinessListAjax() {
		Map<String,Object> resultData = new HashMap<String,Object>();
		try {
			String provinceCode = request.getParameter("provinceCode");
			String cityCode = request.getParameter("cityCode");
			String brefName = request.getParameter("brefName");

			QueryHelper query = new QueryHelper(" from mgr_business")
					.addSelectClause("select * ")
					.addCondition(!StringUtils.isEmpty(brefName),
							"brefName like ? ", "%" + brefName + "%")
					.addCondition(!StringUtils.isEmpty(provinceCode),
							"provinceCode='" + provinceCode + "'")
					.addCondition(!StringUtils.isEmpty(cityCode),
							"areaCode='" + cityCode + "'");
			PageBean pageBean = publishLineService.queryBusinessPageBean(0,
					1000000, query);
			List<MgrBusinessEntity> mgrBusinessEntities = pageBean
					.getRecordList();
			resultData.put("a1", "100");
			resultData.put("a2", mgrBusinessEntities);
		} catch (Exception e) {
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
			resultData.put("a1", "500");
			resultData.put("a2", e.getMessage());
		}
		try {
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			getResponse().getWriter().println(gsonbuilder.create().toJson(resultData));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 跳转到线路商家弹窗
	 * @return
	 */
	@Action(value = "loadBusinessList", results = { @Result(name = "success", location = "../../view/line/pop-selectMerchant1.jsp") })
	public String loadBusinessList(){
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		List<SysArea> proSysAreas = sysAreaService.querySysArea(sysArea);
		request.setAttribute("proSysAreas",proSysAreas);
		String lineBaseId = request.getParameter("lineBaseId");
		if(!StringUtils.isEmpty(lineBaseId)){
			LineBaseInfo lineBaseInfo = publishLineService.getLineBaseInfoById(lineBaseId);
			request.setAttribute("lineBaseInfo",lineBaseInfo);
		}
		return "success";
	}
	
	/**
	 * 4.保存线路的商家
	 */
	@Action(value = "saveBusinessInfo")
	public void saveBusinessInfo(){
		Map<String,Object> resultData = new HashMap<String,Object>();
		try{
			// 商户Id
			String businessId = request.getParameter("businessId");
			// 线路Id
			String lineBaseId = request.getParameter("lineBaseId");
			//线路操作
			LineBaseInfo voInfo = new LineBaseInfo();
			voInfo.setBusinessId(businessId);
			int flag = checkUpdate1(lineBaseId, voInfo,null);
			//保存线路商家
			publishLineService.saveLineBusiness(lineBaseId, businessId);
			
			resultData.put("a1", "100");
		}catch(Exception e){
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
			resultData.put("a1", "500");
			resultData.put("a2",e.getMessage());
		}
		try {
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			getResponse().getWriter().print(gsonbuilder.create().toJson(resultData));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 5.跳转到最终保存线路
	 * 
	 * @return
	 * 
	 */
	@Action(value = "loadCommitLine", results = { @Result(name = "success", location = "../../view/line/line_finished.jsp") })
	public String loadCommitLine() {
		session.put("goback","1");
		// 线路id
		String lineBaseId = request.getParameter("lineBaseId");
		if (StringUtils.isEmpty(lineBaseId)) {
			throw new RuntimeException("lineBaseId 为空");
		}

		CompanyLine companyLine = publishLineService.getCompanyLine(lineBaseId);
		if (companyLine != null) {
			String companyId = companyLine.getCompanyId();
			Company company = publishLineService.getCompanyById(companyId);
			if (company != null) {
				request.setAttribute("companyName", company.getCompanyName());
			}
			request.setAttribute("companyId", companyLine.getCompanyId());
		}
		LineBaseInfo lineBaseInfo = publishLineService.getLineBaseInfoById(lineBaseId);

		request.setAttribute("lineBaseId", lineBaseId);
		request.setAttribute("status", lineBaseInfo.getLineStatus());
		SysAdminVo sysAdmin = (SysAdminVo) request.getSession().getAttribute("userInfo");
		String userId = sysAdmin.getUserId();
		if("150824164220630730".equals(sysAdmin.getUserId())){
			request.setAttribute("forceOpenFlag", "true");
		}
		return "success";
	}
	
	/**
	 * 5.最终保存线路
	 */
	@Action(value = "saveCommitLine")
	public void saveCommitLine() {
		Map<String,Object> resultData = new HashMap<String,Object>();
		//详情回跳保存查询分布信息
		session.put("goback","1");
		try {
			// 线路Id
			String lineBaseId = request.getParameter("lineBaseId");
			
			logger.info("lineBaseId="+lineBaseId);
			
			// 公司id
			String companyId = request.getParameter("companyId");
			// 线路状态
			String status = request.getParameter("status");
			logger.info("status="+status);
			SysAdminVo sysAdmin = (SysAdminVo) session.get("userInfo");

			publishLineService.saveCommit(companyId, lineBaseId,status,sysAdmin);

			resultData.put("a1", "100");
			resultData.put("a2", "保存成功");
		} catch (Exception e) {
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
			resultData.put("a1", "500");
			resultData.put("a2", e.getMessage());
		}
		try {
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			getResponse().getWriter().print(gsonbuilder.create().toJson(resultData));
		} catch (Exception e) {
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 批量处理拆分线路
	 */
	@Action(value = "batchLineSplit")
	public void batchLineSplit(){
		String message = null;
		try {
			message = publishLineService.batchSplitLine();
			if(StringUtils.isEmpty(message)){
				message = "批量处理成功";
			}
		} catch (Exception e) {
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
			message = e.getMessage();
		}
		try {
			getResponse().getWriter().print(message);
		} catch (Exception e) {
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 创建上下班线路-选择vip客户弹窗
	 */
	@Action(value = "selectVIP", results = { @Result(name = "success", location = "../../view/line/pop-selectVIP1.jsp") })
	public String selectVIP() {
		
		// 加载省份数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		// 区域省份集合
		List<SysArea> proSysAreas = sysAreaService.querySysArea(sysArea);
		
		request.setAttribute("proSysAreas", proSysAreas);
		return SUCCESS;
	}
	
	/**
	 * 加载vip客户列表
	 */
	@Action(value = "loadSpecialLineAjax", results = { @Result(type = "json") })
	public void loadSpecialLineAjax(){
		Map<String, Object> resultData = new HashMap<String,Object>();
		try{
			String provinceCode = request.getParameter("provinceCode");
			String cityCode = request.getParameter("cityCode");
			String companyName = request.getParameter("companyName");

			QueryHelper query = new QueryHelper(" FROM company c")
			.addSelectClause("SELECT c.companyId,c.companyName,(SELECT s.areaName FROM sys_area s WHERE c.companyProvince=s.arearCode) AS companyProvince,(SELECT s.areaName FROM sys_area s WHERE c.companyCity=s.arearCode) AS companyCity,(SELECT COUNT(*) FROM company_line cl WHERE c.companyId = cl.companyId ) AS lineCount,(SELECT COUNT(*) FROM company_passenger cp WHERE cp.companyId=c.companyId) AS passengerCount")
			.addCondition(!StringUtils.isEmpty(companyName),"c.companyName like ? ","%"+companyName+"%")
			.addCondition(!StringUtils.isEmpty(provinceCode),"c.companyProvince='"+provinceCode+"'")
			.addCondition(!StringUtils.isEmpty(cityCode), "c.companyCity='"+cityCode+"'")
			.addOrderProperty("c.createOn", false);
		    PageBean pageBean = specialLineService.getPageBean(0,1000000,query);
		    List<Company> companyList = pageBean.getRecordList();
		    resultData.put("a1", "100");
		    resultData.put("a2", companyList);
		}catch(Exception e){
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
			resultData.put("a1", "500");
			resultData.put("a2", e.getMessage());
		}
	    try {
	    	GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			getResponse().getWriter().println(gsonbuilder.create().toJson(resultData));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getSessionUserId() {
		SysAdminVo sysAdmin = (SysAdminVo) request.getSession().getAttribute(KEY_SESSION_USER);
		return null == sysAdmin ? "" : sysAdmin.getUserId();
	}
	
	/**
	 * 加载vip客户列表
	 */
	@Action(value = "getVersion")
	public void getVersion(){
		try{
			Map<String, Object> resultData = new HashMap<String,Object>();
			resultData.put("version", "1.1");
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			getResponse().getWriter().println(gsonbuilder.create().toJson(resultData));
		}catch(Exception e){
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
		}
	}
	
	@Action(value ="isBuyForStation")
	public void isBuyForStation(){
		try{
			Map<String,Object> resultData = new HashMap<String,Object>();
			String stationId = request.getParameter("stationId");
			boolean isBuy = publishLineService.isBuyForStation(stationId);
			resultData.put("isBuy", isBuy);
			GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
			getResponse().getWriter().println(gsonbuilder.create().toJson(resultData));
		}catch(Exception e){
			if(!(e instanceof RuntimeException)){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 切换供应商
	 * @return
	 * @throws   
	 */
	@Action(value = "changeBusiness", results = { @Result(type = "json") })
	public String changeBusiness() throws IOException{
		String oldBusiness = request.getParameter("oldBusiness");//老商户
		String newBusiness = request.getParameter("newBusiness");//新商户
		String seatCount = request.getParameter("seatCount");//座位数
		String lineId = request.getParameter("lineId");//线路ID
		String result = publishLineService.changeBusiness(oldBusiness,newBusiness,seatCount,lineId);
		if("1".equals(result)){
			getResponse().getWriter().println("1");
		}
		if(oldBusiness!=null&&newBusiness!=null){
			String[] lineLog=new String[]{oldBusiness,newBusiness};
			session.put("lineLog", lineLog);
		}
		return null;
	}
	
	
	//线路操作记录1
	private int checkUpdate1(String lineBaseId,LineBaseInfo voInfo, String stationArrStr){
		LineEntity vo = lineService.getLineByLineBaseId(lineBaseId);
		LineOperateLogVo operaVo = null;
		SysAdminVo adminVo = (SysAdminVo) session.get("userInfo");
		int flag=0;
		if(vo!=null){
			operaVo = new LineOperateLogVo();
			StringBuffer content = new StringBuffer();
		//判断线路商家是否修改	
			if(voInfo.getBusinessId()!=null && !"".equals(voInfo.getBusinessId()) ){
				if( !"".equals(vo.getBusinessId())){
					String[] businLog = (String[]) session.get("lineLog");
					if(businLog!=null){
						MgrBusinessEntity vo1 = mgrbusinessService.getMerchantDetail(businLog[0]);
						MgrBusinessEntity vo2 = mgrbusinessService.getMerchantDetail(businLog[1]);
						content.append(LineOperateLogEnum.BusinessId.getValue()+(":{"+vo1.getBrefName()+"→"+vo2.getBrefName()+"},"));
						session.put("lineLog", "");
					}
				}else{
					MgrBusinessEntity vo1 = mgrbusinessService.getMerchantDetail(voInfo.getBusinessId());
					content.append(LineOperateLogEnum.BusinessId.getValue()+(":{"+vo1.getBrefName()+"},"));
				}
			}else{
				if(!voInfo.getLineName().equals(vo.getLineName())){
					content.append(LineOperateLogEnum.LineName.getValue()+(":{原线路名="+vo.getLineName()+"},"));
				}
				String lineKm = String.valueOf(vo.getLineKm());
				if(!voInfo.getLineKm().equals(lineKm)){
					content.append(LineOperateLogEnum.LineKm.getValue()+(":{原里程="+vo.getLineKm()+"},"));
				}
				String lineTime = String.valueOf(vo.getLineTime());
				if(!voInfo.getLineTime().equals(lineTime)){
					content.append(LineOperateLogEnum.LineTime.getValue()+(":{原行程时间="+vo.getLineTime()+"},"));
				}
				String orderPrice = vo.getOrderPrice().toString();
				if(!voInfo.getOrderPrice().equals(orderPrice)){
					content.append(LineOperateLogEnum.OriginalPrice.getValue()+(":{原价格="+vo.getOrderPrice()+"},"));
				}
				String lineStatus = String.valueOf(vo.getLineStatus());
				if(!voInfo.getLineStatus().equals(lineStatus)){
					content.append(LineOperateLogEnum.LineStatus.getValue());
				}
				//站点增加判断
				List<StationInfo> oldStationList = publishLineService.getStationListById(lineBaseId);
				if(oldStationList!=null && !oldStationList.isEmpty()){
					String oldStations="";
					for(StationInfo stationVo : oldStationList){
						oldStations+=stationVo.getName()+";";
					}
					Gson gson = new Gson();
					List<Map<String, Object>> newStationList = gson.fromJson(stationArrStr, List.class);
					StringBuffer satations = new StringBuffer();
					if(oldStationList.size()==newStationList.size()){
						for(Map<String, Object> stationMap : newStationList){
							String stationName = (String) stationMap.get("stationName");
							if(!oldStations.contains(stationName)){
								content.append(LineOperateLogEnum.LineStation.getValue()+(":{原站点="+oldStations+"},"));
								break;
							}
						}
					}else{
						content.append(LineOperateLogEnum.LineStation.getValue()+(":{原站点="+oldStations+"},"));
					}
					
				}
			}
			//content不为空时,有修改内容
			String contentStr = content.toString();
			if(contentStr!=null && !"".equals(contentStr)){
				contentStr = contentStr.substring(0, contentStr.length()-1);
				operaVo.setContent(contentStr);
				operaVo.setUpdateBy(adminVo.getUserId());
				operaVo.setUpdateOn(MyDate.getMyDateLong());
				operaVo.setLineBaseId(lineBaseId);
				flag = publishLineService.logLineOperate(operaVo);
			}
			
		}
		return flag;
		
	}
	
	//线路操作记录2
	private int checkUpdate2(String lineBaseId,String orderStartTime,String orderSeats,String orderDateArr){
		List<LineClassEntity> lineClassVoes = publishLineService.getLineClassEntityListByLineBaseId(lineBaseId);
		LineOperateLogVo lineLogVo = new LineOperateLogVo();
		int flag = 0;
		SysAdminVo adminVo = (SysAdminVo) session.get("userInfo");
		StringBuffer content = new StringBuffer();
		if(orderDateArr!=null && !"".equals(orderDateArr)){
			//查询班排记录
			String oldClass = "";
			if(lineClassVoes!=null){
				for(LineClassEntity classVo : lineClassVoes){
					oldClass+=classVo.getOrderDate()+";";
				}
				//新排班数组
			    String[] newClasses = orderDateArr.split(";");
				for(String newClass : newClasses){
					if(!oldClass.contains(newClass)){
						content.append(LineOperateLogEnum.OrderDate.getValue());
						break;
					}
				}
				if(!lineClassVoes.get(0).getOrderStartTime().equals(orderStartTime)){
					content.append(LineOperateLogEnum.OrderStartTime.getValue());
				}
				if(lineClassVoes.get(0).getOrderSeats()!=Integer.valueOf(orderSeats)){
					content.append(LineOperateLogEnum.OrderSeats.getValue());
				}
			}
		}
		String contentStr = content.toString();
		if(contentStr!=null && !"".equals(contentStr)){
			contentStr=contentStr.substring(0,contentStr.length()-1);
			lineLogVo.setContent(contentStr);
			lineLogVo.setUpdateBy(adminVo.getUserId());
			lineLogVo.setUpdateOn(MyDate.getMyDateLong());
			lineLogVo.setLineBaseId(lineBaseId);
			flag = publishLineService.logLineOperate(lineLogVo);
		}
		
		return flag;
	}
	
	//线路操作记录3
	private int checkUpdate3(String classPriceInfoArrJson,String lineBaseId){
		Gson gson = new Gson();
		//原来的班次价格
		List<LineClassEntity> lineClassVoList = publishLineService.getLineClassEntityListByLineBaseId(lineBaseId);
		//修改的班次价格
		List<Map<String, Object>> classPriceInfoMap = gson.fromJson(classPriceInfoArrJson, List.class);
		int flag = 0;
		LineOperateLogVo logVo = new LineOperateLogVo();
		String newPrice = "";
		String oldPrice = "";
		for(Map<String, Object> map : classPriceInfoMap){
			newPrice=map.get("price")+newPrice;
		}
		for(LineClassEntity vo :lineClassVoList){
			oldPrice+=vo.getPrice();
		}
		if(!oldPrice.contains(newPrice)){
			logVo.setContent(LineOperateLogEnum.Price.getValue());
			SysAdminVo admin = (SysAdminVo) session.get("userInfo");
			logVo.setUpdateBy(admin.getUserId());
			logVo.setUpdateOn(MyDate.getMyDateLong());
			logVo.setLineBaseId(lineBaseId);
			flag = publishLineService.logLineOperate(logVo);
		}
		return flag;
		
	}

	public String getGobackSign() {
		return gobackSign;
	}
	public void setGobackSign(String gobackSign) {
		this.gobackSign = gobackSign;
	}
	
}