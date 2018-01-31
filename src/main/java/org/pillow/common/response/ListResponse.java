package org.pillow.common.response;

import java.util.List;

public class ListResponse<T> extends BaseResponse<List<T>> {

	private int count;

	public ListResponse(List<T> data) {
		super(data);
		this.count = data.size();
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
