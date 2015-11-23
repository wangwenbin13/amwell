package com.amwell.dao;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.AdvertImageVo;
import com.amwell.vo.AdvertManageVo;
import com.amwell.vo.AppVersionVo;
import com.amwell.vo.IosVersionVo;

public interface IAdmanageDao {
	

	Map<String, Object> getAdManage(Search search, int currentPageIndex,int pageSize);

	/* 
	 * 查询广告详情
	 */
	Map<String,Object>  getAdManageDetail(String adId);

	/* 
	 * 添加广告信息
	 */
	int addAdManage(AdvertManageVo adModel, List<AdvertImageVo> picLists);

	/**
	 * 查询版本号
	 */
	List<AppVersionVo> getVersion();

	int changeState(AdvertManageVo adModel);

	int updateAdManage(AdvertManageVo adModel, List<AdvertImageVo> picLists);

	/**
	 * 查询ioS版本信息
	 * @return
	 */
	List<IosVersionVo> getIosVersion();

}
