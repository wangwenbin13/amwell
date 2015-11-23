package com.amwell.vo;

import java.io.Serializable;
import java.util.Comparator;

import com.amwell.vo.app.bean.AppVo_2;

/**
 * app实体bean
 * @author Administrator
 *
 */
public class AppVo_Comp implements Serializable,Comparator<AppVo_2> {

	 /**
     * 如果o1小于o2,返回一个负数;如果o1大于o2，返回一个正数;如果他们相等，则返回0;
     */
	@Override
	public int compare(AppVo_2 o1, AppVo_2 o2) {
		Integer c1 = Integer.valueOf(o1.getA2());
		Integer c2 = Integer.valueOf(o2.getA2());
		if(c1>c2){
			return 1;
		}else if(c1.equals(c2)){
			return 0;
		}else{
			return -1;
		}
	}

	
}
