package com.mineduel.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mineduel.Game;
import com.mineduel.Models.GameMap;

@Controller
public class GameController {
	
	@Autowired
	private GameMap gameMap;
	
    @RequestMapping("game/{id}")
    public String requestGame(@PathVariable String id) {
    	
    	Game requestedGame;
    	
    	synchronized (gameMap) {
    		requestedGame = gameMap.getGame(id);	
		}
    	// Return error
    	if (requestedGame == null) {
    		return "error";
    	}
    	
    	return "game";
    }
    
    @RequestMapping("/game/generate")
    @ResponseBody
    public String generateGame() {
    	String gameId;
    	
    	synchronized (gameMap) {
    		gameId = gameMap.newGame();
		}
    	return gameId;
    }

}
