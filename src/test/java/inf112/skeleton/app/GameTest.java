package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import inf112.skeleton.app.GameObjects.Robot;
import inf112.skeleton.app.GameObjects.Items.IItem;
import inf112.skeleton.app.GameObjects.Items.Pit;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.IBoard;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.board.Square;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.game.Game;

public class GameTest {
	private Game game;
	
	@Before
	public void init() {
		ArrayList<Square> test = new ArrayList<Square>(144);
		for(int x = 0; x < 144; x++) {
			test.add(new Square<IItem>());
		}
		Board board = new Board(12, 12,test);
		board.insertItem(new Position(1,2), new Pit());
		Game game = new Game(board,3);
		this.game = game;
	}
	
	@Test
	public void testFindPriority() {
		game.startRound();
		Robot[] robs = game.getRobots();
		assertEquals(5,robs[0].getCards().length);
		int[] pri = game.findPriority(1);
		Robot rob1 = robs[pri[0]];
		Robot rob2 = robs[pri[1]];
		Robot rob3 = robs[pri[2]];
		assertTrue(rob1.getCards()[1].getPriority() <= rob2.getCards()[1].getPriority());
		assertTrue(rob2.getCards()[1].getPriority() <= rob3.getCards()[1].getPriority());
  }
	
	@Test
	public void testMovement() {
		Robot[] robs = game.getRobots();
		Robot rob1 = robs[1];
		Position pos = rob1.getPosition();
		assertEquals(new Position(2,2), pos);
		game.robotMove(rob1, Direction.NORTH);
		assertEquals(new Position(2,3),pos);
	}
	
	@Test
	public void testPit() {
		Robot[] robs = game.getRobots();
		Robot rob1 = robs[1];
		game.robotMove(rob1, Direction.WEST);
		assertTrue(rob1.isDestroyed());
	}
}
