package com.amwell.action.stat;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ILeaseOrderService;
import com.amwell.utils.StringUtils;
import com.amwell.vo.StatOutEntity;
import com.amwell.vo.SysMgrAdminVo;

/**
 * 
 * @author wangwenbin
 *
 * 2014-10-25
 */
/**
 * 支出统计
 */
@Namespace("/statOutAction")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StatOutAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1995527750107368837L;

	/**
	 * 订单相关
	 */
	@Autowired
	private ILeaseOrderService iLeaseOrderService;

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

	/**
	 * 显示字段 0:默认进来的 1:点击搜索的
	 */
	private Integer searchOrNo = 0;

	/**
	 * 支出列表
	 */

	@Action(value = "getStatOutList", results = {
			@Result(name = "success", location = "../../view/order/orderStatOutList.jsp") })
	public String getStatOutList() throws ParseException {
		/**
		 * 获取用户信息
		 */
		SysMgrAdminVo sysAdmin = (SysMgrAdminVo) request.getSession().getAttribute("userInfo");
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
				: Integer.parseInt(request.getParameter("currentPageIndex"));
		if (null == search) {
			search = new Search();
		}
		search.setField08(sysAdmin.getBusinessId());
		map = iLeaseOrderService.getStatOutListByPage(search, currentPageIndex, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		listSize = list == null ? 0 : list.size();
		totalMoney = (BigDecimal) map.get("totalMoney");
		return SUCCESS;
	}

	/**
	 * 支出列表Excel
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@Action(value = "exportExcel")
	public void exportExcel() throws IOException, ParseException {
		getResponse().setContentType("application/msexcel;charset=GBK");
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
				: Integer.parseInt(request.getParameter("currentPageIndex"));
		/**
		 * 获取用户信息
		 */
		SysMgrAdminVo sysAdmin = (SysMgrAdminVo) request.getSession().getAttribute("userInfo");
		if (null == search) {
			search = new Search();
		}
		search.setField08(sysAdmin.getBusinessId());
		map = iLeaseOrderService.getStatOutListByPage(search, 0, Integer.MAX_VALUE);
		String title = "支出统计列表"; // 导出列表头名称
		List<StatOutEntity> lists = (List) map.get("list");
		List<StatOutEntity> statTotalVos = setValues(lists);
		HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet();
		// 设置单元格长度
		sheet.setDefaultColumnWidth(20);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell;
		String[] cellNames = { "序号", "乘车日期", "班次", "订单号", "线路类型", "线路名称", "乘客", "联系方式", "司机", "车辆", "退款金额(元)", "退款类型",
				"操作人", "操作时间" };
		for (int i = 0; i < cellNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(cellNames[i]);
			cell.setCellStyle(style);
		}
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < statTotalVos.size(); i++) {
			row = sheet.createRow((int) (i + 1));
			StatOutEntity stat = (StatOutEntity) statTotalVos.get(i);
			// 第四步，创建单元格，并设置值
			cell = row.createCell(0);
			cell.setCellValue((double) i + 1);
			cell.setCellStyle(style);
			cell = row.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOrderDate());
			cell = row.createCell(2);
			cell.setCellValue(stat.getOrderStartTime());
			cell.setCellStyle(style);
			cell = row.createCell(3);
			cell.setCellValue(stat.getLeaseOrderNo());
			cell.setCellStyle(style);
			cell = row.createCell(4);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getLineSubType_s());
			cell = row.createCell(5);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getLineName());
			cell = row.createCell(6);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getNickName());
			cell = row.createCell(7);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getTelephone());
			cell = row.createCell(8);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getDriverName());
			cell = row.createCell(9);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getVehicleNumber());
			cell = row.createCell(10);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOutMoney().toString());
			cell = row.createCell(11);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOutType_s());
			cell = row.createCell(12);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getLoginName());
			cell = row.createCell(13);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOperatTime());
		}
		row = sheet.createRow((int) (statTotalVos.size() + 1));
		cell = row.createCell(0);
		cell.setCellValue((double) statTotalVos.size() + 1);
		cell.setCellStyle(style);
		cell = row.createCell(12);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		cell = row.createCell(13);
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
	private List<StatOutEntity> setValues(List<StatOutEntity> lists) {
		List<StatOutEntity> statTotalVos = new ArrayList<StatOutEntity>();
		for (StatOutEntity statOutEntity : lists) {
			StatOutEntity statTotalVo = new StatOutEntity();
			/**
			 * 时间
			 */
			String orderDate = "";
			if (!StringUtils.isEmpty(statOutEntity.getOrderDate())) {
				orderDate = statOutEntity.getOrderDate();
			}
			statTotalVo.setOrderDate(orderDate);
			/**
			 * 线路类型 0:上下班 1:旅游 (自由行)
			 */
			String lineSubType = "";
			if (null != statOutEntity.getLineSubType()) {
				if (0 == statOutEntity.getLineSubType()) {
					lineSubType = "上下班";
				} else if (1 == statOutEntity.getLineSubType()) {
					lineSubType = "自由行";
				}
			}
			statTotalVo.setLineSubType_s(lineSubType);
			/**
			 * 线路名称
			 */
			String lineName = "";
			if (!StringUtils.isEmpty(statOutEntity.getLineName())) {
				lineName = statOutEntity.getLineName();
			}
			statTotalVo.setLineName(lineName);
			/**
			 * /** 乘客
			 */
			String nickName = "";
			if (!StringUtils.isEmpty(statOutEntity.getNickName())) {
				nickName = statOutEntity.getDisplayId() + "/" + statOutEntity.getNickName();
			}
			statTotalVo.setNickName(nickName);
			/**
			 * 联系方式
			 */
			String telephone = "";
			if (!StringUtils.isEmpty(statOutEntity.getTelephone())) {
				telephone = statOutEntity.getTelephone();
			}
			statTotalVo.setTelephone(telephone);
			/**
			 * 司机
			 */
			String driverName = "";
			if (!StringUtils.isEmpty(statOutEntity.getDriverName())) {
				driverName = statOutEntity.getDriverName();
			}
			statTotalVo.setDriverName(driverName);
			/**
			 * 班次
			 */
			String orderStartTime = "";
			if (!StringUtils.isEmpty(statOutEntity.getOrderStartTime())) {
				orderStartTime = statOutEntity.getOrderStartTime();
			}
			statTotalVo.setOrderStartTime(orderStartTime);
			/**
			 * 车辆
			 */
			String vehicleNumber = "";
			if (!StringUtils.isEmpty(statOutEntity.getVehicleNumber())) {
				vehicleNumber = statOutEntity.getVehicleNumber();
			}
			statTotalVo.setVehicleNumber(vehicleNumber);
			/**
			 * 退款金额（元）
			 */
			BigDecimal outMoney = BigDecimal.valueOf(0);
			if (null != statOutEntity.getOutMoney()) {
				outMoney = statOutEntity.getOutMoney();
			}
			statTotalVo.setOutMoney(outMoney);
			/**
			 * 支出类型 0:全部 1:退票 2:改签
			 */
			String outType = "";
			if (null != statOutEntity.getOutType()) {
				if (1 == statOutEntity.getOutType()) {
					outType = "退票";
				} else if (2 == statOutEntity.getOutType()) {
					outType = "改签";
				}
			}
			statTotalVo.setOutType_s(outType);
			/**
			 * 操作人
			 */
			String loginName = "";
			if (!StringUtils.isEmpty(statOutEntity.getLoginName())) {
				loginName = statOutEntity.getLoginName();
			}
			statTotalVo.setLoginName(loginName);
			/**
			 * 操作时间
			 */
			String operatTime = "";
			if (!StringUtils.isEmpty(statOutEntity.getOperatTime())) {
				operatTime = statOutEntity.getOperatTime();
			}
			statTotalVo.setOperatTime(operatTime);
			/**
			 * 订单号
			 */
			String leaseOrderNo = "";
			if (!StringUtils.isAlpha(statOutEntity.getLeaseOrderNo())) {
				leaseOrderNo = statOutEntity.getLeaseOrderNo();
			}
			statTotalVo.setLeaseOrderNo(leaseOrderNo);
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

}
