package com.amwell.service;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.AdvertImageVo;
import com.amwell.vo.AdvertManageVo;
import com.amwell.vo.AppVersionVo;
import com.amwell.vo.IosVersionVo;

public interface IAdManageService {
	
	/**
	 * 分页查询广告列表
	 */
	Map<String,Object> getAdManage(Search search,int currentPageIndex,int pageSize);

	/* 
	 * 查询广告详情
	 */
	Map<String,Object>  getAdManageDetail(String adId);

	/* 
	 * 添加广告信息
	 */
	int addAdManage(AdvertManageVo adModel, List<AdvertImageVo> picLists);

	/**
	 * 查询版本
	 */
	List<AppVersionVo> getVersion();

	int changeState(AdvertManageVo adModel);

	/**
	 * 更新广告
	 * @param adModel
	 * @param picLists
	 * @return
	 */
	int updateAdManage(AdvertManageVo adModel,List<AdvertImageVo> picLists);

	/**
	 * 查询ioS版本信息
	 * @return
	 */
	List<IosVersionVo> getIosVersion();
}
