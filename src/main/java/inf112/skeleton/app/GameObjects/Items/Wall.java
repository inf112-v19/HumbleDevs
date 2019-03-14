package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.GameObjects.Items.IItem;
import inf112.skeleton.app.board.Direction;

/**
 * Wall class that makes all the walls in the game. A robot can´t cross a wall.
 * @author Amalie Rovik
 *
 */

public class Wall implements IItem {
    private Direction dir = null;
    private Direction dir2 = null;
    private boolean corner;

    //Single wall
    public Wall(Direction dir) {
        this.dir = dir;
        this.corner = false;
    }

    //Corner wall
    public Wall(Direction dir1, Direction dir2, boolean corner) {
        this.dir = dir1;
        this.dir2 = dir2;
        this.corner = true;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Direction getDir2() {
        return dir2;
    }

    public void setDir2(Direction dir2) {
        this.dir2 = dir2;
    }

    public boolean isCorner() {
        return corner;
    }

    public void setCorner(boolean corner) {
        this.corner = corner;
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
