package org.pillow.common.response;

public class BaseResponse<T> {

	private Integer status;
	private T data;
	
	public BaseResponse(Integer status, T data) {
		super();
		this.status = status;
		this.data = data;
	}
	
	public BaseResponse(T data) {
		super();
		this.status = 0; //默认为0
		this.data = data;
	}
	
	public BaseResponse() {
		this.status = 0;
		this.data = null;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
