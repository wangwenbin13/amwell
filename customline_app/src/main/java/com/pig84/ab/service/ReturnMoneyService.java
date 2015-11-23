package com.pig84.ab.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 退款相关
 */
public interface ReturnMoneyService {

	/**财付通退款**/
	public int returnFromCFT(String leaseOrderNo, String realReturn,
			HttpServletRequest request, HttpServletResponse response);

}
