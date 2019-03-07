package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.GameObjects.Items.IItem;
import inf112.skeleton.app.board.Direction;

/**
 * Wall class that makes all the walls in the game. A robot can´t cross a wall.
 * @author Amalie Rovik
 *
 */

public class Wall implements IItem {
    private Direction dir;

    public Wall(Direction dir) {
        this.dir = dir;
    }

    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return "Wall";
    }

    @Override
    public char getSymbol() {
        return '|';
    }

    public Direction getDirection() {
        return dir;
    }
}
