<<<<<<< HEAD:src/main/java/inf112/skeleton/app/GameObjects/IRobot.java
package inf112.skeleton.app.GameObjects;
import inf112.skeleton.app.Direction;
=======
package inf112.skeleton.app.gameObjects;

import inf112.skeleton.app.board.Direction;
>>>>>>> master:src/main/java/inf112/skeleton/app/gameObjects/IRobot.java

public interface IRobot {

	/**
	 * Move the robot i steps
	 * @param i number of steps to move
	 */
    void move(int i);
    
    /**
     * @return the direction the robot is facing
     */
    Direction getDirection();

    /**
     * @return the x - position of the robot
     */
    int getX();

    /**
     * @return the y - position of the robot
     */
    int getY();

    /**
     * Take one damage
     */
    void takeDamage();
    /**
     * Repair one health
     */
    void repairHealth();
    
    /**
     * Check if the robot is still alive
     * @return true if the robot is alive, false if it`s dead
     */
    boolean isAlive();

    /**
     * Rotate the robot left
     */
	void rotateLeft();
	
	/**
	 * Rotate the robot right
	 */
	void rotateRight();



}