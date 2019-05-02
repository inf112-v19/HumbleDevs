package inf112.skeleton.app;

import inf112.skeleton.app.gameObjects.Items.*;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.Action;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    //Flag
    @Test
    public void TestThatWeCanMakeAFlag() {
        Flag flag = new Flag(1);

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
        //Test to check damage
        assertEquals(damageMultiplier, 1);
        // Test name
        assertEquals(laser.getName(), "Laser");
    }

    //Pit
    @Test
    public void TestThatWeCanMakeAPit() {
        Pit pit = new Pit();

        //Checks getName
        assertEquals(pit.getName(), "Pit");
    }

    //LaserStart
    @Test
    public void TestthatWeCanMakeALaserShoot() {
        Direction dir = Direction.SOUTH;
        int harm = 6;

        LaserStart lS = new LaserStart(dir, harm);

        //Test to see if direction works
        assertEquals(lS.getDirection(), Direction.SOUTH);
        //Test to check damage
        assertEquals(harm, 6);
        // Test name
        assertEquals(lS.getName(), "LaserStart");
    }

    //CollectionBand
    @Test
    public void TestThatWeCanMakeAConveyorBelt() {
        int movement = 1;
        Direction dir = Direction.EAST;

        ConveyorBelt cb = new ConveyorBelt(dir, movement, false);

        //Test to check rotation
        assertEquals(dir, Direction.EAST);
        //Test to check movement
        assertEquals(movement, 1);
        // Test name
        assertEquals(cb.getName(), "BendingConveyorBelt");
    }

    //Wall
    @Test
    public void TestThatWallIsOk() {
        Direction dir = Direction.WEST;

        Wall wall = new Wall(dir);

        //Test the direction
        assertEquals(wall.getDir(), Direction.WEST);
        // Test name
        assertEquals(wall.getName(), "Wall");
    }

    //CornerWall
    @Test
    public void TestCornerWall() {
        Wall w = new Wall(Direction.NORTH, Direction.SOUTH);

        assertEquals(w.getDir(), Direction.NORTH);
        assertEquals(w.getDir2(), Direction.SOUTH);
        // Test name
        assertEquals(w.getName(), "CornerWall");
    }

    //Gear
    @Test
    public void TestThatGearWorks() {
        Action action = Action.LEFTTURN;
        Gear gear = new Gear(action);

        // Test name
        assertEquals(gear.getName(), "Gear");
        //checks that getAction resolves in the right action
        assertEquals(gear.getAction(), Action.LEFTTURN);
    }
}
