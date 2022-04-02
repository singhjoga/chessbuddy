package org.upkaari.api.common.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NativeQueryBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(NativeQueryBuilder.class);	
	private List<Object> params = new ArrayList<>();
	private StringBuilder sql;
	private StringBuilder where = new StringBuilder();
	private String orderBy;
	private String groupBy;
	public NativeQueryBuilder(String select, String orderBy) {
		this(select, orderBy, null);
	}
	public NativeQueryBuilder(String select, String orderBy, String groupBy) {
		this.sql = new StringBuilder(select);
		this.orderBy=orderBy;
		this.groupBy=groupBy;
	}
	public void appendSql(String appendSql) {
		sql.append(appendSql);
	}
	public void appendSql(String appendSql, Object... paramValues) {
		int i=1;
		for (Object paramValue: paramValues) {
			params.add(paramValue);
			//given sql always contains the param placeholders with 1, 2 etc, withing this sql. So here calculate it for all param based
			//String givenPlaceHolder = "?"+i;
			//String realPlaceHolder = "?"+params.size();
			//appendSql=StringUtils.replace(appendSql, givenPlaceHolder, realPlaceHolder);
			i++;
		} 
		sql.append(appendSql);
	}
	public void appendWhere(Object value, String fieldName, String op) {
		if (value == null) return;
		//If value is a collection. It is probably an IN clause, make sure collection is not empty
		if (value instanceof Collection<?>) {
			if (((Collection<?>)value).isEmpty()) {
				return;
			}
		}
		and(where);
		addFilter(where, value, fieldName, op);
	}
	public void appendWhere(String sql) {
		where.append(" ").append(sql);
	}
	public void appendSqlFilter(Object value, String fieldName, String op) {
		if (value == null) return;
		//If value is a collection. It is probably an IN clause, make sure collection is not empty
		if (value instanceof Collection<?>) {
			if (((Collection<?>)value).isEmpty()) {
				return;
			}
		}
		addFilter(sql, value, fieldName, op);
	}
	public String getFinalSql() {
		StringBuilder temp = new StringBuilder(sql);
		
		if (where.length() > 0) {
			temp.append(" WHERE "+where.toString());
		}
		if (!StringUtils.isEmpty(groupBy)) {
			temp.append(" GROUP BY ").append(groupBy);			
		}
		if (!StringUtils.isEmpty(orderBy)) {
			temp.append(" ORDER BY ").append(orderBy);			
		}
		
		return temp.toString();
	}

	public Query buildNativeQuery(EntityManager em) {
		return buildNativeQuery(em, null);
	}
	public Query buildNativeQuery(EntityManager em, Class<?> resultClass) {
		String strSql = getFinalSql();
		LOG.debug(strSql.toString());
	
		Query query;
		if (resultClass==null) {
			query = em.createNativeQuery(strSql);
		}else {
			query = em.createNativeQuery(strSql, resultClass);
		}
		
		int i=1;
		for (Object param: params) {
			if (param instanceof Collection) {
				//query.setParameter(i, builInClause((Collection<Object>)param));
			}else if (param instanceof Date){
				query.setParameter(i, (Date)param,TemporalType.DATE);
			}else {
				query.setParameter(i, param);
			}
			
			i++;
		}
		
		return query;
	}
	public String builInClause(Collection<?> coll) {
		StringBuilder sb = new StringBuilder("(");
		Iterator<?> itr= coll.iterator();
		int i=0;
		while (itr.hasNext()) {
			Object obj = itr.next();
			if (i > 0) {
				//Put a comma before the value, if it is not the first value
				sb.append(",");
			}
			if (obj instanceof Number) {
				//For numeric values, no single quotes needed
				sb.append(obj);
			}else {
				sb.append("'").append(obj).append("'");
			}
			i++;
		}
		sb.append(")");
		//LOG.info("IN Clause="+sb.toString());
		return sb.toString();
	}
	public <T> List<T> getResultList(EntityManager em, Class<?> resultClass) {
		javax.persistence.Query query = buildNativeQuery(EntityManagerProvider.getEntityManager(), resultClass);
		List<T> result = query.getResultList();
		
		return result;
	}
	private void addFilter(StringBuilder sb, Object value, String fieldName,String op) {
		if (value == null) return;
		String operator=op;
		Object val = value;
		if (value instanceof String && op.equals("=")) {
			String temp = (String)value;
			if (temp.startsWith("*") || temp.endsWith("*")) {
				val = StringUtils.replace(temp, "*", "%");
				operator="like";
			}
		}
		if (val instanceof Collection) {
			sb.append(" "+fieldName+" IN "+builInClause((Collection)val));						
		}else {
			sb.append(" "+fieldName+" "+operator+" ?");			
			params.add(val);
		}
	}
	private void and(StringBuilder sb) {
		if (sb.length() != 0) {
			sb.append(" AND");
		}		
	}
	private void or(StringBuilder sb) {
		if (sb.length() != 0) {
			sb.append(" OR");
		}		
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
