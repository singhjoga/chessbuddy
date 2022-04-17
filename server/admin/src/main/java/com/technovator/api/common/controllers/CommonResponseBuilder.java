package com.technovator.api.common.controllers;

import java.util.List;

import com.technovator.api.common.controllers.CommonResponse.AddResponse;
import com.technovator.api.common.controllers.CommonResponse.ErrorResponse;
import com.technovator.api.common.controllers.CommonResponse.ValidationError;

public class CommonResponseBuilder{
	public static AddResponse addResponse(String id, String location) {
		AddResponse result = new AddResponse(id);
		return result;
	}

	public static ErrorResponse errorResponse(String message, String errorCode) {
		return errorResponse(message, errorCode,null);
	}	
	public static ErrorResponse errorResponse(String message, String errorCode,  List<ValidationError> errors) {
		return new ErrorResponse(message, errorCode, errors);
	}

}
