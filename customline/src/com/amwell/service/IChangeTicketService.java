package com.amwell.service;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.PassengerOrderStatusVo;
import com.amwell.vo.ticket.UserApplicationChangeVo;

public interface IChangeTicketService {

	Map<String, Object> getTicketList(Search search, int currentPageIndex,int pageSize);

	Map<String, Object> changedTicketList(Search search, int currentPageIndex,int pageSize);

	List<PassengerOrderStatusVo> getPassengerOrderList(String lineBaseId,String orderStartTime, String passengerId, String beginOrderDate,String endOrderDate);

	int changeTicket(String lineBaseId, String leaseOrderId,String passengerId, String orderStartTime, String orderDate,String newLineBaseId, String newOrderStartTime, String newOrderDate,String userId);

	/**用户申请改签页面**/
	Map<String, Object> userChangeTicket(Search search, int currentPageIndex,
			int pageSize);

	/**拒绝改签车票**/
	int refuceReturnTicket(String localId,String refusemess,String telephone);

	/**执行改签车票**/
	int doReturnTicket(UserApplicationChangeVo applicationChangeVo);
}
