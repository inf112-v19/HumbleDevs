package inf112.skeleton.app.GameObjects;

import inf112.skeleton.app.board.Direction;

public class Laser implements IItem{

    private Direction dir;
    private int damageMultiplier;

    @Override
    public IItem getType() {
        return null;
    }

    @Override
    public String getName() {
        return "Laser";
    }

    @Override
    public char getSymbol() {
        return '-';
    }

    @Override
    public Direction getDirection() {
        return dir;
    }
}