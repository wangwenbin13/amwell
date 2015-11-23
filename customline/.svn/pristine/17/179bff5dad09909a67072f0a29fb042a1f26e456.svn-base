package com.amwell.service;

import java.io.IOException;
import java.util.Map;

import com.amwell.vo.AppSettingVo;
import com.amwell.vo.AppVersionVo;
import com.amwell.vo.IosVersionVo;

public interface IAppSettingService {

	/**
	 * 修改APP设置值
	 * @return
	 */
	
	int updateAppCount(AppSettingVo appSettingVo);

	/**
	 * 查询APP设置值
	 * @return
	 */
	AppSettingVo getAppSetting();

	/**查找最新的版本号
	 * @return
	 */
	String getNewVsn();


	/**通过版本号和类型查询版本内容
	 * @param appVsn
	 * @return
	 */
	AppVersionVo getAppVesion(String appVsn);


	/**录入新版本信息(android)
	 * @param vo 
	 * @return
	 */
	int addAppVsn(AppVersionVo vo);


	/**录入新版本信息(ios)
	 * @param vo
	 * @return
	 */
	int addIosVsn(IosVersionVo vo);


	/**查询版本号是否最大值(android)
	 * @param vsnList 
	 * @param i 
	 * @param flag 
	 * @return
	 */
	AppVersionVo queryVsn(String vsnList, int i, String flag);


	/**查询版本号是否最大值(IOS)
	 * @param appType
	 * @param version
	 * @param flag 
	 * @return
	 */
	int queryIosVsn(String appType, String version, String flag);


	/**查询IOS版本信息
	 * @param string
	 * @return
	 */
	IosVersionVo getIosVesion(String client);


	/**修改andriod版本信息
	 * @param vo
	 * @return
	 */
	int updateAppVsn(AppVersionVo vo);


	/**查询APP版本列表
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getAppVsnList(int currentPageIndex, int pageSize);


	/**删除APP版本信息
	 * @param appId
	 * @return
	 */
	int deleteAppVsn(String appId);


	/**修改IOS版本和版本号信息
	 * @param vo
	 * @return
	 */
	int updateIosVsn(IosVersionVo vo);

	/**查询IOS版本列表
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getIosVsnList(int currentPageIndex, int pageSize);

	/**删除IOS版本信息
	 * @return
	 * @throws IOException 
	 */
	int deleteIosVsn(String appId);

}
