package inf112.skeleton.app.GameObjects.Items;

/**
 * Flag class that keep track of the flags, witch all the robots have to visit to win.
 * @author Amalie Rovik
 */

public class Flag implements IItem {
    private int flagNum;

    public Flag(int flagNum) {
        this.flagNum = flagNum;
    }

    public int getFlagNum() {
        return flagNum;
    }

    public void setFlagNum(int flagNum) {
        this.flagNum = flagNum;
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