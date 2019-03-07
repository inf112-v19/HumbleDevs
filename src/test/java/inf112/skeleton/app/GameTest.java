package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.IBoard;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.game.Game;

public class GameTest {
	
	@Test
	public void testFindPriority() {
		ArrayList<String> test = new ArrayList<String>();
		ProgramCard[] tet = new ProgramCard[9];
		for(int x = 0; x < 9; x++) {
			//tet[x] = new ProgramCard(x);
		}
		Board hor = new Board(12, 12,test);
		Game fysj = new Game(hor, tet, 3);
		ProgramCard[] s = fysj.shuffleCards();
		assertEquals(9, s.length);
		fysj.startRound();
		int[] sa = new int[3];
		int[] li = fysj.findPriority(0);
		
	}

}