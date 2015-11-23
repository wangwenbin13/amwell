package com.pig84.ab.dao.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.IStationInfoDao;
import com.pig84.ab.vo.Coord;
import com.pig84.ab.vo.LineBaseInfo;
import com.pig84.ab.vo.LineSplitInfo;
import com.pig84.ab.vo.LineUnion;
import com.pig84.ab.vo.ParamVo;
import com.pig84.ab.vo.StationInfo;
import com.pig84.ab.vo.StationUnion;
import com.pig84.ab.vo.bean.AppVo_13;
import com.pig84.ab.vo.bean.AppVo_14;
import com.pig84.ab.vo.bean.AppVo_5;

/**
 * 
 * @author wangwenbin
 *
 */
@Repository
public class RePeat {
	
	@Autowired
	private IStationInfoDao stationInfoDao;

	/**去重**/
	public String quChong(String str){
		String valueString = "";
		Map<String,String> maps = new HashMap<String,String>();
		if(!StringUtils.isEmpty(str)){
			String strs[] = str.split(",");
			if(strs.length>0){
				for(String s:strs){
					if(!StringUtils.isEmpty(s)){
						maps.put(s, "1");
					}
				}
			}
			/**去重**/
			if(null!=maps){
				 //获取map集合中的所有键，存入o到Set集合中，  
		        Set<Map.Entry<String,String>> entry = maps.entrySet(); 
		        //通过迭代器取出map中的键值关系，迭代器接收的泛型参数应和Set接收的一致  
		        Iterator<Map.Entry<String,String>> it = entry.iterator();  
		        while (it.hasNext())  
		        {  
		            //将键值关系取出存入Map.Entry这个映射关系集合接口中  
		            Map.Entry<String,String>  me = it.next();  
		            valueString += me.getKey()+",";
		        }  
			}
		}
		return valueString;
	}
	
	/****
	 * 获取所有站点的起点
	 */
	public Map<String, Object> queryLineBaseInfoStartStation(List<LineBaseInfo> lineBaseInfos) {
		Map<String, Object> maps = new HashMap<String, Object>();
		String startStation = "";
		String orderPrice = "0";//线路价格
		String price = "0";//班次价格
		if(null!=lineBaseInfos && lineBaseInfos.size()>0){
			for(LineBaseInfo baseInfo : lineBaseInfos){
				List<StationInfo> stationInfoList = stationInfoDao.listByLineId(baseInfo.getLineBaseId());
				String startStationId = stationInfoList.get(0).getId();
				startStation += startStationId + ",";
				baseInfo.setFromStation(startStationId);
				//如果线路价格大于班次价格,则为优惠价格
				if(!StringUtils.isEmpty(baseInfo.getOrderPrice())){
					orderPrice = baseInfo.getOrderPrice();
				}
				if(!StringUtils.isEmpty(baseInfo.getPrice())){
					price = baseInfo.getPrice();
				}
				if(Double.parseDouble(orderPrice)>Double.parseDouble(price)){
					baseInfo.setYouhui("1");//有优惠
					baseInfo.setOrderPrice(price);
				}else{
					baseInfo.setYouhui("0");//无优惠
				}
			}
		}
		maps.put("startStation", startStation);
		maps.put("lineBaseInfos", lineBaseInfos);
		return maps;
	}
	
	
	/**组装数据**/
	public static List<AppVo_14> queryPutValueTogetherLineUnion(List<LineUnion> sids,
			Map<String, StationInfo> maps, Map<String, LineBaseInfo> lineStartMap,Integer sourceBy,ParamVo paramVo) {
		List<AppVo_14> vos = new ArrayList<AppVo_14>();
		StationInfo ls = null;
		//double保留两位小数
		DecimalFormat df = new DecimalFormat("######0.00");
		if(null!=sids && sids.size()>0){
			for(LineUnion lineSplitInfo : sids){
				AppVo_14 vo = new AppVo_14();
				LineBaseInfo lineInfo = lineStartMap.get(lineSplitInfo.getLinebaseid());
				if(null!=lineInfo){
					vo.setA1(lineSplitInfo.getLinebaseid());//线路ID
					vo.setA2(maps.get(lineInfo.getFromStation()).getName());//线路起始点
					vo.setA3(lineInfo.getOrderStartTime());//发车时间
					vo.setA4(lineInfo.getOrderPrice());//票价
					StationInfo bStation = maps.get(lineSplitInfo.getBstation());
					if(bStation!=null){
						vo.setA5(bStation.getName());//上车点
					}
					StationInfo eStation = maps.get(lineSplitInfo.getEstation());
					if(eStation!=null){
						vo.setA6(eStation.getName());//下车点
					}
					vo.setA7(lineInfo.getLineKm());//全程距离
					vo.setA8(lineInfo.getLineTime());//行程时间
					vo.setA9(lineSplitInfo.getId());
					vo.setA10(lineInfo.getYouhui());//是否优惠
					vo.setA13(lineInfo.getLineName());//线路名称
					vo.setA14(lineInfo.getOriginalPrice());//原价
					
					//排序依据 0或1:起点经纬度  2:终点经纬度
					if(0==sourceBy || 1==sourceBy){
						ls = bStation;
						double dis = 0.0;
						if(null==ls || ls.getCoord() == null){
							dis = 0.0;
						}else{
							dis  = ls.getCoord().distance(new Coord(Double.parseDouble( paramVo.getsLon()),Double.parseDouble( paramVo.getsLat())));
						}
						vo.setA11(String.valueOf(df.format(dis)));//距离
					}else if(2==sourceBy){
						ls = eStation;
						double dis = 0.0;
						if(null==ls || ls.getCoord() == null){
							dis = 0.0;
						}else{
							dis  = ls.getCoord().distance(new Coord(Double.parseDouble( paramVo.geteLon()),Double.parseDouble( paramVo.geteLat())));
						}
						vo.setA11(String.valueOf(df.format(dis)));
					}
					vo.setA12("1");//发布线路
					vos.add(vo);
				}
			}
			
		}
		return vos;
	}
	
