package inf112.skeleton.app.card;

/**
 * interface for ProgramCard class
 *
 * @author Linh Nguyen
 *
 */


public interface IProgramCard {

    /**
     * get the movement value of the card
     * the movement value should be from -1 to 3
     */
    int getMove();

    /**
     * The priority of the card
     */
    int getPriority();

    /**
     *   Get Action enum for card
     */
    Action getAction();


}
