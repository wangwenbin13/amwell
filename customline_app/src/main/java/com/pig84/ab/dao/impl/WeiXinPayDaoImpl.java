package com.pig84.ab.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ICouponDao;
import com.pig84.ab.dao.IDriverDao;
import com.pig84.ab.dao.IRecommendAwardDao;
import com.pig84.ab.dao.IWeiXinPayDao;
import com.pig84.ab.utils.Event;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.utils.JPush;
import com.pig84.ab.utils.Message;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.BcOrderEntiry;
import com.pig84.ab.vo.GfCouponPassenger;
import com.pig84.ab.vo.LeaseBaseInfo;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.StatPassengerConsumLease;
import com.pig84.ab.vo.StatPassengerRecharge;
import com.pig84.ab.vo.SysMsgInfo;
import com.pig84.ab.vo.SysMsgUser;
import com.pig84.ab.vo.TotalCost;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_6;

/**
 * 
 * @author wangwenbin
 *
 * 2014-12-20
 */
/**
 * 微信支付
 */
@Repository("weiXinPayDao")
public class WeiXinPayDaoImpl extends BaseDao implements IWeiXinPayDao {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDriverDao driverDao;
	
	@Autowired
	private IRecommendAwardDao recommendAwardDao;
	
	@Autowired
	private ICouponDao couponDao;

	/** 微信支付付款返回结果（支付） 小猪巴士 **/
	public String successByWeiXinPay(String orderNo, String money,String type,String thridOrderId) {
		String sql = "select * from lease_base_info where leaseOrderNo = ?";
		Object[] obj = new Object[1];
		obj[0] = orderNo;
		LeaseBaseInfo order = queryBean(LeaseBaseInfo.class, sql, obj);
		if (order == null) {
			return "-1";
		}
		
		// 本地充值
		StatPassengerRecharge cz = new StatPassengerRecharge();
		cz.setRetype("1");
		cz.setRetime(MyDate.Format.DATETIME.now());
		cz.setRerunNo(orderNo);
		cz.setPassengerId(order.getPassengerId());
		
		//支付方式 0：无 1：余额支付，2：财付通 3：银联 4：支付宝 5:微信 6:(APP)微信
		
//		if("4".equals(type)){
//			cz.setMoneyLimit(money);
//		}else{
//			Float money_t = Float.valueOf(money);// 微信(分为单位)| 银联(分为单位)|支付宝(元为单位)
//			Float realMoney = money_t / 100;
//			cz.setMoneyLimit(String.valueOf(realMoney));
//		}
		cz.setMoneyLimit(order.getRealprice());
		cz.setReModel(type);
		int cz_flag = updateData(cz, "stat_passenger_recharge","rechargeId");

		if (cz_flag == -1) {
			return "-1";
		}

		// 本地扣款消费
		String money_pay = "0.0"; // 扣款金额，订单价格
		if ("1".equals(order.getBuyType())) { // 包月购买
			money_pay = order.getRealprice();

		} else if ("0".equals(order.getBuyType())) {
			money_pay = order.getRealprice();
		}
		StatPassengerConsumLease xf = new StatPassengerConsumLease();
		xf.setPassengerId(order.getPassengerId());
		xf.setLeaseOrderNo(orderNo);
		xf.setLeasePayTime(MyDate.Format.DATETIME.now());
		xf.setLeaseLimit(money_pay);
		xf.setBusinessId(order.getBusinessId());

		int xf_flag = updateData(xf,"stat_passenger_consum_lease", "orderConsumId");

		if (xf_flag == -1) {
			logger.warn("微信支付本地消费失败，订单号：{}, 消费金额：{}", orderNo, money_pay);
			return "-1";
		}
		// 更该订单状态
		String sql_changeOrder=null;
		int order_flag=-1;
		
		if(thridOrderId==null){
		    sql_changeOrder = "update lease_base_info set payStatus = '1',ispay = '1' where leaseOrderNo = ?";
		    order_flag = executeSQL(sql_changeOrder,
					new Object[] { orderNo });
		}else{
			  sql_changeOrder = "update lease_base_info set payStatus = '1',ispay = '1', thirdparty =? where leaseOrderNo = ?";
			  order_flag = executeSQL(sql_changeOrder,
						new Object[] { thridOrderId,orderNo });
		}
		if (order_flag != -1) {
			if(driverDao.addImGroupUser(orderNo)==-1){
			}
			Event.BUYTICKET.triggerAsynchronous("userId", order.getPassengerId());
		}

		String sql_lease_pay = "update lease_pay set payModel = ? where leaseOrderNo = ? and payModel = '0'";
		int lease_pay_flag = executeSQL(sql_lease_pay,new Object[] { type,orderNo });
		if(lease_pay_flag<0){
			return "-1";
		}

		return "0";// 通过
	}

