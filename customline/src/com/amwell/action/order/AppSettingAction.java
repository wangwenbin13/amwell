package com.amwell.action.order;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.MyDate;
import com.amwell.entity.Page;
import com.amwell.service.IAppSettingService;
import com.amwell.vo.AppSettingVo;
import com.amwell.vo.AppVersionVo;
import com.amwell.vo.IosVersionVo;
import com.amwell.vo.SysAdminVo;

/**
 * APP设置
 *
 */
@ParentPackage("user-finit")
@Namespace("/appAction")
public class AppSettingAction extends BaseAction{
	
	private static final long serialVersionUID = 9160765633956021592L;

	private static final Logger log = Logger.getLogger(AppSettingAction.class);
	
	@Autowired
	private IAppSettingService appSettingService;
	
	private AppSettingVo appSettingVo;
	
	private String sendType;
	
	private String appVsn;
	
	private String iosVsn;
	
	private AppVersionVo andriodVo;
	
	private IosVersionVo iosVo;
	
	private int currentPageIndex=0;
	
	private int pageSize;
		
	private String client;
	@Action(value="appSetting",results={@Result(name="success",location="../../view/order/appSetting.jsp")})
	public String appSetting ()throws Exception{
		appSettingVo=appSettingService.getAppSetting();
		sendType=String.valueOf(appSettingVo.getSendType());
		
		return SUCCESS;
	}
	
	
	/**
	 * 添加或修改APP设置
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addAppSetting", results={@Result(type="json")})
	public void addAppSetting() throws Exception{
		String json="error";
		AppSettingVo appSetModel = setModel();
		int flag = appSettingService.updateAppCount(appSetModel);
		if(flag>0){
			json="success";
		}
		JsonWriter.write(json);
	}
	
	
	private AppSettingVo setModel(){
		String WXcontent=request.getParameter("WXcontent");  //微信自动回复内容
		int appSetInt=Integer.parseInt(request.getParameter("app"));    //APP订单有效期
		int bcTime=Integer.valueOf(request.getParameter("bcTime")); //包车需求有效期
		int delayTime=Integer.valueOf(request.getParameter("delayTime"));
		String sendType1=request.getParameter("sendType"); //验证码发送方式
		int maxPcScope=Integer.valueOf(request.getParameter("maxPcScope"));
		int maxBusScope=Integer.valueOf(request.getParameter("maxBusScope"));
		int returnTicketFree = Integer.valueOf(request.getParameter("returnTicketFree"));//退票手续费
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo admin =(SysAdminVo) httpSession.getAttribute("userInfo");
		
//		Pattern p = Pattern.compile("^\\d{1,2}$");   //判断appset值为两位的数字（且在0到60的范围内）
		
		AppSettingVo appSetModel=new AppSettingVo();
		appSetModel.setOrderValiteTime(appSetInt);
		appSetModel.setBcTime(bcTime);
		appSetModel.setDelayTime(delayTime);
		appSetModel.setMaxBusScope(maxBusScope);
		appSetModel.setMaxPcScope(maxPcScope);
		appSetModel.setCreateBy(admin.getUserId());
		appSetModel.setSendType(Integer.valueOf(sendType1));
		appSetModel.setWXreply(WXcontent);
		appSetModel.setReturnTicketFree(returnTicketFree);
		return appSetModel;
	}
	
	/**更新版本内容页面跳转
	 * @return
	 */
	@Action(value="appVersion" , results={@Result(name="success",location="../../view/order/appVersion.jsp")})
	public String appVersion(){
		String vsn = appSettingService.getNewVsn();
		andriodVo = appSettingService.getAppVesion("1");
		iosVo = appSettingService.getIosVesion("1");
		if(vsn!=null &&!"".equals(vsn)){
			String vsns[] = vsn.split("/");
			appVsn = "乘客最新版本:"+vsns[0]+"[标识<font color='red'>"+vsns[1]+"</font>];&nbsp;&nbsp;&nbsp;&nbsp;"+"司机最新版本:"+vsns[2]+"[标识<font color='red'>"+vsns[3]+"</font>]";
			if(vsns.length>4){
				iosVsn = "乘客最新版本:"+vsns[4]+";"+"司机最新版本"+vsns[5];
			}
		}
		return SUCCESS;
	}
	
