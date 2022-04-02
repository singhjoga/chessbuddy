package org.upkaari.api._01controllers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;
import org.upkaari.api.common.constants.Views;
import org.upkaari.api.common.controllers.BaseParentResourceController;
import org.upkaari.api.common.controllers.RestResponseBuilder;
import org.upkaari.api.components.work.Work;
import org.upkaari.api.components.work.WorkService;

import com.fasterxml.jackson.annotation.JsonView;

@Path("/works")
@Singleton
@Tag(name = "Works Catalog")
@Produces(MediaType.APPLICATION_JSON)
public class WorkController extends BaseParentResourceController<Work, String>{
	private WorkService service;
	public WorkController() {
		super(null);
	} 
	@Inject
	public WorkController(WorkService service) {
		super(service);
		this.service=service;
	}
	@GET
	@JsonView(value = Views.List.class)
	@Operation( description="Get All")
	public RestResponse<List<Work>> getAll() {
		return RestResponseBuilder.ok(service.findAll());
	}
}
