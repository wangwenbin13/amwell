package com.amwell.action.bc;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.IBusinessBiddingService;
import com.amwell.vo.SysMgrAdminVo;
import com.amwell.vo.bc.AlreadyQuoteLineVo;
import com.amwell.vo.bc.BcLineVo;
import com.amwell.vo.bc.BusinessScheduleVo;

@ParentPackage("user-finit")
@Namespace("/businessBidding")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class BusinessBiddingAction extends BaseAction {

	private static final long serialVersionUID = 2413701737955543118L;

	private static final Logger log = Logger.getLogger(BusinessBiddingAction.class);

	@Autowired
	private IBusinessBiddingService businessBiddingService;

	private int currentPageIndex = 0;

	private int pageSize = 10;

	private BcLineVo bcLineVo;

	private BusinessScheduleVo scheduleVo;

	private String searchOrNo;

	private AlreadyQuoteLineVo alreadyQuoteVo;

	/**
	 * 包车管理-所有包车线路列表
	 * 
	 * @return
	 */
	@Action(value = "getBCAllLineList", results = {
			@Result(name = "success", location = "../../view/bc/bcLinesList.jsp") })
	public String getBCAllLineList() {
		// 处理显示没有数据的时候
		searchOrNo = request.getParameter("searchOrNo");
		if (null == searchOrNo) {
			searchOrNo = "0";
		} else {
			searchOrNo = "1";
		}
		currentPageIndex = super.getCurrentPageIndexFromPage(super.request);
		if (null == search) {
			search = new Search();
		}
		SysMgrAdminVo admin = super.getSessionUser();
		if (null != admin) {
			search.setField01(admin.getBusinessId());
		}
		map = businessBiddingService.getBCAllLineList(search, currentPageIndex, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}

	/**
	 * 包车管理-所有包车线路列表-调度详情
	 * 
	 * @return
	 */
	@Action(value = "getBCLineDetail", results = {
			@Result(name = "success", location = "../../view/bc/bcLinesDetail.jsp") })
	public String getBCLineDetail() {
		String orderNo = super.request.getParameter("orderNo");
		if (StringUtils.hasText(orderNo)) {
			scheduleVo = businessBiddingService.getBusinessOrderDetail(orderNo);
		}
		return SUCCESS;
	}

	/**
	 * 调度司机和车辆
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "scheduleDriverAndCar", results = { @Result(type = "json") })
	public String scheduleDriverAndCar() throws IOException {
		String json = "error";
		BusinessScheduleVo vo = this.createBusinessScheduleVo();
		int flag = businessBiddingService.scheduleDriverAndCar(vo);
		if (flag > 0) {
			json = "success";
		} else if (flag == -1) {
			json = "overtime";
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	private BusinessScheduleVo createBusinessScheduleVo() {
		BusinessScheduleVo vo = new BusinessScheduleVo();
		SysMgrAdminVo adminVo = super.getSessionUser();
		if (null == adminVo) {
			throw new IllegalArgumentException("adminVo can't be null.");
		}
		vo.setCreateBy(adminVo.getBusinessId());
		vo.setOrderNo(super.request.getParameter("orderNo"));
		vo.setDriverName(super.request.getParameterValues("driverName"));
		vo.setDriverTelephone(super.request.getParameterValues("driverTelephone"));
		vo.setCarNumber(super.request.getParameterValues("carNumber"));
		vo.setCarSeats(super.request.getParameterValues("carSeats"));
		return vo;
	}

	/**
	 * 包车管理-待报价列表
	 * 
	 * @return
	 */
	@Action(value = "getWaitQuoteList", results = {
			@Result(name = "success", location = "../../view/bc/waitQuoteList.jsp") })
	public String getWaitQuoteList() {
		currentPageIndex = super.getCurrentPageIndexFromPage(super.request);
		if (null == search) {
			search = new Search();
		}
		SysMgrAdminVo admin = super.getSessionUser();
		if (null != admin) {
			search.setField01(admin.getBusinessId());
		}
		// 排序字段,如： orderBy createOn desc
		String orderBy = request.getParameter("orderBy");
		search.setField11(orderBy);
		map = businessBiddingService.getWaitQuoteList(search, currentPageIndex, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}

	@Action(value = "getBeforeWaitQuoteDetail", results = { @Result(type = "json") })
	public String getBeforeWaitQuoteDetail() throws IOException {
		String json = "error";
		String bc_line_id = super.request.getParameter("bc_line_id");
		SysMgrAdminVo admin = super.getSessionUser();
		if (StringUtils.hasText(bc_line_id) && null != admin) {
			BcLineVo temp = businessBiddingService.getWaitQuoteDetail(admin.getBusinessId(), bc_line_id);
			if (null != temp) {
				json = "success";
			}
		} else {
			log.error("bc_line_id or admin can't be.");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 包车管理-待报价详情
	 * 
	 * @return
	 */
	@Action(value = "getWaitQuoteDetail", results = {
			@Result(name = "success", location = "../../view/bc/waitQuoteDetail.jsp") })
	public String getWaitQuoteDetail() {
		String bc_line_id = super.request.getParameter("bc_line_id");
		SysMgrAdminVo admin = super.getSessionUser();
		if (StringUtils.hasText(bc_line_id) && null != admin) {
			this.bcLineVo = businessBiddingService.getWaitQuoteDetail(admin.getBusinessId(), bc_line_id);
		} else {
			log.error("bc_line_id or admin can't be.");
		}
		return SUCCESS;
	}

	@Action(value = "addBusinessBidding", results = { @Result(type = "json") })
	public String addBusinessBidding() throws IOException {
		String json = "error";
		BusinessBiddingForm biddingForm = this.createBusinessBiddingForm();
		int flag = businessBiddingService.addBusinessBidding(biddingForm);
		if (flag > 0) {
			json = "success";
		} else if (flag == -1) {
			json = "overtime";
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	private BusinessBiddingForm createBusinessBiddingForm() {
		BusinessBiddingForm biddingForm = new BusinessBiddingForm();
		SysMgrAdminVo adminVo = super.getSessionUser();
		if (null == adminVo) {
			throw new IllegalArgumentException("adminVo can't be null.");
		}
		if (StringUtils.hasText(super.request.getParameter("businessBiddingId"))) {
			biddingForm.setBusinessBiddingId(super.request.getParameter("businessBiddingId").trim());
		}
		biddingForm.setBusinessId(adminVo.getBusinessId());
		biddingForm.setBc_line_id(super.request.getParameter("bc_line_id"));
		biddingForm.setExpireTime(super.request.getParameter("expireTime"));
		biddingForm.setCarType(super.request.getParameterValues("carType"));
		biddingForm.setCarSeats(super.request.getParameterValues("carSeats"));
		biddingForm.setCarAge(super.request.getParameterValues("carAge"));
		biddingForm.setTotalCars(super.request.getParameterValues("totalCars"));
		biddingForm.setBasicCost(super.request.getParameter("basicCost"));
		biddingForm.setDriverMealCost(super.request.getParameter("driverMealCost"));
		biddingForm.setDriverHotelCost(super.request.getParameter("driverHotelCost"));
		biddingForm.setOilCost(super.request.getParameter("oilCost"));
		// 总费用要单独计算
		biddingForm.setDriverWorkHour(super.request.getParameter("driverWorkHour"));
		biddingForm.setOvertimeCost(super.request.getParameter("overtimeCost"));
		biddingForm.setLimitMileage(super.request.getParameter("limitMileage"));
		biddingForm.setOvermileageCost(super.request.getParameter("overmileageCost"));
		biddingForm.setNoReturn(super.request.getParameter("noReturn"));
		biddingForm.setReturnOneTime(super.request.getParameter("returnOneTime"));
		biddingForm.setReturnOnePer(super.request.getParameter("returnOnePer"));
		biddingForm.setReturnTwoTime(super.request.getParameter("returnTwoTime"));
		biddingForm.setReturnTwoPer(super.request.getParameter("returnTwoPer"));
		biddingForm.setAdditionalServices(super.request.getParameter("additionalServices"));
		biddingForm.setRemark(super.request.getParameter("remark"));
		return biddingForm;
	}

	/**
	 * 包车管理-已报价列表
	 * 
	 * @return
	 */
	@Action(value = "getAlreadyQuoteList", results = {
			@Result(name = "success", location = "../../view/bc/alreadyQuoteList.jsp") })
	public String getAlreadyQuoteList() {
		currentPageIndex = super.getCurrentPageIndexFromPage(super.request);
		// 处理显示没有数据的时候
		searchOrNo = request.getParameter("searchOrNo");
		if (null == searchOrNo) {
			searchOrNo = "0";
		} else {
			searchOrNo = "1";
		}
		if (null == search) {
			search = new Search();
		}
		SysMgrAdminVo admin = super.getSessionUser();
		if (null != admin) {
			search.setField01(admin.getBusinessId());
		}
		map = businessBiddingService.getAlreadyQuoteList(search, currentPageIndex, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}

	/**
	 * 包车管理-已报价详情
	 * 
	 * @return
	 */
	@Action(value = "getAlreadytQuoteDetail", results = {
			@Result(name = "success", location = "../../view/bc/alreadytQuoteDetail.jsp") })
	public String getAlreadytQuoteDetail() {
		// 传递竟价表ID
		String id = super.request.getParameter("id");
		SysMgrAdminVo admin = super.getSessionUser();
		if (StringUtils.hasText(id) && null != admin) {
			this.alreadyQuoteVo = businessBiddingService.getAlreadytQuoteDetail(admin.getBusinessId(), id);
		} else {
			log.error("bc_line_id or admin can't be.");
		}
		return SUCCESS;
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

	public BcLineVo getBcLineVo() {
		return bcLineVo;
	}

	public void setBcLineVo(BcLineVo bcLineVo) {
		this.bcLineVo = bcLineVo;
	}

	public BusinessScheduleVo getScheduleVo() {
		return scheduleVo;
	}

	public void setScheduleVo(BusinessScheduleVo scheduleVo) {
		this.scheduleVo = scheduleVo;
	}

	public AlreadyQuoteLineVo getAlreadyQuoteVo() {
		return alreadyQuoteVo;
	}

	public void setAlreadyQuoteVo(AlreadyQuoteLineVo alreadyQuoteVo) {
		this.alreadyQuoteVo = alreadyQuoteVo;
	}

	public String getSearchOrNo() {
		return searchOrNo;
	}

	public void setSearchOrNo(String searchOrNo) {
		this.searchOrNo = searchOrNo;
	}

}
