package com.technovator.api.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * Utility functions for handling object properties
 * 
 * @author jogas
 *
 */
public class PojoUtils {

	/**
	 * Set the value of properties of the given type.
	 * 
	 * @param obj object
	 * @param propertyType type of property i.e. String.class, Date.class etc.
	 * @param value value
	 */
	public static void setNullProperties(Object obj, Class<?> propertyType, Object value) {
		for (Field field: FieldUtils.getAllFieldsList(obj.getClass())) {
			if (field.getType().equals(propertyType)) {
				try {
					field.setAccessible(true);
					Object currentValue = field.get(obj);
					if (currentValue == null) {
						field.set(obj, value);
					}
				} catch (IllegalArgumentException e) {
					throw new IllegalStateException(e);
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(e);
				}

			}
		}
		
	}
	
	/**
	 * Returns Method object for the given class and method name.
	 * 
	 * @param claas
	 * @param methodName
	 * @return null when method not found or security exception
	 */
	public static Method getMethod(Class<?> claas, String methodName) {
		try {
			Method method = claas.getMethod(methodName);
			return method;
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}
	
	/**
	 * Returns the Field for the given class and field name.
	 * 
	 * @param claas
	 * @param fieldName
	 * @return null when field not found or security exception
	 */
	public static Field getDeclaredField(Class<?> claas, String fieldName) {
		try {
			Field field = claas.getDeclaredField(fieldName);
			return field;
		} catch (NoSuchFieldException | SecurityException e) {
			return null;
		}
	}
}
