package com.amwell.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.commons.Sha1Utils;
import com.amwell.dao.IPassengerInfoDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.PassengerOrderVo;
import com.amwell.vo.app.PassengerInfo;
import com.amwell.vo.app.bean.AppVo_1;

/**
 * 乘客dao实现类
 * @author 龚雪婷
 *
 */
@Repository
public class PassengerInfoDaoImpl extends DaoSupport implements IPassengerInfoDao {

	/**
	 * 分页查询乘客列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> listByPage1(Search search,int p,int pageSize){
		super.finit("v_passenger_info");
//		sql = "select *,ye as theBalance from v_passenger_info ";
//		sql = "select * from v_passenger_info ";
		sql = "select * from passenger_info ";
		List<Object> conditions=new ArrayList<Object>();
		if(search!= null){
			String cond = " where 1 = 1 ";
			//开始日期
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(registerTime,10) >= ?";
				conditions.add(search.getField01().trim());
			}
			//结束日期
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(registerTime,10) <= ?";
				conditions.add(search.getField02().trim());
			}
			//乘客昵称 或者 ID
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and (nickName like ? or displayId like ?)";
				conditions.add("%"+search.getField03().trim()+"%");
				conditions.add("%"+search.getField03().trim()+"%");
			}
			//手机号码
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and telephone like ?";
				conditions.add("%"+search.getField04().trim()+"%");
			}
			//账号状态
			if(StringUtils.isNotBlank(search.getField05())){
				String[] str=search.getField05().split(",");
				StringBuffer sb=new StringBuffer();
				for (String string : str) {
					sb.append("?,");
					conditions.add(string);
				}
				cond+= " and blackFlag in("+sb.substring(0,sb.length()-1)+")";
			}
			//乘客类型（-1-所有乘客 0-app注册乘客 1-微信注册乘客）
			if(StringUtils.isNotBlank(search.getField09())&&!"-1".equals(search.getField09())){
				cond+= " and bindStatus = ?";
				conditions.add(search.getField09());
			}
			if(StringUtils.isNotBlank(search.getField10())){
				//注册设备类型（1-android  2-ios   3-微信）
				//根据终端类型查询乘客数据
				if(search.getField10().equals("1")||search.getField10().equals("2")){
					cond+= " and terminal <> ?";
					conditions.add("3");
				}else{
					cond+= " and terminal =?";
					conditions.add(search.getField10());
				}
			}
			sql = sql+cond;
			
			//排序列名
			if(StringUtils.isNotBlank(search.getField08())){
				sql=sql+" order by "+search.getField08().trim();
//				conditions.add(search.getField08().trim());
			}
			else{
				sql=sql+" order by registerTime";
			}
			
			//排序方式
			if(StringUtils.isNotBlank(search.getField07())){
				if(search.getField07().equals("ASC")){
					sql=sql+" ASC";
				}
                if(search.getField07().equals("DESC")){
                	sql=sql+" DESC";
				}
				
				//conditions.add(search.getField07().trim());
			}
			else{
				sql=sql+" desc";
			}
		}
		else{
			//默认根据registerTime字段排序
			sql=sql+" order by registerTime desc";
		}
		
		tableDao.setPageSize(pageSize);
		list = tableDao.queryByPage(PassengerInfoEntity.class, sql,p,conditions.toArray());
		
//		//获取账户余额
		for (Object o : list) {
			PassengerInfoEntity passengerInfo=(PassengerInfoEntity) o;
			passengerInfo.setTheBalance(getUserBalance(passengerInfo.getPassengerId()));
			//获取订单数量
			super.finit(" lease_base_info lbi,line_business_order lbo,line_class_info lci ");
			String sql_1 = " SELECT lbo.* " +
					" FROM lease_base_info lbi,line_business_order lbo,line_class_info lci " +
					" WHERE lbi.leaseOrderNo=lbo.leaseOrderId AND lbo.lineClassId = lci.lineClassId AND lbi.ispay = 1 AND " +
					" CONCAT(lci.orderDate,' ',lci.orderStartTime)>NOW() AND lbo.passengerId = '"+passengerInfo.getPassengerId()+"'";
			int result1 = tableDao.queryCount(sql_1);
			passengerInfo.setOrderCount(result1);
			//改签数量
			super.finit("passenger_change_order");
			String sql_2 = "SELECT * FROM passenger_change_order where passengerId = '"+passengerInfo.getPassengerId()+"'";
			int result2 = tableDao.queryCount(sql_2);
			passengerInfo.setChangeOrderCount(result2);
			//退票数量
			super.finit("return_ticket");
			String sql_3 = "SELECT * FROM return_ticket where displayId = '"+passengerInfo.getDisplayId()+"'";
			int result3 = tableDao.queryCount(sql_3);
			passengerInfo.setReturnedOrderCount(result3);
		}
		page = new Page(list,sql,p,pageSize,conditions.toArray());
		map.put("list", list);
		map.put("page", page);
		return map;
	}
	
	public Map<String,Object> listByPage(Search search,int p,int pageSize,String sourcefrom){
		super.finit("v_passenger_info");
		String sql = "";
		String anSql = "";
		anSql = " ,(SELECT SUM(s.moneyLimit) FROM stat_passenger_recharge s WHERE s.passengerid = p.passengerId) AS income, " +
				" (SELECT SUM(l.leaseLimit) FROM stat_passenger_consum_lease l WHERE l.passengerid = p.passengerId) AS outlay, " +
				" (SELECT COUNT(pco.id) FROM passenger_change_order pco WHERE pco.passengerId = p.passengerId) AS changeOrderCount, " +
				" (SELECT COUNT(rt.returnId) FROM return_ticket rt WHERE rt.passengerId = p.passengerId) AS returnedOrderCount " ;
		sql = " SELECT p.*" +anSql+" FROM passenger_info p";
		List<Object> conditions=new ArrayList<Object>();
		if(search!= null){
			String cond = " where 1 = 1 ";
			//开始日期
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and registerTime >= ?";
				conditions.add(search.getField01().trim()+" 00:00:00");
			}
			//结束日期
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and registerTime <= ?";
				conditions.add(search.getField02().trim()+" 00:00:00");
			}
			//乘客昵称 或者 ID
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and (nickName like ? or displayId like ?)";
				conditions.add("%"+search.getField03().trim()+"%");
				conditions.add("%"+search.getField03().trim()+"%");
			}
			//手机号码
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and telephone like ?";
				conditions.add("%"+search.getField04().trim()+"%");
			}
			//账号状态
			if(StringUtils.isNotBlank(search.getField05())){
				String[] str=search.getField05().split(",");
				StringBuffer sb=new StringBuffer();
				for (String string : str) {
					sb.append("?,");
					conditions.add(string);
				}
				cond+= " and blackFlag in("+sb.substring(0,sb.length()-1)+")";
			}
			
			if("1".equals(sourcefrom)){//小猪巴士用户
				if(StringUtils.isNotBlank(search.getField10())){
					//注册设备类型（1-android  2-ios   3-微信）
					//根据终端类型查询乘客数据
					if(search.getField10().equals("1")||search.getField10().equals("2")){
						cond+= " and sourcefrom = '0'";//app用户
					}else{
						cond+= " and sourcefrom = '3'";//微信用户
					}
				}
			}
            if("2".equals(sourcefrom)){//第三方用户
            	if(StringUtils.isNotBlank(search.getField06())){
        			cond+= " and sourcefrom = '"+search.getField06()+"'";
            	}
            	else{
            		cond+= " and sourcefrom in('1','2')";//第三方用户
            	}
			}
			
			sql = sql+cond;
			
			//排序列名
			if(StringUtils.isNotBlank(search.getField08())){
				sql=sql+" order by "+search.getField08().trim();
			}
			else{
				sql=sql+" order by registerTime";
			}
			
			//排序方式
			if(StringUtils.isNotBlank(search.getField07())){
				if(search.getField07().equals("ASC")){
					sql=sql+" ASC";
				}
                if(search.getField07().equals("DESC")){
                	sql=sql+" DESC";
				}
			}
			else{
				sql=sql+" desc";
			}
		}
		else{
			//默认根据registerTime字段排序
			sql=sql+" order by registerTime desc";
		}
		
		tableDao.setPageSize(pageSize);
		list = tableDao.queryByPage(PassengerInfoEntity.class, sql,p,conditions.toArray());
		
		if(null != list && list.size()>0){
	//		//获取账户余额
			for (Object o : list) {
				PassengerInfoEntity passengerInfo=(PassengerInfoEntity) o;
				if(StringUtils.isNotBlank(passengerInfo.getIncome())){
					BigDecimal theBalance=new BigDecimal(passengerInfo.getIncome()).subtract(new BigDecimal(passengerInfo.getOutlay()==null?"0":passengerInfo.getOutlay()));
					passengerInfo.setTheBalance(theBalance.toString());
				}
				else{
					passengerInfo.setTheBalance("0.00");
				}
				
				//获取订单数量
				super.finit(" lease_base_info lbi,line_business_order lbo,line_class_info lci ");
				String sql_1 = " SELECT lbo.* " +
						" FROM lease_base_info lbi,line_business_order lbo,line_class_info lci " +
						" WHERE lbi.leaseOrderNo=lbo.leaseOrderId AND lbo.lineClassId = lci.lineClassId AND lbi.ispay = 1 AND " +
						" CONCAT(lci.orderDate,' ',lci.orderStartTime)>NOW() AND lbo.passengerId = '"+passengerInfo.getPassengerId()+"'";
				int result1 = tableDao.queryCount(sql_1);
				passengerInfo.setOrderCount(result1);
			}
		}
		page = new Page(list,sql.replace(anSql, ""),p,pageSize,conditions.toArray());
		map.put("list", list);
		map.put("page", page);
		return map;
	}
	
	/**
	 * 获取用户余额
	 */
	private String getUserBalance(String userId) {
		float in = 0.0f; //充值总额
		float out = 0.0f;//支出总额
		float money = 0.0f;//余额

		//获取充值总额
		super.finit("stat_passenger_recharge");
		String sql_in = "SELECT SUM(moneyLimit) as a1 FROM stat_passenger_recharge WHERE passengerid = '"+userId+"'";
		AppVo_1 recharge = tableDao.queryBean(AppVo_1.class, sql_in);
		if(recharge!=null){
			if(recharge.getA1()!=null){
				in = Float.valueOf(recharge.getA1());
			}
		}
		
		//获取支出总额
		super.finit("stat_passenger_consum_lease");
		String sql_out = "SELECT SUM(leaseLimit) as a1 FROM stat_passenger_consum_lease WHERE passengerid = '"+userId+"'";
		AppVo_1 lease = tableDao.queryBean(AppVo_1.class, sql_out);
		if(lease!=null){
			if(lease.getA1()!=null){
				out = Float.valueOf(lease.getA1());
			}
		}
		   BigDecimal b1 = new BigDecimal(Float.toString(in));
		   BigDecimal b2 = new BigDecimal(Float.toString(out));
		   money = b1.subtract(b2).floatValue(); 
		   return String.valueOf(money);
	}

	
	/**
	 * 修改乘客信息
	 * @param passengerInfo
	 * @return
	 */
	public int updatePassenger(PassengerInfoEntity passengerInfo,String userId) {
		super.finit("passenger_info");
		int flag = tableDao.updateData(passengerInfo,"passengerId");
		String str=null;
		if(flag>0){
			if(passengerInfo.getBlackFlag()==0){
				str="乘客恢复成功";
			}
			else{
				str="乘客拉黑成功";
			}
		}
		else{
			if(passengerInfo.getBlackFlag()==0){
				str="乘客恢复失败";
			}
			else{
				str="乘客拉黑失败";
			}
		}
		AppendLog(userId,"乘客管理",str);
		return flag;
	}
	
	
	/**
	 * 根据id查询乘客对象
	 * @param id
	 * @return
	 */
	public PassengerInfoEntity getPassengerById(String id) {
		super.finit("passenger_info");
		PassengerInfoEntity passenger = tableDao.queryBeanById(PassengerInfoEntity.class, id,"passengerId");
		//获取账户余额
		passenger.setTheBalance(getUserBalance(passenger.getPassengerId()));
		return passenger;
	}
	
