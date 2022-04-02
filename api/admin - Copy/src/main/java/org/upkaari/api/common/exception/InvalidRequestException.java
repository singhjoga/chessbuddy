package org.upkaari.api.common.exception;

import org.upkaari.api.common.controllers.CommonResponse.ErrorResponse;

public class InvalidRequestException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private ErrorResponse response;

	public InvalidRequestException(ErrorResponse response) {
		super();
		this.response = response;
	}

	public ErrorResponse getResponse() {
		return response;
	}
	
}
