package org.pillow.model.dto;

import org.pillow.common.exception.WebServerError;

public class ServerErrorInfo {

	private String errorName;
	private String errorDesc;
	private String errorDetailMsg;
	
	public ServerErrorInfo(String errorName, String errorDesc, String errorDetailMsg) {
		this.errorName = errorName;
		this.errorDesc = errorDesc;
		this.errorDetailMsg = errorDetailMsg;
	}
	
	public ServerErrorInfo(WebServerError error, String errorDetailMsg) {
		this.errorName = error.name();
		this.errorDesc = error.getErrorDesc();
		this.errorDetailMsg = errorDetailMsg;
	}
	
	public ServerErrorInfo(WebServerError error) {
		this.errorName = error.name();
		this.errorDesc = error.getErrorDesc();
		this.errorDetailMsg = "";
	}
	
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getErrorDetailMsg() {
		return errorDetailMsg;
	}
	public void setErrorDetailMsg(String errorDetailMsg) {
		this.errorDetailMsg = errorDetailMsg;
	}
	
	
}