	/**
	 * 查看乘客未乘坐的订单信息
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> passengerOrderList(Search search,int p,int pageSize){
		super.finit("line_base_info lb,passenger_info p,lease_base_info lbi,line_business_order lbo,line_class_info lci");
		sql = "SELECT lbi.leaseOrderTime,lbi.leaseOrderNo,p.nickName,p.telephone,p.passengerId,lb.lineName,lbi.totalCount,lci.orderStartTime,f_getDateByOrderNo(p.passengerId,lci.orderStartTime,lbi.leaseOrderNo) AS orderDate,f_changeIdsToValues(lb.stationInfoes) AS stationName " +
				"FROM line_base_info lb,passenger_info p,lease_base_info lbi,line_business_order lbo,line_class_info lci " +
				"WHERE lb.lineBaseId = lbi.lineBaseId AND p.passengerId = lbi.passengerId AND lbi.leaseOrderNo=lbo.leaseOrderId AND lbo.lineClassId = lci.lineClassId AND lbi.ispay = 1 AND " +
				"CONCAT(lci.orderDate,' ',lci.orderStartTime)<NOW()";
		List<Object> conditions=new ArrayList<Object>();
		if(search!= null){
			String cond = " ";
			//开始日期
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(lbi.leaseOrderTime,10) >= ?";
				conditions.add(search.getField01().trim());
			}
			//结束日期
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(lbi.leaseOrderTime,10) <= ?";
				conditions.add(search.getField02().trim());
			}
			//乘客 ID
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " AND p.passengerId = ?";
				conditions.add(search.getField03().trim());
			}
			sql = sql+cond+" GROUP BY lbi.leaseOrderNo,lci.orderStartTime";
		}
		//默认根据registerTime字段排序
		sql=sql+" order by lbi.leaseOrderTime desc";
		
		tableDao.setPageSize(pageSize);
		list = tableDao.queryByPage(PassengerOrderVo.class, sql,p,conditions.toArray());
		page = new Page(list,sql,p,pageSize,conditions.toArray());
		map.put("list", list);
		map.put("page", page);
		return map; 
		
		
		
	}

	/**查询所有乘客的号码和ID**/
	public List<PassengerInfoEntity> getAllPassengerInfo() {
		super.finit("passenger_info");
		String sql = " select telephone,passengerId from passenger_info ";
		List<PassengerInfoEntity> telephones = tableDao.queryList(PassengerInfoEntity.class, sql);
		return telephones;
	}
	
