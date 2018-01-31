package org.pillow.common.response;

import java.util.List;

public class PageResponse<T> extends ListResponse<T>{
	
	//总页数
	private long totalPageNum;
	//总元素数
	private long totalCount;

	public PageResponse(long totalCount, long totalPageNum, List<T> data) {
		super(data);
		// TODO Auto-generated constructor stub
		this.totalCount = totalCount;
		this.totalPageNum = totalPageNum;
	}
	
	public long getTotalPageNum() {
		return totalPageNum;
	}
	public void setTotalPageNum(long totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
