package com.amwell.action.order;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.entity.Page;
import com.amwell.service.ILeaseOrderService;

/**
 * 
 * @author wangwenbin
 *
 * 2015-3-13
 */
/**
 * 上下班优惠券统计
 */
@ParentPackage("user-finit")
@Namespace("/busCouponStatAction")
public class BusCouponStatAction extends BaseAction{

	
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
	 *集合大小 
	 */
	private Integer listSize = 0;
	
	/**
	 * 上下班优惠券列表
	 * @return
	 */
	@Action(value = "busCouponStatList", results = { @Result(name = "success", location = "../../view/order/busCouponStatList.jsp") })
	public String changeTicketMainPage(){
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		map = iLeaseOrderService.queryBusCouponStatList(search, currentPageIndex, pageSize);
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
