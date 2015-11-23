package com.pig84.ab.dao;

import java.util.List;

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
 * 包车相关接口
 * @author 
 *
 */
public interface ICharteredLineDao {
	
	/**发布包车信息**/
	public String saveCharteredLine(CharteredLineVo charteredLine,String savaType,String bcLineId);
	
	/**查询过期时间配置**/
	public AppVo_1 getExpireTimeSet();
	
	/**查询登录用户已发布的包车列表**/
	public List<AppVo_9> getCharteredLineList(String passengerId,String lineStatus,int pageSize,int currentPage);
	
	/**查询已有报价包车的商家报价列表**/
	public List<AppVo_3> getBusinessBiddingList(String charteredLineId);
	
	/**查询包车需求详情**/
	public AppVo_15 getCharteredLineDetail(String charteredLineId);
	
	/**查询商家报价详情**/
	public AppVo_18 getBcBiddingDetail(String bcBiddingId,String userId);
	
	/**支付成功后保存订单信息**/
	public String saveBcOrder(BcOrderEntiry order);
	
	/**查询商家介绍信息**/
	public AppVo_2 getBusinessRemark(String businessId);
	
	/**修改包车信息状态**/
	public int updateLineStatus(String bcLineIds,int lineStatus);
	
	/**获取包车信息过期时间，格式yyyy-MM-dd hh:mm:ss**/
	public AppVo_1 getExpireTime(String bcLineId);
	
	/**查询已开通包车业务的城市信息**/
	public List<AppVo_3> getBcCityList();
	
	/**查询包车协议**/
	public AppVo_2 getUserAgreement();
	
	/**查询当天乘客已发布的包车信息条数**/
	public int getCharteredLineCount(String passengerId);
	
	/**获取报价信息**/
	public AppVo_4 getBcBiddingById(String bcBiddingId);
}
