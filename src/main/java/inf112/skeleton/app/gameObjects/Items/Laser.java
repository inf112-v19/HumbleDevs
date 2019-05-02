package inf112.skeleton.app.gameObjects.Items;

import inf112.skeleton.app.board.Direction;

/**
 * A laser on the board. A robot takes damage by staying on a tile with a laser.
 */
public class Laser implements IItem {

    private Direction dir;
    private int damageMultiplier;

    public Laser(Direction dir, int damageMultiplier) {
        this.dir = dir;
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public String getName() {
        return "Laser";
    }

    /**
     * Get the direction that the laser is facing.
     * @return direction of laser
     */
    public Direction getDirection() {
        return dir;
    }

    public int getDamageMultiplier() {
        return damageMultiplier;
    }
}