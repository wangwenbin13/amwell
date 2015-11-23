package com.amwell.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.dao.ILineDao;
import com.amwell.dao.IPublishLineDao;
import com.amwell.entity.Search;
import com.amwell.service.ILineService;
import com.amwell.vo.ClassMonthOrderPriceVo;
import com.amwell.vo.LineAllowanceDetailVo;
import com.amwell.vo.LineAllowanceVo;
import com.amwell.vo.LineBusinessVo;
import com.amwell.vo.LineCarMsgVo;
import com.amwell.vo.LineCityVo;
import com.amwell.vo.LineClassCarDriverVo;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineClassMonthOrderCountVo;
import com.amwell.vo.LineClassOrderPassengerVo;
import com.amwell.vo.LineClassVo;
import com.amwell.vo.LineEnrollVo;
import com.amwell.vo.LineEntity;
import com.amwell.vo.LinePassengerMonthEntity;
import com.amwell.vo.LineScheduleInfoVo;
import com.amwell.vo.LineTimeChange;
import com.amwell.vo.OrderStartTimeVo;
import com.amwell.vo.PassengerInfoVo;
import com.amwell.vo.RecruitLineStationVo;
import com.amwell.vo.StationInfo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.UserLineCallbackVo;
import com.amwell.vo.UserLineEntity;
import com.amwell.vo.app.LineBaseInfo;
import com.amwell.vo.app.bean.AppVo_2;
import com.amwell.vo.app.bean.AppVo_6;

@Service("lineService")
public class LineServiceImpl implements ILineService {

	private static final Logger log = Logger.getLogger(LineServiceImpl.class);
	
	@Autowired
	private IPublishLineDao publishLineDao;

	@Autowired
	private ILineDao lineDao;
	
	public List<StationInfo> loadStationByKeywords(String keywords){
		return lineDao.loadStationByKeywords(keywords);
	}

	public void syncLineTrack(String lineBaseId,
			List<StationInfo> stationList, List<String> newStationIdList) {
		lineDao.syncLineTrack(lineBaseId, stationList, newStationIdList);
	}

	public void syncAllLineTrackMap() {
		lineDao.syncAllLineTrackMap();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAllLines(Search search, int currentPageIndex,int pageSize) {
		Map<String, Object> map = lineDao.getAllLines(search, currentPageIndex, pageSize);
		List<LineBusinessVo> lineList = (List<LineBusinessVo>)map.get("list");
		if(lineList!=null){
			for(LineBusinessVo lineBusinessVo : lineList){
				List<StationInfo> stationInfoList = publishLineDao.listStationInfoByLineId(lineBusinessVo.getLineBaseId());
				if(stationInfoList!=null && !stationInfoList.isEmpty()){
					lineBusinessVo.setStartStationName(stationInfoList.get(0).getName());
					lineBusinessVo.setEndStationName(stationInfoList.get(stationInfoList.size()-1).getName());
				}
			}
		}
		return map;
	}

	public Map<String, Object> getPublishedLines(Search search,
			int currentPageIndex, int pageSize) {
		return lineDao.getAllLines(search, currentPageIndex, pageSize);
	}

	public LineEntity getLineDetail(String lineId) {
		LineEntity line = null;
		return line;
	}

	/**获取所有购买过该路线的用户手机号码**/
	public String getUserTelByLineBaseId(String lineBaseId){
		return lineDao.getUserTelByLineBaseId(lineBaseId);
	}
	
	/**修改发车时间**/
	public String updateLineTime(LineTimeChange lineTimeChange){
		return lineDao.updateLineTime(lineTimeChange);
	}
	
	/**获取修改发车信息**/
	public AppVo_6 getLineChangeInfo(String lineBaseId){
		return lineDao.getLineChangeInfo(lineBaseId);
	}
	
	/**修改发车时间任务计划**/
	public void changeLineTimeByTask(){
		lineDao.changeLineTimeByTask();
	}
	
	/**
	 * 获取招募线路列表
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getRecruitLines(Search search,
			int currentPageIndex, int pageSize) {
		return lineDao.getRecruitLines(search, currentPageIndex, pageSize);
	}

	public void recruitLineOperation(String lineId, String opType) {

	}

	public Map<String, Object> getUserLines(Search search,
			int currentPageIndex, int pageSize) {
		return lineDao.getUserLines(search, currentPageIndex, pageSize);
	}

	public UserLineEntity getUserLineDetail(String applicationId) {
		UserLineEntity userLine = null;
		if (StringUtils.hasText(applicationId)) {
			userLine = lineDao.getUserLineDetail(applicationId);
		}
		return userLine;
	}

	public String addLine(LineEntity line) {
		if (null != line) {
			return lineDao.addLine(line);
		}
		return null;
	}

	/**
	 * 保存线路班次信息
	 * 
	 * @param lineClassList
	 * @return
	 */
	public List<String> addLineClassList(List<LineClassEntity> lineClassList) {
		if (false == CollectionUtils.isEmpty(lineClassList)) {
			return lineDao.addLineClassList(lineClassList);
		}
		return new ArrayList<String>(0);
	}

	public LineEntity getLineByLineBaseId(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			return lineDao.getLineById(lineBaseId);
		}
		return null;
	}

