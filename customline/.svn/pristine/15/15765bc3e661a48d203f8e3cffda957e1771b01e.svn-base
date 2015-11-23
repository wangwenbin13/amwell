package com.amwell.dao;

import java.util.List;

import com.amwell.domain.NewApplicationReport;
import com.amwell.vo.PassengerInfoEntity;

public interface INewApplicationReportDao {
	
	public List<NewApplicationReport> search(String startDate,String endDate,String cityName,String isHandle,int startPage,int pageSize);
	
	public int searchCount(String cityName,String startDate,String endDate,String count);
	
	public List<PassengerInfoEntity> getTelephoneListByApplicationId(String applicationId);
	
	public void setIsHandle(String applicationId,String isHandle);
}
