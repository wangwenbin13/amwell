package com.amwell.service;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.CarClassVo;
import com.amwell.vo.ClassMonthOrderPriceVo;
import com.amwell.vo.DriverClassVo;
import com.amwell.vo.LineClassCarDriverVo;
import com.amwell.vo.LineDetailVo;
import com.amwell.vo.MerchantSchedulingVo;

public interface ILineService {

	/**
	 * 获取商家的线路列表,不区分线路状态
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getMerchantLineList(Search search, int currentPageIndex, int pageSize);

	/**
	 * 获取线路详情，包含其下班次信息
	 * 
	 * @param lineBaseId
	 * @return
	 */
	LineDetailVo getLineDetail(String lineBaseId);

	/**
	 * 获取线路班次的某月司机车辆列表
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	List<LineClassCarDriverVo> getLineClassCarDriverList(String lineBaseId, String orderStartTime, String orderDate);

	/**
	 * 获取司机列表，若司机在某天或某月存在调度记录，则调度计数大于0
	 * 
	 * @param orderStartTime
	 * @param orderDate
	 * @param driverName
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getDriverList(String businessId, String orderStartTime, String orderDate,
			String driverName, int currentPageIndex, int pageSize);

	/**
	 * 获取车辆列表，若车辆在某天或某月存在调度记录，则调度计数大于0
	 * 
	 * @param orderStartTime
	 * @param orderDate
	 * @param carInfo
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getCarList(String businessId, String orderStartTime, String orderDate, String carInfo,
			int currentPageIndex, int pageSize);

	/**
	 * 获取商户的行程安排
	 * 
	 * @param businessId
	 * @param orderDate
	 * @return
	 */
	List<MerchantSchedulingVo> getMerchantSchedulingList(String businessId, String orderDate);

	/**
	 * 查看司机的排班情况
	 * 
	 * @param orderStartTime
	 * @param orderDate
	 * @param beginOrderDate
	 * @param endOrderDate
	 * @param driverId
	 * @return
	 */
	List<DriverClassVo> getDriverClassList(String orderStartTime, String orderDate, String beginOrderDate,
			String endOrderDate, String driverId);

	/**
	 * 查看车辆的排班情况
	 * 
	 * @param orderStartTime
	 * @param orderDate
	 * @param beginOrderDate
	 * @param endOrderDate
	 * @param vehicleId
	 * @return
	 */
	List<CarClassVo> getCarClassList(String orderStartTime, String orderDate, String beginOrderDate,
			String endOrderDate, String vehicleId);

	/**
	 * 调度班次司机、车辆
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param split
	 * @param split2
	 * @param split3
	 * @return
	 */
	int scheduleClassCarDriver(String lineBaseId, String orderStartTime, String[] orderDates, String[] driverIds,
			String[] vehicleIds);

	/**
	 * 根据线路id获取线路起始站点和结束站点的名称
	 * 
	 * @param lineBaseId
	 * @return
	 */
	String getStartAndEndname(String lineBaseId);

	ClassMonthOrderPriceVo getClassOneMonthOrderPrice(String lineBaseId, String orderStartTime, String orderDate);

}
