package com.amwell.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IAdmanageDao;
import com.amwell.entity.Search;
import com.amwell.service.IAdManageService;
import com.amwell.vo.AdvertImageVo;
import com.amwell.vo.AdvertManageVo;
import com.amwell.vo.AppVersionVo;
import com.amwell.vo.IosVersionVo;


@Service("adManageService")
public class AdManageServiceImpl implements IAdManageService  {
	
	@Autowired
	private IAdmanageDao admanageDao;
	
	public Map<String, Object> getAdManage(Search search, int currentPageIndex,int pageSize) {
		return admanageDao.getAdManage(search,currentPageIndex,pageSize);
	}
	/* 
	 * 查询广告详情
	 */
	public Map<String,Object>  getAdManageDetail(String adId) {
		return admanageDao.getAdManageDetail(adId);
	}

	/* 
	 * 添加广告信息
	 */
	public int addAdManage(AdvertManageVo adModel,List<AdvertImageVo> picLists) {
		return admanageDao.addAdManage(adModel,picLists);
	}

	/* 
	 * 查询版本
	 */
	public List<AppVersionVo> getVersion() {
		return admanageDao.getVersion();
	}

	/* 
	 * 改变状态
	 */
	public int changeState(AdvertManageVo adModel) {
		return admanageDao.changeState(adModel);
	}

    public int updateAdManage(AdvertManageVo adModel,List<AdvertImageVo> picLists){
    	return admanageDao.updateAdManage(adModel,picLists);
    }
    /**
	 * 查询ioS版本信息
	 * @return
	 */
	public List<IosVersionVo> getIosVersion() {
		return admanageDao.getIosVersion();
	}

}
