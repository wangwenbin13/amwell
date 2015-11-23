package com.pig84.ab.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.IMapDao;
import com.pig84.ab.dao.IStationInfoDao;
import com.pig84.ab.utils.Conn;
import com.pig84.ab.vo.ParamVo;
import com.pig84.ab.vo.Rect;
import com.pig84.ab.vo.StationInfo;
import com.pig84.ab.vo.StationUnion;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_1_list;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_7_list;

@Repository("mapDao")
@SuppressWarnings("all")
public class MapDaoImpl extends BaseDao implements IMapDao{
	
	@Autowired
	private IStationInfoDao stationInfoDao;
	

	/**根据两组经纬度值获得该范围内的站点**/
	@Override
	public AppVo_1_list queryNearStation(Rect rect, String cityName,String userid,boolean flag) {
		Connection conn=null;
		AppVo_1_list apps = new AppVo_1_list();
		String sql = null;
		try {
			conn =Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			
			/**判断用户是否有购票历史**/
			Long history = 0L;
			//区分华为员工
			String cond ="";
			if(!flag){
				//不是华为员工
				cond = " AND a.lineBaseId NOT IN ( SELECT lineBaseId FROM company_line ) ";
			}
			if(!StringUtils.isEmpty(userid)){
				sql = " SELECT count(*) FROM lease_base_info as a WHERE a.ispay = 1 "+cond+" AND a.passengerId = ?";
				history = qr.query(conn,sql, new ScalarHandler<Long>(1), userid);
			}
			if(history>=1L){
				apps.setA1("1");//有购买记录
			}else{
				apps.setA1("0");//无购买记录
			}
			
			
			String ids = distinctIds(qr,conn,cityName);
			
			
			sql = " SELECT station.id as a6,station.baiduLat as a2,station.baiduLng as a3,station.name as a5 FROM pb_station AS station "+
				" WHERE  station.available = 0 AND station.baiduLat >=? AND station.baiduLat<=? AND station.baiduLng >=? AND station.baiduLng<=? ";
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(rect.getLT().getLat());//minLat   最小的纬度
			paramList.add(rect.getRB().getLat());//maxLat   最大的纬度
			paramList.add(rect.getLT().getLng());//minLng   最小的经度
			paramList.add(rect.getRB().getLng());//maxLng   最大的经度
			
			
			if(!StringUtils.isEmpty(ids)){
				ids = ids.substring(0, ids.length()-1);
				String[] strs=ids.split(",");
				StringBuffer sb=new StringBuffer();
				for (String s : strs) {
					paramList.add(s);
					sb.append("?,");
				}
				sql += " AND station.id in ("+sb.substring(0,sb.length()-1)+") ";
			}
			
			Object[] params = paramList.toArray(new Object[]{});
			
			
			/***经过某一个站点的所以线路信息(一个小猪logo有多条线路信息)**/
			/**获取指定范围内的站点信息**/
			List<AppVo_7_list> appv7 = null;
			List<AppVo_7_list> ap7 = new ArrayList<AppVo_7_list>();
			int count = 1;
			appv7 = qr.query(conn,sql,new BeanListHandler<AppVo_7_list>(AppVo_7_list.class), params);
			/**迭代每一个站点,获取经过它的线路有多少条**/
			if(null!=appv7 && appv7.size()>0){
				List<AppVo_6> appv6 = null;
				for(AppVo_7_list vo:appv7){
					StationInfo stationInfo = stationInfoDao.getById(conn,vo.getA6());
					/**获取站点对应的线路信息**/
					sql = " SELECT b.orderStartTime AS a1,a.orderPrice AS a3,a.lineBaseId AS a4 "+
					" FROM line_base_info a LEFT JOIN  (SELECT lineBaseId,orderStartTime FROM line_class_info where delFlag = '0' GROUP BY lineBaseId ) AS b ON a.lineBaseId = b.lineBaseId "+
					" WHERE 1 = 1 AND a.lineType = '1' AND a.lineSubType = '0' AND a.lineStatus = '3'  "+cond+" AND a.lineBaseId = ? AND a.cityName LIKE ?";
					appv6 = qr.query(conn,sql,new BeanListHandler<AppVo_6>(AppVo_6.class), stationInfo.getLineId(),"%"+cityName+"%");
					//获取站台信息
					if(appv6!=null && appv6.size()>0){
						/***经过同一站点的线路有4条或4条以上,只标一个logo,外加一个线路条数**/
						for (int i = 0; i < appv6.size(); i++) {
							AppVo_6 appVo_6 = appv6.get(i);
							String a2 = stationInfoDao.generateStationNameArrayByLineId(appVo_6.getA4());
							if(a2!=null && !"".equals(a2)){
								appVo_6.setA5(a2.substring(0,a2.indexOf("-")));//起点
								appVo_6.setA6(a2.substring(a2.lastIndexOf("-")+1,a2.length()));//终点
							}
							if(appv6.size()>3){
								/***经过同一站点的线路有4条或4条以上,只标一个logo,外加一个线路条数**/
								vo.setA4(String.valueOf(appv6.size())); //线路条数
								vo.setList(appv6);
								vo.setA1(String.valueOf(count));
							}else if(appv6.size()>1 && appv6.size()<=3){
								/***经过同一站点的线路有1条以上,3条以下,logo的个数就是对应的条数**/
								AppVo_7_list appvo7 = new AppVo_7_list();
								List<AppVo_6> v6 = new ArrayList<AppVo_6>();
								AppVo_6 vo6 = new AppVo_6();
								vo6 = appVo_6;
								if(i==0 || i==1){
									appvo7.setA2(vo.getA2());    //纬度
									appvo7.setA3(String.valueOf((Double.valueOf(vo.getA3())+Double.valueOf(((i+1)*0.00015)))));    //经度
								}else if(i==2){
									appvo7.setA2(String.valueOf((Double.valueOf(vo.getA2())+Double.valueOf(((i+2)*0.00025)))));    //纬度
									appvo7.setA3(String.valueOf((Double.valueOf(vo.getA3())+Double.valueOf(((i+1)*0.00055)))));    //经度
								}
								appvo7.setA1(String.valueOf(count));
								appvo7.setA4("1");          //线路条数
								appvo7.setA5(vo.getA5());
								v6.add(vo6);
								appvo7.setList(v6);
								ap7.add(appvo7);
							}else if(appv6.size()==1){
								/**一个站点只有一条线路经过,logo只有1个**/
								List<AppVo_6> v6 = new ArrayList<AppVo_6>();
								AppVo_6 vo6 = new AppVo_6();
								vo6 = appVo_6;
								v6.add(vo6);
								vo.setList(v6);
								vo.setA1(String.valueOf(count));
								vo.setA4("1");
								ap7.add(vo);
							}
							count++;
						}
					}
				}
			}
			if(null!=appv7 && appv7.size()>0){
				for(AppVo_7_list vo:appv7){
					if(null!=vo.getList() && vo.getList().size()>0){
						ap7.add(vo);
					}
				}
			}
			apps.setList(ap7);
			
			conn.commit();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return apps;
	}

	/**获取最大的搜索范围**/
	@Override
	public int queryMaxBusScope() {
		String sql = " select maxBusScope from sys_app_setting ";
		List<Integer>  maxBusScopes = queryList(Integer.class, sql);
		int  maxBusScope = 2;
		if(null!=maxBusScopes && maxBusScopes.size()>0){
			maxBusScope = maxBusScopes.get(0);
		}
		return maxBusScope;
	}
	
	/**去重ID**/
	public String distinctIds(QueryRunner qr,Connection conn,String cityName){
		/**获取所有正在运营(已经发布)的线路站点**/
		String sql = " SELECT lineBaseId as a1 FROM line_base_info WHERE 1=1 "+
			" AND lineType = 1 AND lineSubType = 0 AND lineStatus = 3  AND cityName LIKE ?";
		List<AppVo_1> appv1s;
		String  ids = "";
		try {
			appv1s = qr.query(conn,sql, new BeanListHandler<AppVo_1>(AppVo_1.class), "%"+cityName+"%");
			Map<String,String> maps = new HashMap<String,String>();
			/**拼接站点字符串**/
			if(null!=appv1s && appv1s.size()>0){
				for(AppVo_1 v1:appv1s){
					List<StationInfo> stationInfoList = stationInfoDao.listByLineId(conn,v1.getA1());
					for(StationInfo stationInfo : stationInfoList){
						maps.put(stationInfo.getId(), "1");
					}
				}
			}
			/**去重**/
			if(null!=maps){
				 //获取map集合中的所有键，存入到Set集合中，  
		        Set<Map.Entry<String,String>> entry = maps.entrySet(); 
		        //通过迭代器取出map中的键值关系，迭代器接收的泛型参数应和Set接收的一致  
		        Iterator<Map.Entry<String,String>> it = entry.iterator();  
		        while (it.hasNext()){  
		            //将键值关系取出存入Map.Entry这个映射关系集合接口中  
		            Map.Entry<String,String>  me = it.next();  
		            ids += me.getKey()+",";
		        }  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	/**根据经纬度范围查找站点信息**/
	@Override
	public List<StationInfo> queryStationByLatLon(Rect rect,String cityName) {
		Connection conn=null;
		List<StationInfo> list = null;
		String sql = null;
		try {
			conn =Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			String ids = distinctIds(qr,conn,cityName);
			
			sql = " SELECT station.id,station.baiduLat,station.baiduLng,station.name FROM pb_station AS station "+
				" WHERE  station.available = 0 AND station.baiduLat >=? AND station.baiduLat<=? AND station.baiduLng >=? AND station.baiduLng<=? ";
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(rect.getLT().getLat());//minLat   最小的纬度
			paramList.add(rect.getRB().getLat());//maxLat   最大的纬度
			paramList.add(rect.getLT().getLng());//minLng   最小的经度
			paramList.add(rect.getRB().getLng());//maxLng   最大的经度
			
			
			if(!StringUtils.isEmpty(ids)){
				ids = ids.substring(0, ids.length()-1);
				String[] strs=ids.split(",");
				StringBuffer sb=new StringBuffer();
				for (String s : strs) {
					paramList.add(s);
					sb.append("?,");
				}
				sql += " AND station.id in ("+sb.substring(0,sb.length()-1)+") ";
			}
			Object[] params = paramList.toArray(new Object[]{});
			list = qr.query(conn,sql,new BeanListHandler<StationInfo>(StationInfo.class), params);
			conn.commit();
		}catch (Exception e) {
			
		}
		return list;
	}

	/**去重ID**/
	private String distinctLineUserAppIds(QueryRunner qr,Connection conn,String cityName){
		/**获取所有通过审核的招募线路站点**/
		String sql = " SELECT startAddress as a1,endAddress as a2 FROM line_user_application_new WHERE 1=1 "+
			" AND auditStatus = 1 AND cityCode = ( SELECT arearCode FROM sys_area WHERE areaName like ? AND areaLevel =2)";
		List<AppVo_2> appv1s;
		String  ids = "";
		try {
			appv1s = qr.query(conn,sql, new BeanListHandler<AppVo_2>(AppVo_2.class), "%"+cityName+"%");
			Map<String,String> maps = new HashMap<String,String>();
			/**拼接站点字符串**/
			if(null!=appv1s && appv1s.size()>0){
				for(AppVo_2 v1:appv1s){
					maps.put(v1.getA1(), "1");
					maps.put(v1.getA2(), "1");
				}
			}
			/**去重**/
			if(null!=maps){
				 //获取map集合中的所有键，存入到Set集合中，  
		        Set<Map.Entry<String,String>> entry = maps.entrySet(); 
		        //通过迭代器取出map中的键值关系，迭代器接收的泛型参数应和Set接收的一致  
		        Iterator<Map.Entry<String,String>> it = entry.iterator();  
		        while (it.hasNext()){  
		            //将键值关系取出存入Map.Entry这个映射关系集合接口中  
		            Map.Entry<String,String>  me = it.next();  
		            //使用Map.Entry中的方法获取键和值  
		            ids += me.getKey()+",";
		        }  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	/**根据经纬度范围查找站点信息--两表联合查询**/
	@Override
	public List<StationUnion> queryUnionByLatLon(Rect rect, String cityName,String stationName) {
		if (rect == null) {
			return Collections.emptyList();
		}
		Connection conn=null;
		List<StationUnion> list = null;
		String sql = null;
		try {
			conn =Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			sql = " SELECT a.id AS id,a.name AS stationName,a.baiduLng AS loni ,a.baiduLat AS lati,'1' as type FROM pb_station AS a  "+
				" WHERE  a.available = 0 AND a.baiduLat >=? AND a.baiduLat<=? AND a.baiduLng >=? AND a.baiduLng<=? ";
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(rect.getLT().getLat());//minLat   最小的纬度
			paramList.add(rect.getRB().getLat());//maxLat   最大的纬度
			paramList.add(rect.getLT().getLng());//minLng   最小的经度
			paramList.add(rect.getRB().getLng());//maxLng   最大的经度
			
			if(!StringUtils.isEmpty(stationName)){
				sql += " AND a.name LIKE ? ";
				paramList.add("%"+stationName+"%");
			}
			Object[] params = paramList.toArray(new Object[]{});
			list = qr.query(conn,sql,new BeanListHandler<StationUnion>(StationUnion.class), params);
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	/**根据经纬度范围查找站点信息--line_user_application_station_new(招募信息)**/
	@Override
	public List<StationUnion> queryUserAppByLatLon(Rect rect, ParamVo paramVo) {
		if (rect == null) {
			return Collections.emptyList();
		}
		Connection conn=null;
		List<StationUnion> list = null;
		String sql = null;
		try {
			conn =Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			List<Object> paramList = new ArrayList<Object>();
			sql = " SELECT b.appliStationId AS id,b.stationName as name,b.loni,b.lati,'2' AS type FROM line_user_application_station_new AS b  ";
			sql += " WHERE 1=1 AND b.lati >=? AND b.lati<=? AND b.loni >=? AND b.loni<=? ";
			paramList.add(rect.getLT().getLat());//minLat   最小的纬度
			paramList.add(rect.getRB().getLat());//maxLat   最大的纬度
			paramList.add(rect.getLT().getLng());//minLng   最小的经度
			paramList.add(rect.getRB().getLng());//maxLng   最大的经度
			
			String stationName = paramVo.getStationName();
			if(!StringUtils.isEmpty(stationName)){
				sql += " AND b.stationName LIKE ? ";
				paramList.add("%"+stationName+"%");
			}
			
			Object[] params = paramList.toArray(new Object[]{});
			list = qr.query(conn,sql,new BeanListHandler<StationUnion>(StationUnion.class), params);
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
