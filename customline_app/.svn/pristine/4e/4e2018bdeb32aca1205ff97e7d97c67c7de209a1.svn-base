package com.pig84.ab.vo.bean;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

/**
 * app实体bean
 * @author Administrator
 *
 */
public class AppVo3_Comp implements Serializable,Comparator<AppVo_14> {

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(AppVo_14 o1, AppVo_14 o2) {
		if(null!=o1 && !StringUtils.isEmpty(o1.getA3()) && null!=o2 && !StringUtils.isEmpty(o2.getA3())){
			String[] a1 = o1.getA3().split(":");  //08:30
			String[] a2 = o2.getA3().split(":");  //17:20
			if(Integer.parseInt(a1[0])>Integer.parseInt(a2[0])){
				return 1;
			}else if(Integer.parseInt(a1[0])<Integer.parseInt(a2[0])){
				return -1;
			}else{
				if(Integer.parseInt(a1[1])>Integer.parseInt(a2[1])){
					return 1;
				}else if(Integer.parseInt(a1[1])<Integer.parseInt(a2[1])){
					return -1;
				}else{
					return 0;
				}
			}
			
		}
		return 0;
	}

	
}
