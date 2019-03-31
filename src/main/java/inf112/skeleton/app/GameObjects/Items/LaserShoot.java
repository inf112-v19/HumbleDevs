package inf112.skeleton.app.gameObjects.Items;

import inf112.skeleton.app.board.Direction;

public class LaserShoot implements IItem {

    private Direction dir;
    private int harm;

    public LaserShoot(Direction dir, int harm) {
        this.dir = dir;
        this.harm = harm;
    }

    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return "LaserShoot";
    }

    @Override
    public char getSymbol() {
        return '>';
    }

    public Direction getDirection() {
        return dir;
    }

    public int getHarm() {
        return harm;
    }
}