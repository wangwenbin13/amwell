package com.pig84.ab.utils;

import com.pig84.ab.service.IBookService;
import com.pig84.ab.service.ILoginAndRegisterService;
import com.pig84.ab.service.IWeiXinPayService;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_4;

/** 
* @author 作者 : wangwenbin
* @version 创建时间：2015年9月21日 下午2:38:07 
* 支付回调工具
*/

public class PayReturnUtils {

	
	
	/**
	 * 处理回调成功以后的订单相关数据
	 * @param out_trade_no
	 * @param total_fee
	 * @param type
	 * @param trade_no
	 * @param tradeType
	 * @return
	 */
	public int payReturnSuccess(String out_trade_no,String total_fee,String type,String trade_no,String tradeType,IWeiXinPayService weiXinPayService,
			ILoginAndRegisterService loginAndRegisterService,IBookService bookService){
		//支付方式 0：无 1：余额支付，2：财付通 3：银联 4：支付宝 5:微信 6:(APP)微信
		
		int statu = 0;
		if(!"1".equals(tradeType)){
			return 0;
		}
		if("1".equals(type)){
			type = "4";
		}else if("2".equals(type)){
			type = "6";
		}else if("3".equals(type)){
			type = "7";
		}
		// 注意交易单不要重复处理--小猪巴士
		boolean exits = weiXinPayService.getLeasePayByOrderNoIsExits(out_trade_no,type);
		if (exits) {
			String flag = weiXinPayService.successByWeiXinPay(out_trade_no, total_fee,type,trade_no);
			if ("0".equals(flag)) {
				AppVo_4 info = bookService.getOrderTimeAndUserPhone(out_trade_no);// a1:手机号码
				// a2:时间串
				// a3:从哪里到哪里

				String context = "亲，您的车票 ("+info.getA2()+info.getA3()+ ") 买好啦!请提前5分钟在上车点等候，凭有效电子车票乘车【小猪巴士】";
					
				/** 判断是不是华为员工 **/
				boolean isHuaWei = loginAndRegisterService.judgeUserType(info.getA1());

				if (isHuaWei) {
					/** 判断是否送对应的补贴信息 **/
					AppVo_3 appV3 = loginAndRegisterService
							.juderSendAllowance(info.getA1(),
									out_trade_no);
					if (null != appV3 && appV3.getA1().equals("1")) {
						/** 发送华为对应的信息 **/
						context += "此票价由财富之舟补贴" + appV3.getA3()+ "元，由小猪巴士补贴" + appV3.getA2() + "元";
					}
				}
				/** 判断订单是否已存在收入统计之中，存在，说明已经发送过了，则不发送短信 **/
				int count = bookService.isExitsInStatTotal(out_trade_no);
				if (count <= 0) {
					new Message(context).sendTo(info.getA1());
				}
				/** 添加收入统计 **/
				statu = bookService.addStatTotal(out_trade_no);
			}
		}
			
		return statu;
	}
}
