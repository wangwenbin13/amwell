package com.amwell.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.ICarOwnerLineDao;
import com.amwell.entity.Search;
import com.amwell.service.ICarOwnerLineService;
import com.amwell.vo.pc.CarOwnerLineVo;

@Service("carOwnerLineService")
public class CarOwnerLineServiceImpl implements ICarOwnerLineService  {
	
	@Autowired
	private ICarOwnerLineDao carOwnerLineDao;
	
	public Map<String, Object> getLineList(Search search, int currentPageIndex,int pageSize){
		return carOwnerLineDao.getLineList(search, currentPageIndex, pageSize);
	}
	
	
	public CarOwnerLineVo getLineDetail(String pcLineId){
		return carOwnerLineDao.getPcLineDetail(pcLineId);
	}

}
