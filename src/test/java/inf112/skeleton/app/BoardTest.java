package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.skeleton.app.GameObjects.Items.DefaultTile;
import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Square;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    Board board;

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
    public void isFreeReturnsTrueWhenContainingNoRobot() {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertTrue(board.isFree(x, y));
            }
        }
    }

    @Test
    public void isFreeReturnsFalseWhenContainingRobot() {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                board.getSquare(x,y).addRobot(new Player(Direction.NORTH, x, y, 1, "testBot"));
            }
        }

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertFalse(board.isFree(x, y));
            }
        }
    }

    @Test
    public void insertedElementIsPlacedInCorrectPosition() {
        DefaultTile tile = new DefaultTile();
        board.insertItem(2,2, tile);
        assertTrue(board.getItems(2,2).contains(tile));
    }

    @Test
    public void newBoardShouldFillWithEmptySquares() {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertTrue(board.getItems(x, y).isEmpty());
            }
        }
    }

    /**
     * TODO:
     * Make this test work
     * It seems impossible to load the TiledMap from here.
     * Neither is it possible to get the TiledMap form the graphic-class.
     * The instance variable tiledMap is returned as null.
     */
    @Test
    public void itemFactoryCreatesDefaultTile() {

        TiledMap tiledMap = new TmxMapLoader().load("assets/maps/layeredTestMap.tmx");
        board = new Board(tiledMap);
        Square sq = board.getSquare(0, 0);
        assertTrue(sq.getListOfItems().get(0) instanceof DefaultTile);
    }

}
