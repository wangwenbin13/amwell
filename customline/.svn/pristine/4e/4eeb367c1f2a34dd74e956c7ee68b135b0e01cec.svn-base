package com.amwell.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.Begin;
import com.amwell.commons.MyDataSource;
import com.amwell.service.ILeaseOrderService;
import com.amwell.service.financial.IFinancialService;
import com.amwell.vo.app.bean.AppVo_2;
import com.amwell.vo.app.bean.AppVo_3;

/**
 * 
 * @author wangwenbin
 *
 * 2015-1-8
 */
@SuppressWarnings("all")
@ParentPackage("no-interceptor")
@Namespace("/record")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class RecordAction extends BaseAction{

	@Autowired
	private ILeaseOrderService iLeaseOrderService;
	
	@Autowired
	private IFinancialService financialService;
	
	/**
	 * 恢复财务数据
	 */
	@Action(value="recordDate",results = { @Result( type = "json") })
	public String recordDate() throws Exception{
		getResponse().setContentType("text/html;charset=UTF-8");
		String psd = request.getParameter("psd");
		if("CD5EA73CD58F827FA78EEF7197B8EE606C99B2E6".equals(psd)){
			boolean flag = new Begin().recordBack(iLeaseOrderService);
			if(flag){
				getResponse().getWriter().print("success");
			}else{
				getResponse().getWriter().print("error");
			}
		}
		return null;
	}
	
	/***
	 * 批量修改线路名称
	 */
	@Action(value="changeLineName",results = { @Result( type = "json") })
	public String changeLineName() throws Exception{
		Connection conn = null;
		String sql = null;
		try{
			conn = MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			sql = " SELECT newName AS a1,oldName AS a2 FROM sheet1 ";
			List<AppVo_2> list = qr.query(conn, sql,new BeanListHandler<AppVo_2>(AppVo_2.class));
			if(null!=list && list.size()>0){
				AppVo_2 vo = new AppVo_2();
				Object[] args = null;
				for(int i=0;i<list.size();i++){
					vo = list.get(i);
					sql = " UPDATE line_base_info SET lineName= ? WHERE lineName = ? ";
					args = new Object[2];
					args[0] = vo.getA1();
					args[1] = vo.getA2();
					qr.update(conn,sql, args);
				}
			}
			getResponse().getWriter().print("success");
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} catch (SQLException e1) {
				getResponse().getWriter().print("error");
				e1.printStackTrace();
			}
		}finally{
			try {
				if(null!=conn && conn.isClosed()==false){
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 财务统计
	 */
	@Action(value="queryStatFin",results = { @Result( type = "json") })
	public String queryStatFin() throws Exception{
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,-3);//昨天
		Date starttime=calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yesterday = sdf.format(starttime);
		financialService.addCityOrder(yesterday);
		//financialService.countInCome(yesterday);
		getResponse().getWriter().print("success");
		return null;
	}
	
	/**
	 *更改线路价格(通用价格-指定日期(包括指定日期)以后的班次价格)
	 */
	@Action(value="updateLinePrice",results = { @Result( type = "json") })
	public String updateLinePrice() throws Exception{
		Connection conn = null;
		String sql = null;
		try{
			conn = MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			sql = " SELECT A AS a1,B AS a2,C AS a3 FROM updateLinePrice ";
			List<AppVo_3> list = qr.query(conn, sql,new BeanListHandler<AppVo_3>(AppVo_3.class));
			int statu = 0;
			if(null!=list && list.size()>0){
				AppVo_3 vo = new AppVo_3();
				Object[] args = null;
				for(int i=0;i<list.size();i++){
					vo = list.get(i);
					//改线路价格
					sql = " UPDATE line_base_info SET orderPrice= ? WHERE lineName = ? ";
					args = new Object[2];
					args[0] = vo.getA2().trim();
					args[1] = vo.getA1().trim();
					statu = qr.update(conn,sql, args);
					System.out.println("line_base_info:"+statu);
					
					//改指定日期(包括指定日期)以后的班次价格
					sql = " UPDATE line_class_info SET price = ? WHERE lineBaseId = "
							+ "( SELECT lineBaseId FROM line_base_info WHERE lineName = ? AND lineType = 1 AND lineSubType = 0 AND lineStatus = 3 ) AND orderDate >= ? AND delFlag = 0 ";
					args = new Object[3];
					args[0] = vo.getA2().trim();
					args[1] = vo.getA1().trim();
					args[2] = vo.getA3().trim();
					statu = qr.update(conn, sql, args);
					System.out.println("line_class_info:"+statu);
				}
			}
			getResponse().getWriter().print("success");
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} catch (SQLException e1) {
				getResponse().getWriter().print("error");
				e1.printStackTrace();
			}
		}finally{
			try {
				if(null!=conn && conn.isClosed()==false){
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
