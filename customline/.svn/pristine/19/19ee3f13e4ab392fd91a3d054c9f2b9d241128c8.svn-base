package com.amwell.action.coupon;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.amwell.action.BaseAction;
import com.amwell.commons.StringUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.coupon.ICouponGroupService;


/**
 * 优惠券统计
 * @author gxt
 *
 */
@ParentPackage("user-finit")
@Namespace("/couponStatistics")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CouponStatisticsAction extends BaseAction {
	@Autowired
	private ICouponGroupService couponGroupService;

	/**
	 * 记录数的下标记数，0开始，到10
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize = 15;

	/**
	 * 优惠券统计列表
	 */
	@Action(value = "couponStatisticsList", results = { @Result(name = "success", location = "../../view/coupon/couponStatisticsList.jsp") })
	public String list(){
		try {
			if(request.getParameter("p")==null){//第一次访问该列表时清空session中的查询条件信息
				session.remove("couponStatisticsList_Cond");
			}
			/*当前页*/
			currentPageIndex = StringUtil.objectToInt(request.getParameter("p")!=null?request.getParameter("p"):"0");
			/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
			search = (Search) (search == null?session.get("couponStatisticsList_Cond"):search);
			session.put("couponStatisticsList_Cond", search);
			
			//集合对象
			map = couponGroupService.getCouponStatistics(search,currentPageIndex,pageSize);
			list = (List) map.get("list");//数据对象
			page = (Page) map.get("page");//分页对象
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
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
}
