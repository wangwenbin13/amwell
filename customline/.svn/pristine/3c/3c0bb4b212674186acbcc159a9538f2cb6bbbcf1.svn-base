package com.amwell.dao.coupon.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.dao.coupon.ICouponGroupDao;
import com.amwell.dao.coupon.ICouponInfoDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.coupon.CouponGroup;
import com.amwell.vo.coupon.CouponGroupPassengerDetail;
import com.amwell.vo.coupon.CouponInfo;
import com.amwell.vo.coupon.CouponStatisticsVo;

/**
 * 组合券信息
 * @author gxt
 *
 */
@Repository(value="couponGroupDaoImpl")
public class CouponGroupDaoImpl extends DaoSupport implements ICouponGroupDao {

	@Autowired
	private ICouponInfoDao couponDao;
	
	/**
	 * 查询最大id
	 * @return
	 */
	@Override
	public String getMaxId(){
		super.finit("coupon_group");
		String sql="SELECT MAX(id) as id FROM coupon_group";
		CouponGroup cg=tableDao.queryBean(CouponGroup.class, sql);
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
	public int updateCouponGroup(CouponGroup couponGroup){
		if(null!=couponGroup){
			//保存组合券信息
			super.finit("coupon_group");
			if(StringUtils.isBlank(couponGroup.getId())){//添加
				
				CouponGroup cg=getCouponGroupById(couponGroup.getCouponGroupID());
				if(null!=cg){//批次号已存在
					return -1;
				}
				
				String sql="INSERT INTO coupon_group (couponGroupName, couponGroupCount, createBy, createOn,couponGroupID,averageNum) VALUES(?,?,?,?,?,?)";
				args=new Object[6];
				args[0]=couponGroup.getCouponGroupName();
				args[1]=couponGroup.getCouponGroupCount();
				args[2]=couponGroup.getCreateBy();
				args[3]=couponGroup.getCreateOn();
				args[4]=couponGroup.getCouponGroupID();
				args[5]=couponGroup.getAverageNum();
				return tableDao.executeSQL(sql, args);
			}
			else{//修改
				return tableDao.updateData(couponGroup,"id");
			}
		}
		return 0;
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
		super.finit("coupon_group");
		StringBuffer sql=new StringBuffer(" SELECT cg.*,sya.userName FROM coupon_group cg LEFT JOIN sys_admin sya ON cg.createBy = sya.userId ");
		List<String> condition=new ArrayList<String>();
		if(null!=search){
			StringBuffer sb=new StringBuffer();
			int tempNum=0;
			//组合券批次号
			if(StringUtils.isNotBlank(search.getField01())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" cg.couponGroupID LIKE ? ");
				condition.add("%"+search.getField01().trim()+"%");
				tempNum++;
			}
			//优惠券有效期
			if(StringUtils.isNotBlank(search.getField02())||StringUtils.isNotBlank(search.getField03())){
				List<CouponInfo> couponList=couponDao.getCouponGroup(null,null,search.getField02(),search.getField03());
				if(null!=couponList&&couponList.size()>0){
					StringBuffer temp=new StringBuffer();
					for (CouponInfo couponInfo : couponList) {
						condition.add(couponInfo.getCouponGroupId()+"");
						temp.append("?,");
					}
					if(tempNum>0){
						sb.append(" and ");
					}
					sb.append(" cg.couponGroupID in ("+temp.substring(0,temp.length()-1)+") ");
					tempNum++;
				}
			}
			//优惠券名
			if(StringUtils.isNotBlank(search.getField05())){
				List<CouponInfo> couponList=couponDao.getCouponGroup(null,search.getField05(),null,null);
				if(null!=couponList&&couponList.size()>0){
					StringBuffer temp=new StringBuffer();
					for (CouponInfo couponInfo : couponList) {
						condition.add(couponInfo.getCouponGroupId()+"");
						temp.append("?,");
					}
					if(tempNum>0){
						sb.append(" and ");
					}
					sb.append(" cg.couponGroupID in ("+temp.substring(0,temp.length()-1)+") ");
					tempNum++;
				}
			}
			//配置人名
			if(StringUtils.isNotBlank(search.getField04())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" sya.userName LIKE ? ");
				condition.add("%"+search.getField04().trim()+"%");
				tempNum++;
			}
			
			if(tempNum>0){
				sql.append(" WHERE ").append(sb);
			}
		}
		sql.append(" order by cg.createOn desc ");
		
		List<CouponGroup> list=null;
		if(pageSize>0){//分页查询
			tableDao.setPageSize(pageSize);
			list = tableDao.queryByPage(CouponGroup.class, sql.toString(),currentPage,condition.toArray());
			page = new Page(list,sql.toString(),currentPage,pageSize,condition.toArray());
			map.put("page", page);
		}
		else{//不分页查询
			list = tableDao.queryList(CouponGroup.class, sql.toString(), condition.toArray());
		}
		
