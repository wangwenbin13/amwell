package com.pig84.ab.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.IPayDao;
import com.pig84.ab.utils.Conn;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.LeaseBaseInfo;
import com.pig84.ab.vo.StatOutEntity;
import com.pig84.ab.vo.StatTotalEntity;
import com.pig84.ab.vo.Payment.OrderRRZ;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_10;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_5;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_7;

/**
 * 支付相关业务
 * @author 张强
 * @time 2015-09-17 11:59:59
 *
 */

@Repository
public class PayDaoImpl extends BaseDao implements IPayDao {

	/**
	 * 更具订单号获取订单详细
	 */
	public LeaseBaseInfo getLeaseBaseInfoByOrderNo(String orderNo) {
		String sql = "select * from lease_base_info where leaseOrderNo = ? ";
		Object[] params = new Object[1];
		params[0] = orderNo;
		LeaseBaseInfo orderInfo = queryBean(LeaseBaseInfo.class, sql, params);
		return orderInfo;
	}

	/**
	 * 退票检查
	 * @param orderNo  订单号
	 * @param localIds 座位主键ID(多个用‘，’分割)
	 * @param userid 用户ID
	 * @return
	 */
	@Override
	public OrderRRZ checkOrder(String orderNo, String localIds,String userid) {
		OrderRRZ orderRRZ = new OrderRRZ();
		List<String> params = new ArrayList<String>();
		StringBuilder sql = new StringBuilder(" SELECT a.passengerid AS a1, c.isdiscount AS a2, b.orderDate AS a3, b.orderStartTime AS a4, d.payModel AS a5,c.thirdparty AS a6 ");
		sql.append(" FROM line_business_order a LEFT JOIN line_class_info b ON a.lineClassId = b.lineClassId "
				+ " LEFT JOIN lease_base_info c ON c.leaseOrderNo = a.leaseOrderId "
				+ " LEFT JOIN lease_pay AS d ON d.leaseOrderNo = c.leaseOrderNo "
				+ " WHERE a.leaseOrderId = ? ");
		params.add(orderNo);
		String[] localIdStr = localIds.split(",");
		String conds = " AND a.localid IN ( ";
		for(String localId :localIdStr){
			if(!StringUtils.isEmpty(localId)){
				conds +="?,";
				params.add(localId);
			}
		}
		conds = conds.substring(0, conds.length()-1);
		sql.append(conds);
		sql.append(" ) ");
		sql.append(" AND a.status NOT IN ('3','4') AND c.ispay=1 ORDER BY a.lineClassId ASC ");
		if(params.size()<2){
			orderRRZ.setResult("7");
			return orderRRZ;
		}
		List<AppVo_6> list = queryList(AppVo_6.class, sql.toString(), params.toArray());
		
		AppVo_6 v6 = null;
		
		if(list!=null && list.size()!=0){
			for (int i = 0; i < list.size(); i++) {
				v6 = list.get(i);
				if(!userid.equals(v6.getA1())){//非法改签
					orderRRZ.setResult("6");
					return orderRRZ;
				}else{
					if("1".equals(v6.getA2())){
						orderRRZ.setResult("2");//有优惠,不允许退票
						return orderRRZ;
					}else if(MyDate.diffMinutes(MyDate.Format.DATETIME.now(),v6.getA3()+" "+v6.getA4()+":00")>=0){
						orderRRZ.setResult("4");//已过了发车时间,车票已过期
						return orderRRZ;
					}else if(!MyDate.isAfterNMinute(v6.getA3()+" "+v6.getA4()+":00", 60)){
						orderRRZ.setResult("1");//离发车时间小于1小时，不可退票
						return orderRRZ;
					}else{
						orderRRZ.setResult("0");//正常情况
					}
					orderRRZ.setType(v6.getA5());
					orderRRZ.setThirdparty(v6.getA6());
				}
			}
		}else{
			orderRRZ.setResult("7");
		}
		return orderRRZ;
	}

	/**
	 * 根据座位主键ID获取对应的班次ID,返回多个班次,用","分隔
	 * @param localIds localIds 座位主键ID(多个用‘，’分割)
	 * @return
	 */
	@Override
	public String queryLineClassIds(String localIds) {
		//SELECT group_concat(lineClassId) AS a1 FROM line_business_order WHERE localId IN ('140930141231230143','140930141231282144','140930141231334145')
		StringBuilder sql = new StringBuilder(" SELECT group_concat(lineClassId) AS a1 FROM line_business_order WHERE localId IN (");
		StringBuilder cond = new StringBuilder();
		String[] localIdStr = localIds.split(",");
		List<String> params = new ArrayList<String>();
		for(String local :localIdStr){
			if(!StringUtils.isEmpty(local)){
				cond.append("?,");
				params.add(local);
			}
		}
		sql.append(cond.substring(0, cond.length()-1));
		sql.append(")");
		AppVo_1 vo = queryBean(AppVo_1.class, sql.toString(), params.toArray());
		if(null!=vo){
			return vo.getA1();
		}
		return null;
	}

}
