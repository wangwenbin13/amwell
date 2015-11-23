package com.amwell.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amwell.commons.MyDate;
import com.amwell.commons.QueryHelper;
import com.amwell.dao.IPublishLineDao;
import com.amwell.infrastructure.config.HolidayConfig;
import com.amwell.service.ILineService;
import com.amwell.service.IPublishLineService;
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
import com.google.gson.Gson;

/**
 * 发布线路服务
 * 
 * @author shawn.zheng
 * 
 */
@Service("publishLineService")
@Transactional
public class PublishLineServiceImpl implements IPublishLineService {

	private Logger logger = Logger.getLogger(PublishLineServiceImpl.class);

	@Autowired
	private IPublishLineDao publishLineDao;

	@Autowired
	private ILineService lineService;

	/**
	 * 获取所有的站点
	 */
	public List<StationInfo> getAllStations() {
		return publishLineDao.getAllStations();
	}

	/**
	 * 1 保存线路的基本信息（保存站点，保存线路基础信息，同步拆分线路）
	 * 
	 * @param adminUserId
	 *            管理员id
	 * 
	 * @param stationArrJson
	 *            站点信息JsonArray
	 * 
	 * @param lineBaseInfo
	 *            线路信息
	 * @param resultMap
	 *            返回结果
	 * 
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String saveLineBaseInfo(String adminUserId, String stationArrStr, LineBaseInfo lineBaseInfo,
			Map<String, String> resultMap) throws Exception {
		if (StringUtils.isEmpty(adminUserId)) {
			throw new RuntimeException("adminUserId 为空");
		}
		if (lineBaseInfo == null) {
			throw new RuntimeException("lineBaseInfo 为空");
		}
		if (StringUtils.isEmpty(lineBaseInfo.getLineManager())) {
			throw new RuntimeException("lineManager 为空");
		}
		if (StringUtils.isEmpty(lineBaseInfo.getProvinceCode())) {
			throw new RuntimeException("provinceCode 为空");
		}
		if (StringUtils.isEmpty(lineBaseInfo.getCityCode())) {
			throw new RuntimeException("cityCode 为空");
		}
		if (StringUtils.isEmpty(lineBaseInfo.getLineName())) {
			throw new RuntimeException("lineName 为空");
		}
		if (StringUtils.isEmpty(lineBaseInfo.getLineStatus())) {
			throw new RuntimeException("lineStatus 为空");
		}
		if (StringUtils.isEmpty(lineBaseInfo.getLineTime())) {
			throw new RuntimeException("lineTime 为空");
		}
		if (StringUtils.isEmpty(lineBaseInfo.getOrderPrice())) {
			throw new RuntimeException("orderPrice 为空");
		}
		if (StringUtils.isEmpty(lineBaseInfo.getLineSubType())) {
			throw new RuntimeException("lineSubType 为空");
		}
		if (StringUtils.isEmpty(stationArrStr)) {
			throw new RuntimeException("stationArrStr 为空");
		}
		Gson gson = new Gson();
		List<Map<String, Object>> stationArr = gson.fromJson(stationArrStr, List.class);
		if (stationArr == null || stationArr.size() == 0) {
			throw new RuntimeException("stationArrJson 为空");
		}
		boolean isExist = isExistLine(lineBaseInfo.getLineName(), lineBaseInfo.getLineBaseId());
		if (isExist) {
			throw new RuntimeException("系统已存在该条线路，请另外起一个线路名称");
		}
		// 上车点id集合
		List<String> upStationList = new ArrayList<String>();
		// 下车点id集合
		List<String> downStationList = new ArrayList<String>();
		LineBaseInfo oldLineBaseInfo = null;
		if (!StringUtils.isEmpty(lineBaseInfo.getLineBaseId())) {
			oldLineBaseInfo = publishLineDao.getLineBaseInfoById(lineBaseInfo.getLineBaseId());
		} else {
			lineBaseInfo.setLineStatus("0");
		}
		lineBaseInfo.setLineType("1");
		lineBaseInfo.setCreateBy(adminUserId);
		lineBaseInfo.setUpdateBy(adminUserId);
		String nowTime = MyDate.formatTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
		lineBaseInfo.setCreateOn(nowTime);
		lineBaseInfo.setUpdateOn(nowTime);
		// 保存线路基础信息
		publishLineDao.syncLineBaseInfo(lineBaseInfo);
		// 同步班次价格
		boolean handleFlag = handleClassPriceSync(lineBaseInfo, oldLineBaseInfo);
		resultMap.put("isChangeFlag", String.valueOf(handleFlag));
		// 保存站点信息
		parseStationData(stationArr, adminUserId, upStationList, downStationList, lineBaseInfo);
		// 同步线路拆分信息
		publishLineDao.batchSplitInfoByLineId(lineBaseInfo.getLineBaseId());
		if (StringUtils.isEmpty(lineBaseInfo.getLineBaseId())) {
			throw new RuntimeException("lineBaseId 为空");
		}
		return lineBaseInfo.getLineBaseId();
	}

	/**
	 * 同步班次价格
	 * 
	 * @param lineBaseInfo
	 *            当前线路信息
	 * @param oldLineBaseInfo
	 *            数据库线路信息
	 */
	private boolean handleClassPriceSync(LineBaseInfo lineBaseInfo, LineBaseInfo oldLineBaseInfo) {
		if (oldLineBaseInfo == null) {
			return false;
		}
		// 未改变价格
		if (lineBaseInfo.getOrderPrice().equals(oldLineBaseInfo.getOrderPrice())) {
			return false;
		}
		List<LineClassEntity> lineClassEntityList = publishLineDao
				.getLineClassListAfterToday(lineBaseInfo.getLineBaseId());
		if (lineClassEntityList == null) {
			return false;
		}
		for (LineClassEntity lineClassEntity : lineClassEntityList) {
			// 未设优惠价
			if (lineClassEntity.getPrice().equals(oldLineBaseInfo.getOrderPrice())) {
				// 改变班次价格
				publishLineDao.updateClassPrice(lineClassEntity.getLineClassId(), lineBaseInfo.getOrderPrice());
			}
		}
		return true;
	}

