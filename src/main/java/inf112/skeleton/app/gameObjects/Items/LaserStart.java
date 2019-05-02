package inf112.skeleton.app.gameObjects.Items;

import inf112.skeleton.app.board.Direction;

/**
 * A LaserStart is the start of a laserbeam, this is needed to decide the shooting direction of a beam.
 */
public class LaserStart extends Laser {

    public LaserStart(Direction dir, int damageMultiplier) {
        super(dir, damageMultiplier);
    }


    @Override
    public String getName() {
        return "LaserStart";
    }

}
