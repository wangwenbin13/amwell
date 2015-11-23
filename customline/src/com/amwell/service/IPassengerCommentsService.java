package com.amwell.service;

import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.PassengerCommentsEntity;

/**
 * 乘客评论信息service接口类
 * @author 龚雪婷
 *
 */
public interface IPassengerCommentsService {

	/**
	 * 分页查询乘客评论列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> listByPage(Search search,int currentPage, int pageSize);
	
	/**
	 * 根据id查询乘客评论对象
	 * @param id
	 * @return
	 */
	PassengerCommentsEntity getPassengerCommentsById(String id);
	
	/**
	 * 修改乘客评论信息
	 * @param passengerInfo
	 * @return
	 */
	int updatePassengerComment(PassengerCommentsEntity comment,String userId);
}