	/**
	 * 根据线路Id获取线路信息
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @return
	 */
	public LineBaseInfo getLineBaseInfoById(String lineBaseId) {
		return publishLineDao.getLineBaseInfoById(lineBaseId);
	}

	/**
	 * 根据线路数据获取关联的站点列表
	 * 
	 * @param lineBaseInfo
	 *            线路信息
	 * 
	 * @return
	 * 
	 */
	public List<LineStationVo> getLineStationEntityListByLineInfo(LineBaseInfo lineBaseInfo) {
		List<LineStationVo> lineStationVoList = new ArrayList<LineStationVo>();
		List<StationInfo> stationInfoList = publishLineDao.listStationInfoByLineId(lineBaseInfo.getLineBaseId());
		for (StationInfo stationInfo : stationInfoList) {
			LineStationVo lineStationVo = new LineStationVo();
			lineStationVo.setStationInfoId(stationInfo.getId());
			lineStationVo.setLati(String.valueOf(stationInfo.getBaiduLat()));
			lineStationVo.setLoni(String.valueOf(stationInfo.getBaiduLng()));
			lineStationVo.setStationName(stationInfo.getName());
			lineStationVo.setArriveTime(stationInfo.getArriveTime());
			lineStationVo.setStationType(String.valueOf(stationInfo.getType()));
			String stationName = stationInfo.getName();
			if (stationName.contains("（公交站）") || stationName.contains("(公交站)")) {
				lineStationVo.setStationSuffix("busStation");
			} else if (stationName.contains("（地铁站）") || stationName.contains("(地铁站)")) {
				lineStationVo.setStationSuffix("metroStation");
			} else {
				lineStationVo.setStationSuffix("default");
			}
			lineStationVo.setTipdesc(stationInfo.getTipdesc());
			lineStationVoList.add(lineStationVo);
		}
		return lineStationVoList;
	}

	public int getMaxOrderNumByLineId(String lineId) {
		return publishLineDao.getMaxOrderNumByLineId(lineId);
	}

	/**
	 * 根据线路id获取关联的班次列表
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @return
	 * 
	 */
	public List<LineClassEntity> getLineClassEntityList(String lineBaseId) {
		return publishLineDao.getLineClassEntityListByLineBaseId(lineBaseId);
	}

