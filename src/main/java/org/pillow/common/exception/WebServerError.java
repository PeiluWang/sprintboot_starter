package org.pillow.common.exception;

public enum WebServerError {

	/**
	 * 用户输入类错误
	 * @author peilu.wang
	 *
	 */
	SUCCESS("成功"),
	API_NOT_FOUND("不存在的API"), 
	USERNAME_OR_PASSWORD_INCORRECT("用户名或密码不正确"), 
	INVALID_INPUT("不合法的输入"), 

	
	/**
	 * DBUtil调用错误
	 */
	DBUTIL_INVALID_INPUT("DBUtil输入参数不合法"),
	
	
	/**
	 * 其它错误
	 */
	OTHER("其它错误");
	
	// 错误说明
	private String errorDesc;

	WebServerError(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
	@Override
	public String toString() {
		return this.name() +"("+this.errorDesc+")";
	}
	
}
