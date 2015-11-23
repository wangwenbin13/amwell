package com.amwell.commons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.jdbc.JDBCAppender;

public class DbAppender extends JDBCAppender {
	@Override
	protected void execute(String sql) throws SQLException {
		Connection con = null;
	    PreparedStatement pstmt = null ;
	    try
	    {
	      con = getConnection();
	      String[] sqls = sql.split("~");
	      
    	  String insertSQL = "INSERT INTO log4j (log_id, log_date, log_level, location, message) VALUES (?,?,?,?,?)" ;
    	  pstmt = connection.prepareStatement(insertSQL);
    	  pstmt.setString(1,StringUtil.getUUID()) ;
    	  pstmt.setString(2,DateUtil.getDateTime());
    	  pstmt.setString(3,sqls[0]);
    	  pstmt.setString(4,sqls[1] + "," + sqls[2]);
    	  pstmt.setString(5,sqls[3]);
    	  pstmt.executeUpdate();
    	  
	    } catch (SQLException e) {
	      if (pstmt != null)
	      pstmt.close();
	      throw e;
	    }
	    pstmt.close() ;
	    closeConnection(con);
	}
}

