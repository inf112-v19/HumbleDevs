package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.GameObjects.Items.IItem;

/**
 * Pit class. If a robot class crosses a Pit it
 * @author Amalie Rovik
 *
 */

public class Pit implements IItem {
    public Pit() {}

    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return "Pit";
    }

    @Override
    public char getSymbol() {
        return '@';
    }

}
