package com.pig84.ab.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.IStationInfoDao;
import com.pig84.ab.dao.ITicketDao;
import com.pig84.ab.dao.impl.RePeat;
import com.pig84.ab.service.ITicketService;
import com.pig84.ab.vo.QueryTicketResult;
import com.pig84.ab.vo.StationInfo;
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
@Service("ticketService")
public class TicketServiceImpl implements ITicketService {

	@Autowired
	private ITicketDao ticketDao;
	
	@Autowired
	private IStationInfoDao stationInfoDao;
	
	@Autowired
	private RePeat rePeat;
	
	/**车票列表**/
	public QueryTicketResult queryTicketList(int pageSize, int currentPage,String passengerId) {
		QueryTicketResult queryTicketResult = new QueryTicketResult();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		List<TicketInfo> list = ticketDao.queryTicketList(pageSize,currentPage,passengerId,date);
		//当天有购买的班次
		List<AppVo_11> hasTicketList = ticketDao.queryHasTickList(passengerId,date);
		//当天有购买班次的map
		Map<String, String> hasTickMaps = new HashMap<String, String>();
		if(null!=hasTicketList && hasTicketList.size()>0){
			for(AppVo_11 vo10:hasTicketList ){
				hasTickMaps.put(vo10.getA11(), "1");
			}
		}
		if(null!=list && list.size()>0){
			//拼接所有的站点
			String stationIds = "";
			for(TicketInfo ticketInfo : list){
				if(null!=ticketInfo){
					if(!StringUtils.isEmpty(ticketInfo.getA1())){
						stationIds += ticketInfo.getA7().split(",")[0]+",";//追加   主线路的 起点
					}
					if(!StringUtils.isEmpty(ticketInfo.getA9())){
						stationIds += ticketInfo.getA9()+",";//追加子线路的(上车点)起点
					}
					if(!StringUtils.isEmpty(ticketInfo.getA10())){
						stationIds +=ticketInfo.getA10()+",";//追加子线路的(下车点)终点
					}
				}
			}
			//获得去重以后的所有站点
			stationIds = rePeat.quChong(stationIds);
			//根据站点ID获取站点信息
			List<StationInfo> stationInfos = stationInfoDao.queryStationById(stationIds);
			//以站点ID为key,站点名字为value的map
			Map<String,String> maps = new HashMap<String,String>();
			if(null!=stationInfos && stationInfos.size()>0){
				for(StationInfo StationInfo : stationInfos){
					maps.put(StationInfo.getId(), StationInfo.getName());
				}
			}
			//列表追加站点名字
			for(TicketInfo ticketInfo : list){
				if(null!=ticketInfo){
					if(!StringUtils.isEmpty(ticketInfo.getA7())){
						ticketInfo.setA7(maps.get(ticketInfo.getA7().split(",")[0]));//主线路的 起点
					}
					if(!StringUtils.isEmpty(ticketInfo.getA9())){
						ticketInfo.setA9(maps.get(ticketInfo.getA9()));//子线路的(上车点)起点
					}
					if(!StringUtils.isEmpty(ticketInfo.getA10())){
						ticketInfo.setA10(maps.get(ticketInfo.getA10()));//子线路的(下车点)终点
					}
					//标识是否优惠
					if(Double.parseDouble(ticketInfo.getA13())>Double.parseDouble(ticketInfo.getA6())){
						ticketInfo.setA6(ticketInfo.getA6());
						ticketInfo.setA2("1");//有优惠
					}else{
						ticketInfo.setA2("0");//没有优惠
					}
				}
				if(!StringUtils.isEmpty(hasTickMaps.get(ticketInfo.getA11()))){
					ticketInfo.setA12("1");//当天有车票
				}else{
					ticketInfo.setA12("0");//当天无车票
				}
			}
		}
		queryTicketResult.setList(list);
		queryTicketResult.setA2(date);
		return queryTicketResult;
	}

	/**展示电子票V2.3**/
	public AppVo_14 showTicket_V2_3(String leaseOrderNo, String passengerId,String cid){
		return ticketDao.showTicket_V2_3(leaseOrderNo, passengerId,cid);
	}

}
