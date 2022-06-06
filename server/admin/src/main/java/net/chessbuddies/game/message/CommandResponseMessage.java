package net.chessbuddies.game.message;

import java.util.Map;


public class CommandResponseMessage extends Message<CommandData>{
	public CommandResponseMessage() {
		this(null, null);
	}

	public CommandResponseMessage(Integer command, Map<String, String> data) {
		super(MESSAGE_TYPE_COMMAND, new CommandData(command, data));
	}
}
