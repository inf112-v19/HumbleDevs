package inf112.skeleton.app.gameObjects.Items;

import inf112.skeleton.app.board.Direction;

/**
 * Wall class that makes all the walls in the game. A robot can´t cross a wall.
 */

public class Wall implements IItem {
    private Direction dir = null;
    private Direction dir2 = null;
    private String name;

    //Single wall
    public Wall(Direction dir) {
        this.dir = dir;
        this.name = "Wall";
    }

    //Corner wall
    public Wall(Direction dir1, Direction dir2) {
        this.dir = dir1;
        this.dir2 = dir2;
        this.name = "CornerWall";
    }

    /**
     * Get the direction of the first wall
     * @return direction of the first wall
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * Get the direction of the second wall, given that it is a corner wall
     * @return direction of the second wall
     */
    public Direction getDir2() {
        return dir2;
    }

    @Override
    public String getName() {
        return this.name;
    }
}