		if(null!=list&&list.size()>0){
			//查询所有优惠券
			Map<String,Object> m=couponDao.getListByCondition(null,0,0);
			List<CouponInfo> couponList=(List<CouponInfo>) m.get("list");
			List<CouponInfo> result=null;
			for (CouponGroup couponGroup : list) {
				result=new ArrayList<CouponInfo>();
				for (CouponInfo couponInfo : couponList) {
					if(couponGroup.getCouponGroupID().equals(couponInfo.getCouponGroupId())){
						result.add(couponInfo);
					}
				}
				couponGroup.setCouponList(result);
			}
		}
		map.put("list", list);
		return map;
	}
	
	/**
	 * 根据组合券名称查询组合券信息
	 * @param couponGroupName
	 * @return
	 */
    public List<CouponGroup> getCouponGroup(String couponGroupName,String condition){
    	super.finit("coupon_group");
		StringBuffer sql=new StringBuffer(" SELECT id,couponGroupName FROM coupon_group WHERE couponGroupName ");
		args=new Object[1];
		if("like".equals(condition)){
			sql.append(" LIKE ? ");
			args[0]="%"+couponGroupName+"%";
		}
		if("=".equals(condition)){
			sql.append(" = ? ");
			args[0]=couponGroupName;
		}
		return tableDao.queryList(CouponGroup.class, sql.toString(), args);
    }
    
    /**
	 * 查询组合券批次号
	 * @return
	 */
	public String getCouponGroupID(){
		super.finit("coupon_group");
		String sql="SELECT MAX(couponGroupID) AS a1 FROM coupon_group";
		AppVo_1 vo=tableDao.queryBean(AppVo_1.class, sql);
		if(null!=vo&&StringUtils.isNotBlank(vo.getA1())){
			if(Integer.parseInt(vo.getA1())>0){
				return vo.getA1();
			}
		}
		return null;
	}
	
	/**
	 * 根据发放id查询优惠券信息
	 * @param couponGroupGrantId
	 * @return
	 */
	public CouponGroup getCouponGroup(String couponGroupGrantId){
		super.finit("coupon_group_grant");
		String sql="SELECT cg.couponGroupID,cg.couponGroupCount,cg.averageNum FROM coupon_group_grant cgg,coupon_group cg WHERE cgg.couponGroupId = cg.couponGroupID AND cgg.id = ?";
		args=new Object[1];
		args[0]=couponGroupGrantId;
		CouponGroup cg=tableDao.queryBean(CouponGroup.class, sql, args);
		if(null!=cg){
			//根据优惠券批次号查询优惠券
			super.finit("coupon_info");
			sql="SELECT * FROM coupon_info WHERE couponGroupId = ?";
			args=new Object[1];
			args[0]=cg.getCouponGroupID();
			List<CouponInfo> list=tableDao.queryList(CouponInfo.class, sql, args);
			cg.setCouponList(list);
		}
		return cg;
	}
	
	/**
	 * 根据组合券id查询组合券信息
	 * @param couponGroupId
	 * @return
	 */
	public CouponGroup getCouponGroupById(String couponGroupId){
		super.finit("coupon_group");
		String sql="SELECT couponGroupID,couponGroupName,couponGroupCount,averageNum FROM coupon_group WHERE couponGroupID = ?";
		args=new Object[1];
		args[0]=couponGroupId;
		return tableDao.queryBean(CouponGroup.class, sql, args);
	}
	
	/**
	 * 多条件查询优惠券统计列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getCouponStatistics(Search search,int currentPage, int pageSize){
		super.finit("coupon_info");
		StringBuffer sql=new StringBuffer(" SELECT couponGroupId,id as couponId,couponName,couponValue,couponCon,num,effectiveTime,expirationTime,delayDays,couponCount," +
				"couponValue*couponCount AS couponTotalValue FROM coupon_info ");
		List<String> condition=new ArrayList<String>();
		if(null!=search){
			StringBuffer sb=new StringBuffer();
			int tempNum=0;
			//组合券批次号
			if(StringUtils.isNotBlank(search.getField01())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" couponGroupId LIKE ? ");
				condition.add("%"+search.getField01().trim()+"%");
				tempNum++;
			}
			//优惠券名
			if(StringUtils.isNotBlank(search.getField02())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" couponName LIKE ? ");
				condition.add("%"+search.getField02().trim()+"%");
				tempNum++;
			}
			if(tempNum>0){
				sql.append(" WHERE ").append(sb);
			}
		}
		sql.append(" ORDER BY createOn DESC ");
		
		List<CouponStatisticsVo> list=null;
		if(pageSize>0){//分页查询
			tableDao.setPageSize(pageSize);
			list = tableDao.queryByPage(CouponStatisticsVo.class, sql.toString(),currentPage,condition.toArray());
			page = new Page(list,sql.toString(),currentPage,pageSize,condition.toArray());
			map.put("page", page);
		}
		else{//不分页查询
			list = tableDao.queryList(CouponStatisticsVo.class, sql.toString(), condition.toArray());
		}
		
		if(null!=list&&list.size()>0){
			List<CouponGroup> couponGroupList=new ArrayList<CouponGroup>();
			CouponGroup cg=null;
			//查询优惠券发放信息
			List<String> couponGroupIdList=new ArrayList<String>();
			StringBuffer sb=new StringBuffer();
			List<String> strs=new ArrayList<String>();
			for (CouponStatisticsVo vo : list) {
				String id=vo.getCouponGroupId();
				if(!couponGroupIdList.contains(id)){//控制不出现重复id
					sb.append("?,");
					strs.add(id);
					couponGroupIdList.add(id);
				}
			}
			
			strs.addAll(couponGroupIdList);
			
			super.finit("coupon_group_passenger_detail");
			sql=new StringBuffer(
					" SELECT couponGroupId,couponId,COUNT(id) AS passengerId,'used' AS useState FROM coupon_group_passenger_detail WHERE couponGroupId IN ("+sb.substring(0,sb.length()-1)+") AND useState = 'used' GROUP BY couponGroupId,couponId "
					+ "UNION "
					+ "SELECT couponGroupId,couponId,COUNT(id) AS passengerId,'all' AS useState FROM coupon_group_passenger_detail WHERE couponGroupId IN ("+sb.substring(0,sb.length()-1)+") GROUP BY couponGroupId,couponId ");
			List<CouponGroupPassengerDetail> detailList=tableDao.queryList(CouponGroupPassengerDetail.class, sql.toString(), strs.toArray());
			
			if(null!=detailList&&detailList.size()>0){
				String couponGroupId = null;//批次号
				String couponId = null;//优惠券id
				int couponSendCount = 0;//优惠券发放数量
				int couponUsedCount = 0;//优惠券使用数量
				
				for (CouponStatisticsVo vo : list) {
					couponGroupId=vo.getCouponGroupId();
					couponId=vo.getCouponId();
					couponSendCount=0;
					couponUsedCount=0;
					for (CouponGroupPassengerDetail detail : detailList) {
						if(couponGroupId.equals(detail.getCouponGroupId())&&couponId.equals(detail.getCouponId())){
							if(detail.getUseState().equals("all")){
								couponSendCount=Integer.parseInt(detail.getPassengerId());
							}
							if(detail.getUseState().equals("used")){
								couponUsedCount=Integer.parseInt(detail.getPassengerId());
							}
						}
					}
					vo.setCouponSendCount(couponSendCount+"");
					vo.setCouponUsedCount(couponUsedCount+"");
					vo.setCouponSendValue(new BigDecimal(vo.getCouponValue()).multiply(new BigDecimal(couponSendCount)).toString());
					vo.setCouponUsedValue(new BigDecimal(vo.getCouponValue()).multiply(new BigDecimal(couponUsedCount)).toString());
					if(couponSendCount==0){
						vo.setCouponUsedProbability("0.0000%");
					}
					else{
						BigDecimal bd=BigDecimal.valueOf(couponUsedCount).divide(BigDecimal.valueOf(couponSendCount),4,BigDecimal.ROUND_HALF_EVEN);
						vo.setCouponUsedProbability(bd.multiply(BigDecimal.valueOf(100))+"%");
					}
				}
			}
			else{
				for (CouponStatisticsVo vo : list) {
					vo.setCouponSendCount("0");
					vo.setCouponUsedCount("0");
					vo.setCouponSendValue("0.00");
					vo.setCouponUsedValue("0.00");
					vo.setCouponUsedProbability("0.0000%");
				}
			}
			
			//重新封装信息(根据批次号进行分组显示)
			List<CouponStatisticsVo> temp=null;
			for (String string : couponGroupIdList) {
				cg=new CouponGroup();
				cg.setCouponGroupID(string);
				temp=new ArrayList<CouponStatisticsVo>();
				for (CouponStatisticsVo vo : list) {
					if(string.equals(vo.getCouponGroupId())){
						temp.add(vo);
					}
				}
				cg.setStatisticsList(temp);
				couponGroupList.add(cg);
			}
			
			map.put("list", couponGroupList);
		}
		return map;
	}
	
	public static void main(String[] args) {
		int couponSendCount = 200;
		int couponUsedCount = 13;
		BigDecimal bd=BigDecimal.valueOf(couponUsedCount).divide(BigDecimal.valueOf(couponSendCount),4,BigDecimal.ROUND_HALF_EVEN);
		System.out.println(bd.multiply(BigDecimal.valueOf(100))+"%");
	}
}
