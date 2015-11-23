package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.StringUtil;
import com.amwell.dao.IPinCheLineValidDateDao;
import com.amwell.vo.pc.PcLineValidityVo;

@Repository("pinCheLineValidDateDao")
public class PinCheLineValidDateDaoImpl extends DaoSupport implements IPinCheLineValidDateDao {
		

	private static final Logger log = Logger.getLogger(PinCheLineValidDateDaoImpl.class);
	
	public List<PcLineValidityVo> getValidDateList() {
		super.finit("pc_line_validity a,sys_admin b");
		String sql = "select a.*,b.loginName,b.userName from pc_line_validity a,sys_admin b where a.operateBy=b.userId   order by a.operateOn desc";
		return super.tableDao.queryList(PcLineValidityVo.class, sql);
	}

	public int updateValidDate(PcLineValidityVo v) {
		Connection conn = MyDataSource.getConnect();
		if(null==conn){
			log.error("Connection is null.");
			throw new IllegalStateException("Connection is null.");
		}
		if(null==v){
			log.error("createPcLineValidityVo is null.");
			throw new NullPointerException("createPcLineValidityVo is null.");
		}
		int flag =-1;
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			String sql ="";
			if(StringUtils.hasText(v.getId())){
				//更新
				sql = "update pc_line_validity set validityDays=?,operateBy=?,operateOn=? where id=?";
				flag = qr.update(conn, sql, new Object[]{v.getValidityDays(),v.getOperateBy(),v.getOperateOn(),v.getId()});
			}else{
				//新增
				sql ="insert into pc_line_validity(id,validityDays,operateBy,operateOn) values(?,?,?,?)";
				flag = qr.update(conn,sql,new Object[]{StringUtil.generateSequenceNo(),v.getValidityDays(),v.getOperateBy(),v.getOperateOn()});
			}
			
			conn.commit();
		} catch (Exception e) {
			flag = -9999;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e.getLocalizedMessage());
			}
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return flag;
	}

}
