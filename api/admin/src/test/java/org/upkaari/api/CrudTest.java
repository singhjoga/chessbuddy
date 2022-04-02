package org.upkaari.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;

import org.upkaari.api.common.domain.AbstractResource;

public abstract class CrudTest<T extends AbstractResource, ID extends Serializable> extends AbstractTestRestAssured{
	private Class<T> simpleType;
	private Class<T[]> arrayType;

	public CrudTest(Class<T> simpleType, Class<T[]> arrayType) {
		super();
		this.simpleType = simpleType;
		this.arrayType = arrayType;
	}
	protected void testAdd() throws Exception {
	   	T obj = create();
        ID id = (ID)createResourceSuccess(getResourceUri(), obj);
        T[] list = getResource(getResourceUri(),arrayType);
        T saved = findByIdIn(list, id);
        assertThat(saved).isNotNull();
	}
	protected abstract T create();
	protected T findByIdIn(T[] list, ID id) {
		for (T obj: list) {
			if (id.equals(obj.getId())) {
				return obj;
			}
		}
		
		return null;
	}
}
