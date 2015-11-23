package com.pig84.ab.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.IBookDao;
import com.pig84.ab.dao.IPayDao;
import com.pig84.ab.dao.impl.RePeat;
import com.pig84.ab.service.IPayService;
import com.pig84.ab.vo.LeaseBaseInfo;
import com.pig84.ab.vo.User;
import com.pig84.ab.vo.Payment.OrderRRZ;
import com.pig84.ab.vo.bean.AppVo_5;

/**
 * 支付相关业务
 * @author 张强
 * @time 2015-09-17 11:59:59
 *
 */
@Service
public class PayServiceImpl implements IPayService {
	
	@Autowired
	private IPayDao payDao;
	
	@Autowired
	private IBookDao bookDao;
	
	/**根据订单号获取订单详细**/
	public LeaseBaseInfo getLeaseBaseInfoByOrderNo(String orderNo){
		return payDao.getLeaseBaseInfoByOrderNo(orderNo);
	}

	/**退票检查**/
	@Override
	public OrderRRZ checkOrder(String orderNo, String localIds,User appUser) {
		OrderRRZ checkOrder = new OrderRRZ();
		if(StringUtils.isEmpty(localIds)){
			checkOrder.setResult("7");
			return checkOrder;
		}
		if(null!=appUser && !StringUtils.isEmpty(appUser.getPassengerId())){
			checkOrder = payDao.checkOrder(orderNo,localIds,appUser.getPassengerId());
		}else{
			checkOrder.setResult("5");
		}
		return checkOrder;
	}

	/**退票相关金额**/
	@Override
	public AppVo_5 queryReturnRelMon(String orderNo, String localIds) {
		//手续费的百分比
		Integer percent = bookDao.queryReturnPercent();
		//根据座位主键ID获取对应的原票价
		String price = bookDao.queryPriceByLocalIds(localIds);
		AppVo_5 vo5 = RePeat.countTickertMoney(price,percent);
		return vo5;
	}

	/**根据座位主键ID获取对应的班次ID,返回多个班次,用","分隔**/
	@Override
	public String queryLineClassIds(String localIds) {
		return payDao.queryLineClassIds(localIds);
	}

}
