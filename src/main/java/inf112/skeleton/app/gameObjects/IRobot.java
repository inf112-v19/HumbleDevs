package inf112.skeleton.app.GameObjects;

import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.card.ProgramCard;

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
     * Remove one damage token
     */
    void repairDamage();
    
    /**
     * Check if the robot is still in the game.
     * @return true if the robot has died 3 times, false otherwise.
     */
    boolean gameOver();

    /**
     * Rotate the robot left
     */
	void rotateLeft();
	
	/**
	 * Rotate the robot right
	 */
	void rotateRight();
	
	/**
	 * Kill the robot
	 */
	void die();
	
	/**
	 * @return the number of flags the robot has visited
	 */
	int visitedFlags();
	
	/**
	 * @return the respawn position
	 */
	Position respawn();

	/**
	 * Visit a flag. Flags visited must increments and the backup - position is updated
	 */
	void visitFlag();

	/**
	 * @return the robots cards for one round
	 */
	ProgramCard[] getCards();

	/**
	 * @return the position of the robot
	 */
	Position getPosition();
	
	/**
	 * Power down the robot for one turn, all damage tokens are removed
	 */
	void powerDown();



}