	/** 付款返回结果（支付）包车 **/
	public String saveBcOrder(BcOrderEntiry order) {
		// 获取不重复的订单编号
		order.setOrderNo(getBcOrderNo());
		int flag = updateData(order,"bc_order", "orderId");
		if (flag == 1) {// 订单保存成功，则修改包车信息状态
			flag = updateLineStatus(order.getBc_line_id(), 6);// 6.已支付

			if(StringUtils.isNotBlank(order.getCouponGiftId())){//支付时使用了优惠券，则修改优惠券使用状态
				//查询乘客电话
				String s="SELECT telephone as a1 FROM passenger_info WHERE passengerId = ?";
				Object[] params = new Object[1];
				params[0]=order.getPassengerId();
				AppVo_1 vo1=queryBean(AppVo_1.class, s, params);
				
				/** 更改gf_coupon_passenger **/
				GfCouponPassenger gf = new GfCouponPassenger();
				gf.setPassengerId(order.getPassengerId());
				gf.setTelephone(vo1.getA1());
				gf.setCouponGiftId(order.getCouponGiftId());
				gf.setOrderId(order.getOrderNo());
				gf.setUseState("1");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = df.format(new Date());
				gf.setUserTime(time);
				gf.setTheModule("2");
				updateData(gf, "gf_coupon_passenger","counponTeleId");
			}
		}
		if (flag == 1) {// 前面的操作成功
			// 获取商家电话
			String sql = "SELECT contactsPhone AS a1 FROM mgr_business WHERE businessId = ?";
			AppVo_1 vo = queryBean(AppVo_1.class, sql,
					new Object[] { order.getBusinessId() });

			// 获取包车信息
			sql = "SELECT beginAddress AS a1,endAddress AS a2,startTime AS a3,createOn as a4 FROM bc_line WHERE bc_line_id = ?";
			AppVo_4 v4 = queryBean(AppVo_4.class, sql,
					new Object[] { order.getBc_line_id() });

			// 发送短信给商家
			// XXX Should not send SMS in DAO
			new Message("您好，订单号为%s，出车时间为：%s，%s—%s 的包车已完成支付，请尽快完成调度。", order.getOrderNo(), v4.getA3(), v4.getA1(), v4.getA2())
					.sendTo(vo.getA1());

			flag = sendMsgAfterSaveOrder(order.getPassengerId(), v4);
		}
		return String.valueOf(flag);
	}

	// 保存订单后发送站内信给乘客
	private int sendMsgAfterSaveOrder(String passengerId, AppVo_4 v4) {
		PassengerInfo passenger = queryBeanById(PassengerInfo.class,passengerId,"passenger_info", "passengerId");

		// 保存站内信息并推送给用户
		SysMsgInfo smi = new SysMsgInfo();
		smi.setTheModule("2");// 所属模块（1.小猪巴士 2.包车）
		smi.setMsgType("1");// 消息类型 0:短信消息 1:软件消息
		smi.setMsgSubType("0");// 消息子类型 --->软件消息:待定 --->系统消息 0:系统消息 1:人工消息
		smi.setMsgTitle("包车（" + v4.getA1() + "-" + v4.getA2() + "线路 支付成功！）");// 标题
		String msgContent = "尊敬的用户您好，您于" + v4.getA4() + "所发布的发车时间为："
				+ v4.getA3() + "，" + v4.getA1() + "-" + v4.getA2()
				+ "的包车信息已支付成功！" + "请前往个人中心-包车订单查看吧！我公司工作时间为"
				+ PropertyManage.get("workingHours")
				+ "，若有任何疑问请拨打客服电话"
				+ PropertyManage.get("companyTel");
		smi.setMsgContext(msgContent);// 内容
		smi.setMsgTarget("0");// 指定发送对象类型 0：乘客 1：司机 2：商家
		smi.setCreateBy(passenger.getNickName());
		smi.setCreateOn(MyDate.Format.DATETIME.now());
		smi.setIssend("0");// 发送状态（0：已发送 1：未发送）
		int flag = updateData(smi,"sys_msg_info", "msgId");
		if (flag == 1) {

			SysMsgUser smu = new SysMsgUser();
			smu.setUserId(passenger.getPassengerId());
			smu.setMsgId(smi.getMsgId());
			smu.setReadFlag("0");// --->软件消息:已读标志 0：未读 1：已读 --->短信消息 ：只表示发送对应关系
			smu.setSendPhoneNo(passenger.getTelephone());
			smu.setSendTime(MyDate.Format.DATETIME.now());
			flag = updateData(smu,"sys_msg_user","localId");
			if (flag == 1) {
				String mobileNo[] = new String[1];
				mobileNo[0] = passenger.getTelephone();
				AppVo_6 v_6 = new AppVo_6();
				v_6.setA1(smi.getMsgTitle());// 标题
				v_6.setA2(smi.getMsgContext());// 消息正文
				v_6.setA3("");// 图片URL
				v_6.setA4(smi.getMsgId());// 消息ID
				v_6.setA5(PropertyManage.get("companyTel"));// 客服电话
				v_6.setA6(smi.getTheModule());// 所属模块

				// 调用消息推送的方法
				boolean result = JPush.sendMessage(v_6, mobileNo);
				if (!result) {
					flag = 0;
				}
			}
		}
		return flag;
	}

