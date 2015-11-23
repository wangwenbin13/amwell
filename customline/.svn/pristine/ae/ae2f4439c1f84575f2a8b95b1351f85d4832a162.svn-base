package com.amwell.dao;

import java.util.List;

import com.amwell.commons.QueryHelper;
import com.amwell.entity.LineSplitInfo;
import com.amwell.vo.Company;
import com.amwell.vo.CompanyLine;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineClassScheduleVo;
import com.amwell.vo.LineOperateLogVo;
import com.amwell.vo.PageBean;
import com.amwell.vo.StationInfo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.app.LeaseBaseInfo;
import com.amwell.vo.app.LineBaseInfo;

public interface IPublishLineDao {

	/**
	 * 获取所有的站点
	 */
	public List<StationInfo> getAllStations();

	/**
	 * 根据线路id获取站点列表
	 * 
	 * @param lineId
	 *            线路Id
	 */
	public List<StationInfo> listStationInfoByLineId(String lineId);

	/**
	 * 根据线路id获取全部站点列表(包括不使用)
	 * 
	 * @param lineId
	 *            线路Id
	 */
	public List<StationInfo> queryAllListStationInfoByLineId(String lineId);

	/**
	 * 获取一条线路的最大购票数（今天以后）
	 * 
	 * @param lineId
	 * @return
	 */
	public int getMaxOrderNumByLineId(String lineId);

	/**
	 * 根据id删除站点
	 * 
	 * @param id
	 *            站点Id
	 */
	public void deleteStationById(String id);

	/**
	 * 添加站点
	 * 
	 * @param list
	 * 
	 */
	public void syncLineStation(StationInfo stationInfo);

	/**
	 * 开启站点状态
	 * 
	 * @param lineBaseId
	 *            线路Id
	 */
	public void turnOnStationByLineId(String lineBaseId);

	/**
	 * 同步线路信息
	 * 
	 * @param lineBaseInfo
	 *            线路信息
	 * 
	 */
	public void syncLineBaseInfo(LineBaseInfo lineBaseInfo);

	/**
	 * 同步拆分线路信息
	 * 
	 * @param lineSplitInfo
	 *            拆分线路信息
	 * 
	 */
	public void syncLineSplitInfo(LineSplitInfo lineSplitInfo);

	/**
	 * 根据id获取线路信息
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @return
	 * 
	 */
	public LineBaseInfo getLineBaseInfoById(String lineBaseId);

	/**
	 * 删除拆分线路 根据上车点
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @param upStation
	 *            上车点Id
	 * 
	 * @return
	 */
	public int deleteLineSplitInfoByUpStation1(String lineBaseId, String upStation);

	/**
	 * 删除拆分线路 根据下车点
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @param downStation
	 *            下车点
	 * 
	 * @return
	 */
	public int deleteLineSplitInfoByDownStation(String lineBaseId, String downStation);

	/**
	 * 根据线路Id获取班次信息列表
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @return
	 */
	public List<LineClassEntity> getLineClassEntityListByLineBaseId(String lineBaseId);

	/**
	 * 同步班次信息
	 * 
	 * @param lineClassEntity
	 *            班次信息
	 * 
	 */
	public void syncLineClassInfo(LineClassEntity lineClassEntity);

	/**
	 * 同步班次列表
	 * 
	 * @param lineClassEntityList
	 *            班次列表
	 * 
	 */
	public void syncLineClassInfoList(List<LineClassEntity> lineClassEntityList);

	/**
	 * 根据条件查询班次信息
	 * 
	 * @param lineBaseId
	 *            线路id
	 * @param orderStartTime
	 *            发车时间
	 * @param orderDate
	 *            班次日期
	 * @return
	 */
	public LineClassEntity getLineClassEntityByCondition(String lineBaseId, String orderStartTime, String orderDate);

	/**
	 * 删除班次信息
	 * 
	 * @param lineBaseId
	 *            线路id
	 * @param orderStartTime
	 *            发车时间
	 * @param orderDate
	 *            排班日期
	 */
	public void deleteClassInfoList(String lineBaseId, String orderStartTime, String orderDate);

	public String batchSplitInfo();

	public void batchSplitInfoByLineId(String lineBaseId);

	/**
	 * 更新班次的价格
	 * 
	 * @param lineClassEntityList
	 *            班次信息列表
	 * 
	 */
	public void updateLineClassPrice(List<LineClassEntity> lineClassEntityList);

	/**
	 * 添加专车线路关联信息
	 * 
	 * @param companyLine
	 */
	public void addCompanyLine(CompanyLine companyLine);

	/**
	 * 获取专车线路关联系想你
	 * 
	 * @param companyLine
	 * 
	 * @return
	 * 
	 */
	public CompanyLine getCompanyLine(CompanyLine companyLine);

	/**
	 * 获取专车线路相关信息
	 * 
	 * @param lineBaseId
	 *            线路id
	 * 
	 * @return
	 * 
	 */
	public CompanyLine getCompanyLine(String lineBaseId);

