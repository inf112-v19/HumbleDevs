package inf112.skeleton.app.gameObjects;

import inf112.skeleton.app.gameObjects.Items.Flag;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.board.Position;
import inf112.skeleton.app.card.ProgramCard;
/**
 * Interface for the robot class
 * @author Even Kolsgaard
 *
 */
public interface IRobot {

    /**
     * Move the robot i steps in the direction that the robot is facing
     * @param i number of steps to move
     */
    void move(int i);

    /**
     * Move the robot one step in the given direction
     * @param dir
     */
    void move(Direction dir);

    /**
     * Set a new backup for the robot
     * @param newBackup is the position for the new backup
     */
    void makeBackup(Position newBackup);

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
     * Take damage
     * @param i number of damage
     */
    void takeDamage(int i);

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
     * @return the respawn the robot, its new position will be the last backup
     */
    void respawn();

    /**
     * Check that the robot is visiting the flag in the correct order. The flag is registered if it's in the correct order,
     * otherwise do nothing.
     */
    void visitFlag(Flag flag);

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

    /**
     * Checks if the robot is powered down
     * @return true of the robot is powered down, false otherwise
     */
    boolean isPoweredDown();

    /**
     * Get the number of damage that the robot has taken. Can only be between 0 and 10
     * @return the number of damage tokens that the robot have
     */
    int getDamageTokens();

    /**
     * Checks if the robot is destroyed
     * @return true if the robot is destroyed, false otherwise
     */
    boolean isDestroyed();


}