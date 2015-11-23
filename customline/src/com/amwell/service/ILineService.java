package com.amwell.service;

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

public interface ILineService {

	/**
	 * 根据关键词获取带有经纬度的站点列表
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

	/**
	 * 修改站点信息的时候同步线路轨迹表
	 * 
	 * @param lineBaseId
	 *            线路id
	 * @param stationList
	 *            站点列表
	 * @param newStationIdList
	 *            站点id列表
	 */
	public void syncLineTrack(String lineBaseId,
			List<StationInfo> stationList, List<String> newStationIdList);

	/**
	 * 同步全部线路轨迹表
	 */
	public void syncAllLineTrackMap();

	Map<String, Object> getAllLines(Search search, int currentPage, int pageSize);

	Map<String, Object> getPublishedLines(Search search, int currentPage,
			int pageSize);

	LineEntity getLineDetail(String lineId);

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

	void recruitLineOperation(String lineId, String opType);

	Map<String, Object> getUserLines(Search search, int currentPage,
			int pageSize);

	UserLineEntity getUserLineDetail(String lineId);

	/**
	 * 保存线路信息
	 * 
	 * @param line
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
	 * 根据主键ID查询线路
	 * 
	 * @param lineBaseId
	 * @return
	 */
	public LineEntity getLineByLineBaseId(String lineBaseId);

	/**
	 * 将调度中的线路发送给商家，此时线路的状态为调度中，且线路未分配商家
	 * 
	 * @param lineBaseId
	 * @param businessId
	 * @return
	 */
	int updateLine(LineEntity line);

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
	 * 获取招募线路详情
	 * 
	 * @param lineBaseId
	 * @return
	 */
	RecruitLineStationVo getRecruitLineStation(String lineBaseId);

	/**
	 * 获取站点图片列表
	 * 
	 * @param stationInfoId
	 * @return
	 */
	List<String> getStationPicUrlList(String stationInfoId);

	/**
	 * 检查线路名称是否重复，含有重名时，返回true,反之返回false
	 * 
	 * @param lineBaseId
	 * @param lineName
	 * @return
	 */
	boolean checkSameLineName(String lineBaseId, String lineName,
			String lineType);

	/**
	 * 分页查询招募线路的报名列表
	 * 
	 * @param lineBaseId
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getApplyPassengerList(String lineBaseId,
			int currentPageIndex, int pageSize);

	/**
	 * 查询招募线路的报名列表
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

	/**
	 * 获取线路班次工作日的司机车辆信息
	 * 
	 * @param lineBaseId
	 *            线路ID,此参不能为空
	 * @param orderStartTime
	 *            发车时间，格式：08:00
	 * @param orderDate
	 *            工作日所属年月2014-09
	 * @return
	 */
	List<LineClassCarDriverVo> getLineClassCarDriverList(String lineBaseId,
			String orderStartTime, String orderDate);

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
	 * 根据条件查询包月信息
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	LinePassengerMonthEntity getLinePassengerMonth(String lineBaseId,
			String orderStartTime, String orderDate);

	/**
	 * 增加包月信息，返回主键ID
	 * 
	 * @param entity
	 * @return
	 */
	String addLinePassengerMonth(LinePassengerMonthEntity entity);

	/**
	 * 批量添加包月信息，返回主键集合
	 * 
	 * @param list
	 * @return 如果list为空,则返回列表长度为0
	 */
	public List<String> addLinePassengerMonths(
			List<LinePassengerMonthEntity> list);

	/**
	 * 删除包月信息
	 * 
	 * @param lineBaseId
	 *            线路ID主键
	 * @return
	 */
	int deleteLinePassengerMonth(String lineBaseId);

	/**
	 * 分组统计班次月工作日订购人数
	 * 
	 * @return
	 */
	List<LineClassMonthOrderCountVo> getClassMonthOrderCount(String lineBaseId,
			String orderDate, String orderStartTime);

