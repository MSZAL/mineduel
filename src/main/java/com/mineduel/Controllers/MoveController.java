package com.mineduel.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveController {
	
//	public Board board = new Board(16,50);
	
	// TODO: Takes in ID for current game
	// TODO: Takes in player
//	@RequestMapping(value = "/move", method = RequestMethod.POST, consumes = "application/json")
//	public ResponseEntity<?> response(@RequestBody Move move) {
//		RevealMap revealMap = new RevealMap(board, Integer.parseInt(move.getX()), Integer.parseInt(move.getY()));
//		return ResponseEntity.ok(revealMap.getMap());
//	}
	
//	@RequestMapping(value = "/move", method = RequestMethod.POST, consumes = "application/json")
//	public String response(@RequestBody Move move) {
//		RevealMap revealMap = new RevealMap(board, Integer.parseInt(move.getX()), Integer.parseInt(move.getY()));
//		return revealMap.toString();
//	}
}
