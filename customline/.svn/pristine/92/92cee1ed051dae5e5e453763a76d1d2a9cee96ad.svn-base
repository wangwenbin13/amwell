package com.amwell.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.amwell.commons.QueryHelper;
import com.amwell.vo.Company;
import com.amwell.vo.CompanyLine;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineClassScheduleVo;
import com.amwell.vo.LineOperateLogVo;
import com.amwell.vo.LineStationVo;
import com.amwell.vo.PageBean;
import com.amwell.vo.StationInfo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.app.LineBaseInfo;

public interface IPublishLineService {
	
	/**
	 * 获取所有的站点
	 */
	public List<StationInfo> getAllStations();
	
	/**
	 * 获取一条线路的最大购票数（今天以后）
	 * 
	 * @param lineId
	 * @return
	 */
	public int getMaxOrderNumByLineId(String lineId);
	
	/**
	 * 1 保存线路的基本信息（保存站点，保存线路基础信息，同步拆分线路）
	 * 
	 * @param adminUserId 管理员id
	 * 
	 * @param stationArrJson 站点信息JsonArray
	 * 
	 * @param lineBaseInfo 线路信息
	 * @param resultMap 返回结果
	 * 
	 * @return
	 * 
	 */
	public String saveLineBaseInfo(String adminUserId,String stationArrStr,LineBaseInfo lineBaseInfo,Map<String,String> resultMap) throws Exception;
	
	/**
	 * 根据线路Id获取线路信息
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @return
	 */
	public LineBaseInfo getLineBaseInfoById(String lineBaseId);
	
	/**
	 * 根据线路数据获取关联的站点列表
	 * 
	 * @param lineBaseInfo 线路信息
	 * 
	 * @return
	 * 
	 */
	public List<LineStationVo> getLineStationEntityListByLineInfo(LineBaseInfo lineBaseInfo);
	
	/**
	 * 根据线路id获取关联的班次列表
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @return
	 * 
	 */
	public List<LineClassEntity> getLineClassEntityList(String lineBaseId);
	
	/**
	 * 保存班次信息
	 * 
	 * @param lineBaseId 线路id
	 * 
	 * @param orderStartTime 班次时间
	 * 
	 * @param orderSeats 座位数
	 * 
	 * @param session 会话
	 * 
	 * @param orderDateArr 排班日期
	 * 
	 * @param deletingOrderDates 删除的班次日期
	 * 
	 */
	public void addLineClassInfoData(String lineBaseId,String orderStartTime,String orderSeats,HttpSession session,String orderDateArr,String deletingOrderDates);
	
	/**
	 * 更新班次的价格
	 * 
	 * @param classPriceInfoArrJson
	 * @param lineBaseId
	 * @param orderStartTime
	 * 
	 */
	public void updateLineClassPrice(String classPriceInfoArrJson,String lineBaseId,String orderStartTime);
	
	/**
	 * 添加专车线路关联信息
	 * 
	 * @param companyId 公司id
	 * 
	 * @param lineBaseId 线路id
	 * 
	 */
	public void addCompanyLine(String companyId,String lineBaseId);
	
	public void saveCommit(String companyId,String lineBaseId,String status,SysAdminVo sysAdmin);
		
	
	/**
	 * 获取专车线路关联信息
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @return
	 */
	public CompanyLine getCompanyLine(String lineBaseId);
	
	/**
	 * 根据Id获取公司信息
	 * 
	 * @param companyId 公司id
	 * 
	 * @return
	 * 
	 */
	
	public Company getCompanyById(String companyId);
	/**
	 * 变更线路状态
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @param status 线路状态
	 * 
	 */
	public void updateLineStatus(String lineBaseId,String status);
	
	/**
	 * 获取商家列表
	 * 
	 * @param parseInt 页码
	 * 
	 * @param pageSize 一页大小
	 * 
	 * @param query 查询条件
	 * 
	 * @return
	 * 
	 */
	public PageBean queryBusinessPageBean(int parseInt,int pageSize,QueryHelper query);
	
	/**
	 * 设置线路的商家
	 * 
	 * @param lineBaseId 线路id
	 * 
	 * @param businessId 商家id
	 * 
	 */
	public void saveLineBusiness(String lineBaseId,String businessId);
	
	/**
	 * 获取排班信息
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @return
	 * 
	 */
	public List<LineClassScheduleVo> getLineClassScheduleVoList(String lineBaseId);
	
	/**
	 * 更新拆分线路的状态
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @param status 状态
	 * 
	 */
	public void setLineSplitStatus(String lineBaseId,String status);
	
	/**
	 * 批量处理拆分线路
	 */
	public String batchSplitLine();
	
	/**
	 * 判断线路是否存在
	 * 
	 * @param lineName 线路名称
	 * @param lineBaseId 线路Id
	 * 
	 * @return
	 * 
	 */
	public boolean isExistLine(String lineName,String lineBaseId);
	
	/**
	 * 拆分站点
	 */
	public void splitAllLineStation() throws Exception;
	
	/**
	 * 根据线路Id获取站点列表
	 * @param lineBaseId
	 * @return
	 */
	public List<StationInfo> getStationListById(String lineBaseId);
	
	/**修改商户**/
	public String changeBusiness(String oldBusiness,String newBusiness,String seatCount,String lineId);
	
	/**
	 * 获取未排班日期的最早一天
	 * @param lineId 线路id
	 * @return
	 */
	public String getNotSchedulingDate(String lineId);
	
	/** 判断一个站点是否被已购票的订单引用（当前时间往后）*/
	public boolean isBuyForStation(String stationId);

	/**
	 * 跟椐线路查询班次排班列表 
	 * @param lineBaseId
	 * @return
	 */
	public List<LineClassEntity> getLineClassEntityListByLineBaseId(String lineBaseId);

	/**
	 * 记录线路操作记录
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
