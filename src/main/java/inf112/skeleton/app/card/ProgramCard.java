package inf112.skeleton.app.card;

import inf112.skeleton.app.card.IProgramCard;

public class ProgramCard implements IProgramCard {
    private int move;
    private int priority;
    private Action action;

    public ProgramCard(int move, int priority, Action action){
        this.move=move;
        this.priority=priority;
        this.action=action;
    }

    @Override
    public int getMove(){ return this.move; }

    @Override
    public int getPriority(){ return priority; }

    @Override
    public Action getAction(){
        return this.action;
    }

}
