package inf112.skeleton.app.GameObjects.Items;

/**
 * CollectionBand that gives info to the game class on the movement and possibly rotation.
 * @author Amalie Rovik
 */

public class CollectionBand implements IItem {

    private int movement;
    private int rotation;

    public CollectionBand(int movement, int rotation) {
        this.movement = movement;
        this.rotation = rotation;
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
        return movement;
    }

    public int rotation() {
        return rotation;
    }
}
