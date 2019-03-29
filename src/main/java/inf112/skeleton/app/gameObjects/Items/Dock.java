package inf112.skeleton.app.gameObjects.Items;

/**
 * A dock is a starting position. There are a total of 8 docks in a game.
 */
public class Dock implements IItem {
    private int number;
    private String name;

    public Dock(int number){
        this.number = number;
        this.name = "Dock" + number;
    }

    /**
     * Get the number of the dock.
     * @return dock number
     */
    public int getNumber(){
        return number;
    }

    @Override
    public String getName() {
        return name;
    }
}
