package inf112.skeleton.app.gameObjects.Items;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.Action;

public abstract class ItemFactory {

    public static IItem getItem(int id) {
        //Default Tile
        if (id == 1) return new DefaultTile();
        if (id == 2) return new Pit();

        // Flags
        if (id == 3) return new Flag(1);
        if (id == 4) return new Flag(2);
        if (id == 5) return new Flag(3);
        if (id == 6) return new Flag(4);

        //Bending clockwise conveyorbelts
        if (id == 7) return new ConveyorBelt(Direction.SOUTH, 2, false);
        if (id == 8) return new ConveyorBelt(Direction.WEST, 2, false);
        if (id == 13) return new ConveyorBelt(Direction.EAST, 2, false);
        if (id == 14) return new ConveyorBelt(Direction.NORTH, 2, false);

        //Bending anti-clockwise conveyorbelts
        if (id == 9) return new ConveyorBelt(Direction.EAST, 2, true);
        if (id == 10) return new ConveyorBelt(Direction.SOUTH, 2, true);
        if (id == 15) return new ConveyorBelt(Direction.NORTH, 2, true);
        if (id == 16) return new ConveyorBelt(Direction.WEST, 2, true);

        //Bending normalspeed clockwise converyorbelts
        if (id == 19) return new ConveyorBelt(Direction.SOUTH, 1, false);
        if (id == 20) return new ConveyorBelt(Direction.WEST, 1, false);
        if (id == 25) return new ConveyorBelt(Direction.EAST, 1, false);
        if (id == 26) return new ConveyorBelt(Direction.NORTH, 1, false);

        //Bending normalspeed anti-clockwise conveyorbelts
        if (id == 21) return new ConveyorBelt(Direction.EAST, 1, true);
        if (id == 22) return new ConveyorBelt(Direction.SOUTH, 1, true);
        if (id == 27) return new ConveyorBelt(Direction.NORTH, 1, true);
        if (id == 28) return new ConveyorBelt(Direction.WEST, 1, true);

        //Wall
        if (id == 11) return new Wall(Direction.NORTH);
        if (id == 12) return new Wall(Direction.SOUTH);
        if (id == 17) return new Wall(Direction.WEST);
        if (id == 18) return new Wall(Direction.EAST);

        //Corner Wall
        if (id == 23) return new Wall(Direction.WEST, Direction.NORTH);
        if (id == 24) return new Wall(Direction.EAST, Direction.NORTH);
        if (id == 29) return new Wall(Direction.WEST, Direction.SOUTH);
        if (id == 30) return new Wall(Direction.EAST, Direction.SOUTH);

        //Doublespeed straight conveyorbelt
        if (id == 31) return new ConveyorBelt(Direction.NORTH, 2);
        if (id == 32) return new ConveyorBelt(Direction.SOUTH, 2);
        if (id == 33) return new ConveyorBelt(Direction.WEST, 2);
        if (id == 34) return new ConveyorBelt(Direction.EAST, 2);

        //Straight conveyorbelt
        if (id == 37) return new ConveyorBelt(Direction.NORTH, 1);
        if (id == 38) return new ConveyorBelt(Direction.SOUTH, 1);
        if (id == 39) return new ConveyorBelt(Direction.WEST, 1);
        if (id == 40) return new ConveyorBelt(Direction.EAST, 1);

        //Hammer and wrench
        if (id == 35) return new RepairTool(true);
        //Only wrench
        if (id == 36) return new RepairTool(false);

        //Red gear (left)
        if (id == 41) return new Gear(Action.LEFTTURN);
        //Green gear (right)
        if (id == 42) return new Gear(Action.RIGHTTURN);

        //Laser vertical
        if (id == 67) return new Laser(Direction.NORTH, 1);
        //Laser horizontal
        if (id == 68) return new Laser(Direction.WEST, 1);

        //Lasterstart north
        if (id == 69) return new Laser(Direction.NORTH, 1, true);
        if (id == 70) return new Laser(Direction.EAST, 1, true);
        if (id == 71) return new Laser(Direction.SOUTH, 1, true);
        if (id == 72) return new Laser(Direction.WEST, 1, true);

        //DoubleLaser vertical
        if (id == 73) return new Laser(Direction.NORTH, 2);
        //DoubleLaser horizontal
        if (id == 74) return new Laser(Direction.WEST, 2);

        //DoubleLasterstart north
        if (id == 75) return new Laser(Direction.NORTH, 2, true);
        if (id == 76) return new Laser(Direction.EAST, 2, true);
        if (id == 77) return new Laser(Direction.SOUTH, 2, true);
        if (id == 78) return new Laser(Direction.WEST, 2, true);

        //Docks (starting positions for robots)
        if (id == 85) return new Dock(1);
        if (id == 86) return new Dock(2);
        if (id == 87) return new Dock(3);
        if (id == 88) return new Dock(4);
        if (id == 89) return new Dock(5);
        if (id == 90) return new Dock(6);
        if (id == 91) return new Dock(7);
        if (id == 92) return new Dock(8);

        //Missing items:
        // - triplelasers
        // - "triple" conveyorbelts

        return null;

    }

}
