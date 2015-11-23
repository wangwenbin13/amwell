package com.amwell.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IPassengerInfoDao;
import com.amwell.entity.Search;
import com.amwell.service.IPassengerInfoService;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.app.PassengerInfo;

/**
 * 乘客信息service实现类
 * @author 龚雪婷
 *
 */
@Service("passengerInfoService")
public class PassengerInfoServiceImpl implements IPassengerInfoService {

	@Autowired
	private IPassengerInfoDao dao;
	
	/**
	 * 分页查询乘客列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> listByPage(Search search,int currentPage, int pageSize,String sourcefrom){
		return dao.listByPage(search, currentPage, pageSize,sourcefrom);
	}

	/**
	 * 修改乘客信息
	 * @param passengerInfo
	 * @return
	 */
	public int updatePassenger(PassengerInfoEntity passengerInfo,String userId){
		return dao.updatePassenger(passengerInfo,userId);
	}
	
	/**
	 * 根据id查询乘客对象
	 * @param id
	 * @return
	 */
	public PassengerInfoEntity getPassengerById(String id){
		return dao.getPassengerById(id);
	}
	
	/**
	 * 查看乘客未乘坐的订单信息
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> passengerOrderList(Search search,int currentPage, int pageSize){
		return dao.passengerOrderList(search, currentPage, pageSize);
	}

	/**查询所有乘客的号码和ID**/
	public List<PassengerInfoEntity> getAllPassengerInfo() {
		return dao.getAllPassengerInfo();
	}

	/**更新密码**/
	public void updatePass(PassengerInfo passengerInfo) {
		dao.updatePass(passengerInfo);
	}

	/* 
	 * 验证手机号码用户是否存在
	 */
	public String checktelephone(String telephone,String cityCode) {
		String[] teleCount=telephone.split(";");
		String noPasPhone="";
		String woringNo="";
		Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
		for(String phone : teleCount){
			if("".equals(phone)){
				continue;
			}
			Matcher m = p.matcher(phone);
			if(!m.matches()){
				woringNo+=phone+";";
				continue;
			}
			int flag = dao.checkPassengerPhone(phone);
			if(flag<1){
				noPasPhone+=phone+";";
			}
		}
		return woringNo+"/"+noPasPhone;
	}

	/* 重设用户密码
	 */
	public int resetPsPassword(String passengerId,String userId) {
		return dao.resetPsPassword(passengerId,userId);
	}
}
