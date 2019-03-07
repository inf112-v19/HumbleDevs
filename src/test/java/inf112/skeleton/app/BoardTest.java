package inf112.skeleton.app;

import inf112.skeleton.app.board.Board;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardTest {
    Board<Integer> board;

    @Before
    public void setUp() {
        board = new Board(4,4, new ArrayList<String>());
    }


    @Test
    public void boardHasCorrectDimensions() {
        for (int i = 0; i < 20; i++) {
            board = new Board(i,i, new ArrayList<String>());
            assertEquals(i, board.getHeight());
            assertEquals(i, board.getWidth());
        }
    }

    @Test
    public void isFreeReturnsTrueWhenContainingNull() {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertTrue(board.isFree(x, y));
            }
        }
    }

    @Test
    public void isFreeReturnsFalseWhenContainingAnObject() {
        for (int i = 0; i < board.getSize(); i++) {
            board.insertElement(i, i);
        }

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertFalse(board.isFree(x, y));
            }
        }
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
