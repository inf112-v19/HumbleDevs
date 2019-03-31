package inf112.skeleton.app.board;

import java.util.ArrayList;

import inf112.skeleton.app.gameObjects.Robot;

/**
 * The interface of a square on the map
 * @author Even Kolsgaard
 * @param <T>
 *
 */
public interface ISquare<T> {
	
/**
 * Method that checks if there are any elements on this particular square
 * @return the list of element if they exists, otherwise null
 */
public ArrayList<T> getElements();
	
/**
 * Checks if there already is a robot on this square
 * @return True if there is a robot on the square, otherwise False
 */
public boolean occupied();

/**
 * Method that adds a robot to the square, given that it's not occupied
 * @robot robot the robot to be added
 * @return True if it succeeded to add a robot, False otherwise
 */
public boolean addRobot(Robot robot);

/**
 * Add an item to a square
 * @param elem the element to be added
 */
public void addElement(T elem);

/**
 * Method to remove the robot from the square
 */
public void removeRobot();

/**
 * Clears all the elements from the list
 */
public void removeElements();

/**
 * @return the robot if there is one, otherwise return null
 */
public Robot getRobot();

}
