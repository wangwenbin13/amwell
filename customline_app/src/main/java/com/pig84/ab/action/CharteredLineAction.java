package com.pig84.ab.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.pig84.ab.alipay.AlipayNotify;
import com.pig84.ab.cache.UserCache;
import com.pig84.ab.service.IAliPayService;
import com.pig84.ab.service.IBookService;
import com.pig84.ab.service.ICharteredLineService;
import com.pig84.ab.service.ICouponService;
import com.pig84.ab.utils.Html;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.BcOrderEntiry;
import com.pig84.ab.vo.CharteredLineVo;
import com.pig84.ab.vo.User;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_11_list;
import com.pig84.ab.vo.bean.AppVo_15;
import com.pig84.ab.vo.bean.AppVo_18;
import com.pig84.ab.vo.bean.AppVo_1_list;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_5;
import com.pig84.ab.vo.bean.AppVo_9;

/**
 * 包车相关
 * @author 
 *
 */
@ParentPackage("no-interceptor")
@Namespace("/app_chartered")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CharteredLineAction extends BaseAction {

	@Autowired
	private ICharteredLineService charteredLineService;
	
	@Autowired
	private IBookService bookService;
	
	@Autowired
	private IAliPayService aliPayService;//支付宝
	
	@Autowired
	private ICouponService giftService;
	
	/**
	 * 发布包车信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "saveCharteredLine",results={@Result(type="json")})
	public String saveCharteredLine(){
		User appUser = UserCache.getUser();
		AppVo_2 vo = new AppVo_2();
		if(null==appUser){
			vo.setA1("3");//3.表示未登录
		}
		else{
			String startTime=request.getParameter("startTime");// 发车时间 时间格式 2014-11-02 19:00
			int result=MyDate.compare_date_time(startTime+":00", MyDate.Format.DATETIME.now());
			if(result>-1){//开始时间不小于当前时间
				String passengerId=appUser.getPassengerId();// 乘客ID，外健
				
			    String savaType=request.getParameter("savaType");//1.新加  2.覆盖
			    String bcLineId=request.getParameter("bcLineId");//包车线路id（覆盖的时候用）
			    
				int businessType=1;// 业务类型，1:活动包车 2:商务接送 3:过港接送
				if(!(null==request.getParameter("businessType")||""==request.getParameter("businessType"))){
					businessType=Integer.parseInt(request.getParameter("businessType"));
				}
				String beginAddress=request.getParameter("beginAddress");// 起点
				String endAddress=request.getParameter("endAddress");// 终点
				String journeyRemark=request.getParameter("journeyRemark");// 行程备注
				journeyRemark = Html.purge(journeyRemark);
				BigDecimal expectPrice=new BigDecimal("0.00");// 预期价格
				int charteredMode=1;// 包车方式，1：单趟 2:往返 3:包天
				if(!(null==request.getParameter("charteredMode")||""==request.getParameter("charteredMode"))){
					charteredMode=Integer.parseInt(request.getParameter("charteredMode"));
				}

				String returnTime=request.getParameter("returnTime");// 返程时间，对于单趟包车方式，此字段为空 时间格式 2014-11-02 19:00
				int carAge=0;// 车龄 0:不限 1:1年以内 2:3年以内 3:5年以内
				if(!(null==request.getParameter("carAge")||""==request.getParameter("carAge"))){
					carAge=Integer.parseInt(request.getParameter("carAge"));
				}
				int totalPassengers=0;// 总人数
				if(!(null==request.getParameter("totalPassengers")||""==request.getParameter("totalPassengers"))){
					totalPassengers=Integer.parseInt(request.getParameter("totalPassengers"));
				}
				int totalCars=0;// 总车辆数
				if(!(null==request.getParameter("totalCars")||""==request.getParameter("totalCars"))){
					totalCars=Integer.parseInt(request.getParameter("totalCars"));
				}
				int needInvoice=0;// 0:不需要发票 1:需要发票
				if(!(null==request.getParameter("needInvoice")||""==request.getParameter("needInvoice"))){
					needInvoice=Integer.parseInt(request.getParameter("needInvoice"));
				}
				String invoiceHeader=request.getParameter("invoiceHeader");// 发票抬头
				if(!StringUtils.isEmpty(invoiceHeader)){
					invoiceHeader = Html.purge(invoiceHeader);
				}
				String contacts=request.getParameter("contacts");// 联系人
				String contactPhone=request.getParameter("contactPhone");// 联系电话
				
				int lineStatus=0;// 线路状态 0:待审核 1：未通过 2:待报价 3：已报价 4:已过期 5:已删除 6:已支付
				
				//过期时间存时间格式，页面倒计时用过期时间减去系统时间得出
				String hours = charteredLineService.getExpireTimeSet().getA1();
				Date d = DateUtils.addHours(new Date(), Integer.valueOf(hours));
				String expireTime = MyDate.Format.DATETIME.format(d);// 过期时间

				String provinceCode=request.getParameter("provinceCode");//省级编码
				String cityCode=request.getParameter("cityCode");//市级编码
				String cityName=request.getParameter("cityName");//城市名称
				
				CharteredLineVo charteredLine=new CharteredLineVo();
				charteredLine.setBusinessType(businessType);
				charteredLine.setBeginAddress((null==beginAddress||"".equals(beginAddress))?null:beginAddress.trim());
				charteredLine.setEndAddress((null==endAddress||"".equals(endAddress))?null:endAddress.trim());
				charteredLine.setJourneyRemark(journeyRemark);
				charteredLine.setExpectPrice(expectPrice);
				charteredLine.setCharteredMode(charteredMode);
				charteredLine.setStartTime(startTime);
				charteredLine.setReturnTime(returnTime);
				charteredLine.setCarAge(carAge);
				charteredLine.setTotalPassengers(totalPassengers);
				charteredLine.setTotalCars(totalCars);
				charteredLine.setNeedInvoice(needInvoice);
				charteredLine.setInvoiceHeader(invoiceHeader);
				charteredLine.setContacts(contacts);
				charteredLine.setContactPhone(contactPhone);
				charteredLine.setPassengerId(passengerId);
				charteredLine.setLineStatus(lineStatus);
				charteredLine.setCreateOn(MyDate.Format.DATETIME.now());
				charteredLine.setExpireTime(expireTime);
				charteredLine.setProvinceCode(provinceCode);
				charteredLine.setCityCode(cityCode);
				charteredLine.setCityName(cityName);
				
				String flag = charteredLineService.saveCharteredLine(charteredLine,savaType,bcLineId);
				
				if("1".equals(flag)||"2".equals(flag)){
					//成功
					vo.setA1(flag);
					int theResult=0;
					if("1".equals(flag)){
						//查询当天乘客已发布的包车信息条数
						int count=charteredLineService.getCharteredLineCount(charteredLine.getPassengerId());
						//从配置文件获取发布条数
						String charteredLineNum=PropertyManage.get("charteredLineNum");
						int num=(null==charteredLineNum||"".equals(charteredLineNum))?5:Integer.parseInt(charteredLineNum);
						theResult=num-count;
					}
					vo.setA2(theResult+"");//返回当天还可以发布的包车信息条数
				}else{
					//失败
					vo.setA1("0");
				}
			}
			else{
				//开始时间小于当前时间
				vo.setA1("-1");
			}
		}
		write(vo);
		return null;
	}
	
	/**
	 * 查询过期时间配置
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getExpireTimeSet",results={@Result(type="json")})
	public String getExpireTimeSet(){
    	AppVo_1 vo = charteredLineService.getExpireTimeSet();
    	write(vo);
		return null;
	}
	
	
	
	/**
	 * 查询登录用户已发布的包车列表
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getCharteredLineList",results={@Result(type="json")})
	public String getCharteredLineList(){
    	User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_1_list vl=new AppVo_1_list();
		if(null==appUser){
			vl.setA1("0");//未登录
		}
		else{
			int pageSize = 5;//每页显示条数
			if(!(null==request.getParameter("pageSize")||""==request.getParameter("pageSize"))){
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
			}
			int currentPage = 0;//当前页数
			if(!(null==request.getParameter("currentPage")||"".equals(request.getParameter("currentPage")))){
				currentPage=Integer.parseInt(request.getParameter("currentPage"));
			}
			
			vl.setA1("1");//已登录
			
			List<AppVo_9> list=charteredLineService.getCharteredLineList(appUser.getPassengerId(),"0,1,2,3",pageSize,currentPage);//0“待审核”、1“审核未通过”、2“待报价”、3“已报价”
			vl.setList(list);
		}
		write(vl);
		return null;
	}
	
	/**
	 * 查询包车需求详情
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getCharteredLineDetail",results={@Result(type="json")})
	public String getCharteredLineDetail(){
    	User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_15 vo=new AppVo_15();
		if(null==appUser){
			vo.setA14("0");//未登录
		}
		else{
			String charteredLineId=request.getParameter("charteredLineId");// 包车id
			vo = charteredLineService.getCharteredLineDetail(charteredLineId);
			vo.setA14("1");//已登录
		}
		write(vo);
		return null;
	}
	
	/**
	 * 查询已有报价包车的商家报价列表
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getBusinessBiddingList",results={@Result(type="json")})
	public String getBusinessBiddingList(){
    	User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_11_list vl=new AppVo_11_list();
		if(null==appUser){
			vl.setA9("0");//未登录
		}
		else{
			String charteredLineId=request.getParameter("charteredLineId");// 包车id
			//包车详情
			AppVo_15 vo = charteredLineService.getCharteredLineDetail(charteredLineId);
			//报价列表
			List<AppVo_3> list=charteredLineService.getBusinessBiddingList(charteredLineId);
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
	 * 查询商家报价详情
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getBcBiddingDetail",results={@Result(type="json")})
	public String getBcBiddingDetail(){
    	User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_18 vo=new AppVo_18();
		if(null==appUser){
			vo.setA15("0");//未登录
		}
		else{
			String bcBiddingId=request.getParameter("bcBiddingId");// 报价id
			vo = charteredLineService.getBcBiddingDetail(bcBiddingId,appUser.getPassengerId());
			vo.setA15("1");//已登录
		}
		write(vo);
		return null;
	}
	
	/**
	 * 支付报价
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "payTotalCost",results={@Result(type="json")})
	public String payTotalCost(){
    	String bcLineId = request.getParameter("bcLineId");//包车线路ID
		String businessId = request.getParameter("businessId");//竞价商家ID
		String totalCost = request.getParameter("totalCost");//报价总价
		String userid = "";//用户ID
		User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_5 vo = new AppVo_5();
		if(appUser!=null){
			vo.setA1("1");//已登录
			userid =  appUser.getPassengerId();
			String money = bookService.getUserBalance(userid);//用户余额
			vo.setA2(totalCost);//报价总价
			vo.setA3(money);//用户余额
			vo.setA4(bcLineId);
			vo.setA5(businessId);
		}else{
			vo.setA1("0");//未登录
		}
		write(vo);
		return null;
	}
	
	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
	
	/**
	 * 支付宝支付返回结果（支付） ****包车****
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "monitorAliPayBcResult_pay",results={@Result(type="json")})
	public String monitorAliPayBcResult_pay(){
        try {
        	//获取支付宝POST过来反馈信息
    		Map<String,String> params = new HashMap<String,String>();
    		Map requestParams = request.getParameterMap();
    		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
    			
    			
    			String name = (String) iter.next();
    			String[] values = (String[]) requestParams.get(name);
    			String valueStr = "";
    			for (int i = 0; i < values.length; i++) {
    				valueStr = (i == values.length - 1) ? valueStr + values[i]
    						: valueStr + values[i] + ",";
    			}
    			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
    			params.put(name, valueStr);
    		}
    		
    		//支付宝交易号
    		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
    		
    		//报价id
    		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
    		
    		String subject = new String(request.getParameter("subject").getBytes("ISO-8859-1"),"UTF-8");
    		//获取用户优惠券id
    		String counponTeleId=(null==subject||"".equals(subject))?null:(subject.contains("id:")?subject.split("id:")[1]:null);
    		logger.info("优惠券编号: {}", counponTeleId);
    		//交易状态
    		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
    		

    		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
    		HttpServletResponse response = ServletActionContext.getResponse();
    		response.setContentType("text/html;charset=UTF-8");

    		if(AlipayNotify.verify(params)){//验证成功
    			//////////////////////////////////////////////////////////////////////////////////////////
    			//请在这里加上商户的业务逻辑程序代码

    			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
    			
    			if(trade_status.equals("TRADE_FINISHED")){
    				//判断该笔订单是否在商户网站中已经做过处理
    					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
    					//如果有做过处理，不执行商户的业务程序
    				
    				//注意：
    				//该种交易状态只在两种情况下出现
    				//1、开通了普通即时到账，买家付款成功后。
    				//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
    			} else if (trade_status.equals("TRADE_SUCCESS")){
    				
    				//判断该笔订单是否在商户网站中已经做过处理
    					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
    					//如果有做过处理，不执行商户的业务程序

    				//保存订单信息
    				String flag=toDoSaveBcOrder(out_trade_no,1,counponTeleId);//支付宝支付
    				// XXX Extract all getWriter().println to somewhere
    				if(!"1".equals(flag)){
    					response.getWriter().println("fail");
    				}
    				//注意：
    				//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
    			}
    			response.getWriter().println("success");	//请不要修改或删除
    			//////////////////////////////////////////////////////////////////////////////////////////
    		}else{//验证失败
    			response.getWriter().println("fail");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**封装包车订单信息并保存订单**/
	private String toDoSaveBcOrder(String out_trade_no,Integer payModel,String counponTeleId){
			
		//根据报价id查询报价总价
		AppVo_4 vo=bookService.getBcBiddingById(out_trade_no);
		BigDecimal totalPrice=new BigDecimal(vo.getA3());

		BcOrderEntiry order=new BcOrderEntiry();
		order.setBc_line_id(vo.getA1());//包车信息id
		order.setBusinessId(vo.getA2());//商家id
		order.setPassengerId(vo.getA4());//登录用户id
		order.setPrice(totalPrice);//总价
		order.setOrderStatus(1);//订单状态,1:待调度 2：已调度(同时输入司机、车辆) 3：已过期
		order.setPayModel(payModel);//乘客支付方式，0.无 1:支付宝 2：银联
		order.setPayTime(MyDate.Format.DATETIME.now());
		
		if(null!=counponTeleId&&!"".equals(counponTeleId)){
			//根据优惠券id查询优惠券金额
			AppVo_1 vo_1=giftService.getCouponValueById(counponTeleId);
			BigDecimal giftValue=new BigDecimal(vo_1.getA1());
			BigDecimal thePrice=null;
			BigDecimal giftMoney=null;
			BigDecimal giftLeftMoney=null;
			if(totalPrice.compareTo(giftValue)==1){//总价大于优惠券面值
				thePrice=totalPrice.subtract(giftValue);
				giftMoney=giftValue;
				giftLeftMoney=new BigDecimal("0");
			}
			else{//总价小于等于优惠券面值
				thePrice=new BigDecimal("0");
				giftMoney=totalPrice;
				giftLeftMoney=giftValue.subtract(totalPrice);
			}
			
			order.setTotalPrice(thePrice);//车票单价(实际支付)
			order.setCouponGiftId(counponTeleId);//优惠ID
			order.setGiftMoney(giftMoney);//优惠金额
			order.setGiftLeftMoney(giftLeftMoney);//优惠券未使用金额
		}
		else{
			order.setTotalPrice(totalPrice);//车票单价(实际支付)
		}
		
		return charteredLineService.saveBcOrder(order);
//		return null;
	}
	
	/**
	 * 支付金额为0元时，直接保存订单信息
	 * @return
	 * @throws IOException
	 */
	@Action(value = "saveBcOrder",results={@Result(type="json")})
	public String saveBcOrder(){
    	User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_2 vo=new AppVo_2();
		if(null==appUser){
			vo.setA1("0");//未登录
		}
		else{
			vo.setA1("1");//已登录
			
			String bcBusinessBiddingId=request.getParameter("bcBusinessBiddingId");//商家报价id
			String counponTeleId=request.getParameter("counponTeleId");//优惠券id
			
			String flag=toDoSaveBcOrder(bcBusinessBiddingId,0,counponTeleId);//0表示无支付方式
			vo.setA2(flag);
		}
		write(vo);
		return null;
	}
	
	/**
	 * 查询商家介绍信息
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getBusinessRemark",results={@Result(type="json")})
	public String getBusinessRemark(){
    	User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_2 vo=new AppVo_2();
		if(null==appUser){
			vo.setA2("0");//未登录
		}
		else{
			String businessId=request.getParameter("businessId");//商家id
			vo = charteredLineService.getBusinessRemark(businessId);
			vo.setA2("1");//已登录
		}
		write(vo);
		return null;
	}
	
	/**
	 * 批量删除已发布列表中的包车信息
	 * @return
	 * @throws IOException
	 */
	@Action(value = "lineBatchDel",results={@Result(type="json")})
	public String lineBatchDel(){
        	User appUser = UserCache.getUser();//当前登录用户信息
    		AppVo_1 vo=new AppVo_1();
    		if(appUser==null){
    			vo.setA1("0");//未登录
    		}
    		else{
    			String bcLineIds=request.getParameter("bcLineIds");//一到多个包车线路id，用逗号分割连接
    			int flag = charteredLineService.updateLineStatus(bcLineIds,5);//5,已删除
    			if(flag>0){
    				vo.setA1("1");//成功
    			}
    			else{
    				vo.setA1("2");//失败
    			}
    		}
		write(vo);
		return null;
	}
	
	/**
	 * 查询已开通包车业务的城市信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getBcCityList",results={@Result(type="json")})
	public String getBcCityList(){
    	List<AppVo_3> list=charteredLineService.getBcCityList();
		if(list==null || list.size()==0){
			AppVo_3 vo_3=new AppVo_3();
			vo_3.setA1("19");
			vo_3.setA2("1607");
			vo_3.setA3("深圳市");
			list.add(vo_3);
		}
		write(list);
		return null;
	}
	
	/**
	 * 查询包车协议
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getUserAgreement",results={@Result(type="json")})
	public String getUserAgreement(){
    	User appUser = UserCache.getUser();//当前登录用户信息
		AppVo_2 vo=new AppVo_2();
		if(null==appUser){
			vo.setA1("0");//未登录
		}
		else{
			vo = charteredLineService.getUserAgreement();
			vo.setA1("1");//已登录
		}
		write(vo);
		return null;
	}
}