package inf112.skeleton.app;

import inf112.skeleton.app.gameObjects.Items.*;
import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.board.Square;
import inf112.skeleton.app.card.Action;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
                board.getSquare(x,y).addRobot(new Player(Direction.NORTH, x, y,"testBot","testing"));
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
     * Follows top row-based 1-indexing from the tileSetLarge64.png
     */
    @Test
    public void insertElementByTileId() {
        // 1 = defaultTile ...
        insertElementByTileIdCreatesCorrectItem(1, new DefaultTile());
        insertElementByTileIdCreatesCorrectItem(2, new Pit());

        insertElementByTileIdCreatesCorrectItem(3, new Flag(1));
        insertElementByTileIdCreatesCorrectItem(4, new Flag(2));
        insertElementByTileIdCreatesCorrectItem(5, new Flag(3));
        insertElementByTileIdCreatesCorrectItem(6, new Flag(4));

        insertElementByTileIdCreatesCorrectItem(7, new ConveyorBelt(Direction.SOUTH, 2, false));
        insertElementByTileIdCreatesCorrectItem(8, new ConveyorBelt(Direction.WEST, 2, false));
        insertElementByTileIdCreatesCorrectItem(13, new ConveyorBelt(Direction.EAST, 2, false));
        insertElementByTileIdCreatesCorrectItem(14, new ConveyorBelt(Direction.NORTH, 2, false));

        insertElementByTileIdCreatesCorrectItem(9, new ConveyorBelt(Direction.EAST, 2, true));
        insertElementByTileIdCreatesCorrectItem(10, new ConveyorBelt(Direction.SOUTH, 2, true));
        insertElementByTileIdCreatesCorrectItem(15, new ConveyorBelt(Direction.NORTH, 2, true));
        insertElementByTileIdCreatesCorrectItem(16, new ConveyorBelt(Direction.WEST, 2, true));

        insertElementByTileIdCreatesCorrectItem(19, new ConveyorBelt(Direction.SOUTH, 1, false));
        insertElementByTileIdCreatesCorrectItem(20, new ConveyorBelt(Direction.WEST, 1, false));
        insertElementByTileIdCreatesCorrectItem(25, new ConveyorBelt(Direction.EAST, 1, false));
        insertElementByTileIdCreatesCorrectItem(26, new ConveyorBelt(Direction.NORTH, 1, false));

        insertElementByTileIdCreatesCorrectItem(21, new ConveyorBelt(Direction.EAST, 1, true));
        insertElementByTileIdCreatesCorrectItem(22, new ConveyorBelt(Direction.SOUTH, 1, true));
        insertElementByTileIdCreatesCorrectItem(27, new ConveyorBelt(Direction.NORTH, 1, true));
        insertElementByTileIdCreatesCorrectItem(28, new ConveyorBelt(Direction.WEST, 1, true));

        insertElementByTileIdCreatesCorrectItem(11, new Wall(Direction.NORTH));
        insertElementByTileIdCreatesCorrectItem(12, new Wall(Direction.SOUTH));
        insertElementByTileIdCreatesCorrectItem(17, new Wall(Direction.WEST));
        insertElementByTileIdCreatesCorrectItem(18, new Wall(Direction.EAST));

        insertElementByTileIdCreatesCorrectItem(23, new Wall(Direction.WEST, Direction.NORTH));
        insertElementByTileIdCreatesCorrectItem(24, new Wall(Direction.EAST, Direction.NORTH));
        insertElementByTileIdCreatesCorrectItem(29, new Wall(Direction.WEST, Direction.SOUTH));
        insertElementByTileIdCreatesCorrectItem(30, new Wall(Direction.EAST, Direction.SOUTH));

        insertElementByTileIdCreatesCorrectItem(31, new ConveyorBelt(Direction.NORTH, 2));
        insertElementByTileIdCreatesCorrectItem(32, new ConveyorBelt(Direction.SOUTH, 2));
        insertElementByTileIdCreatesCorrectItem(33, new ConveyorBelt(Direction.WEST, 2));
        insertElementByTileIdCreatesCorrectItem(34, new ConveyorBelt(Direction.EAST, 2));

        insertElementByTileIdCreatesCorrectItem(37, new ConveyorBelt(Direction.NORTH, 1));
        insertElementByTileIdCreatesCorrectItem(38, new ConveyorBelt(Direction.SOUTH, 1));
        insertElementByTileIdCreatesCorrectItem(39, new ConveyorBelt(Direction.WEST, 1));
        insertElementByTileIdCreatesCorrectItem(40, new ConveyorBelt(Direction.EAST, 1));

        insertElementByTileIdCreatesCorrectItem(35, new RepairTool(true));
        insertElementByTileIdCreatesCorrectItem(36, new RepairTool(false));

        insertElementByTileIdCreatesCorrectItem(41, new Gear(Action.LEFTTURN));
        insertElementByTileIdCreatesCorrectItem(42, new Gear(Action.RIGHTTURN));

        insertElementByTileIdCreatesCorrectItem(67, new Laser(Direction.NORTH, 1));
        insertElementByTileIdCreatesCorrectItem(68, new Laser(Direction.WEST, 1));

        insertElementByTileIdCreatesCorrectItem(69, new Laser(Direction.NORTH, 1));
        insertElementByTileIdCreatesCorrectItem(70, new Laser(Direction.EAST, 1));
        insertElementByTileIdCreatesCorrectItem(71, new Laser(Direction.SOUTH, 1));
        insertElementByTileIdCreatesCorrectItem(72, new Laser(Direction.WEST, 1));

        insertElementByTileIdCreatesCorrectItem(73, new Laser(Direction.NORTH, 2));
        insertElementByTileIdCreatesCorrectItem(74, new Laser(Direction.WEST, 2));

        insertElementByTileIdCreatesCorrectItem(75, new Laser(Direction.NORTH, 2));
        insertElementByTileIdCreatesCorrectItem(76, new Laser(Direction.EAST, 2));
        insertElementByTileIdCreatesCorrectItem(77, new Laser(Direction.SOUTH, 2));
        insertElementByTileIdCreatesCorrectItem(78, new Laser(Direction.WEST, 2));

        insertElementByTileIdCreatesCorrectItem(85, new Dock(1));
        insertElementByTileIdCreatesCorrectItem(86, new Dock(2));
        insertElementByTileIdCreatesCorrectItem(87, new Dock(3));
        insertElementByTileIdCreatesCorrectItem(88, new Dock(4));
        insertElementByTileIdCreatesCorrectItem(89, new Dock(5));
        insertElementByTileIdCreatesCorrectItem(90, new Dock(6));
        insertElementByTileIdCreatesCorrectItem(91, new Dock(7));
        insertElementByTileIdCreatesCorrectItem(92, new Dock(8));
    }

    public void insertElementByTileIdCreatesCorrectItem(int tileId, IItem expectedItem) {
        board.clearSquare(0, 0);
        board.insertItem(0, 0, tileId);
        Square sq = board.getSquare(0, 0);
        IItem actualItem = (IItem) sq.getElements().get(0);
        assertEquals(expectedItem.getClass(), actualItem.getClass());

        assertEquals(expectedItem.getName(), actualItem.getName());

        if (actualItem instanceof Flag) flagHasCorrectProperties((Flag) expectedItem, (Flag) actualItem);
        if (actualItem instanceof ConveyorBelt) conveyorBeltHasCorrectProperties((ConveyorBelt) expectedItem, (ConveyorBelt) actualItem);
        if (actualItem instanceof Wall) wallHasCorrectProperties((Wall) expectedItem, (Wall) actualItem);
        if (actualItem instanceof Gear) gearHasCorrectProperties((Gear) expectedItem, (Gear) actualItem);
        if (actualItem instanceof Laser) laserHasCorrectProperties((Laser) expectedItem, (Laser) actualItem);
        if (actualItem instanceof RepairTool) repairToolHasCorrectProperties((RepairTool) expectedItem, (RepairTool) actualItem);
        if (actualItem instanceof Dock) dockHasCorrectProperties((Dock) expectedItem, (Dock) actualItem);
    }

    private void dockHasCorrectProperties(Dock expectedItem, Dock actualItem) {
        assertEquals(expectedItem.getNumber(), expectedItem.getNumber());
    }

    private void repairToolHasCorrectProperties(RepairTool expectedItem, RepairTool actualItem) {
        assertEquals(expectedItem.wrenchAndHammer(), actualItem.wrenchAndHammer());
    }

    public void flagHasCorrectProperties(Flag expected, Flag actual) {
        assertEquals(expected.getFlagNum(), actual.getFlagNum());
    }

    public void conveyorBeltHasCorrectProperties(ConveyorBelt expected, ConveyorBelt actual) {
        assertEquals(expected.getDirection(), actual.getDirection());
        assertEquals(expected.getSpeed(), actual.getSpeed());
        assertEquals(expected.getRotation(), actual.getRotation());
    }

    public void wallHasCorrectProperties(Wall expected, Wall actual) {
        assertEquals(expected.getDir(), actual.getDir());
        assertEquals(expected.getDir2(), actual.getDir2());
    }

    public void gearHasCorrectProperties(Gear expected, Gear actual) {
        assertEquals(expected.getAction(), actual.getAction());
    }

    public void laserHasCorrectProperties(Laser expected, Laser actual) {
        assertEquals(expected.getDirection(), actual.getDirection());
        assertEquals(expected.getDamageMultiplier(), actual.getDamageMultiplier());
    }

    @Test
    public void testIndexCalculations(){
        int index1 = board.toIndex(0,0);
        assertEquals(0,index1);
        int index2 = board.toIndex(0,1);
        assertEquals(4,index2);
        Position pos = board.getPositionFromIndex(index2);
        assertEquals(new Position(0,1),pos);
        Position pos1 = board.getPositionFromIndex(12);
        assertEquals(new Position(0,3),pos1);
    }

    @Test
    public void testDockPositions(){
        for (int x = 0; x < 8; x++){
            Dock dock = new Dock(x+1);
            board.insertItem(board.getPositionFromIndex(x), dock);
        }
        ArrayList<Position> startDocks = board.getDockPositions();
        for(int y = 0; y < 8; y++){
            assertEquals(board.getPositionFromIndex(y), startDocks.get(y));
        }
    }
}
