package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.GameObjects.Items.IItem;
import inf112.skeleton.app.board.Direction;

public class Laser implements IItem {

    private Direction dir;
    private int damageMultiplier;

    public Laser(Direction dir, int damageMultiplier) {
        this.dir = dir;
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return "Laser";
    }

    @Override
    public char getSymbol() {
        return '-';
    }

    public Direction getDirection() {
        return dir;
    }
}