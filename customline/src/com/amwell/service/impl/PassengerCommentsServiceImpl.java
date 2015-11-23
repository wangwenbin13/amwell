package com.amwell.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amwell.dao.IPassengerCommentsDao;
import com.amwell.entity.Search;
import com.amwell.service.IPassengerCommentsService;
import com.amwell.vo.PassengerCommentsEntity;

/**
 * 乘客评论信息service实现类
 * @author 龚雪婷
 *
 */
@Service("passengerCommentsService")
public class PassengerCommentsServiceImpl implements IPassengerCommentsService {

	@Autowired
	private IPassengerCommentsDao dao;
	
	/**
	 * 分页查询乘客评论列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> listByPage(Search search,int currentPage, int pageSize){
		return dao.listByPage(search, currentPage, pageSize);
	}
	
	/**
	 * 根据id查询乘客评论对象
	 * @param id
	 * @return
	 */
	public PassengerCommentsEntity getPassengerCommentsById(String id){
		return dao.getPassengerCommentsById(id);
	}
	
	/**
	 * 修改乘客评论信息
	 * @param passengerInfo
	 * @return
	 */
	public int updatePassengerComment(PassengerCommentsEntity comment,String userId){
		return dao.updatePassengerComment(comment, userId);
	}
}
