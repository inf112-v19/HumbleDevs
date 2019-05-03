package inf112.skeleton.app.gameObjects.Items;

/**
 * The game have to options for repairing tools. It is a wrench or a wrenchAndHammer combined.
 * The difference is that in addition to remove one damage token, wrenchAndHammer also gives one
 * option card.
 */
public class RepairTool implements IItem {

    public RepairTool() {
    }
    @Override
    public String getName() {
        return "Wrench";
    }
}
