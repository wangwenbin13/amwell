package com.service.games.fcz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.games.fcz.FczHelper;
import com.bean.games.fcz.FczOwner;
import com.bean.games.fcz.FczSetting;
import com.mapper.games.fcz.FczHelperMapper;
import com.mapper.games.fcz.FczOwnerMapper;
import com.mapper.games.fcz.FczSettingMapper;
import com.util.common.StringUtil;

/**
 * 复仇者活动
 * 
 * @author shawn.zheng
 * 
 */
@Service
@Transactional
public class FczService {

	/**
	 * 复仇者活动设置
	 */
	@Autowired
	private FczSettingMapper fczSettingMapper;

	/**
	 * 复仇者活动参与者
	 */
	@Autowired
	private FczOwnerMapper fczOwnerMapper;

	/**
	 * 复仇者活动帮忙者
	 */
	@Autowired
	private FczHelperMapper fczHelperMapper;

	public void addSetting(FczSetting fczSetting) {
		fczSetting.setStoreId(StringUtil.generateSequenceNo());
		fczSettingMapper.add(fczSetting);
	}

	public void updateSetting(FczSetting fczSetting) {
		fczSettingMapper.update(fczSetting);
	}

	public void saveSetting(FczSetting fczSetting) {
		if (fczSetting != null) {
			if (StringUtils.isEmpty(fczSetting.getStoreId())) {
				addSetting(fczSetting);
			} else {
				updateSetting(fczSetting);
			}
		}
	}

	public void deleteSetting(FczSetting fczSetting) {
		fczSettingMapper.delete(fczSetting);
	}

	public FczSetting getSetting(FczSetting fczSetting) {
		return fczSettingMapper.get(fczSetting);
	}

	public void addOwner(FczOwner fczOwner) {
		fczOwner.setStoreId(StringUtil.generateSequenceNo());
		fczOwnerMapper.add(fczOwner);
	}

	public void updateOwner(FczOwner fczOwner) {
		fczOwnerMapper.update(fczOwner);
	}

	public void editOwner(FczOwner fczOwner) {
		if (fczOwner != null) {
			if (StringUtils.isEmpty(fczOwner.getStoreId())) {
				addOwner(fczOwner);
			} else {
				updateOwner(fczOwner);
			}
		}
	}

	public FczOwner getOwner(FczOwner fczOwner) {
		return fczOwnerMapper.get(fczOwner);
	}

	public FczOwner getOwnerByOpenId(String openId) {
		FczOwner fczOwner = new FczOwner();
		fczOwner.setOpenId(openId);
		return getOwner(fczOwner);
	}

	public List<FczOwner> listOwner(FczOwner fczOwner) {
		return fczOwnerMapper.list(fczOwner);
	}

	public void addHelper(FczHelper fczHelper) {
		fczHelper.setStoreId(StringUtil.generateSequenceNo());
		fczHelperMapper.add(fczHelper);
	}

	public void updateHelper(FczHelper fczHelper) {
		fczHelperMapper.update(fczHelper);
	}

	public void editHelper(FczHelper fczHelper) {
		if (fczHelper != null) {
			if (StringUtils.isEmpty(fczHelper.getStoreId())) {
				addHelper(fczHelper);
			} else {
				updateHelper(fczHelper);
			}
		}
	}

	public FczHelper getHelper(FczHelper fczHelper) {
		return fczHelperMapper.get(fczHelper);
	}

	public List<FczHelper> listHelper(FczHelper fczHelper) {
		return fczHelperMapper.list(fczHelper);
	}
	
	public int countAllOwner(){
		return fczOwnerMapper.countAll();
	}
	
	public int countOwnerByCondition(int totalPoint){
		Map<String,Integer> params = new HashMap<String,Integer>();
		params.put("totalPoint", totalPoint);
		return fczOwnerMapper.countByCondition(params);
	}

}
