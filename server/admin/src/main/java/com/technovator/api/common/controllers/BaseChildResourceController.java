package com.technovator.api.common.controllers;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.GET;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;
import com.technovator.api.common.constants.Views;
import com.technovator.api.common.domain.IdentifiableEntity;
import com.technovator.api.common.services.BaseChildEntityService;

import com.fasterxml.jackson.annotation.JsonView;


public abstract class BaseChildResourceController<T extends IdentifiableEntity<ID>, ID extends Serializable, PARENT_ID extends Serializable> extends BaseCrudController<T, ID> {
	private BaseChildEntityService<T, ID, PARENT_ID> service;

	public BaseChildResourceController(BaseChildEntityService<T, ID, PARENT_ID> service) {
		super(service);
		this.service = service;
	}

	@GET
	@JsonView(value=Views.List.class) 
	public RestResponse<List<T>> findAll(//
			@Parameter(description="ID of the parent object",example = "1", required = true) @RestQuery(value = "parentId") PARENT_ID parentId) {

		List<T> body = service.findAll(parentId);
		return RestResponseBuilder.ok(body);
	}

}
