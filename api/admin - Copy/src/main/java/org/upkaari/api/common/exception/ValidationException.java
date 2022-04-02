package org.upkaari.api.common.exception;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.upkaari.api.common.controllers.CommonResponse.ValidationError;

/**
 * Validation exceptions
 * 
 * @author JogaSingh
 *
 */
public class ValidationException extends BusinessRulesException {
	private static final long serialVersionUID = 1L;
	private List<ValidationError> errors;

	public ValidationException(String field, String errorCode, String message) {
		this(Arrays.asList(new ValidationError(field, message, errorCode)));
	}
	
	public ValidationException(List<ValidationError> errors) {
		super(null,CommonErrorCodes.VALIDATION_ERROR);
		this.errors = errors;
	}

	public ValidationException(Set<? extends ConstraintViolation<?>> violations) {
		this(ValidationError.from(violations));
	}

	public List<ValidationError> getErrors() {
		return errors;
	}
	@Override
	public String getMessage() {
		return toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (ValidationError error : this.errors) {
			if (sb.length() > 0) {
				sb.append(System.lineSeparator());
			}
			sb.append("field=").append(error.getField()).append(", message=").append(error.getMessage())
					.append(", errorCode=").append(error.getCode());

		}

		return sb.toString();
	}

}
