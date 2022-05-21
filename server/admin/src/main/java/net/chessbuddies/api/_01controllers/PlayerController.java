package net.chessbuddies.api._01controllers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;

import com.fasterxml.jackson.annotation.JsonView;
import com.technovator.api.common.constants.Views;
import com.technovator.api.common.controllers.BaseParentResourceController;
import com.technovator.api.common.controllers.RestResponseBuilder;
import com.technovator.api.common.controllers.CommonResponse.ErrorResponse;

import net.chessbuddies.player.Player;
import net.chessbuddies.player.PlayerService;

@Path("/players")
@Singleton
@Tag(name = "Game Players")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerController extends BaseParentResourceController<Player, String>{
	private PlayerService service;
	public PlayerController() {
		super(null);
	} 
	@Inject
	public PlayerController(PlayerService service) {
		super(service);
		this.service=service;
	}
	@GET
	@JsonView(value = Views.List.class)
	@Operation( description="Get All")
	public RestResponse<List<Player>> getAll() {
		List<Player> result = service.findAll();
		return RestResponseBuilder.ok(result);
	}
	@GET
	@Path("{id}")
	@JsonView(value = Views.View.class)
	@Operation(description = "Get an existing resource by ID. Not Found error is thrown if the resource is not found")

	public RestResponse<Player> getOne(@RestPath String id, @RestQuery Boolean returnQuestionAnswers) {
		return RestResponseBuilder.ok(service.getById(id, returnQuestionAnswers));
	}
		
}
