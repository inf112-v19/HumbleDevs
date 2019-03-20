package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.skeleton.app.GameObjects.Items.*;
import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Square;
import inf112.skeleton.app.card.Action;
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
                board.getSquare(x,y).addRobot(new Player(Direction.NORTH, x, y,"testBot"));
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

    @Test
    public void insertElementByTileId() {
        // 1 = defaultTile ...
        insertElementByTileIdCreatesCorrectTile(1, new DefaultTile());
        insertElementByTileIdCreatesCorrectTile(2, new Pit());
        insertElementByTileIdCreatesCorrectTile(3, new Flag(1));
        insertElementByTileIdCreatesCorrectTile(7, new ConveyorBelt(Direction.SOUTH, 2, false));
        insertElementByTileIdCreatesCorrectTile(11, new Wall(Direction.NORTH));
        insertElementByTileIdCreatesCorrectTile(35, new RepairTool());
        insertElementByTileIdCreatesCorrectTile(41, new Gear(Action.LEFTTURN));
//        insertElementByTileIdCreatesCorrectTile(67, new Laser(Direction.NORTH, 1));


    }

    public void insertElementByTileIdCreatesCorrectTile(int tileId, IItem expectedItem) {
        board.clearSquare(0, 0);
        board.insertItem(0, 0, tileId);
        Square sq = board.getSquare(0, 0);
        assertEquals(expectedItem.getClass(), sq.getElements().get(0).getClass());
    }


}
