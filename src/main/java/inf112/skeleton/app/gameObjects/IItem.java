package inf112.skeleton.app.GameObjects;
import inf112.skeleton.app.board.Direction;


/*
 * Interface for cards and items
 */
public interface IItem {
    int getId();

<<<<<<< HEAD
    void setId(int id);

    void createItem(int id);
=======
    /*
     * Return the Item type
     */
    IItem getType();

    /*
     * Return the name of the item
     */
    String getName();

    /*
     * Returns the symbol of the item
     */
    String getSymbol();

    /*
     * Returns the direction of the item
     */
    Direction getDirection();
>>>>>>> master
}
