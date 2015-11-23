package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.dao.IStationInfoDao;
import com.amwell.vo.StationInfo;

@Repository
public class StationInfoDao extends DaoSupport implements IStationInfoDao {

	@Override
	public List<StationInfo> listByLineId(String lineId) {
		super.finit("pb_station");
		String sql = "select * from pb_station where lineId=? and available=0 and type!=2 order by sortNum";
		Object[] params = new Object[1];
		params[0] = lineId;
		List<StationInfo> stationList = super.tableDao.queryList(StationInfo.class, sql, params);
		return stationList;
	}

	public List<StationInfo> listByLineId(Connection conn, String lineId) throws SQLException {
		String sql = "select * from pb_station where lineId=? and available=0 and type!=2 order by sortNum";
		Object[] params = new Object[1];
		params[0] = lineId;
		QueryRunner queryRunner = new QueryRunner();
		List<StationInfo> stationList = queryRunner.query(conn, sql,
				new BeanListHandler<StationInfo>(StationInfo.class), params);
		return stationList;
	}

	public List<StationInfo> listAllByLineId(String lineId) {
		super.finit("pb_station");
		String sql = "select * from pb_station where lineId=? and available=0 order by sortNum";
		Object[] params = new Object[1];
		params[0] = lineId;
		List<StationInfo> stationList = super.tableDao.queryList(StationInfo.class, sql, params);
		return stationList;
	}

	/** 根据站点ID获取站点信息 **/
	public List<StationInfo> queryStationById(String allStationIds) {
		List<StationInfo> list = null;
		super.finit("pb_station");
		String sql = null;
		if (!StringUtils.isEmpty(allStationIds)) {
			sql = " SELECT id,name,baiduLng,baiduLat FROM pb_station WHERE 1=1 ";
			allStationIds = allStationIds.substring(0, allStationIds.length() - 1);
			String[] strs = allStationIds.split(",");
			StringBuffer sb = new StringBuffer();
			List<String> paramList = new ArrayList<String>();
			for (String s : strs) {
				paramList.add(s);
				sb.append("?,");
			}
			sql += " AND id in (" + sb.substring(0, sb.length() - 1) + ") ";
			list = tableDao.queryList(StationInfo.class, sql, paramList.toArray());
		}
		return list;
	}

	@Override
	public List<StationInfo> listByName(String name) {
		super.finit("pb_station");
		String sql = "select * from pb_station where name like ? and available=0 and type!=2 order by sortNum";
		Object[] params = new Object[1];
		params[0] = "%" + name + "%";
		List<StationInfo> stationList = super.tableDao.queryList(StationInfo.class, sql, params);
		return stationList;
	}

	@Override
	public StationInfo getById(String id) {
		super.finit("pb_station");
		String sql = "select * from pb_station where id = ? and available=0 order by sortNum";
		Object[] params = new Object[1];
		params[0] = id;
		StationInfo stationInfo = super.tableDao.queryBean(StationInfo.class, sql, params);
		return stationInfo;
	}

	@Override
	public StationInfo getById(Connection conn, String id) throws SQLException {
		String sql = "select * from pb_station where id = ? and available=0 order by sortNum";
		Object[] params = new Object[1];
		params[0] = id;
		QueryRunner queryRunner = new QueryRunner();
		StationInfo stationInfo = queryRunner.query(conn, sql, new BeanHandler<StationInfo>(StationInfo.class), params);
		return stationInfo;
	}

	/**
	 * 根据线路Id来组建其站点的Id序列
	 * 
	 * @param lineId
	 * 
	 * @return
	 * 
	 */
	@Override
	public String generateStationIdArrayByLineId(String lineId) {
		List<StationInfo> stationInfoList = listByLineId(lineId);
		StringBuffer stationIdArray = new StringBuffer();
		if (stationInfoList != null) {
			for (int index = 0; index < stationInfoList.size(); index++) {
				StationInfo stationInfo = stationInfoList.get(index);
				if (stationInfo.getType() != 2) {
					stationIdArray.append(stationInfo.getId());
					if (index < stationInfoList.size() - 1) {
						stationIdArray.append(",");
					}
				}
			}
		}
		return stationIdArray.toString();
	}

	@Override
	public String generateStationIdArrayByLineId(Connection conn, String lineId) throws SQLException {
		List<StationInfo> stationInfoList = listByLineId(conn, lineId);
		StringBuffer stationIdArray = new StringBuffer();
		if (stationInfoList != null) {
			for (int index = 0; index < stationInfoList.size(); index++) {
				StationInfo stationInfo = stationInfoList.get(index);
				if (stationInfo.getType() != 2) {
					stationIdArray.append(stationInfo.getId());
					if (index < stationInfoList.size() - 1) {
						stationIdArray.append(",");
					}
				}
			}
		}
		return stationIdArray.toString();
	}

	/**
	 * 根据线路id来组建其站点的名称序列
	 * 
	 * @param lineId
	 * @return
	 */
	@Override
	public String generateStationNameArrayByLineId(String lineId) {
		List<StationInfo> stationInfoList = listByLineId(lineId);
		StringBuffer stationIdArray = new StringBuffer();
		if (stationInfoList != null) {
			for (int index = 0; index < stationInfoList.size(); index++) {
				StationInfo stationInfo = stationInfoList.get(index);
				if (stationInfo.getType() != 2) {
					stationIdArray.append(stationInfo.getName());
					if (index < stationInfoList.size() - 1) {
						stationIdArray.append("-");
					}
				}
			}
		}
		return stationIdArray.toString();
	}

	@Override
	public String generateStationNameArrayByLineId(Connection conn, String lineId) throws SQLException {
		List<StationInfo> stationInfoList = listByLineId(conn, lineId);
		StringBuffer stationIdArray = new StringBuffer();
		if (stationInfoList != null) {
			for (int index = 0; index < stationInfoList.size(); index++) {
				StationInfo stationInfo = stationInfoList.get(index);
				if (stationInfo.getType() != 2) {
					stationIdArray.append(stationInfo.getName());
					if (index < stationInfoList.size() - 1) {
						stationIdArray.append("-");
					}
				}
			}
		}
		return stationIdArray.toString();
	}

	public String generateStationMask(String lineId) {
		List<StationInfo> stationInfoList = listByLineId(lineId);
		StringBuffer stationMaskArray = new StringBuffer();
		if (stationInfoList != null) {
			for (int index = 0; index < stationInfoList.size(); index++) {
				StationInfo stationInfo = stationInfoList.get(index);
				if (stationInfo.getType() == 1) {
					stationMaskArray.append("1").append(",");
				} else if (stationInfo.getType() == 0) {
					stationMaskArray.append("0").append(",");
				} else {
					continue;
				}
			}
		}
		String stationMask = stationMaskArray.toString();
		return stationMask.substring(0, stationMask.length() - 1);
	}

	public String generateFullStationMask(String lineId) {
		List<StationInfo> stationInfoList = listAllByLineId(lineId);
		StringBuffer stationFullMaskArray = new StringBuffer();
		if (stationInfoList != null) {
			for (int index = 0; index < stationInfoList.size(); index++) {
				StationInfo stationInfo = stationInfoList.get(index);
				if (stationInfo.getType() == 2) {
					stationFullMaskArray.append("0").append(",");
				} else {
					stationFullMaskArray.append("1").append(",");
				}
			}
		}
		String stationMask = stationFullMaskArray.toString();
		return stationMask.substring(0, stationMask.length() - 1);
	}

}
