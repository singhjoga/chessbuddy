package com.technovator.api.common.services;

import com.technovator.api.common.context.ClientContext;

public class BaseService{

	
	public BaseService() {
		super();
	}
	
	public String getLoggedUser() {
		return ClientContext.getInstance().getClientId();
	}
}