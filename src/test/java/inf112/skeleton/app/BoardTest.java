package inf112.skeleton.app;

import inf112.skeleton.app.board.Board;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    Board<Integer> board;

    @Before
    public void setUp() {
        board = new Board(4,4);
    }


    @Test
    public void boardHasCorrectDimensions() {
        for (int i = 0; i < 20; i++) {
            board = new Board(i,i);
            assertEquals(i, board.getHeight());
            assertEquals(i, board.getWidth());
        }
    }

    @Test
    public void isFreeReturnsTrueWhenContainingNull() {

        assertTrue(board.isFree());


    }

    @Test
    public void insertedElementIsPlacedInCorrectPosition() {
        board.insertElement(2,2, 0);
        assertEquals((Integer) 0, board.getElement(2,2));
    }

    @Test
    public void newBoardShouldFillWithNullValues() {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertNull(board.getElement(x, y));
            }
        }
    }


}
