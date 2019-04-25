package inf112.skeleton.app;

import java.util.ArrayList;

import inf112.skeleton.app.gameObjects.Items.*;
import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.card.Action;
import inf112.skeleton.app.card.ProgramCard;
import org.junit.Before;
import org.junit.Test;

import inf112.skeleton.app.gameObjects.Robot;
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
        this.robs = new Robot[3];
        for(int y = 0; y < 3; y++){
            Player player = new Player(0, Direction.NORTH, 2+y,2, "Robot" + y, "dd");
            robs[y] = player;
            board.insertRobot(new Position(2+y,2), player);
        }
        this.game = new Game(board, robs);
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
    public void testMovingOutsideBoard(){
        Robot rob = robs[0];
        game.robotMove(rob,Direction.SOUTH,false);
        game.robotMove(rob,Direction.SOUTH,false);
        game.robotMove(rob,Direction.SOUTH,false);
        assertTrue(rob.isDestroyed());
        Robot rob1 = robs[2];
        game.robotMove(rob1, Direction.EAST,false);
        game.robotMove(rob1, Direction.EAST,false);
        game.robotMove(rob1, Direction.EAST,false);
        game.robotMove(rob1, Direction.EAST,false);
        game.robotMove(rob1, Direction.EAST,false);
        game.robotMove(rob1, Direction.EAST,false);
        game.robotMove(rob1, Direction.EAST,false);
        game.robotMove(rob1, Direction.EAST,false);
        game.robotMove(rob1, Direction.EAST,false);
        assertTrue(rob1.isDestroyed());
    }

    /**
     * If a robot has over 4 damage tokens, then his registers should start to lock. The method findPriority(),
     * that finds in which order the robots should do their turn, should only return the index of robots that doesn't
     * have their register i locked.
     */
    @Test
    public void testPriorityWhenRegistersLock(){
        Robot rob = robs[0];
        rob.takeDamage(9);
        game.startRound();
        int[] pri = game.findPriority(3);
        assertEquals(2,pri.length);
    }
    @Test
    public void testMovement() {
        Robot rob1 = robs[0];
        Position pos = rob1.getPosition();
        assertEquals(new Position(2,2), pos);
        game.robotMove(rob1, Direction.NORTH,false);
        assertEquals(new Position(2,3),pos);
    }
    /**
     * Checks if the robot is destroyed and removed from the board when it walks into a pit.
     */
    @Test
    public void testPit() {
        Robot rob1 = robs[0];
        game.robotMove(rob1, Direction.WEST,false);
        assertTrue(rob1.isDestroyed());
        assertNull(board.getRobot(rob1.getPosition()));
    }

    @Test
    public void testUpdateBoard() {
        Robot rob1 = robs[0];
        game.robotMove(rob1, rob1.getDirection(),false);
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
        game.robotMove(rob1, Direction.EAST,false);
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
        game.robotMove(rob1, Direction.WEST,false);
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
        game.robotMove(rob,Direction.SOUTH,false);
        game.robotMove(rob,Direction.SOUTH,false);
        assertEquals(new Position(2,1),rob.getPosition());
        Wall wall2 = new Wall(Direction.EAST);
        Wall wall3 = new Wall(Direction.WEST);
        board.insertItem(2,1,wall2);
        board.insertItem(3,2,wall3);
        game.robotMove(rob,Direction.EAST,false);
        assertEquals(new Position(2,1),rob.getPosition());
    }
    @Test
    public void testMovementIntoCornerWall(){
        Robot rob = robs[0];
        Wall cornerWall = new Wall(Direction.WEST, Direction.SOUTH);
        board.insertItem(2,1,cornerWall);
        game.robotMove(rob,Direction.SOUTH,false);
        game.robotMove(rob,Direction.SOUTH,false);
        game.robotMove(rob,Direction.WEST,false);
        assertEquals(new Position(2,1),rob.getPosition());
    }

    @Test
    public void testShootingLasers(){
        Robot rob = robs[0];
        Robot rob2 = robs[1];
        rob2.rotateLeft();
        game.robotShootLasers();
        assertEquals(1,rob.getDamageTokens());
        Wall wall = new Wall(Direction.WEST);
        board.insertItem(3,2,wall);
        game.robotShootLasers();
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
        game.robotShootLasers();
        assertEquals(1,rob2.getDamageTokens());
        // Wall behind shooter on his tile, target should take damage
        Wall wall2 = new Wall(Direction.WEST);
        board.insertItem(2,2,wall2);
        game.robotShootLasers();
        assertEquals(2,rob2.getDamageTokens());
        // Wall in front of robot on targets tile, should not take damage
        Wall wall3 = new Wall(Direction.WEST);
        board.insertItem(3,2,wall3);
        game.robotShootLasers();
        assertEquals(2,rob2.getDamageTokens());
        rob2.takeDamage(7);
        game.robotShootLasers();
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
        game.robotMove(rob,Direction.SOUTH,false);
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
        game.robotMove(rob,Direction.SOUTH,false);
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
    /**
     * Tests if the flags work properly. A flag should only be registered if the order is correct. The robot should therefore
     * register the first flag, but not the third flag when the second flag is skipped.
     */
    @Test
    public void testTouchFlag(){
        Robot rob = robs[0];
        Flag flag = new Flag(1);
        board.insertItem(2,2,flag);
        game.repairAndCheckFlags();
        assertEquals(1,rob.visitedFlags());
        game.repairAndCheckFlags();
        assertEquals(1,rob.visitedFlags());
        Flag flag1 = new Flag(3);
        game.robotMove(rob,Direction.NORTH,false);
        board.insertItem(2,3,flag1);
        game.repairAndCheckFlags();
        assertEquals(1,rob.visitedFlags());
    }
    @Test
    public void testRobotWon(){
        Robot rob = robs[0];
        for(int x = 0; x < 4; x++){
            rob.visitFlag(new Flag(x+1));
        }
        assertEquals(rob,game.finished());
    }
    @Test
    public void testLimitedCards(){
        Robot rob = robs[0];
        rob.takeDamage(4);
        game.startRound();
        assertEquals(5,rob.getCards().length);
        rob.takeDamage(4);
        game.startRound();
        assertNull(rob.getCards()[4]);
        assertNull(rob.getCards()[3]);
        assertNull(rob.getCards()[2]);
    }

    /**
     * If a robot is blocking the exit of a conveyor belt, then the exiting robot should not move when the
     * conveyor belt is activated
     */
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
    /**
     * A robot should not be rotated when it moves onto a turning conveyor belt. It should keep its rotation and be moved that way.
     * If there is another turning conveyor belt, the robot should only rotate 90 degrees.
     */
    @Test
    public void testSpecialCase2ConveyorBelt(){
        ConveyorBelt cb1 = new ConveyorBelt(Direction.EAST,1,true);
        ConveyorBelt cb2 = new ConveyorBelt(Direction.EAST,1);
        ConveyorBelt cb3 = new ConveyorBelt(Direction.SOUTH,1,true);
        board.insertItem(2,3,cb1);
        Robot rob = robs[0];
        game.robotMove(rob, Direction.NORTH,false);
        assertEquals(Direction.NORTH, rob.getDirection());
        game.activateMovement();
        assertEquals(Direction.NORTH,rob.getDirection());
        board.insertItem(3,3,cb2);
        board.insertItem(4,3,cb3);
        game.activateMovement();
        assertEquals(Direction.EAST,rob.getDirection());
    }

    /**
     * The conveyorBelt should move the robots simultaneously
     */
    @Test
    public void testConveyorBeltQueue(){
        ConveyorBelt cb1 = new ConveyorBelt(Direction.EAST,1);
        board.insertItem(2,2,cb1);
        board.insertItem(3,2,cb1);
        board.insertItem(4,2,cb1);
        game.activateMovement();
        assertEquals(new Position(3,2),robs[0].getPosition());
        assertEquals(new Position(4,2),robs[1].getPosition());
        assertEquals(new Position(5,2),robs[2].getPosition());
    }

    /**
     * The conveyor belt should not move the robots if there is a robot at the end blocking the exit
     */
    @Test
    public void testSpecialCase3ConveyorBelt(){
        ConveyorBelt cb1 = new ConveyorBelt(Direction.EAST,1);
        board.insertItem(2,2,cb1);
        board.insertItem(3,2,cb1);
        game.activateMovement();
        assertEquals(new Position(2,2),robs[0].getPosition());
        assertEquals(new Position(3,2),robs[1].getPosition());
        assertEquals(new Position(4,2),robs[2].getPosition());
    }

    @Test
    public void testOnePhase(){
        game.startRound();
        Robot rob1 = robs[0];
        Robot rob2 = robs[1];
        Robot rob3 = robs[2];
        ProgramCard[] cards = rob1.getCards();
        cards[0] = new ProgramCard(2,100,Action.MOVEFORWARD);
        cards = rob2.getCards();
        cards[0] = new ProgramCard(2,110,Action.MOVEFORWARD);
        cards = rob3.getCards();
        cards[0] = new ProgramCard(2,120,Action.MOVEFORWARD);
        game.phase(0);
        assertEquals(new Position(2,4),rob1.getPosition());
    }
    @Test
    public void testPhasePriority(){
        game.startRound();
        Robot rob1 = robs[0];
        Robot rob2 = robs[1];
        Robot rob3 = robs[2];
        rob1.rotateRight();
        ProgramCard[] cards = rob1.getCards();
        cards[0] = new ProgramCard(2,140,Action.MOVEFORWARD);
        cards = rob2.getCards();
        cards[0] = new ProgramCard(2,110,Action.MOVEFORWARD);
        cards = rob3.getCards();
        cards[0] = new ProgramCard(2,120,Action.MOVEFORWARD);
        game.phase(0);
        assertEquals(new Position(4,2),rob1.getPosition());
        assertEquals(new Position(5,4), rob2.getPosition());
        assertEquals(new Position(6,4),rob3.getPosition());
    }

    @Test
    public void testWhenRobotIsDestroyed(){
        Robot rob = robs[0];
        rob.rotateRight();
        rob.die();
        game.robotShootLasers();
        assertEquals(0,robs[1].getDamageTokens());
    }

    @Test
    public void testConveyorBeltInWall(){
        ConveyorBelt cb = new ConveyorBelt(Direction.NORTH,1);
        board.insertItem(2,2,cb);
        Wall wall = new Wall(Direction.NORTH);
        board.insertItem(2,2,wall);
        game.activateMovement();
        assertEquals(new Position(2,2),robs[0].getPosition());
    }
    @Test
    public void testConveyorBeltInPit(){
        ConveyorBelt cb = new ConveyorBelt(Direction.WEST,1);
        board.insertItem(2,2,cb);
        game.activateMovement();
        assertTrue(robs[0].isDestroyed());
    }
    @Test
    public void leftTurningConveyorBelt(){
        ConveyorBelt cb = new ConveyorBelt(Direction.NORTH, 2);
        ConveyorBelt cb1 = new ConveyorBelt(Direction.WEST,1,false);
        board.insertItem(2,2,cb);
        board.insertItem(2,3,cb1);
        game.activateMovement();
        assertEquals(new Position(1,3),robs[0].getPosition());
        assertEquals(Direction.WEST,robs[0].getDirection());
    }
    @Test
    public void destroyRobotWithLaser(){
        Robot rob = robs[0];
        Robot rob1 = robs[1];
        rob.rotateRight();
        rob1.takeDamage(9);
        game.robotShootLasers();
        assertTrue(rob1.isDestroyed());
        Laser laser = new Laser(Direction.SOUTH,1);
        board.insertItem(2,2,laser);
        LaserShoot ls = new LaserShoot(Direction.SOUTH,1);
        board.insertItem(2,2,ls);
        game.activatePassiveItems();
        rob.takeDamage(9);
        assertTrue(rob.isDestroyed());
    }
    @Test
    public void testRepairTool(){
        Robot rob = robs[0];
        rob.takeDamage(5);
        RepairTool rt = new RepairTool(false);
        board.insertItem(2,3,rt);
        game.robotMove(rob,Direction.NORTH,false);
        game.repairAndCheckFlags();
        assertEquals(4,rob.getDamageTokens());
        rob.die();
        game.respawnRobots();
        assertEquals(new Position(2,3),rob.getPosition());
    }
    @Test
    public void testProgramCards(){
        Robot rob = robs[0];
        ProgramCard back1 = new ProgramCard(1,100,Action.MOVEBACKWARD);
        ProgramCard leftTurn = new ProgramCard(0,110,Action.LEFTTURN);
        ProgramCard rightTurn = new ProgramCard(0,110,Action.RIGHTTURN);
        ProgramCard uTurn = new ProgramCard(0,110,Action.UTURN);
        ProgramCard move1 = new ProgramCard(1,120,Action.MOVEFORWARD);
        ProgramCard move2 = new ProgramCard(2,120,Action.MOVEFORWARD);
        ProgramCard move3 = new ProgramCard(3,120,Action.MOVEFORWARD);
        ProgramCard[] cards = {back1, move1,move2,move3,leftTurn, rightTurn, uTurn};
        rob.setCards(cards);

        // Move backward 1
        game.robotDoTurn(rob,0);
        assertEquals(new Position(2,1),rob.getPosition());

        // Move forward 1
        game.robotDoTurn(rob,1);
        assertEquals(new Position(2,2),rob.getPosition());

        // Move forward 2
        game.robotDoTurn(rob,2);
        assertEquals(new Position(2,4),rob.getPosition());

        // Move forward 3
        game.robotDoTurn(rob,3);
        assertEquals(new Position(2,7),rob.getPosition());

        // Rotate left
        game.robotDoTurn(rob,4);
        assertEquals(Direction.WEST, rob.getDirection());

        // Rotate right
        game.robotDoTurn(rob,5);
        assertEquals(Direction.NORTH, rob.getDirection());

        // U - turn
        game.robotDoTurn(rob,6);
        assertEquals(Direction.SOUTH, rob.getDirection());
    }
    @Test
    public void testProgramCardIntoObjects(){
        Robot rob = robs[0];
        ProgramCard move2 = new ProgramCard(2,100,Action.MOVEFORWARD);
        ProgramCard back = new ProgramCard(1,100,Action.MOVEBACKWARD);
        ProgramCard back3 = new ProgramCard(3,100,Action.MOVEBACKWARD);
        ProgramCard[] cards = {move2,back,back3};
        rob.setCards(cards);
        rob.rotateLeft();
        game.robotDoTurn(rob,0);
        assertTrue(rob.isDestroyed());
        game.robotDoTurn(rob,1);
        assertEquals(new Position(2,2),rob.getPosition());

        Robot rob1 = robs[1];
        Wall wall = new Wall(Direction.NORTH);
        board.insertItem(3,2,wall);
        rob1.setCards(cards);
        game.robotDoTurn(rob1,0);
        assertEquals(new Position(3,2),rob1.getPosition());
        rob1.rotateLeft();
        rob1.rotateLeft();
        game.robotDoTurn(rob1,2);
        assertEquals(new Position(3,2),rob1.getPosition());

        rob1.rotateLeft();
        game.robotDoTurn(rob1,2);
        assertTrue(rob.isDestroyed());
    }

    @Test
    public void testQueueIntoWall(){
        Robot rob = robs[0];
        Wall wall = new Wall(Direction.EAST);
        board.insertItem(3,2,wall);
        game.robotMove(rob,Direction.EAST,false);
        assertEquals(new Position(2,2), rob.getPosition());
    }
    @Test
    public void testDoubleConveyorBeltButItEndsAtFirstStep(){
        Robot rob = robs[0];
        ConveyorBelt cb = new ConveyorBelt(Direction.SOUTH,2);
        board.insertItem(2,2,cb);
        game.activateMovement();
        assertEquals(new Position(2,1),rob.getPosition());
    }
    @Test
    public void conveyorBeltOutOfTheMap(){
        Robot rob = robs[0];
        ConveyorBelt cb = new ConveyorBelt(Direction.SOUTH,2);
        board.insertItem(2,2,cb);
        board.insertItem(2,1,cb);
        board.insertItem(2,0,cb);
        game.activateMovement();
        game.activateMovement();
        assertTrue(rob.isDestroyed());
    }
    @Test
    public void testConveyorBeltIntoCornerWalls(){
        Wall wall = new Wall(Direction.EAST,Direction.NORTH);
        ConveyorBelt cb = new ConveyorBelt(Direction.WEST,1);
        board.insertItem(2,3,wall);
        board.insertItem(2,3,cb);
        game.robotMove(robs[0],Direction.NORTH,false);
        game.activateMovement();
        assertEquals(new Position(1,3), robs[0].getPosition());
    }
    @Test
    public void walkIntoCornerWalls(){
        Wall wall = new Wall(Direction.NORTH,Direction.WEST);
        Robot rob = robs[0];
        board.insertItem(3,2,wall);
        game.robotMove(rob,Direction.EAST,false);
        assertEquals(new Position(2,2), rob.getPosition());
    }
}