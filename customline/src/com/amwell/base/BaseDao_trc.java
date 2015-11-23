package com.amwell.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.amwell.commons.MyDataSource;



/**
 * 数据库操作基础类(事务处理)
 * 
 * @author zhangqiang
 * 
 */
@SuppressWarnings("all")
public class BaseDao_trc {
	
	/**
	 * 执行批处理方法
	 * @param sqls
	 * @return
	 */
	 public final boolean excuteSqls(String[] sqls){
		boolean flag = false;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = MyDataSource.getConnect();
			// 点禁止自动提交，设置回退
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (int i = 0; i < sqls.length; i++) {
				stmt.addBatch(sqls[i]);    //合适批处理事务操作
			}
			stmt.executeBatch();
            conn.commit();//提交JDBC事务
            stmt.clearBatch();
			flag = true;
		}
		catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("运行异常，回滚事务！");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            try {
            	if(conn!=null){
            		conn.setAutoCommit(true);
            	}
            	if(stmt!=null){
            		stmt.close();
            	}
            	if(conn!=null){
            		conn.close();
            	}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return flag;
	}
}
