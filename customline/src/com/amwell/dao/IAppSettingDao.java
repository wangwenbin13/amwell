package com.amwell.dao;

import java.io.IOException;
import java.util.Map;

import com.amwell.vo.AppSettingVo;
import com.amwell.vo.AppVersionVo;
import com.amwell.vo.IosVersionVo;
import com.amwell.vo.bc.BcTimeSetVo;

public interface IAppSettingDao {


	/**
	 * 修改APP设置
	 * @param userId
	 * @return
	 */
	int updateAppCount(AppSettingVo appSettingVo);

	AppSettingVo getAppSet();

//	AppSettingVo getDelay();

	AppSettingVo getAppSend();

	BcTimeSetVo getBcTime();

	int addAppSet(int i);

	/**
	 * 查找最新的版本号
	 * @return
	 */
	String getNewVsn();

	/**通过版本号与类型查询版本信息
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
	 * @param i 
	 * @param flag 
	 * @param vsnList 
	 * @return
	 */
	AppVersionVo queryVsn(String vsn, int i, String flag);

	/**查询版本号是否最大值(IOS)
	 * @param appType
	 * @param version
	 * @param flag 
	 * @return
	 */
	int queryIosVsn(String appType, String version, String flag);

	/**查询IOS版本信息
	 * @param client
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
	Map<String, Object> getAppVsnLIst(int currentPageIndex, int pageSize);

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
