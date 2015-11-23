package com.service.games;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.games.Anniversary;
import com.mapper.games.AnniversaryMapper;
import com.service.v1.UserService;
import com.util.common.SendMessageUtil;

import net.sf.json.JSONObject;

/**
 *  /\_/\
 * (=+_+=)
 *   ( ).
 * @author sunjiaxiang
 * 貌似这是一个提供各种周年有关数据获取处理的类哟。
 */
@Service
@Transactional
public class AnniversaryService {
	
	@Autowired
	private AnniversaryMapper anniversaryMapper;
	
	@Autowired
	private UserService userService;
	
	/** 检测用户是否购买过票； */
	public boolean checkBuy(String uid) {
		Anniversary anniversary = anniversaryMapper.getByUid(uid);
		return (anniversary != null && !StringUtils.isEmpty(anniversary.getTel()));
	}
	
	/** 购买成功； */
	public void success(String uid) {
		if (checkBuy(uid)) {
			return;
		}
		JSONObject json = userService.getByPassengerId(uid);
		Anniversary anniversary = new Anniversary();
		anniversary.setCreateTime(new Date().getTime());
		anniversary.setUid(uid);
		anniversary.setTel(json.getString("a6"));
		anniversaryMapper.add(anniversary);
		StringBuilder msg = new StringBuilder();
		msg.append("手机号码为：").append(anniversary.getTel()).append("的用户购买了车票。");
		SendMessageUtil.sendMessage("13510948006", msg.toString());
	}
	
	/** 活动是否还继续； */
	public boolean isAcitvity() {
		return anniversaryMapper.count() < 300;
	}
}
