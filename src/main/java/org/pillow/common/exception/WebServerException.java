package org.pillow.common.exception;

public class WebServerException extends Exception{

	private WebServerError error; //错误枚举类型
	private String errorDetailMsg; //错误详细信息
	
	public WebServerException(WebServerError error) {
		this.error = error;
		this.errorDetailMsg = "";
	}
	
	public WebServerException(WebServerError error, String errorDetailMsg) {
		this.error = error;
		this.errorDetailMsg = errorDetailMsg;
	}

	@Override
	public String toString(){
		if(errorDetailMsg==null || errorDetailMsg.isEmpty()) {
			return error.toString();
		}
		return error + ":" + errorDetailMsg;
	}
	
	public WebServerError getError() {
		return error;
	}

	public void setError(WebServerError error) {
		this.error = error;
	}

	public String getErrorDetailMsg() {
		return errorDetailMsg;
	}

	public void setErrorDetailMsg(String errorDetailMsg) {
		this.errorDetailMsg = errorDetailMsg;
	}
	
}
