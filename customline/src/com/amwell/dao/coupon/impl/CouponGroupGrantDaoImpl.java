package com.amwell.dao.coupon.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.dao.coupon.ICouponGroupGrantDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.coupon.CouponGroup;
import com.amwell.vo.coupon.CouponGroupGrant;
import com.amwell.vo.coupon.CouponGroupPassengerDetail;

/**
 * 组合券发放信息
 * @author gxt
 *
 */
@Repository(value="couponGroupGrantDaoImpl")
public class CouponGroupGrantDaoImpl extends DaoSupport implements ICouponGroupGrantDao {
	
	/**
	 * 查询最大id
	 * @return
	 */
	@Override
	public String getMaxId(){
		super.finit("coupon_group_grant");
		String sql="SELECT MAX(id) as id FROM coupon_group_grant";
		CouponGroupGrant cg=tableDao.queryBean(CouponGroupGrant.class, sql);
		if(null!=cg){
			return cg.getId();
		}
		return null;
	}
	
	/**
	 * 保存或修改组合券
	 * @param coupon
	 * @return
	 */
	@Override
	public int updateCouponGroupGrant(CouponGroupGrant couponGroupGrant){
		if(null!=couponGroupGrant){
			//保存组合券信息
			super.finit("coupon_group_grant");
			if(StringUtils.isBlank(couponGroupGrant.getId())){//添加
				String sql="INSERT INTO coupon_group_grant (couponGroupId,couponGroupName,sendCouponType,startTime,endTime,createBy,createOn,taskName) VALUES(?,?,?,?,?,?,?,?)";
				args=new Object[8];
				args[0]=couponGroupGrant.getCouponGroupId();
				args[1]=couponGroupGrant.getCouponGroupName();
				args[2]=couponGroupGrant.getSendCouponType();
				args[3]=couponGroupGrant.getStartTime();
				args[4]=couponGroupGrant.getEndTime();
				args[5]=couponGroupGrant.getCreateBy();
				args[6]=couponGroupGrant.getCreateOn();
				args[7]=couponGroupGrant.getTaskName();
				return tableDao.executeSQL(sql, args);
			}
			else{//修改
				return tableDao.updateData(couponGroupGrant,"id");
			}
		}
		return 0;
	}
	
