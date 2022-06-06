package net.chessbuddies.game.message;

import java.util.Map;

public class CommandMessage extends Message<CommandData>{
	public static final int COMMAND_PLAY_FIRST_ASK=1;
	public static final int COMMAND_PLAY_FIRST_CONFIRM=2;
	public static final int COMMAND_PLAY_START=3;
	public static final int COMMAND_MAKE_MOVE=4;
	public CommandMessage() {
		this(null, null);
	}
	public CommandMessage(Integer command, Map<String, String> data) {
		this(new CommandData(command, data));
	}
	public CommandMessage(CommandData data) {
		super(MESSAGE_TYPE_COMMAND, data);
	}
}
