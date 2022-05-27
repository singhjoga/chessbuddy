package net.chessbuddies.game.message;

public class InfoMessage extends Message<InfoData>{
	public InfoMessage(String code, String message) {
		super(MESSAGE_TYPE_INFO, new InfoData(code, message));
	}
}