	/**
	 * 保存班次信息
	 * 
	 * @param lineBaseId
	 *            线路id
	 * 
	 * @param orderStartTime
	 *            班次时间
	 * 
	 * @param orderSeats
	 *            座位数
	 * 
	 * @param session
	 *            会话
	 * 
	 * @param orderDateArr
	 *            排班日期
	 * @param deletingOrderDates 删除的班次日期
	 */
	public void addLineClassInfoData(String lineBaseId, String orderStartTime, String orderSeats, HttpSession session,
			String orderDateArr,String deletingOrderDates) {
		if (StringUtils.isEmpty(lineBaseId)) {
			throw new RuntimeException("lineBaseId 为空");
		}
		if (StringUtils.isEmpty(orderStartTime)) {
			throw new RuntimeException("orderStartTime 为空");
		}
		if (StringUtils.isEmpty(orderSeats)) {
			throw new RuntimeException("orderSeats 为空");
		}
		if (StringUtils.isEmpty(orderDateArr)) {
			throw new RuntimeException("orderDateArr 为空");
		}
		List<LineClassEntity> lineClassEntityList = publishLineDao.getLineClassEntityListByLineBaseId(lineBaseId);
		//数据库存在的现有班次
		StringBuffer orderDateArrSession = new StringBuffer();
		String driverId = null;
		String vehicleId = null;
		if (lineClassEntityList != null && lineClassEntityList.size() > 0) {
			driverId = lineClassEntityList.get(0).getDriverId();
			vehicleId = lineClassEntityList.get(0).getVehicleId();
			for (LineClassEntity lineClassEntity : lineClassEntityList) {
				orderDateArrSession.append(lineClassEntity.getOrderDate()).append(",");
			}
		}
		// 新增的排班日期
		List<String> addDateList = new ArrayList<String>();
		// 取消的排班日期
		List<String> deleteDateList = new ArrayList<String>();
		String[] orderDateArray = orderDateArr.split(";");
		
		//删除的班次日期
		String[] delOrderDate = {};
		if(!StringUtils.isEmpty(deletingOrderDates)){
			delOrderDate = deletingOrderDates.split(";");
		}
		
		// 计算删除的排班日期
		for (String orderDate : delOrderDate) {
			deleteDateList.add(orderDate);
		}
		// 计算新增的排班日期
		for (String orderDate : orderDateArray) {
			if (!orderDateArrSession.toString().contains(orderDate)) {
				if (!HolidayConfig.isHoliday(orderDate)) {
					addDateList.add(orderDate);
				}
			}
		}
		
		LineBaseInfo lineBaseInfo = publishLineDao.getLineBaseInfoById(lineBaseId);
		String businessId = null;
		if (lineBaseInfo != null) {
			businessId = lineBaseInfo.getBusinessId();
		}
		List<LineClassEntity> addLineClassEntityList = new ArrayList<LineClassEntity>();
		for (String orderDate : addDateList) {
			LineClassEntity lineClassEntity = new LineClassEntity();
			lineClassEntity.setLineBaseId(lineBaseId);
			lineClassEntity.setOrderDate(orderDate);
			lineClassEntity.setLineBusinessId(businessId);
			lineClassEntity.setOrderSeats(Integer.parseInt(orderSeats));
			lineClassEntity.setPrice(lineBaseInfo.getOrderPrice());
			lineClassEntity.setOrderStartTime(orderStartTime);
			lineClassEntity.setDelFlag(0);
			lineClassEntity.setDriverId(driverId);
			lineClassEntity.setVehicleId(vehicleId);
			addLineClassEntityList.add(lineClassEntity);
		}
		publishLineDao.syncLineClassInfoList(addLineClassEntityList);
		publishLineDao.updateLineClassSeats(lineBaseId, orderSeats);
		for (String orderDate : deleteDateList) {
			publishLineDao.deleteClassInfoList(lineBaseId, orderStartTime, orderDate);
		}
	}

