package net.chessbuddies.game.message;

public class ErrorMessage extends Message<InfoData>{
	public ErrorMessage(String code, String message) {
		super(MESSAGE_TYPE_ERROR, new InfoData(code, message));
	}
}
