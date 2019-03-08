package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import inf112.skeleton.app.board.Position;

public class PositionTest {
	
	@Test
	public void testIndex() {
		Position pos = new Position(0,0);
		assertEquals(0,pos.getIndex());
		pos = new Position(5,0);
		assertEquals(5,pos.getIndex());
		pos = new Position(0,1);
		assertEquals(12,pos.getIndex());
		pos = new Position(3,3);
		assertEquals(39,pos.getIndex());
		pos.moveEast();
		assertEquals(40,pos.getIndex());
		pos.moveSouth();
		assertEquals(28,pos.getIndex());
	}
	@Test
	public void testEquals() {
		Position pos1 = new Position(1,2);
		Position pos2 = new Position(2,2);
		pos1.moveEast();
		assertEquals(pos1,pos2);
	}

}
