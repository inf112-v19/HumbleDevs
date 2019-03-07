package inf112.skeleton.app.GameObjects.Items;

/**
 * To make a corner we need to walls, so we made an own class for corners.
 * @author Amalie Rovik
 *
 */

public class CornerWall implements IItem {

    private Wall wall1;
    private Wall wall2;

    public CornerWall(Wall wall1, Wall wall2) {
        this.wall1 = wall1;
        this.wall2 = wall2;
    }

    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return "cornerWall";
    }

    @Override
    public char getSymbol() {
        return '<';
    }
}
