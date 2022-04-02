package org.upkaari.api.common.controllers;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import org.upkaari.api.common.auditlog.AuditLog;
import org.upkaari.api.common.constants.Views;
import org.upkaari.api.common.domain.IdentifiableEntity;
import org.upkaari.api.common.services.BaseCrudService;

import com.fasterxml.jackson.annotation.JsonView;

public abstract class BaseParentResourceController<T extends IdentifiableEntity<ID>, ID extends Serializable> extends BaseCrudController<T, ID> {
	private BaseCrudService<T, ID> service;
	public BaseParentResourceController(BaseCrudService<T, ID> service) {
		super(service);
		this.service = service;
	}

	@GET
	@Path("{id}/history")
	@JsonView(value = Views.List.class)
	@Operation( description="Returns the change history for given resource id")
	public RestResponse<List<AuditLog>> getHistory(@RestPath ID id) {
		List<AuditLog> body = service.getHistory(id);
		return RestResponseBuilder.ok(body);
	}
	
}
