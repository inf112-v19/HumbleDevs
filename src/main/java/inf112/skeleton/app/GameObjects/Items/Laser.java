package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.GameObjects.Items.IItem;
import inf112.skeleton.app.board.Direction;

public class Laser implements IItem {

    private Direction dir;
    private int damageMultiplier;
    private boolean start;

    public Laser(Direction dir, int damageMultiplier) {
        this.dir = dir;
        this.damageMultiplier = damageMultiplier;
        start = false;
    }
    // Hva er start?
    public Laser(Direction dir, int damageMultiplier, boolean start) {
        this.dir = dir;
        this.damageMultiplier = damageMultiplier;
        this.start = start;
    }

    public boolean isStart() {
        return start;
    }

    @Override
    public String getName() {
        return "Laser";
    }

    public Direction getDirection() {
        return dir;
    }

    public int getDamageMultiplier() {
        return damageMultiplier;
    }
}