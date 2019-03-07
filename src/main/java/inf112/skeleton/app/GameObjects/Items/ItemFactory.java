package inf112.skeleton.app.GameObjects.Items;

public class ItemFactory {
    private int id;


    public static IItem getItem(int id) {
        switch (id) {
            case 1:
                return new DefaultTile();
        }
        return null;
    }


}
