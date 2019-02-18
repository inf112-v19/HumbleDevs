package inf112.skeleton.app;

public class ProgramCard implements IProgramCard{
    private int move;
    private int rotate;
    private int priority;

    public ProgramCard(int move, int rotate, int priority){
        this.move=move;
        this.rotate=rotate;
        this.priority=priority;
    }


    @Override
    public int getMove(){
        return move;
    }

    @Override
    public int getRotate() {
    return rotate;
    }

    @Override
    public int getPriority(){
        return priority;

    }


}
