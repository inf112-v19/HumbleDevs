package inf112.skeleton.app.GameObjects;

import inf112.skeleton.app.board.Direction;

public class LaserShoot implements IItem {

    private Direction dir;
    private int harm;

    public LaserShoot(Direction dir, int harm) {
        this.dir = dir;
        this.harm = harm;
    }

    @Override
    public IItem getType() {
        return null;
    }

    @Override
    public String getName() {
        return "LaserShoot";
    }

    @Override
    public char getSymbol() {
        return '>';
    }

    @Override
    public Direction getDirection() {
        return dir;
    }

    public int getHarm() {
        return harm;
    }
}
