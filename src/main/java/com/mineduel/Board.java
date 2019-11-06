package com.mineduel;

import java.util.Random;

public class Board {

	private static final int MINE = -1;
	private final int mines;
	private final int size;
	private int[][] board;
	private RevealedBoard revealedBoard;
	
	public Board(int size, int mines) {
		this.mines = mines;
		this.size = size;
		this.board = new int[size][size];
		this.revealedBoard = new RevealedBoard(this);
		generateBoard();
	}
	
	public boolean isValidPosition(int x, int y) {
		if (x < 0 || x >= size || y < 0 || y >= size)
			return false;
		return true;
	}
	
	public boolean hasWon() {
		return false;
	}
	
	private int countAdjacentMines(int x, int y) {
		
		int mineCount = 0;
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == 0 && j == 0)
					continue;
				
				if (isValidPosition(x + i, y + j)) {
					if (board[x + i][y + j] == MINE) {
						mineCount++;
					}
				}
			}
		}
		
		return mineCount;
	}

	private void setMine(int x, int y) {
		board[x][y] = MINE;
	}
	
	private boolean isMine(int x, int y) {
		return board[x][y] == MINE ? true : false;
	}
	
	private void generateBoard() {
		Random random = new Random();
		
		int mineCount = mines;
		while (mineCount > 0) {
			int mx = random.nextInt(size);
			int my = random.nextInt(size);
			if (!isMine(mx,my)) {
				setMine(mx,my);
				mineCount--;
			}
		}
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] != MINE) {
					board[i][j] = countAdjacentMines(i, j);
				}
			}
		}
	}
	
	public int getBoardValue(int x, int y) {
		return board[x][y];
	}

	public int getMines() {
		return mines;
	}

	public int getSize() {
		return size;
	}
	
	public RevealedBoard getRevealedBoard() {
		return revealedBoard;
	}
}
