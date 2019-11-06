package com.mineduel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RevealMap {

	private final Board board;
	private final RevealedBoard revealedBoard;
	private final int x;
	private final int y;
	private Map<String, Integer> reveal;
	
	public RevealMap(Board board, int x, int y) {
		this.board = board;
		this.revealedBoard = board.getRevealedBoard();
		this.y = y;
		this.x = x;
		reveal = new HashMap<String,Integer>();
		populateMap(x,y);
	}
	
	private void populateMap(int cx, int cy) {
		int val = board.getBoardValue(cx, cy);
		
		if (reveal.get(cx + "-" + cy) != null)
			return;
		
		if (val != 0) {
			reveal.put(cx + "-" + cy, val);
			revealedBoard.setRevealed(cx, cy, val);
		} else {
			reveal.put(cx + "-" + cy,board.getBoardValue(cx, cy));
			revealedBoard.setRevealed(cx, cy, val);
			
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 && j == 0)
						continue;
					
					if (board.isValidPosition(cx + i, cy + j)) {
							populateMap(cx + i, cy + j);
					}
				}
			}
		}
	}
	
	public Map<String,Integer> getMap() {
		return reveal;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		
		
	    Iterator<Entry<String, Integer>> it = reveal.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String,Integer> pair = (Map.Entry<String,Integer>)it.next();
	        
	        sb.append("\"" + pair.getKey() + "\":" + pair.getValue() + ",");
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    // Removes the ',' and closes JSON object
	    sb.replace(sb.length() - 1, sb.length(), "}");
		
	    // If there are no entries
	    if (sb.toString().equals("}")) {
	    	return "{}";
	    }
	    
		return sb.toString();
	}
	
}
