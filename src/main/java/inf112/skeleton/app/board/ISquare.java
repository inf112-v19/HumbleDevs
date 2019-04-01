package inf112.skeleton.app.board;

import java.util.ArrayList;

import inf112.skeleton.app.gameObjects.Robot;

/**
 * Interface of the square. A board will have a square in every position.
 * @param <T> What to keep in the square in addition to robots.
 */
public interface ISquare<T> {

    /**
     * Method that checks if there are any elements on this particular square
     * @return the list of element if they exists, otherwise null
     */
    ArrayList<T> getElements();

    /**
     * Checks if there already is a robot on this square
     * @return True if there is a robot on the square, otherwise False
     */
    boolean occupied();

    /**
     * Method that adds a robot to the square, given that it's not occupied
     * @robot robot the robot to be added
     * @return True if it succeeded to add a robot, False otherwise
     */
    boolean addRobot(Robot robot);

    /**
     * Add an item to a square
     * @param elem the element to be added
     */
    void addElement(T elem);

    /**
     * Method to remove the robot from the square
     */
    void removeRobot();

    /**
     * Clears all the elements from the list
     */
    void removeElements();

    /**
     * @return the robot if there is one, otherwise return null
     */
    Robot getRobot();
}