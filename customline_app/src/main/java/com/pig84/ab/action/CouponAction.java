package com.pig84.ab.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.cache.UserCache;
import com.pig84.ab.dao.ICouponRuleDao;
import com.pig84.ab.service.ICouponService;
import com.pig84.ab.service.IPassengerInfoService;
import com.pig84.ab.utils.ApplicationContextHolder;
import com.pig84.ab.utils.Event;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.User;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_2_list;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.coupon.GrantInfo;

/**
 * 新版优惠券相关
 * 
 * @author gongxueting
 * 
 */
@ParentPackage("no-interceptor")
@Namespace("/app_coupon")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CouponAction  extends BaseAction {
	
	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private IPassengerInfoService passengerService;
	
	/**
	 * 发送优惠券
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "sendCoupon",results={@Result(type="json")})
	public String sendCoupon(){
		try {
			String telephone = request.getParameter("telephone");
			//查询所有用户
			List<PassengerInfo> passengerList=new ArrayList<PassengerInfo>();
			PassengerInfo appUser=passengerService.getPassengerById(null, telephone);
			passengerList.add(appUser);
			//查询所有符合条件的规则
			ICouponRuleDao couponRuleDao=(ICouponRuleDao) ApplicationContextHolder.getBean("couponRuleDaoImpl");
//			List<GrantInfo> grantList=couponRuleDao.getGrantInfo(null);
			/*if((null!=passengerList&&passengerList.size()>0)&&(null!=grantList&&grantList.size()>0)){
				//发放优惠券
				int flag=0;
				for (GrantInfo grantInfo : grantList) {
					flag=grantInfo.promote(passengerList);
					if(flag==-2){
						System.out.println("批次号："+grantInfo.getCouponGrant().getCouponList().get(0).getCouponGroupId()+"，用户不满足发放规则");
					}
				}
				System.out.println("发放成功");
			}
			else{
				System.out.println("暂无待发放优惠券或用户已无领取机会");
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		write(1);
		return null;
	}
	

	/**
	 * 查询在有效期内的可用优惠券列表
	 *   . 按结束时间由近到远排序
	 *   . 相同时间的，按优惠券价格排序，由小到大
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getUsableCoupon",results={@Result(type="json")})
	public String getUsableCoupon(){
		return myPublicGetMethod("1");
	}
	
	/**
	 * 查询未到有效期开始时间的优惠券列表
	 *    . 按开始时间由近到远
	 *    . 相同时间的，按优惠券价格排序，由小到大
	 *
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getUnStartCoupon",results={@Result(type="json")})
	public String getUnStartCoupon(){
		return myPublicGetMethod("2");
	}
	
	/**
	 * 查询已过期的优惠券列表
	 *    . 按结束时间由近到远
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getExpiredCoupon",results={@Result(type="json")})
	public String getExpiredCoupon(){
		return myPublicGetMethod("3");
	}
	
	private String myPublicGetMethod(String type){
		
		int currentIndex = 0;//从第几个开始取
		if(!(null==request.getParameter("currentIndex")||""==request.getParameter("currentIndex"))){
			currentIndex = Integer.parseInt(request.getParameter("currentIndex"));
		}
		int theCount = 0;//取多少条数据
		if(!(null==request.getParameter("theCount")||"".equals(request.getParameter("theCount")))){
			theCount=Integer.parseInt(request.getParameter("theCount"));
		}
		
		String uid = request.getParameter("uid");//微信端传过来的用户id
		
		AppVo_2_list vo=new AppVo_2_list();
		List<AppVo_6> list=null;
		
		if(StringUtils.isNotBlank(uid)){
			if(type.equals("1")){//查询有效期内的优惠券
				list=couponService.getMyUsableCoupon(uid,null,currentIndex,theCount);
			}
			if(type.equals("2")){//查询未开始的优惠券
				list=couponService.getUnStartCoupon(uid,null,currentIndex,theCount);
			}
			if(type.equals("3")){//查询已过期的优惠券
				list=couponService.getExpiredCoupon(uid,null,currentIndex,theCount);
			}
		}
		else{
			User appUser = UserCache.getUser();//当前登录用户信息
			if(null==appUser){
				vo.setA1("0");//未登录
			}
			else{
				vo.setA1("1");//已登录
				vo.setA2(MyDate.Format.DATE.now());//系统当前日期
				String telephone=appUser.getTelephone();// 登录用户电话号码
				//String telephone="13028872323";// 登录用户电话号码
				if(type.equals("1")){//查询有效期内的优惠券
					list=couponService.getMyUsableCoupon(null,telephone,currentIndex,theCount);
				}
				if(type.equals("2")){//查询未开始的优惠券
					list=couponService.getUnStartCoupon(null,telephone,currentIndex,theCount);
				}
				if(type.equals("3")){//查询已过期的优惠券
					list=couponService.getExpiredCoupon(null,telephone,currentIndex,theCount);
				}
			}
		}
		
		vo.setList(null==list?new ArrayList<AppVo_6>():list);
		
		write(vo);
		return null;
	}
	
	/**
	 * 获取优惠券
	 */
	@Action(value = "getCoupon",results={@Result(type="json")})
	public String getCoupon(){
		AppVo_1 vo=new AppVo_1();
		User appUser = UserCache.getUser();//当前登录用户信息
		if(null==appUser){
			vo.setA1("0");//未登录
		}
		else{
			vo.setA1("1");//已登录
			Map<String, Object> data = new HashMap<>();
			PassengerInfo user = new PassengerInfo();
			user.setPassengerId(appUser.getPassengerId());
			user.setTelephone(appUser.getTelephone());
//			user.setPassengerId("1510131047124652");
//			user.setTelephone("18126194229");
			data.put("user", user);
			String gameName=request.getParameter("gameName");
			if("爱游游2.0".equals(gameName)){
				gameName="AYY";
			}
			else if("泡椒游侠".equals(gameName)){
				gameName="PJYX";
			}
			else if("360手机卫士".equals(gameName)){
				gameName="SJWS";
			}
			if("PP助手".equals(gameName)){
				gameName="PPZS";
			}
			else if("轻听".equals(gameName)){
				gameName="QT";
			}
			else if("觅恋".equals(gameName)){
				gameName="ML";
			}
			else if("节操精选".equals(gameName)){
				gameName="JCJX";
			}
			else if("全民射水果".equals(gameName)){
				gameName="QMSSG";
			}
			else if("天天斗地主".equals(gameName)){
				gameName="TTDDZ";
			}
			else if("梦幻神魔".equals(gameName)){
				gameName="MHSM";
			}
			else if("我欲修仙".equals(gameName)){
				gameName="WYXX";
			}
			else if("战龙之刃".equals(gameName)){
				gameName="ZLZR";
			}
			else if("曙光之战".equals(gameName)){
				gameName="SGZZ";
			}
			data.put("gameName", gameName);
			Event.SINGLEHANDLE.triggerAsynchronous(data);
		}
	    write(vo);
		return null;
	}
	
	/**
	 * 统计与泡椒游戏相关的已发放优惠券总金额，判断是否终止活动
	 */
	@Action(value = "couponValStatistics",results={@Result(type="json")})
	public String couponValStatistics(){
		try {
			AppVo_1 vo=this.couponService.getCouponValTotal();
			if(null==vo){
				vo=new AppVo_1();
				vo.setA1("0.00");
			}
			BigDecimal bd=new BigDecimal(vo.getA1());
			//泡椒游戏发放优惠券总金额
			String couponTotalVal = PropertyManage.get("coupon_total_val");
			if(bd.compareTo(new BigDecimal(couponTotalVal))>=0){
				vo.setA1("1");//停止活动
			}
			else{
				vo.setA1("0");//不停止活动
			}
		    write(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
