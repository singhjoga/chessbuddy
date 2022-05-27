package net.chessbuddies.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.chessbuddies.game.message.CommandMessage;
import net.chessbuddies.game.message.CommandResponseMessage;
import net.chessbuddies.game.message.ErrorMessage;
import net.chessbuddies.game.message.GameInitiateMessage;
import net.chessbuddies.game.message.InfoMessage;
import net.chessbuddies.game.message.Message;
import net.chessbuddies.game.message.MessageEncoder;
import net.chessbuddies.game.message.MessageParser;

@ServerEndpoint(value = "/game/{username}", encoders = { MessageEncoder.class })
@ApplicationScoped
@Slf4j
public class GameController {
	private static final String INTIAL_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	private static final String TEST_INTIAL_FEN = "r1b1k1r1/pppp1ppp/n3p2P/P7/7q/P5P1/2PPPP2/1NBQKBNn w q - 0 10";
	Map<String, UserInfo> sessions = new ConcurrentHashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) {
		print("Open: " + username);
		UserInfo userInfo = sessions.get(username);
		if (userInfo != null && userInfo.getSession() != null) {
			sendErrorMessage(session, "DuplicateUser", "Username is already in use");
			try {
				session.close();
			} catch (IOException e) {
				print("Error: closing seession for " + username);
			}
			return;
		}
		if (userInfo == null) {
			userInfo = new UserInfo(username, session, null, null);
		} else {
			userInfo.setSession(session);
		}
		sessions.put(username, userInfo);
		sendInfoMessage(session, "OK", "OK");
	}

	@OnClose
	public void onClose(Session session, @PathParam("username") String username) {
		sessions.remove(username);
		print("Close: " + username);
	}

	@OnError
	public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
		sessions.remove(username);
		try {
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		print("Server Error: " + username + ". " + throwable.getMessage());
	}

	@OnMessage
	public void onMessage(String message, @PathParam("username") String username) {
		print("Message: " + username + ": " + message);
		UserInfo userInfo = sessions.get(username);
		Session session = userInfo.getSession();
		Message<?> msgObj = MessageParser.parse(message);
		sendOK(session);
		if (msgObj instanceof GameInitiateMessage) {
			onInitiateGame(username, (GameInitiateMessage) msgObj);
		} else if (msgObj instanceof CommandResponseMessage) {
			handleCommandResponse(username, (CommandResponseMessage) msgObj);
		} else {

		}
	}

	private void handleCommandResponse(String username, CommandResponseMessage cmdResponse) {
		UserInfo userInfo = sessions.get(username);
	
		int cmd = cmdResponse.getPayload().getCommand();
		if (cmd == CommandMessage.COMMAND_PLAY_FIRST_ASK) {
			//it is a reply to the prompt for play first question. Get it confirmed from the other player.
			sendCommand(userInfo.getBuddy(), new CommandMessage(CommandMessage.COMMAND_PLAY_FIRST_CONFIRM, cmdResponse.getPayload().getData()));
		}else if (cmd == CommandMessage.COMMAND_PLAY_FIRST_CONFIRM) {
			//get the response from data
			String resp = cmdResponse.getPayload().getData().get("response");
			//get the command which was sent
			CommandMessage sentMsg = userInfo.getGameInfo().getLastMessage().getMessage();
			String playFirst = sentMsg.getPayload().getData().get("playFirst");
			if (!playFirst.equals(username) && !playFirst.equals(userInfo.getBuddy())) {
				//value should be a username
				throw new IllegalStateException("playFirst element value must be a player name");
			}
			if (resp.equals("reject")) {
				//send new proposal to buddy
				playFirst = playFirst.equals(username)?userInfo.getBuddy():username;
				sentMsg.getPayload().getData().put("playFirst", playFirst);
				sendCommand(userInfo.getBuddy(), sentMsg);
			}else {
				//start game
				GameInfo gameInfo = userInfo.getGameInfo();
				//playing first is white and other is black
				String blackPayer = playFirst.equals(username)?userInfo.getBuddy():username;
				gameInfo.setWhitePlayer(playFirst);
				gameInfo.setBlackPlayer(blackPayer);
				Map<String, String> cmdData = new HashMap<>();
				cmdData.put("initialFeb", TEST_INTIAL_FEN);
				CommandMessage cmdMsg = new CommandMessage(CommandMessage.COMMAND_PLAY_START, cmdData);
				// send start msg to both
				sendCommand(userInfo.getBuddy(), cmdMsg);
				sendCommand(username, cmdMsg);
			}
		}
	}

	private void sendOK(Session session) {
		sendInfoMessage(session, "OK", "OK");
	}

	private void sendErrorMessage(Session session, String code, String message) {
		ErrorMessage msg = new ErrorMessage(code, message);
		sendMessage(session, msg);
	}

	private void sendInfoMessage(Session session, String code, String message) {
		InfoMessage msg = new InfoMessage(code, message);
		sendMessage(session, msg);
	}
	private void sendCommand(String payer, Integer command) {
		CommandMessage msg = new CommandMessage(command, null);
		sendCommand(payer, msg);
	}
	private void sendCommand(String player, CommandMessage msg) {
		UserInfo userInfo = sessions.get(player);
		LastMessage lastMsg = new LastMessage(msg, player, System.nanoTime());
		userInfo.getGameInfo().setLastMessage(lastMsg);
		sendMessage(userInfo.getSession(), msg);
		log.info("{} sent command: {}", player, MessageParser.toJson(msg));
	}

	private void sendMessage(Session session, Message<?> msg) {
		session.getAsyncRemote().sendObject(msg, result -> {
			if (result.getException() != null) {
				print("Unable to send message: " + result.getException());
			}
		});
	}

	private void onInitiateGame(String username, GameInitiateMessage msg) {
		UserInfo userInfo = sessions.get(username);
		Session session = userInfo.getSession();
		String buddy = msg.getPayload().getBuddy();
		Boolean waitForBuddy = msg.getPayload().getWaitForBuddy();
		if (StringUtils.isNotBlank(buddy)) {
			UserInfo buddyUserInfo = sessions.get(buddy);
			if (buddyUserInfo == null && !waitForBuddy) {
				log.error("Buddy {} fot for user {}", buddy, username);
				sendErrorMessage(session, "BuddyNotFound", "Buddy not found: " + buddy);
				return;
			} else {
				if (buddyUserInfo != null) {
					userInfo.setBuddy(buddy);
					buddyUserInfo.setBuddy(username);
					initiateGame(userInfo);
				} else {
					// create dummy session, so that game can be initiated as soon as the buddy logs
					// in
					buddyUserInfo = new UserInfo(buddy, null, null, username);
					sessions.put(buddy, buddyUserInfo);
				}
			}
		} else {
			if (!waitForBuddy) {
				sendErrorMessage(session, "InvalidRequest", "waitForBuddy should be set to true or buddy should be provided");
				return;
			} else {
				GameInfo gameInfo = new GameInfo();
				gameInfo.setStatus(GameStatus.WaitingForBuddy);
				userInfo.setGameInfo(gameInfo);
			}
		}
		sendOK(session);
	}

	private void initiateGame(UserInfo userInfo) {
		log.info("Initiage game: player {}, buddy {}", userInfo.getUsername(), userInfo.getBuddy());
		UserInfo buddyInfo = sessions.get(userInfo.getBuddy());
		GameInfo gameInfo = new GameInfo();
		userInfo.setGameInfo(gameInfo);
		buddyInfo.setGameInfo(gameInfo);
		// buddy logs always first, therefore he is asked the first move.
		sendCommand(userInfo.getBuddy(), CommandMessage.COMMAND_PLAY_FIRST_ASK);
	}

	private void print(String msg) {
		log.info(msg);
	}

	@Data
	@AllArgsConstructor
	public static class UserInfo {
		private String username;
		private Session session;
		private GameInfo gameInfo;
		private String buddy;
	}

	@Data
	public static class GameInfo {
		private String blackPlayer;
		private String whitePlayer;
		private GameStatus status;
		private LastMessage lastMessage;

	}

	@Data
	@AllArgsConstructor
	public static class LastMessage {
		private CommandMessage message;
		private String player;
		private long sentAtTimeNs;
	}

	public static enum GameStatus {
		WaitingForBuddy, Initiated, Started
	}
}
