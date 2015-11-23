package com.pig84.ab.dao;

import com.pig84.ab.vo.LeaseBaseInfo;

/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 订单相关
 */
public interface ILeaseDao {

	/**根据订单号获取订单信息**/
	public LeaseBaseInfo queryLeaseBaseInfo(String orderNo);

	/**退票--原来的总票价**/
	public String queryReturnTicketOldMoney(String orderNo);

	/**获取订单的支付方式**/
	public String queryLeasePayType(String orderNo);

	/**检查该退款记录是否存在**/
	public boolean checkReturnCount(String localIds, String lineClassIds,
			String orderNo, String passengerId, String realBack);

	/**添加退款记录信息**/
	public void addReturnMoneyCount(String localIds, String lineClassIds,
			String orderNo, String passengerId, String realBack);

	/**检查是否已经申请过退票---如果申请了,则不再重复申请**/
	public int qeryReturnTicket(String lineClassIds, String orderNo, String passengerId);

	public String getOrderTime(String orderNo);

	public String queryPrice(String leaseOrderNo);

}
