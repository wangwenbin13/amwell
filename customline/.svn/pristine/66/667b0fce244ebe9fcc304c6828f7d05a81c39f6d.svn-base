package com.amwell.action.marketing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.Message;
import com.amwell.commons.Message.Provider;
import com.amwell.commons.MyDate;
import com.amwell.entity.MarketingModel;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.IAppSettingService;
import com.amwell.service.ILineService;
import com.amwell.service.IMarketingService;
import com.amwell.vo.AppSettingVo;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.MarketingVo;
import com.amwell.vo.MsgTemplateVo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.app.PassengerInfo;

/**
 * @author wangwenbin
 *
 * 2014-8-14
 */
/**
 * 营销管理_
 */
@ParentPackage("user-finit")
@Namespace("/marketing")
public class MarketingAction extends BaseAction{
	
	private static final long serialVersionUID = 530828002305203335L;

	/**
	 * 短信消息
	 */
	@Autowired
	private IMarketingService iMarketingService;
	
	@Autowired
	private ILineService lineService;
	
	@Autowired
	private IAppSettingService appSettingService;
	
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

	private List<MarketingModel> msgList;// 消息详情内容

	private List<PassengerInfo> passengerList;// 消息详情乘客信息
	
	private MarketingModel marketingBean;  //短信消息bean
	private String startDate;//查询乘客条件－－发车时间
	private String classTime;//查询乘客条件－－班次
	private String lineBase;//查询乘客条件－－线p


	/**
	 * 乘客列表信息
	 */
	private List<PassengerInfo> passengerInfos;
	
	/**
	 * 乘客分布列表信息
	 */
	private Page  passengerPage;
	
	private String telephone;
	
	private File excelFile;
	
	private String telephoneAdd;
	
	private MsgTemplateVo msgTemplateVo;
	
	private int msgBusiness;
	
	/**
	 * 剩余短信条数
	 */
	private Integer msgCount;
	
	
	/**
	 * 短信总管理管理
	 */
	@Action(value="marketingManager",results={@Result(name="success",location="../../view/marketing/marketingManager.jsp")})
	public String marketingManager()throws Exception{
		return SUCCESS;
	}
	

	
	/**
	 * 短信管理
	 */
	@Action(value="getMarketingList",results={@Result(name="success",location="../../view/marketing/marketingManagerList.jsp")})
	public String getMarketingList()throws Exception{
		//MarketingVo marketingVo =new MarketingVo();
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		if(search==null){
			search = new Search();
		}
		search.setField07("0");//消息类型 0:短信消息 1:软件站内消息 2：软件推送消息
		map=iMarketingService.getMarketingList(search, currentPageIndex, pageSize);
		list=(List<?>) map.get("list");
		page=(Page) map.get("page");
				
		return SUCCESS;
	}
	
	/**
	 * 短信模板页面
	 * @return
	 */
	@Action(value="marketingTemplate",results={@Result(name="success",location="../../view/marketing/marketingModel.jsp")})
	public String marketingTemplate(){
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		if(search==null){
			search = new Search();
		}
		map=iMarketingService.msgtemplateModelList(1);
		list=(List<?>) map.get("list");
		page=(Page) map.get("page");
		
		return SUCCESS;
	}
	
	
	/**
	 * 查询短信详情
	 * @return
	 * @throws Exception
	 */
	@Action(value="marketingDetail",results={@Result(name="success",location="../../view/marketing/marketingDetail.jsp")})
	public String pushAPPMsgDetail()throws Exception{
		String messageId=request.getParameter("messageId");
		marketingBean=iMarketingService.marketingDetail(messageId);
		return SUCCESS;
	}
	
	
	