	/**组装数据**/
	public static List<AppVo_14> queryPutValueTogether(List<LineSplitInfo> sids,
			Map<String, StationInfo> maps, Map<String, LineBaseInfo> lineStartMap,Integer sourceBy,ParamVo paramVo) {
		List<AppVo_14> vos = new ArrayList<AppVo_14>();
		StationInfo ls = null;
		//double保留两位小数
		DecimalFormat df = new DecimalFormat("######0.00");
		StationInfo stationInfo = null; 
		if(null!=sids && sids.size()>0){
			for(LineSplitInfo lineSplitInfo : sids){
				AppVo_14 vo = new AppVo_14();
				LineBaseInfo lineInfo = lineStartMap.get(lineSplitInfo.getLinebaseid());
				if(null!=lineInfo){
					vo.setA1(lineSplitInfo.getLinebaseid());//线路ID
					vo.setA2(maps.get(lineInfo.getFromStation()).getName());//线路起始点
					vo.setA3(lineInfo.getOrderStartTime());//发车时间
					vo.setA4(lineInfo.getOrderPrice());//票价
					stationInfo = maps.get(lineSplitInfo.getBstation());
					if(null!=stationInfo){
						vo.setA5(stationInfo.getName());//上车点
					}
					stationInfo = maps.get(lineSplitInfo.getEstation());
					if(null!=stationInfo){
						vo.setA6(stationInfo.getName());//下车点
					}
					vo.setA7(lineInfo.getLineKm());//全程距离
					vo.setA8(lineInfo.getLineTime());//行程时间
					vo.setA9(lineSplitInfo.getSid());
					vo.setA10(lineInfo.getYouhui());//是否优惠
					vo.setA12(lineInfo.getOriginalPrice());//原价
					
					//排序依据 0或1:起点经纬度  2:终点经纬度
					if(0==sourceBy || 1==sourceBy){
						ls = maps.get(lineSplitInfo.getBstation());
						double dis = 0.00;
						if( null== paramVo || null==ls || ls.getCoord() == null || StringUtils.isEmpty(paramVo.getsLon()) || StringUtils.isEmpty(paramVo.getsLat())){
							dis = 0.00;
						}else{
							dis  = ls.getCoord().distance(new Coord(Double.parseDouble( paramVo.getsLon()),Double.parseDouble( paramVo.getsLat())));
						}
						vo.setA11(String.valueOf(df.format(dis)));
					}else if(2==sourceBy){
						ls = maps.get(lineSplitInfo.getEstation());
						double dis = 0.00;
						if(null== paramVo || null==ls || ls.getCoord() == null || StringUtils.isEmpty(paramVo.getsLon()) || StringUtils.isEmpty(paramVo.getsLat())){
							dis = 0.00;
						}else{
							dis  = ls.getCoord().distance(new Coord(Double.parseDouble( paramVo.getsLon()),Double.parseDouble( paramVo.getsLat())));
						}
						vo.setA11(String.valueOf(df.format(dis)));
					}
					vos.add(vo);
				}
			}
			
		}
		return vos;
	}

