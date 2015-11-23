package com.pig84.ab.dao;

import java.util.List;

import com.pig84.ab.vo.TicketInfo;
import com.pig84.ab.vo.bean.AppVo_11;
import com.pig84.ab.vo.bean.AppVo_14;

/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 车票相关
 */
public interface ITicketDao {

	/**车票列表**/
	public List<TicketInfo> queryTicketList(int pageSize, int currentPage,String passengerId,String date);

	/**当天有购买的班次**/
	public List<AppVo_11> queryHasTickList(String passengerId, String date);

	/**展示电子票V2.3**/
	public AppVo_14 showTicket_V2_3(String leaseOrderNo, String passengerId,String cid);

}
