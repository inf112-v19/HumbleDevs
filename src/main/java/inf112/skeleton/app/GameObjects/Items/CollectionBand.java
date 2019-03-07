package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.Action;

/**
 * CollectionBand that gives info to the game class on the movement and possibly rotation.
 * @author Amalie Rovik
 */

public class CollectionBand implements IItem {

    private int movement;
    private Action rotation;
    private Direction dir;

    public CollectionBand(int movement, Action rotation, Direction dir) {
        this.movement = movement;
        this.rotation = rotation;
        this.dir = dir;
    }

    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return "CollectionBand";
    }

    @Override
    public char getSymbol() {
        return '_';
    }

    public int getMovement() {
        return this.movement;
    }

    public Action getRotation() {
        return this.rotation;
    }
    public Direction getDirection() {
    	return this.dir;
    }
}
