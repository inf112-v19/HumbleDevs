package inf112.skeleton.app;

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

    /**
     * Needs to be updated if the tileSetLarge64 file is edited.
     * Follows top 1-indexing from the tileSetLarge64.png
     */
    @Test
    public void insertElementByTileId() {
        // 1 = defaultTile ...
        insertElementByTileIdCreatesCorrectItemClass(1, new DefaultTile());
        insertElementByTileIdCreatesCorrectItemClass(2, new Pit());

        insertElementByTileIdCreatesCorrectItemClass(3, new Flag(1));
        insertElementByTileIdCreatesCorrectItemClass(4, new Flag(2));
        insertElementByTileIdCreatesCorrectItemClass(5, new Flag(3));
        insertElementByTileIdCreatesCorrectItemClass(6, new Flag(4));

        insertElementByTileIdCreatesCorrectItemClass(7, new ConveyorBelt(Direction.SOUTH, 2, false));
        insertElementByTileIdCreatesCorrectItemClass(8, new ConveyorBelt(Direction.WEST, 2, false));
        insertElementByTileIdCreatesCorrectItemClass(13, new ConveyorBelt(Direction.EAST, 2, false));
        insertElementByTileIdCreatesCorrectItemClass(14, new ConveyorBelt(Direction.NORTH, 2, false));

        insertElementByTileIdCreatesCorrectItemClass(9, new ConveyorBelt(Direction.EAST, 2, true));
        insertElementByTileIdCreatesCorrectItemClass(10, new ConveyorBelt(Direction.SOUTH, 2, true));
        insertElementByTileIdCreatesCorrectItemClass(15, new ConveyorBelt(Direction.NORTH, 2, true));
        insertElementByTileIdCreatesCorrectItemClass(16, new ConveyorBelt(Direction.WEST, 2, true));

        insertElementByTileIdCreatesCorrectItemClass(19, new ConveyorBelt(Direction.SOUTH, 1, false));
        insertElementByTileIdCreatesCorrectItemClass(20, new ConveyorBelt(Direction.WEST, 1, false));
        insertElementByTileIdCreatesCorrectItemClass(25, new ConveyorBelt(Direction.EAST, 1, false));
        insertElementByTileIdCreatesCorrectItemClass(26, new ConveyorBelt(Direction.NORTH, 1, false));

        insertElementByTileIdCreatesCorrectItemClass(21, new ConveyorBelt(Direction.EAST, 1, true));
        insertElementByTileIdCreatesCorrectItemClass(22, new ConveyorBelt(Direction.SOUTH, 1, true));
        insertElementByTileIdCreatesCorrectItemClass(27, new ConveyorBelt(Direction.NORTH, 1, true));
        insertElementByTileIdCreatesCorrectItemClass(28, new ConveyorBelt(Direction.WEST, 1, true));

        insertElementByTileIdCreatesCorrectItemClass(11, new Wall(Direction.NORTH));
        insertElementByTileIdCreatesCorrectItemClass(12, new Wall(Direction.SOUTH));
        insertElementByTileIdCreatesCorrectItemClass(17, new Wall(Direction.WEST));
        insertElementByTileIdCreatesCorrectItemClass(18, new Wall(Direction.EAST));

        insertElementByTileIdCreatesCorrectItemClass(23, new Wall(Direction.WEST, Direction.NORTH));
//        insertElementByTileIdCreatesCorrectItemClass(24, new Wall(Direction.EAST, Direction.NORTH));
//        insertElementByTileIdCreatesCorrectItemClass(29, new Wall(Direction.WEST, Direction.SOUTH));
//        insertElementByTileIdCreatesCorrectItemClass(30, new Wall(Direction.EAST, Direction.SOUTH));

        insertElementByTileIdCreatesCorrectItemClass(31, new ConveyorBelt(Direction.NORTH, 2));
        insertElementByTileIdCreatesCorrectItemClass(32, new ConveyorBelt(Direction.SOUTH, 2));
        insertElementByTileIdCreatesCorrectItemClass(33, new ConveyorBelt(Direction.WEST, 2));
        insertElementByTileIdCreatesCorrectItemClass(34, new ConveyorBelt(Direction.EAST, 2));

        insertElementByTileIdCreatesCorrectItemClass(37, new ConveyorBelt(Direction.NORTH, 1));
        insertElementByTileIdCreatesCorrectItemClass(38, new ConveyorBelt(Direction.SOUTH, 1));
        insertElementByTileIdCreatesCorrectItemClass(39, new ConveyorBelt(Direction.WEST, 1));
        insertElementByTileIdCreatesCorrectItemClass(40, new ConveyorBelt(Direction.EAST, 1));

        insertElementByTileIdCreatesCorrectItemClass(35, new RepairTool());
        insertElementByTileIdCreatesCorrectItemClass(36, new RepairTool());

        insertElementByTileIdCreatesCorrectItemClass(41, new Gear(Action.LEFTTURN));
        insertElementByTileIdCreatesCorrectItemClass(42, new Gear(Action.RIGHTTURN));

//        insertElementByTileIdCreatesCorrectItemClass(67, new Laser(Direction.NORTH, 1));


    }

    public void insertElementByTileIdCreatesCorrectItemClass(int tileId, IItem expectedItem) {
        board.clearSquare(0, 0);
        board.insertItem(0, 0, tileId);
        Square sq = board.getSquare(0, 0);
        Class c2 = sq.getElements().get(0).getClass();
        assertEquals(expectedItem.getClass(), sq.getElements().get(0).getClass());
    }


}
