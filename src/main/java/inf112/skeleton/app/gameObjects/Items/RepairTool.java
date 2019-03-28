package inf112.skeleton.app.gameObjects.Items;

/*
 * The game have to options for repairing tools. It is a wrench or a wrenchAndHammer combined.
 * The difference is that in addition to remove one damage token, wrenchAndHammer also gives one
 * option card.
 */
public class RepairTool implements IItem {
    //private boolean wrench; //Removes one damage token
    private boolean wrenchAndHammer; //Removes one damage token and receives an option card

    // Constructor sets if it is a wrench or a wrenchAndHammer.
    public RepairTool(boolean wrenchAndHammer) {
        this.wrenchAndHammer = wrenchAndHammer;
    }

    @Override
    public String getName() {
        if (wrenchAndHammer = true)
            return "wrenchAndHammer";
        else return "wrench";
    }

    public boolean wrenchAndHammer() {
        return wrenchAndHammer;
    }
}