	/**
	 * 1.1 解析站点数据
	 * 
	 * @param stationArrJson
	 *            站点json列表
	 * 
	 * @param adminUserId
	 *            管理员Id
	 * 
	 * @param upStationList
	 *            上车站点列表 (被填充)
	 * 
	 * @param downStationList
	 *            下车站点列表（被填充）
	 * 
	 * @param lineBaseInfo
	 *            线路信息（被填充）
	 * 
	 */
	private void parseStationData(List<Map<String, Object>> stationArrJson, String adminUserId,
			List<String> upStationList, List<String> downStationList, LineBaseInfo lineBaseInfo) {
		if (stationArrJson == null || stationArrJson.size() == 0) {
			throw new RuntimeException("stationArrJson 为空");
		}
		if (StringUtils.isEmpty(adminUserId)) {
			throw new RuntimeException("adminUserId 为空");
		}
		if (lineBaseInfo == null) {
			throw new RuntimeException("lineBaseInfo 为空");
		}
		// 删除本次不存在的站点
		List<StationInfo> oldStationInfoList = publishLineDao.listStationInfoByLineId(lineBaseInfo.getLineBaseId());
		List<String> nowStationList = new ArrayList<String>();
		for (int index = 0; index < stationArrJson.size(); index++) {
			Map<String, Object> stationJson = stationArrJson.get(index);
			if (stationJson.containsKey("stationName")) {
				nowStationList.add((String) stationJson.get("stationInfoId"));
			}
		}
		for (StationInfo oldStationInfo : oldStationInfoList) {
			boolean flag = true;
			for (String nowStation : nowStationList) {
				if (nowStation!=null&&nowStation.equals(oldStationInfo.getId())) {
					flag = false;
					break;
				}
			}
			if (flag) {
				publishLineDao.deleteStationById(oldStationInfo.getId());
			}
		}
		// 添加新增的点或修改修改的点
		for (int index = 0; index < stationArrJson.size(); index++) {
			Map<String, Object> stationJson = stationArrJson.get(index);
			String stationType = (String) stationJson.get("stationType");
			StationInfo stationInfo = new StationInfo();
			if (stationJson.containsKey("stationInfoId")) {
				stationInfo.setId((String) stationJson.get("stationInfoId"));
			}
			if (stationJson.containsKey("stationSuffix")) {
				String stationName = (String) stationJson.get("stationName");
				stationName = stationName.replace("(公交站)", "");
				stationName = stationName.replace("（公交站）", "");
				stationName = stationName.replace("(地铁站)", "");
				stationName = stationName.replace("（地铁站）", "");
				stationName = stationName.replace("公交车", "");
				stationName = stationName.replace("公交", "");
				stationName = stationName.replace("地铁站", "");
				stationName = stationName.replace("地铁", "");
				stationName = stationName.replace("（", "(");
				stationName = stationName.replace("）", ")");
				String stationSuffix = (String) stationJson.get("stationSuffix");
				if ("busStation".equals(stationSuffix)) {
					if (!stationName.contains("公交站")) {
						stationName = stationName + "(公交站)";
					}
				} else if ("metroStation".equals(stationSuffix)) {
					if (!stationName.contains("地铁站")) {
						stationName = stationName + "(地铁站)";
					}
				}
				stationInfo.setName(stationName);
			} else {
				stationInfo.setName((String) stationJson.get("stationName"));
			}
			stationInfo.setArriveTime((String) stationJson.get("arriveTime"));
			stationInfo.setLineId(lineBaseInfo.getLineBaseId());
			stationInfo.setBaiduLng(Double.valueOf((String) stationJson.get("lng")));
			stationInfo.setBaiduLat(Double.valueOf((String) stationJson.get("lat")));
			stationInfo.setSortNum(index);
			if(stationJson.containsKey("tipdesc")){
				stationInfo.setTipdesc((String)stationJson.get("tipdesc"));
			}
			// 同步站点数据，获取站点id
			if (stationType.equals("1")) {
				stationInfo.setType(1);
				syncStationInfo(stationInfo);
				upStationList.add(stationInfo.getId());
			} else if (stationType.equals("0")) {

				stationInfo.setType(0);
				syncStationInfo(stationInfo);
				downStationList.add(stationInfo.getId());
			} else {
				stationInfo.setType(2);
				syncStationInfo(stationInfo);
			}
		}
	}

	/**
	 * 1.1.1 同步站点信息（同步数据到数据库，并返回站点Id）
	 * 
	 * @param lineStationEntity
	 *            站点信息
	 * 
	 * @param adminUserId
	 *            管理员Id
	 * 
	 * @return
	 * 
	 */
	private String syncStationInfo(StationInfo lineStationEntity) {
		publishLineDao.syncLineStation(lineStationEntity);
		return lineStationEntity.getId();
	}

