package inf112.skeleton.app.gameObjects.Items;

import inf112.skeleton.app.card.Action;

/**
 * Class for the gear. The gear will rotate the robot and can turn 90 degrees to the left or the right.
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

    /**
     * Get the direction which it rotates. Could be LEFTTURN or RIGHTTURN.
     * @return the direction.
     */
    public Action getAction() {
        return action;
    }
}