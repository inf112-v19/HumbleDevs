package inf112.skeleton.app.board;

import inf112.skeleton.app.GameObjects.Items.IItem;

import java.util.ArrayList;

public interface IBoard {
    /**
     * Place an item on the board
     */
    void insertItem(Position position, IItem item);
    void insertItem(int x, int y, IItem item);

    /**
     * Get the square in a given position
     * @param x
     * @param y
     * @return
     */
    Square getSquare(int x, int y);

    /**
     * Get list of items in a position
     */
    ArrayList<IItem> getItems(Position position);
    ArrayList<IItem> getItems(int x, int y);

    /**
     * Check if a location on the board is free (contains no robot)
     * @return
     */
    boolean isFree(Position position);
    boolean isFree(int x, int y);

    /**
     * Calculate from x, y position to list-index
     * @param x
     * @param y
     * @return
     */
    int toIndex(int x, int y);

    /**
     * Get height of the board
     * @return
     */
    int getHeight();

    /**
     * Get width of the board
     * @return
     */
    int getWidth();

    /**
     * Get size of the board (height*width)
     * @return
     */
    int getSize();


    /**
     * Clear board
     */
    void clear();
}
