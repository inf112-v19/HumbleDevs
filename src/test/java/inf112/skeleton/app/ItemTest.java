package inf112.skeleton.app;

import inf112.skeleton.app.GameObjects.Items.*;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.Action;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    //Flag
    @Test
    public void TestThatWeCanMakeAFlag() {
        Flag flag = new Flag();

        assertEquals(flag.getName(), "Flag");
    }

    //Laser
    @Test
    public void TestThatWeCanMakeALaser() {
        Direction dir = Direction.NORTH;
        int damageMultiplier = 1;
        Laser laser = new Laser(dir, damageMultiplier);

        //Test to see if direction works
        assertEquals(laser.getDirection(), Direction.NORTH);
        // Test to check symbol
        assertEquals(laser.getSymbol(), '-');
        //Test to check damage
        assertEquals(damageMultiplier, 1);
        // Test name
        assertEquals(laser.getName(), "Laser");
    }

    //Hammer
    @Test
    public void TestThatWeCanMakeAHammer() {
        Hammer hammer = new Hammer();

        //Checks getName
        assertEquals(hammer.getName(), "Hammer");
        //Test getSymbol
        assertEquals(hammer.getSymbol(), '€');
    }

    //Pit
    @Test
    public void TestThatWeCanMakeAPit() {
        Pit pit = new Pit();

        //Checks getName
        assertEquals(pit.getName(), "Pit");
        //Test getSymbol
        assertEquals(pit.getSymbol(), '@');
    }

    //Wrench
    @Test
    public void TestThatWeCanMakeAWrench() {
        Wrench w = new Wrench();

        //Checks getName
        assertEquals(w.getName(), "Wrench");
        //Test getSymbol
        assertEquals(w.getSymbol(), '$');
    }

    //LaserShoot
    @Test
    public void TestthatWeCanMakeALaserShoot() {
        Direction dir = Direction.SOUTH;
        int harm = 6;

        LaserShoot lS = new LaserShoot(dir, harm);

        //Test to see if direction works
        assertEquals(lS.getDirection(), Direction.SOUTH);
        // Test to check symbol
        assertEquals(lS.getSymbol(), '>');
        //Test to check damage
        assertEquals(harm, 6);
        // Test name
        assertEquals(lS.getName(), "LaserShoot");
    }

    //CollectionBand
    @Test
    public void TestThatWeCanMakeACollectionBand() {
        int movement = 1;
        Action rotation = Action.LEFTTURN;
        Direction dir = Direction.EAST;

        CollectionBand cb = new CollectionBand(movement, rotation, dir);

        // Test to check symbol
        assertEquals(cb.getSymbol(), '_');
        //Test to check rotation
        assertEquals(rotation, Direction.EAST);
        //Test to check movement
        assertEquals(movement, 1);
        // Test name
        assertEquals(cb.getName(), "CollectionBand");
    }

    //Wall
    @Test
    public void TestThatWallIsOk() {
        Direction dir = Direction.WEST;

        Wall wall = new Wall(dir);

        //Test the direction
        assertEquals(wall.getDirection(), Direction.WEST);
        // Test name
        assertEquals(wall.getName(), "Wall");
        //Test the symbol of the wall
        assertEquals(wall.getSymbol(), '|');
    }

    //CornerWall
    @Test
    public void TestCornerWall() {
        Wall wallNorth = new Wall(Direction.NORTH);
        Wall wallEast = new Wall(Direction.EAST);

        CornerWall cw = new CornerWall(wallEast, wallNorth);

        assertEquals(wallEast.getDirection(), Direction.EAST);
        // Test name
        assertEquals(cw.getName(), "CornerWall");
        //Test the symbol of the wall
        assertEquals(cw.getSymbol(), '<');

    }

    //Gear
    @Test
    public void TestThatGearWorks() {
        Action action = Action.LEFTTURN;
        Gear gear = new Gear(action);

        // Test name
        assertEquals(gear.getName(), "Gear");
        //Test the symbol of the wall
        assertEquals(gear.getSymbol(), '°');
        //checks that getAction resolves in the right action
        assertEquals(gear.getAction(), Action.LEFTTURN);
    }
}
