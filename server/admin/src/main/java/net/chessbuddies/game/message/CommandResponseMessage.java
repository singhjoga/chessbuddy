package net.chessbuddies.game.message;

import java.util.Map;


public class CommandResponseMessage extends Message<CommandData>{
	public static final int COMMAND_PLAY_FIRST_ASK=1;
	public static final int COMMAND_PLAY_FIRST_CONFIRM=2;
	
	public CommandResponseMessage() {
		this(null, null);
	}

	public CommandResponseMessage(Integer command, Map<String, String> data) {
		super(MESSAGE_TYPE_COMMAND, new CommandData(command, data));
	}
}
