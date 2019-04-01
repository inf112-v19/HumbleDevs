package inf112.skeleton.app.gameObjects.Items;

import inf112.skeleton.app.board.Direction;

/**
 * A LaserShoot is the laser projectile that the laser shoots. It will have a direction and there could be multiple
 * LaserShoots on square. The parameter rays says how many laser beams there are.
 */
public class LaserShoot implements IItem {

    private Direction dir;
    private int rays;

    public LaserShoot(Direction dir, int rays) {
        this.dir = dir;
        this.rays = rays;
    }

    @Override
    public String getName() {
        return "LaserShoot";
    }

    /**
     * Get the direction that the laser projectile is moving in.
     * @return direction of laser.
     */
    public Direction getDirection() {
        return dir;
    }

    /**
     * Get how much damage that the laser inflicts
     * @return damage of laser
     */
    public int getRays() {
        return rays;
    }
}
