package org.pillow.common.database;

import java.util.List;

/**
 * 分页数据
 */
public class PageData<T> {
	
	long totalElements;
	long pageNum;
	List<T> pageData;
	
	public PageData(long totalElements, long pageNum, List<T> pageData) {
		super();
		this.totalElements = totalElements;
		this.pageNum = pageNum;
		this.pageData = pageData;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public List<T> getPageData() {
		return pageData;
	}
	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	public long getPageNum() {
		return pageNum;
	}
	public void setPageNum(long pageNum) {
		this.pageNum = pageNum;
	}
	
}
