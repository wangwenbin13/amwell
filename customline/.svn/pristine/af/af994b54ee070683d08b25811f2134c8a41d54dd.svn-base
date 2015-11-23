package com.amwell.dao;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.PassengerOrderStatusVo;
import com.amwell.vo.ticket.UserApplicationChangeVo;

public interface IChangeTicketDAO {

	Map<String, Object> getTicketList(Search search, int currentPageIndex,int pageSize);
			

	Map<String, Object> changedTicketList(Search search, int currentPageIndex,int pageSize);


	List<PassengerOrderStatusVo> getPassengerOrderList(String lineBaseId,String orderStartTime, String passengerId, String beginOrderDate,String endOrderDate);


	int changeTicket(String lineBaseId, String leaseOrderId,String passengerId, String orderStartTime, String orderDate,String newLineBaseId, String newOrderStartTime, String newOrderDate,String uerId);


	/**用户申请改签页面**/
	Map<String, Object> userChangeTicket(Search search, int currentPageIndex,
			int pageSize);


	/**拒绝改签车票**/
	int refuceReturnTicket(String localId,String refusemess,String telephone);

	/**执行改签车票**/
	int doReturnTicket(UserApplicationChangeVo changeVo);

	/**判断是否存在该改签记录,如果存在,说明已经改签过了,不允许改签**/
	public  int checkChangeTicket(UserApplicationChangeVo changeVo);
			
			
}