	/**
	 * 保存或修改组合券发放(带事物处理)
	 * @param coupon
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int updateCouponGroupGrantT(CouponGroupGrant couponGroupGrant){
		
        if(null==couponGroupGrant){
        	return 0;//添加失败
		}
		
        Connection conn = MyDataSource.getConnect();
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			//添加或修改时判断批次号是否存在
			String str="SELECT couponGroupID,couponGroupName,couponGroupCount,averageNum FROM coupon_group WHERE couponGroupID = ?";
			args=new Object[1];
			args[0]=couponGroupGrant.getCouponGroupId();
			CouponGroup cg=qr.query(conn,str,args,new BeanHandler<CouponGroup>(CouponGroup.class));
			if(null==cg||StringUtils.isBlank(cg.getCouponGroupID())){
				return -2;//批次号不存在
			}
			couponGroupGrant.setCouponGroupName(cg.getCouponGroupName());
			
			String couponGroupGrantId = couponGroupGrant.getId();
			//修改时，先将原来的规则信息删除
			if(StringUtils.isNotBlank(couponGroupGrantId)){
				String sql="delete FROM coupon_rule WHERE couponGroupGrantId = ?";
				args=new Object[1];
				args[0]=couponGroupGrantId;
				qr.update(conn,sql, args);
			}
			
			boolean flagT=false;//标识是否是添加操作
			if(StringUtils.isBlank(couponGroupGrantId)){
				flagT=true;
				//查询最新的发放id
				String sql="SELECT MAX(id) as id FROM coupon_group_grant";
				CouponGroupGrant cgg=qr.query(conn,sql,new BeanHandler<CouponGroupGrant>(CouponGroupGrant.class));
				couponGroupGrantId = (Integer.parseInt(cgg.getId())+1)+"";
				couponGroupGrant.setId(couponGroupGrantId);
			}
			
			//保存发放信息
			if(flagT){//添加
				String sql="INSERT INTO coupon_group_grant (couponGroupId,couponGroupName,sendCouponType,startTime,endTime,createBy,createOn,taskName,selectPass,modeOfExecution) VALUES(?,?,?,?,?,?,?,?,?,?)";
				args=new Object[10];
				args[0]=couponGroupGrant.getCouponGroupId();
				args[1]=couponGroupGrant.getCouponGroupName();
				args[2]=couponGroupGrant.getSendCouponType();
				args[3]=couponGroupGrant.getStartTime();
				args[4]=couponGroupGrant.getEndTime();
				args[5]=couponGroupGrant.getCreateBy();
				args[6]=couponGroupGrant.getCreateOn();
				args[7]=couponGroupGrant.getTaskName();
				args[8]=couponGroupGrant.getSelectPass();
				args[9]=couponGroupGrant.getModeOfExecution();
				qr.update(conn,sql, args);
			}
			else{//修改
				String sql="update coupon_group_grant set couponGroupId=?,couponGroupName=?,sendCouponType=?,startTime=?,endTime=?,updateBy=?,updateOn=?,taskName=? where id=?";
				args=new Object[9];
				args[0]=couponGroupGrant.getCouponGroupId();
				args[1]=couponGroupGrant.getCouponGroupName();
				args[2]=couponGroupGrant.getSendCouponType();
				args[3]=couponGroupGrant.getStartTime();
				args[4]=couponGroupGrant.getEndTime();
				args[5]=couponGroupGrant.getUpdateBy();
				args[6]=couponGroupGrant.getUpdateOn();
				args[7]=couponGroupGrant.getTaskName();
				args[8]=couponGroupGrant.getId();
				qr.update(conn,sql, args);
			}
			
			//添加规则信息
			saveCouponRule(couponGroupGrant,qr,conn,flagT);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			if(null!=conn){
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return 1;//添加成功
	}
	
	/*保存规则*/
	private int saveCouponRule(CouponGroupGrant couponGroupGrant,QueryRunner qr,Connection conn,boolean flagT) throws SQLException{
		String rules=couponGroupGrant.getRules();
		if(StringUtils.isBlank(rules)){
			return 0;//保存失败
		}
		//格式：规则名1,条件1,条件值1#规则名2,条件2,条件值2
		String[] obj=rules.split("#");
		for (String string : obj) {
			String[] strs=string.split(",");
			String temp=null;
			if(strs[0].equals("telephone")){
				//把手机号码去重
				temp=delRepetition(strs[2], temp);
			}
			else{
				temp=strs[2];
			}
			
			//保存规则信息
			super.finit("coupon_rule");
			String sql="INSERT INTO coupon_rule (ruleName,theCondition,theValue,couponGroupGrantId,createBy,createOn) VALUES(?,?,?,?,?,?)";
			args=new Object[6];
			args[0]=strs[0];
			args[1]=strs[1];
			args[2]=temp;
			args[3]=couponGroupGrant.getId();
			if(flagT){//添加时
				args[4]=couponGroupGrant.getCreateBy();
				args[5]=couponGroupGrant.getCreateOn();
			}
			else{//修改时
				args[4]=couponGroupGrant.getUpdateBy();
				args[5]=couponGroupGrant.getUpdateOn();
			}
			qr.update(conn,sql, args);
		}
		return 1;//保存成功
	}
	
