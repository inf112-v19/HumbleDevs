package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.gameObjects.Robot;

public class RobotTest {

    @Test
    public void testDamage() {
        Player rob = new Player(0,Direction.NORTH, 0, 0, "Ingrid", "testing");
        rob.takeDamage();
        assertEquals(1,rob.getDamageTokens());
    }

    @Test
    public void testDamage2() {
        Player rob = new Player(0, Direction.NORTH, 0, 0, "Ole", "testing");
        int exp = 1;
        for (int i = 0; i <9; i ++){
            rob.takeDamage();
            assertEquals(exp++,rob.getDamageTokens());
        }
    }

    @Test
    public void testTenDamageOneLifeLost(){
        Player rob = new Player(0, Direction.NORTH, 0, 0, "Solveig", "testing");
        for (int i = 0; i <10; i ++){
            rob.takeDamage();
        }
        assertEquals(0,rob.getDamageTokens());
        assertEquals(2,rob.getLifeTokens());

    }
    @Test
    public void testDestroyed(){
        Player rob = new Player(0, Direction.NORTH, 0, 0, "Ida", "testing");
        rob.isDestroyed();
        assertEquals(3,rob.getLifeTokens());
    }
    @Test
    public void testStillAliveAfterGettingDestroyedTwice(){
        Player rob = new Player(0, Direction.NORTH, 0, 0, "Kaja", "testing");
        for(int i = 0; i<2; i++){
            rob.isDestroyed();
            rob.respawn();
        }
        assertTrue(rob.getLifeTokens() > 0);
    }
	@Test
	public void testMovement() {
		Player rob = new Player(0, Direction.NORTH, 0,0,"Kåre Kålrabi", "testing");
		rob.move(rob.getDirection());
		assertEquals(new Position(0,1),rob.getPosition());
		rob.move(Direction.EAST);
		assertEquals(new Position(1,1),rob.getPosition());
	}
	@Test
	public void testRotation() {
		Player rob = new Player(0, Direction.NORTH, 0,0,"Albert","testing");
		rob.rotateLeft();
		assertEquals(Direction.WEST, rob.getDirection());
		rob.rotateRight();
		assertEquals(Direction.NORTH, rob.getDirection());
	}
	@Test
	public void testLife() {
		Player rob = new Player(0, Direction.NORTH, 0,0,"Raymond","testing");
		rob.die();
		rob.die();
		assertFalse(rob.gameOver());
		rob.die();
		assertTrue(rob.gameOver());
	}
	@Test
	public void testBackup() {
		Player rob = new Player(0, Direction.NORTH, 0,0,"Odin","testing");
		Position pos = new Position(5,5);
		rob.makeBackup(pos);
		rob.die();
		rob.respawn();
		assertEquals(pos,rob.getPosition());
	}
}