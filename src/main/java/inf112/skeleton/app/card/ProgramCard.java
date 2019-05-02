package inf112.skeleton.app.card;

import java.util.Objects;

/**
 * An implementation of the IProgramCard interface
 *
 * @author Linh Nguyen
 */

public class ProgramCard implements IProgramCard {
    private int move;
    private int priority;
    private Action action;
    private boolean used;

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
        this.used = false;
    }
    public void use(){
        this.used = true;
    }
    public boolean isUsed(){
        return this.used;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramCard that = (ProgramCard) o;
        return move == that.move &&
                priority == that.priority &&
                used == that.used &&
                action == that.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(move, priority, action, used);
    }
}
