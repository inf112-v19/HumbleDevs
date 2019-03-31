package inf112.skeleton.app.gameObjects.Items;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.Action;

public abstract class ItemFactory {

    public static IItem getItem(int id) {
        if (id == 1) {
            return new DefaultTile();
        }
        if (id == 2) {
            return new Pit();
        }
        if (id == 3 || id == 4 || id == 5 || id == 6) {
            if (id == 3) {
                return new Flag(1);
            }
            if (id == 4) {
                return new Flag(2);
            }
            if (id == 5) {
                return new Flag(3);
            }
            else {
                return new Flag(4);
            }
        }



        //Bending clockwise converyorbelts
        if (id == 7) {
            return new ConveyorBelt(Direction.SOUTH, 2, false);
        }
        if (id == 8) {
            return new ConveyorBelt(Direction.WEST, 2, false);
        }
        if (id == 13) {
            return new ConveyorBelt(Direction.EAST, 2, false);
        }
        if (id == 14) {
            return new ConveyorBelt(Direction.NORTH, 2, false);
        }

        //Bending anti-clockwise conveyorbelts
        if (id == 9) {
            return new ConveyorBelt(Direction.EAST, 2, true);
        }
        if (id == 10) {
            return new ConveyorBelt(Direction.SOUTH, 2, true);
        }
        if (id == 15) {
            return new ConveyorBelt(Direction.NORTH, 2, true);
        }
        if (id == 16) {
            return new ConveyorBelt(Direction.WEST, 2, true);
        }

        //Bending normalspeed clockwise converyorbelts
        if (id == 19) {
            return new ConveyorBelt(Direction.SOUTH, 1, false);
        }
        if (id == 20) {
            return new ConveyorBelt(Direction.WEST, 1, false);
        }
        if (id == 25) {
            return new ConveyorBelt(Direction.EAST, 1, false);
        }
        if (id == 26) {
            return new ConveyorBelt(Direction.NORTH, 1, false);
        }

        //Bending normalspeed anti-clockwise conveyorbelts
        if (id == 21) {
            return new ConveyorBelt(Direction.EAST, 1, true);
        }
        if (id == 22) {
            return new ConveyorBelt(Direction.SOUTH, 1, true);
        }
        if (id == 27) {
            return new ConveyorBelt(Direction.NORTH, 1, true);
        }
        if (id == 28) {
            return new ConveyorBelt(Direction.WEST, 1, true);
        }

        //Wall
        if (id == 11) {
            return new Wall(Direction.NORTH);
        }
        if (id == 12) {
            return new Wall(Direction.SOUTH);
        }
        if (id == 17) {
            return new Wall(Direction.WEST);
        }
        if (id == 18) {
            return  new Wall(Direction.EAST);
        }

        //Corner Wall

        if (id == 23) {
            new Wall(Direction.WEST, Direction.NORTH);
        }
        if (id == 24) {
            new Wall(Direction.EAST, Direction.NORTH);
        }

        if (id == 29) {
            new Wall(Direction.WEST, Direction.SOUTH);
        }

        if (id == 30) {
            new Wall(Direction.EAST, Direction.SOUTH);
        }

        //Doublespeed straight conveyorbelt
        if (id == 31) {
            return new ConveyorBelt(Direction.NORTH, 2);
        }
        if (id == 32) {
            return new ConveyorBelt(Direction.SOUTH, 2);
        }
        if (id == 33) {
            return new ConveyorBelt(Direction.WEST, 2);
        }
        if (id == 34) {
            return new ConveyorBelt(Direction.EAST, 2);
        }

        //Straight conveyorbelt
        if (id == 37) {
            return new ConveyorBelt(Direction.NORTH, 1);
        }
        if (id == 38) {
            return new ConveyorBelt(Direction.SOUTH, 1);
        }
        if (id == 39) {
            return new ConveyorBelt(Direction.WEST, 1);
        }
        if (id == 40) {
            return new ConveyorBelt(Direction.EAST, 1);
        }

        //Hammer and wrench
        if (id == 35) {
            return new RepairTool();
        }

        //Only wrench
        if (id == 36) {
            return new RepairTool();
        }

        //Red gear (left)
        if (id == 41) {
            return new Gear(Action.LEFTTURN);
        }

        //Green gear (right)
        if (id == 42) {
            return new Gear(Action.RIGHTTURN);
        }

        //Laser vertical
        if (id == 67) {
            new Laser(Direction.NORTH, 1);
        }

        //Laser horizontal
        if (id == 68) {
            new Laser(Direction.WEST, 1);
        }

        //Lasterstart north
        if (id == 69) {
            new Laser(Direction.NORTH, 1, true);
        }
        //Lasterstart east
        if (id == 70) {
            new Laser(Direction.EAST, 1, true);
        }

        //Lasterstart south
        if (id == 71) {
            new Laser(Direction.SOUTH, 1, true);
        }

        //Lasterstart west
        if (id == 72) {
            new Laser(Direction.WEST, 1, true);
        }

        //DoubleLaser vertical
        if (id == 73) {
            new Laser(Direction.NORTH, 2);
        }

        //DoubleLaser horizontal
        if (id == 74) {
            new Laser(Direction.WEST, 2);
        }

        //DoubleLasterstart north
        if (id == 75) {
            new Laser(Direction.NORTH, 2, true);
        }
        //DoubleLasterstart east
        if (id == 76) {
            new Laser(Direction.EAST, 2, true);
        }

        //DoubleLasterstart south
        if (id == 77) {
            new Laser(Direction.SOUTH, 2, true);
        }

        //DoubleLasterstart west
        if (id == 78) {
            new Laser(Direction.WEST, 2, true);
        }

        //Missing items:
        // - triplelasers
        // - weird stuff at the bottom of tmx file
        // - "triple" conveyorbelts

        return null;

    }

}
