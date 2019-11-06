package com.mineduel;

import java.util.Map;

public class Game {
	
	private final int size = 16;
	private final int mines = 50;
	private final int numOfPlayers;
	
	private Board[] boards;
	
	public Game() {
		boards = new Board[1];
		numOfPlayers = 1;
		
		for (int i = 0; i < boards.length; i++) {
			boards[i] = new Board(size,mines);
		}
	}
	
	public Map<String,Integer> reveal(int player, int x, int y) {
		RevealMap revealMap = new RevealMap(boards[player], x, y);
		
		return revealMap.getMap();
	}
	
	public String getRevealedBoardOfPlayer(int player) {
		return boards[player].getRevealedBoard().toString();
	}
	
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	
	public Board getBoard(int player) {
		return boards[player];
	}

}
