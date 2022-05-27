package net.chessbuddies.api._01controllers;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.technovator.api.common.controllers.BaseController;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.chessbuddies.engine.AI;
import net.chessbuddies.engine.Board;
@Path("/engine")
@Singleton
@Tag(name = "Chess Engine")
@Produces(MediaType.APPLICATION_JSON)
public class EngineController extends BaseController{

	@POST
	@Path("/move")
	public MoveResponse makeMove(@RequestBody MoveRequest request) {
		Board board = new Board(request.fen);
		AI ai = new AI(3, 1, board.getTurnColour(), "AI", 3000);
		String move = ai.getMove(board).getGuiString();
		return new MoveResponse(move);
	}
	
	@Data
	public static class MoveRequest {
		private String fen;
		private Level level;
	}
	@Data
	@AllArgsConstructor
	public static class MoveResponse {
		private String move;
	}
	public static enum Level {
		EASY,
		MEDIUM
	}
}
