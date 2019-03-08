package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.GameObjects.Robot;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.board.Square;

public class SquareTest {
	private Position pos = new Position(1,1);
	private Square sq = new Square();
	
	@Test
	public void insertRobot() {
		Robot rob = new Player(Direction.EAST, 1, 1, 10, "hei");
		sq.addRobot(rob);
		assertEquals(rob, sq.getRobot());
	}

}
