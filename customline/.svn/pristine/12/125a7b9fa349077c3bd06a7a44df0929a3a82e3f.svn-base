package com.amwell.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IAppSettingDao;
import com.amwell.service.IAppSettingService;
import com.amwell.vo.AppSettingVo;
import com.amwell.vo.AppVersionVo;
import com.amwell.vo.IosVersionVo;
import com.amwell.vo.bc.BcTimeSetVo;

/**
 * @author Administrator
 *
 */
@Service("appSettingService")
public class AppSettingSerivceImpl implements IAppSettingService {
	
	@Autowired
	private IAppSettingDao iAppSettingDao;
	
	/**
	 * 修改APP设置值
	 * @return
	 */
	public int updateAppCount(AppSettingVo appSettingVo) {
		
		return iAppSettingDao.updateAppCount(appSettingVo);
	}

	/**
	 * 查询APP设置值
	 * @return
	 */
	public AppSettingVo getAppSetting() {

		AppSettingVo appSetModel = iAppSettingDao.getAppSet();
		if(appSetModel==null){
			iAppSettingDao.addAppSet(1);
			appSetModel = iAppSettingDao.getAppSet();
		}
		AppSettingVo appSendModel = iAppSettingDao.getAppSend();
		if(appSendModel==null){
			iAppSettingDao.addAppSet(2);
			appSendModel = iAppSettingDao.getAppSend();
		}

		BcTimeSetVo bcSetModel = iAppSettingDao.getBcTime();
		if(bcSetModel==null){
			iAppSettingDao.addAppSet(4);
			bcSetModel = iAppSettingDao.getBcTime();
		}
		appSetModel.setSendType(appSendModel.getSendType());
		appSetModel.setBusiness(appSendModel.getBusiness());
		appSetModel.setBcTime(Integer.valueOf(bcSetModel.getParaValue()));
		
		return appSetModel;
	}

	public String getNewVsn() {
		String vsn = iAppSettingDao.getNewVsn();
		return vsn;
	}

	public AppVersionVo getAppVesion(String appUserType) {
		return iAppSettingDao.getAppVesion(appUserType);
	}

	/*录入新版本信息(android)
	 */
	public int addAppVsn(AppVersionVo vo) {
		return iAppSettingDao.addAppVsn(vo);
	}

	/**录入新版本信息(ios)
	 * @param vo
	 * @return
	 */
	public int addIosVsn(IosVersionVo vo) {
		return iAppSettingDao.addIosVsn(vo);
	}

	/**查询版本号是否最大值(android)
	 * @return
	 */
	public AppVersionVo queryVsn(String vsn,int i, String flag) {
		return iAppSettingDao.queryVsn(vsn, i,flag);
	}

	/**
	 * 查询版本号是否最大值(IOS)
	 * @return
	 */
	public int queryIosVsn(String appType, String version,String flag) {
		return iAppSettingDao.queryIosVsn(appType,version,flag);
	}

	/* 
	 * 查询IOS版本信息
	 */
	public IosVersionVo getIosVesion(String client) {
		return iAppSettingDao.getIosVesion(client);
	}

	/**修改andriod版本信息
	 * @param vo
	 * @return
	 */
	public int updateAppVsn(AppVersionVo vo) {
		return iAppSettingDao.updateAppVsn(vo);
	}

	
	/* 查询APP版本列表
	 * @see com.amwell.service.IAppSettingService#getAppVsnList(int, int)
	 */
	public Map<String, Object> getAppVsnList(int currentPageIndex, int pageSize) {
		return iAppSettingDao.getAppVsnLIst(currentPageIndex,pageSize);
	}

	/**删除APP版本信息
	 * @param appId
	 * @return
	 */
	public int deleteAppVsn(String appId) {
		return iAppSettingDao.deleteAppVsn(appId);
	}

	/**修改IOS版本和版本号信息
	 * @param vo
	 * @return
	 */
	public int updateIosVsn(IosVersionVo vo) {
		return iAppSettingDao.updateIosVsn(vo);
	}

	/**查询IOS版本列表
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getIosVsnList(int currentPageIndex, int pageSize) {
		
		return iAppSettingDao.getIosVsnList(currentPageIndex,pageSize);
	}

	/**删除IOS版本信息
	 * @return
	 * @throws IOException 
	 */
	public int deleteIosVsn(String appId) {
		return iAppSettingDao.deleteIosVsn(appId);
	}
}