	/**
	 * 更新班次的价格
	 * 
	 * @param lineClassEntityList
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void updateLineClassPrice(String classPriceInfoArrJson, String lineBaseId, String orderStartTime) {
		if (StringUtils.isEmpty(classPriceInfoArrJson)) {
			throw new RuntimeException("classPriceInfoArrJson 为空");
		}
		if (StringUtils.isEmpty(lineBaseId)) {
			throw new RuntimeException("lineBaseId 为空");
		}
		if (StringUtils.isEmpty(orderStartTime)) {
			throw new RuntimeException("orderStartTime 为空");
		}
		Gson gson = new Gson();
		List<Map<String, Object>> classPriceArray = gson.fromJson(classPriceInfoArrJson, List.class);
		List<LineClassEntity> lineClassEntityList = new ArrayList<LineClassEntity>();
		for (int index = 0; index < classPriceArray.size(); index++) {
			Map<String, Object> classPrice = classPriceArray.get(index);
			LineClassEntity lineClassEntity = new LineClassEntity();
			lineClassEntity.setLineBaseId(lineBaseId);
			lineClassEntity.setOrderDate((String) classPrice.get("orderDate"));
			lineClassEntity.setOrderStartTime(orderStartTime);
			lineClassEntity.setPrice((String) classPrice.get("price"));
			lineClassEntityList.add(lineClassEntity);
		}
		publishLineDao.updateLineClassPrice(lineClassEntityList);
	}

	/**
	 * 添加专车线路关联信息
	 * 
	 * @param companyId
	 *            公司id
	 * 
	 * @param lineBaseId
	 *            线路id
	 * 
	 */
	public void addCompanyLine(String companyId, String lineBaseId) {
		CompanyLine companyLine = new CompanyLine();
		companyLine.setCompanyId(companyId);
		companyLine.setLineBaseId(lineBaseId);
		publishLineDao.addCompanyLine(companyLine);
	}

	public void saveCommit(String companyId, String lineBaseId, String status, SysAdminVo sysAdmin) {
		if (StringUtils.isEmpty(lineBaseId)) {
			throw new RuntimeException("lineBaseId 为空");
		}
		if (StringUtils.isEmpty(status)) {
			throw new RuntimeException("status 为空");
		}
		if (!StringUtils.isEmpty(companyId)) {
			CompanyLine companyLine = new CompanyLine();
			companyLine.setCompanyId(companyId);
			companyLine.setLineBaseId(lineBaseId);
			publishLineDao.addCompanyLine(companyLine);
		}
		LineBaseInfo oldLine = publishLineDao.getLineBaseInfoById(lineBaseId);
		publishLineDao.updateLineStatus(lineBaseId, status);
		LineBaseInfo lineBaseInfo = publishLineDao.getLineBaseInfoById(lineBaseId);
		List<LineClassEntity> lineClassEntityList = publishLineDao.getLineClassEntityListByLineBaseId(lineBaseId);
		logger.info("status=" + status);
		if (status.equals("3")) {
			setLineSplitStatus(lineBaseId, "1");
			//线路从删除或下线变为上线状态，需要启动其站点
			if("4".equals(oldLine.getLineStatus())||"5".equals(oldLine.getLineStatus())){
				publishLineDao.turnOnStationByLineId(lineBaseId);
			}
			lineService.addImGroupInfo(sysAdmin.getUserId(), lineBaseInfo.getLineName(),
					String.valueOf(lineClassEntityList.get(0).getOrderSeats()), lineBaseId);
			logger.info("lineBaseId=" + lineBaseId);
		} else {
			// 删除线路相关的拆分线路
			setLineSplitStatus(lineBaseId, "0");
			// 删除线路相关的站点
			// publishLineDao.deleteStationByLineId(lineBaseId);
		}
	}

	/**
	 * 获取专车线路关联信息
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @return
	 */
	public CompanyLine getCompanyLine(String lineBaseId) {
		return publishLineDao.getCompanyLine(lineBaseId);
	}

	/**
	 * 根据Id获取公司信息
	 * 
	 * @param companyId
	 *            公司id
	 * 
	 * @return
	 * 
	 */
	public Company getCompanyById(String companyId) {
		return publishLineDao.getCompanyById(companyId);
	}