	/**
	 * 添加短信页面跳转
	 * @return
	 * @throws Exception
	 */
	@Action(value="addMarketing",results={@Result(name="success",location="../../view/marketing/marketingAdd.jsp")})
	public String addMarketingMsg()throws Exception{
		Provider provider = Provider.from(request.getParameter("provider"));
		// 获取所有的模板列表
		map=iMarketingService.msgtemplateModelList(1);
		list=(List<?>) map.get("list");
		AppSettingVo appSettingVo = appSettingService.getAppSetting(); 
		msgBusiness = appSettingVo.getBusiness();
		String msgCount="";
		try {
			msgCount=String.valueOf(Message.balance(provider));
			//判断返回的值是否是为数字
			Pattern pattern = Pattern.compile("^([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?$");
			if(pattern.matcher(msgCount).matches()){
			}else{
				msgCount="信息获取超时！";
			}
		} catch (Exception e) {
			msgCount="信息获取超时！";
		}
		request.setAttribute("msgCount", msgCount);
		request.setAttribute("msgBusiness", msgBusiness);
		
		return SUCCESS;
	}

	/**
	 * 添加短信
	 */
	@Action(value="addMarketingMsg",results={@Result(type="json")})
	public void addMarketing() throws Exception {
		Provider provider = Provider.from(request.getParameter("provider"));
		int flag=0;
		List<String> mobiles = new ArrayList<String>();
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo msgUser = (SysAdminVo) httpSession.getAttribute("userInfo");// 登录用帐号资料
		String userName = msgUser.getLoginName();
		super.getResponse().setContentType("text/html;charset=UTF-8");
		// search field02:短信消息内容 ; field03:消息标题;field04:发送对象;field05:定时发送;field06:定时发送时间;
		if (search == null) {
			search = new Search();
		}
		Map<String, Object> maps = new HashMap<String,Object>();
		MarketingVo marketingVo = new MarketingVo();
		String chooseTelephone = request.getParameter("phoneNumber");
//手动选择发送乘客
			if ("1".equals(search.getField04())) {
				// 电话号码字符转为数组
				mobiles = telephoneArray(chooseTelephone.split(" "));
				//设置短信数据bean
				marketingVo = setMarketModel(1, userName);
				//save短信信息内容返回  msgId
				String msgId = iMarketingService.addMessage(marketingVo,mobiles,MyDate.getMyDateLong());
				if (msgId.isEmpty()) {
					maps.put("result", "erren");
					JsonWriter.write(maps);
					return;
				}
				// 判断是否立即发送.field05是定时设置，值为1.定时发送只保存数据（定时发送时间），不操作发送。
				flag = atOnceSendMessage(provider, marketingVo.getMsgContext(), mobiles.toArray(new String[mobiles.size()]));
			}
		
// 发送全部乘客
			if ("3".equals(search.getField04())) {
				//设置短信数据bean	
				marketingVo = setMarketModel(2, userName);
				// 获取所有乘客信息列表（提取乘客手机号码）
				map = iMarketingService.getPassengerList(null, null, null, null);
				@SuppressWarnings("unchecked")
				List<PassengerInfo> passengerList = (List<PassengerInfo>) map.get("list");
				// 获取乘客电话号码集合
				for (PassengerInfo passenger : passengerList) {
					String phone = passenger.getTelephone();
					mobiles.add(phone);
				}
				//save短信信息内容返回  msgId
				String msgId = iMarketingService.addMessage(marketingVo,mobiles, MyDate.getMyDateLong());
				if (msgId.isEmpty()) {
					maps.put("result", "erren");
					JsonWriter.write(maps);
					return;
				}
				flag = atOnceSendMessage(provider, marketingVo.getMsgContext(), mobiles.toArray(new String[mobiles.size()]));
			}
		
// 批量导入乘客信息发送电话
			if ("2".equals(search.getField04())) {
				// 判断电话号码是否为零
				if (search.getField01() == null || "".equals(search.getField01())) {
					maps.put("result", "error");
					JsonWriter.write(maps);
					return;
				}
				//设置短信数据bean
				marketingVo = setMarketModel(3, userName);
				// 电话号码字符转为数组
				mobiles = telephoneArray(search.getField01().split(","));
				//save短信信息内容返回  msgId
				String msgId = iMarketingService.addMessage(marketingVo,mobiles, MyDate.getMyDateLong()	);
				if (msgId.isEmpty()) {
					maps.put("result", "erren");
					JsonWriter.write(maps);
					return;
				}
				// 判断是否立即发送.field05是定时设置，值为1.定时发送只保存数据（定时发送时间），不操作发送。
				flag = atOnceSendMessage(provider, marketingVo.getMsgContext(), mobiles.toArray(new String[mobiles.size()]));
			}
		if(flag>0){
			maps.put("result", "success");
		}else {
			maps.put("result", "error");
		}
		JsonWriter.write(maps);
		return;
	}
	
