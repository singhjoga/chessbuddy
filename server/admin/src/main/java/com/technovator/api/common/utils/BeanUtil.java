package com.technovator.api.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {
	private static final Logger LOG = LoggerFactory.getLogger(BeanUtil.class);

	public static void copyProperties(Object dest, Object src) {
		try {
			
			BeanUtils.copyProperties(dest, src);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalStateException(e);
		}

	}

	public static void setFieldValue(Object bean, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(bean, value);
			LOG.debug("Field " + field.getName() + " value set to " + value + " Type: " + field.getType().getName());
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}

	}

	public static Object getFieldValue(Object bean, String fieldName) {
		try {
			Field field = FieldUtils.getField(bean.getClass(), fieldName, true);
			field.setAccessible(true);
			return field.get(bean);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	public static Object getFieldValue(Object bean, Field field) {
		try {
			field.setAccessible(true);
			return field.get(bean);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	public static List<Object> getFieldValue(Object bean, List<Field> fields) {
		List<Object> result = new ArrayList<>();
		for (Field field : fields) {
			result.add(getFieldValue(bean, field));
		}

		return result;
	}
	public static Object getFieldValue(Object bean, String fieldName, String valueWhenNotFound) {
		try {
			Field field = FieldUtils.getField(bean.getClass(), fieldName, true);
			if (field == null) {
				return valueWhenNotFound;
			}
			field.setAccessible(true);
			return field.get(bean);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
}
