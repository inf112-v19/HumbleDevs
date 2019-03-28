package inf112.skeleton.app.gameObjects.Items;

/**
 * Flag class that keep track of the flags, witch all the robots have to visit to win.
 */

public class Flag implements IItem {

    // To finish the game you have to visit x flags in a specific order
    // In the constructor it will be given a order number.
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
    public String getName() {
        return "Flag";
    }
}