package com.pig84.ab.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.cache.UserCache;
import com.pig84.ab.service.IBookService;
import com.pig84.ab.service.ICharteredLineService;
import com.pig84.ab.service.ILoginAndRegisterService;
import com.pig84.ab.service.IPayService;
import com.pig84.ab.service.IWeiXinPayService;
import com.pig84.ab.utils.Http;
import com.pig84.ab.utils.Message;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PayReturnUtils;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.LeaseBaseInfo;
import com.pig84.ab.vo.User;
import com.pig84.ab.vo.Payment.OrderRRZ;
import com.pig84.ab.vo.Payment.PayBean;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_5;

/**
 * 支付相关
 * @author zhangqiang
 * 
 */
@ParentPackage("no-interceptor")
@Namespace("/app_payNew")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class PaymentAction extends BaseAction{
	
	@Autowired
	private IPayService payService;
	
	@Autowired
	private ICharteredLineService charteredService;
	
	@Autowired
	private IBookService bookService;
	

	@Autowired
	private IWeiXinPayService weiXinPayService;


	@Autowired
	private ILoginAndRegisterService loginAndRegisterService;
	
	/**
	 * 获取支付订单详细
	 * @param orderNo 订单号
	 * @return 
	 */
	@Action(value = "getOrderInfo", results = { @Result(type = "json") })
	public String getOrderInfo(){
		String orderNo = request.getParameter("orderNo");//订单号
		PayBean info = new PayBean();
		if(StringUtils.isEmpty(orderNo)){
			return null;
		}
		if(orderNo.contains("BS")){//巴士支付
			LeaseBaseInfo order = payService.getLeaseBaseInfoByOrderNo(orderNo);
			if(order==null){return null;}//商品判空
			info.setOrderNo(orderNo);//商品订单号
			info.setLimitTime(MyDate.addTimeByMin(MyDate.Format.DATETIME.now(),30));//商品支付有效时间
			info.setDesc("小猪上下班");//商品描述
			info.setTitle("车票支付");//商品标题
			info.setPrice(order.getRealprice());//商品价格
		}else{//包车支付
			AppVo_2 vo=charteredService.getBcPayInfo(orderNo.replace("BC_", ""));
			if(vo==null){return null;}//商品判空
			info.setOrderNo(orderNo);//报价id
			info.setLimitTime(vo.getA1());//支付有效时间
			info.setDesc("小猪包车");//商品描述
			info.setTitle("报价支付");//商品标题
			info.setPrice(vo.getA2());//商品价格
		}
		
		write(info);
		return null;
	}
	
	/**
	 * 支付结果通知地址
	 * @return
	 */
	@Action(value = "merchantNotice", results = { @Result(type = "json") })
	public String merchantNotice(){
		String orderNo = request.getParameter("orderNo");//商户订单号
		String tradeOrderNo = request.getParameter("tradeOrderNo");//第三方交易号
		String fee = request.getParameter("fee");//交易金额
		String payModel = request.getParameter("payModel");//第三方类型（1：支付宝   2：微信  3：PayPal）
		String tradeType = request.getParameter("tradeType");//交易状态（0：订单支付或退款过  1：成功   2：失败）
		
		if(orderNo.contains("BS")){//巴士支付
			//巴士支付回调后流程
			PayReturnUtils pay = new PayReturnUtils();
			pay.payReturnSuccess(orderNo, fee, payModel, tradeOrderNo,tradeType,weiXinPayService,loginAndRegisterService,bookService);
			
		}else{//包车支付
			//处理包车订单
			charteredService.saveBcOrder(orderNo, payModel,tradeType);
		}
		
		return null;
	}
	
	/**
	 * 退票
	 * @return
	 */
	@Action(value = "returnOrder", results = { @Result(type = "json") })
	public String returnOrder(){
		String orderNo = request.getParameter("orderNo");//商户订单号
		String localIds = request.getParameter("localIds");//商品主键S
		User appUser = UserCache.getUser();// 当前登录用户信息
		
		AppVo_1 vo = new AppVo_1();
		if(appUser == null){
			vo.setA1("0");
			write(vo);
			return null;
		}
		
		OrderRRZ orderRRZ = payService.checkOrder(orderNo,localIds,appUser);
		String flag = "0";
		if("0".equals(orderRRZ.getResult())){
			
			//退票相关金额
			AppVo_5 v5 = payService.queryReturnRelMon(orderNo,localIds);
			//可以退票
			if("6".equals(orderRRZ.getType())){
				//新版APP微信支付
				String url = PropertyManage.get("weixin_pay");
				String totalPrice = bookService.getOrderPrice(orderNo);
				//0:失败  1：成功
				flag = Http.post(url, "leaseOrderNo",orderNo,"realReturn",v5.getA3(),"totalPrice",totalPrice);
				vo.setA1(flag);
			}else if("7".equals(orderRRZ.getType())){
				//payPal
				String url = PropertyManage.get("paypal_pay");
				//0:失败  1：成功
				flag = Http.post(url, "Thirdparty",orderRRZ.getThirdparty(),"price",v5.getA3(),"orderNo",orderNo);
				vo.setA1(flag);
			}else{
				flag = "1";
				vo.setA1(flag);
			}
			if("1".equals(flag)){
				//根据座位主键ID获取对应的班次ID,返回多个班次,用","分隔
				String lineClassIds = payService.queryLineClassIds(localIds);
				v5.setA4(appUser.getPassengerId());
				int statu = bookService.returnTicket(localIds, lineClassIds, orderNo, v5, orderRRZ.getType(), "1");
				if(0!=statu){
					// 发送短信
					new Message("【小猪巴士】退款提醒：您申请的%s元退款，近期将退回至您的付款账户，请注意查收。", v5.getA3()).sendTo(appUser.getTelephone());
				}
				flag = String.valueOf(statu);
				vo.setA1(flag);
			}
		}
		write(vo);
		return null;
	}
	
}
