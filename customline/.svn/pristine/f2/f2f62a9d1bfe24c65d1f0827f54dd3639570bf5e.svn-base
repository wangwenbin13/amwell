package com.amwell.dao;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.ClassMonthOrderPriceVo;
import com.amwell.vo.LineAllowanceDetailVo;
import com.amwell.vo.LineAllowanceVo;
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

public interface ILineDao {

	/**
	 * 根据关键词获取有经度的站点列表
	 * 
	 * @param keywords
	 *            关键词
	 * @return
	 */
	public List<StationInfo> loadStationByKeywords(String keywords);

	/** 获取所有购买过该路线的用户手机号码 **/
	public String getUserTelByLineBaseId(String lineBaseId);

	/** 修改发车时间 **/
	public String updateLineTime(LineTimeChange lineTimeChange);

	/** 获取修改发车信息 **/
	public AppVo_6 getLineChangeInfo(String lineBaseId);

	/** 修改发车时间任务计划 **/
	public void changeLineTimeByTask();

	Map<String, Object> getUserLines(Search search, int currentPage,
			int pageSize);

	/**
	 * 修改站点信息的时候同步线路轨迹表
	 * 
	 * @param lineBaseId  线路id
	 * @param stationList 站点列表
	 * @param newStationIdList 站点id列表
	 */
	public void syncLineTrack(String lineBaseId, List<StationInfo> stationList, List<String> newStationIdList);

	/**
	 * 同步全部线路轨迹表
	 */
	public void syncAllLineTrackMap();

	/**
	 * 保存线路信息，返回线路主健
	 * 
	 * @param line
	 * @return
	 */
	String addLine(LineEntity line);

	/**
	 * 保存线路班次信息
	 * 
	 * @param lineClassList
	 * @return
	 */
	List<String> addLineClassList(List<LineClassEntity> lineClassList);

	/**
	 * 查询所有非招募线路
	 * 
	 * @return
	 */
	Map<String, Object> getAllLines(Search search, int currentPage, int pageSize);

	/**
	 * 获取招募线路列表
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getRecruitLines(Search search, int currentPageIndex,
			int pageSize);

	/**
	 * 获取用户线路详情
	 * 
	 * @param applicationId
	 * @return
	 */
	UserLineEntity getUserLineDetail(String applicationId);

	/**
	 * 根据主键ID查询线路
	 * 
	 * @param lineBaseId
	 * @return
	 */
	LineEntity getLineById(String lineBaseId);

	/**
	 * 修改线路
	 * 
	 * @param lineEntity
	 * @return
	 */
	int updateLine(LineEntity lineEntity);

	/**
	 * 修改班次座位数
	 * 
	 * @param line
	 * @return
	 */
	int updateOrderSeats(String lineBaseId, String orderStartTime,
			int theNewSeats);

	int updateLine(LineEntity line, List<String> delClassList,
			List<LineClassEntity> addClassList, List<String> delMonthList,
			List<LinePassengerMonthEntity> addMonthList,
			String delOrderStartTimes);

	/**
	 * 查询招募线路及其站点信息
	 * 
	 * @param lineBaseId
	 * @return
	 */
	RecruitLineStationVo getRecruitLineStation(String lineBaseId);

	/**
	 * 根据站点ID查询站点信息
	 * 
	 * @param stationInfoId
	 * @return
	 */
	StationInfo getStation(String stationInfoId);

	/**
	 * 返回重名的线路总数,lineName为必填字段，若不填，则返回0
	 * 
	 * @param lineBaseId
	 * @param lineName
	 * @return
	 */
	int getSameLineNameCount(String lineBaseId, String lineName, String lineType);

	/**
	 * 删除站点信息
	 * 
	 * @param list
	 *            站点表主键ID集合
	 * @return
	 */
	int deleteStations(List<String> list);

	Map<String, Object> getApplyPassengerList(String lineBaseId,
			int currentPageIndex, int pageSize);

	/**
	 * 查询招募线路报名的乘客列表
	 * 
	 * @param lineBaseId
	 * @return
	 */
	List<PassengerInfoVo> getRecruitLinePassengers(String lineBaseId);

	/**
	 * 获取线路的调度信息，此处只获取线路，商家，班次信息
	 * 
	 * @param lineBaseId
	 * @return
	 */
	LineScheduleInfoVo getLineScheduleInfo(String lineBaseId);

	String addLinePassengerMonth(LinePassengerMonthEntity entity);

	int deleteLinePassengerMonth(String lineBaseId);

	List<LineClassCarDriverVo> getLineClassCarDriverList(String lineBaseId,
			String orderStartTime, String orderDate);

