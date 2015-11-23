package com.amwell.action.coupon;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.amwell.service.coupon.ICouponGroupService;
import com.amwell.service.coupon.ICouponInfoService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.coupon.CouponGroup;
import com.amwell.vo.coupon.CouponGroupGrant;
import com.amwell.vo.coupon.CouponInfo;


/**
 * 组合券信息
 * @author gxt
 *
 */
@ParentPackage("user-finit")
@Namespace("/couponGroup")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CouponGroupAction extends BaseAction {
	@Autowired
	private ICouponGroupService couponGroupService;
	
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
	
	private CouponGroup couponGroup;

	/**
	 * 组合券列表
	 */
	@Action(value = "couponGroupList", results = { @Result(name = "success", location = "../../view/coupon/couponGroupList.jsp") })
	public String list(){
		try {
			if(request.getParameter("p")==null){//第一次访问该列表时清空session中的查询条件信息
				session.remove("couponGroupList_Cond");
			}
			/*当前页*/
			currentPageIndex = StringUtil.objectToInt(request.getParameter("p")!=null?request.getParameter("p"):"0");
			/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
			search = (Search) (search == null?session.get("couponGroupList_Cond"):search);
			if(null==search){
				//获取用户的登陆信息
				SysAdminVo admin = (SysAdminVo) session.get("userInfo");
				search=new Search();
				search.setField04(admin.getUserName());
			}
			session.put("couponGroupList_Cond", search);
			
			//集合对象
			map = couponGroupService.getListByCondition(search,currentPageIndex,pageSize);
			list = (List) map.get("list");//数据对象
			page = (Page) map.get("page");//分页对象
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入组合券添加页
	 */
	@Action(value = "toCouponGroupAddPage", results = { @Result(name = "success", location = "../../view/coupon/couponGroupAdd.jsp") })
	public String toAddPage(){
		try {
			//组合券批次号
			String couponGroupId=couponGroupService.getCouponGroupID();
			String currentDate=MyDate.getMyDateForOrder();//格式150722
			if(null==couponGroupId){
				couponGroupId=currentDate+"0001";
			}
			else{
				String temp=couponGroupId.substring(0,6);
				if(Integer.parseInt(temp)==Integer.parseInt(currentDate)){
					int num=Integer.parseInt(couponGroupId.substring(6,10))+1;
					if(num<10){
						couponGroupId=currentDate+"000"+num;
					}
					else if(num>=10&&num<100){
						couponGroupId=currentDate+"00"+num;
					}
					else if(num>=100&&num<1000){
						couponGroupId=currentDate+"0"+num;
					}
					else if(num>=1000&&num<10000){
						couponGroupId=currentDate+num;
					}
					else{
						couponGroupId="-1";
					}
				}
				else{
					couponGroupId=currentDate+"0001";
				}
			}
			
			request.setAttribute("couponGroupID",couponGroupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 添加组合券
	 * @return
	 * @throws Exception
	 */
	@Action(value="couponGroupAdd",results = { @Result( type = "json") })
	public String couponGroupAdd()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		String effectiveTime = request.getParameter("effectiveTime");
		String expirationTime = request.getParameter("expirationTime");
		String delayDays = request.getParameter("delayDays");
		String couponGroupCount=request.getParameter("couponGroupCount");
		String averageNum=request.getParameter("averageNum");
		
		String[] couponName=request.getParameterValues("couponName");
		String[] couponValue=request.getParameterValues("couponValue");
		String[] couponCon=request.getParameterValues("couponCon");
		String[] num=request.getParameterValues("num");
		
		String couponGroupID=request.getParameter("couponGroupID");
		
		//获取用户的登陆信息
		SysAdminVo admin = (SysAdminVo) session.get("userInfo");

		if(couponName.length>0){
			int flag=0;
			this.couponGroup=new CouponGroup();
			this.couponGroup.setCouponGroupID(couponGroupID);
			this.couponGroup.setCouponGroupName(couponGroupID+"组合券");
			this.couponGroup.setCouponGroupCount(couponGroupCount);
			this.couponGroup.setAverageNum(averageNum);
			this.couponGroup.setCreateBy(admin.getUserId());
			this.couponGroup.setCreateOn(MyDate.getMyDateLong());
			
			synchronized (CouponGroupAction.class) {//添加同步锁
				//添加组合券
				flag=couponGroupService.updateCouponGroup(this.couponGroup);
				if(flag==-1){
					pw.print(flag);
					return null;
				}
			}
			
			CouponInfo coupon=null;
			for (int i = 0; i < couponName.length; i++) {				
				coupon=new CouponInfo();
				coupon.setCouponName(couponName[i]);
				coupon.setCouponValue(couponValue[i]);
				coupon.setCouponCon(couponCon[i]);
				
				if(null!=effectiveTime&&!"".equals(effectiveTime)){
					coupon.setEffectiveTime(effectiveTime);
					coupon.setExpirationTime(expirationTime);
				}
				if(null!=delayDays&&!"".equals(delayDays)){
					
					coupon.setDelayDays(delayDays);
				}
				coupon.setCreateBy(admin.getUserId());
				coupon.setCreateOn(MyDate.getMyDateLong());
				coupon.setCouponGroupId(couponGroupID);
				coupon.setCouponGroupName(couponGroupID+"组合券");
				coupon.setNum(num[i]);
//				coupon.setNum("1");
				coupon.setCouponCount(Integer.parseInt(num[i])*Integer.parseInt(couponGroupCount)+"");
				
				synchronized (CouponGroupAction.class) {//添加同步锁
					//添加优惠券
					flag=couponService.updateCoupon(coupon);
					if(flag==-1){
						pw.print(flag);
						return null;
					}
				}
			}
			pw.print(flag);
			return null;
		}
		pw.print("0");
		return null;
	}
	
	
	/**
	 * 优惠券数据迁移
	 * @return
	 * @throws Exception
	 */
	@Action(value="updateData",results = { @Result( type = "json") })
	public String updateData()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		String flag=this.couponService.updateData();

		if(flag.equals("1")){
			pw.print("优惠券数据迁移成功");
		}
        if(flag.equals("-2")){
        	pw.print("优惠券数据迁移执行过，不能重复执行");
		}
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

	public CouponGroup getCouponGroup() {
		return couponGroup;
	}

	public void setCouponGroup(CouponGroup couponGroup) {
		this.couponGroup = couponGroup;
	}
}
