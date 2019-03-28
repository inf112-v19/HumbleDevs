package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.board.Direction;

/**
 * Wall class that makes all the walls in the game. A robot can´t cross a wall.
 */

public class Wall implements IItem {
    private Direction dir = null;
    private Direction dir2 = null;
    private boolean corner;
    private String name;
    private char symbol;

    //Single wall
    public Wall(Direction dir) {
        this.dir = dir;
        this.corner = false;
        this.name = "Wall";
    }

    //Corner wall
    public Wall(Direction dir1, Direction dir2) {
        this.dir = dir1;
        this.dir2 = dir2;
        this.corner = true;
        this.name = "CornerWall";
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

    public void setCorner (boolean corner) {
        this.corner = corner;
    }

    @Override
    public String getName() {
        return this.name;
    }
}