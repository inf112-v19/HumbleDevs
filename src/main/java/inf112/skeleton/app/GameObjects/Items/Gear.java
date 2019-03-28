package inf112.skeleton.app.GameObjects.Items;

import inf112.skeleton.app.card.Action;

/*
 * Gear class for rotation
 */

public class Gear implements IItem {
    private Action action;

    public Gear(Action action) {
        this.action = action;
    }

    @Override
    public String getName() {
        return "Gear";
    }

    public Action getAction() {
        return action;
    }
}