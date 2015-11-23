package com.pig84.ab.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ILeaseDao;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.LeaseBaseInfo;
import com.pig84.ab.vo.bean.AppVo_1;
/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 订单相关
 */
@Repository
public class LeaseDaoImpl extends BaseDao  implements ILeaseDao {

	/**根据订单号获取订单信息**/
	public LeaseBaseInfo queryLeaseBaseInfo(String orderNo) {
		//SELECT leaseOrderNo,slineId,ridesInfo,totalCount,lineBaseId FROM lease_base_info WHERE leaseOrderNo = '141011293564'
		String sql = " SELECT leaseOrderNo,slineId,ridesInfo,totalCount,lineBaseId,leaseOrderTime,thirdparty,ustation,dstation FROM lease_base_info WHERE leaseOrderNo = ? ";
		Object[] params = new Object[1];
		params[0] = orderNo;
		LeaseBaseInfo leaseBaseInfo = queryBean(LeaseBaseInfo.class, sql, params);
		return leaseBaseInfo;
	}

	/**退票--原来的总票价**/
	public String queryReturnTicketOldMoney(String orderNo) {
		String sql = " SELECT oldMoney AS a1 FROM return_ticket WHERE operTime = ( SELECT min(operTime) FROM return_ticket WHERE leaseOrderNo = ? GROUP BY leaseOrderNo ) AND leaseOrderNo = ? ";
		Object[] params = new Object[2];
		params[0] = params[1] = orderNo;
		AppVo_1 vo = queryBean(AppVo_1.class, sql, params);
		if(null!=vo){
			return vo.getA1();
		}
		return null;
	}

	/**获取订单的支付方式**/
	public String queryLeasePayType(String orderNo) {
		//SELECT b.payModel AS a1 FROM lease_base_info AS a LEFT JOIN lease_pay AS b ON a.leaseOrderNo = b.leaseOrderNo WHERE a.leaseOrderNo = '141011639427' AND b.payModel NOT IN (0,1)
		String sql = " SELECT b.payModel AS a1 FROM lease_base_info AS a LEFT JOIN lease_pay AS b ON a.leaseOrderNo = b.leaseOrderNo WHERE a.leaseOrderNo = ? AND b.payModel NOT IN (0,1) ";
		Object[] params = new Object[1];
		params[0] = orderNo;
		AppVo_1 vo = queryBean(AppVo_1.class, sql, params);
		String type = "0";
		if(null!=vo && !StringUtils.isEmpty(vo.getA1())){
			type = vo.getA1();
		}
		return type;
	}

	/**检查该退款记录是否存在**/
	public boolean checkReturnCount(String localIds, String lineClassIds,
			String orderNo, String passengerId, String realBack) {
		String sql = " SELECT id FROM return_record WHERE localIds = ? AND lineClassIds = ? AND orderNo = ? AND passengerId = ? AND realBack = ? ";
		List<String> cons = new ArrayList<String>();
		cons.add(localIds);
		cons.add(lineClassIds);
		cons.add(orderNo);
		cons.add(passengerId);
		cons.add(realBack);
		int count = queryCount(sql, cons.toArray());
		boolean flag = false;
		if(count<1){
			flag = true;
		}
		return flag;
	}

	/**添加退款记录信息**/
	public void addReturnMoneyCount(String localIds, String lineClassIds,
			String orderNo, String passengerId, String realBack) {
		String sql = " INSERT INTO return_record(localIds,lineClassIds,orderNo,passengerId,realBack,time) VALUES(?,?,?,?,?,?) ";
		List<String> cons = new ArrayList<String>();
		cons.add(localIds);
		cons.add(lineClassIds);
		cons.add(orderNo);
		cons.add(passengerId);
		cons.add(realBack);
		cons.add(MyDate.Format.DATETIME.now());
		executeSQL(sql, cons.toArray());
	}

	/**检查是否已经申请过退票---如果申请了,则不再重复申请**/
	public int qeryReturnTicket(String lineClassIds, String orderNo, String passengerId) {
		String[] lineclasses ={};
		if(!StringUtils.isEmpty(lineClassIds)){
			lineclasses = lineClassIds.split(",");
		}
		String lineclassId = null;
		if(!StringUtils.isEmpty(orderNo) && !StringUtils.isEmpty(passengerId)){
			for(int i=0;i<lineclasses.length;i++){
				if(!StringUtils.isEmpty(lineclasses[i])){
					lineclassId = lineclasses[i];
					break;
				}
			}
			String sql = " SELECT COUNT(*) AS a1 FROM stat_out WHERE leaseOrderNo = ? AND passengerId = ? AND lineClassId = ? AND outType = 1 ";
			Object[] args = new Object[3];
			args[0] = orderNo;
			args[1] = passengerId;
			args[2] = lineclassId;
			AppVo_1 v1 = queryBean(AppVo_1.class, sql, args);
			if(null!=v1){
				return Integer.valueOf(v1.getA1());
			}
		}
		return 0;
	}

	/**
	 * 获取订单失效时间
	 */
	@Override
	public String getOrderTime(String orderNo) {
		String sql = "select leaseOrderTime as a1 from lease_base_info where leaseOrderNo = '"+orderNo+"'";
		AppVo_1 vo = queryBean(AppVo_1.class, sql);
		String value = "";
		
		if(vo!=null){
			String time  = vo.getA1();//订单时间
			String sql_setting = "select max(orderValiteTime) as a1 from sys_app_setting";
			AppVo_1 setting = queryBean(AppVo_1.class, sql_setting);
			if(setting!=null){
				String temp = setting.getA1();//有效时长（分钟）
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				 Date now;
				try {
					 now = sdf.parse(time);
					 Date afterDate = new Date(now .getTime() + Integer.valueOf(temp)*60*1000);
					 value = sdf.format(afterDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	/**获取订单总价**/
	@Override
	public String queryPrice(String leaseOrderNo) {
		//SELECT SUM(payMoney) AS a1 FROM lease_pay WHERE leaseOrderNo = '141011293564'
		String sql = "SELECT SUM(payMoney) AS a1 FROM lease_pay WHERE leaseOrderNo = ? ";
		Object[] params = new Object[1];
		params[0] = leaseOrderNo;
		AppVo_1 vo = queryBean(AppVo_1.class, sql, params);
		if(null!=vo){
			return vo.getA1();
		}
		return "0";
	}

}
