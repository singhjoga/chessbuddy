package com.technovator.api.common.exception;

public class TechnicalException extends RuntimeException{
	private static final long serialVersionUID = 3763442425924813764L;
	
	private String errorCode;
	public TechnicalException(String message, Throwable e, String errorCode) {
		super(message,e);
		this.errorCode=errorCode;
	}

	public TechnicalException(String message, Throwable e) {
		this(message, e, null);
	}

	public TechnicalException(String message, String errorCode) {
		this(message,null,errorCode);
	}	
	public TechnicalException(String message) {
		this(message, null, null);
	}

	public TechnicalException(Throwable cause) {
		this(null,cause,null);
	}
	public String getErrorCode() {
		return errorCode;
	}
	
}
