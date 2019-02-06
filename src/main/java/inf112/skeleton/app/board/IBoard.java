package inf112.skeleton.app.board;

public interface IBoard<T> {
    /**
     * Place an element on the board
     */
    void insertElement(IPosition position, T element);
    void insertElement(int x, int y, T element);

    /**
     * Get an element on the board
     */
    void getElement(IPosition position);
    void getElement(int x, int y);

    /**
     * Check if a location on the board is free (is null)
     * @return
     */
    boolean isFree(IPosition position);
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
     * Clear board
     */
    void clear();
}
