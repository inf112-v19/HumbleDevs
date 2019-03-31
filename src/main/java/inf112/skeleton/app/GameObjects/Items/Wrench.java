package inf112.skeleton.app.gameObjects.Items;

/**
 * Wrench class to fix the robots.
 * @author Amalie Rovik
 *
 */

public class Wrench implements IItem {

    public Wrench() {}

    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return "Wrench";
    }

    @Override
    public char getSymbol() {
        return '$';
    }
}
