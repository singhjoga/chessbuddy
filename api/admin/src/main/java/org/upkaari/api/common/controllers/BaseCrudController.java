package org.upkaari.api.common.controllers;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import org.upkaari.api.common.constants.Views;
import org.upkaari.api.common.controllers.CommonResponse.ErrorResponse;
import org.upkaari.api.common.domain.IdentifiableEntity;
import org.upkaari.api.common.services.BaseCrudService;

import com.fasterxml.jackson.annotation.JsonView;

public abstract class BaseCrudController<T extends IdentifiableEntity<ID>, ID extends Serializable>
		extends BaseCudController<T, ID> {

	private BaseCrudService<T, ID> service;

	public BaseCrudController(BaseCrudService<T, ID> service) {
		super(service);
		this.service = service;
	}

	@GET
	@Path("{id}")
	@JsonView(value = Views.View.class)
	@Operation(description = "Get an existing resource by ID. Not Found error is thrown if the resource is not found")

	//@APIResponse(responseCode = "200", description = "Found the FSC", content = {
	//		@Content(mediaType = "application/json", schema = @Schema(implementation = Work.class)) })
	@APIResponse(responseCode = "400", description = "Invalid input supplied", content = {
			@Content(schema = @Schema(implementation = ErrorResponse.class)) })
	@APIResponse(responseCode = "404", description = "Fsc not found", content = @Content)

	public RestResponse<T> getOne(@RestPath ID id) {
		return RestResponseBuilder.ok(service.getById(id));
	}

}