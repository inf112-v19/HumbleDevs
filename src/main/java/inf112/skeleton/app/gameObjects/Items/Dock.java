package inf112.skeleton.app.gameObjects.Items;

public abstract class Dock implements IItem {
    private int number;
    private String name;

    public Dock(int number){
        this.number = number;
        this.name = "Dock" + number;
    }

    public int getNumber(){
        return number;
    }

    @Override
    public String getName() {
        return name;
    }
}
