package com.amwell.dao;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.CarClassVo;
import com.amwell.vo.ClassMonthOrderPriceVo;
import com.amwell.vo.DriverClassVo;
import com.amwell.vo.LineClassCarDriverVo;
import com.amwell.vo.LineDetailVo;
import com.amwell.vo.MerchantSchedulingVo;

public interface ILineDao {

	Map<String, Object> getMerchantLineList(Search search, int currentPageIndex, int pageSize);

	LineDetailVo getLineDetail(String lineBaseId);

	List<LineClassCarDriverVo> getLineClassCarDriverList(String lineBaseId, String orderStartTime, String orderDate);

	Map<String, Object> getDriverList(String businessId, String orderStartTime, String orderDate, String driverName,
			int currentPageIndex, int pageSize);

	Map<String, Object> getCarList(String businessId, String orderStartTime, String orderDate, String carInfo,
			int currentPageIndex, int pageSize);

	List<MerchantSchedulingVo> getMerchantSchedulingList(String businessId, String orderDate);

	List<DriverClassVo> getDriverClassList(String orderStartTime, String orderDate, String beginOrderDate,
			String endOrderDate, String driverId);

	List<CarClassVo> getCarClassList(String orderStartTime, String orderDate, String beginOrderDate,
			String endOrderDate, String vehicleId);

	/**
	 * 根据线路id获取线路起始站点和结束站点的名称
	 * 
	 * @param lineBaseId
	 * @return
	 */
	String getStartAndEndname(String lineBaseId);

	ClassMonthOrderPriceVo getClassOneMonthOrderPrice(String lineBaseId, String orderStartTime, String orderDate);

	// 班次司机车辆调度
	int scheduleClassCarDriver(String lineBaseId, String orderStartTime, String[] orderDates, String[] driverIds,
			String[] vehicleIds);

}
