package com.mineduel.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.mineduel.Game;
import com.mineduel.RevealMap;

@Component
public class SocketHandler extends TextWebSocketHandler {

	@Autowired
	private GameMap gameMap;
	private Map<String, List<WebSocketSession>> sessionMap;
	private Map<WebSocketSession,String> sessionGameMap;
	
	public SocketHandler() {
		this.sessionGameMap = new HashMap<WebSocketSession,String>();
		this.sessionMap = new HashMap<String, List<WebSocketSession>>();
	}
	
	// TODO: Figure out how to handle that unchecked conversion
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		Map<String,Object> value = new Gson().fromJson(message.getPayload(), Map.class);
		String gameId = (String) value.get("gameId");
		
		// Check if this is a new user
		if (((String) value.get("newPlayer")).equals("y")) {
			List<WebSocketSession> sessions = sessionMap.get(gameId);
			
			sessionGameMap.put(session,gameId);
			
			// Checks if this is a new game
			if (sessions == null) {
				List<WebSocketSession> newSessionList = new ArrayList<>();
				newSessionList.add(session);
				sessionMap.put(gameId, newSessionList);
			} else {
				sessions.add(session);
				Game game = gameMap.getGame(gameId);
				
				for (int i = 0; i < game.getNumOfPlayers(); i++) {
					session.sendMessage(new TextMessage(game.getRevealedBoardOfPlayer(i)));
				}
			}
		} else {
			
			// Get the move the player sent and update board
			int squareX = (int) (double) value.get("squareX");
			int squareY = (int) (double) value.get("squareY");
			
			RevealMap revealMap = new RevealMap(gameMap.getGame(gameId).getBoard(0), squareX, squareY);
			String revealMapString = revealMap.toString();
			
			// Send players the new board data representation
			List<WebSocketSession> sessionsInGame = sessionMap.get(gameId);
			
			for (WebSocketSession s : sessionsInGame) {
				s.sendMessage(new TextMessage(revealMapString));
			}
			
		}

	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String gameId = sessionGameMap.get(session);
		List<WebSocketSession> sessions = sessionMap.get(gameId);
		
		sessionGameMap.remove(session);
		sessions.remove(session);
	}
	
	

}