	/**
	 * 根据id获取公司信息
	 * 
	 * @param companyId
	 *            公司id
	 * 
	 * @return
	 * 
	 */
	public Company getCompanyById(String companyId);

	/**
	 * 变更线路状态
	 * 
	 * @param lineBaseId
	 *            线路id
	 * 
	 * @param status
	 *            线路状态
	 * 
	 */
	public void updateLineStatus(String lineBaseId, String status);

	/**
	 * 获取商家列表
	 * 
	 * @param parseInt
	 *            页码
	 * 
	 * @param pageSize
	 *            一页大小
	 * 
	 * @param query
	 *            查询条件
	 * 
	 * @return
	 * 
	 */
	public PageBean getMgrBusinessPageBean(int parseInt, int pageSize, QueryHelper query);

	/**
	 * 更新线路的商家
	 * 
	 * @param lineBaseId
	 *            线路id
	 * 
	 * @param businessId
	 *            商家id
	 * 
	 */
	public void setLineBusiness(String lineBaseId, String businessId);

	/**
	 * 获取排班信息
	 * 
	 * @param lineBaseId
	 *            线路id
	 * 
	 * @return
	 * 
	 */
	public List<LineClassScheduleVo> getLineClassScheduleVoList(String lineBaseId);

	/**
	 * 获取线路列表
	 * 
	 * @return
	 */
	public List<LineBaseInfo> getLineList();

	/**
	 * 获取拆分线路列表根据线路Id
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @return
	 * 
	 */
	public List<LineSplitInfo> getLineSplitByLineBaseId(String lineBaseId);

	/**
	 * 更新拆分线路的状态
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @param status
	 *            状态
	 * 
	 */
	public void setLineSplitStatus(String lineBaseId, String status);

	/**
	 * 获取订单列表
	 * 
	 * @return
	 */
	public List<LeaseBaseInfo> getLeaseBaseInfoList();

	/**
	 * 获取拆分线路id
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * @param uStation
	 *            上车点
	 * @param dStation
	 *            下车点
	 * @return
	 */
	public LineSplitInfo getLineSplitByCondition(String lineBaseId, String uStation, String dStation);

	/**
	 * 获取全部拆分线路
	 * 
	 * @return
	 */
	public List<LineSplitInfo> getAllLineSplitInfoList();

	/**
	 * 更新订单数据
	 * 
	 * @param leaseBaseInfo
	 */
	public void syncLeaseBaseInfo(LeaseBaseInfo leaseBaseInfo);

	/**
	 * 更新班次的座位数
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @param seats
	 *            座位数
	 * 
	 */
	public void updateLineClassSeats(String lineBaseId, String seats);

	/**
	 * 根据线路名称获取线路信息
	 * 
	 * @param lineName
	 *            线路名称
	 * 
	 * @return
	 * 
	 */
	public LineBaseInfo getLineBaseInfoByLineName(String lineName);

	/**
	 * 同步订单的拆分信息
	 */
	public void syncLeaseBaseSplitInfo();

	/**
	 * 根据条件获取站点信息
	 * 
	 * @param lineBaseId
	 *            线路id
	 * @param lng
	 *            经度
	 * @param lat
	 *            纬度
	 * @return
	 */
	public StationInfo getStationEntityByCondition(String lineBaseId, String lng, String lat);

	/**
	 * 拆分站点
	 */
	public void splitAllLineStation() throws Exception;

	/** 修改商户 **/
	public String changeBusiness(String oldBusiness, String newBusiness, String seatCount, String lineId);

	/**
	 * 获取线路的班次的最大日期
	 * 
	 * @param lineId
	 *            线路id
	 * @return
	 */
	public String getMaxOrderDateByLineId(String lineId);

	/**
	 * 获取某条线路今天起排的班次列表
	 * 
	 * @param lineBaseId
	 *            线路列表
	 * @return
	 */
	public List<LineClassEntity> getLineClassListAfterToday(String lineBaseId);

	/**
	 * 修改班次价格
	 * 
	 * @param lineClassId
	 *            班次Id
	 * @param price
	 *            价格
	 */
	public void updateClassPrice(String lineClassId, String price);

	/** 判断一个站点是否被已购票的订单引用（当前时间往后） */
	public boolean isBuyForStation(String stationId);

	/** 根据线路Id删除站点 lineId 线路Id */
	public void deleteStationByLineId(String lineId);

	public void setAutoPutTicket(String lineBaseId, String autoPutTicket);

	public LineClassEntity getMaxDateClassEntity(String lineBaseId);
	
	public LineClassEntity getByLineBaseIdAndOrderDate(String lineBaseId,String orderDate);

	public List<LineBaseInfo> listAutoPutTicketLine();

	/**
	 * 记录线路操作记录
	 * 
	 * @param operaVo
	 * @return
	 */
	public int logLineOperate(LineOperateLogVo operaVo);

	/**根据用户名查找运营人员信息
	 * @param lineManager
	 * @return
	 */
	public SysAdminVo getlineManagerInfoByName(String lineManager);
}
