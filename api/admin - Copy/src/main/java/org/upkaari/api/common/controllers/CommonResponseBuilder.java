package org.upkaari.api.common.controllers;

import java.util.List;

import org.upkaari.api.common.controllers.CommonResponse.AddResponse;
import org.upkaari.api.common.controllers.CommonResponse.ErrorResponse;
import org.upkaari.api.common.controllers.CommonResponse.ValidationError;

public class CommonResponseBuilder{
	public static AddResponse addResponse(String id, String location) {
		AddResponse result = new AddResponse(id, location);
		return result;
	}

	public static ErrorResponse errorResponse(String message, String errorCode) {
		return errorResponse(message, errorCode,null);
	}	
	public static ErrorResponse errorResponse(String message, String errorCode,  List<ValidationError> errors) {
		return new ErrorResponse(message, errorCode, errors);
	}

}
