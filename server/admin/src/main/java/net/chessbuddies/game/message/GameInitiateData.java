package net.chessbuddies.game.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameInitiateData {
	
	String buddy;
	Boolean waitForBuddy;
}
