package com.amwell.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

public class LongResultSetHandler implements ResultSetHandler<Long> {

	public Long handle(ResultSet rs) throws SQLException {
		if(rs.next()){
			try{
				BigDecimal total = rs.getBigDecimal(1);
				if(null==total){
					return 0L;
				}
				return new Long(total.longValue());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return 0L;
	}

}
