package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.board.Direction;

public class ItemFactory {

    public static IItem getItem(int id) {
        if (id == 1) {
            return new DefaultTile();
        }
        if (id == 2) {
            return new Pit();
        }
        if (id == 3 || id == 4 || id == 5 || id == 6) {
            return new Flag();
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

        //Bending normalspeed anti-clockwise conveyorbelts
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




    }

}
