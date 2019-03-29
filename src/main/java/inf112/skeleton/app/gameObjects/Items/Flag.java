package inf112.skeleton.app.gameObjects.Items;

/**
 * Flag class that keep track of the flags, witch all the robots have to visit to win. The flags must be visited in a
 * specific order, hence the flag number.
 */
public class Flag implements IItem {
    private int flagNum;

    public Flag(int flagNum) {
        this.flagNum = flagNum;
    }

    /**
     * Get the number of the flag. The number is the order that it has to be visited, i.e. the flag with number 1 must
     * be visited before visiting the flag with number 1.
     * @return the flag number.
     */
    public int getFlagNum() {
        return flagNum;
    }

    @Override
    public String getName() {
        return "Flag";
    }
}