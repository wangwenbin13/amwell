package com.pig84.ab.service.impl;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.alipay.AlipayConfig;
import com.pig84.ab.alipay.RSA;
import com.pig84.ab.dao.IAliPayDao;
import com.pig84.ab.dao.IBookDao;
import com.pig84.ab.dao.ICharteredLineDao;
import com.pig84.ab.service.IAliPayService;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.bean.AppVo_4;

@Service
public class AliPayServiceImpl implements IAliPayService {
	
	private static final Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);

	@Autowired
	private IAliPayDao aliPayDao;
	
	@Autowired
	private ICharteredLineDao charteredLineDao;
	
	@Autowired
	private IBookDao bookDao;

	/**
	 * 构建支付宝订单内容（充值）
	 */
	public String bulidRechargeOrderByAli(String userid, String money) {
		String flag = aliPayDao.bulidRechargeOrderByAli(userid, money);
		String result = "";
		if(!"0".equals(flag)){//插入数据成功
			//构建支付宝内容
			result = payZfb(money, "余额充值", "余额充值,订单号:" + flag ,flag,"30m",AlipayConfig.RECHARG_BACK_RUL);
		}
		
		return result;
	}

	/**
	 * 支付宝充值成功，添加金额
	 */
	public String addMoney(String orderNo) {
		return aliPayDao.addMoney(orderNo);
	}
	
	/**
	 * 构建支付宝订单内容（付款）
	 */
	public String bulidPayOrderByAli(String money, String orderNo) {
		String time =  aliPayDao.getTime();//订单超时时间
		String result = payZfb(money, "订单支付", "订单支付,订单号:" + orderNo ,orderNo,time+"m",AlipayConfig.RECHARG_BACK_RUL_PAY);
		return result;
	}
	
	/**
	 * 构建支付宝订单内容（付款）包车
	 */
	public String bulidPayBcOrderByAli(String money,String bcBiddingId,String counponTeleId) {
		//根据报价id查询报价总价
		AppVo_4 vo=bookDao.getBcBiddingById(bcBiddingId);
		String time =  charteredLineDao.getExpireTime(vo.getA1()).getA1();//包车信息过期时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//计算过期分钟数
		Long theTimeS=0L;
		try {
			 Calendar c=Calendar.getInstance();
			 Date expireTime = sdf.parse(time);
			 theTimeS=(expireTime .getTime()-c.getTime().getTime())/60000 + 5;//计算当前时间下距离包车信息过期时间之间的分钟数，并向后推迟5分钟
		} catch (ParseException e) {
			logger.error("Parse expire time failed: {}", time);
			// XXX Should do something?
		}
		String title="报价支付"+(StringUtils.isNotBlank(counponTeleId)?"，优惠券id:"+counponTeleId:"");
		String content="报价支付，包车线路id:" + vo.getA1();
		String result = payZfb(money,title,content,bcBiddingId,theTimeS+"m",AlipayConfig.RECHARG_BACK_RUL_PAY_BC);
		return result;
	}


/******************************************************************支付宝附加方法**************************************************************************/	
	
	
	
	//组装订单信息
	private String  payZfb(String price,String title,String desc,String orderNo,String time,String url) {
		String type = PropertyManage.get("run_type");
		if("1".equals(type)){
			//测试环境
			title = "test|"+title;
		}
		String infor = makeOrder(price,title,desc,orderNo,time,url);
		String sign = RSA.sign(infor, AlipayConfig.private_key,AlipayConfig.input_charset);
		sign = URLEncoder.encode(sign);
		return infor += "&sign=\"" + sign + "\"&" + getSignType();
	}

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	// subject="珍珠项链 【2元包邮】韩版 韩国 流行饰品太阳花小巧雏菊 珍珠项链2M15"
	// body="【2元包邮】韩版 韩国 流行饰品太阳花小巧雏菊 珍珠项链2M15" price="一口价:0.01"
	@SuppressWarnings("deprecation")
	private String makeOrder(String price,String title,String desc,String orderNo, String time,String url) {

		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(AlipayConfig.partner);//合作商ID
		sb.append("\"&out_trade_no=\"");
		sb.append(orderNo);
		sb.append("\"&subject=\"");
		sb.append(title);
		sb.append("\"&body=\"");
		sb.append(desc);
		sb.append("\"&total_fee=\"");
		sb.append(price);//价格
		sb.append("\"&it_b_pay=\"");
		sb.append(time);//超时时间
		sb.append("\"&notify_url=\"");
		// 网址需要做URL编码
		sb.append(URLEncoder.encode(url));//回调函数
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(AlipayConfig.DEFAULT_SELLER);//收款人

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"");

		return new String(sb);
	}
	
/******************************************************************支付宝附加方法**************************************************************************/	
}

