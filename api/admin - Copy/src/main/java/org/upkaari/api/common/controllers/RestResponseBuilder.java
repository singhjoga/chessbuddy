package org.upkaari.api.common.controllers;

import javax.ws.rs.core.Response;

public class RestResponseBuilder {
	public static Response ok(Object body) {
		return Response.status(Response.Status.OK).entity(body).build();
	}
	public static Response ok() {
		return Response.status(Response.Status.OK).build();
	}
	public static Response created(String uri) {
		return Response.status(Response.Status.CREATED).header("location", uri).build();
	}
}
