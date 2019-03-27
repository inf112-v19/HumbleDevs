package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;

public class RobotTest {
	
	@Test
	public void testMovement() {
		Player rob = new Player(Direction.NORTH, 0,0,"Kåre Kålrabi", "testing");
		rob.move(1);
		assertEquals(new Position(0,1),rob.getPosition());
		rob.move(Direction.EAST);
		assertEquals(new Position(1,1),rob.getPosition());
	}
	@Test
	public void testRotation() {
		Player rob = new Player(Direction.NORTH, 0,0,"Albert","testing");
		rob.rotateLeft();
		assertEquals(Direction.WEST, rob.getDirection());
		rob.rotateRight();
		assertEquals(Direction.NORTH, rob.getDirection());
	}
	@Test
	public void testLife() {
		Player rob = new Player(Direction.NORTH, 0,0,"Raymond","testing");
		rob.die();
		rob.die();
		assertFalse(rob.gameOver());
		rob.die();
		assertTrue(rob.gameOver());
	}
	@Test
	public void testBackup() {
		Player rob = new Player(Direction.NORTH, 0,0,"Odin","testing");
		Position pos = new Position(5,5);
		rob.makeBackup(pos);
		rob.die();
		rob.respawn();
		assertEquals(pos,rob.getPosition());
	}
}