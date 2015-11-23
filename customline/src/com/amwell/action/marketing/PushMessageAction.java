package com.amwell.action.marketing;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.MyDate;
import com.amwell.entity.MarketingModel;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ILineService;
import com.amwell.service.IMarketingService;
import com.amwell.vo.MarketingVo;
import com.amwell.vo.MsgTemplateVo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.app.PassengerInfo;

/**
 * 推送APP消息
 * @author Administrator
 *
 */
@ParentPackage("user-finit")
@Namespace("/pushMessage")
public class PushMessageAction extends BaseAction{
//	private static final long serialVersionUID = 8198245251170760188L;

	/**
	 * 短信消息
	 */
	@Autowired
	private IMarketingService iMarketingService;
	
	@Autowired
	private ILineService lineService;
	/**
	 * 记录数的下标记数，0开始，到10
	 */
	private Integer currentPageIndex = 0;
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize = 10;
	/**
	 * 验证未通过的电话号码
	 */
	private List<String> rows=new ArrayList<String>();
	
	/**
	 * 验证重复的手机号码
	 */
	private List<String> sameTelephone=new ArrayList<String>();
	/**
	 * 乘客列表信息
	 */
	private List<PassengerInfo> passengerInfos;
	
	/**
	 * 乘客分布列表信息
	 */
	private Page  passengerPage;
	
    private	List<MarketingModel> APPList;//消息详情内容
    
    private List<PassengerInfo> passengerList;//消息详情乘客信息
    
    private MarketingModel msgModel;
    
	
	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<String> getRows() {
		return rows;
	}

	public void setRows(List<String> rows) {
		this.rows = rows;
	}

	public List<PassengerInfo> getPassengerInfos() {
		return passengerInfos;
	}

	public void setPassengerInfos(List<PassengerInfo> passengerInfos) {
		this.passengerInfos = passengerInfos;
	}

	public Page getPassengerPage() {
		return passengerPage;
	}

	public void setPassengerPage(Page passengerPage) {
		this.passengerPage = passengerPage;
	}

	private File excelFile;
	
	private MsgTemplateVo msgTemplateVo;

	/**
	 *  APP推送消息
	 * @throws Exception
	 */
	@Action(value="pushAPPManager",results={@Result(name="success",location="../../view/marketing/pushAPPManager.jsp")})
	public String pushAPPManager()throws Exception{
		//System.out.println("marketingManager...");
		return SUCCESS;
	}
	
	/**
	 * 推送消息列表
	 * @throws Exception
	 */
	@Action(value="pushAPPMsgList",results={@Result(name="success",location="../../view/marketing/pushAPPMsgList.jsp")})
	public String pushAPPMsgList()throws Exception{
		
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		if(search==null){
			search = new Search();
		}
		search.setField07("2");//消息类型 0:短信消息 1:软件站内消息 2：软件推送消息
		map=iMarketingService.getMarketingList(search, currentPageIndex, pageSize);
		list=(List) map.get("list");
		page=(Page) map.get("page");
		return SUCCESS;
	}
	/**
	 * 推送消息详情
	 * @throws Exception
	 */
	@Action(value="pushAPPMsgDetail",results={@Result(name="success",location="../../view/marketing/pushAPPMsgDetail.jsp")})
	public String pushAPPMsgDetail()throws Exception{
		String messageId=request.getParameter("messageId");
		if(messageId==null && "".equals(messageId)){
			pushAPPManager();
		}
		msgModel=iMarketingService.marketingDetail(messageId);
//		APPList=(List)APPMap.get("list");
//		passengerList=(List)APPMap.get("passengerList");
		return SUCCESS;
	}
	
	/**
	 * 推送消息模板
	 * @throws Exception
	 */
	@Action(value="pushAPPTemplate",results={@Result(name="success",location="../../view/marketing/pushAPPMsgModel.jsp")})
	public String pushAPPTemplate()throws Exception{
		// 获取所有的模板列表
		map=iMarketingService.msgtemplateModelList(3);
		list=(List) map.get("list");
		return SUCCESS;
	}
	/**
	 * 增加推送消息模板
	 * @throws Exception
	 */
	@Action(value="pushAPPMsgModelAdd",results={@Result(name="success",location="../../view/marketing/pushAPPMsgModelAdd.jsp")})
	public String pushAPPMsgModelAdd()throws Exception{
		//System.out.println("marketingManager...");
		return SUCCESS;
	}
	
