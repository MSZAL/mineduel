package com.mineduel.Models;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.mineduel.Game;

@Component
public class GameMap {
	
	private final HashMap<String,Game> gameMap;
	private final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final int KEY_LENGTH = 5;

	public GameMap() {
		gameMap = new HashMap<String,Game>();
	}
	
	public String newGame() {
		Random random = new Random();
		StringBuilder id = new StringBuilder(KEY_LENGTH);
		
		for (int i = 0; i < KEY_LENGTH; i++) {
			id.append(alphabet.charAt(random.nextInt(alphabet.length())));
		}
		
		newGame(id.toString());
		
		return id.toString();
	}
	
	public void newGame(String id) {
		gameMap.put(id, new Game());
	}
	
	public void deleteGame(String id) {
		gameMap.remove(id);
	}
	
	public Game getGame(String id) {
		return gameMap.get(id);
	}
	
	public Set<String> getGameIds() {
		return gameMap.keySet();
	}
	
}
