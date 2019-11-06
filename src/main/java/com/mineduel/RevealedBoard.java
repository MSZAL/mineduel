package com.mineduel;

import java.util.Arrays;

public class RevealedBoard {

	private Board board;
	private int[][] revealedBoard;
	
	public RevealedBoard(Board board) {
		this.board = board;
		revealedBoard = new int[board.getSize()][board.getSize()];
		
		for (int[] row : revealedBoard)
			Arrays.fill(row, -2);
	}
	
	public void setRevealed(int x, int y, int value) {
		revealedBoard[x][y] = value;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		
		for (int i = 0; i < revealedBoard.length; i++) {
			for (int j = 0; j < revealedBoard.length; j++) {
				if (revealedBoard[i][j] != -2)
					sb.append("\"" + i + "-" + j + "\":" + revealedBoard[i][j] + ",");
			}
		}
		
	    sb.replace(sb.length() - 1, sb.length(), "}");
		
		return sb.toString();
	}
	
}
