package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import inf112.skeleton.app.board.Direction;

/**
 * Tests to test the methods in the enum class Direction
 *
 * @author Even Kolsgaard
 */
public class DirectionTest {

    @Test
    public void testOpposite() {
        Direction dir = Direction.NORTH;
        assertEquals(Direction.SOUTH, dir.getOppositeDirection());
        dir = Direction.SOUTH;
        assertEquals(Direction.NORTH, dir.getOppositeDirection());
        dir = Direction.WEST;
        assertEquals(Direction.EAST, dir.getOppositeDirection());
        dir = Direction.EAST;
        assertEquals(Direction.WEST, dir.getOppositeDirection());
    }

    @Test
    public void testRight() {
        Direction dir = Direction.NORTH;
        assertEquals(Direction.EAST, dir.right());
        dir = Direction.SOUTH;
        assertEquals(Direction.WEST, dir.right());
        dir = Direction.WEST;
        assertEquals(Direction.NORTH, dir.right());
        dir = Direction.EAST;
        assertEquals(Direction.SOUTH, dir.right());
    }

    @Test
    public void testLeft() {
        Direction dir = Direction.NORTH;
        assertEquals(Direction.WEST, dir.left());
        dir = Direction.SOUTH;
        assertEquals(Direction.EAST, dir.left());
        dir = Direction.WEST;
        assertEquals(Direction.SOUTH, dir.left());
        dir = Direction.EAST;
        assertEquals(Direction.NORTH, dir.left());
    }
}
