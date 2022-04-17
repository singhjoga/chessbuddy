package com.technovator.api.common.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import com.technovator.api.common.constants.Views;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Common REST Response
 * @author JogaSingh
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
public class CommonResponse {
	public static class WarningResponse extends MessageDetail{
		@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
		private List<Warning> warnings;
		public WarningResponse() {
			super(null,null);
		}
		public WarningResponse(String message, Set<String> warningList) {
			super(message, null);
			if (warningList != null && !warningList.isEmpty()) {
				warnings = new ArrayList<CommonResponse.Warning>();
				for (String warnMsg: warningList) {
					warnings.add(new Warning(warnMsg,null));
				}
			}
		}
		public List<Warning> getWarnings() {
			return warnings;
		}
		public void setWarnings(List<Warning> warnings) {
			this.warnings = warnings;
		}
		
	}
	@Schema(name="ErrorModel")
	public static class ErrorResponse extends MessageDetail{
		@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
		private List<ValidationError> errors=new ArrayList<>();		
		public ErrorResponse() {
			this(null, null,null);
		}
		public ErrorResponse(String message, String code, List<ValidationError> errors) {
			super(message, code);
			if (errors != null) {
				this.errors = errors;
			}
		}
		public List<ValidationError> getErrors() {
			return errors;
		}
		public void setErrors(List<ValidationError>  errors) {
			this.errors = errors;
		}
	}

	public static class Warning extends MessageDetail{
		public Warning() {
			this(null, null);
		}
		public Warning(String message, String code) {
			super(message, code);
		}
	}
	public static class Error extends MessageDetail{		
		public Error() {
			this(null,null);
		}
		public Error(String message, String code) {
			super(message, code);
		}
	}
	public static class ValidationError extends Error{
		@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
		private String field;
		public ValidationError() {
			super(null, null);
		}
		public ValidationError(String field, String message, String code) {
			super(message, code);
			this.field=field;
		}
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public static List<ValidationError> from(List<? extends Set<? extends ConstraintViolation<?>>> violations) {
			List<ValidationError> errors = new ArrayList<CommonResponse.ValidationError>();
			int i=0;
			for (Set<? extends ConstraintViolation<?>> s: violations) {
				from(errors,s, i);
			}
			
			return errors;
		}
		public static List<ValidationError> from(Set<? extends ConstraintViolation<?> > violations) {
			List<ValidationError> errors = new ArrayList<CommonResponse.ValidationError>();
			return from (errors, violations, null);
		}
		public static List<ValidationError> from(List<ValidationError> errors, Set<? extends ConstraintViolation<?> > violations, Integer index) {
			for (ConstraintViolation<?> violation : violations) {
				addError(errors,violation,index);
			}
			return errors;
		}
		private static void addError(List<ValidationError> errors, ConstraintViolation<?> violation, Integer index) {
			String field=StringUtils.substringAfterLast(violation.getPropertyPath().toString(), ".");
			if (StringUtils.isEmpty(field)) {
				field=violation.getPropertyPath().toString();
			}
			if (index != null) {
				field=field+"."+index;
			}
			String errorCode=violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
			errors.add(new ValidationError(field, violation.getMessage(), errorCode));
		}
	}

	public static class MessageDetail {
		@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
		private String message;
		@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
		private String code;
		public MessageDetail(String message, String code) {
			super();
			this.message = message;
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
	}

	public static class AddResponse {
		
		@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
		private String id;

		public AddResponse() {
		}
		public AddResponse(String id) {
			this.id = id;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
	}
	public static class BulkOperationResponse{
		@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
		private int affectedItems;
		@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})		
		private WarningResponse warning;
		public BulkOperationResponse(int affectedItems) {
			this.affectedItems=affectedItems;
		}
		public int getAffectedItems() {
			return affectedItems;
		}
		public void setAffectedItems(int affectedItems) {
			this.affectedItems = affectedItems;
		}
		public WarningResponse getWarning() {
			return warning;
		}
		public void setWarning(WarningResponse warning) {
			this.warning = warning;
		}
		
	}
	public static class BulkAddUpdateResponse{
		@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
		private List<String> ids;

		public BulkAddUpdateResponse(List<String> ids) {
			super();
			this.ids = ids;
		}

		public List<String> getIds() {
			return ids;
		}

		public void setIds(List<String> ids) {
			this.ids = ids;
		}

	}
}
