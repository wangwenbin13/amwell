package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.StringUtil;
import com.amwell.dao.IGiftDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.gf.GiftVo;

@Repository("giftDao")
public class GiftDaoImpl extends DaoSupport implements IGiftDao {

	/* 
	 *查询列表
	 */
	public Map<String, Object> listByPage(Search search, int currentPageIndex,int pageSize) {
		super.finit("gf_gifts,sys_admin");
		Map<String,Object> res  = new HashMap<String,Object>();
		String sql="SELECT gf.*,sy.`loginName`  FROM gf_gifts AS gf LEFT JOIN sys_admin AS sy ON gf.`operatorId`=sy.`userId`  WHERE state !=2 ";
		//设置查询条件
		List<Object> paramList = new ArrayList<Object>();
		//设置排序
		sql+=" order by gf.`giftId` DESC";
		Object[] params = paramList.toArray();
		List<GiftVo> list= super.tableDao.queryByPage(GiftVo.class, sql, currentPageIndex, pageSize, params);
		Page page = new Page(list, sql, currentPageIndex, pageSize);
		res.put("list", list);
		res.put("page", page);
		return res;
	}

	/* 
	 * 添加礼品
	 */
	public int addGiftPage(GiftVo giftModel) {
		int flag=0;
		Connection conn = MyDataSource.getConnect();
		if(conn==null){
			throw new  IllegalStateException("Connection is null");
		}
		if(giftModel==null){
			throw new IllegalArgumentException("giftModel is null");
		}
		try {
			conn.setAutoCommit(false);
			String giftPriId=StringUtil.generateSequenceNo();
			String sql ="INSERT INTO gf_gifts (giftPriId,giftId,giftName,giftType,giftValue,giftCon,state,operatorId,operateTime) VALUES (?,?,?,?,?,?,?,?,?)";
			QueryRunner qr = new QueryRunner();
			flag = qr.update(conn, sql, new Object[]{giftPriId,giftModel.getGiftId(),giftModel.getGiftName(),giftModel.getGiftType(),giftModel.getGiftValue(),giftModel.getGiftCon(),0,giftModel.getOperatorId(),giftModel.getOperateTime()});
			conn.commit();
		} catch (Exception e) {
			flag=-99;
			e.printStackTrace();
			if(conn!=null){
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
			}
		}finally{
			if(flag>0){
				AppendLog(giftModel.getOperatorId(), "礼品添加", "添加成功");
			}else{
				AppendLog(giftModel.getOperatorId(), "礼品添加", "添加失败");
			}
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (Exception e2) {
			}
		}
		return flag;
	}

	/* 
	 * 删除礼品
	 */
	public int deleteGiftPage(String giftId) {
		int flag=0;
		Connection conn = MyDataSource.getConnect();
		if(conn==null){
			throw new IllegalStateException("Connection is null!");
		}
		if(giftId==null){
			throw new IllegalArgumentException("giftId is null");
		}
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			String sql="update gf_gifts set state=? where giftId = ?";
			flag = qr.update(conn, sql, new Object[]{2,giftId});
			conn.commit();
			
		} catch (Exception e) {
			flag=-99;
			e.printStackTrace();
			if(conn!=null){
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
			}
		}finally{
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (Exception e2) {
			}
		}
		return flag;
	}

	/* 
	 * 获取礼品ID
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public String getGiftId() {
		Connection conn = MyDataSource.getConnect();
		Object result=null;
		if(conn==null){
			throw new IllegalStateException("connection is null!");
		}
		try {
			QueryRunner qr = new QueryRunner();
			String sql="SELECT MAX(giftId) AS giftid FROM gf_gifts";
			result = qr.query(conn, sql, new Object[]{}, new ResultSetHandler(){
				public Object handle(ResultSet rs) throws SQLException {
					if(rs.next()){
						return rs.getObject(1);
					}
					return null;
				}});
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null){
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
			}
		}finally{
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (Exception e2) {
			}
		}
		return result==null?null:result.toString();
	}

	/* 
	 * 添加日志
	 */
	public int addSysLog(int flag, String operatorId,String log) {
		if(flag>0){
			AppendLog(operatorId, log, log+"成功");
		}else{
			AppendLog(operatorId, log, log+"失败");
		}
		return 0;
	}

	/*
	 *查询礼品名称是否已存在
	 */
	public int getGiftName(String giftName) {
		super.finit("gf_gifts");
		int flag=0;
		String sql="select * from gf_gifts where giftName = ? and state != 2 ";
		flag = super.tableDao.queryCount(sql, new Object[]{giftName});
		
		return flag;
	}
	
	
	
	
	

}
