package inf112.skeleton.app.GameObjects.Items;

/**
 * Wrench class to fix the robots.
 * @author Amalie Rovik
 *
 */

public class Hammer implements IItem {

    public Hammer() {}

    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public char getSymbol() {
        return '€';
    }
}
