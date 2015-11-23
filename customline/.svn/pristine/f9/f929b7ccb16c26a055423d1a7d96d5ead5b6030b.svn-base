package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import com.amwell.base.DaoSupport;
import com.amwell.commons.SqlBuilder;
import com.amwell.dao.ILineCostSetDao;
import com.amwell.dao.IPublishLineDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.LineCostSetVo;
import com.amwell.vo.StationInfo;

@Repository("lineCostSetDao")
public class LineCostSetDaoImpl extends DaoSupport implements ILineCostSetDao{

	private static final Logger log = Logger.getLogger(LineCostSetDaoImpl.class);
	
	@Autowired
	private IPublishLineDao publishLineDao;
	
	/**
	 * 多条件查询线路成本设置列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize){
		Map<String, Object> res = new HashMap<String, Object>();
		super.finit("line_base_info a LEFT JOIN mgr_business b ON a.businessId = b.businessId LEFT JOIN line_cost_set c ON a.lineBaseId = c.lineBaseId");
		StringBuffer sql = new StringBuffer(
				" SELECT a.lineBaseId,a.cityName,b.brefName,a.lineSubType,a.lineName,a.orderPrice," +
				"(SELECT g.orderStartTime FROM line_class_info g WHERE g.linebaseid = a.`lineBaseId` AND g.delflag = '0' LIMIT 1) AS orderStartTime," +
				"c.theMode,c.costSharing,c.startTime,c.endTime,c.id" +
				" FROM line_base_info a LEFT JOIN mgr_business b ON a.businessId = b.businessId LEFT JOIN line_cost_set c ON a.lineBaseId = c.lineBaseId " +
				" WHERE 1=1 AND a.lineType=1 AND a.lineStatus in('0','3') ");
		List<Object> paramList = new ArrayList<Object>();
		if (null != search) {
			if (StringUtils.hasText(search.getField01())) {//供应商
				sql.append(" and b.brefName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
			}
			if (StringUtils.hasText(search.getField02())) {//线路名称
				sql.append(" and a.lineName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField02().trim()));
			}
			if (StringUtils.hasText(search.getField03())) {//成本分摊方式
				sql.append(" and c.theMode=?");
				paramList.add(search.getField03().trim());
			}
			if (StringUtils.hasText(search.getField04())) {//有效期开始时间
				sql.append(" and c.startTime >= ?");
				paramList.add(search.getField04().trim());
			}
			if (StringUtils.hasText(search.getField05())) {//有效期结束时间
				sql.append(" and c.endTime <= ?");
				paramList.add(search.getField05().trim());
			}
			if (StringUtils.hasText(search.getField06())) {//是否设置成本
				if(search.getField06().equals("0")){//未设置成本
					sql.append(" and c.theMode is null ");
				}
				if(search.getField06().equals("1")){//已设置成本
					sql.append(" and c.theMode is not null ");
				}
			}
		}
		sql.append(" order by a.createOn desc ");
		List<LineCostSetVo> list = super.tableDao.queryByPage(LineCostSetVo.class,sql.toString(),currentPage,pageSize, paramList.toArray());
		// 需要重新装list,取出起点，终点名称
		if (false == CollectionUtils.isEmpty(list)) {
			for (LineCostSetVo v : list) {
				List<StationInfo> stationInfoList = publishLineDao.listStationInfoByLineId(v.getLineBaseId());
				if(stationInfoList!=null&&stationInfoList.size()>=2){
					v.setStartStationName(stationInfoList.get(0).getName());
					v.setEndStationName(stationInfoList.get(stationInfoList.size()-1).getName());
				}
			}
		}
		Page page = new Page(list,sql.toString(),currentPage, pageSize,paramList.toArray());
		res.put("page", page);
		res.put("list", list);
		return res;
	}
	
	/**
	 * 多条件查询线路成本设置列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getListAndHistory(Search search,int currentPage, int pageSize){
		Map<String, Object> res = new HashMap<String, Object>();
		super.finit("line_base_info a LEFT JOIN mgr_business b ON a.businessId = b.businessId LEFT JOIN line_cost_set c ON a.lineBaseId = c.lineBaseId");
		StringBuffer sql = new StringBuffer(
				" SELECT a.lineBaseId,a.cityName,b.brefName,a.lineSubType,a.lineName,a.orderPrice," +
				"(SELECT g.orderStartTime FROM line_class_info g WHERE g.linebaseid = a.`lineBaseId` AND g.delflag = '0' LIMIT 1) AS orderStartTime," +
				"c.theMode,c.costSharing,c.startTime,c.endTime,c.id" +
				" FROM line_base_info a LEFT JOIN mgr_business b ON a.businessId = b.businessId LEFT JOIN line_cost_set c ON a.lineBaseId = c.lineBaseId " +
				" WHERE 1=1 AND a.lineType=1 AND a.lineStatus in('0','3') ");
		List<Object> paramList = new ArrayList<Object>();
		if (null != search) {
			if (StringUtils.hasText(search.getField01())) {//供应商
				sql.append(" and b.brefName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
			}
			if (StringUtils.hasText(search.getField02())) {//线路名称
				sql.append(" and a.lineName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField02().trim()));
			}
			if (StringUtils.hasText(search.getField03())) {//成本分摊方式
				sql.append(" and c.theMode=?");
				paramList.add(search.getField03().trim());
			}
			if (StringUtils.hasText(search.getField04())) {//有效期开始时间
				sql.append(" and c.startTime >= ?");
				paramList.add(search.getField04().trim());
			}
			if (StringUtils.hasText(search.getField05())) {//有效期结束时间
				sql.append(" and c.endTime <= ?");
				paramList.add(search.getField05().trim());
			}
			if (StringUtils.hasText(search.getField06())) {//是否设置成本
				if(search.getField06().equals("0")){//未设置成本
					sql.append(" and c.theMode is null ");
				}
				if(search.getField06().equals("1")){//已设置成本
					sql.append(" and c.theMode is not null ");
				}
			}
		}
		sql.append(" order by a.createOn desc ");
		List<LineCostSetVo> list = super.tableDao.queryByPage(LineCostSetVo.class,sql.toString(),currentPage,pageSize, paramList.toArray());
		
		//查询所有历史成本
		super.finit("line_base_info a LEFT JOIN mgr_business b ON a.businessId = b.businessId LEFT JOIN line_cost_set_history c ON a.lineBaseId = c.lineBaseId");
		sql = new StringBuffer(
				" SELECT a.lineBaseId,a.cityName,b.brefName,a.lineSubType,a.lineName,a.orderPrice," +
				"(SELECT g.orderStartTime FROM line_class_info g WHERE g.linebaseid = a.`lineBaseId` AND g.delflag = '0' LIMIT 1) AS orderStartTime," +
				"c.theMode,c.costSharing,c.startTime,c.endTime,c.id" +
				" FROM line_cost_set_history c LEFT JOIN line_base_info a ON a.lineBaseId = c.lineBaseId LEFT JOIN mgr_business b ON a.businessId = b.businessId " +
				" WHERE 1=1 AND a.lineType=1 AND a.lineStatus in('0','3') ");
		List<LineCostSetVo> historyList = super.tableDao.queryList(LineCostSetVo.class,sql.toString());

		List<LineCostSetVo> temp = new ArrayList<>();
		
		if (false == CollectionUtils.isEmpty(list)) {
			
			if (false == CollectionUtils.isEmpty(historyList)) {
				for (LineCostSetVo vo : list) {
					String lineBaseId = vo.getLineBaseId();
					for (LineCostSetVo vo1 : historyList) {
						if(lineBaseId.equals(vo1.getLineBaseId())){
							vo1.setCostSharing(vo1.getCostSharing()+"（历史）");
							temp.add(vo1);
						}
					}
					temp.add(vo);
				}
			}
			else{
				temp.addAll(list);
			}
			
			// 需要重新装list,取出起点，终点名称
			for (LineCostSetVo v : temp) {
				List<StationInfo> stationInfoList = publishLineDao.listStationInfoByLineId(v.getLineBaseId());
				if(stationInfoList!=null&&stationInfoList.size()>=2){
					v.setStartStationName(stationInfoList.get(0).getName());
					v.setEndStationName(stationInfoList.get(stationInfoList.size()-1).getName());
				}
			}
		}
		res.put("list", temp);
		return res;
	}

	/**
	 * 添加或修改线路及成本设置
	 * @param vo
	 * @return
	 */
	@Override
	public int updateLineCostSet(LineCostSetVo vo) {
		int flag=0;
		if(null!=vo){
			super.finit("line_cost_set");
			if(null==vo.getId()||"".equals(vo.getId())){//添加
				String sql="INSERT INTO line_cost_set (lineBaseId,theMode,costSharing,startTime,endTime,createBy,createOn) VALUES(?,?,?,?,?,?,?)";
				args=new Object[7];
				args[0]=vo.getLineBaseId();
				args[1]=vo.getTheMode();
				args[2]=vo.getCostSharing();
				args[3]=vo.getStartTime();
				args[4]=vo.getEndTime();
				args[5]=vo.getCreateBy();
				args[6]=vo.getCreateOn();
				flag=tableDao.executeSQL(sql, args);
				if(flag==1){
					log.info("添加线路成本设置成功，线路id："+vo.getLineBaseId()+"，用户id："+vo.getCreateBy());
				}
				return flag;
			}
			else{//修改
				//修改前将原设置信息移到历史表里
				String sql=" SELECT * FROM line_cost_set WHERE id = ? ";
				args=new Object[1];
				args[0]=vo.getId();
				LineCostSetVo oldVo=tableDao.queryBean(LineCostSetVo.class, sql, args);
				if(null!=oldVo){
					if(!(vo.getTheMode().equals(oldVo.getTheMode())&&vo.getCostSharing().equals(oldVo.getCostSharing())&&vo.getStartTime().equals(oldVo.getStartTime())&&vo.getEndTime().equals(oldVo.getEndTime()))){
						super.finit("line_cost_set_history");
						sql="INSERT INTO line_cost_set_history (lineBaseId,theMode,costSharing,startTime,endTime,createBy,createOn,updateBy,updateOn) VALUES(?,?,?,?,?,?,?,?,?)";
						args=new Object[9];
						args[0]=oldVo.getLineBaseId();
						args[1]=oldVo.getTheMode();
						args[2]=oldVo.getCostSharing();
						args[3]=oldVo.getStartTime();
						args[4]=oldVo.getEndTime();
						args[5]=oldVo.getCreateBy();
						args[6]=oldVo.getCreateOn();
						args[7]=vo.getUpdateBy();
						args[8]=vo.getUpdateOn();
						tableDao.executeSQL(sql, args);
					}
				}
				super.finit("line_cost_set");
				flag=tableDao.updateData(vo,"id");
				if(flag==1){
					log.info("修改线路成本设置成功，线路id："+vo.getLineBaseId()+"，用户id："+vo.getCreateBy());
				}
				return flag;
			}
		}
		return 0;
	}
	
