package inf112.skeleton.app.board;

public interface IBoard {

    /**
     * Place a game piece on the board
     */
    void placePiece(IPosition position);

    /**
     * Check if a location on the board is free
     * @return
     */
    boolean isFree(IPosition position);

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