	/**
	 * 将调度中的线路发送给商家，此时线路的状态为调度中，且线路未分配商家
	 * 
	 * @param lineBaseId
	 * @param businessId
	 * @return
	 */
	public int updateLine(LineEntity line) {
		if (null != line) {
			if (StringUtils.hasText(line.getProvinceCode())
					&& StringUtils.hasText(line.getCityCode())) {
				String cityName = lineDao.getCityName(line.getProvinceCode(),
						line.getCityCode());
				line.setCityName(cityName);
			} else {
				line.setProvinceCode(null);
				line.setCityCode(null);
			}
			return lineDao.updateLine(line);
		}
		return 0;
	}

	/**
	 * 修改班次座位数
	 * 
	 * @param line
	 * @return
	 */
	public int updateOrderSeats(String lineBaseId, String orderStartTime,
			int theNewSeats) {
		return lineDao
				.updateOrderSeats(lineBaseId, orderStartTime, theNewSeats);
	}

	public int updateLine(LineEntity line, List<String> delClassList,
			List<LineClassEntity> addClassList, List<String> delMonthList,
			List<LinePassengerMonthEntity> addMonthList,
			String delOrderStartTimes) {
		if (null != line && StringUtils.hasText(line.getProvinceCode())
				&& StringUtils.hasText(line.getCityCode())) {
			String cityName = lineDao.getCityName(line.getProvinceCode(), line
					.getCityCode());
			line.setCityName(cityName);
		}
		return lineDao.updateLine(line, delClassList, addClassList,
				delMonthList, addMonthList, delOrderStartTimes);
	}

