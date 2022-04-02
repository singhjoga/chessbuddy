package org.upkaari.api.common.controllers;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import org.upkaari.api.common.constants.Views;
import org.upkaari.api.common.controllers.CommonResponse.AddResponse;
import org.upkaari.api.common.controllers.CommonResponse.BulkAddUpdateResponse;
import org.upkaari.api.common.domain.IdentifiableEntity;
import org.upkaari.api.common.services.BaseCrudService;

import com.fasterxml.jackson.annotation.JsonView;

public abstract class BaseCudController<T extends IdentifiableEntity<ID>, ID extends Serializable> extends BaseController {

	private BaseCrudService<T, ID> service;
	public BaseCudController(BaseCrudService<T, ID> service) {
		super();
		this.service = service;
	}
	
	@POST
	@Operation(description = "Add a new resource")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResponse<AddResponse> add(@JsonView(value = Views.Add.class) @RequestBody T obj) {
		T saved = service.add(obj);
		return addResponse(saved.getId().toString());
	}
	@PUT()
	@Path("bulk")
	@Operation(description = "Add or overwite a list of resources")
	public RestResponse<BulkAddUpdateResponse> addOverwriteList(@RequestBody List<T> objList) {
		List<ID> ids = service.addOrOverwriteAll(objList);
		return bulkAddUpdateResponse(ids);
	}
	@PATCH()
	@Path("bulk")
	@Operation(description = "Add or patch a list of resources")
	public RestResponse<BulkAddUpdateResponse> addUpdateList(@RequestBody List<T> objList) {
		List<ID> ids = service.addOrUpdateAll(objList);
		return bulkAddUpdateResponse(ids);
	}

	@PATCH()
	@Path("{id}")
	@Operation(description = "Partial update an existing resource")
	public RestResponse<Object> update(@RestPath ID id, @JsonView(value = Views.Update.class) @RequestBody T obj) {
		service.update(id, obj);
		return okResponse();
	}


	@PUT()
	@Path("{id}")
	@Operation(description = "Overwrite an existing resource")
	public RestResponse<Object> overwite(@RestPath ID id, @JsonView(value = Views.Update.class) @RequestBody T obj) {
		service.overwrite(id, obj);
		return okResponse();
	}

	@DELETE
	@Path("{id}")
	@Operation(description = "Delete an existing resource. Validation error is returned if it is referenced in other resources")
	public RestResponse<Object> delete(@RestPath ID id) {
		service.delete(id);
		return okResponse();
	}

}