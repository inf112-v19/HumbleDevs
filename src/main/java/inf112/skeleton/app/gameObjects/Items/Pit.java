package inf112.skeleton.app.gameObjects.Items;

/**
 * Class for a bottomless pit. The robot will die if it falls down.
 */

public class Pit implements IItem {
    public Pit() {}

    @Override
    public String getName() {
        return "Pit";
    }
}