package com.amwell.action.bc;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.entity.Page;
import com.amwell.service.ICharteredOrderService;

/**
 * 
 * @author wangwenbin
 *
 * 2015-3-13
 */
/**
 * 包车优惠券统计
 */
@ParentPackage("user-finit")
@Namespace("/bcCouponStatAction")
public class BcCouponStatAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7209113096299825881L;

	/**
	 * 每页显示的记录数
	 */
	private Integer pageSize = 10;
	
	/**
	 * 记录数的下标起始数 limit 0,10中的0
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 *集合大小 
	 */
	private Integer listSize = 0;
	
	/**
	 * 包车订单
	 */
	@Autowired
	private ICharteredOrderService charteredOrderService;
	
	/**
	 * 包车优惠券列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Action(value = "bcCouponStatList", results = { @Result(name = "success", location = "../../view/bcLine/bcCouponStatList.jsp") })
	public String changeTicketMainPage(){
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		map = charteredOrderService.queryBusCouponStatList(search, currentPageIndex, pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		listSize = list==null?0:list.size();
		return SUCCESS;
		
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
	
}