	/**查询所有乘客的id、城市、电话、注册时间、设备类型、用户来源、性别**/
	public List<PassengerInfoEntity> getAllPassenger() {
		super.finit("passenger_info");
		String sql = " SELECT passengerId,cityCode,telephone,registerTime,terminal,sourcefrom,sex FROM passenger_info WHERE accountStatus = '0' AND blackFlag = '0' ";
		List<PassengerInfoEntity> telephones = tableDao.queryList(PassengerInfoEntity.class, sql);
		return telephones;
	}
	
	/**
	 * 通过电话号码查询乘客信息
	 */
	public PassengerInfo getPassengerId(String telephone,String cityCode){
		super.finit("passenger_info");
		List<Object> paramesters=new ArrayList<Object>();
		paramesters.add(telephone);
		String sql="select * from passenger_info where telephone = ?  ";
		if(!"".equals(cityCode)){
			sql+="  and cityCode = ?  ";
			paramesters.add(cityCode);
		}
		PassengerInfo passengerInfo=super.tableDao.queryBean(PassengerInfo.class, sql, paramesters.toArray());
		return passengerInfo;
	}

	/**更新密码**/
	public void updatePass(PassengerInfo passengerInfo) {
		super.finit("passenger_info");
		tableDao.updateData(passengerInfo, "passengerId");
	}

	/* 重设用户密码
	 */
	public int resetPsPassword(String passengerId,String userId) {
		super.finit("passenger_info");
		int flag=0;
		String sql="select * from passenger_info where passengerId=?";
		PassengerInfo vo = super.tableDao.queryBean(PassengerInfo.class, sql, new Object[]{passengerId});
		if(vo!=null){
			String psw =  Sha1Utils.encrypt(vo.getTelephone()+"&"+"123456");
			vo.setPassWord(psw);
			flag = super.tableDao.updateData(vo, "passengerId");
		}
		if(flag>0){
			AppendLog(userId, "重置用户密码", "重置用户密码成功");
		}else{
			AppendLog(userId, "重置用户密码", "重置用户密码失败");
		}
		return flag;
	}
	
	public int checkPassengerPhone(String telephone){
		super.finit("passenger_info");
		int flag = -99;
		String sql = "select * from passenger_info where telephone = ? ";
		flag = super.tableDao.queryCount(sql, new Object[]{telephone});
		return flag;
		
	}
}
