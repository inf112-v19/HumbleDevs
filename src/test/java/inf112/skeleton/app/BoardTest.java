package inf112.skeleton.app;

import inf112.skeleton.app.board.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BoardTest {

    @Test
    public void newBoardShouldFillWithNullValues() {
        Board<Integer> board = new Board<>(4,4);

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertNull(board.getElement(x, y));
            }
        }
    }
}
