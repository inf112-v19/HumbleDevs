package inf112.skeleton.app.GameObjects.Items;

public class DefaultTile implements IItem{
    private String name = "DefaultTile";

    public DefaultTile() {

    }

    @Override
    public String getName() {
        return name;
    }
}
