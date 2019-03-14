package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

import inf112.skeleton.app.GameObjects.Items.Wall;
import org.junit.Before;
import org.junit.Test;

import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.GameObjects.Robot;
import inf112.skeleton.app.GameObjects.Items.IItem;
import inf112.skeleton.app.GameObjects.Items.Pit;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.IBoard;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.board.Square;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.game.Game;

public class GameTest {
    private Game game;
    private Board board;
    private Robot[] robs;

    @Before
    public void init() {
        ArrayList<Square> test = new ArrayList<Square>(144);
        for(int x = 0; x < 144; x++) {
            test.add(new Square<IItem>());
        }
        Board board = new Board(12, 12,test);
        this.board = board;
        board.insertItem(new Position(1,2), new Pit());
        Game game = new Game(board,3);
        this.game = game;
        this.robs = game.getRobots();
    }

    @Test
    public void testFindPriority() {
        game.startRound();
        assertEquals(5,robs[0].getCards().length);
        int[] pri = game.findPriority(1);
        Robot rob1 = robs[pri[0]];
        Robot rob2 = robs[pri[1]];
        Robot rob3 = robs[pri[2]];
        assertTrue(rob1.getCards()[1].getPriority() <= rob2.getCards()[1].getPriority());
        assertTrue(rob2.getCards()[1].getPriority() <= rob3.getCards()[1].getPriority());
    }

    @Test
    public void testMovement() {
        Robot rob1 = robs[0];
        Position pos = rob1.getPosition();
        assertEquals(new Position(2,2), pos);
        game.robotMove(rob1, Direction.NORTH);
        assertEquals(new Position(2,3),pos);
    }

    @Test
    public void testPit() {
        Robot rob1 = robs[0];
        game.robotMove(rob1, Direction.WEST);
        assertTrue(rob1.isDestroyed());
        assertEquals(null,board.getRobot(rob1.getPosition()));
    }

    @Test
    public void testUpdateBoard() {
        Robot rob1 = robs[0];
        game.robotMove(rob1, rob1.getDirection());
        assertEquals(new Position(2,3),rob1.getPosition());
        Robot rob2 = board.getRobot(new Position(2,3));
        assertEquals(rob1,rob2);
        Robot rob = board.getRobot(new Position(2,2));
        assertEquals(null,rob);
    }

    @Test
    public void testBoardMovement() {
        Robot rob1 = robs[0];
        Robot rob2 = robs[1];
        Robot rob3 = robs[2];
        game.robotMove(rob1, Direction.EAST);
        assertEquals(new Position(3,2),rob1.getPosition());
        assertEquals(new Position(4,2),rob2.getPosition());
        assertEquals(new Position(5,2),rob3.getPosition());
        assertEquals(board.getRobot(new Position(2,2)), null);
        assertEquals(board.getRobot(new Position(3,2)), rob1);
        assertEquals(board.getRobot(new Position(4,2)), rob2);
        assertEquals(board.getRobot(new Position(5,2)), rob3);
    }
    @Test
    public void respawnTest() {
        Robot rob1 = robs[0];
        game.robotMove(rob1, Direction.WEST);
        assertTrue(rob1.isDestroyed());
        game.respawnRobots();
        assertEquals(new Position(2,2), rob1.getPosition());
        assertEquals(rob1, board.getRobot(new Position(2,2)));
    }

    @Test
    public void testMovementIntoSingleWall(){
        Robot rob = robs[0];
        Wall wall = new Wall(Direction.NORTH);
        board.insertItem(2,0,wall);
        game.robotMove(rob,Direction.SOUTH);
        game.robotMove(rob,Direction.SOUTH);
        assertEquals(new Position(2,1),rob.getPosition());
        Wall wall2 = new Wall(Direction.EAST);
        Wall wall3 = new Wall(Direction.WEST);
        board.insertItem(2,1,wall2);
        board.insertItem(3,2,wall3);
        game.robotMove(rob,Direction.EAST);
        assertEquals(new Position(2,1),rob.getPosition());
    }
    @Test
    public void testMovementIntoCornerWall(){
        Robot rob = robs[0];
        Wall cornerWall = new Wall(Direction.WEST, Direction.SOUTH);
        board.insertItem(2,1,cornerWall);
        game.robotMove(rob,Direction.SOUTH);
        game.robotMove(rob,Direction.SOUTH);
        game.robotMove(rob,Direction.WEST);
        assertEquals(new Position(2,1),rob.getPosition());
    }
}