	/**添加新版本更新信息(android)
	 * @return
	 * @throws IOException 
	 */
	@Action(value="appVsnSet" , results={@Result(type="json")})
	public String appVsnSet() throws IOException{
		String json = "error";
		int result =0;
		String appUpdate = request.getParameter("appUpdate");
		String appUserType = request.getParameter("appUserType");
		String appWXcontent = request.getParameter("appWXcontent");
		String vsn = request.getParameter("vsn");
		String flag = request.getParameter("flag");
		String url = request.getParameter("url");
		//查询是否有相同的版本号或者版本标识
		AppVersionVo vsVo = appSettingService.queryVsn(vsn,Integer.valueOf(appUserType),flag);
		//查询最大的app版本数据ID值
		AppVersionVo vo =  appSettingService.getAppVesion("");
		vo.setFlag(flag);
		vo.setAppType(appUserType);
		vo.setInfo(appWXcontent);
		vo.setIsmust(appUpdate);
		vo.setType("0");
		vo.setUrl(url);
		vo.setVsn(vsn);
		vo.setVtime(MyDate.getMyDateLong());
		if(vsVo==null || "".equals(vsVo)){
			//添加新版本值时,获取ID最大值,新增数据ID+1;
			vo.setAppId(String.valueOf(Integer.valueOf(vo.getAppId())+1));
			result = appSettingService.addAppVsn(vo);
		}else{
			//如果有相同的版本号或标识则修改数据 ID不变
			vo.setAppId(vsVo.getAppId());
			result = appSettingService.updateAppVsn(vo); 
		}
		if(result>0){
			json="success";
		}
		getResponse().getWriter().print(json);
		return null;
	}
	
	
	/**
	 * 添加新版本更新信息(ios)
	 * @return
	 * @throws IOException 
	 */
	@Action(value="iosVsnSet" , results={@Result(type="json")})
	public String iosVsnSet() throws IOException{
		String json = "error";
		String version = request.getParameter("vsnIos");
		String ismust =  request.getParameter("iosUpdate");
		String appType =  request.getParameter("iosUserType");
		String info =  request.getParameter("iosWXcontent");
		
		String flag = request.getParameter("iosFlag");
		String url = request.getParameter("iosUrl");
		
		int count = appSettingService.queryIosVsn(appType, version,flag);
		IosVersionVo vo = new IosVersionVo();
		vo.setAppType(Integer.valueOf(appType));
		vo.setInfo(info);
		vo.setIsmust(Integer.valueOf(ismust));
		vo.setVersion(version);
		vo.setFlag(flag);
		vo.setUrl(url);
		vo.setVtime(MyDate.getMyDateLong());
		int result=0;
		if(count>0){
			result = appSettingService.updateIosVsn(vo);
		}else{
			result = appSettingService.addIosVsn(vo);
		}
		if(result>0){
			json="success";
		}
		getResponse().getWriter().print(json);
		return null;
	}

	
	/**修改版本设置页面跳转
	 * @return
	 * @throws IOException
	 */
//	@Action(value="updateVsn" , results={@Result(name="success" , location="../../view/order/appUpdateVsn.jsp")})
//	public String updateVsn() throws IOException{
//		client = request.getParameter("type");
//		if("1".equals(client)){
//			andriodVo = appSettingService.getAppVesion("1");
//		}else {
//			iosVo = appSettingService.getIosVesion("1");
//		}
//		return SUCCESS;
//	}
	
