package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.SqlBuilder;
import com.amwell.dao.IPassengerLineDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.pc.PassengerLineVo;
import com.amwell.vo.pc.PcLineStationVo;

@Repository("passengerLineDao")
public class PassengerLineDaoImpl extends DaoSupport implements IPassengerLineDao {

	public Map<String, Object> getLineList(Search search, int currentPageIndex,int pageSize) {
		super.finit("pc_line");	
		StringBuffer sql = new StringBuffer("select a.*,b.displayId,b.nickName,b.telephone from pc_line a inner join passenger_info b on a.passengerId=b.passengerId where a.publisherFlag=1 ");
		List<Object> paramList = new ArrayList<Object>();
		if(null!=search){
			if(StringUtils.hasText(search.getField01())){
				sql.append(" and a.createOn>=?");
				paramList.add(search.getField01().trim()+" 00:00:00");
			}
			if(StringUtils.hasText(search.getField02())){
				sql.append(" and a.createOn<=?");
				paramList.add(search.getField02().trim()+" 23:59:59");
			}
			if(false==StringUtils.hasText(search.getField01())&&false==StringUtils.hasText(search.getField02())){
				//如果页面未选择时间，则默认显示昨天到今天的记录
				sql.append(" and a.createOn between ? and ?");
				Calendar ca = Calendar.getInstance();
				ca.set(Calendar.HOUR_OF_DAY, 23);
				ca.set(Calendar.MINUTE, 59);
				ca.set(Calendar.SECOND,59);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String todayTime = sdf.format(ca.getTime());
				ca.add(Calendar.DAY_OF_MONTH, -1);
				ca.set(Calendar.HOUR_OF_DAY, 0);
				ca.set(Calendar.MINUTE, 0);
				ca.set(Calendar.SECOND,0);
				String yesterdayTime = sdf.format(ca.getTime());
				paramList.add(yesterdayTime);
				paramList.add(todayTime);
			}
			if(StringUtils.hasText(search.getField03())){
			    Integer carSeats4I = null;
				try{
					carSeats4I = Integer.parseInt(search.getField03().trim());
				}catch(Exception e){
					
				}
				if(null!=carSeats4I&&carSeats4I.intValue()>0){
					sql.append(" and a.carSeats>=?");
					paramList.add(carSeats4I);
				}
			}
			if(StringUtils.hasText(search.getField04())){
			    Integer carSeats4I = null;
				try{
					carSeats4I = Integer.parseInt(search.getField04().trim());
				}catch(Exception e){
					
				}
				if(null!=carSeats4I&&carSeats4I.intValue()>0){
					sql.append(" and a.carSeats<=?");
					paramList.add(carSeats4I);
				}
			}
			if(StringUtils.hasText(search.getField05())){
				//起点
				sql.append(" and a.startStationName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
			if(StringUtils.hasText(search.getField06())){
				//终点
				sql.append(" and a.endStationName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField06().trim()));
			}
			if(StringUtils.hasText(search.getField07())){
				sql.append(" and (b.nickName like ? or b.displayId like ?)");
			    paramList.add(SqlBuilder.getSqlLikeValue(search.getField07().trim()));
			    paramList.add(SqlBuilder.getSqlLikeValue(search.getField07().trim()));
			}
//			if(StringUtils.hasText(search.getField08())){
//				sql.append(" and a.provinceCode=?");
//				paramList.add(search.getField08());
//			}
//			if(StringUtils.hasText(search.getField09())){
//				sql.append(" and a.cityCode=?");
//				paramList.add(search.getField09());
//			}
			if(StringUtils.hasText(search.getField10())){
				sql.append(" and a.lineType=?");
				paramList.add(search.getField10());
			}
			if(StringUtils.hasText(search.getField11())){
				sql.append(" and a.lineSubType=?");
				paramList.add(search.getField11());
			}
			if(StringUtils.hasText(search.getField12())){
				sql.append(" and b.telephone like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField12().trim()));
			}
		}
		sql.append(" order by a.createOn desc");
		List<PassengerLineVo> passengerLineList = super.tableDao.queryByPage(PassengerLineVo.class, sql.toString(), currentPageIndex, pageSize, paramList.toArray(new Object[]{}));
		Map<String, Object> res = new HashMap<String,Object>();
		Page page = new Page(passengerLineList,sql.toString(),currentPageIndex,pageSize,paramList.toArray(new Object[]{}));
		res.put("page", page);
		res.put("list",passengerLineList);
		return res;
	}

	public PassengerLineVo getPcLineDetail(String pcLineId) {
		super.finit("pc_line");	
		String sql = "select a.*,b.displayId,b.nickName,b.telephone from pc_line a inner join passenger_info b on a.passengerId=b.passengerId where a.publisherFlag=1 and pcLineId=?";
		PassengerLineVo v = super.tableDao.queryBean(PassengerLineVo.class, sql, new Object[]{pcLineId});
		if(null!=v){
			v.setStationList(getPassengerLineStationList(v.getStationInfoes()));
		}
		return v;
	}
		

	private List<PcLineStationVo> getPassengerLineStationList(String pcLineId){
		super.finit("pc_line_station");
		String sql= "select * from pc_line_station where pcStationId in("+pcLineId+") order by field(pcStationId,"+pcLineId+")";
		return super.tableDao.queryList(PcLineStationVo.class, sql);
	}

	public int updateLineOnOff(String pcLineId, int lineOnOff) {
		int flag = 0;
		if(StringUtils.hasText(pcLineId)&&(lineOnOff==0||lineOnOff==1)){
			Connection conn = MyDataSource.getConnect();
			if(conn==null){
				return -9999;
			}
			try {
				conn.setAutoCommit(false);
				QueryRunner qr = new QueryRunner();
				String updateSql = "update pc_line set lineOnOff=? where pcLineId=?";
				flag = qr.update(conn, updateSql, new Object[]{lineOnOff,pcLineId});
				conn.commit();
			} catch (Exception e) {
				flag = -9999;
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			}finally{
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
			
		}
		return flag;
	}

}