	/**
	 * 获取招募线路详情
	 * 
	 * @param lineBaseId
	 * @return
	 */
	public RecruitLineStationVo getRecruitLineStation(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			return lineDao.getRecruitLineStation(lineBaseId);
		}
		return null;
	}

	/**
	 * 获取站点图片列表
	 * 
	 * @param stationInfoId
	 * @return
	 */
	public List<String> getStationPicUrlList(String stationInfoId) {
		return new ArrayList<String>(0);
	}

	public boolean checkSameLineName(String lineBaseId, String lineName,
			String lineType) {
		if (false == StringUtils.hasText(lineName)) {
			throw new IllegalArgumentException(
					"parameter lineName can't be empty.");
		} else {
			int count = lineDao.getSameLineNameCount(lineBaseId, lineName,
					lineType);
			return count > 0 ? true : false;
		}
	}

	public Map<String, Object> getApplyPassengerList(String lineBaseId,
			int currentPageIndex, int pageSize) {
		if (StringUtils.hasText(lineBaseId)) {
			return lineDao.getApplyPassengerList(lineBaseId, currentPageIndex,
					pageSize);
		}
		return null;
	}

	public List<PassengerInfoVo> getRecruitLinePassengers(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			return lineDao.getRecruitLinePassengers(lineBaseId);
		}
		return new ArrayList<PassengerInfoVo>(0);
	}

	/**
	 * 获取线路的调度信息，此处只获取线路，商家，班次信息
	 */
	public LineScheduleInfoVo getLineScheduleInfo(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			return lineDao.getLineScheduleInfo(lineBaseId);
		}
		return null;
	}

	public List<LineClassCarDriverVo> getLineClassCarDriverList(
			String lineBaseId, String orderStartTime, String orderDate) {
		if (StringUtils.hasText(lineBaseId)) {
			return lineDao.getLineClassCarDriverList(lineBaseId,
					orderStartTime, orderDate);
		}
		return new ArrayList<LineClassCarDriverVo>(0);
	}

	/**
	 * 根据线路id、发车时间、所选年月查询班次信息
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	public List<LineClassEntity> getLineClassByConditoins(String lineBaseId,
			String orderStartTime, String orderDate) {
		return lineDao.getLineClassByConditoins(lineBaseId, orderStartTime,
				orderDate);
	}

	/**
	 * 根据线路id、发车时间查询班次年月信息
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	public List<LineClassVo> getLineClassYearAndMonth(String lineBaseId,
			String orderStartTime) {
		return lineDao.getLineClassYearAndMonth(lineBaseId, orderStartTime);
	}

	/**
	 * 根据线路id、日期、发车时间判断对应班次是否已经被订座
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	public int judgeLineClassUsed(String lineBaseId, String orderStartTime,
			String orderDate) {
		return lineDao
				.judgeLineClassUsed(lineBaseId, orderStartTime, orderDate);
	}

	public String addLinePassengerMonth(LinePassengerMonthEntity entity) {
		if (null != entity) {
			return lineDao.addLinePassengerMonth(entity);
		}
		return null;
	}

	public List<String> addLinePassengerMonths(
			List<LinePassengerMonthEntity> list) {
		if (false == CollectionUtils.isEmpty(list)) {
			List<String> res = new ArrayList<String>();
			for (LinePassengerMonthEntity v : list) {
				res.add(this.addLinePassengerMonth(v));
			}
			return res;
		}
		return new ArrayList<String>(0);
	}

	public int deleteLinePassengerMonth(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			return lineDao.deleteLinePassengerMonth(lineBaseId);
		}
		return 0;
	}

	public List<LineClassMonthOrderCountVo> getClassMonthOrderCount(
			String lineBaseId, String orderDate, String orderStartTime) {
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderDate)
				&& StringUtils.hasText(orderStartTime)) {
			return lineDao.getClassMonthOrderCount(lineBaseId, orderDate,
					orderStartTime);
		}
		return new ArrayList<LineClassMonthOrderCountVo>(0);
	}

	public Map<String, Object> getOrderPassengerList(String lineBaseId,
			String orderDate, String orderStartTime, int currentPageIndex,
			int pageSize) {
		return lineDao.getOrderPassengerList(lineBaseId, orderDate,
				orderStartTime, currentPageIndex, pageSize);
	}

	/**
	 * 根据条件查询包月信息
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	public LinePassengerMonthEntity getLinePassengerMonth(String lineBaseId,
			String orderStartTime, String orderDate) {
		return lineDao.getLinePassengerMonth(lineBaseId, orderStartTime,
				orderDate);
	}

	public StationInfo getStationByName(String stationName) {
		if (StringUtils.hasText(stationName)) {
			List<StationInfo> stations = lineDao.getStationByName(stationName);
			if (false == CollectionUtils.isEmpty(stations)) {
				return stations.get(0);
			}
		}
		return null;
	}

	public List<ClassMonthOrderPriceVo> getClassMonthOrderPrice(
			String lineBaseId, String orderStartTime) {
		if (StringUtils.hasText(lineBaseId)
				&& StringUtils.hasText(orderStartTime)) {
			return lineDao.getClassMonthOrderPrice(lineBaseId, orderStartTime);
		}
		return new ArrayList<ClassMonthOrderPriceVo>(0);
	}

	public int judgeLineClassOrderCount(String lineBaseId, String orderStartTime) {
		if (StringUtils.hasText(lineBaseId)
				&& StringUtils.hasText(orderStartTime)) {
			if (orderStartTime.contains(",")) {
				String[] arr = orderStartTime.split(",");
				for (String s : arr) {
					int count = lineDao.getLineClassOrderCount(lineBaseId, s);
					if (count > 0) {
						return count;
					}
				}
				return 0;
			} else {
				return lineDao.getLineClassOrderCount(lineBaseId,
						orderStartTime);
			}
		}
		throw new IllegalArgumentException(
				"lineBaseId and orderStartTime can't be null.");
	}

	public List<OrderStartTimeVo> checkSameClassTime(String lineBaseId,
			String orderStartTimes) {
		if (StringUtils.hasText(lineBaseId)
				&& StringUtils.hasText(orderStartTimes)) {
			return lineDao.getSameClassTime(lineBaseId, orderStartTimes);
		}
		return null;
	}

	public List<LineClassVo> getLineClassList(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			return lineDao.getLineClassList(lineBaseId);
		}
		return new ArrayList<LineClassVo>(0);
	}

	public ClassMonthOrderPriceVo getClassOneMonthOrderPrice(String lineBaseId,
			String orderStartTime, String orderDate) {
		if (StringUtils.hasText(lineBaseId)
				&& StringUtils.hasText(orderStartTime)
				&& StringUtils.hasText(orderDate)) {
			return lineDao.getClassOneMonthOrderPrice(lineBaseId,
					orderStartTime, orderDate);
		}
		return null;
	}

	public int getEffectiveOrderCount(String lineBaseId) {
		return lineDao.getEffectiveOrderCount(lineBaseId);
	}

	public List<LineClassEntity> getQuarterLineClassList(String lineBaseId,
			String orderStartTime) {
		return lineDao.getQuarterLineClassList(lineBaseId, orderStartTime);
	}

	public List<LineClassEntity> getLineClassList(String lineName,
			String orderDate) {
		return lineDao.getLineClassList(lineName, orderDate);
	}

	public int getLineClassOrderSeats(String lineBaseId, String orderStartTime) {
		return lineDao.getLineClassOrderSeats(lineBaseId, orderStartTime);
	}


	public Map<String, Object> getLinesForOuter(Search search,
			int curPageIndex, int pageSize) {
		return lineDao.getLinesForOuter(search, curPageIndex, pageSize);
	}

	public int addLineEnrol(LineEnrollVo lineEnrollVo) {
		return lineDao.addLineEnrol(lineEnrollVo);
	}

	public int addUserLineCallBack(UserLineCallbackVo callbackVo) {
		return lineDao.addUserLineCallBack(callbackVo);
	}

	public Map<String, Object> getUserLinesStationCount(Search search,
			int currentPageIndex, int pageSize) {
		return lineDao.getUserLinesStationCount(search, currentPageIndex,
				pageSize);
	}

	public List<LineEntity> getAllLineList() {
		return lineDao.getAllLineList();
	}

	public UserLineEntity getUserLineDetailByKey(String applicationId) {
		return lineDao.getUserLineDetailByKey(applicationId);
	}

	/**
	 * 根据id查询用户申请线路信息
	 * 
	 * @param applicationId
	 * @return
	 */
	public UserLineEntity getUserLineById(String applicationId) {
		return lineDao.getUserLineById(applicationId);
	}

	/**
	 * 修改用户申请线路信息
	 * 
	 * @param ule
	 * @return
	 */
	public int updateUserLine(UserLineEntity ule, String userId) {
		return lineDao.updateUserLine(ule, userId);
	}

	public Map<String, Object> getUserLineApplyList(String applicationId,
			int currentPageIndex, int pageSize) {
		return lineDao.getUserLineApplyList(applicationId, currentPageIndex,
				pageSize);
	}

	public int getLineEnrollNum(String lineBaseId, String applyTel) {
		List<LineEnrollVo> enrollList = lineDao.getLineEnrollList(lineBaseId,
				applyTel);
		return CollectionUtils.isEmpty(enrollList) ? 0 : enrollList.size();
	}

	/**
	 * 获取班次
	 */
	public List<String> getAllLineOrderStartTime() {
		return lineDao.getAllLineOrderStartTime();
	}

	public Map<String, Object> getStationApplyList(Search search,
			int curPageIndex, int pageSize) {
		return lineDao.getStationApplyList(search, curPageIndex, pageSize);
	}

	public LineCityVo getLineCity(String lineBaseId) {
		return lineDao.getLineCity(lineBaseId);
	}

	public int setLineAllowance(LineAllowanceVo v) {
		return lineDao.setLineAllowance(v);
	}

	public LineAllowanceDetailVo getLineAllowance(String lineBaseId) {
		return lineDao.getLineAllowance(lineBaseId);
	}

	public List<LineClassOrderPassengerVo> getLineClassOrderPassengers(
			String lineBaseId, String orderStartTime, String startTime,
			String endTime) {
		return lineDao.getLineClassOrderPassengers(lineBaseId, orderStartTime,
				startTime, endTime);
	}

	/** 判断线路是否有排班 **/
	@Override
	public String judgeLineClass(String lineBaseId) {
		return lineDao.judgeLineClass(lineBaseId);
	}

	/** 把线路指定给新的商家 **/
	@Override
	public int updateLineToNewBusiness(String lineBaseId, String businessId) {
		return lineDao.updateLineToNewBusiness(lineBaseId,businessId);
	}

	/**获得上次排班的最后一次工作日的司机和车辆**/
	@Override
	public AppVo_2 getDriverAndCar(String lineBaseId) {
		return lineDao.getDriverAndCar(lineBaseId);
	}
	
	/**添加圈子信息**/
	public int addImGroupInfo(String userId,String lineName,String orderSeats,String lineBaseId){
		return lineDao.addImGroupInfo(userId, lineName, orderSeats, lineBaseId);
	}
	
	/**
	 * 查询所有线路id和名称
	 * @return
	 */
	public List<LineBaseInfo> getLineInfo(){
		return lineDao.getLineInfo();
	}

	/**
	 * 获取线路运营人员名称列表
	 * @return
	 */
	public List<SysAdminVo> getAdminName() {
		return lineDao.getAdminName();
	}

	/**
	 * 查询线路操作日志
	 * @param lineBaseId
	 * @return
	 */
	public Map<String, Object> getLineLog(String lineBaseId,int currentPageIndex,int pageSize) {
		return lineDao.getLineLog(lineBaseId,currentPageIndex,pageSize);
	}

	/**
	 * 查询定制线路车辆信息
	 * @return
	 */
	public List<LineCarMsgVo> getDefineCarMsg() {
		return lineDao.getDefineCarMsg();
	}
}