	/**组装招募线路信息**/
	public static List<AppVo_14> queryPutValueTogeterZhaoMu(
			List<LineUnion> zhaomu, Map<String, StationUnion> zhaomumap,
			Integer sourceBy, ParamVo paramVo,Map<String, String> appMap) {
		List<AppVo_14> vos = new ArrayList<AppVo_14>();
		//double保留两位小数
		DecimalFormat df = new DecimalFormat("######0.00");
		StationUnion ls = null;
		String baoming = "0";
		if(null!=zhaomu && zhaomu.size()>0){
			for(LineUnion lineUnion : zhaomu){
				AppVo_14 vo = new AppVo_14();
				vo.setA1(lineUnion.getId());//招募线路的ID
				vo.setA2(lineUnion.getApplyNum().toString());//报名人数
				vo.setA3(lineUnion.getStartTime());//发车时间
				vo.setA4(lineUnion.getLinePrice());//票价
				ls = zhaomumap.get(lineUnion.getBstation());
				if(null!=ls){
					vo.setA5(ls.getStationName());//上车点
				}
				ls = zhaomumap.get(lineUnion.getEstation());
				if(null!=ls){
					vo.setA6(ls.getStationName());//下车点
				}
				vo.setA7(lineUnion.getLineKm());//全程距离
				vo.setA8(lineUnion.getCostTime());//行程时间
				vo.setA9("");//子线路ID
				baoming = appMap.get(lineUnion.getId());
				if(StringUtils.isEmpty(baoming)){
					baoming = "0";
				}else{
					baoming = "1";
				}
				vo.setA10(baoming);//是否报名     1:已报名   0:未报名
				
				//排序依据 0或1:起点经纬度  2:终点经纬度
				if(0==sourceBy || 1==sourceBy){
					ls = zhaomumap.get(lineUnion.getBstation());
					double dis  = new Coord(Double.parseDouble(ls.getLoni()), Double.parseDouble(ls.getLati()))
									.distance(new Coord(Double.parseDouble( paramVo.getsLon()),Double.parseDouble( paramVo.getsLat())));
					vo.setA11(String.valueOf(df.format(dis)));
				}else if(2==sourceBy){
					ls = zhaomumap.get(lineUnion.getEstation());
					double dis  = new Coord(Double.parseDouble(ls.getLoni()), Double.parseDouble(ls.getLati()))
								.distance(new Coord(Double.parseDouble( paramVo.geteLon()),Double.parseDouble( paramVo.geteLat())));
					vo.setA11(String.valueOf(df.format(dis)));
				}
				vo.setA12("2");//招募线路
				vos.add(vo);
			}
		}
		return vos;
	}
	
	
	/**
	 * 计算退票相关的数据
	 * @return
	 */
	public static AppVo_5 countTickertMoney(String prices,Integer percent){
		AppVo_5 vo = new AppVo_5();
		String[] price = prices.split(",");
		BigDecimal totalPrice = new BigDecimal("0.00");//退票总价
		for(String p : price){
			if(!StringUtils.isEmpty(p)){
				totalPrice = totalPrice.add(new BigDecimal(p));
			}
		}
		vo.setA1(totalPrice.toString());//退票的总价
		BigDecimal bigPer = new BigDecimal(percent).divide(new BigDecimal("100"));//BigDecimal的百分比
		vo.setA2(bigPer.toString());//手续费百分比
		vo.setA5(totalPrice.multiply(bigPer).setScale(2, BigDecimal.ROUND_HALF_UP).toString());//手续费  保留2位小数
		totalPrice = totalPrice.subtract(totalPrice.multiply(bigPer));
		vo.setA3(totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());//实际退票金额  保留2位小数
		return vo;
	}

	/**
	 * 算出所有有效站点的和当前点的距离
	 * @param poiStar
	 * @param paramVo
	 * @return
	 */
	public static List<StationUnion> countDist(List<StationUnion> stats, ParamVo paramVo,int sourceBy) {
		if(null!=stats && stats.size()>0){
			double dis = 0.0;
			DecimalFormat df = new DecimalFormat("######0.00");
			for(StationUnion stat: stats){
				if(null!=stat && (StringUtils.isEmpty(stat.getLati()) || "0".equals(stat.getLati())
						|| StringUtils.isEmpty(stat.getLoni()) || "0".equals(stat.getLoni()))){
					stat.setDistance("0");
				}else{
					String lon = null;
					String lat = null;
					if(2!=sourceBy){
						lon = paramVo.getsLon();
						lat = paramVo.getsLat();
					}else{
						lon = paramVo.geteLon();
						lat = paramVo.geteLat();
					}
					dis  = new Coord(Double.parseDouble(stat.getLoni()), Double.parseDouble(stat.getLati()))
							.distance(new Coord(Double.parseDouble(lon),Double.parseDouble(lat)));
					stat.setDistance(String.valueOf(df.format(dis)));
				}
			}
		}
		return stats;
	}
}
