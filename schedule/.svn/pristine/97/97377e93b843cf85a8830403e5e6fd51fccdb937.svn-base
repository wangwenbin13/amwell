package com.amwell.action.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.DESCryptUtil;
import com.amwell.commons.JSONUtil;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.MyDate;
import com.amwell.commons.Sha1Utils;
import com.amwell.commons.StringUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.IDriverInfoService;
import com.amwell.service.ILineService;
import com.amwell.service.ISysAdminService;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.DriverClassScheduleVo;
import com.amwell.vo.DriverInfoEntity;
import com.amwell.vo.DriverSchedulePageVo;
import com.amwell.vo.DriverScheduleVo;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineClassVo;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.SysMgrAdminVo;

/**
 * 调度平台-司机管理
 * 
 * @author 龚雪婷
 *
 */
@ParentPackage("user-finit")
@Namespace("/dispatchDriver")
@SuppressWarnings("all")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class DriverAction extends BaseAction {

	private Logger logger = Logger.getLogger(DriverAction.class);

	@Autowired
	private IDriverInfoService driverInfoService;

	@Autowired
	private ILineService lineService;

	@Autowired
	private ISysAdminService sysAdminService;

	// 司机对象
	private DriverInfoEntity driverInfoEntity;

	private String driverId;// 司机id

	/**
	 * 记录数的下标记数，0开始，到10
	 */
	private Integer currentPage = 0;

	/**
	 * 显示字段 0:默认进来的 1:点击搜索的
	 */
	private Integer searchOrNo = 0;

	/**
	 * 加载司机列表
	 */
	@Action(value = "driverInfoList", results = {
			@Result(name = "success", location = "../../view/driver/driverList.jsp") })
	public String list() {
		try {
			if (request.getParameter("p") == null) {// 第一次访问该列表时清空session中的查询条件信息
				session.remove("driverInfoList_Cond");
			}
			/* 当前页 */
			currentPage = StringUtil.objectToInt(
					request.getParameter("currentPage") != null ? request.getParameter("currentPage") : "0");
			/* 将条件存到session,便于刷新后任然存在页面而不会丢失 */
			search = (Search) (search == null ? session.get("driverInfoList_Cond") : search);
			session.put("driverInfoList_Cond", search);
			/* 每页显示条数 */
			int pageSize = 10;
			// 集合对象
			if (null == search) {
				search = new Search();
			}
			search.setField04(this.getSessionUser().getBusinessId());// 传入登录商户id为条件
			map = driverInfoService.listByPage(search, currentPage, pageSize);
			list = (List) map.get("list");// 数据对象
			if (!(null == list || list.size() == 0)) {
				for (Object o : list) {
					// 根据省份证号计算年龄
					DriverInfoEntity die = (DriverInfoEntity) o;
					if (null != die && StringUtils.isNotBlank(die.getIdNumber())) {
						String str = die.getIdNumber().substring(6, 14);
						String birthday = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6);
						long theLong = new Date().getTime() - MyDate.strToDate(birthday).getTime();
						float value = (float) theLong / 1000 / 60 / 60 / 24 / 365;
						int d = Math.round(value);
						die.setAge(String.valueOf(d));
					}
				}
			}
			page = (Page) map.get("page");// 分页对象
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 查看司机详情
	 * 
	 * @return
	 */
	@Action(value = "driverInfoDetail", results = {
			@Result(name = "success", location = "../../view/driver/driverDetail.jsp") })
	public String detail() {
		try {
			String driverId = request.getParameter("driverId");
			driverInfoEntity = driverInfoService.getDriverById(driverId);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 根据时间段显示司机排班信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getDriverScheduleList", results = { @Result(type = "json") })
	public String getDriverScheduleList() throws IOException {
		String json = "[]";
		String driverId = super.request.getParameter("driverId");
		String beginOrderDate = super.request.getParameter("beginOrderDate");
		String endOrderDate = super.request.getParameter("endOrderDate");
		String pageSizeStr = super.request.getParameter("pageSize");
		String currPageStr = super.request.getParameter("currPage");
		String totalCountStr = super.request.getParameter("totalCount");
		int pageSize = 7;
		if (StringUtils.isNotBlank(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		int currPage = 0;
		if (StringUtils.isNotBlank(currPageStr)) {
			currPage = Integer.parseInt(currPageStr) - 1;
		}
		int totalCount = 0;
		if (StringUtils.isNotBlank(totalCountStr)) {
			totalCount = Integer.parseInt(totalCountStr);
		}
		search = new Search();
		search.setField01(beginOrderDate);
		search.setField02(endOrderDate);
		map = driverInfoService.lineClassPage(search, driverId, currPage, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		currPage = currPage + 1;
		totalCount = page.getTotalCount();
		List<DriverScheduleVo> driverScheduleList = new ArrayList<DriverScheduleVo>();
		LineClassVo vo = null;
		List<String> l = new ArrayList<String>();
		if (!(null == list || list.size() == 0)) {
			Iterator<LineClassVo> it = list.iterator();
			while (it.hasNext()) {
				vo = it.next();
				if (!l.contains(vo.getOrderDate())) {
					l.add(vo.getOrderDate());
				}
			}
		}
		DriverScheduleVo v = null;
		List<DriverClassScheduleVo> scheduleList = null;
		DriverClassScheduleVo d1 = null;
		for (String s : l) {
			v = new DriverScheduleVo();
			v.setOrderDate(s);
			scheduleList = new ArrayList<DriverClassScheduleVo>();
			for (Object o : list) {
				vo = (LineClassVo) o;
				if (vo.getOrderDate().equals(s)) {
					if (StringUtils.isNotBlank(vo.getLineBaseId())) {
						String name = this.lineService.getStartAndEndname(vo.getLineBaseId());
						if (StringUtils.isNotBlank(name)) {
							String[] sn = name.split(",");
							d1 = new DriverClassScheduleVo();
							if (sn.length > 0) {
								d1.setEndname(sn[sn.length - 1]);
							}
							d1.setLineClassId(null);
							d1.setLineSubType(vo.getLineSubType());
							int theTime = vo.getLineTime();
							String timeStr = null;
							if (theTime < 60) {
								timeStr = theTime + "分钟";
							}
							if (theTime >= 60) {
								timeStr = theTime / 60 + "小时";
								if (theTime % 60 > 0) {
									timeStr = timeStr + theTime % 60 + "分钟";
								}
							}
							d1.setLineTime(timeStr);
							d1.setOrderDate(vo.getOrderDate());
							d1.setOrderStartTime(vo.getOrderStartTime());
							if (sn.length > 0) {
								d1.setStartname(sn[0]);
							}
							d1.setVehicleNumber(vo.getVehicleNumber());
							scheduleList.add(d1);
						}
					}
				}
			}
			v.setScheduleList(scheduleList);
			driverScheduleList.add(v);
		}
		DriverSchedulePageVo thePage = new DriverSchedulePageVo();
		thePage.setPageSize(pageSize);
		thePage.setTotalCount(totalCount);
		thePage.setCurrPage(currPage);
		thePage.setResultDate(driverScheduleList);
		json = JSONUtil.formObjectToJSONStr(thePage);
		// 去掉前后的[]，以保证页面能正常使用json数据
		//json = json.substring(1, json.length() - 1);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 根据司机id导出排班信息
	 * 
	 * @return
	 */
	@Action(value = "exportDriverScheduleList")
	public void exportDriverScheduleList() {
		try {
			String driverId = super.request.getParameter("driverId");
			String beginOrderDate = super.request.getParameter("beginOrderDate");
			String endOrderDate = super.request.getParameter("endOrderDate");
			search = new Search();
			search.setField01(beginOrderDate);
			search.setField02(endOrderDate);
			map = driverInfoService.lineClass(search, driverId);
			list = (List) map.get("list");// 数据对象
			String title = ""; // 导出列表头名称
			String[] hearders = {};
			String[] fields = {};
			title = "班次信息";
			hearders = new String[] { "日期", "发车时间", "类型", "线路", "车牌号", "预计时间" };// 表头数组
			fields = new String[] { "orderDate", "orderStartTime", "lineSubTypeS", "lineName", "vehicleNumber",
					"lineTimeStr" };// 对象属性数组
			// 转换类型
			if (null != list && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					LineClassVo lineClassVo = (LineClassVo) list.get(i);
					lineClassVo.setLineSubTypeS(lineClassVo.getLineSubType() == 0 ? "上下班" : "旅游");
					int theTime = lineClassVo.getLineTime();
					String timeStr = null;
					if (theTime < 60) {
						timeStr = theTime + "分钟";
					}
					if (theTime >= 60) {
						timeStr = theTime / 60 + "小时";
						if (theTime % 60 > 0) {
							timeStr = timeStr + theTime % 60 + "分钟";
						}
					}
					lineClassVo.setLineTimeStr(timeStr);
				}
			}
			TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
			JsGridReportBase report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td, agent);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
	}

	/**
	 * 查询司机是否和班次关联
	 * 
	 * @return
	 */
	@Action(value = "searchBeforeDel", results = { @Result(type = "json") })
	public String searchBeforeDel() {
		try {
			String returnStr = "-1";
			String driverId = request.getParameter("driverId");
			if (StringUtils.isNotBlank(driverId)) {
				List<LineClassEntity> list = this.driverInfoService.getLineClassByDriverId(driverId);
				if (!(null == list || list.size() == 0)) {
					String str = "";
					for (LineClassEntity lce : list) {
						str = lce.getOrderStartTime() + "、" + str;
					}
					returnStr = "司机" + str.substring(0, str.length() - 1) + "已被调度排班，确定要删除司机吗？";
				}
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(returnStr);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 单个或多个司机删除
	 * 
	 * @return
	 */
	@Action(value = "driverInfoDelete", results = { @Result(type = "json") })
	public String delete() {
		try {
			String returnStr = null;
			String driverId = request.getParameter("driverId");
			if (StringUtils.isBlank(driverId)) {
				returnStr = "信息丢失，请刷新列表重新操作！";
			} else {
				int delFlag = driverInfoService.deleteDriverById(driverId, this.getSessionUser().getUserId());
				if (delFlag > 0) {
					returnStr = "ok";
				} else {
					returnStr = "删除失败，请刷新列表重新操作！";
				}
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(returnStr);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 进入编辑页面
	 * 
	 * @return
	 */
	@Action(value = "toDriverEditPage", results = {
			@Result(name = "success", location = "../../view/driver/driverAdd.jsp") })
	public String toEditPage() {
		try {
			String driverId = request.getParameter("driverId");
			if (StringUtils.isNotBlank(driverId)) {
				driverInfoEntity = driverInfoService.getDriverById(driverId);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 保存司机信息
	 * 
	 * @return
	 */
	@Action(value = "driverInfoSave", results = { @Result(type = "json") })
	public void save() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String resultStr = null;
		String requestType = request.getParameter("requestType");
		if (!"page".equals(requestType)) {
			if (driverInfoEntity == null) {
				resultStr = "请进入添加页面进行添加操作";
			} else {
				if (StringUtils.isBlank(driverInfoEntity.getDriverName())) {
					resultStr = "司机姓名不能为空，请进入添加页面重新输入";
				}
				if (null == resultStr && StringUtils.isBlank(driverInfoEntity.getTelephone())) {
					resultStr = "手机号码不能为空，请进入添加页面重新输入";
				}
				if (null == resultStr && null == driverInfoEntity.getAccountStatus()) {
					resultStr = "账号状态不能为空，请进入添加页面重新输入";
				}
				if (null == resultStr) {
					resultStr = convertEncoding(driverInfoEntity);
				}
			}
		}
		if (null != resultStr) {
			map.put("status", "9");
			map.put("message", resultStr);
			JsonWriter.write(map);
			return;
		}
		if (StringUtils.isNotBlank(driverInfoEntity.getTelephone())) {
			// 根据电话号码查询司机信息
			List<DriverInfoEntity> driverList = this.driverInfoService
					.getDriverByTel(driverInfoEntity.getTelephone().trim());
			if (null == driverList || driverList.size() == 0) {
				resultStr = "0";
				// 电话号码不存在
				map.put("status", "0");
			} else {
				if ((StringUtils.isBlank(driverInfoEntity.getDriverId()) && driverList.size() > 0)
						|| (StringUtils.isNotBlank(driverInfoEntity.getDriverId())
								&& (!driverList.get(0).getDriverId().equals(driverInfoEntity.getDriverId())))) {
					resultStr = "1";// 电话号码已经存在
					map.put("status", "1");
					MgrBusinessEntity business = sysAdminService.getBusinessById(driverList.get(0).getBusinessId());
					map.put("message", business.getBrefName());
				} else {
					resultStr = "0";// 电话号码不存在
					map.put("status", "0");
				}
			}
		}
		if (resultStr == "0") {
			boolean flagType = false;
			String str = driverInfoEntity.getTelephone();
			// 将密码进行加密
			String passWord = Sha1Utils
					.encrypt(driverInfoEntity.getTelephone() + "&" + str.substring(str.length() - 6, str.length()));
			driverInfoEntity.setPassWord(passWord);
			SysMgrAdminVo vo = this.getSessionUser();
			driverInfoEntity.setBusinessId(vo.getBusinessId());// ************获取登录用户id
			// 开发人员备用字段
			driverInfoEntity.setComments(DESCryptUtil
					.des(driverInfoEntity.getTelephone() + "&" + str.substring(str.length() - 6, str.length())));

			if (StringUtils.isBlank(driverInfoEntity.getDriverId())) {
				flagType = true;
				driverInfoEntity.setCreateBy(vo.getUserId());
				driverInfoEntity.setCreateOn(MyDate.getMyDateLong());
			} else {
				driverInfoEntity.setUpdateBy(vo.getUserId());
				driverInfoEntity.setUpdateOn(MyDate.getMyDateLong());
			}
			int flag = driverInfoService.saveOrUpdateDriver(driverInfoEntity, this.getSessionUser().getUserId());
			if (flagType) {// 添加司机
				if (flag > 0) {
					// 添加司机成功
					map.put("status", "2");
					map.put("message", "添加司机成功");
				} else {
					// 添加司机失败
					map.put("status", "3");
					map.put("message", "添加司机失败");
				}
			} else {// 修改司机
				if (flag > 0) {
					// 修改司机成功
					map.put("status", "4");
					map.put("message", "修改司机成功");
				} else {
					// 修改司机失败
					map.put("status", "5");
					map.put("message", "修改司机失败");
				}
			}
		}
		JsonWriter.write(map);
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	private String convertEncoding(DriverInfoEntity driverInfoEntity) throws Exception {
		// 取出客户提交的参数集
		Enumeration<String> paramNames = request.getParameterNames();
		// 遍历参数集取出每个参数的名称及值
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();// 取出参数名称
			if (StringUtils.isNotBlank(name) && name.equals("driverInfoEntity.driverId")) {// 司机ID
				String[] values = request.getParameterValues(name); // 根据参数名称取出其值
				if (!(null == values || values.length == 0 || StringUtils.isBlank(values[0]))) {
					driverInfoEntity.setDriverId(new String(values[0].trim().getBytes("iso-8859-1"), "UTF-8"));
				}
			}
			if (StringUtils.isNotBlank(name) && name.equals("driverInfoEntity.driverName")) {// 司机姓名
				String[] values = request.getParameterValues(name);
				if (!(null == values || values.length == 0 || StringUtils.isBlank(values[0]))) {
					String driverName = new String(values[0].trim().getBytes("iso-8859-1"), "UTF-8");
					if (driverName.length() <= 64) {
						driverInfoEntity.setDriverName(driverName);
					} else {
						return "司机姓名长度不能超过64";
					}
				}
			}
			if (StringUtils.isNotBlank(name) && name.equals("driverInfoEntity.telephone")) {// 司机电话
				String[] values = request.getParameterValues(name);
				if (!(null == values || values.length == 0 || StringUtils.isBlank(values[0]))) {
					String reg = "^((0{0,1})|(\\+?86)|(\\(\\+86\\)))(13[0-9]|15[0-9]|147|170|18[0-9])[0-9]{8}$";
					String telephone = new String(values[0].trim().getBytes("iso-8859-1"), "UTF-8");
					if (match(reg, telephone)) {
						driverInfoEntity.setTelephone(telephone);
					} else {
						return "请输入合法手机号码";
					}
				}
			}
			if (StringUtils.isNotBlank(name) && name.equals("driverInfoEntity.birthday")) {// 出生年月
				String[] values = request.getParameterValues(name);
				if (!(null == values || values.length == 0 || StringUtils.isBlank(values[0]))) {
					String birthday = new String(values[0].trim().getBytes("iso-8859-1"), "UTF-8");
					if (birthday.length() <= 64) {
						driverInfoEntity.setBirthday(birthday);
					} else {
						return "出生年月长度不能超过64";
					}
				}
			}
			if (StringUtils.isNotBlank(name) && name.equals("driverInfoEntity.driverNo")) {// 驾驶证号码
				String[] values = request.getParameterValues(name);
				if (!(null == values || values.length == 0 || StringUtils.isBlank(values[0]))) {
					String reg = "^[0-9]{17}[xX0-9]{1}$";
					String driverNo = new String(values[0].trim().getBytes("iso-8859-1"), "UTF-8");
					if (match(reg, driverNo)) {
						driverInfoEntity.setDriverNo(driverNo);
					} else {
						return "请输入合法驾驶证号码";
					}
				}
			}
			if (StringUtils.isNotBlank(name) && name.equals("driverInfoEntity.inspectionDate")) {// 年检日期
				String[] values = request.getParameterValues(name);
				if (!(null == values || values.length == 0 || StringUtils.isBlank(values[0]))) {
					String inspectionDate = new String(values[0].trim().getBytes("iso-8859-1"), "UTF-8");
					if (inspectionDate.length() <= 64) {
						driverInfoEntity.setInspectionDate(inspectionDate);
					} else {
						return "年检日期长度不能超过64";
					}
				}
			}
			if (StringUtils.isNotBlank(name) && name.equals("driverInfoEntity.idNumber")) {// 身份证号
				String[] values = request.getParameterValues(name);
				if (!(null == values || values.length == 0 || StringUtils.isBlank(values[0]))) {
					String reg = "^[0-9]{6}[1-9]{1}[0-9]{3}[0-1]{1}[0-9]{1}[0-3]{1}[0-9]{1}[0-9]{3}[xX0-9]{1}$";
					String idNumber = new String(values[0].trim().getBytes("iso-8859-1"), "UTF-8");
					if (match(reg, idNumber)) {
						driverInfoEntity.setIdNumber(idNumber);
					} else {
						return "请输入合法身份证号码";
					}
				}
			}
			if (StringUtils.isNotBlank(name) && name.equals("driverInfoEntity.entryDate")) {// 入职日期
				String[] values = request.getParameterValues(name);
				if (!(null == values || values.length == 0 || StringUtils.isBlank(values[0]))) {
					String entryDate = new String(values[0].trim().getBytes("iso-8859-1"), "UTF-8");
					if (entryDate.length() <= 64) {
						driverInfoEntity.setEntryDate(entryDate);
					} else {
						return "入职日期长度不能超过64";
					}
				}
			}
		}
		return null;
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	@Action(value = "resetPassword", results = { @Result(type = "json") })
	public String resetPassword() {
		try {
			String returnStr = null;
			String driverId = request.getParameter("driverId");
			if (StringUtils.isBlank(driverId)) {
				returnStr = "信息丢失，请刷新列表重新操作！";
			} else {
				DriverInfoEntity die = driverInfoService.getDriverById(driverId);
				if (null == die) {
					returnStr = "信息丢失，请刷新列表重新操作！";
				} else {
					DriverInfoEntity driver = new DriverInfoEntity();
					driver.setDriverId(driverId);
					// 获取手机号码后六位为初始密码
					// 将密码进行加密
					String str = die.getTelephone();
					String passWord = Sha1Utils
							.encrypt(die.getTelephone() + "&" + str.substring(str.length() - 6, str.length()));
					driver.setPassWord(passWord);
					// 开发人员备用字段
					driver.setComments(
							DESCryptUtil.des(die.getTelephone() + "&" + str.substring(str.length() - 6, str.length())));
					int delFlag = driverInfoService.saveOrUpdateDriver(driver, this.getSessionUser().getUserId());
					if (delFlag > 0) {
						returnStr = "ok";
					} else {
						returnStr = "重置密码失败，请刷新列表重新操作！";
					}
				}
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(returnStr);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * java模拟发送http post请求实现短信发送
	 */
	private void postRequest(String httpUrl, String content, String mobileNo) {
		try {
			/**
			 * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
			 * java.net.URL and //java.net.URLConnection
			 * 
			 * 使用页面发送请求的正常流程：在页面http://www.faircanton.com/message/loginlytebox.
			 * asp中输入用户名和密码，然后按登录，
			 * 跳转到页面http://www.faircanton.com/message/check.asp进行验证
			 * 验证的的结果返回到另一个页面
			 * 
			 * 使用java程序发送请求的流程：使用URLConnection向http://www.faircanton.com/message
			 * /check.asp发送请求 并传递两个参数：用户名和密码 然后用程序获取验证结果
			 */
			// String
			URL url = new URL(httpUrl);
			URLConnection connection = url.openConnection();
			/**
			 * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
			 * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
			 */
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "8859_1");
			out.write("content=" + content + "&mobileNo=" + mobileNo); // 向页面传递数据。post的关键所在！
			// remember to clean up
			out.flush();
			out.close();

			/**
			 * 这样就可以发送一个看起来象这样的POST： POST /jobsearch/jobsearch.cgi HTTP 1.0
			 * ACCEPT: text/plain Content-type:
			 * application/x-www-form-urlencoded Content-length: 99 username=bob
			 * password=someword
			 */
			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			// 传说中的三层包装阿！
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine + "/r/n";

			}
			logger.info(sTotalString);
			if (sTotalString.contains("03")) {
				logger.info("给司机发送短信成功");
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
	}

	public DriverInfoEntity getDriverInfoEntity() {
		return driverInfoEntity;
	}

	public void setDriverInfoEntity(DriverInfoEntity driverInfoEntity) {
		this.driverInfoEntity = driverInfoEntity;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getSearchOrNo() {
		return searchOrNo;
	}

	public void setSearchOrNo(Integer searchOrNo) {
		this.searchOrNo = searchOrNo;
	}
}
