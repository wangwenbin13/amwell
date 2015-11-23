package com.amwell.action.coupon;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.amwell.action.BaseAction;
import com.amwell.commons.MyDate;
import com.amwell.commons.StringUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.coupon.ICouponInfoService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.coupon.CouponInfo;


/**
 * 优惠券信息
 * @author gxt
 *
 */
@ParentPackage("user-finit")
@Namespace("/couponInfo")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CouponInfoAction extends BaseAction {
	@Autowired
	private ICouponInfoService couponService;

	/**
	 * 记录数的下标记数，0开始，到10
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize = 10;
	
	private CouponInfo couponInfo;

	/**
	 * 优惠券列表
	 */
	@Action(value = "couponInfoList", results = { @Result(name = "success", location = "../../view/coupon/couponList.jsp") })
	public String list(){
		try {
			if(request.getParameter("p")==null){//第一次访问该列表时清空session中的查询条件信息
				session.remove("couponInfoList_Cond");
			}
			/*当前页*/
			currentPageIndex = StringUtil.objectToInt(request.getParameter("p")!=null?request.getParameter("p"):"0");
			/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
			search = (Search) (search == null?session.get("couponInfoList_Cond"):search);
			session.put("couponInfoList_Cond", search);
			//集合对象
			map = couponService.getListByCondition(search,currentPageIndex,pageSize);
			list = (List) map.get("list");//数据对象
			page = (Page) map.get("page");//分页对象
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入优惠券添加页
	 */
	@Action(value = "toCouponAddPage", results = { @Result(name = "success", location = "../../view/coupon/couponAdd.jsp") })
	public String toAddPage(){
		return SUCCESS;
	}
	
	/**
	 * 添加优惠券
	 * @return
	 * @throws Exception
	 */
	@Action(value="couponAdd",results = { @Result( type = "json") })
	public String couponAdd()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		//获取用户的登陆信息
		SysAdminVo admin = (SysAdminVo) session.get("userInfo");
		
		this.couponInfo.setCreateBy(admin.getUserId());
		this.couponInfo.setCreateOn(MyDate.getMyDateLong());
		int flag=couponService.updateCoupon(this.couponInfo);

		pw.print(flag);
		return null;
	}
	
	/**
	 * 删除优惠券
	 * @return
	 * @throws Exception
	 */
	@Action(value="couponDel",results = { @Result( type = "json") })
	public String couponDel()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		String couponId=request.getParameter("couponId");
		int flag=couponService.delCoupon(couponId);

		pw.print(flag);
		return null;
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

	public CouponInfo getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(CouponInfo couponInfo) {
		this.couponInfo = couponInfo;
	}
}