	List<LineClassMonthOrderCountVo> getClassMonthOrderCount(String lineBaseId,
			String orderDate, String orderStartTime);

	Map<String, Object> getOrderPassengerList(String lineBaseId,
			String orderDate, String orderStartTime, int currentPageIndex,
			int pageSize);

	/**
	 * 根据线路id、发车时间、所选年月查询班次信息
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	List<LineClassEntity> getLineClassByConditoins(String lineBaseId,
			String orderStartTime, String orderDate);

	/**
	 * 根据线路id、日期、发车时间判断对应班次是否已经被订座
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	int judgeLineClassUsed(String lineBaseId, String orderStartTime,
			String orderDate);

	/**
	 * 根据线路id、发车时间查询班次年月信息
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	List<LineClassVo> getLineClassYearAndMonth(String lineBaseId,
			String orderStartTime);

	/**
	 * 根据条件查询包月信息
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	LinePassengerMonthEntity getLinePassengerMonth(String lineBaseId,
			String orderStartTime, String orderDate);

	List<StationInfo> getStationByName(String stationName);

	List<ClassMonthOrderPriceVo> getClassMonthOrderPrice(String lineBaseId,
			String orderStartTime);

	int getLineClassOrderCount(String lineBaseId, String orderStartTime);

	List<OrderStartTimeVo> getSameClassTime(String lineBaseId,
			String orderStartTimes);

	List<LineClassVo> getLineClassList(String lineBaseId);

	ClassMonthOrderPriceVo getClassOneMonthOrderPrice(String lineBaseId,
			String orderStartTime, String orderDate);

	int getEffectiveOrderCount(String lineBaseId);

	public List<LineClassEntity> getQuarterLineClassList(String lineBaseId,
			String orderStartTime);

	List<LineClassEntity> getLineClassList(String lineName, String orderDate);

	int getLineClassOrderSeats(String lineBaseId, String orderStartTime);

	Map<String, Object> getLinesForOuter(Search search, int curPageIndex,
			int pageSize);

	int addLineEnrol(LineEnrollVo lineEnrollVo);

	int addUserLineCallBack(UserLineCallbackVo callbackVo);

	Map<String, Object> getUserLinesStationCount(Search search,
			int currentPageIndex, int pageSize);

	List<LineEntity> getAllLineList();

	UserLineEntity getUserLineDetailByKey(String applicationId);

	/**
	 * 根据id查询用户申请线路信息
	 * 
	 * @param applicationId
	 * @return
	 */
	UserLineEntity getUserLineById(String applicationId);

	/**
	 * 修改用户申请线路信息
	 * 
	 * @param ule
	 * @return
	 */
	int updateUserLine(UserLineEntity ule, String userId);

	/**
	 * 根据省级编码和市级编码获取城市名称
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @return
	 */
	String getCityName(String provinceCode, String cityCode);

	Map<String, Object> getUserLineApplyList(String applicationId,
			int currentPageIndex, int pageSize);

	List<LineEnrollVo> getLineEnrollList(String lineBaseId, String applyTel);

	/**
	 * 获取班次
	 * 
	 * @return
	 */
	List<String> getAllLineOrderStartTime();

	Map<String, Object> getStationApplyList(Search search, int curPageIndex,
			int pageSize);

	LineCityVo getLineCity(String lineBaseId);

	int setLineAllowance(LineAllowanceVo v);

	LineAllowanceDetailVo getLineAllowance(String lineBaseId);

	List<LineClassOrderPassengerVo> getLineClassOrderPassengers(
			String lineBaseId, String orderStartTime, String startTime,
			String endTime);

	/** 判断线路是否有排班 **/
	String judgeLineClass(String lineBaseId);

	/** 把线路指定给新的商家 **/
	int updateLineToNewBusiness(String lineBaseId, String businessId);

	/**获得上次排班的最后一次工作日的司机和车辆**/
	public AppVo_2 getDriverAndCar(String lineBaseId);
	
	/**添加圈子信息**/
	public int addImGroupInfo(String userId,String lineName,String orderSeats,String lineBaseId);
	
	/**
	 * 查询所有线路id和名称
	 * @return
	 */
	public List<LineBaseInfo> getLineInfo();

	/**
	 * 获取线路运营人员名称列表
	 * @return
	 */
	public List<SysAdminVo> getAdminName();

	/**
	 * 查询线路操作日志
	 * @param lineBaseId
	 * @param pageSize 
	 * @param currentPageIndex 
	 * @return
	 */
	public Map<String, Object> getLineLog(String lineBaseId, int currentPageIndex, int pageSize);

	/**
	 * 查询定制线路车辆信息
	 * @return
	 */
	public List<LineCarMsgVo> getDefineCarMsg();
	
	
}
