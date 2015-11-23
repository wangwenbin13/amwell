package com.amwell.commons;

import java.math.BigDecimal;


/**
 * 数字处理类
 * @author zhangqiang
 *
 */
public class NumberUtil {
	/**
	 * 获得精度下的对象(四舍五入)
	 * @param bd
	 * @param scale
	 * @return
	 */
	public static BigDecimal getScaleValue(BigDecimal bd,int scale) {
		if(bd==null) {
			bd = new BigDecimal("0") ;
		}
		return bd.setScale(scale, BigDecimal.ROUND_HALF_UP) ;
	}
	
	/**
	 * 获得精度下的对象(四舍五入)
	 * 默认两位小数
	 * @param bd
	 * @return
	 */
	public static BigDecimal getScaleValue(Object bd) {
		BigDecimal b = null ;
		if(bd!=null) {
			if(bd instanceof BigDecimal) {
				b = (BigDecimal) bd ;
			}else {
				try {
					b = new BigDecimal(bd+"");
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		return getScaleValue(b,2);
	}
	
}
