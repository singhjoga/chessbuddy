package net.chessbuddies.game.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageParser {
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static Message<?> parse(String jsonStr) {
		try {
			JsonNode jsonNode = mapper.readTree(jsonStr);
			int type = jsonNode.get("type").asInt();
			switch (type) {
			case Message.MESSAGE_TYPE_GAME_INITIATE:
				return mapper.readValue(jsonStr, GameInitiateMessage.class);
			case Message.MESSAGE_TYPE_ERROR:
				return mapper.readValue(jsonStr, ErrorMessage.class);
			case Message.MESSAGE_TYPE_INFO:
				return mapper.readValue(jsonStr, InfoMessage.class);
			case Message.MESSAGE_TYPE_COMMAND:
				return mapper.readValue(jsonStr, CommandMessage.class);		
			case Message.MESSAGE_TYPE_COMMAND_RESPONSE:
				return mapper.readValue(jsonStr, CommandResponseMessage.class);		
			default:
				throw new IllegalArgumentException("Invalid message type "+type);
				
			}
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e.getMessage(),e);
		}
	}
	
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e.getMessage(),e);
		}
	}
}
