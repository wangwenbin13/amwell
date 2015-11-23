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
import com.amwell.dao.ISysSensitiveWordDao;
import com.amwell.vo.SysSensitiveWordVo;

@Repository("sysSensitiveWordDao")
public class SysSensitiveWordDaoImpl extends DaoSupport implements 	ISysSensitiveWordDao {
	
	private static final Logger log = Logger.getLogger(SysSensitiveWordDaoImpl.class);

	public List<SysSensitiveWordVo> getSensitiveWordList() {
		super.finit("sys_sensitiveword");
		String sql = "select a.*,b.loginName,b.userName from sys_sensitiveword a,sys_admin b where a.operateBy=b.userId   order by a.operateOn desc";
		return super.tableDao.queryList(SysSensitiveWordVo.class, sql);
	}

	public int updateSensitiveWord(SysSensitiveWordVo v) {
		Connection conn = MyDataSource.getConnect();
		if(null==conn){
			log.error("Connection is null.");
			throw new IllegalStateException("Connection is null.");
		}
		if(null==v){
			log.error("sysSensitiveWordVo is null.");
			throw new NullPointerException("sysSensitiveWordVo is null.");
		}
		int flag =-1;
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			String sql ="";
			if(StringUtils.hasText(v.getWordId())){
				//更新
				sql = "update sys_sensitiveword set sensitiveWord=?,operateBy=?,operateOn=? where wordId=?";
				flag = qr.update(conn, sql, new Object[]{v.getSensitiveWord(),v.getOperateBy(),v.getOperateOn(),v.getWordId()});
			}else{
				//新增
				sql ="insert into sys_sensitiveword(wordId,sensitiveWord,operateBy,operateOn) values(?,?,?,?)";
				flag = qr.update(conn,sql,new Object[]{StringUtil.generateSequenceNo(),v.getSensitiveWord(),v.getOperateBy(),v.getOperateOn()});
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
