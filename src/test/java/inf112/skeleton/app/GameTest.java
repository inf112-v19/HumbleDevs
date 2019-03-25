package inf112.skeleton.app;

import java.util.ArrayList;

import inf112.skeleton.app.GameObjects.Items.*;
import inf112.skeleton.app.card.Action;
import org.junit.Before;
import org.junit.Test;

import inf112.skeleton.app.GameObjects.Robot;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.board.Square;
import inf112.skeleton.app.game.Game;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private Board board;
    private Robot[] robs;

    @Before
    public void init() {
        ArrayList<Square> test = new ArrayList<>(144);
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
        assertNull(board.getRobot(rob1.getPosition()));
    }

    @Test
    public void testUpdateBoard() {
        Robot rob1 = robs[0];
        game.robotMove(rob1, rob1.getDirection());
        assertEquals(new Position(2,3),rob1.getPosition());
        Robot rob2 = board.getRobot(new Position(2,3));
        assertEquals(rob1,rob2);
        Robot rob = board.getRobot(new Position(2,2));
        assertNull(rob);
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
        assertNull(board.getRobot(new Position(2,2)));
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

    @Test
    public void testMovementIntoLaser(){
        Robot rob = robs[0];
        Laser laser = new Laser(Direction.SOUTH, 1);
        Laser laser2 = new Laser(Direction.EAST, 1);
        board.insertItem(2,2,laser2);
        board.insertItem(2,3,laser);
        game.robotMove(rob,Direction.NORTH);
        assertEquals(new Position(2,2), rob.getPosition());
        game.robotMove(rob, Direction.WEST);
        assertEquals(new Position(2,2), rob.getPosition());
        game.robotMove(rob,Direction.SOUTH);
        assertEquals(new Position(2,1), rob.getPosition());
    }

    @Test
    public void testShootingLasers(){
        Robot rob = robs[0];
        Robot rob2 = robs[1];
        rob2.rotateLeft();
        game.shootLasers();
        assertEquals(1,rob.getDamageTokens());
        Wall wall = new Wall(Direction.WEST);
        board.insertItem(3,2,wall);
        game.shootLasers();
        assertEquals(1,rob.getDamageTokens());
    }
    @Test
    public void testShootingWalls(){
        Robot rob = robs[0];
        Robot rob2 = robs[1];
        rob.rotateRight();
        //Wall behind robot, should take damage
        Wall wall = new Wall(Direction.EAST);
        board.insertItem(3,2,wall);
        game.shootLasers();
        assertEquals(1,rob2.getDamageTokens());
        // Wall behind shooter on his tile, target should take damage
        Wall wall2 = new Wall(Direction.WEST);
        board.insertItem(2,2,wall2);
        game.shootLasers();
        assertEquals(2,rob2.getDamageTokens());
        // Wall in front of robot on targets tile, should not take damage
        Wall wall3 = new Wall(Direction.WEST);
        board.insertItem(3,2,wall3);
        game.shootLasers();
        assertEquals(2,rob2.getDamageTokens());
        rob2.takeDamage(7);
        game.shootLasers();
    }

    @Test
    public void testTrackLaser(){
        Object rob = game.trackLaser(Direction.EAST, new Position(0,2));
        assertEquals(rob,robs[0]);
        Wall wall = new Wall(Direction.WEST);
        board.insertItem(5,5,wall);
        Object wallHit = game.trackLaser(Direction.EAST,new Position(0,5));
        assertEquals(wall,wallHit);
        Object miss = game.trackLaser(Direction.NORTH,new Position(0,5));
        assertNull(miss);
    }

    @Test
    public void testLaser(){
        Robot rob = robs[0];
        Laser laser = new Laser(Direction.SOUTH,2);
        LaserShoot shoot = new LaserShoot(Direction.SOUTH,2);
        board.insertItem(2,5,laser);
        board.insertItem(2,5,shoot);
        board.insertItem(2,4,shoot);
        board.insertItem(2,3,shoot);
        board.insertItem(2,2,shoot);
        board.insertItem(2,1,shoot);
        board.insertItem(2,0,shoot);
        game.activatePassiveItems();
        assertEquals(2,rob.getDamageTokens());
        Wall wall = new Wall(Direction.NORTH);
        board.insertItem(2,1,wall);
        game.activatePassiveItems();
        assertEquals(4,rob.getDamageTokens());
        board.insertItem(2,2,wall);
        game.activatePassiveItems();
        assertEquals(4,rob.getDamageTokens());
    }
    @Test
    public void doubleConveyorBeltTest(){
        Robot rob = robs[0];
        ConveyorBelt cb = new ConveyorBelt(Direction.EAST,2);
        ConveyorBelt cb2 = new ConveyorBelt(Direction.SOUTH,2,true);
        ConveyorBelt cb3 = new ConveyorBelt(Direction.WEST,2, true);
        ConveyorBelt cb4 = new ConveyorBelt(Direction.WEST,2);
        board.insertItem(2,1,cb);
        board.insertItem(3,1,cb2);
        board.insertItem(3,0,cb3);
        board.insertItem(2,0,cb4);
        board.insertItem(2,0,cb4);
        game.robotMove(rob,Direction.SOUTH);
        game.activateMovement();
        assertEquals(new Position(3,0),rob.getPosition());
        game.activateMovement();
        assertEquals(new Position(1,0),rob.getPosition());
    }

    @Test
    public void singleConveyorBeltTest(){
        Robot rob = robs[0];
        ConveyorBelt cb = new ConveyorBelt(Direction.SOUTH,1);
        ConveyorBelt cb2 = new ConveyorBelt(Direction.WEST,1);
        ConveyorBelt cb3 = new ConveyorBelt(Direction.NORTH,1);
        board.insertItem(2,1,cb);
        board.insertItem(2,0,cb2);
        board.insertItem(1,0,cb3);
        game.robotMove(rob,Direction.SOUTH);
        game.activateMovement();
        assertEquals(new Position(2,0), rob.getPosition());
        game.activateMovement();
        assertEquals(new Position(1,0), rob.getPosition());
        game.activateMovement();
        assertEquals(new Position(1,1), rob.getPosition());
    }

    @Test
    public void testGear(){
        Robot rob = robs[0];
        Direction dir = rob.getDirection();
        Gear gear = new Gear(Action.LEFTTURN);
        board.insertItem(2,2,gear);
        game.activateMovement();
        assertEquals(dir.left(), rob.getDirection());
        Gear gear1 = new Gear(Action.RIGHTTURN);
        rob.move(Direction.NORTH);
        board.insertItem(2,3,gear1);
        game.activateMovement();
        assertEquals(dir,rob.getDirection());
    }
    @Test
    public void testTouchFlag(){
        Robot rob = robs[0];
        Flag flag = new Flag(1);
        board.insertItem(2,2,flag);
        game.repairAndCheckFlags();
        assertEquals(1,rob.visitedFlags());
        Flag flag1 = new Flag(3);
        game.robotMove(rob,Direction.NORTH);
        board.insertItem(2,3,flag1);
        game.repairAndCheckFlags();
        assertEquals(1,rob.visitedFlags());
    }
    @Test
    public void testLimitedCards(){
        Robot rob = robs[0];
        rob.takeDamage(4);
        game.startRound();
        assertEquals(5,rob.getCards().length);
    }
    @Test
    public void testSpecialCase1ConveyorBelt(){
        ConveyorBelt cb = new ConveyorBelt(Direction.WEST,1);
        board.insertItem(3,2,cb);
        Robot rob1 = robs[0];
        Robot rob2 = robs[1];
        game.activateMovement();
        assertEquals(new Position(2,2),rob1.getPosition());
        assertEquals(new Position(3,2),rob2.getPosition());
    }
    @Test
    public void testConveyorBeltTurn(){
        ConveyorBelt cb = new ConveyorBelt(Direction.NORTH, 1);
        ConveyorBelt cb1 = new ConveyorBelt(Direction.EAST,1,true);
        Robot rob1 = robs[0];
        board.insertItem(2,2,cb);
        board.insertItem(2,3,cb1);
        game.activateMovement();
        assertEquals(new Position(2,3), rob1.getPosition());
        assertEquals(Direction.EAST,rob1.getDirection());
    }
    @Test
    public void testSpecialCase2ConveyorBelt(){
        ConveyorBelt cb1 = new ConveyorBelt(Direction.EAST,1,true);
        board.insertItem(2,3,cb1);
        Robot rob = robs[0];
        game.robotMove(rob, Direction.NORTH);
        assertEquals(Direction.NORTH, rob.getDirection());
    }
    @Test
    public void testSpecialCase3ConveyorBelt(){
        ConveyorBelt cb1 = new ConveyorBelt(Direction.NORTH,1);
        Robot rob1 = robs[0];
        Robot rob2 = robs[1];
        game.robotMove(rob2,Direction.NORTH);
        game.robotMove(rob2,Direction.WEST);
        assertEquals(new Position(2,2), rob1.getPosition());
        assertEquals(new Position(2,3), rob2.getPosition());
        board.insertItem(2,2,cb1);
        board.insertItem(2,3,cb1);
        game.activateMovement();
        //assertEquals(new Position(2,3), rob1.getPosition());
        //assertEquals(new Position(2,4), rob2.getPosition());
    }
}