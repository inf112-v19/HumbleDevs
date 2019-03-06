package inf112.skeleton.app.card;

import inf112.skeleton.app.card.IProgramCard;

public class ProgramCard implements IProgramCard {
    private int move;
    private int priority;
    private Movement movement;

    public ProgramCard(int move, int priority, Movement movement){
        this.move=move;
        this.priority=priority;
        this.movement=movement;
    }

    @Override
    public int getMove(){ return this.move; }

    @Override
    public int getPriority(){ return priority; }

    @Override
    public Movement getMovement(){
        return this.movement;
    }

}
