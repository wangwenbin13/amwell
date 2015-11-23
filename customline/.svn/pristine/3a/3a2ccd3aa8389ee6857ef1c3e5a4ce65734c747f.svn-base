package com.amwell.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.amwell.vo.StationInfo;

/**
 * 站点Dao
 * 
 * @author shawn.zheng
 *
 */
public interface IStationInfoDao {

	/**
	 * 根据线路id获取关联站点
	 * 
	 * @param lineId
	 * @return
	 */
	public List<StationInfo> listByLineId(String lineId);

	public List<StationInfo> listByLineId(Connection conn, String lineId) throws SQLException;

	/**
	 * 根据线路Id来组建其站点的Id序列
	 * 
	 * @param lineId
	 * 
	 * @return
	 * 
	 */
	public String generateStationIdArrayByLineId(String lineId);

	public String generateStationIdArrayByLineId(Connection conn, String lineId) throws SQLException;

	/**
	 * 根据线路id来组建其站点的名称序列
	 * 
	 * @param lineId
	 * @return
	 */
	public String generateStationNameArrayByLineId(String lineId);

	public String generateStationNameArrayByLineId(Connection conn, String lineId) throws SQLException;

	/**
	 * 根据名称获取关联站点
	 * 
	 * @param name
	 * @return
	 */
	public List<StationInfo> listByName(String name);

	/**
	 * 根据id获取站点
	 * 
	 * @param id
	 * @return
	 */
	public StationInfo getById(String id);

	public StationInfo getById(Connection conn, String id) throws SQLException;

	/**
	 * 根据线路id获取其掩码
	 * 
	 * @param lineId
	 * @return
	 */
	public String generateStationMask(String lineId);

	/**
	 * 根据线路id获取其全部掩码（包括途经点）
	 * 
	 * @param lineId
	 * @return
	 */
	public String generateFullStationMask(String lineId);

	public List<StationInfo> queryStationById(String allStationIds);

}
