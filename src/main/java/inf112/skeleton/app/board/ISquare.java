package inf112.skeleton.app.board;

import inf112.skeleton.app.GameObjects.Robot;

/**
 * The interface of a square on the map
 * @author Even Kolsgaard
 * @param <T>
 *
 */
public interface ISquare<T> {
	
/**
 * Method that checks if there are any elemtns on this particular square
 * @return the element if there is one, otherwise null
 */
public T getElement();
	
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
 * @param element the element to be added
 * @return True if it succeeded to add an element, False otherwise
 */
public boolean addElement(T element);

/**
 * Method to remove the robot from the square
 */
public void removeRobot();

/**
 * Method to remove element from the square
 */
public void removeElement();

/**
 * @return the robot if there is one, otherwise return null
 */
public Robot getRobot();

}