	/**
	 * 变更线路状态
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @param status
	 *            线路状态
	 * 
	 */
	public void updateLineStatus(String lineBaseId, String status) {
		publishLineDao.updateLineStatus(lineBaseId, status);
	}

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
	public PageBean queryBusinessPageBean(int parseInt, int pageSize, QueryHelper query) {
		return publishLineDao.getMgrBusinessPageBean(parseInt, pageSize, query);
	}

	/**
	 * 设置线路的商家
	 * 
	 * @param lineBaseId
	 *            线路id
	 * 
	 * @param businessId
	 *            商家id
	 * 
	 */
	public void saveLineBusiness(String lineBaseId, String businessId) {
		if (StringUtils.isEmpty(businessId)) {
			throw new RuntimeException("businessId 为空");
		}
		if (StringUtils.isEmpty(lineBaseId)) {
			throw new RuntimeException("lineBaseId 为空");
		}
		publishLineDao.setLineBusiness(lineBaseId, businessId);
	}

	/**
	 * 获取排班信息
	 * 
	 * @param lineBaseId
	 *            线路Id
	 * 
	 * @return
	 * 
	 */
	public List<LineClassScheduleVo> getLineClassScheduleVoList(String lineBaseId) {
		return publishLineDao.getLineClassScheduleVoList(lineBaseId);
	}

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
	public void setLineSplitStatus(String lineBaseId, String status) {
		publishLineDao.setLineSplitStatus(lineBaseId, status);
	}

	/**
	 * 批量处理拆分线路
	 */
	public String batchSplitLine() {
		StringBuffer message = new StringBuffer();
		message.append(publishLineDao.batchSplitInfo());
		publishLineDao.syncLeaseBaseSplitInfo();
		return message.toString();
	}

	/**
	 * 判断线路是否存在
	 * 
	 * @param lineName
	 *            线路名称
	 * @param lineBaseId
	 *            线路id
	 * 
	 * @return
	 * 
	 */
	public boolean isExistLine(String lineName, String lineBaseId) {
		LineBaseInfo lineBaseInfo = publishLineDao.getLineBaseInfoByLineName(lineName);
		if (lineBaseInfo == null) {
			return false;
		} else {
			return !lineBaseInfo.getLineBaseId().equals(lineBaseId);
		}
	}

	/**
	 * 拆分站点
	 */
	public void splitAllLineStation() throws Exception {
		publishLineDao.splitAllLineStation();
	}

	/*
	 * 根据线路获取站点信息
	 */
	public List<StationInfo> getStationListById(String lineBaseId) {
		return publishLineDao.listStationInfoByLineId(lineBaseId);
	}

	/** 修改商户 **/
	public String changeBusiness(String oldBusiness, String newBusiness, String seatCount, String lineId) {
		return publishLineDao.changeBusiness(oldBusiness, newBusiness, seatCount, lineId);
	}

	/**
	 * 获取未排班日期的最早一天
	 * 
	 * @param lineId
	 *            线路id
	 * @return
	 */
	public String getNotSchedulingDate(String lineId) {
		String maxOrderDate = publishLineDao.getMaxOrderDateByLineId(lineId);
		long maxOrderDateValue = MyDate.strToUTCLong(maxOrderDate, "yyyy-MM-dd");
		long notSchedulingDateValue = maxOrderDateValue + (60 * 60 * 24L) * 1000;
		String notSchedulingDate = MyDate.formatTime(notSchedulingDateValue, "yyyy-MM-dd");
		return notSchedulingDate;
	}

	public boolean isBuyForStation(String stationId) {
		return publishLineDao.isBuyForStation(stationId);
	}

	/**
	 * 跟椐线路查询班次排班列表
	 * 
	 * @param lineBaseId
	 * @return
	 */
	public List<LineClassEntity> getLineClassEntityListByLineBaseId(String lineBaseId) {

		return publishLineDao.getLineClassEntityListByLineBaseId(lineBaseId);
	}

	/**
	 * 记录线路操作记录
	 * 
	 * @param operaVo
	 * @return
	 */
	public int logLineOperate(LineOperateLogVo operaVo) {
		return publishLineDao.logLineOperate(operaVo);
	}

	/**根据用户名查找运营人员信息
	 * @param lineManager
	 * @return
	 */
	public SysAdminVo getlineManagerInfoByName(String lineManager) {
		return publishLineDao.getlineManagerInfoByName(lineManager);
	}
}
