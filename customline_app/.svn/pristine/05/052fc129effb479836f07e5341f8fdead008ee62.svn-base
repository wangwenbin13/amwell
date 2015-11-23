package com.pig84.ab.dao;

import java.util.List;

import com.pig84.ab.vo.ApplyReturn;
import com.pig84.ab.vo.PassengerComments;
import com.pig84.ab.vo.bean.AppVo_10;
import com.pig84.ab.vo.bean.AppVo_10_list;
import com.pig84.ab.vo.bean.AppVo_15_list;
import com.pig84.ab.vo.bean.AppVo_18;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_25;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_5;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_7;




/**
 * 预定相关接口
 * @author zhangqiang
 *
 */
public interface IBookDao {
	
	public AppVo_15_list orderInfoByDays(String orderNo);
	
	/**取消订单**/
	public String cancelOrder(String orderNo);
	
	/**电子票详情（按次购买）**/
	public AppVo_10_list ticketInfoByDays(String orderNo);
	
	/**获取用户余额**/
	public String getUserBalance(String userId);
	
	/**获取用户未读消息条数**/
	public String getUnReadMsgCount(String userId);
	
	/**获取用户未读订单数**/
	public String getUnPayOrderCount(String userId);
	
	/**获取订单价格**/
	public String getOrderPrice(String orderNo);
	
	/**获取报价信息**/
	public AppVo_4 getBcBiddingById(String bcBiddingId);
	
	/**根据订单号获取订单对应的发车时间与用户手机号码**/
	public AppVo_4 getOrderTimeAndUserPhone(String orderNo);
	
	/**根据线路ID获取班次线路详细（NEW）**/
	public AppVo_15_list LineAndClassInfo(String lineBaseId);
	
	/**评论列表**/
	public List<AppVo_6> commentList(int currentPage,int pageSize,String lineBaseId);

	/***查看记录是否存在**/
	public int isExitsInStatTotal(String outTradeNo);

	/**添加收入统计**/
	public int addStatTotal(String orderNo);
	
	/**可用优惠券列表**/
	public List<AppVo_10> getMyGif(String orderPrice,String userid,int currentPage,int pageSize,String type);
	
	/**根据订单号检查订单是否有误**/
	public boolean checkOrder(String orderNo);
	
	/**可改签列表**/
	public List<AppVo_6> changeList(String orderNo,String userid);

	/**改签日期列表**/
	public List<AppVo_6> changeDateList(String orderNo, int currentPage, int pageSize);
	
	/**获取线路相关信息**/
	public AppVo_6 getLineInfo(String orderNo);
	
	/**
	 * 订单评价V2
	 */
	public String orderCommentsV2(String orderNo,PassengerComments pc);

	/**新版改签(APP前端直接改签,不需要通过平台审批)**/
	public String changeTicket(String orderNo, String cids);

	/**计算退票的相关数据**/
	public Integer queryReturnPercent();

	/**退票**/
	public String returnTicket(String localIds, String lineClassIds, String orderNo,AppVo_5 vo_4,String payType);
	
	
/*************************************************************************新增接口（V2.0）***********************************************************************/
	
	/**班车详情**/
	public AppVo_15_list getLineAndClassInfo(String lineBaseId,String slineId,String passengerid);
	
	/**可用优惠卷列表(v2.0新接口)**/
	public List<AppVo_10> getUsableGif(String userid,String type);
	
	/**车票预定（按次购买V2.0新增接口）**/
	public String bookLineByDays_new(String lineBaseid,String lineSplitId,String lineClassIds,String userid,String userType,String gifId);
	
	/**获取订单对应的优惠券金额**/
	public AppVo_2 getOrderGifMoney(String orderNo);
	
	/**获取包车订单对应的优惠券金额**/
	public AppVo_2 getBcOrderGifMoney(String orderNo);
	
	/**申请退票**/
	public int applyReturnTicket(ApplyReturn applyReturn);

	/**添加发送信息**/
	public String addMsgInfo(ApplyReturn applyReturn);
	
	/**订单列表v2.1**/
	public List<AppVo_18> getOrderListByV2_1(String userid,String orderType,String currentPage,String pageSize);
	
	/**获取订单详细v2.1**/
	public AppVo_15_list getOrderInfoByV2_1(String orderNo);
	
	/**改签详情(2.1)**/
	public List<AppVo_6> changeTicketDetail_V2_1(String localId);

	/**退票详情(2.1)**/
	public List<AppVo_7> returnTicketDetail_V2_1(String localId);
	
	/** 电子票列表v2.3**/
	public List<AppVo_25> getTicktListByV2_3(String userId,String type,String currentPage,String pageSize);

	/**查找原实际金额**/
	public String queryPriceByLocalIds(String localIds);
}
