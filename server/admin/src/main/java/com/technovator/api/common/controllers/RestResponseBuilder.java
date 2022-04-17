package com.technovator.api.common.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import com.technovator.api.common.controllers.CommonResponse.AddResponse;

public class RestResponseBuilder {
	public static <T> RestResponse<T> ok(T body) {
		return ResponseBuilder.ok(body).build();
	}

	public static RestResponse<Object> ok() {
		return ResponseBuilder.ok().build();
	}

	public static RestResponse<AddResponse> created(String uri, String id) {
		AddResponse resp = new AddResponse(id);
		return ResponseBuilder.create(Status.CREATED, resp).header("Location", uri).build();
	}
}
