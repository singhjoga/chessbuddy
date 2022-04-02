package org.upkaari.api.common.events;

import org.upkaari.api.common.domain.AppObect;

public class BaseEvent<T extends AppObect> {
	private T data;
	private String resourceType;
	private String action;

	public BaseEvent(T data, String resourceType, String action) {
		super();
		this.data = data;
		this.resourceType = resourceType;
		this.action = action;
	}

	public T getData() {
		return data;
	}

	public String getResourceType() {
		return resourceType;
	}

	public String getAction() {
		return action;
	}
	
}
