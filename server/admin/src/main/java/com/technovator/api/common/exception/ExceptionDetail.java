package com.technovator.api.common.exception;

public class ExceptionDetail {
	private String message;
	private String errorCode;
	public ExceptionDetail(String message) {
		this(message,null);
	}
	public ExceptionDetail(String message, String errorCode) {
		this.message = message;
		this.errorCode=errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
