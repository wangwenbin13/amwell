package com.amwell.vo.gf;

import java.util.List;

import com.amwell.entity.base.BaseEntity;

public class CouponContainerVo extends BaseEntity {

	private static final long serialVersionUID = 642187790543919871L;

	private int pageSize;

	private int totalCount;

	private int currPage;

	private List<CouponVo> resultData;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<CouponVo> getResultData() {
		return resultData;
	}

	public void setResultData(List<CouponVo> resultData) {
		this.resultData = resultData;
	}

}
