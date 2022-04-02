package org.upkaari.api.common.controllers;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.upkaari.api.common.constants.Views;
import org.upkaari.api.common.domain.IdentifiableEntity;
import org.upkaari.api.common.services.BaseChildEntityService;

import com.fasterxml.jackson.annotation.JsonView;


public abstract class BaseChildResourceController<T extends IdentifiableEntity<ID>, ID extends Serializable, PARENT_ID extends Serializable> extends BaseCrudController<T, ID> {
	private BaseChildEntityService<T, ID, PARENT_ID> service;

	public BaseChildResourceController(BaseChildEntityService<T, ID, PARENT_ID> service) {
		super(service);
		this.service = service;
	}

	@GET
	@JsonView(value=Views.List.class) 
	public Response findAll(//
			@Parameter(description="ID of the parent object",example = "1", required = true) @QueryParam(value = "parentId") PARENT_ID parentId) {

		List<T> body = service.findAll(parentId);
		return RestResponseBuilder.ok(body);
	}
	
}
