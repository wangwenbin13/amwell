package com.amwell.vo;

import java.util.List;

import com.amwell.entity.base.BaseEntity;

public class MerchantVo extends BaseEntity {

	private static final long serialVersionUID = -7254980886626388763L;

	private int pageSize;

	private int totalCount;

	private int currPage;

	private List<MgrBusinessEntity> resultData;

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

	public List<MgrBusinessEntity> getResultData() {
		return resultData;
	}

	public void setResultData(List<MgrBusinessEntity> resultData) {
		this.resultData = resultData;
	}

}
