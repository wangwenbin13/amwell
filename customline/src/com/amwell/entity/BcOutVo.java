package com.amwell.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author wangwenbin
 *
 * 2014-11-29
 */
/**
 * 包车支出Vo
 */
public class BcOutVo extends BcOrderVo implements Serializable{
	
	/**
	 * 支出金额
	 */
	private BigDecimal bc_out_money;
	
	/**
	 * 支出类型
	 * 1:退票
	 */
	private String bc_out_type;
	
	/**
	 * 支出时间
	 * @return
	 */
	private String operateTime;

	public BigDecimal getBc_out_money() {
		return bc_out_money;
	}

	public void setBc_out_money(BigDecimal bcOutMoney) {
		bc_out_money = bcOutMoney;
	}

	public String getBc_out_type() {
		return bc_out_type;
	}

	public void setBc_out_type(String bcOutType) {
		bc_out_type = bcOutType;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	
}
