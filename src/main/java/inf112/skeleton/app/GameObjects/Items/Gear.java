package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.card.Action;

/*
 * Gear class for rotation
 * @author Amalie Rovik
 */

public class Gear implements IItem {
    private Action action;

    public Gear(Action action) {
        this.action = action;
    }

    @Override
    public int tileId() {
        return 0;
    }

    @Override
    public String getName() {
        return "Gear";
    }

    @Override
    public char getSymbol() {
        return '°';
    }

    public Action getAction() {
        return action;
    }
}