	// 获取订单号
	private String getBcOrderNo() {
		// 检查订单号是否重复
		String orderNo = IdGenerator.seq();
		String sql_orderNo = "select orderId from bc_order where orderNo = ?";
		int count = queryCount(sql_orderNo, new Object[] { orderNo });

		if (count > 0) {// 订单号重复，从新生成
			getBcOrderNo();
		}
		return orderNo;
	}

	/** 修改包车信息状态 **/
	public int updateLineStatus(String bcLineIds, int lineStatus) {
		String sql = "UPDATE bc_line SET lineStatus = ?";
		List<Object> conditions = new ArrayList<Object>();
		conditions.add(lineStatus);
		// 动态处理多个包车id
		if (StringUtils.isNotBlank(bcLineIds)) {
			String[] str = bcLineIds.split(",");
			StringBuffer sb = new StringBuffer();
			for (String string : str) {
				sb.append("?,");
				conditions.add(string);
			}
			sql += " WHERE  bc_line_id in(" + sb.substring(0, sb.length() - 1)+ ")";
		}
		return executeSQL(sql, conditions.toArray());
	}

	/**
	 * 注意交易单不要重复处理
	 */
	public boolean getBcOrderByBidIdIsExits(String bcBidId) {
		String table = " bc_order AS bcOrder LEFT JOIN bc_business_bidding AS bcBid ON bcBid.bc_line_id = bcOrder.bc_line_id ";
		String sql = " SELECT bcOrder.orderId FROM " + table
				+ " where 1=1 and bcBid.id = ? ";
		Object[] params = new Object[1];
		params[0] = bcBidId;
		int count = queryCount(sql, params);
		if (count > 0) {
			/*** 已经支付过了 */
			return false;
		}
		return true;
	}

	/** 注意交易单不要重复处理--小猪巴士 **/
	public boolean getLeasePayByOrderNoIsExits(String orderNo,String type) {
		String sql = " select leaseOrderNo from lease_pay where leaseOrderNo = ? and payModel = ? ";
		Object[] params = new Object[2];
		params[0] = orderNo;
		params[1] = type;
		int count = queryCount(sql, params);
		if (count > 0) {
			/*** 已经支付过了 */
			return false;
		}
		return true;
	}

	/** 如果使用了优惠券，更新gf_coupon_passenger ***/
	public void updateGfPassenger(String counponTeleId, String orderNo) {
		couponDao.updateCouponState(null,null,"used",orderNo,MyDate.Format.DATETIME.now(), counponTeleId);
	}

	/** 根据订单号查找对应的优惠券信息 **/
	public String findCouponTeleId(String outTradeNo) {
		return couponDao.findCouponDetailId(outTradeNo);
	}

	/** 根据bcLineId查找对应的订单价格 **/
	public BigDecimal getOrderPrice(String bcLineId, String businessId) {
		String sql = " select totalCost from bc_business_bidding where bc_line_id = ? and businessId = ? ";
		Object[] params = new Object[2];
		params[0] = bcLineId;
		params[1] = businessId;
		TotalCost totalCost = queryBean(TotalCost.class, sql, params);
		if (null != totalCost) {
			return totalCost.getTotalCost();
		}
		return new BigDecimal("0.00");
	}
}
