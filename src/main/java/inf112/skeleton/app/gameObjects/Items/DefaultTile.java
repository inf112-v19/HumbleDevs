package inf112.skeleton.app.GameObjects.Items;

public class DefaultTile implements IItem{
    private int id;
    private String name;
    private char symbol;

    public DefaultTile() {

    }

    @Override
    public int tileId() {
        return id;
    }

    @Override
    public String getName() {
        return "DefaultTile";
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
