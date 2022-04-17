package com.technovator.api.common.validation;

import java.lang.annotation.Annotation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.technovator.api.common.db.EntityManagerProvider;

public abstract class SqlValidator<T extends Annotation, V> implements ConstraintValidator<T, V>{
	private String sql;
	public SqlValidator(String sql) {
		super();
		this.sql = sql;
	}

	@Override
	public void initialize(T constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(V value, ConstraintValidatorContext context) {
		if (value==null) return true;
	    EntityManager em = EntityManagerProvider.getEntityManager();
	    Query q = em.createNativeQuery(sql);
	    q.setParameter(1, value);
	    if (q.getResultList().isEmpty()) {
	    	return false;
	    }
	    return true;
	}

}
