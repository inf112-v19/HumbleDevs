package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.board.Direction;

public class LaserShoot implements IItem {

    private Direction dir;
    private int harm;

    public LaserShoot(Direction dir, int harm) {
        this.dir = dir;
        this.harm = harm;
    }

    @Override
    public String getName() {
        return "LaserShoot";
    }

    public Direction getDirection() {
        return dir;
    }

    public int getHarm() {
        return harm;
    }
}
