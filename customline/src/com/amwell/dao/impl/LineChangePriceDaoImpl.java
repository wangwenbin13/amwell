package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.amwell.commons.MyDataSource;
import com.amwell.dao.ILineChangePriceDao;
import com.amwell.entity.LineChangePrice;

/**
 * 线路通票修改的dao
 * @author shawn.zheng
 *
 */
@Repository("lineChangePriceDao")
public class LineChangePriceDaoImpl implements ILineChangePriceDao{
	
	private static final Logger LOG = Logger.getLogger(LineChangePrice.class);
	
	private static final QueryRunner QUERY_RUNNER = new QueryRunner();
	
	/**
	 * 获取待处理的任务列表
	 * 
	 * @return
	 */
	public List<LineChangePrice> getLineChangePriceList() {
		Connection conn = null;
		List<LineChangePrice> lineChangePriceList = null;
		try {
			conn = MyDataSource.getConnect();
			String sql = "select * from line_change_price where handleStatus=0";
			lineChangePriceList = QUERY_RUNNER.query(conn, sql,
					new BeanListHandler<LineChangePrice>(LineChangePrice.class));
		} catch (SQLException e) {
			LOG.info(e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					LOG.info(e.getMessage(), e);
				}
			}
		}
		return lineChangePriceList;
	}
	
	/**
	 * 执行修改价格的任务
	 * @param lineChangePriceList 待处理的任务列表
	 */
	public void handleLineChangePrice(List<LineChangePrice> lineChangePriceList){
		Connection conn = null;
		String sql = null;
		Object[] params = null;
		try{
			conn = MyDataSource.getConnect();
			for(LineChangePrice lineChangePrice : lineChangePriceList){
				sql = "update line_base_info set orderPrice=? where lineBaseId=?";
				params = new Object[2];
				params[0] = lineChangePrice.getNewPrice();
				params[1] = lineChangePrice.getLineId();
				QUERY_RUNNER.update(conn, sql,params);
			}
		}catch(SQLException e){
			LOG.info(e.getMessage(),e);
		}finally{
			if(conn!=null){
				try{
					conn.close();
				}catch(Exception e){
					LOG.info(e.getMessage(),e);
				}
			}
		}
	}
	
}
