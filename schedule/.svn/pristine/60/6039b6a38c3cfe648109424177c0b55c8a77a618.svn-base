package com.amwell.action.stat;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.entity.StatTotalVo;
import com.amwell.service.ILeaseOrderService;
import com.amwell.utils.StringUtils;
import com.amwell.vo.StatTotalEntity;
import com.amwell.vo.SysMgrAdminVo;

/**
 * @author wangwenbin
 *
 * 2014-8-21
 */
/**
 * 日账目统计
 */
@ParentPackage("user-finit")
@Namespace("/statDayIncomeAction")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StatDayIncomeAction extends BaseAction {

	private static final long serialVersionUID = 7819907155297554595L;

	/**
	 * 每页显示的记录数
	 */
	private Integer pageSize = 10;

	/**
	 * 记录数的下标起始数 limit 0,10中的0
	 */
	private Integer currentPageIndex = 0;

	/**
	 * 集合大小
	 */
	private Integer listSize = 0;
	/**
	 * 金额合计
	 */
	private BigDecimal totalMoney;

	@Autowired
	private ILeaseOrderService iLeaseOrderService;

	/**
	 * 显示字段 0:默认进来的 1:点击搜索的
	 */
	private Integer searchOrNo = 0;

	/**
	 * 页面控制时间字段--控制页面能选择的最大时间
	 */
	private String maxDate;

	/**
	 * 日收入统计
	 */

	@Action(value = "getDayIncome", results = {
			@Result(name = "success", location = "../../view/order/dayIncome.jsp") })
	public String getDayIncome() throws Exception {
		String searchDay = "";
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
				: Integer.parseInt(request.getParameter("currentPageIndex"));
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		/**
		 * 默认是查询昨天的数据
		 */
		if (null == search || (StringUtils.isEmpty(search.getField04()) && StringUtils.isEmpty(search.getField05()))) {
			Date date = new Date();
			search = new Search();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, -1);// 把日期往前退一天.整数往后推,负数往前移动
			date = calendar.getTime();
			String date_s = TimeFormat.format(date);
			maxDate = date_s;
			search.setField04(date_s);
			search.setField05(date_s);
		}
		if (StringUtils.isEmpty(search.getField05())) {
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, -1);// 把日期往前退一天.整数往后推,负数往前移动
			date = calendar.getTime();
			String date_s = TimeFormat.format(date);
			search.setField05(date_s);
		}
		if (null != search && !StringUtils.isEmpty(search.getField05())) {
			searchDay = search.getField05();
			String date = "";
			Date selectDate = TimeFormat.parse(searchDay);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(selectDate);
			calendar.add(Calendar.DATE, 1);// 把日期往前退一天.整数往后推,负数往前移动
			selectDate = calendar.getTime();
			/**
			 * 明天的时间
			 */
			date = TimeFormat.format(selectDate);
			search.setField05(date);
		}
		/**
		 * 获取用户信息
		 */
		SysMgrAdminVo sysAdmin = (SysMgrAdminVo) request.getSession().getAttribute("userInfo");
		/**
		 * 设置商户ID
		 */
		search.setField06(sysAdmin.getBusinessId());
		map = iLeaseOrderService.queryStatDayStatTotalList(search, currentPageIndex, pageSize);
		if (null != search && !StringUtils.isEmpty(search.getField05())) {
			/**
			 * 查询完以后把时间变回来
			 */
			search.setField05(searchDay);
		}
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		listSize = list == null ? 0 : list.size();
		totalMoney = (BigDecimal) map.get("totalMoney");

		return SUCCESS;
	}

	/**
	 * 导出 日收入统计Excel
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "exportExcel")
	public void exportExcel() throws IOException, ParseException {
		getResponse().setContentType("application/msexcel;charset=GBK");
		/**
		 * 默认是查询昨天的数据
		 */
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (null == search || (StringUtils.isEmpty(search.getField04()) && StringUtils.isEmpty(search.getField05()))) {
			Date date = new Date();
			search = new Search();
			String date_s = TimeFormat.format(date);
			search.setField04(date_s);
			search.setField05(date_s);
		}
		String searchDay = "";
		if (null != search && !StringUtils.isEmpty(search.getField05())) {
			searchDay = search.getField05();
			String date = "";
			Date selectDate = TimeFormat.parse(searchDay);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(selectDate);
			calendar.add(Calendar.DATE, 1);// 把日期往前退一天.整数往后推,负数往前移动
			selectDate = calendar.getTime();
			/**
			 * 昨天的时间
			 */
			date = TimeFormat.format(selectDate);
			search.setField05(date);
		}
		/**
		 * 获取用户信息
		 */
		SysMgrAdminVo sysAdmin = (SysMgrAdminVo) request.getSession().getAttribute("userInfo");
		/**
		 * 设置商户ID
		 */
		search.setField06(sysAdmin.getBusinessId());
		map = iLeaseOrderService.queryStatDayStatTotalList(search, 0, Integer.MAX_VALUE);
		if (null != search && !StringUtils.isEmpty(search.getField05())) {
			/**
			 * 查询完以后把时间变回来
			 */
			search.setField05(searchDay);
		}
		String title = "日收入统计列表"; // 导出列表头名称
		List<StatTotalEntity> lists = (List) map.get("list");
		List<StatTotalVo> statTotalVos = setValues(lists);
		HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet();
		// 设置单元格长度
		sheet.setDefaultColumnWidth((short) 20);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell;
		String[] cellNames = { "序号", "时间", "线路类型", "线路名称", "乘客", "司机", "车辆", "收款金额（元）" };
		for (int i = 0; i < cellNames.length; i++) {
			cell = row.createCell((short) i);
			cell.setCellValue(cellNames[i]);
			cell.setCellStyle(style);
		}
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < statTotalVos.size(); i++) {
			row = sheet.createRow((int) (i + 1));
			StatTotalVo stat = (StatTotalVo) statTotalVos.get(i);
			// 第四步，创建单元格，并设置值
			cell = row.createCell(0);
			cell.setCellValue((double) i + 1);
			cell.setCellStyle(style);
			cell = row.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOrderDate());
			cell = row.createCell(2);
			cell.setCellValue(stat.getLineSubType());
			cell.setCellStyle(style);
			cell = row.createCell(3);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getLineName());
			cell = row.createCell(4);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getNickName());
			cell = row.createCell(5);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getDriverName());
			cell = row.createCell(6);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getVehicleNumber());
			cell = row.createCell(7);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getConsumeLimit().toString());

		}
		row = sheet.createRow((int) (statTotalVos.size() + 1));
		cell = row.createCell(0);
		cell.setCellValue((double) statTotalVos.size() + 1);
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		totalMoney = (BigDecimal) map.get("totalMoney");
		cell.setCellValue(String.valueOf(totalMoney));
		cell.setCellStyle(style);
		String agent = request.getHeader("User-Agent");
		if (null != agent && agent.indexOf("Firefox") > -1) {
			title = new String(title.getBytes("utf-8"), "iso-8859-1");
		} else {
			title = String.valueOf(URLEncoder.encode(title, "UTF-8"));
		}
		String sFileName = title + ".xls";
		String attachment = "attachment;filename=\"" + sFileName + "\"";
		getResponse().setHeader("Content-Disposition", attachment);
		getResponse().setHeader("Connection", "close");
		getResponse().setHeader("Content-Type", "application/vnd.ms-excel");
		wb.write(getResponse().getOutputStream());
		wb.close();
	}

	/**
	 * 给集合赋值
	 * 
	 * @param lists
	 * @return
	 */
	private List<StatTotalVo> setValues(List<StatTotalEntity> lists) {
		List<StatTotalVo> statTotalVos = new ArrayList<StatTotalVo>();
		for (StatTotalEntity statTotalEntity : lists) {
			StatTotalVo statTotalVo = new StatTotalVo();
			/**
			 * 时间
			 */
			String orderDate = "";
			if (!StringUtils.isEmpty(statTotalEntity.getOrderDate())) {
				orderDate = statTotalEntity.getOrderDate();
			}
			statTotalVo.setOrderDate(orderDate);
			/**
			 * 线路类型 0:上下班 1:旅游 (自由行)
			 */
			String lineSubType = "";
			if (null != statTotalEntity.getLineSubType()) {
				if (0 == statTotalEntity.getLineSubType()) {
					lineSubType = "上下班";
				} else if (1 == statTotalEntity.getLineSubType()) {
					lineSubType = "自由行";
				}
			}
			statTotalVo.setLineSubType(lineSubType);
			/**
			 * 线路名称
			 */
			String lineName = "";
			if (!StringUtils.isEmpty(statTotalEntity.getLineName())) {
				lineName = statTotalEntity.getLineName();
			}
			statTotalVo.setLineName(lineName);
			/**
			 * 乘客
			 */
			statTotalVo.setNickName(statTotalEntity.getNickName() + "/" + statTotalEntity.getDisplayId());
			/**
			 * 司机
			 */
			String driverName = "";
			if (!StringUtils.isEmpty(statTotalEntity.getDriverName())) {
				driverName = statTotalEntity.getDriverName();
			}
			statTotalVo.setDriverName(driverName);
			/**
			 * 班次
			 */
			String orderStartTime = "";
			if (!StringUtils.isEmpty(statTotalEntity.getOrderStartTime())) {
				orderStartTime = statTotalEntity.getOrderStartTime();
			}
			statTotalVo.setOrderStartTime(orderStartTime);

			/**
			 * 车辆
			 */
			String vehicleNumber = "";
			if (!StringUtils.isEmpty(statTotalEntity.getVehicleNumber())) {
				vehicleNumber = statTotalEntity.getVehicleNumber();
			}
			statTotalVo.setVehicleNumber(vehicleNumber);
			/**
			 * 收款金额（元）
			 */
			BigDecimal consumeLimit = BigDecimal.valueOf(0);
			if (null != statTotalEntity.getConsumeLimit()) {
				consumeLimit = statTotalEntity.getConsumeLimit();
			}
			statTotalVo.setConsumeLimit(consumeLimit);

			statTotalVos.add(statTotalVo);
		}
		return statTotalVos;
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

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getSearchOrNo() {
		return searchOrNo;
	}

	public void setSearchOrNo(Integer searchOrNo) {
		this.searchOrNo = searchOrNo;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

}
