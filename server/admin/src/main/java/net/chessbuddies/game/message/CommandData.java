package net.chessbuddies.game.message;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandData {
	private Integer command;
	private Map<String, String> data;
}
