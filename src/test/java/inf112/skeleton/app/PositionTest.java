package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import inf112.skeleton.app.board.Position;

public class PositionTest {

    @Test
    public void testEquals() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(2, 2);
        pos1.moveEast();
        assertEquals(pos1, pos2);
    }

}
