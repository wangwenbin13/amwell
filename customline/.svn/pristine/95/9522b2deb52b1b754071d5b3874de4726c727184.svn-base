package com.amwell.action.marketing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.amwell.service.IMarketingService;
import com.amwell.vo.MarketingVo;
import com.amwell.vo.MsgTemplateVo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.app.PassengerInfo;

/**
 * APP站内消息管理
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
@ParentPackage("user-finit")
@Namespace("/insideMessage")
public class InsideMessageAction extends BaseAction {
	/**
	 * 短信消息
	 */
	@Autowired
	private IMarketingService iMarketingService;

	// @Autowired
	// private ILineService lineService;
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
	private List<String> rows = new ArrayList<String>();

	/**
	 * 验证重复的手机号码
	 */
	private List<String> sameTelephone = new ArrayList<String>();
	/**
	 * 乘客列表信息
	 */
	private List<PassengerInfo> passengerInfos;

	private List<MarketingModel> msgList;// 消息详情内容

	private List<PassengerInfo> passengerList;// 消息详情乘客信息

	private MarketingModel msgModel;

	/**
	 * 乘客分布列表信息
	 */

	private MsgTemplateVo msgTemplateVo;

	/**
	 * APP站内信管理
	 * 
	 * @throws Exception
	 */
	@Action(value = "insideAPPManager", results = { @Result(name = "success", location = "../../view/marketing/insideAPPManager.jsp") })
	public String insideAPPManager() throws Exception {
		// System.out.println("marketingManager...");
		return SUCCESS;
	}

	/**
	 * 站内信列表
	 * 
	 * @throws Exception
	 */
	@Action(value = "getAPPInsideList", results = { @Result(name = "success", location = "../../view/marketing/insideAPPList.jsp") })
	public String getAPPInsideList() throws Exception {
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
				: Integer.parseInt(request.getParameter("currentPageIndex"));
		if (search == null) {
			search = new Search();
		}
		search.setField07("1");// 消息类型 0:短信消息 1:软件站内消息 2：软件推送消息
		map = iMarketingService.getMarketingList(search, currentPageIndex,
				pageSize);
		list = (List) map.get("list");
		page = (Page) map.get("page");
		return SUCCESS;
	}

	/**
	 * 站内消息 详情
	 * 
	 * @throws Exception
	 */
	@Action(value = "insideAPPDetail", results = { @Result(name = "success", location = "../../view/marketing/insideAPPDetail.jsp") })
	public String insideAPPDetail() throws Exception {
		String messageId = request.getParameter("messageId");
		msgModel = iMarketingService.marketingDetail(messageId);
		// msgList=(List)marketingMap.get("list");
		// passengerList=(List)marketingMap.get("passengerList");
		return SUCCESS;
	}

	/**
	 * 站内信模板
	 * 
	 * @throws Exception
	 */
	@Action(value = "insideAPPTemplate", results = { @Result(name = "success", location = "../../view/marketing/insideAPPModel.jsp") })
	public String insideAPPTemplate() throws Exception {
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
				: Integer.parseInt(request.getParameter("currentPageIndex"));
		if (search == null) {
			search = new Search();

		}

		map = iMarketingService.msgtemplateModelList(2);
		list = (List) map.get("list");
		page = (Page) map.get("page");
		return SUCCESS;
	}

	/**
	 * TO_增加站内信模板
	 * 
	 * @throws Exception
	 */
	@Action(value = "insideAPPModelAdd", results = { @Result(name = "success", location = "../../view/marketing/insideAPPModelAdd.jsp") })
	public String insideAPPModelAdd() throws Exception {

		return SUCCESS;
	}

	/**
	 * 增加站内信模板
	 * 
	 * @return
	 * @throws Exception
	 */

	@Action(value = "addTemplateModel", results = { @Result(type = "json") })
	public String addTemplateModel() throws Exception {
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();

		HttpSession httpSession = ServletActionContext.getRequest()
				.getSession();
		SysAdminVo msgUser = (SysAdminVo) httpSession.getAttribute("userInfo");// 登录用帐号资料
		String userId = msgUser.getUserId();
		String result = "no";
		if (null != msgTemplateVo) {
			String time = MyDate.getMyDate();
			msgTemplateVo.setCreateOn(time);
			msgTemplateVo.setCreateBy(userId);
			msgTemplateVo.setUpdateBy(userId);
			msgTemplateVo.setUpdateOn(time);
			msgTemplateVo.setTemplate_status(1); // 模板是否有效，1有效0无效
			msgTemplateVo.setTemplate_type(2);
			iMarketingService.addSMSModer(msgTemplateVo);
			result = "yes";
		}

		pw.write(result);

		return null;
	}

	/**
	 * TO_修改模板页面
	 */
	@Action(value = "marketingModelEdit", results = { @Result(name = "success", location = "../../view/marketing/insideAPPModelEdit.jsp") })
	public String marketingModelEdit() throws Exception {
		if (msgTemplateVo != null && !StringUtils.isEmpty(msgTemplateVo.getId())) {
			msgTemplateVo = iMarketingService.queryMsgTemplateVo(msgTemplateVo.getId(), 2);
		}
		return SUCCESS;
	}

	/**
	 * 修改模板
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "editMarketingModel", results = { @Result(type = "json") })
	public String editMarketingModel() throws Exception {
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();

		HttpSession httpSession = ServletActionContext.getRequest()
				.getSession();
		SysAdminVo msgUser = (SysAdminVo) httpSession.getAttribute("userInfo");// 登录用帐号资料
		String userId = msgUser.getUserId();
		String result = "no";
		if (null != msgTemplateVo && msgTemplateVo.getId() != null) {
			msgTemplateVo.setUpdateBy(userId);
			msgTemplateVo.setUpdateOn(MyDate.getMyDate());
			msgTemplateVo.setTemplate_status(1); // 模板是否有效，1有效0无效
			msgTemplateVo.setTemplate_type(2);
			iMarketingService.addSMSModer(msgTemplateVo);
			result = "yes";
		}

		pw.write(result);

		return null;
	}

	/**
	 * 删除模板
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "marketingModelDel", results = { @Result(type = "json") })
	public String marketingModelDel() throws Exception {
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();

		// HttpSession httpSession =
		// ServletActionContext.getRequest().getSession();
		// SysAdminVo msgUser=(SysAdminVo)
		// httpSession.getAttribute("userInfo");//登录用帐号资料
		// String userId =msgUser.getUserId();
		String result = "no";
		if (null != msgTemplateVo && msgTemplateVo.getId() != null) {

			iMarketingService.deleteSMSModer(msgTemplateVo.getId(), 2);
			result = "yes";
		}

		pw.write(result);

		return null;
	}

	/**
	 * 发送站内消息页面跳转
	 * 
	 * @throws Exception
	 */
	@Action(value = "addInsideApp", results = { @Result(name = "success", location = "../../view/marketing/insideAPPSend.jsp") })
	public String addAppJump() throws Exception {
		// 获取所有的模板列表
		map = iMarketingService.msgtemplateModelList(2);
		list = (List) map.get("list");
		return SUCCESS;
	}

	/**
	 * 根据模板ID获取模板内容
	 * 
	 */
	@Action(value = "queryMarketingModel", results = { @Result(type = "json") })
	public String queryMarketingModel() throws Exception {
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();

		if (msgTemplateVo != null &&!StringUtils.isEmpty(msgTemplateVo.getId())) {
			msgTemplateVo = iMarketingService.queryMsgTemplateVo(msgTemplateVo.getId(), 2);
			String json = com.amwell.commons.JSONUtil.formObjectToJSONStr(msgTemplateVo);
			pw.write(json);
		}

		return null;
	}

	/**
	 * 发送站内消息
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addInsideMsg", results = { @Result(type = "json") })
	public void addInsideApp() throws Exception {
		List<String> phoneUrlList = new ArrayList<String>();
		HttpSession httpSession = ServletActionContext.getRequest()
				.getSession();
		SysAdminVo msgUser = (SysAdminVo) httpSession.getAttribute("userInfo");// 登录用帐号资料
		String userName = msgUser.getLoginName();
		super.getResponse().setContentType("text/html;charset=UTF-8");
		// search field02:短信消息内容 ;
		// field03:消息标题;field04:发送对象;field05:定时发送;field06:定时发送时间;08：图片，09：线路，10：乘车日期，11：班次
		if (search == null) {
			search = new Search();
		}
		Map<String, Object> maps = new HashMap<String,Object>();

		MarketingVo marketingVo = new MarketingVo();
		String chooseTelephone = request.getParameter("phoneNumber");

		// 手动选择发送乘客
		if ("1".equals(search.getField04())) {
			// 电话号码字符转为数组
			String[] telephones = chooseTelephone.split(" ");

			marketingVo = setMessageModel(1, userName);

			for (String phone : telephones) {
				phoneUrlList.add(phone);
			}
			String msgId = iMarketingService.addMessage(marketingVo,
					phoneUrlList, MyDate.getMyDateLong());
			if (msgId.isEmpty()) {
				maps.put("result", "error");
				JsonWriter.write(maps);
				return;
			}
			// 判断是否立即发送.field06是定时设置，值.定时发送只保存数据（定时发送时间），不操作发送。
			if (search.getField06() == null || "".equals(search.getField06())) {

				int count = iMarketingService.sendAPPMsg(msgId); // 发送消息
			}

		}

		// 发送全部乘客
		if ("3".equals(search.getField04())) {

			marketingVo = setMessageModel(2, userName);

			// 获取所有乘客信息列表（提取乘客手机号码）
			map = iMarketingService.getPassengerList(null, null, null, null);
			List<PassengerInfo> passengerList = (List) map.get("list");
			// 获取乘客电话号码集合
			for (PassengerInfo passenger : passengerList) {
				String phone = passenger.getTelephone();
				phoneUrlList.add(phone);
			}
			String msgId = iMarketingService.addMessage(marketingVo,phoneUrlList, MyDate.getMyDateLong());
			if (msgId.isEmpty()) {
				maps.put("result", "error");
				JsonWriter.write(maps);
				return;
			}
			if (search.getField06() == null || "".equals(search.getField06())) {
				iMarketingService.sendAPPMsg(msgId); // 发送消息
			}
		}
		// 批量导入乘客信息发送电话
		if ("2".equals(search.getField04())) {
			marketingVo = setMessageModel(3, userName);
			// 电话号码字符转为数组
			String chooseTelephones = search.getField01();
			String[] telephones = chooseTelephones.split(",");
			for (String phone : telephones) {
				phoneUrlList.add(phone);
			}
			String msgId = iMarketingService.addMessage(marketingVo,
					phoneUrlList, MyDate.getMyDateLong());
			// 判断是否立即发送.field05是定时设置，值为1.定时发送只保存数据（定时发送时间），不操作发送。
			if (search.getField06() == null || "".equals(search.getField06())) {

				iMarketingService.sendAPPMsg(msgId); // 发送消息
			}
		}
		maps.put("result", "success");
		JsonWriter.write(maps);
		return;
	}

	// 组装信息MODEL
	private MarketingVo setMessageModel(int type, String userName) {
		MarketingVo msgModel = new MarketingVo();
		msgModel.setMsgContext(search.getField02());
		msgModel.setTheModule(1);
		msgModel.setMsgType(1);
		msgModel.setMsgSubType(1);
		msgModel.setMsgTarget(0);
		msgModel.setMsgTiTLE(search.getField03());
		msgModel.setMsgTarget(0);
		msgModel.setCreateBy(userName);
		msgModel.setCreateOn(MyDate.getMyDate());
		if (type == 1) {
			msgModel.setOrderDate(search.getField10());
			msgModel.setOrderStartTime(search.getField11());
			msgModel.setLineName(search.getField09());
		}
		msgModel.setPicUrl(search.getField08());
		// String time = MyDate.getMyDateLong();
		if (search.getField06() != null && !"".equals(search.getField06())) {
			msgModel.setInitTime(search.getField06());// 定时设置
		}
		msgModel.setIssend("1");
		return msgModel;
	}

	/**
	 * 导入Excel模板
	 * 
	 * @throws IOException
	 */
	@Action(value = "downloadTemplate")
	public void downloadTemplate() throws IOException {
		String rootPath = super.request.getRealPath("/");
		String fileName = "DownloadTelephoneTemple.xlsx";
		String path = rootPath.replace("\\", "/") + "template" + "/" + fileName;
		File file = new File(path);
		// 以流的形式下载文件。
		InputStream fis = new BufferedInputStream(new FileInputStream(path));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		HttpServletResponse response = super.getResponse();
		// 清空response
		response.reset();
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
		response.addHeader("Content-Length", "" + file.length());
		OutputStream out = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		out.write(buffer);
		out.flush();
		out.close();
	}

	/**
	 * 站内消息图片预览
	 * 
	 * @throws Exception
	 */
	@Action(value = "getViewPic", results = { @Result(name = "success", location = "../../view/marketing/pop-viewPic.jsp") })
	public String getViewPic() throws Exception {
		// System.out.println("marketingManager...");
		return SUCCESS;
	}

	public MsgTemplateVo getMsgTemplateVo() {
		return msgTemplateVo;
	}

	public void setMsgTemplateVo(MsgTemplateVo msgTemplateVo) {
		this.msgTemplateVo = msgTemplateVo;
	}

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

	public List<MarketingModel> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<MarketingModel> msgList) {
		this.msgList = msgList;
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
