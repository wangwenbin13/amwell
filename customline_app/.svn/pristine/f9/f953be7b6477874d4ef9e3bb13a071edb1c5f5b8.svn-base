package com.pig84.ab.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.cache.UserCache;
import com.pig84.ab.service.ICharteredMyCarService;
import com.pig84.ab.vo.User;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_11_list;
import com.pig84.ab.vo.bean.AppVo_15;
import com.pig84.ab.vo.bean.AppVo_17;
import com.pig84.ab.vo.bean.AppVo_18_list;
import com.pig84.ab.vo.bean.AppVo_1_list;
import com.pig84.ab.vo.bean.AppVo_22_list;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_9;

/**
 * 
 * @author wangwenbin
 *
 * 2014-12-3
 */
/**
 * 包车部分信息
 */
@ParentPackage("no-interceptor")
@Namespace("/app_charteredMyCar")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CharteredMyCarAction extends BaseAction{

	@Autowired
	private ICharteredMyCarService charteredMyCarService;
	
	
	/**
	 * 我的包车---已支付列表
	 */
	@Action(value = "getMyCarHasPayList",results={@Result(type="json")})
	public String getMyCarHasPayList(){
		String pageSize_ = request.getParameter("pageSize");//每页显示条数
		String currentPage_ = request.getParameter("currentPage");//当前页数
		
		int currentPage = 0;
		int pageSize = 5;
		
		if(currentPage_!=null && !currentPage_.equals("")){
			currentPage = Integer.valueOf(currentPage_);
		}
		
		if(pageSize_!=null && !pageSize_.equals("")){
			pageSize = Integer.valueOf(pageSize_);
		}
		User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_1_list vo = new AppVo_1_list();
		String userid = "";
		if (appUser!=null) {
			vo.setA1("1");
			userid = appUser.getPassengerId();
			List<AppVo_6> list = charteredMyCarService.getMyCarHasPayList(userid,String.valueOf(currentPage), String.valueOf(pageSize));
			vo.setList(list);
		}else{
			vo.setA1("0");		//0 ：用户未登录
		}
		write(vo);
		return null;
	}
	
	/**
	 * 我的包车---包车详情
	 */
	@Action(value = "getMyCarOrderDetail",results={@Result(type="json")})
	public String getMyCarOrderDetail(){
		/**包车线路ID**/
		String bcLineId = request.getParameter("bcLineId");
		AppVo_18_list vo = new AppVo_18_list();
		
		User appUser = UserCache.getUser();//当前登录用户信息
		String userid = "";
		if (appUser!=null) {
			userid = appUser.getPassengerId();
			vo = charteredMyCarService.getMyCarOrderDetail(bcLineId);
		}else{
			vo.setA1("0");		//0 ：用户未登录
		}
		
		write(vo);
		return null;
	}
	
	/**
	 * 我的包车---包车详情---需求详情
	 */
	@Action(value = "getMyCarOrderBcLineDetail",results={@Result(type="json")})
	public String getMyCarOrderBcLineDetail(){
		/**包车线路ID**/
		String bcLineId = request.getParameter("bcLineId");
		AppVo_17 vo = new AppVo_17();
		User appUser = UserCache.getUser();//当前登录用户信息
		String userid = "";
		if (appUser!=null) {
			userid = appUser.getPassengerId();
			vo = charteredMyCarService.getMyCarOrderBcLineDetail(bcLineId);
		}else{
			vo.setA1("0");		//0 ：用户未登录
		}
		write(vo);
		return null;
	}
	
	/**
	 * 我的包车---包车详情---报价详情
	 */
	@Action(value = "getMyCarOrderBusinessBidDetail",results={@Result(type="json")})
	public String getMyCarOrderBusinessBidDetail(){
		/**商户报价ID**/
		String businessBid = request.getParameter("businessBid");
		AppVo_22_list vo = new AppVo_22_list();
		User appUser = UserCache.getUser();//当前登录用户信息
		String userid = "";
		if (appUser!=null) {
			userid = appUser.getPassengerId();
			vo = charteredMyCarService.getMyCarOrderBusinessBidDetail(businessBid);
			if(null!=vo){
				/**
				 * 乘客须知
				 */
				String passengerNotice = "";
				passengerNotice += "1,不计入报价的项，若实际运行时，发生此类费用，将在行程结束时现场结算。<br/>";
				passengerNotice += "2,根据相关法规车辆正常使用时间为6:00-2:00（次日），司机工作时间为"+vo.getA9()+"小时，超出8小时之外的时间按"+vo.getA10()+"元/小时补足司机工资。<br/>";
				passengerNotice += "3,本次运行按"+vo.getA11()+"公里/天算，超出公里数按"+vo.getA12()+"元/公里另计；<br/>";
				passengerNotice += "4,本次报价不包含油费，客户用车完毕后与司机将油箱的油料补满。（以油表为准）。<br/>";
				passengerNotice += "5,报价之外的费用需在行程结束后现场支付。<br/>";
				vo.setA20(passengerNotice);
				
				/**
				 * 退票须知
				 */
				String returnNotice = "";
				returnNotice += "1、发车前"+vo.getA13()+"小时不退款；<br/>";
				returnNotice += "2、在发车"+vo.getA14()+"小时前可以申请退款，退款手续费为"+vo.getA15()+"%；<br/>";
				returnNotice += "3、在发车"+vo.getA16()+"小时前可以申请退款，退款手续费为"+vo.getA17()+"%<br/>";
				vo.setA21(returnNotice);
			}
		}else{
			vo.setA1("0");		//0 ：用户未登录
		}
		write(vo);
		return null;
	}
	
	
	/**
	 * 我的包车---历史记录
	 */
	@Action(value = "getMyCarOrderHistoryList",results={@Result(type="json")})
	public String getMyCarOrderHistoryList(){
		String pageSize_ = request.getParameter("pageSize");//每页显示条数
		String currentPage_ = request.getParameter("currentPage");//当前页数
		
		int currentPage = 0;
		int pageSize = 5;
		
		if(currentPage_!=null && !currentPage_.equals("")){
			currentPage = Integer.valueOf(currentPage_);
		}
		
		if(pageSize_!=null && !pageSize_.equals("")){
			pageSize = Integer.valueOf(pageSize_);
		}
		User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_1_list vo = new AppVo_1_list();
		String userid = "";
		if (appUser!=null) {
			vo.setA1("1");
			userid = appUser.getPassengerId();
			List<AppVo_9> list = charteredMyCarService.getMyCarOrderHistoryList(userid,String.valueOf(currentPage), String.valueOf(pageSize));
			vo.setList(list);
		}else{
			vo.setA1("0");		//0 ：用户未登录
		}
		write(vo);
		return null;
	}
	
	/**
	 * 我的包车---历史记录---详情
	 */
	@Action(value = "getMyCarOrderHistoryDetail",results={@Result(type="json")})
	public String getMyCarOrderHistoryDetail(){
    	User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_11_list vl=new AppVo_11_list();
		if(null==appUser){
			vl.setA9("0");//未登录
		}
		else{
			String charteredLineId=request.getParameter("charteredLineId");// 包车id
			//包车详情
			AppVo_15 vo = charteredMyCarService.getCharteredLineDetail(charteredLineId);
			//报价列表
			List<AppVo_3> list=charteredMyCarService.getBusinessBiddingList(charteredLineId);
			vl.setA1(vo.getA1());//起点
			vl.setA2(vo.getA2());//终点
			vl.setA3(vo.getA4());//包车方式
			vl.setA4(vo.getA5());//出发时间
			vl.setA5(vo.getA6());//返程时间
			vl.setA6(vo.getA7());//车龄
			vl.setA7(vo.getA8());//总人数
			vl.setA8(vo.getA13());
			vl.setList(list);
			
			vl.setA9("1");//已登录
			vl.setA10(vo.getA14());//城市名称
			vl.setA11(vo.getA15());//驳回原因
		}
		write(vl);
		return null;
	}
	
	/**
	 * 我的包车---历史记录---删除历史记录
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "delMyCarOrderHistoryList",results={@Result(type="json")})
	public String delMyCarOrderHistoryList(){
		String ids = request.getParameter("ids");//线路ID串
		String userid = "";
		AppVo_1 vo = new AppVo_1();
		User appUser = UserCache.getUser();//当前登录用户信息
		if (appUser!=null) {
			userid = appUser.getPassengerId();
			String flag = charteredMyCarService.delMyCarOrderHistoryList(ids);//1:成功，2：失败
			vo.setA1(flag);
		}else{
			vo.setA1("0");//未登录
		}
		write(vo);
		return null;
	}
}