	/**
	 * 添加短信模板
	 * @return
	 */
	@Action(value="addTemplateModel",results = { @Result( type = "json") })
	public String addTemplateModel()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo msgUser=(SysAdminVo) httpSession.getAttribute("userInfo");//登录用帐号资料
		String userId =msgUser.getUserId();
		String result = "no";
		if(null != msgTemplateVo){
			String time = MyDate.getMyDate();
			msgTemplateVo.setCreateOn(time);
			msgTemplateVo.setCreateBy(userId);
			msgTemplateVo.setUpdateBy(userId);
			msgTemplateVo.setUpdateOn(time);
			msgTemplateVo.setTemplate_status(1); //模板是否有效，1有效0无效
			msgTemplateVo.setTemplate_type(3);
			String count=iMarketingService.addSMSModer(msgTemplateVo);
			result = "yes";
		}
		
		pw.write(result);
		
		return null;
	}
	
	/**
	 * TO_修改模板页面
	 */
	@Action(value="marketingModelEdit",results={@Result(name="success",location="../../view/marketing/pushAPPMsgModelEdit.jsp")})
	public String marketingModelEdit()throws Exception{
		if(msgTemplateVo != null &&!StringUtils.isEmpty(msgTemplateVo.getId())){
			msgTemplateVo = iMarketingService.queryMsgTemplateVo(msgTemplateVo.getId(),3);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据模板ID获取模板内容
	 * 
	 */
	@Action(value="queryMarketingModel",results = { @Result( type = "json") })
	public String queryMarketingModel()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		if(msgTemplateVo != null &&!StringUtils.isEmpty(msgTemplateVo.getId())){
			msgTemplateVo = iMarketingService.queryMsgTemplateVo(msgTemplateVo.getId(),3);
			String json = com.amwell.commons.JSONUtil.formObjectToJSONStr(msgTemplateVo);
			pw.write(json);
		}
		return null;
	}
	/**
	 * 修改模板
	 * @return
	 * @throws Exception
	 */
	@Action(value="editMarketingModel",results = { @Result( type = "json") })
	public String editMarketingModel()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo msgUser=(SysAdminVo) httpSession.getAttribute("userInfo");//登录用帐号资料
		String userId =msgUser.getUserId();
		String result = "no";
		if(null != msgTemplateVo && msgTemplateVo.getId() != null){
			msgTemplateVo.setUpdateBy(userId);
			msgTemplateVo.setUpdateOn(MyDate.getMyDate());
			msgTemplateVo.setTemplate_status(1); //模板是否有效，1有效0无效
			msgTemplateVo.setTemplate_type(3);
			String count=iMarketingService.addSMSModer(msgTemplateVo);
			result = "yes";
		}
		
		pw.write(result);
		
		return null;
	}
	
	/**
	 * 删除模板
	 * @return
	 * @throws Exception
	 */
	@Action(value="marketingModelDel",results = { @Result( type = "json") })
	public String marketingModelDel()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo msgUser=(SysAdminVo) httpSession.getAttribute("userInfo");//登录用帐号资料
		String userId =msgUser.getUserId();
		String result = "no";
		if(null != msgTemplateVo && msgTemplateVo.getId() != null){
			
			String count=iMarketingService.deleteSMSModer(msgTemplateVo.getId(),3);
			result = "yes";
		}
		
		pw.write(result);
		
		return null;
	}
	
	/**
	 * 推送消息
	 * @throws Exception
	 */
	@Action(value="addPushApp",results={@Result(name="success",location="../../view/marketing/pushAPPMsgSend.jsp")})
	public String addPushApp()throws Exception{
		// 获取所有的模板列表
		map=iMarketingService.msgtemplateModelList(3);
		list=(List) map.get("list");
		return SUCCESS;
	}
	
	/**
	 * 发送推送消息
	 * @return
	 * @throws Exception
	 */
	@Action(value="sendPushApp",results={@Result(type="json")})
	public void sendPushApp()throws Exception{
		List<String> phoneUrlList = new ArrayList<String>();
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo msgUser=(SysAdminVo) httpSession.getAttribute("userInfo");//登录用帐号资料
		String userName=msgUser.getLoginName();	
		//search field02:短信消息内容 ; field03:消息标题;field04:发送对象 ;field05:定时发送;field06:定时发送时间;
		if(search==null){
			search = new Search();	
		}
		Map<String, Object> maps = new HashMap<String,Object>();
		MarketingVo marketingVo=new MarketingVo();
		String chooseTelephone=request.getParameter("phoneNumber");
		//手动选择发送乘客
		if("1".equals(search.getField04())){	
			//电话号码字符转为数组
			String[] telephones=chooseTelephone.split(" ");
			//电话号码校验
			for(String phone:telephones){
				phoneUrlList.add(phone);
			}
			marketingVo = setMsgModel(1,userName);
			String msgId=iMarketingService.addMessage(marketingVo, phoneUrlList, MyDate.getMyDateLong());
			// 判断是否立即发送
			if (search.getField06() == null	|| "".equals(search.getField06())) {
				iMarketingService.sendAPPMsg(msgId);				
			} 
		}
		//发送全部乘客
		if("3".equals(search.getField04())){
			marketingVo = setMsgModel(2,userName);
			//获取所有乘客信息列表（提取乘客手机号码）
			map=iMarketingService.getPassengerList(null,null,null,null);
			List<PassengerInfo> passengerList=(List)map.get("list");
			//获取乘客电话号码集合
			for(PassengerInfo passenger : passengerList){
				String phone=passenger.getTelephone();
				phoneUrlList.add(phone);
			}
			String msgId=iMarketingService.addMessage(marketingVo, phoneUrlList, MyDate.getMyDateLong());//保存发送信息返回短信ID
			// 判断是否立即发送
			if (search.getField06() == null	|| "".equals(search.getField06())) {
				iMarketingService.sendAPPMsg(msgId);				// 发送消息
			} 
		}
		//批量导入乘客信息发送电话
		if("2".equals(search.getField04())){
			marketingVo = setMsgModel(3,userName);
			// 电话号码字符转为数组
			String chooseTelephones = search.getField01();
			String[] telephones = chooseTelephones.split(",");
			for (String phone : telephones) {
				phoneUrlList.add(phone);
			}
			String msgId=iMarketingService.addMessage(marketingVo, phoneUrlList, MyDate.getMyDateLong());//保存发送信息返回短信ID
			// 判断是否立即发送
			if (search.getField06() == null	|| "".equals(search.getField06())) {
				iMarketingService.sendAPPMsg(msgId);				// 发送消息
			} 
		}
		maps.put("result", "success");
		JsonWriter.write(maps);
	}

	//组装信息MODEL
	private MarketingVo setMsgModel(int type,String userName){
		MarketingVo msgModel = new MarketingVo();
		// 消息发送记录
		msgModel.setMsgContext(search.getField02());
		msgModel.setMsgType(2);
		msgModel.setTheModule(1);
		msgModel.setMsgSubType(1);
		msgModel.setMsgTarget(0);
		msgModel.setMsgTiTLE("无");
		msgModel.setCreateBy(userName);
		msgModel.setCreateOn(MyDate.getMyDate());
		msgModel.setIssend("1");
		if(type==1){
			msgModel.setLineName(search.getField09());
			msgModel.setOrderDate(search.getField10());
			msgModel.setOrderStartTime(search.getField11());
		}
		if (search.getField06() != null && !"".equals(search.getField06())) {
			msgModel.setInitTime(search.getField06());// 定时设置
		}
		
		return msgModel;
		
	}
	
	public MsgTemplateVo getMsgTemplateVo() {
		return msgTemplateVo;
	}

	public void setMsgTemplateVo(MsgTemplateVo msgTemplateVo) {
		this.msgTemplateVo = msgTemplateVo;
	}

	public List<MarketingModel> getAPPList() {
		return APPList;
	}

	public void setAPPList(List<MarketingModel> aPPList) {
		APPList = aPPList;
	}

	public List<PassengerInfo> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<PassengerInfo> passengerList) {
		this.passengerList = passengerList;
	}

	public MarketingModel getMsgModel() {
		return msgModel;
	}

	public void setMsgModel(MarketingModel msgModel) {
		this.msgModel = msgModel;
	}
	
	
	
	
}
