package com.amwell.action.line;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.commons.JSONUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ILineService;
import com.amwell.service.ISysLogService;
import com.amwell.vo.CarClassVo;
import com.amwell.vo.ClassMonthOrderPriceVo;
import com.amwell.vo.DriverClassVo;
import com.amwell.vo.DriverVo;
import com.amwell.vo.LineClassCarDriverVo;
import com.amwell.vo.LineDetailVo;
import com.amwell.vo.SysMgrAdminVo;
import com.amwell.vo.VehicleInfoVo;

/**
 * 调度平台线路Action
 * 
 * @author Administrator
 *
 */
@ParentPackage("user-finit")
@Namespace("/line")
@Scope("prototype")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class LineAction extends BaseAction {

	private static final long serialVersionUID = -551285202592371884L;

	@Autowired
	private ILineService lineService;

	@Autowired
	private ISysLogService logService;

	private int currentPageIndex = 0;

	private int pageSize = 10;

	private LineDetailVo lineDetailVo;

	/**
	 * 显示字段 0:默认进来的 1:点击搜索的
	 */
	private Integer searchOrNo = 0;

	/**
	 * 所有线路
	 * 
	 * @return
	 */
	@Action(value = "getAllLineList", results = {
			@Result(name = "success", location = "../../view/line/linesList.jsp") })
	public String getAllLineList() {
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
				: Integer.parseInt(request.getParameter("currentPageIndex"));
		if (null == search) {
			search = new Search();
		}
		SysMgrAdminVo admin = this.getSessionUser();
		if (null != admin) {
			search.setField03(admin.getBusinessId());
		}
		map = lineService.getMerchantLineList(search, currentPageIndex, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}

	@Action(value = "getLineDetail", results = {
			@Result(name = "success", location = "../../view/line/linesDetail.jsp") })
	public String getLineDetail() {
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			lineDetailVo = this.lineService.getLineDetail(lineBaseId);
		}
		return SUCCESS;
	}

	/**
	 * 获取月份工作日的车辆司机信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getClassCarDriver", results = { @Result(type = "json") })
	public String getClassCarDriver() throws IOException {
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			if (orderDate.length() == 6) {
				int index = orderDate.indexOf("-");
				orderDate = orderDate.substring(0, index + 1) + "0" + orderDate.substring(index + 1);
			}
			List<LineClassCarDriverVo> ccdList = this.lineService.getLineClassCarDriverList(lineBaseId, orderStartTime,
					orderDate);
			if (false == CollectionUtils.isEmpty(ccdList)) {
				json = JSONUtil.formObjectsToJSONStr(ccdList);
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	@Action(value = "getDriverList", results = { @Result(type = "json") })
	public String getDriverList() throws IOException {
		String json = "[]";
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		String driverName = super.request.getParameter("driverName");
		SysMgrAdminVo admin = this.getSessionUser();
		if (null != admin && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			if (StringUtils.hasText(driverName)) {
				driverName = URLDecoder.decode(driverName, "utf-8");
			}
			Map<String, Object> res = this.lineService.getDriverList(admin.getBusinessId(), orderStartTime, orderDate,
					driverName, 0, Integer.MAX_VALUE);
			if (false == CollectionUtils.isEmpty(res)) {
				List<DriverVo> dList = (List) res.get("list");
				if (false == CollectionUtils.isEmpty(dList)) {
					json = JSONUtil.formObjectsToJSONStr(dList);
				}
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	@Action(value = "getCarList", results = { @Result(type = "json") })
	public String getCarList() throws IOException {
		String json = "[]";
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		String carInfo = super.request.getParameter("carInfo");
		SysMgrAdminVo admin = this.getSessionUser();
		if (null != admin && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			if (StringUtils.hasText(carInfo)) {
				carInfo = URLDecoder.decode(carInfo, "utf-8");
			}
			Map<String, Object> res = this.lineService.getCarList(admin.getBusinessId(), orderStartTime, orderDate,
					carInfo, 0, Integer.MAX_VALUE);
			if (false == CollectionUtils.isEmpty(res)) {
				List<VehicleInfoVo> cList = (List) res.get("list");
				if (false == CollectionUtils.isEmpty(cList)) {
					json = JSONUtil.formObjectsToJSONStr(cList);
				}
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 查看司机排班
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getDriverClass", results = { @Result(type = "json") })
	public String getDriverClass() throws IOException {
		String json = "[]";
		String driverId = super.request.getParameter("driverId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		String beginOrderDate = super.request.getParameter("beginOrderDate");
		String endOrderDate = super.request.getParameter("endOrderDate");
		if (StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate) && StringUtils.hasText(driverId)) {
			if (StringUtils.hasText(beginOrderDate) || StringUtils.hasText(endOrderDate)) {
				orderDate = null;
			}
			List<DriverClassVo> dcList = this.lineService.getDriverClassList(orderStartTime, orderDate, beginOrderDate,
					endOrderDate, driverId);
			if (false == CollectionUtils.isEmpty(dcList)) {
				json = JSONUtil.formObjectsToJSONStr(dcList);
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 查看车辆排班
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getCarClass", results = { @Result(type = "json") })
	public String getCarClass() throws IOException {
		String json = "[]";
		String vehicleId = super.request.getParameter("vehicleId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		String beginOrderDate = super.request.getParameter("beginOrderDate");
		String endOrderDate = super.request.getParameter("endOrderDate");
		if (StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate) && StringUtils.hasText(vehicleId)) {
			if (StringUtils.hasText(beginOrderDate) || StringUtils.hasText(endOrderDate)) {
				orderDate = null;
			}
			List<CarClassVo> ccList = this.lineService.getCarClassList(orderStartTime, orderDate, beginOrderDate,
					endOrderDate, vehicleId);
			if (false == CollectionUtils.isEmpty(ccList)) {
				json = JSONUtil.formObjectsToJSONStr(ccList);
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 调度班次司机和车辆
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "scheduleClassCarDriver", results = { @Result(type = "json") })
	public String scheduleClassCarDriver() throws IOException {
		String json = "error";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		// 多个工作日以逗号分开
		String orderDates = super.request.getParameter("orderDates");
		// 多个司机ID以逗号分开
		String driverIds = super.request.getParameter("driverIds");
		// 多个车辆ID以逗号分开
		String vehicleIds = super.request.getParameter("vehicleIds");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDates)
				&& StringUtils.hasText(driverIds) && StringUtils.hasText(vehicleIds)) {
			// 工作日，司机，车辆三者长度必须一致
			int oLen = orderDates.split(",").length;
			int dLen = driverIds.split(",").length;
			int vLen = vehicleIds.split(",").length;
			if (oLen != dLen || oLen != vLen) {
				// 数据错误
				json = "lenError";
			} else {
				// 数据正常
				int flag = this.lineService.scheduleClassCarDriver(lineBaseId, orderStartTime, orderDates.split(","),
						driverIds.split(","), vehicleIds.split(","));
				if (flag > 0) {
					json = "success";
				}
			}
		}
		if ("success".equals(json)) {
			logService.appendLog(super.getSessionUser() != null ? super.getSessionUser().getUserId() : "",
					super.getClientIp(request), "线路调度", "线路调度成功");
		} else {
			logService.appendLog(super.getSessionUser() != null ? super.getSessionUser().getUserId() : "",
					super.getClientIp(request), "线路调度", "线路调度失败");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	@Action(value = "scheduleLine", results = {
			@Result(name = "success", location = "../../view/line/linesScheduleInfo.jsp") })
	public String scheduleLine() {
		return SUCCESS;
	}

	@Action(value = "getLineOrderNoCount", results = {
			@Result(name = "success", location = "../../view/line/linesOrderNum.jsp") })
	public String getLineOrderNum() {
		return SUCCESS;
	}

	/**
	 * 查看线路班次包月价格
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getClassOneMonthOrderPrice", results = { @Result(type = "json") })
	public String getClassOneMonthOrderPrice() throws IOException {
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		// 参数要求：2014-09
		String orderDate = super.request.getParameter("orderDate");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			ClassMonthOrderPriceVo cmo = this.lineService.getClassOneMonthOrderPrice(lineBaseId, orderStartTime,
					orderDate);
			if (null != cmo) {
				json = JSONUtil.formObjectToJSONStr(cmo);
			}

		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
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

	public LineDetailVo getLineDetailVo() {
		return lineDetailVo;
	}

	public void setLineDetailVo(LineDetailVo lineDetailVo) {
		this.lineDetailVo = lineDetailVo;
	}

	public Integer getSearchOrNo() {
		return searchOrNo;
	}

	public void setSearchOrNo(Integer searchOrNo) {
		this.searchOrNo = searchOrNo;
	}

	/**
	 * 线路详情-查看包月价格
	 */
	@Action(value = "viewMonthPrice", results = {
			@Result(name = "success", location = "../../view/line/pop-viewMonthPrice.jsp") })
	public String viewMonthPrice() {
		return SUCCESS;
	}

	/**
	 * 线路详情-查看包月价格
	 */
	@Action(value = "viewSitePice", results = {
			@Result(name = "success", location = "../../view/line/pop-viewSitePic.jsp") })
	public String viewSitePice() {
		return SUCCESS;
	}

}
