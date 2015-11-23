package com.amwell.entity;

import java.util.ArrayList;
import java.util.List;

import com.amwell.base.BaseDao;
import com.amwell.commons.StringUtil;

@SuppressWarnings("all")
public class Page {
	private int pageSize;
	private int totalCount;
	private int currentPage;
	private int startIndex;
	private int[] indexes = new int[0];
	private int nextIndex;
	private int previousIndex;
	private int pageCount;
	private List items;
	private int lastIndex;
	private String currentUrl;
	private List pageList;
	private BaseDao dao = new BaseDao("");

	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	public List getPageList() {
		return pageList;
	}

	public void setPageList(List pageList) {
		this.pageList = pageList;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}
	
	public Page(){
		
	}

	public Page(List items, String sql, int startIndex, int pageSize) {
		int totalCount = 0;
		if(!"".equals(sql)){
			totalCount = dao.queryCount(sql);
		}
		
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
		initPage();
	}
	
	public Page(Object args[], String sql, int startIndex, int pageSize) {
		int totalCount = 0;
		if(!"".equals(sql)){
			totalCount = dao.queryCount(sql);
		}
		
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
		initPage();
	}
	
	
	public Page(List items, String sql, int startIndex) {
		int totalCount = 0;
		if(!"".equals(sql)){
			if(items != null && items.size()>0){
				totalCount = dao.queryCount(sql);
			}
			setPageSize(dao.getPageSize());
		}
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
		initPage();
	}
	
	public Page(List items, String sql, int startIndex, int pageSize, Object... params) {
		int totalCount = 0;
		if(!"".equals(sql)){
			if(items != null && items.size()>0){
				totalCount = dao.queryCount(sql,params);
			}
			setPageSize(pageSize);
		}
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
		initPage();
	}
	
	/**固定十页专用分页
	 * @param items
	 * @param sql
	 * @param startIndex
	 * @param pageSize
	 * @param params
	 */
	public Page(int totalCount, int startIndex, int pageSize) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
//		setItems(items);
		setStartIndex(startIndex);
		initPage();
	}
	
	public Page(List items, String sql, int startIndex, Object... params) {
		int totalCount = 0;
		if(!"".equals(sql)){
			if(items != null && items.size()>0){
				totalCount = dao.queryCount(sql,params);
			}
			setPageSize(dao.getPageSize());
		}
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
		initPage();
	}
	
	/**
	 * 初始化数字分页
	 * 
	 */
	protected void initPage(){
		pageList = new ArrayList<Formbean>();
		//翻页页码
		int intAllpage = getPageCount();
		int thisPage = getCurrentPage();
		int addPagei = 0;
		int startPagei = 0;
		if(thisPage-5 < 0){
			addPagei = 10 - thisPage;
		}else{
			addPagei = 5;
		}
		if(intAllpage-thisPage < 5 ){
			startPagei = 9-intAllpage+thisPage;
		}else{
			startPagei = 4;
		}
		for(int i = startPagei ; i > 0 ; i--){
			if((thisPage-i) > 0){
				int p = (thisPage-i-1)*pageSize<0?0:(thisPage-i-1)*pageSize;
				Formbean pageForm = new Formbean();
				pageForm.setField01(thisPage-i+"");
				pageForm.setField02("0");
				pageForm.setField03(StringUtil.objectToString(p));
				pageList.add(pageForm);
			}
		}
		for(int i = 0 ; i <= addPagei ; i++){
			int currentPage = thisPage+i;
			int p = (currentPage-1)*pageSize<0?0:(currentPage-1)*pageSize;
			if(currentPage <= intAllpage){
				Formbean pageForm = new Formbean();
				pageForm.setField01(currentPage+"");
				if(i==0){
					pageForm.setField02("1");
					pageForm.setField03(StringUtil.objectToString(p));
				}else{
					pageForm.setField02("0");
					pageForm.setField03(StringUtil.objectToString(p));
				}
				pageList.add(pageForm);
			}
		}
	}
	
	
	
	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = totalCount / pageSize;
			if (totalCount % pageSize > 0) {
				count++;
			}
			indexes = new int[count];
			for (int i = 0; i < count; i++) {
				indexes[i] = pageSize * i;
			}
		} else {
			this.totalCount = 0;
		}
	}

	/**
	 * 得到总记录数
	 * 
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
	}

	/**
	 * 得到分页索引的数组
	 * 
	 * @return
	 */
	public int[] getIndexes() {
		return indexes;
	}

	public void setStartIndex(int startIndex) {
		if (totalCount <= 0) {
			this.startIndex = 0;
		} else if (startIndex >= totalCount) {
			this.startIndex = indexes[indexes.length - 1];
		} else if (startIndex < 0) {
			this.startIndex = 0;
		} else {
			this.startIndex = indexes[startIndex / pageSize];
		}
	}

	/**
	 * 当前页
	 * 
	 * @return
	 */
	public int getStartIndex() {
		return startIndex;
	}

	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}

	/**
	 * 下一页
	 * 
	 * @return
	 */
	public int getNextIndex() {
		int nextIndex = getStartIndex() + pageSize;
		if (nextIndex >= totalCount) {
			return getStartIndex();
		} else {
			return nextIndex;
		}
	}

	public void setPreviousIndex(int previousIndex) {
		this.previousIndex = previousIndex;
	}

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public int getPreviousIndex() {
		int previousIndex = getStartIndex() - pageSize;
		if (previousIndex < 0) {
			return 0;
		} else {
			return previousIndex;
		}
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		int count = totalCount / pageSize;
		if (totalCount % pageSize > 0)
			count++;
		return count;
	}

	public int getCurrentPage() {
		return getStartIndex() / pageSize + 1;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getLastIndex() {
		if (indexes.length == 0) {
			return 0;
		} else {
			return indexes[indexes.length - 1];
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 得到已分页好的结果集
	 * 
	 * @return
	 */
	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
}