	/**
	 * 查询android修改版本信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	@Action(value = "andriodVsnMsg", results = { @Result(type = "json") })
	public void andriodVsnUpdate() throws IOException, JSONException {
		getResponse().setContentType("text/html;charset=UTF-8");
		String clientType = request.getParameter("clientType");
		AppVersionVo vo = appSettingService.getAppVesion(clientType);
		if(vo!=null){
			JsonWriter.write(vo);
		}else{
			vo=new AppVersionVo();
			vo.setFlag("");
			vo.setInfo("");
			vo.setUrl("");
			vo.setVsn("");
			JsonWriter.write(vo);
		}
	}
	
	/**
	 * 查询IOS修改版本信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	@Action(value = "iosVsnMsg", results = { @Result(type = "json") })
	public void iosVsnMsg() throws IOException {
		String clientType = request.getParameter("clientType");
		IosVersionVo vo = appSettingService.getIosVesion(clientType);
		if (vo != null) {
			JsonWriter.write(vo);
		} else {
			String json = "error";
			JsonWriter.writeString(json);
		}
	}
	
	/**
	 * APP版本列表
	 * @return
	 */
	@Action(value="appVsnList" , results={@Result(name="success",location="../../view/order/appVsnList.jsp")})
	public String appVsnList(){
		currentPageIndex = request.getParameter("p")==null?0:Integer.valueOf(request.getParameter("p"));
		pageSize = 20;
		map = appSettingService.getAppVsnList(currentPageIndex,pageSize);
		list = (List) map.get("list");
		page = (Page) map.get("page");
		return SUCCESS;
		
	}
	
	/**查询IOS版本列表
	 * @return
	 */
	@Action(value="iosVsnList" , results={@Result(name="success",location="../../view/order/iosVsnList.jsp")})
	public String iosVsnList(){
		currentPageIndex = request.getParameter("p")==null?0:Integer.valueOf(request.getParameter("p"));
		pageSize = 20;
		map = appSettingService.getIosVsnList(currentPageIndex,pageSize);
		list = (List) map.get("list");
		page = (Page) map.get("page");
		return SUCCESS;
	}
	
	/**删除APP版本信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="deleteAppVsn",results={@Result(type="json")})
	public String deleteAppVsn() throws IOException{
		getResponse().setContentType("text/html,setchar=UTF-8");
		String json="error";
		String appId=request.getParameter("appId");
		if(appId!=null && !"".equals(appId)){
			int flag = appSettingService.deleteAppVsn(appId);
			if(flag>0){
				json="success";
			}
		}
		System.out.println(json);
		getResponse().getWriter().print(json);
		return null;
	}
	
	/**删除IOS版本信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value="deleteIosVsn",results={@Result(type="json")})
	public String deleteIosVsn() throws IOException{
		getResponse().setContentType("text/html,setchar=UTF-8");
		String json="error";
		String appId=request.getParameter("appId");
		if(appId!=null && !"".equals(appId)){
			int flag = appSettingService.deleteIosVsn(appId);
			if(flag>0){
				json="success";
			}
		}
		System.out.println(json);
		getResponse().getWriter().print(json);
		return null;
	}
	
	
	

	public AppSettingVo getAppSettingVo() {
		return appSettingVo;
	}
	public void setAppSettingVo(AppSettingVo appSettingVo) {
		this.appSettingVo = appSettingVo;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getAppVsn() {
		return appVsn;
	}
	public void setAppVsn(String appVsn) {
		this.appVsn = appVsn;
	}
	public String getIosVsn() {
		return iosVsn;
	}
	public void setIosVsn(String iosVsn) {
		this.iosVsn = iosVsn;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public AppVersionVo getAndriodVo() {
		return andriodVo;
	}
	public void setAndriodVo(AppVersionVo andriodVo) {
		this.andriodVo = andriodVo;
	}
	public IosVersionVo getIosVo() {
		return iosVo;
	}
	public void setIosVo(IosVersionVo iosVo) {
		this.iosVo = iosVo;
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
