package inf112.skeleton.app.GameObjects.Items;

/**
 * Flag class that keep track of the flags, witch all the robots have to visit to win.
 * @author Amalie Rovik
 */

public class Flag implements IItem {
    private int order;

    // To finish the game you have to visit x flags in a specific order
    // In the constructor it will be given a order number.
    public Flag(int order) {
        this.order = order;
    }

    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return "Flag";
    }

    public char getSymbol() {
        return 'F';
    }
}