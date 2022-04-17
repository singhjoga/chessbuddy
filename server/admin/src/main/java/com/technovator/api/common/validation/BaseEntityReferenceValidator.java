package com.technovator.api.common.validation;

import java.lang.annotation.Annotation;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.technovator.api.common.db.EntityManagerProvider;
import com.technovator.api.common.domain.IdentifiableEntity;

public abstract class BaseEntityReferenceValidator<T extends Annotation> implements ConstraintValidator<T, Object>{

	private T annotation;
	
	@Override
	public void initialize(T constraintAnnotation) {
		this.annotation=constraintAnnotation;
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value==null) return true;
		Class<? extends IdentifiableEntity<?>> refEntity = getValue(annotation);
	    EntityManager em = EntityManagerProvider.getEntityManager();
	    Object result=em.find(refEntity, value);
	    return result != null;
	}
	protected abstract Class<? extends IdentifiableEntity<?>> getValue( T annotation);
}
