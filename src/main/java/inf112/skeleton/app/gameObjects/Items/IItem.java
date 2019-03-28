package inf112.skeleton.app.gameObjects.Items;


/*
 * Interface for cards and items
 * @author Amalie Rovik
 */
public interface IItem {

    int tileId();

    /*
     * Return the name of the item
     */
    String getName();

    /*
     * Returns the symbol of the item
     */
    char getSymbol();
}
