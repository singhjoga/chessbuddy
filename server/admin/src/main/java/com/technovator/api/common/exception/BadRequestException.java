package com.technovator.api.common.exception;

import java.util.List;

public class BadRequestException extends TechnicalException{
	private static final long serialVersionUID = -871593672052845759L;

	public BadRequestException(String message) {
		super(message);
	}
	public BadRequestException(String message, List<String> errors) {
		super(getErrorStr(message, errors));
	}
	
	private static String getErrorStr(String message, List<String> errors) {
		StringBuilder sb = new StringBuilder(message);
		int i=0;
		for (String error: errors) {
			i++;
			sb.append(System.lineSeparator()).append(i).append(". ") .append(error);
		}
		
		return sb.toString();
	}
}
