package inf112.skeleton.app.gameObjects.Items;

public class Dock implements IItem {
    private int number;
    private String name;

    public Dock(int number){
        this.number = number;
        this.name = "Dock" + number;
    }

    @Override
    public int tileId() {
        return 0;
    }
    public int getNumber(){
        return number;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public char getSymbol() {
        return 0;
    }
}
