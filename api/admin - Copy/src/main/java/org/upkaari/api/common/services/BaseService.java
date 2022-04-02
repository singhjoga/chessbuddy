package org.upkaari.api.common.services;

import org.upkaari.api.common.context.ClientContext;

public class BaseService{

	
	public BaseService() {
		super();
	}
	
	public String getLoggedUser() {
		return ClientContext.getInstance().getClientId();
	}
}