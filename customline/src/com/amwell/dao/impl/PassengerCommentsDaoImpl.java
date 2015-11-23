package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.dao.IPassengerCommentsDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.PassengerCommentsEntity;
import com.amwell.vo.PassengerCommentsVo;

/**
 * 乘客评论dao实现类
 * @author 龚雪婷
 *
 */
@Repository
public class PassengerCommentsDaoImpl extends DaoSupport implements IPassengerCommentsDao {

	/**
	 * 分页查询乘客评论列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> listByPage(Search search,int p,int pageSize){
//		SELECT 
//		pc.commentId,pc.commentTime,pai.nickName,lbi.lineName,li.leaseOrderNo,pc.commentContext 
//		FROM 
//		passenger_comments pc LEFT JOIN passenger_info pai ON pc.passengerId = pai.passengerId LEFT JOIN line_base_info lbi ON lbi.lineBaseId = pc.lineBaseId  LEFT JOIN lease_base_info li ON li.leaseOrderId = pc.leaseOrderId
//		WHERE pai.nickName = 'nicole1';
		
		super.finit("passenger_comments pc LEFT JOIN passenger_info pai ON pc.passengerId = pai.passengerId LEFT JOIN line_base_info lbi ON lbi.lineBaseId = pc.lineBaseId ");
		sql = "select " +
				"pc.commentTime as commentTime,pai.displayId as displayId,pai.nickName as nickName,lbi.lineName as lineName,pc.leaseOrderId as leaseOrderNo,pc.commentContext as commentContext,pc.commentStatus as commentStatus,pc.commentId as commentId,pc.starPoint,REPLACE(pc.advinces,',','<br/>') AS advinces " +
				"from " +
				"passenger_comments pc LEFT JOIN passenger_info pai ON pc.passengerId = pai.passengerId " +
				"LEFT JOIN line_base_info lbi ON lbi.lineBaseId = pc.lineBaseId  " ;
		List<Object> conditions=new ArrayList<Object>();
		if(search!= null){
			String cond = " where 1 = 1 ";
			//开始日期
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(pc.commentTime,10) >= ?";
				conditions.add(search.getField01().trim());
			}
			//结束日期
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(pc.commentTime,10) <= ?";
				conditions.add(search.getField02().trim());
			}
			//乘客ID或者昵称
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and (pai.displayId like ? or pai.nickName like ?)";
				conditions.add("%"+search.getField03().trim()+"%");
				conditions.add("%"+search.getField03().trim()+"%");
			}
			//线路名称
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and lbi.lineName like ?";
				conditions.add("%"+search.getField04().trim()+"%");
			}
			//订单编号
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and pc.leaseOrderId like ?";
				conditions.add("%"+search.getField05().trim()+"%");
			}
			//评分
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond+= " and pc.starPoint = ?";
				conditions.add(search.getField06().trim());
			}
			
			sql = sql+cond;
			
			//排序列名
			if(StringUtils.isNotBlank(search.getField08())){
				sql=sql+" order by "+search.getField08().trim();
//				conditions.add(search.getField08().trim());
			}
			else{
				sql=sql+" order by commentTime";
			}
			
			//排序方式
			if(StringUtils.isNotBlank(search.getField07())){
				if(search.getField07().equals("ASC")){
					sql=sql+" ASC";
				}
                if(search.getField07().equals("DESC")){
                	sql=sql+" DESC";
				}
//				sql=sql+" ?";
//				conditions.add(search.getField07().trim());
			}
			else{
				sql=sql+" desc";
			}
		}
		else{
			sql = sql+" order by pc.commentTime desc ";
		}

		tableDao.setPageSize(pageSize);
		list = tableDao.queryByPage(PassengerCommentsVo.class, sql,p,conditions.toArray());
		page = new Page(list,sql,p,pageSize,conditions.toArray());
		map.put("list", list);
		map.put("page", page);
		return map;
	}
	
	/**
	 * 根据id查询乘客评论对象
	 * @param id
	 * @return
	 */
	public PassengerCommentsEntity getPassengerCommentsById(String id){
		super.finit("passenger_comments");
		return tableDao.queryBeanById(PassengerCommentsEntity.class, id,"commentId");
	}
	
	/**
	 * 修改乘客评论信息
	 * @param passengerInfo
	 * @return
	 */
	public int updatePassengerComment(PassengerCommentsEntity comment,String userId) {
		super.finit("passenger_comments");
		int flag = tableDao.updateData(comment,"commentId");
		String str=null;
		if(flag>0){
			if(comment.getCommentStatus()==0){
				str="乘客显示成功";
			}
			else{
				str="乘客屏蔽成功";
			}
		}
		else{
			if(comment.getCommentStatus()==0){
				str="乘客显示失败";
			}
			else{
				str="乘客屏蔽失败";
			}
		}
		AppendLog(userId,"乘客管理",str);
		return flag;
	}
}
