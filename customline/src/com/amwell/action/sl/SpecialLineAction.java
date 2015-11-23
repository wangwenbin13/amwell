package com.amwell.action.sl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

import org.apache.log4j.Logger;
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
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.MyDate;
import com.amwell.commons.QueryHelper;
import com.amwell.entity.Search;
import com.amwell.service.ILineCustomService;
import com.amwell.service.ILineService;
import com.amwell.service.IPublishLineService;
import com.amwell.service.ISpecialLineService;
import com.amwell.service.ISysAreaService;
import com.amwell.vo.Company;
import com.amwell.vo.LineEntity;
import com.amwell.vo.PageBean;
import com.amwell.vo.StationInfo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.opensymphony.xwork2.ActionContext;

/**
 * 客户专线Action
 * 
 * @author huxiaojun
 */
@ParentPackage("user-finit")
@Namespace("/specialLineLine")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class SpecialLineAction extends BaseAction {
	private static final long serialVersionUID = -3529473553337358112L;
	private static final Logger log = Logger.getLogger(SpecialLineAction.class);
	
	@Autowired(required=false)
	private ISpecialLineService specialLineService;
	
	@Autowired(required=false)
	private ILineCustomService lineCustomService;
	
	@Autowired
	private ILineService lineService;
	
	@Autowired
	private IPublishLineService publishLineService;
	
	//存放查询参数条件
	private Search search = new Search();
	/**
	 * 每页显示的记录数
	 */
	private int  pageSize = 10;
	/**
	 * 记录数的下标起始数 limit 0,10中的0
	 */
	private String currentPage = "1";
	
	private Company company;
	
	private File excelFile;
	
	/**
	 * 验证未通过的电话号码
	 */
	private List<String> rows = new ArrayList<String>();
	
	/**
	 * 验证重复的手机号码
	 */
	private List<String> sameTelephone = new ArrayList<String>();
	
	/**
	 * 验证是否已存在的手机号码
	 */
	private List<String> existTelePhone = new ArrayList<String>();
	
	/**
	 * 存放已经在数据库存在的号码
	 */
	private List<String> databaseLists = new ArrayList<String>();
	
	/**
	* 区域
	*/
	@Autowired
	private ISysAreaService iSysAreaService;
	
	/**
	 * 区域
	 */
	private SysArea sysArea;
	
	/**
	 * 区域省份集合
	 */
	private List<SysArea> proSysAreas;
	
	/**
	 * 线路集合
	 */
	private List<LineEntity> lines;
	
	/**
	 * 获取公司线路
	 */
	@Action(value = "getCompanyLineList", results = { @Result(name = "success", location = "../../view/merchant/companyLineList.jsp") })
	public String getCompanyLineList() {
		if(search==null){
			search = new Search();
		}
		QueryHelper query = new QueryHelper(" FROM company c")
			.addSelectClause("SELECT c.companyId,c.companyName,(SELECT s.areaName FROM sys_area s WHERE c.companyProvince=s.arearCode) AS companyProvince,(SELECT s.areaName FROM sys_area s WHERE c.companyCity=s.arearCode) AS companyCity,(SELECT COUNT(*) FROM company_line cl WHERE c.companyId = cl.companyId ) AS lineCount,(SELECT COUNT(*) FROM company_passenger cp WHERE cp.companyId=c.companyId) AS passengerCount")
			.addCondition(search != null && search.getField01() != null && !"".equals(search.getField01()),"c.companyName like ? ","%"+search.getField01()+"%")
			.addOrderProperty("c.createOn", false)
			.addLimitClause(Integer.parseInt(currentPage), pageSize);
		
		PageBean pageBean = specialLineService.getPageBean(Integer.parseInt(currentPage),pageSize,query);
		ActionContext.getContext().getValueStack().push(pageBean);
		
		return SUCCESS;
	}
	
	/**
	 * 新增公司的总页面
	 */
	@Action(value = "addCompany", results = { @Result(name = "success", location = "../../view/merchant/addCompany.jsp") })
	public String addCompany() {
		/**
		 * 省份
		 */
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		
		return SUCCESS;
	}
	
	/**
	 * 删除公司
	 */
	@Action(value="deleteCompany",results = { @Result( type = "json") })
	public String deleteCompany()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		String companyIds = request.getParameter("companyIds");
		
		if(null != companyIds && !"".equals(companyIds)){
			String[] companys = companyIds.split(";");
			List<String> cIds = new ArrayList<String>();
			for(int i=0;i<companys.length;i++){
				cIds.add(companys[i]);
			}
			boolean flag = specialLineService.deleteCompany(cIds);
			if(flag){
				pw.print("yes");
			}else{
				pw.print("no");
			}
		}else{
			pw.print("error");
		}
		
		return null;
	}
	
	
	/**
	 * 新增公司及线路及员工
	 */
	@Action(value="addCompanyByLinesByEmploy",results = { @Result( type = "json") })
	public String addCompanyByLinesByEmploy()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		//
		if(null != search 
				&& !"".equals(search.getField04()) 
				&& !"请选择".equals(search.getField05()) 
				&& !"".equals(search.getField05()) 
				&& !"请选择".equals(search.getField06())
				&& !"".equals(search.getField06())
				){
			
			//获取当前用户
			HttpSession httpSession = ServletActionContext.getRequest().getSession();
			SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
			
			Company c = new Company();
			if(!"".equals(search.getField02()) ){
				//员工号码
				String[] telPhones = search.getField02().split(",");
				List<String> tels = new ArrayList<String>();
				for(int i=0;i<telPhones.length;i++){
					tels.add(telPhones[i]);
				}
				c.setPassengerTelList(tels);
			}
			
			// 线路ID
			if(!"".equals(search.getField03())){
				String[] lineIds = search.getField03().split(",");
				List<String> lines = new ArrayList<String>();
				for(int i=0;i<lineIds.length;i++){
					lines.add(lineIds[i]);
				}
				c.setLineIdList(lines);
			}
			
			
			//公司名称
			c.setCompanyName(search.getField04());
			//省份
			c.setCompanyProvince(search.getField05());
			//城市
			c.setCompanyCity(search.getField06());
			//添加当前人
			c.setCreateBy(admin.getUserId());
			//添加时间
			c.setCreateOn(MyDate.getMyDateLong());
			
			//添加入库
			int count = specialLineService.addCompanyByLinesByEmploy(c);
			
			if(count >0 ){
				pw.print("success");
			}else{
				pw.print("error");
			}
			
		}else{
			pw.print("Illegal");
		}
		return null;
	}
	
	/**
	 * 修改公司
	 */
	@Action(value="updateCompany",results = { @Result( type = "json") })
	public String updateCompany()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		//
		if(null != search  
				&& !"".equals(search.getField04()) 
				&& !"请选择".equals(search.getField05()) 
				&& !"".equals(search.getField05()) 
				&& !"请选择".equals(search.getField06())
				&& !"".equals(search.getField06())
				&& !"".equals(search.getField01())
				){
			
			//获取当前用户
			HttpSession httpSession = ServletActionContext.getRequest().getSession();
			SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
			
			Company c = new Company();
			//员工ID
			c.setCompanyId(search.getField01());
			
			//员工号码
			if(!"".equals(search.getField02())){
				String[] telPhones = search.getField02().split(",");
				List<String> tels = new ArrayList<String>();
				for(int i=0;i<telPhones.length;i++){
					tels.add(telPhones[i]);
				}
				c.setPassengerTelList(tels);
			}
			
			
			// 线路ID
			if(!"".equals(search.getField03())){
				String[] lineIds = search.getField03().split(",");
				List<String> lines = new ArrayList<String>();
				for(int i=0;i<lineIds.length;i++){
					lines.add(lineIds[i]);
				}
				c.setLineIdList(lines);
			}
			
			
			//公司名称
			c.setCompanyName(search.getField04());
			//省份
			c.setCompanyProvince(search.getField05());
			//城市
			c.setCompanyCity(search.getField06());
			//修改人
			c.setUpdateBy(admin.getUserId());
			//修改时间
			c.setUpdateOn(MyDate.getMyDateLong());
			
			//修改公司
			int count = specialLineService.updateCompany(c);
			System.out.println("count:"+count);
			if(count >0 ){
				pw.print("success");
			}else{
				pw.print("error");
			}
			
			
			
		}else{
			pw.print("Illegal");
		}
		
		return null;
	}
	
	
	/**
	 * 新增专线
	 */
	@Action(value = "addSpecialLine", results = { @Result(name = "success", location = "../../view/merchant/pop_addSpecialLine.jsp") })
	public String addSpecialLine() {
		
		/**
		 * 省份
		 */
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		return SUCCESS;
	}
	
	/**
	 * 新增专线-详情
	 */
	@Action(value = "specialLineDetail", results = { @Result(name = "success", location = "../../view/merchant/specialLineDetail.jsp") })
	public String specialLineDetail() {
		
		//获取公司编号
		String companyId = request.getParameter("companyId");
		if(null != companyId && !"".equals(companyId)){
			Company  c = new Company();
			c.setCompanyId(companyId);
			company = specialLineService.queryCompany(c,1);
			
			if(null != company ){
				//获取公司对应的线路
				if(null != company.getLineIdList() && company.getLineIdList().size()>0){
					//得到公司对应的线路
					lines = lineCustomService.queryCompanyLines(company.getLineIdList());
					StringBuffer buf = new StringBuffer();
					for(int i=0;i<lines.size();i++){
						LineEntity entity = lines.get(i);
						List<StationInfo> stationInfoList = publishLineService.getStationListById(entity.getLineBaseId());
						String startStationName = stationInfoList.get(0).getName();
						String endStationName = stationInfoList.get(stationInfoList.size()-1).getName();
						if(i<lines.size()-1){
							buf.append(entity.getLineBaseId()+","+entity.getLineName()+","+startStationName+"/"+endStationName+";");
						}else{
							buf.append(entity.getLineBaseId()+","+entity.getLineName()+","+startStationName+"/"+endStationName);
						}
					}
					company.setLineStr(buf.toString());
				}
				
				//员工
				if(null != company.getPassengerTelList() && company.getPassengerTelList().size()>0){
					String telephone = "";
					for(int i=0;i<company.getPassengerTelList().size();i++){
						if(i <company.getPassengerTelList().size()-1){
							telephone += company.getPassengerTelList().get(i)+",";
						}else{
							telephone += company.getPassengerTelList().get(i);
						}
					}
					System.out.println("telephone:"+telephone);
					company.setTelephone(telephone);
				}
				
				
			}
		}
		
		/**
		 * 省份
		 */
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		
		return SUCCESS;
	}
	
	/**
	 * 获得省份下面的城市
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "queryAreaCity", results = { @Result(type = "json") })
	public void queryAreaCity() throws Exception {
		if (null == sysArea || null == sysArea.getArearCode()) {
			JsonWriter.writeString("");
			return;
		}
		SysArea area = new SysArea();
		area.setFdCode(sysArea.getArearCode());
		List<SysArea> areaCitys = iSysAreaService.querySysArea(area);
		if (null != areaCitys) {
			List list = new ArrayList();
			for (int i = 0; i < areaCitys.size(); i++) {
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("arearCode", areaCitys.get(i).getArearCode());
				obj.put("areaName", areaCitys.get(i).getAreaName());
				list.add(obj);
			}
			JsonWriter.write(list);
		} else {
			JsonWriter.writeString("");
		}
	}
	
	/**
	 * 获取所有线路分页
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "queryAllLine", results = { @Result(type = "json") })
	public void queryAllLine() throws Exception {
		// 获得当前页
		String currPage = request.getParameter("currPage");
		if (null == currPage) {
			currPage = "1";
		}
		// 省份
		String provinceCode = request.getParameter("provinceCode");
		// 城市
		String areaCode = request.getParameter("areaCode");
		String lineName = request.getParameter("lineName");
		if (StringUtils.hasText(lineName)) {
			lineName = URLDecoder.decode(lineName, "utf-8");
		}
		QueryHelper query = new QueryHelper(" FROM line_base_info c")
				.addSelectClause("SELECT c.lineBaseId,c.lineName,c.startArea,c.endArea")
				.addCondition(true, "c.lineSubType = ?", 0).addCondition(true, "c.lineType = ?", 1)
				.addCondition(provinceCode != null && !"请选择".equals(provinceCode), "c.provinceCode = ?", provinceCode)
				.addCondition(areaCode != null && !"请选择".equals(areaCode), "c.cityCode = ?", areaCode)
				.addCondition(StringUtils.hasText(lineName), "c.lineName like ?", "%" + lineName + "%")
				.addCondition("c.lineStatus=?", 3)
				.addCondition("c.lineBaseId not in(select lineBaseId from company_line)", new Object[] {})
				.addOrderProperty("c.createOn", false).addLimitClause(Integer.parseInt(currPage), 5);
		PageBean pageBean = lineCustomService.getPageBean(Integer.parseInt(currPage), 5, query);
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		Map<String, Object> jsonDate = new HashMap<String, Object>();
		jsonDate.put("pageSize", pageBean.getPageSize());
		jsonDate.put("totalCount", pageBean.getRecordCount());
		jsonDate.put("currPage", pageBean.getCurrentPage());
		List resultData = new ArrayList();
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			LineEntity line = (LineEntity) pageBean.getRecordList().get(i);
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("lineBaseId", line.getLineBaseId());
			obj.put("lineName", line.getLineName());
			String startAndEnd = "";
			List<String> stationNames = this.getLineStationNames(line.getLineBaseId());
			if (false == CollectionUtils.isEmpty(stationNames)) {
				startAndEnd = stationNames.get(0) + "/" + stationNames.get(stationNames.size() - 1);
			}
			obj.put("startAndEnd", startAndEnd);
			resultData.add(obj);
		}
		jsonDate.put("resultData", resultData);
		JsonWriter.write(jsonDate);
	}
	
	public List<String> getLineStationNames(String lineBaseId){
	    List<String> stationNames = new ArrayList<String>();
		List<StationInfo> stationList = publishLineService.getStationListById(lineBaseId);
	    if(false==CollectionUtils.isEmpty(stationList)){
	    	for(StationInfo v : stationList){
	    		stationNames.add(v.getName());
	    	}
	    }
	    return stationNames;
	}
	
	
	/**
	 * 读取Excel表格里的手机号码
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "upload", results = { @Result(type = "json") })
	public void upload() throws IOException, JSONException {
		getResponse().setContentType("text/html;charset=UTF-8");
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		Map<String, Object> maps = new HashMap<String, Object>();
		try {
			FileInputStream fis = new FileInputStream(excelFile);
			int size = fis.available();
			if (size >= 500 * 1024) {
				getResponse().getWriter().print("overSize");
			} else {
				Workbook wb = WorkbookFactory.create(new FileInputStream(excelFile));
				// 默认读取第一个sheet
				Sheet sheet = wb.getSheetAt(0);
				// 得到总行数
				int rowNum = sheet.getLastRowNum();
				Row firstRow = sheet.getRow(0);
				if (null != firstRow && rowNum > 0) {
					int firstRowCellNumber = firstRow.getLastCellNum();
					Cell firstCell = firstRow.getCell(0);
					String headFirstName = null;
					if (null != firstCell) {
						headFirstName = this.getCellValue(firstCell);
					}
					if (firstRowCellNumber != 1 || !"手机号码".equals(headFirstName)) {
						maps.put("excelError", "Excel模板错误或无数据");
						JsonWriter.write(maps);
						return;
					}
				} else {
					maps.put("excelError", "Excel模板错误或无数据");
					JsonWriter.write(maps);
					return;
				}
				Row row = null;
				Cell cell = null;
				String cellValue = null;
				// 正文内容应该从第二行开始,第一行为表头的标题
				for (int i = 1; i <= rowNum; i++) {
					row = sheet.getRow(i);
					if (null == row) {
						continue;
					}
					// 本例只读取第一列数据
					cell = row.getCell(0);
					cellValue = this.getCellValue(cell);
					if (null != cellValue) {
						map.put(i, cellValue);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 接受已经导入的电话号码
		String choocePassengerList = request.getParameter("choocePassengerList");
		System.out.println(choocePassengerList);
		if (null != choocePassengerList && !"null".equals(choocePassengerList)) {
			String[] tels = choocePassengerList.split(",");
			// 导入的号码已经导入
			for (int i = 0; i < tels.length; i++) {
				for (int j = 1; j <= map.size(); j++) {
					String tvalue = map.get(j);
					if (tels[i].equals(tvalue)) {
						maps.put("duplicate", tels[i]);
						JsonWriter.write(maps);
						return;
					}
				}
			}
		}
		String res = null;
		try {
			res = checkMap(map);
		} catch (Exception e) {
			// 如果有异常，则通过json返回错误信息
			e.printStackTrace();
		}
		if (rows.size() > 0) {
			maps.put("wrongTelephone", rows.toString()); // 错误的电话号码序号列表
		}
		if (sameTelephone.size() > 0) {
			maps.put("theSameTelephone", sameTelephone.toString()); // 重复的电话号码序号列表
		}
		// 数据库存在
		if (databaseLists.size() > 0) {
			maps.put("databaseLists", databaseLists.toString());
		}
		maps.put("importTelephones", res);
		maps.put("result", "success");
		JsonWriter.write(maps);
	}
	
	private String getCellValue(Cell cell){
		DecimalFormat df = new DecimalFormat("0");// 格式化数字
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		String cellValue = null; 
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
		return cellValue;
	}
	/**
	 * 第N行存在必填项为空,第N行存在格式错误,第N行和第N+1行号码重复,如果验证失败则抛出受查异常
	 * @param map
	 */
	private String checkMap(Map<Integer, String> map) {
		//验证逻辑
		int number=1;
		StringBuffer res = new StringBuffer();//最终号码集合
		List<String> samePhoneRes = new ArrayList<String>();  //相同号码集合
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
						number=number+1;
						if (m.matches()) {
							//判断是否已经在数据库存在
							boolean flag = specialLineService.queryTelPhone(s);
							if(flag){
								//存在
								databaseLists.add(s);
							}
							if (samePhoneRes.contains(s)) { //判断是否有重复的号码
								sameTelephone.add(String.valueOf(number));
							} else {
								//如果数据库不存在
								if(!flag){
									samePhoneRes.add(s);
								}
							}
						}else{
							rows.add(String.valueOf(number));//号码格式错误列表
						}
					}
				 }
			 }
		}
		if(samePhoneRes.size()>0){
			for(int i=0;i<samePhoneRes.size();i++){
				res.append(samePhoneRes.get(i)+",");
			}
		}else{
			res.append("");
		}
		if(res.length()>0){
			//移除最后一个逗号
			res.deleteCharAt(res.length()-1);
		}
		return res.toString();
	}
	
	/**
	 * 验证公司名称是否存在
	 */
	@Action(value = "companyNameIsExist", results={@Result(type="json")})
	public String companyNameIsExist()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		String result = "error";
		if(null != company && !"".equals(company.getCompanyName())){
			
			Company c = specialLineService.companyNameIsExist(company);
			if(null == c){
				result = "no";
			}else{
				result = "yes";
			}
		}
		pw.print(result);
		
		return null;
	}
	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<SysArea> getProSysAreas() {
		return proSysAreas;
	}

	public void setProSysAreas(List<SysArea> proSysAreas) {
		this.proSysAreas = proSysAreas;
	}

	public SysArea getSysArea() {
		return sysArea;
	}

	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public List<String> getExistTelePhone() {
		return existTelePhone;
	}

	public void setExistTelePhone(List<String> existTelePhone) {
		this.existTelePhone = existTelePhone;
	}

	public List<LineEntity> getLines() {
		return lines;
	}

	public void setLines(List<LineEntity> lines) {
		this.lines = lines;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	
	
	
}
