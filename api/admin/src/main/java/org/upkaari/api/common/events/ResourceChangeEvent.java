package org.upkaari.api.common.events;

import org.upkaari.api.common.domain.AppObect;

public class ResourceChangeEvent<T extends AppObect> extends BaseEvent<T>{
	public ResourceChangeEvent(T data, String resourceType, String action) {
		super(data, resourceType, action);
	}
}
