package inf112.skeleton.app.card;

/**
 * An implementation of the IProgramCard interface
 *
 * @author Linh Nguyen
 */

public class ProgramCard implements IProgramCard {
    private int move;
    private int priority;
    private Action action;

    /**
     * Constructor of the movement cards
     *
     * @param move the move value
     * @param priority the unique priority of the card
     * @param action The movement the card that will impose
     */
    public ProgramCard(int move, int priority, Action action){
        this.move=move;
        this.priority=priority;
        this.action=action;
    }
    /**
     *
     * @return the move value
     */
    @Override
    public int getMove(){ return this.move; }

    /**
     *
     * @return The priority of the card
     */
    @Override
    public int getPriority(){ return priority; }

    /**
     *
     * @return Get Action enum for card
     */
    @Override
    public Action getAction(){
        return this.action;
    }

    public String getActionAndMovement(Action action, int movement) {
        return action+String.valueOf(movement);
    }



}
