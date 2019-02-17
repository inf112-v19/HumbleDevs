package inf112.skeleton.app;

import inf112.skeleton.app.board.Position;

public interface IRobot {

	/**
	 * Move the robot i steps
	 * @param i number of steps to move
	 */
    void move(int i);
    
    /**
     * Set a new backup for the robot
     * @param newBackup is the position for the new backup
     */
    public void makeBackup(Position newBackup);
    
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
