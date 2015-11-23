package com.pig84.ab.service.impl;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.ICharteredLineDao;
import com.pig84.ab.service.ICharteredLineService;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.BcOrderEntiry;
import com.pig84.ab.vo.CharteredLineVo;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_15;
import com.pig84.ab.vo.bean.AppVo_18;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_9;

/**
 * 包车相关
 * @author 
 *
 */
@Service("charteredLineService")
public class CharteredLineServiceImpl implements ICharteredLineService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ICharteredLineDao charteredLineDao;
	
	/**发布包车信息**/
	public String saveCharteredLine(CharteredLineVo charteredLine,String savaType,String bcLineId){
		return charteredLineDao.saveCharteredLine(charteredLine,savaType,bcLineId);
	}
	
	/**查询过期时间配置**/
	public AppVo_1 getExpireTimeSet(){
		return charteredLineDao.getExpireTimeSet();
	}
	
	/**查询登录用户已发布的包车列表**/
	public List<AppVo_9> getCharteredLineList(String passengerId,String lineStatus,int pageSize,int currentPage){
		return charteredLineDao.getCharteredLineList(passengerId,lineStatus,pageSize,currentPage);
	}
	
	/**查询已有报价包车的商家报价列表**/
	public List<AppVo_3> getBusinessBiddingList(String charteredLineId){
		return charteredLineDao.getBusinessBiddingList(charteredLineId);
	}
	
	/**查询包车需求详情**/
	public AppVo_15 getCharteredLineDetail(String charteredLineId){
		return charteredLineDao.getCharteredLineDetail(charteredLineId);
	}
	
	/**查询商家报价详情**/
	public AppVo_18 getBcBiddingDetail(String bcBiddingId,String userId){
		return charteredLineDao.getBcBiddingDetail(bcBiddingId,userId);
	}
	
	/**支付成功后保存订单信息**/
	public String saveBcOrder(BcOrderEntiry order){
		return charteredLineDao.saveBcOrder(order);
	}
	
	/**支付成功后保存订单信息**/
	public String saveBcOrder(String biddingId,String payModel,String tradeType){
		//包车支付的orderNo结构为：BC_biddingId（biddingId为报价id）
		if (StringUtils.isNotBlank(biddingId)) {
			biddingId=biddingId.replace("BC_", "");
			//tradeType:交易状态（0：订单支付或退款过  1：成功   2：失败）
			if("1".equals(tradeType)){
				//根据报价id查询报价总价
				AppVo_4 vo=charteredLineDao.getBcBiddingById(biddingId);
				BigDecimal totalPrice=new BigDecimal(vo.getA3());
				
				BcOrderEntiry order=new BcOrderEntiry();
				order.setBc_line_id(vo.getA1());//包车信息id
				order.setBusinessId(vo.getA2());//商家id
				order.setPassengerId(vo.getA4());//登录用户id
				order.setPrice(totalPrice);//总价
				order.setOrderStatus(1);//订单状态,1:待调度 2：已调度(同时输入司机、车辆) 3：已过期
				//payModel第三方类型（1：支付宝   2：微信支付）
				//乘客支付方式，0.无 1:支付宝 2：银联 3:微信支付 4:(APP)微信
				if("1".equals(payModel)){
					order.setPayModel(1);
				}
				if("2".equals(payModel)){
					order.setPayModel(4);
				}
				order.setPayTime(MyDate.Format.DATETIME.now());
				order.setTotalPrice(totalPrice);//车票单价(实际支付)
				return charteredLineDao.saveBcOrder(order);
			}
		}
		return null;
	}
	
	/**查询商家介绍信息**/
	public AppVo_2 getBusinessRemark(String businessId){
		return charteredLineDao.getBusinessRemark(businessId);
	}
	
	/**修改包车信息状态**/
	public int updateLineStatus(String bcLineIds,int lineStatus){
		return charteredLineDao.updateLineStatus(bcLineIds, lineStatus);
	}
	
	/**查询已开通包车业务的城市信息**/
	public List<AppVo_3> getBcCityList(){
		return charteredLineDao.getBcCityList();
	}
	
	/**查询包车协议**/
	public AppVo_2 getUserAgreement(){
		return charteredLineDao.getUserAgreement();
	}
	
	/**查询当天乘客已发布的包车信息条数**/
	public int getCharteredLineCount(String passengerId){
		return charteredLineDao.getCharteredLineCount(passengerId);
	}
	
	/**根据报价id获取支付信息**/
	public AppVo_2 getBcPayInfo(String biddingId){
		AppVo_2 vo=new AppVo_2();
		// 根据报价id查询报价总价
		AppVo_4 v_3 = charteredLineDao.getBcBiddingById(biddingId);
		//根据包车线路id查询包车过期时间
		String time =  charteredLineDao.getExpireTime(v_3.getA1()).getA1();//包车信息过期时间
		//计算过期分钟数
		Long theTimeS=0L;
		try {
			 Calendar c=Calendar.getInstance();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			 Date expireTime = sdf.parse(time);
			 theTimeS=(expireTime .getTime()-c.getTime().getTime())/60000 + 5;//计算当前时间下距离包车信息过期时间之间的分钟数，并向后推迟5分钟
		} catch (ParseException e) {
			logger.error("Parse expire time failed: {}", time);
		}
		vo.setA1(theTimeS+"m");
		vo.setA2(v_3.getA3());
		return vo;
	}
}
