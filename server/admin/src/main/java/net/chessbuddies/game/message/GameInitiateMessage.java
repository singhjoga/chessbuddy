package net.chessbuddies.game.message;

public class GameInitiateMessage extends Message<GameInitiateData>{
	
	public GameInitiateMessage() {
		super(null, null);
	}

	public GameInitiateMessage(String buddy, Boolean waitForBuddy) {
		super(MESSAGE_TYPE_GAME_INITIATE, new GameInitiateData(buddy, waitForBuddy));
	}
}