	// 判断是否立即发送
	private int atOnceSendMessage(Provider provider, String content, String... mobiles){
		if (search.getField06() == null || "".equals(search.getField06())) {
			new Message(content).sendTo(provider, mobiles);
		}
		return 1;  // XXX Always success
	}
	// 电话号码字符转为数组
	private List<String> telephoneArray(String[] telephones){
		List<String> phoneUrlList=new ArrayList<String>();
		for(String telephon : telephones){
			phoneUrlList.add(telephon);
		}
		return phoneUrlList; 
	}
	
	/**
	 * 条件查询乘客列表信息
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@Action(value="passengerList",results = { @Result( type = "json") })
	public void passengerList () throws IOException, JSONException{
		super.getResponse().setContentType("text/html;charset=UTF-8");
		String phone=request.getParameter("telephone");
		classTime=request.getParameter("classTime");
		lineBase=request.getParameter("lineBase");
		startDate=request.getParameter("startDate");
		lineBase =URLDecoder.decode(lineBase,"utf-8");
		map=iMarketingService.getPassengerList(phone, classTime, lineBase, startDate);
		@SuppressWarnings("unchecked")
		List<PassengerInfo> list = (List<PassengerInfo>) map.get("list");
		JsonWriter.write(list);
		return;
	}
	
	/**
	 * 导出Excel模板
	 * @throws IOException
	 */
	@Action(value="downloadTemplate")
	public void downloadTemplate() throws IOException{
		String rootPath = super.request.getRealPath("/");
		String fileName="DownloadTelephoneTemple.xlsx";
		String path =rootPath.replace("\\", "/")+"template"+"/"+fileName;
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
        response.addHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"ISO-8859-1")); 
        response.addHeader("Content-Length", "" + file.length());  
        OutputStream out = new BufferedOutputStream(response.getOutputStream());    
        response.setContentType("application/octet-stream");  
        out.write(buffer);  
        out.flush();  
        out.close(); 
	}
	
	/**
	 * 读取Excel表格里的手机号码
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "upload", results={@Result(type="json")})
	public void upload() throws IOException, JSONException{
		getResponse().setContentType("text/html;charset=UTF-8");
		TreeMap<Integer,String> map = new TreeMap<Integer,String>();
		FileInputStream fis  = null;
		try {
			fis = new FileInputStream(excelFile);
			int size = fis.available();
			if(size>=500*1024){
				getResponse().getWriter().print("overSize");
			}else{
				Workbook wb = WorkbookFactory.create(new FileInputStream(excelFile));
				// 默认读取第一个sheet
				Sheet sheet = wb.getSheetAt(0);
				// 得到总行数
				int rowNum = sheet.getLastRowNum();
				Row row = sheet.getRow(0);
				Cell cell = null;
				String cellValue = null;
				DecimalFormat df = new DecimalFormat("0");// 格式化数字
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
				// 正文内容应该从第二行开始,第一行为表头的标题
				for (int i = 1; i <= rowNum; i++) {
					row = sheet.getRow(i);
					if(null==row){
						continue;
					}
					//本例只读取第一列数据
					cell = row.getCell(0);
					if(null!=cell){
						switch (cell.getCellType()){
						  case Cell.CELL_TYPE_STRING:
							   cellValue = cell.getRichStringCellValue().getString();
							   break;
						  case Cell.CELL_TYPE_NUMERIC:
							  //此处只处理一种数字数据格式
							  if ("@".equals(cell.getCellStyle().getDataFormatString())) {
								  cellValue = df.format(cell.getNumericCellValue());
							  } 
							  else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
								  cellValue = df.format(cell.getNumericCellValue());
							  } else {
								  cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
							  }
							  break;
						}
					}
				   map.put(i, cellValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				fis.close();
			}
		}

		Map<String,String> maps = new HashMap<String,String>();
		maps= checkMap(map);
		//将数据存放于session
		if(Integer.valueOf(maps.get("count"))>0){
			//短信发送完成之后，须将会话中的数据清除 
			maps.put("result", "success");
			JsonWriter.write(maps);
		}else{
			maps.put("result", "error");
			JsonWriter.write(maps);
		}
		return;
	}
	
	/**
	 * 号码重得，错误验证
	 * 第N行存在必填项为空,第N行存在格式错误,第N行和第N+1行号码重复,如果验证失败则抛出受查异常
	 * @param map
	 */
	private Map<String, String> checkMap(Map<Integer, String> map) {
		//验证逻辑
		Map<String, String> phoneMap=new HashMap<String, String>();
		int count=0;
		String rightNO = "";//正确号码集合
		StringBuffer sameNO=new StringBuffer();//相同号码集合
		StringBuffer wrongNO=new StringBuffer();//错误号码集合
		List<String> allPhones = new ArrayList<String>();  //所有号码的集合
		
		if(false==CollectionUtils.isEmpty(map)){
			 Collection<String> coll = map.values();
			 if(false==CollectionUtils.isEmpty(coll)){
				 String s = null;
				 Iterator<String> it = coll.iterator();
				 while(it.hasNext()){
					 s = it.next().replaceAll("[^0-9]", "");
					 //此处只判空，不处理重复逻辑
					if (StringUtils.hasText(s)) {
						// 此处判断手机号码格式验证
						Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
						Matcher m = p.matcher(s);
						if (!m.matches()) {
							wrongNO.append(s+",");
						}else{
							//判断是否有重复的号码
							if (allPhones.contains(s)) {
								sameNO.append(s+",");
							} else {
								allPhones.add(s);
								rightNO+=s;
								count++;
								if (it.hasNext()) {
									rightNO+=",";
								}
							}
						}
					}
				 }
			 }
		}
		
//		String[] phoneList=rightNO.split(",");
		phoneMap.put("samePhones", sameNO.toString()); // 重复的电话号码序号列表
		phoneMap.put("importTelephones", rightNO.toString());
		phoneMap.put("wrongTelephone", wrongNO.toString()); //错误的电话号码序号列表	
		phoneMap.put("count", String.valueOf(count));
		return phoneMap;
	}
	
	/**
	 * TO_短信模板添加页面
	 * @return
	 */
	@Action(value="marketingModelAdd",results={@Result(name="success",location="../../view/marketing/marketingModelAdd.jsp")})
	public String marketingModelAdd(){
		
		
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
			msgTemplateVo.setTemplate_type(1);
			iMarketingService.addSMSModer(msgTemplateVo);
			result = "yes";
		}
		
		pw.write(result);
		
		return null;
	}
	
	/**
	 * TO_修改模板页面
	 */
	@Action(value="marketingModelEdit",results={@Result(name="success",location="../../view/marketing/marketingModelEdit.jsp")})
	public String marketingModelEdit()throws Exception{
		if(msgTemplateVo != null &&!org.apache.commons.lang3.StringUtils.isEmpty(msgTemplateVo.getId())){
			msgTemplateVo = iMarketingService.queryMsgTemplateVo(msgTemplateVo.getId(),1);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据模板ID获取模板内容
	 * 
	 */
	@Action(value="queryMarketingModel",results = { @Result( type = "json") })
	public void queryMarketingModel()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		if(msgTemplateVo != null &&!org.apache.commons.lang3.StringUtils.isEmpty(msgTemplateVo.getId())){
			msgTemplateVo = iMarketingService.queryMsgTemplateVo(msgTemplateVo.getId(),1);
			String json = com.amwell.commons.JSONUtil.formObjectToJSONStr(msgTemplateVo);
			pw.write(json);
		}
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
			msgTemplateVo.setTemplate_type(1);
			iMarketingService.addSMSModer(msgTemplateVo);
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
		String result = "no";
		if(null != msgTemplateVo && msgTemplateVo.getId() != null){
			
			iMarketingService.deleteSMSModer(msgTemplateVo.getId(),1);
			result = "yes";
		}
		
		pw.write(result);
		
		return null;
	}
	
	
	/**
	 * 查找班次列表
	 * @return
	 */
	@Action(value="getClassList",results = { @Result( type = "json") })
	public void getClassList()throws Exception{
		map=iMarketingService.getClassList();
		String lineName =super.request.getParameter("lineName");
		String orderDate = super.request.getParameter("orderDate");
		List<LineClassEntity> classlist=lineService.getLineClassList(lineName, orderDate);
		JsonWriter.write(classlist);
		return;
	}
	
	//组装信息MODEL
	private MarketingVo setMarketModel(int sendType,String userName){
		MarketingVo marketingVo = new MarketingVo();
		marketingVo.setMsgContext(search.getField02());
		marketingVo.setTheModule(1);
		marketingVo.setMsgType(0);
		marketingVo.setMsgSubType(1);
		marketingVo.setMsgTarget(0);
		marketingVo.setMsgTiTLE("");
		marketingVo.setCreateBy(userName);
		marketingVo.setCreateOn(MyDate.getMyDate());
		if(sendType==1){
			marketingVo.setOrderDate(search.getField10());
			marketingVo.setOrderStartTime(search.getField11());
			marketingVo.setLineName(search.getField09());
		}
		if (search.getField06() != null && !"".equals(search.getField06())) {
			marketingVo.setInitTime(search.getField06());// 定时设置
		}
		marketingVo.setIssend("1");
		return marketingVo;
	}
	
	/**
	 * 更换短信服务商
	 */
	@Action(value = "checkMsgBusiness", results = { @Result(type = "json") })
	public void checkMsgBusiness() throws Exception {
		String provider = request.getParameter("provider");
		String count = String.valueOf(Message.balance(Provider.from(provider)));
		JsonWriter.write(count);
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
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public File getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
	public List<String> getRows() {
		return rows;
	}
	public void setRows(List<String> rows) {
		this.rows = rows;
	}
	public List<String> getSameTelephone() {
		return sameTelephone;
	}
	public void setSameTelephone(List<String> sameTelephone) {
		this.sameTelephone = sameTelephone;
	}
	public String getTelephoneAdd() {
		return telephoneAdd;
	}
	public void setTelephoneAdd(String telephoneAdd) {
		this.telephoneAdd = telephoneAdd;
	}
	public MsgTemplateVo getMsgTemplateVo() {
		return msgTemplateVo;
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
	public Integer getMsgCount() {
		return msgCount;
	}
	public void setMsgCount(Integer msgCount) {
		this.msgCount = msgCount;
	}
	public void setMsgTemplateVo(MsgTemplateVo msgTemplateVo) {
		this.msgTemplateVo = msgTemplateVo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getClassTime() {
		return classTime;
	}
	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}
	public String getLineBase() {
		return lineBase;
	}
	public void setLineBase(String lineBase) {
		this.lineBase = lineBase;
	}
	public MarketingModel getMarketingBean() {
		return marketingBean;
	}
	public void setMarketingBean(MarketingModel marketingBean) {
		this.marketingBean = marketingBean;
	}
	public int getMsgBusiness() {
		return msgBusiness;
	}
	public void setMsgBusiness(int msgBusiness) {
		this.msgBusiness = msgBusiness;
	}
	
}