	/**
	 * 获取线路及成本设置历史信息
	 * @param lineBaseId
	 * @return
	 */
	@Override
	public List<LineCostSetVo> getLineAndCostHistory(String lineBaseId){
		super.finit("line_cost_set_history");
		String str="SELECT * FROM line_cost_set_history WHERE lineBaseId = ?";
		args=new Object[1];
		args[0]=lineBaseId;
		int count=super.tableDao.queryCount(str, args);
		if(count>0){
			super.finit("line_base_info a LEFT JOIN mgr_business b ON a.businessId = b.businessId LEFT JOIN line_cost_set_history c ON a.lineBaseId = c.lineBaseId");
			StringBuffer sql = new StringBuffer(
					" SELECT a.lineBaseId,a.cityName,b.brefName,a.lineSubType,a.lineName,a.orderPrice," +
					"(SELECT g.orderStartTime FROM line_class_info g WHERE g.linebaseid = a.`lineBaseId` AND g.delflag = '0' LIMIT 1) AS orderStartTime," +
					"c.theMode,c.costSharing,c.startTime,c.endTime,c.id" +
					" FROM line_base_info a LEFT JOIN mgr_business b ON a.businessId = b.businessId LEFT JOIN line_cost_set_history c ON a.lineBaseId = c.lineBaseId " +
					" WHERE 1=1 AND a.lineType=1 AND a.lineStatus in('0','3') and a.lineBaseId = ? ");
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(lineBaseId);
			List<LineCostSetVo> list = super.tableDao.queryList(LineCostSetVo.class,sql.toString(), paramList.toArray());
			List<LineCostSetVo> resultList=new ArrayList<LineCostSetVo>();
			
			// 需要重新装list,取出起点，终点名称
			if (false == CollectionUtils.isEmpty(list)) {
				//查询最新成本设置信息，显示到列表第一条
				super.finit("line_base_info a LEFT JOIN mgr_business b ON a.businessId = b.businessId LEFT JOIN line_cost_set c ON a.lineBaseId = c.lineBaseId");
				sql = new StringBuffer(
						" SELECT a.lineBaseId,a.cityName,b.brefName,a.lineSubType,a.lineName,a.orderPrice," +
						"(SELECT g.orderStartTime FROM line_class_info g WHERE g.linebaseid = a.`lineBaseId` AND g.delflag = '0' LIMIT 1) AS orderStartTime," +
						"c.theMode,c.costSharing,c.startTime,c.endTime,c.id" +
						" FROM line_base_info a LEFT JOIN mgr_business b ON a.businessId = b.businessId LEFT JOIN line_cost_set c ON a.lineBaseId = c.lineBaseId " +
						" WHERE 1=1 AND a.lineType=1 AND a.lineStatus in('0','3') and a.lineBaseId = ? ");
				args=new Object[1];
				args[0]=lineBaseId;
				LineCostSetVo vo=tableDao.queryBean(LineCostSetVo.class, sql.toString(), args);
				
				resultList.add(vo);
				for (LineCostSetVo lineCostSetVo : list) {
					resultList.add(lineCostSetVo);
				}
				
				for (LineCostSetVo v : resultList) {
					List<StationInfo> stationInfoList = publishLineDao.listStationInfoByLineId(v.getLineBaseId());
					if(stationInfoList!=null&&stationInfoList.size()>=2){
						v.setStartStationName(stationInfoList.get(0).getName());
						v.setEndStationName(stationInfoList.get(stationInfoList.size()-1).getName());
					}
				}
			}
			
			return resultList;
		}
		else{
			return null;
		}
	}
}
