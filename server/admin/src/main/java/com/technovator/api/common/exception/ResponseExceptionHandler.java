package com.technovator.api.common.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.technovator.api.common.controllers.CommonResponse.ErrorResponse;
import com.technovator.api.common.controllers.CommonResponse.ValidationError;
import com.technovator.api.common.controllers.CommonResponseBuilder;

//@RestControllerAdvice
//@Component
public class ResponseExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(ResponseExceptionHandler.class);

	public static class TechnicalExceptionHandler<T extends TechnicalException> implements ExceptionMapper<T> {
		private Status status;		
		public TechnicalExceptionHandler(Status status) {
			super();
			this.status = status;
		}
		@Override
		public Response toResponse(T e) {
			return Response.status(status).entity(CommonResponseBuilder.errorResponse(e.getMessage(), e.getErrorCode())).build();
		}
	}

	public static class RuntimeExceptionHandler<T extends RuntimeException> implements ExceptionMapper<T> {
		private Status status;
		private String errorCode;
		public RuntimeExceptionHandler(Status status, String errorCode) {
			super();
			this.status = status;
		}
		@Override
		public Response toResponse(T e) {
			return Response.status(status).entity(CommonResponseBuilder.errorResponse(e.getMessage(), errorCode)).build();
		}
	}
	@Provider
	public static class ResourceNotFoundExceptionHandler extends TechnicalExceptionHandler<ResourceNotFoundException> {
		public ResourceNotFoundExceptionHandler() {
			super(Response.Status.NOT_FOUND);
		}
	}
	@Provider
	public static class AccessDeniedExceptionHandler extends TechnicalExceptionHandler<AccessDeniedException> {
		public AccessDeniedExceptionHandler() {
			super(Response.Status.FORBIDDEN);
		}
	}
	@Provider
	public static class AuthorizationExceptionHandler extends TechnicalExceptionHandler<AuthorizationException> {
		public AuthorizationExceptionHandler() {
			super(Response.Status.FORBIDDEN);
		}
	}
	@Provider
	public static class BadRequestExceptionHandler extends TechnicalExceptionHandler<BadRequestException> {
		public BadRequestExceptionHandler() {
			super(Response.Status.BAD_REQUEST);
		}
	}

	@Provider
	public static class BusinessRulesExceptionHandler extends TechnicalExceptionHandler<BusinessRulesException> {
		public BusinessRulesExceptionHandler() {
			super(Response.Status.PRECONDITION_FAILED);
		}
	}
	@Provider
	public static class IllegalStateExceptionHandler extends RuntimeExceptionHandler<IllegalStateException> {
		public IllegalStateExceptionHandler() {
			super(Response.Status.INTERNAL_SERVER_ERROR, CommonErrorCodes.INTERNAL_ERROR);
		}
	}
	@Provider
	public static class IllegalArgumentExceptionHandler extends RuntimeExceptionHandler<IllegalArgumentException> {
		public IllegalArgumentExceptionHandler() {
			super(Response.Status.BAD_REQUEST, CommonErrorCodes.INTERNAL_ERROR);
		}
	}
	@Provider
	public static class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {	
		@Override
		public Response toResponse(ValidationException e) {
			ErrorResponse resp = new ErrorResponse();
			resp.setErrors(e.getErrors());
			resp.setMessage("Validation error");
			return Response.status(Response.Status.BAD_REQUEST).entity(resp).build();
		}
	}
	@Provider
	public static class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {	
		@Override
		public Response toResponse(ConstraintViolationException e) {
			ErrorResponse resp = createConstraintViolationsResponse(e.getConstraintViolations());
			resp.setMessage("Constration violation");
			return Response.status(Response.Status.BAD_REQUEST).entity(resp).build();
		}
	}

	private static ErrorResponse createConstraintViolationsResponse(Set<ConstraintViolation<?> > violations) {
		ErrorResponse resp = new ErrorResponse();
		for (ConstraintViolation<?> violation : violations) {
			addError(resp,violation,null);
		}
		return resp;	
	}
	private static void addError(ErrorResponse resp, ConstraintViolation<?> violation, Integer index) {
		String field=StringUtils.substringAfterLast(violation.getPropertyPath().toString(), ".");
		if (StringUtils.isEmpty(field)) {
			field=violation.getPropertyPath().toString();
		}
		if (index != null) {
			field=field+"."+index;
		}
		String errorCode=violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
		resp.getErrors().add(new ValidationError(field, violation.getMessage(), errorCode));
	}
}
