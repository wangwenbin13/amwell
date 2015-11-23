package com.service.games;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.games.Impossible;
import com.bean.games.ImpossibleInfo;
import com.mapper.games.ImpossibleInfoMapper;
import com.mapper.games.ImpossibleMapper;

/**
 *  /\_/\
 * (=+_+=)
 *   ( ).
 * @author sunjiaxiang
 * 貌似这是一个提供各种和用户有关数据获取处理的类哟。
 */
@Service
@Transactional
public class ImpossibleService {
	
	@Autowired
	private ImpossibleMapper impossibleMapper;
	
	@Autowired
	private ImpossibleInfoMapper impossibleInfoMapper;
	
	private static Map<String, Integer> infoMap;
	private static long beginTime = 0;
	private static long endTime = 0;
	private static long today = 0;
	
	private final String PRIZED_KEY_1 = "prize1";
	private final String PRIZED_KEY_2 = "prize2";
	private final String PRIZED_KEY_3 = "prize3";
	private final String PRIZED_KEY_4 = "prize4";
	
	/** 每个小时自动刷新剩余奖品数量； */
	public void job() {  
        init();
    }
	
	/** 检测活动是否进行中； */
	public boolean isActivate() {
		if (beginTime == 0 || endTime == 0) {
			init();
		}
		// 检测是否活动过期；
		long curTime = System.currentTimeMillis();
		return curTime > beginTime && curTime < endTime;
	}
	
	/** 检测用户是否参加过活动； */
	public int checkResult(String uid) {
		// 检测是否参加过活动;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", uid);
		map.put("time", today);
		Impossible oldImpossible = impossibleMapper.getByIdAndTime(map);
		if (oldImpossible != null) {
			return oldImpossible.getPrize();
		}
		return -1;
	}
	
	/** 保存刮奖结果； */
	public void saveResult(String uid, String tel, int res) {
		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(tel))
			return;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", uid);
		map.put("time", today);
		Impossible newImpossible = impossibleMapper.getByIdAndTime(map);
		if (newImpossible != null)
			return;
		newImpossible = new Impossible();
		newImpossible.setPrize(res);
		newImpossible.setUid(uid);
		newImpossible.setTel(tel);
		newImpossible.setCreateTime(new Date().getTime());
		impossibleMapper.add(newImpossible);
	}
	
	/** 获取用户刮奖结果，如果参加过返回参加结果（未中奖返回0），如果未参加过返回本次结果（未中奖返回0）； */
	public int getResult() {
		// 检测是否中奖
		int res = (int)(Math.random() * 9999);
		if (res == 0) {
			res = updateResult(PRIZED_KEY_1, 1);
		} else if (res < 5) {
			res = updateResult(PRIZED_KEY_2, 2);
		} else if (res < 9) {
			res = updateResult(PRIZED_KEY_3, 3);
		} else if (res < 30) {
			res = updateResult(PRIZED_KEY_4, 4);
		} else {
			res = 0;
		}
		return res;
	}
	
	/** 从数据库中初始化数据； */
	@SuppressWarnings("deprecation")
	private synchronized void init() {
		ImpossibleInfo info = impossibleInfoMapper.get();
		if (infoMap == null) {
			infoMap = new HashMap<String, Integer>();
		}
		Date day = new Date();
		today = new Date(day.getYear(), day.getMonth(), day.getDate()).getTime();
		infoMap.put(PRIZED_KEY_1, info.getPrize1());
		infoMap.put(PRIZED_KEY_2, info.getPrize2());
		infoMap.put(PRIZED_KEY_3, info.getPrize3());
		infoMap.put(PRIZED_KEY_4, info.getPrize4());
		beginTime = info.getBeginTime();
		endTime = info.getEndTime();
	}
	
	/** 计算结果； */
	private int updateResult(String key, int res) {
		synchronized (infoMap) {
			int count = infoMap.get(key);
			if (count > 0) {
				infoMap.put(key, count - 1);
				return res;
			} else {
				return 0;
			}
		}
	}
}
