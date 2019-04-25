package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.gameObjects.Robot;
import inf112.skeleton.app.gameObjects.Items.Flag;
import inf112.skeleton.app.gameObjects.Items.IItem;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.board.Square;

public class SquareTest {
    private Position pos = new Position(1, 1);
    private Square<IItem> sq = new Square<IItem>();

	@Test
	public void insertRobot() {
		Robot rob = new Player(0, Direction.EAST, 1,2, "hei","testing");
		sq.addRobot(rob);
		assertEquals(rob, sq.getRobot());
	}
	
	@Test
	public void itemTest() {
		Flag flag = new Flag(1);
		sq.addElement(flag);
		ArrayList<IItem> items = sq.getElements();
		assertEquals(1,items.size());
		assertEquals(flag,items.get(0));
	}


}
