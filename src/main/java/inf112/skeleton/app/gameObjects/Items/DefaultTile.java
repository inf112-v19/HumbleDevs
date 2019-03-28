package inf112.skeleton.app.gameObjects.Items;

public class DefaultTile implements IItem{
    private String name = "DefaultTile";

    public DefaultTile() {

    }

    @Override
    public String getName() {
        return name;
    }
}
