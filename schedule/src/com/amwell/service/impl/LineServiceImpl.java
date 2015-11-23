package com.amwell.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.amwell.dao.ILineDao;
import com.amwell.entity.Search;
import com.amwell.service.ILineService;
import com.amwell.vo.CarClassVo;
import com.amwell.vo.ClassMonthOrderPriceVo;
import com.amwell.vo.DriverClassVo;
import com.amwell.vo.LineClassCarDriverVo;
import com.amwell.vo.LineDetailVo;
import com.amwell.vo.MerchantSchedulingVo;

@Service("lineService")
public class LineServiceImpl implements ILineService {

	@Autowired
	private ILineDao lineDao;

	public Map<String, Object> getMerchantLineList(Search search, int currentPageIndex, int pageSize) {
		return lineDao.getMerchantLineList(search, currentPageIndex, pageSize);
	}

	public LineDetailVo getLineDetail(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			return lineDao.getLineDetail(lineBaseId);
		}
		return null;
	}

	public List<LineClassCarDriverVo> getLineClassCarDriverList(String lineBaseId, String orderStartTime,
			String orderDate) {
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			return lineDao.getLineClassCarDriverList(lineBaseId, orderStartTime, orderDate);
		}
		return new ArrayList<LineClassCarDriverVo>(0);
	}

	public Map<String, Object> getDriverList(String businessId, String orderStartTime, String orderDate,
			String driverName, int currentPageIndex, int pageSize) {
		return lineDao.getDriverList(businessId, orderStartTime, orderDate, driverName, currentPageIndex, pageSize);
	}

	public Map<String, Object> getCarList(String businessId, String orderStartTime, String orderDate, String carInfo,
			int currentPageIndex, int pageSize) {
		return lineDao.getCarList(businessId, orderStartTime, orderDate, carInfo, currentPageIndex, pageSize);
	}

	public List<MerchantSchedulingVo> getMerchantSchedulingList(String businessId, String orderDate) {
		return lineDao.getMerchantSchedulingList(businessId, orderDate);
	}

	public List<DriverClassVo> getDriverClassList(String orderStartTime, String orderDate, String beginOrderDate,
			String endOrderDate, String driverId) {
		return lineDao.getDriverClassList(orderStartTime, orderDate, beginOrderDate, endOrderDate, driverId);
	}

	public List<CarClassVo> getCarClassList(String orderStartTime, String orderDate, String beginOrderDate,
			String endOrderDate, String vehicleId) {
		return lineDao.getCarClassList(orderStartTime, orderDate, beginOrderDate, endOrderDate, vehicleId);
	}

	public int scheduleClassCarDriver(String lineBaseId, String orderStartTime, String[] orderDates, String[] driverIds,
			String[] vehicleIds) {
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && ArrayUtils.isNotEmpty(orderDates)
				&& ArrayUtils.isNotEmpty(driverIds) && ArrayUtils.isNotEmpty(vehicleIds)
				&& orderDates.length == driverIds.length && orderDates.length == vehicleIds.length) {
			return lineDao.scheduleClassCarDriver(lineBaseId, orderStartTime, orderDates, driverIds, vehicleIds);
		}
		return 0;
	}

	/**
	 * 根据线路id获取线路起始站点和结束站点的名称
	 * 
	 * @param lineBaseId
	 * @return
	 */
	public String getStartAndEndname(String lineBaseId) {
		return lineDao.getStartAndEndname(lineBaseId);
	}

	public ClassMonthOrderPriceVo getClassOneMonthOrderPrice(String lineBaseId, String orderStartTime,
			String orderDate) {
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			return lineDao.getClassOneMonthOrderPrice(lineBaseId, orderStartTime, orderDate);
		}
		return null;
	}

}
