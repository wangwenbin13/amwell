package com.amwell.action.bc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
import com.amwell.commons.HttpRequestUtils;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.MyDate;
import com.amwell.commons.PropertyManage;
import com.amwell.commons.SystemConstants;
import com.amwell.entity.Page;
import com.amwell.service.ICharteredLineService;
import com.amwell.service.IMarketingService;
import com.amwell.service.IPassengerInfoService;
import com.amwell.service.ISysAreaService;
import com.amwell.service.ISysLogService;
import com.amwell.vo.MarketingVo;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.amwell.vo.bc.BcbusinessBiddingVo;
import com.amwell.vo.bc.CharteredLineVo;
import com.amwell.vo.bc.LineStatusEnum;

/**
 * 包车线路Action
 * @author Administrator
 *
 */
@ParentPackage("user-finit")
@Namespace("/charteredLine")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class CharteredLineAction extends BaseAction {

	private static final long serialVersionUID = 8145943845995732077L;
	
	private static final Logger log = Logger.getLogger(CharteredLineAction.class);
	@Autowired
	private ICharteredLineService charteredLineService;
	
	@Autowired
	private ISysLogService logService;
	
	@Autowired
	IMarketingService marketingService;
	
	private int currentPageIndex;
	
	private int pageSize=10;
	
	private CharteredLineVo lineModel;
	
	private BcbusinessBiddingVo offerModel;
	
	private String bcLineId="";
	
	@Autowired
	private ISysAreaService  iSysAreaService;
	
	@Autowired
	private IPassengerInfoService passengerService;
	
	/**
	 * 区域省份集合
	 */
	private List<SysArea> proSysAreas;
	
	private List<SysArea> citySysAreas;
	
	private String provinceCode;
	
	private String cityCode;
	
	
	
	/**
	 * 获取包车线路列表
	 * @return
	 */
	@Action(value = "getCharteredLineList", results = { @Result(name = "success", location = "../../view/bcLine/bsLineList.jsp") })
	public String getCharteredLineList(){
		Map<String, Object> map=new  HashMap<String, Object>();
		map=charteredLineService.getCharteredLineList(search, currentPageIndex, pageSize);
	    list=(List)map.get("list");
	    page=(Page)map.get("page");
//	    charteredLineService.judgeOfferTime();
		//加载省数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		return SUCCESS;
	}
	/**
	 * 包车用户详情
	 * @return
	 */
	@Action(value = "getCharteredLineDetail", results = { @Result(name = "success", location = "../../view/bcLine/bsLineDetail.jsp") })
	public String getCharteredLineDetail(){
		String bcLineId =request.getParameter("bc_line_id");
		this.lineModel=charteredLineService.getCharteredLineDetail(bcLineId);
		long start = MyDate.strToUTCLong(lineModel.getNowTime(), "yyyy-MM-dd HH:mm:ss")/1000;
		long end = MyDate.strToUTCLong(lineModel.getExpireTime(), "yyyy-MM-dd HH:mm:ss")/1000;
		lineModel.setRemainingTime(end-start);
		map=charteredLineService.getBusinessOfferList(bcLineId);
		list=(List)map.get("list");
		return SUCCESS;
	}
	

	/**
	 * 包车信息审核不通过
	 * @return
	 * @throws JSONException 
	 */
	@Action(value="getOutAudit",results = { @Result( type = "json") })
	public String getOutAudit() throws IOException{
		super.getResponse().setContentType("text/html;charset=UTF-8");
		String json="error";
		HttpSession httpSession=ServletActionContext.getRequest().getSession();
		SysAdminVo admin=(SysAdminVo)httpSession.getAttribute("userInfo");      //操作用户
//		rejectContent=URLDecoder.decode(rejectContent, "utf-8");
		String rejectContent=request.getParameter("feedbackMsg");
		String bcLineId=request.getParameter("bcLineId");
		String usePhone=request.getParameter("usePhone");
		CharteredLineVo model=charteredLineService.getCharteredLineDetail(bcLineId);//包车信息
		model.setRejectContent(rejectContent);
		model.setLineStatus(LineStatusEnum.NOT_PASS_AUDIT.getValue());
		model.setOperateTime(MyDate.getMyDate());
		model.setOperatorId(admin.getUserId());
		int flag=charteredLineService.setOutAudit(model);
		this.lineModel=charteredLineService.getCharteredLineDetail(bcLineId);
		
		MarketingVo msgModel=setCheckModel();  //消息详情
		
		List<String> phone=new ArrayList<String>();
		phone.add(usePhone);
		if(model.getSourceFrom()==0){
			//向APP用户推送消息
			String msgId=marketingService.addMessage(msgModel, phone, MyDate.getMyDateLong());
			marketingService.sendAPPMsg(msgId);
		}else if(model.getSourceFrom()==1){
			//向微信用户发送推送消息
			String requestUrl = PropertyManage.getInfoProperty("wechat_back_root")+"sendPushMessageAction/sendBcLineMessage";
			if(StringUtils.hasText(requestUrl)){
				requestUrl+="?passengerId="+model.getPassengerId()+"&bcLineId="+bcLineId+"&msgType=3";
				HttpRequestUtils.sendGetRequest(requestUrl);
			}else{
				log.error("property weixin.push.message.url can't set value.");
			}
		}
		if(flag>0){
			logService.appendLog(admin.getUserId(), super.getClientIp(request), "包车信息审核", "审核成功");
			json="success";
		}else{
			logService.appendLog(admin.getUserId(), super.getClientIp(request),"包车信息审核", "审核失败");
			json="error";
		}
		JsonWriter.write(json);
		return null;
	}
	
	
	private MarketingVo setCheckModel(){
		String context="尊敬的用户您好，您于"+lineModel.getCreateOn()+"所填写的发车时间为："+lineModel.getStartTime()+"，"+lineModel.getBeginAddress()+
				"－"+lineModel.getEndAddress()+"的包车信息审核未通过！如需查看详情，请前往个人中心－包车订单查看!"+SystemConstants.COMPANY_INFO;
		MarketingVo msgModel = new MarketingVo();
		String beginAddress;
		String endAddress;
		if(lineModel.getBeginAddress().length()>5){
			 beginAddress=lineModel.getBeginAddress().substring(0, 5);
		}else{
			 beginAddress=lineModel.getBeginAddress();
		}
		if(lineModel.getEndAddress().length()>5){
			 endAddress=lineModel.getEndAddress().substring(0, 5);
		}else{
			 endAddress=lineModel.getEndAddress();
		}
		String title="包车（"+beginAddress+"－"+endAddress+"）线路，审核不通过";
		msgModel = setMsgModel(context, title, 2);

		return msgModel;
	}
	
	
	/**
	 * 选择商家弹窗
	 * @return
	 */
	@Action(value = "getSelectMerchant", results = { @Result(name = "success", location = "../../view/bcLine/bsLineSelectMerchant.jsp") })
	public String getSelectMerchant(){
		bcLineId=request.getParameter("bcLineId");
//		String random=request.getParameter("random");
		CharteredLineVo bcModel=charteredLineService.getCharteredLineDetail(bcLineId);
		MgrBusinessEntity model=new MgrBusinessEntity();
		model.setProvinceCode(bcModel.getProvinceCode().toString());
		model.setAreaCode(bcModel.getCityCode().toString());
		provinceCode=bcModel.getProvinceCode().toString();
		cityCode=bcModel.getCityCode().toString();
		map=charteredLineService.getSelectMerchant(model);
		list=(List)map.get("list");
		//加载省数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		return SUCCESS;
	}
	
	/**
	 * 根据省份ID获取城市
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value="getProvince",results = { @Result( type = "json") })
	public void getProvince() throws Exception {
		String proId = request.getParameter("proId");
		SysArea sysArea = new SysArea();
		sysArea.setFdCode(proId);
		if(proId!=null && !"".equals(proId)){
			citySysAreas = iSysAreaService.querySysArea(sysArea);
		}
		getResponse().setContentType("text/html;charset=GBK");
		JsonWriter.write(citySysAreas);
	}
	
	/**
	 * 条件查询商家列表
	 */
	@SuppressWarnings("unchecked")
	@Action(value="queryMerchant",results = { @Result( type = "json") })
	public void queryMerchant()throws IOException, JSONException{
		getResponse().setContentType("text/html;charset=UTF-8");
		String companyName=request.getParameter("companyName");
		String provinceCode=request.getParameter("provinceCode");
		String cityCode=request.getParameter("cityCode");
		MgrBusinessEntity model=new MgrBusinessEntity();
		model.setName(companyName);
		if(provinceCode!=null && !"".equals(provinceCode)){
			model.setProvinceCode(provinceCode);
		}
		if(cityCode!=null && !"".equals(cityCode)){
			model.setAreaCode(cityCode);
		}
		map=charteredLineService.getSelectMerchant(model);
		list=(List)map.get("list");
		JsonWriter.write(list);
	}
	
	/**
	 * 发送包车信息给商家
	 * @return
	 */
	@Action(value="sendBcToMerchant",results = { @Result( type = "json") })
	public void sendBcToMerchant()throws IOException,JSONException{
//		String result="success";
		HttpSession httpSession=ServletActionContext.getRequest().getSession();
		SysAdminVo admin=(SysAdminVo)httpSession.getAttribute("userInfo");
		bcLineId=request.getParameter("bcLineId");
		String[] businessIds=request.getParameter("loginIds").split(",");
		int status = sendMsgBusiness(bcLineId,businessIds);
		
		CharteredLineVo bcModel=charteredLineService.getCharteredLineDetail(bcLineId);//包车信息
//		发送短信给商家
		MarketingVo msgModel=new MarketingVo();            
		String context = "您好，用户于" + MyDate.getMyDate() + "发布了一条新的包车信息，（发车时间为："
				+ bcModel.getStartTime() + ",从" + bcModel.getBeginAddress() + "到" + bcModel.getEndAddress()+"），请尽快报价。";
		msgModel = setMsgModel(context,"短信",1);
		//查询商家电话号码
		List<String> phoneList=new ArrayList<String>();
		for(String i:businessIds){
			phoneList.add(charteredLineService.getBusinessPhone(i));
		}
		if(status>0){
			logService.appendLog(admin.getUserId(), super.getClientIp(request), "包车信息状态设置,发送包车给商家", "发送成功");
		    //发送给商家的短信  
			String msgId=marketingService.addMessage(msgModel, phoneList, MyDate.getMyDateLong());
			marketingService.sendAPPMsg(msgId);
			
			if(bcModel.getSourceFrom()==0){
				//向APP用户发送推送信息
				MarketingVo passModel=new MarketingVo();
				List<String> teleList=new ArrayList<String>();
				createPushMsg(bcModel,passModel,teleList);
				
			}
			if(bcModel.getSourceFrom()==1){
				//向微信用户发送推送消息
				String requestUrl = PropertyManage.getInfoProperty("wechat_back_root")+"sendPushMessageAction/sendBcLineMessage";
				if(StringUtils.hasText(requestUrl)){
					requestUrl+="?passengerId="+bcModel.getPassengerId()+"&bcLineId="+bcLineId+"&msgType=1";
					HttpRequestUtils.sendGetRequest(requestUrl);
				}else{
					log.error("property weixin.push.message.url can't set value.");
				}
			}
		  
		}else{
			logService.appendLog(admin.getUserId(), super.getClientIp(request),"包车信息状态设置,发送包车给商家", "发送失败");
		}
		JsonWriter.write(status);
		
	}
	
//	发送短信给商家
	private int sendMsgBusiness(String buclineid,String[] businessIds){
		String expireTime = request.getParameter("expireTime");
		BcbusinessBiddingVo model=new BcbusinessBiddingVo();
		model.setCreateOn(MyDate.getMyDateLong());
		model.setBc_line_id(buclineid);
		model.setBusinessIds(businessIds);
		model.setOfferEndTime(expireTime);
	    return	charteredLineService.sendBcToBusiness(model);//商家报价
	}
	
	
	//向APP用户推送信息
	public void createPushMsg(CharteredLineVo bcModel,MarketingVo passModel,List<String> teleList){
		//推送消息给乘客开始
		String passContest="尊敬的用户您好，您于"+bcModel.getCreateOn()+"所填写的发车时间为："+bcModel.getStartTime()+"，"+bcModel.getBeginAddress()+
				"－"+bcModel.getEndAddress()+"的包车信息审核已通过！目前正在等待商家报价状态，若有新的报价，我们将第一时间反馈到 个人中心－包车订单，请留意!"+SystemConstants.COMPANY_INFO;
		String paBgAddress;
		String paEdAddress;
		if(bcModel.getBeginAddress().length()>5){
			paBgAddress=bcModel.getBeginAddress().substring(0, 5);
		}else{
			paBgAddress=bcModel.getBeginAddress();
		}
		if(bcModel.getEndAddress().length()>5){
			paEdAddress=bcModel.getEndAddress().substring(0, 5);
		}else{
			paEdAddress=bcModel.getEndAddress();
		}
		String title = "包车（"+paBgAddress+"－"+paEdAddress+"）线路，审核已通过";
		passModel = setMsgModel(passContest, title, 2);
		//查询乘客号码
		String passengerId=bcModel.getPassengerId();
		PassengerInfoEntity passBean=passengerService.getPassengerById(passengerId);
		String telephone=passBean.getTelephone();
		teleList.add(telephone);
		String msgPasId=marketingService.addMessage(passModel, teleList, MyDate.getMyDateLong());
		marketingService.sendAPPMsg(msgPasId);
	}
	
	
	/**
	 * 选择商家报价详情
	 * @return
	 */
	@Action(value = "getMerchantPriceDetail", results = { @Result(name = "success", location = "../../view/bcLine/merchantPriceDetail.jsp") })
	public String getMerchantPriceDetail(){
		String offerId=request.getParameter("offerId");
		offerModel=charteredLineService.getBusinessOfferDetail(offerId);
		String biddingId = offerModel.getId();
		map=charteredLineService.getBiddingCars(biddingId);   //车辆数量信息
		list=(List)map.get("list");
		
		return SUCCESS;
	}
	
	//设置消息实体类
	private MarketingVo setMsgModel(String context,String title,int type){
		MarketingVo model = new MarketingVo();
		model.setMsgContext(context);
		model.setTheModule(2);
		model.setMsgSubType(0);
		model.setIssend("1");
		model.setMsgTiTLE(title);
		model.setCreateBy("SYSTEM");
		model.setCreateOn(MyDate.getMyDate());
		if(type==1){
			model.setMsgType(0);
			model.setMsgTarget(2);
		}else if(type==2){
			model.setMsgType(1);
			model.setMsgTarget(0);
		}
		return model;
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
	public CharteredLineVo getLineModel() {
		return lineModel;
	}
	public void setLineModel(CharteredLineVo lineModel) {
		this.lineModel = lineModel;
	}
	public String getBcLineId() {
		return bcLineId;
	}
	public void setBcLineId(String bcLineId) {
		this.bcLineId = bcLineId;
	}
	public BcbusinessBiddingVo getOfferModel() {
		return offerModel;
	}
	public void setOfferModel(BcbusinessBiddingVo offerModel) {
		this.offerModel = offerModel;
	}
	public List<SysArea> getProSysAreas() {
		return proSysAreas;
	}
	public void setProSysAreas(List<SysArea> proSysAreas) {
		this.proSysAreas = proSysAreas;
	}
	public List<SysArea> getCitySysAreas() {
		return citySysAreas;
	}
	public void setCitySysAreas(List<SysArea> citySysAreas) {
		this.citySysAreas = citySysAreas;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	
	
	
}