	/*手机号码去重*/
	private String delRepetition(String strs,String temp){
		String[] ss=strs.split(";");
		List<String> l=new ArrayList<String>();
		for (String string2 : ss) {
			if(!l.contains(string2)){
				l.add(string2);
			}
		}
		for (String string3 : l) {
			if(null==temp){
				temp=string3;
			}
			else{
				temp=temp+";"+string3;
			}
		}
		return temp;
	}
	
	
	/**
	 * 根据发放id查询发放对象
	 * @param couponGroupGrantId
	 * @return
	 */
	public CouponGroupGrant getInfoById(String couponGroupGrantId){
		super.finit("coupon_group_grant");
		String sql="SELECT * FROM coupon_group_grant WHERE id = ?";
		args=new Object[1];
		args[0]=couponGroupGrantId;
		return tableDao.queryBean(CouponGroupGrant.class, sql,args);
	}
	
	/**
	 * 多条件查询组合券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getListByCondition(Search search, int currentPage,
			int pageSize) {
		super.finit("coupon_group_grant");
		StringBuffer sql=new StringBuffer(" SELECT cgg.*,sya.userName,sya1.userName as updateUserName FROM coupon_group_grant cgg LEFT JOIN sys_admin sya ON cgg.createBy = sya.userId LEFT JOIN sys_admin sya1 ON cgg.updateBy = sya1.userId ");
		List<String> condition=new ArrayList<String>();
		if(null!=search){
			StringBuffer sb=new StringBuffer();
			int tempNum=0;
			//组合券名称
			if(StringUtils.isNotBlank(search.getField01())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" cgg.couponGroupId LIKE ? ");
				condition.add("%"+search.getField01().trim()+"%");
				tempNum++;
			}
			//发送方式
			if(StringUtils.isNotBlank(search.getField02())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" cgg.sendCouponType = ? ");
				condition.add(search.getField02().trim());
				tempNum++;
			}
			//开始时间
			if(StringUtils.isNotBlank(search.getField03())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" (? BETWEEN cgg.startTime AND cgg.endTime) ");
				condition.add(search.getField03().trim());
				tempNum++;
			}
			//结束时间
			if(StringUtils.isNotBlank(search.getField04())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" (? BETWEEN cgg.startTime AND cgg.endTime) ");
				condition.add(search.getField04().trim());
				tempNum++;
			}
			//执行方式
			if(StringUtils.isNotBlank(search.getField05())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" cgg.modeOfExecution = ? ");
				condition.add(search.getField05().trim());
				tempNum++;
			}
			//用户名
			if(StringUtils.isNotBlank(search.getField06())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" sya.userName LIKE ? ");
				condition.add("%"+search.getField06().trim()+"%");
				tempNum++;
			}
			
			if(tempNum>0){
				sql.append(" WHERE ").append(sb);
			}
		}
		sql.append(" order by cgg.createOn desc ");
		
		if(pageSize>0){//分页查询
			tableDao.setPageSize(pageSize);
			list = tableDao.queryByPage(CouponGroupGrant.class, sql.toString(),currentPage,condition.toArray());
			page = new Page(list,sql.toString(),currentPage,pageSize,condition.toArray());
			map.put("list", list);
			map.put("page", page);
		}
		else{//不分页查询
			list = tableDao.queryList(CouponGroupGrant.class, sql.toString(), condition.toArray());
			map.put("list", list);
		}
		return map;
	}
	
	/**
	 * 根据发放id查询用户发放详情
	 * @param couponGroupGrantId
	 * @return
	 */
	public Map<String, Object> getDetail(String couponGroupGrantId,int currentPage, int pageSize){
		super.finit("coupon_group_passenger_detail");
		String sql="SELECT * FROM coupon_group_passenger_detail WHERE grantId = ?";
		args=new Object[1];
		args[0]=couponGroupGrantId;
		tableDao.setPageSize(pageSize);
		list = tableDao.queryByPage(CouponGroupPassengerDetail.class, sql.toString(),currentPage,args);
		page = new Page(list,sql.toString(),currentPage,pageSize,args);
		map.put("list", list);
		map.put("page", page);
		return map;
	}
}
