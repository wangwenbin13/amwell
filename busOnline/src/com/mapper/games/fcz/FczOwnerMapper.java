package com.mapper.games.fcz;

import java.util.List;
import java.util.Map;

import com.bean.games.fcz.FczOwner;

public interface FczOwnerMapper {
	public void add(FczOwner fczOwner);
	
	public void delete(FczOwner fczOwner);
	
	public void update(FczOwner fczOwner);
	
	public List<FczOwner> list(FczOwner fczOwner);
	
	public FczOwner get(FczOwner fczOwner);
	
	public int countAll();
	
	public int countByCondition(Map<String,Integer> params);
}
