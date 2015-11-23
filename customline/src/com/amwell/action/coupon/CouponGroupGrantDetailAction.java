package com.amwell.action.coupon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.springframework.util.CollectionUtils;
import com.amwell.action.BaseAction;
import com.amwell.commons.JSONUtil;
import com.amwell.commons.MyDate;
import com.amwell.commons.StringUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ILineService;
import com.amwell.service.IPassengerInfoService;
import com.amwell.service.ISysAreaService;
import com.amwell.service.coupon.ICouponGroupGrantService;
import com.amwell.service.coupon.ICouponGroupPassengerDetailService;
import com.amwell.service.coupon.ICouponGroupService;
import com.amwell.service.coupon.ICouponRuleService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.amwell.vo.app.LineBaseInfo;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.coupon.CouponGroup;
import com.amwell.vo.coupon.CouponGroupGrant;
import com.amwell.vo.coupon.CouponGroupPassengerDetail;
import com.amwell.vo.coupon.CouponRule;

/**
 * 优惠券发放详情
 * @author gxt
 *
 */
@ParentPackage("user-finit")
@Namespace("/couponGroupGrantDetail")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CouponGroupGrantDetailAction extends BaseAction {
	
	private static final Logger log = Logger.getLogger(CouponGroupGrantDetailAction.class);
	
	@Autowired
	private ICouponGroupPassengerDetailService detailService;
	

	/**
	 * 记录数的下标记数，0开始，到10
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize = 15;

	/**
	 * 优惠券发放详情列表
	 */
	@Action(value = "couponGroupGrantDetailList", results = { @Result(name = "success", location = "../../view/coupon/couponGroupGrantDetailList.jsp") })
	public String list(){
		try {
			if(request.getParameter("p")==null){//第一次访问该列表时清空session中的查询条件信息
				session.remove("couponGroupGrantDetailList_Cond");
			}
			/*当前页*/
			if(null!=session.get("myPageIndex")){
				this.currentPageIndex = Integer.valueOf(session.get("myPageIndex").toString());
				super.session.remove("myPageIndex");
			}
			else{
				currentPageIndex = StringUtil.objectToInt(request.getParameter("p")!=null?request.getParameter("p"):"0");
			}
			
			/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
			search = (Search) (search == null?session.get("couponGroupGrantDetailList_Cond"):search);
			if(null!=search&&StringUtils.isNotBlank(search.getField01())){
				//集合对象
				map = detailService.getListByCondition(search,currentPageIndex,pageSize);
				list = (List) map.get("list");//数据对象
				page = (Page) map.get("page");//分页对象
			}
			session.put("couponGroupGrantDetailList_Cond", search);
			
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
