package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.IBoard;

public class GameTest {
	
	
	
	@Test
	public void testFindPriority() {
		ArrayList<String> test = new ArrayList<String>();
		Card[] tet = new Card[9];
		for(int x = 0; x < 9; x++) {
			tet[x] = new Card(x);
		}
		Board hor = new Board(12, 12,test);
		Game fysj = new Game(hor, tet, 3);
		Card[] s = fysj.shuffleCards();
		assertEquals(9, s.length);
		fysj.startRound();
		ArrayList li = (ArrayList) fysj.findPriority(0);
		
	}

}
