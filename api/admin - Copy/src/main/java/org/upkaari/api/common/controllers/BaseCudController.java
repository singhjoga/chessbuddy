package org.upkaari.api.common.controllers;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.upkaari.api.common.constants.Views;
import org.upkaari.api.common.domain.IdentifiableEntity;
import org.upkaari.api.common.services.BaseCrudService;

import com.fasterxml.jackson.annotation.JsonView;

public class BaseCudController<T extends IdentifiableEntity<ID>, ID extends Serializable> extends BaseController {

	private BaseCrudService<T, ID> service;
	public BaseCudController(BaseCrudService<T, ID> service) {
		super();
		this.service = service;
	}

	@POST
	@Operation(description = "Add a new resource")
	public Response add(@JsonView(value = Views.Add.class) @RequestBody T obj) {
		T saved = service.add(obj);
		return addResponse(saved.getId().toString());
	}
	@PUT()
	@Path("bulk")
	@Operation(description = "Add or overwite a list of resources")
	public Response addOverwriteList(@RequestBody List<T> objList) {
		List<ID> ids = service.addOrOverwriteAll(objList);
		return bulkAddUpdateResponse(ids);
	}
	@PATCH()
	@Path("bulk")
	@Operation(description = "Add or patch a list of resources")
	public Response addUpdateList(@RequestBody List<T> objList) {
		List<ID> ids = service.addOrUpdateAll(objList);
		return bulkAddUpdateResponse(ids);
	}
	@PATCH()
	@Path("{id}")
	@Operation(description = "Partial update an existing resource")
	public Response update(@PathParam ID id, @JsonView(value = Views.Update.class) @RequestBody T obj) {
		service.update(id, obj);
		return okResponse();
	}
	@PUT()
	@Path("{id}")
	@Operation(description = "Overwrite an existing resource")
	public Response overwite(@PathParam ID id, @JsonView(value = Views.Update.class) @RequestBody T obj) {
		service.overwrite(id, obj);
		return okResponse();
	}
	@DELETE
	@Path("{id}")
	@Operation(description = "Delete an existing resource. Validation error is returned if it is referenced in other resources")
	public Response delete(@PathParam ID id) {
		service.delete(id);
		return okResponse();
	}

}