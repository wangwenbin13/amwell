package com.amwell.vo.gf;

import java.util.List;

import com.amwell.entity.base.BaseEntity;

public class GiftContainerVo extends BaseEntity {

	private static final long serialVersionUID = -2588655978211327333L;

	private int pageSize;

	private int totalCount;

	private int currPage;

	private List<GiftVo> resultData;

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

	public List<GiftVo> getResultData() {
		return resultData;
	}

	public void setResultData(List<GiftVo> resultData) {
		this.resultData = resultData;
	}

}
