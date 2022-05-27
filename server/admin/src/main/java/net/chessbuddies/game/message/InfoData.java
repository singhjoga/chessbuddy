package net.chessbuddies.game.message;

public class InfoData {
	private String code;
	private String message;
	
	public InfoData(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
}
