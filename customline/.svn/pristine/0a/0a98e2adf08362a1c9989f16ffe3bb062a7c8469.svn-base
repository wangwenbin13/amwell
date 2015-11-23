package com.amwell.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IChangeTicketDAO;
import com.amwell.entity.Search;
import com.amwell.service.IChangeTicketService;
import com.amwell.vo.PassengerOrderStatusVo;
import com.amwell.vo.ticket.UserApplicationChangeVo;
@Service("changeTicketService")
public class ChangeTicketServiceImpl implements IChangeTicketService {

	@Autowired
	private IChangeTicketDAO changeTicketDao;
	
	public Map<String, Object> getTicketList(Search search,int currentPageIndex, int pageSize) {
		return changeTicketDao.getTicketList(search,currentPageIndex, pageSize);
	}

	public Map<String, Object> changedTicketList(Search search,int currentPageIndex, int pageSize) {
		return changeTicketDao.changedTicketList(search,currentPageIndex, pageSize);
	}

	public List<PassengerOrderStatusVo> getPassengerOrderList(String lineBaseId, String orderStartTime, String passengerId,String beginOrderDate,String endOrderDate) {
		return changeTicketDao.getPassengerOrderList(lineBaseId,orderStartTime,passengerId,beginOrderDate,endOrderDate);
	}

	public int changeTicket(String lineBaseId, String leaseOrderId,String passengerId, String orderStartTime, String orderDate,String newLineBaseId, String newOrderStartTime, String newOrderDate,String userId) {
		return changeTicketDao.changeTicket(lineBaseId, leaseOrderId,passengerId, orderStartTime, orderDate,newLineBaseId, newOrderStartTime, newOrderDate,userId);
	}

	/**用户申请改签页面**/
	@Override
	public Map<String, Object> userChangeTicket(Search search,
			int currentPageIndex, int pageSize) {
		return changeTicketDao.userChangeTicket(search,currentPageIndex,pageSize);
	}

	/**拒绝改签车票**/
	@Override
	public int refuceReturnTicket(String localId,String refusemess,String telephone) {
		return changeTicketDao.refuceReturnTicket(localId,refusemess,telephone);
	}

	/**执行改签车票**/
	public int doReturnTicket(UserApplicationChangeVo changeVo) {
		//判断是否存在该改签记录,如果存在,说明已经改签过了,不允许改签
		int statu = changeTicketDao.checkChangeTicket(changeVo);
		if(statu==0){
			statu = changeTicketDao.doReturnTicket(changeVo);
		}
		return statu;
	}

}
