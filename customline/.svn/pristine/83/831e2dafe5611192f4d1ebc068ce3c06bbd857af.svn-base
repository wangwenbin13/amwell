package com.amwell.domain;

import java.util.List;

import com.amwell.commons.ApplicationContextHolder;
import com.amwell.dao.INewApplicationReportDao;
import com.amwell.vo.PassengerInfoEntity;

public class NewApplicationReport {
	/**
	 * 招募定制线路Id
	 */
	private String applicationId;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 招募发起日期
	 */
	private String applicationTime;

	/**
	 * 上车点
	 */
	private String ustation;

	/**
	 * 下车点
	 */
	private String dstation;

	/**
	 * 报名人数
	 */
	private String enrollNum;
	
	/**
	 * 搜索次数
	 */
	private long searchNum;
	
	/**
	 * 总计 报名人数+搜索次数
	 */
	private long totalNum;
	
	/**
	 * 状态 1-已处理        其他-未处理
	 */
	private String isHandle;

	public NewApplicationReport() {
		super();
	}
	
	public NewApplicationReport(String applicationId, String cityName, String applicationTime, String ustation,
			String dstation, String enrollNum) {
		super();
		this.applicationId = applicationId;
		this.cityName = cityName;
		this.applicationTime = applicationTime;
		this.ustation = ustation;
		this.dstation = dstation;
		this.enrollNum = enrollNum;
	}
	
	public static List<NewApplicationReport> search(String startDate,String endDate,String cityName,String isHandle,int currentPage,int pageSize){
		int startPage = pageSize*(currentPage-1);
		INewApplicationReportDao iNewApplicationReportDao = ApplicationContextHolder.getBean(INewApplicationReportDao.class);
		List<NewApplicationReport> newApplicationReportList = iNewApplicationReportDao.search(startDate,endDate, cityName,isHandle,startPage,pageSize);
		return newApplicationReportList;
	}
	
	public static void handle(String applicationId){
		INewApplicationReportDao iNewApplicationReportDao = ApplicationContextHolder.getBean(INewApplicationReportDao.class);
		iNewApplicationReportDao.setIsHandle(applicationId, "1");
	}
	
	public long getSearchNum(){
		return this.searchNum;
	}
	
	public void setSearchNum(long searchNum){
		this.searchNum = searchNum;
	}
	
	public void setTotalNum(long totalNum){
		this.totalNum = totalNum;
	}
	
	public long getTotalNum(){
		return this.totalNum;
	}
	
	public static List<PassengerInfoEntity> listTelephoneByApplication(String applicationId){
		INewApplicationReportDao iNewApplicationReportDao = ApplicationContextHolder.getBean(INewApplicationReportDao.class);
		return iNewApplicationReportDao.getTelephoneListByApplicationId(applicationId);
	}
	
	public static int count(String startDate,String endDate,String cityName,String isHandle){
		INewApplicationReportDao iNewApplicationReportDao = ApplicationContextHolder.getBean(INewApplicationReportDao.class);
		int count = iNewApplicationReportDao.searchCount(cityName,startDate,endDate,isHandle);
		return count;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getUstation() {
		return ustation;
	}

	public void setUstation(String ustation) {
		this.ustation = ustation;
	}

	public String getDstation() {
		return dstation;
	}

	public void setDstation(String dstation) {
		this.dstation = dstation;
	}

	public String getEnrollNum() {
		return enrollNum;
	}

	public void setEnrollNum(String enrollNum) {
		this.enrollNum = enrollNum;
	}

	public String getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}

}
