package inf112.skeleton.app.board;

import inf112.skeleton.app.gameObjects.Items.IItem;
import inf112.skeleton.app.gameObjects.Robot;

import java.util.ArrayList;

public interface IBoard {
    /**
     * Place an item on the board
     */
    void insertItem(Position position, IItem item);
    void insertItem(int x, int y, IItem item);

    /**
     * Get the square for given x and y coordinates
     * @param x position
     * @param y position
     * @return square
     */
    Square getSquare(int x, int y);

    /**
     * Get list of items in a position
     */
    ArrayList<IItem> getItems(Position position);
    ArrayList<IItem> getItems(int x, int y);

    /**
     * Check if a location on the board is free (contains no robot)
     * @return true if it contains no robot, false otherwise
     */
    boolean isFree(Position position);
    boolean isFree(int x, int y);

    /**
     * Get a robot from a position on the board
     * @param pos the position
     * @return a robot if it exists, otherwise null
     */
    Robot getRobot(Position pos);

    /**
     * Remove a robot from a position
     * @param pos the position
     */
    void removeRobot(Position pos);

    /**
     * Insert a robot on a given position
     * @param pos the position
     * @param rob the robot
     * @return true if it is successful, false otherwise
     */
    boolean insertRobot(Position pos, Robot rob);
    /**
     * Calculate from x, y position to list-index
     * @param x position
     * @param y position
     * @return index
     */
    int toIndex(int x, int y);
    /**
     * Calculate from a position to list-index
     * @param pos position
     * @return index
     */
    int toIndex(Position pos);

    /**
     * Calculate the position from the list-index
     * @param index ¨
     */
    Position getPositionFromIndex(int index);

    /**
     * Get height of the board
     * @return height
     */
    int getHeight();

    /**
     * Get width of the board
     * @return width
     */
    int getWidth();

    /**
     * Get size of the board (height*width)
     * @return size
     */
    int getSize();


    /**
     * Clear board
     */
    void clear();
}
