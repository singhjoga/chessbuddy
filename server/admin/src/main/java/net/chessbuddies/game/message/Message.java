package net.chessbuddies.game.message;

public abstract class Message<T> {
	public static final int MESSAGE_TYPE_ERROR=11;
	public static final int MESSAGE_TYPE_INFO=0;
	public static final int MESSAGE_TYPE_GAME_INITIATE=1;
	public static final int MESSAGE_TYPE_COMMAND=2;
	public static final int MESSAGE_TYPE_COMMAND_RESPONSE=3;
	public static final int MESSAGE_TYPE_GAME_MOVE_REQUEST=4;
	public static final int MESSAGE_TYPE_GAME_MOVE_RESPONSE=5;
	
	private Integer type;
	private T payload;

	public Message(Integer type, T payload) {
		super();
		this.type = type;
		this.payload=payload;
	}
	
	public Integer getType() {
		return type;
	}

	public T getPayload() {
		return payload;
	}
}
