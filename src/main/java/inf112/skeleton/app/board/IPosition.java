package inf112.skeleton.app.board;

/**
 * The interface of a position. A position is a x and y coordinate
 */
public interface IPosition {

    /**
     * Get the x - coordinate of the position
     * @return the x - coordinate
     */
    int getX();

    /**
     * Get the y - coordinate of the position
     * @return the y - position
     */
    int getY();

    /**
     * Move the position one step to the north. The y - coordinate increments.
     */
    void moveNorth();

    /**
     * Move the position one step to the east. The x - coordinate increments.
     */
    void moveEast();

    /**
     * Move the position one step to the south. The y - coordinate decrements.
     */
    void moveSouth();

    /**
     * Move the position one step to the west. The x - coordinate decrements.
     */
    void moveWest();

    /**
     * Move the position one step in the given direction.
     * @param dir the direction to move.
     */
    void move(Direction dir);
}