	/**
	 * 查询班次某一天的订购乘客列表
	 * 
	 * @param lineBaseId
	 * @param orderDate
	 * @param orderStartTime
	 * @return
	 */
	Map<String, Object> getOrderPassengerList(String lineBaseId,
			String orderDate, String orderStartTime, int currentPageIndex,
			int pageSize);

	/**
	 * 重用站点，如果站点名称一致，则只取列表第一个
	 * 
	 * @param stationName
	 *            如果此参数为空，则返回null
	 * @return
	 */
	StationInfo getStationByName(String stationName);

	/**
	 * 获取班次一个季度的包月价格,起始时间从本月1号开始
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @return
	 */
	List<ClassMonthOrderPriceVo> getClassMonthOrderPrice(String lineBaseId,
			String orderStartTime);

	/**
	 * 根据线路ID、班次时间查询订单总数
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @return
	 */
	int judgeLineClassOrderCount(String lineBaseId, String orderStartTime);

	/**
	 * 与删除的班次发车时间比对，检测发车时间是否重复
	 * 
	 * @param lineBaseId
	 * @param orderStartTimes
	 * @return
	 */
	List<OrderStartTimeVo> checkSameClassTime(String lineBaseId,
			String orderStartTimes);

	/**
	 * 获取线路下的所有班次发车时间
	 * 
	 * @param lineBaseId
	 * @return
	 */
	List<LineClassVo> getLineClassList(String lineBaseId);

	/**
	 * 查看班次单月包月价格
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	ClassMonthOrderPriceVo getClassOneMonthOrderPrice(String lineBaseId,
			String orderStartTime, String orderDate);

	/**
	 * 统计线路关联的订单数
	 * 
	 * @param lineBaseId
	 * @return
	 */
	int getEffectiveOrderCount(String lineBaseId);

	public List<LineClassEntity> getQuarterLineClassList(String lineBaseId,
			String orderStartTime);

	public List<LineClassEntity> getLineClassList(String lineName,
			String orderDate);

	int getLineClassOrderSeats(String lineBaseId, String orderStartTime);

	/**
	 * 查询线路
	 * 
	 * @param search
	 * @param curPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getLinesForOuter(Search search, int curPageIndex,
			int pageSize);

	/**
	 * 招募报名
	 * 
	 * @param lineEnrollVo
	 * @return
	 */
	int addLineEnrol(LineEnrollVo lineEnrollVo);

	/**
	 * 创建用户申请线路回访
	 * 
	 * @param callbackVo
	 * @return
	 */
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
	 * 获取用户申请线路报名列表
	 * 
	 * @param applicationId
	 * @return
	 */
	Map<String, Object> getUserLineApplyList(String applicationId,
			int currentPageIndex, int pageSize);

	int getLineEnrollNum(String lineBaseId, String applyTel);

	/**
	 * 获取班次
	 * 
	 * @return
	 */
	List<String> getAllLineOrderStartTime();

	Map<String, Object> getStationApplyList(Search search, int curPageIndex,
			int pageSize);

	/**
	 * 获取线路省份，城市，城市名称
	 * 
	 * @param lineBaseId
	 * @return
	 */
	LineCityVo getLineCity(String lineBaseId);

	int setLineAllowance(LineAllowanceVo v);

	LineAllowanceDetailVo getLineAllowance(String lineBaseId);

	List<LineClassOrderPassengerVo> getLineClassOrderPassengers(
			String lineBaseId, String orderStartTime, String startTime,
			String endTime);

	/** 判断线路是否有排班 **/
	public String judgeLineClass(String lineBaseId);

	/** 把线路指定给新的商家 **/
	public int updateLineToNewBusiness(String lineBaseId, String businessId);

	/**获得上次排班的最后一次工作日的司机和车辆**/
	public AppVo_2 getDriverAndCar(String lineBaseId);
	
	/**添加圈子信息**/
	public int addImGroupInfo(String userId,String lineName,String orderSeats,String lineBaseId);
	
	/**
	 * 查询所有线路id和名称
	 * @return
	 */
	public List<LineBaseInfo> getLineInfo();

	/**获取线路运营人员名称列表
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