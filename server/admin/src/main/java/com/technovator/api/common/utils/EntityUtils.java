package com.technovator.api.common.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import com.technovator.api.common.db.EntityManagerProvider;
import com.technovator.api.common.db.NativeQueryBuilder;

public class EntityUtils {
	private static final String FIND_SQL="SELECT COUNT(*) FROM %s";

	public static long findRecordCount(Class<?> targetEntity, Field targetField, Object fieldValue) {
		return findRecordCount(targetEntity, Arrays.asList(targetField), Arrays.asList(fieldValue));
	}
	public static long findRecordCount(Class<?> targetEntity, List<Field> targetFields, List<Object> fieldValues) {
	    String tableName = getEntityTableName(targetEntity);
	    if (targetFields.size() != fieldValues.size()) {
	    	throw new IllegalArgumentException("Elements in both targetFields and fieldValues must be equal");
	    }
	    
	    
		NativeQueryBuilder qb = new NativeQueryBuilder(String.format(FIND_SQL, tableName), null);

	    int i =0;
	    for (Field field: targetFields) {
		    String fieldName = getEntityColumnName(field);
		    Object value = fieldValues.get(i);
			qb.appendWhere(value, fieldName, "=");
			i++;
	    }
		javax.persistence.Query query = qb.buildNativeQuery(EntityManagerProvider.getEntityManager());
		Number n = (Number)query.getSingleResult();
		return n.longValue();
	}
	public static String getEntityTableName(Class<?> entity) {
		String tableName;
		Table t = entity.getAnnotation(Table.class);
		if (t != null) {
			tableName = t.name();
		}else {
			tableName=entity.getSimpleName();
			Entity e = entity.getAnnotation(Entity.class);
			if (e != null && StringUtils.isNotEmpty(e.name())) {
				tableName=e.name();
			}		
		}

		return tableName;
	}
	public static String getEntityColumnName(Field field) {
		String name=field.getName();
		Column c = field.getAnnotation(Column.class);
		if (c != null && StringUtils.isNotEmpty(c.name())) {
			name=c.name();
		}
		
		return name;
	}
	public static String getFieldNames(List<Field> fields) {
		StringJoiner joiner = new StringJoiner(",");
		for (Field field: fields) {
			joiner.add(field.getName());
		}
		
		return joiner.toString();
	}
}
